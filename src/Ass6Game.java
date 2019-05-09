
import Gui.DrawSurface;
import Gui.GuiManager;
import Gui.KeyboardSensor;


import java.io.*;


/**
 *
 */
public class Ass6Game {
    public static final int HIGH_SCORE_TABLE_SIZE = 5;
    public static final int SCREEN_HEIGHT = 600;
    public static final int SCREEN_WIDTH = 800;
    public static final int LIVES = 7;
    /**
     * running the game.
     * @param args file path from command line
     */
    public static void main(String[] args) {
        final GuiManager GuiManager = new GuiManager("Arkanoid", SCREEN_WIDTH, SCREEN_HEIGHT);
        final KeyboardSensor keyboard = GuiManager.getKeyboardSensor();
        final DrawSurface d = GuiManager.getDrawSurface();
        final AnimationRunner runner = new AnimationRunner(GuiManager, 60);
        final File fileName = new File("highscores");
        final HighScoresTable highScoresTable =
                                    new HighScoresTable(HIGH_SCORE_TABLE_SIZE);
    try {
        highScoresTable.load(fileName);
    } catch (IOException e) {
        try {
            highScoresTable.save(fileName);
        } catch (IOException e1) {
            System.out.println("file highscore error");
        }
    }
    LevelSetReader levelSetReader = new LevelSetReader();

    String levelSetsFile = "level_sets.txt";
    if (args.length != 0) {
        levelSetsFile = args[0];
    }
    Reader reader = null;

   InputStream is = ClassLoader.getSystemClassLoader().
                            getResourceAsStream(levelSetsFile);
    if (is == null) {
        System.out.println("file path: " + levelSetsFile);
        System.out.println("ERROR READING FILE.");
    }
    reader = new InputStreamReader(is);

    Menu<Task<Void>> menu =
            new MenuAnimation<Task<Void>>("Menu Title", d, keyboard, runner);
    Menu<Task<Void>> subMenu = levelSetReader.
        fromReader(reader, keyboard, runner, d, highScoresTable, GuiManager, fileName);

    menu.addSelection("h", "High Scores", new Task<Void>() {
        @Override
        public Void run() {
            HighScoresAnimation highScoresAnimation =
                                    new HighScoresAnimation(highScoresTable,
                                                       KeyboardSensor.SPACE_KEY,
                                                        keyboard);
            KeyPressStoppableAnimation highScreen =
                                new KeyPressStoppableAnimation(keyboard,
                                                       KeyboardSensor.SPACE_KEY,
                                                       highScoresAnimation);
            runner.run(highScreen);
            return null;
       }
    });

    menu.addSelection("s", "New Game", new Task<Void>() {
        @Override
        public Void run() {
            return null;
        }
    });

     menu.addSelection("q", "Quit", new Task<Void>() {
        @Override
        public Void run() {
                System.exit(0);
                GuiManager.close();
                return null;
        }
     });
     menu.addSubMenu("s", "New Game", subMenu);
     while (true) {
        menu.resetStop();
        runner.run(menu);
        // A menu with the selections is displayed.
        // Runs until user presses "a", "b"  or "c"
        Task<Void> result = menu.getStatus();
        result.run();
        // result will contain "option a", "option b"
        // or "option c"
     }
    }

}
