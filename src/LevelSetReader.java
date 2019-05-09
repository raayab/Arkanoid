
import Gui.DialogManager;
import Gui.DrawSurface;
import Gui.GuiManager;
import Gui.KeyboardSensor;


import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reading a set of levels to run from a file.
 */
public class LevelSetReader {

    /**
     * Creating new menu of tasks according to the reader.
     * @param reader the reader.
     * @param keyboard the KeyboardSensor.
     * @param runner the AnimationRunner.
     * @param d the DrawSurface.
     * @param highScoresTable the HighScoresTable.
     * @param GuiManager the GuiManager.
     * @param fileName the file path.
     * @return the needed menu.
     */
    public Menu<Task<Void>> fromReader(java.io.Reader reader,
                                        final KeyboardSensor keyboard,
                                        final AnimationRunner runner,
                                        final DrawSurface d,
                                        final HighScoresTable highScoresTable,
                                        final GuiManager GuiManager,
                                        final File fileName) {
        Menu<Task<Void>> subMenu = new Menu<Task<Void>>() {
            private List<Selection<Task<Void>>> menu =
                                        new ArrayList<Selection<Task<Void>>>();
            private Task<Void> pressedKey;
            private Boolean stop = false;

            @Override
            public void addSelection(String key,
                                     String message,
                                     Task<Void> returnVal) {
                Selection<Task<Void>> selection =
                             new Selection<Task<Void>>(key, message, returnVal);
                menu.add(selection);
            }

            @Override
            public Task<Void> getStatus() {
                return pressedKey;
            }

            @Override
            public void resetStop() {
                stop = false;
            }

            @Override
            public void addSubMenu(String key,
                                   String message,
                                   Menu<Task<Void>> subMenu) {
                System.out.println("Not supported");
            }

            @Override
            public void doOneFrame(DrawSurface d, double dt) {
                d.setColor(Color.ORANGE);
                d.fillRectangle(0, 0, 800, 600);
                d.setColor(Color.MAGENTA);
                int y = 100;
                d.setColor(Color.BLACK);
                for (Selection<Task<Void>> selection : menu) {
                    d.drawText(100, y + 50, "(" + selection.getKey() + ")", 15);
                    d.drawText(130, y + 50, selection.getMessage(), 15);
                    y = y + 50;
                }
                for (Selection<Task<Void>> selection : menu) {
                    if (keyboard.isPressed(selection.getKey())) {
                        pressedKey = selection.getReturnVal();
                        stop = true;
                    }
                }
            }

            @Override
            public boolean shouldStop() {
                return this.stop;
            }
        };

        try {
            final LineNumberReader is = new LineNumberReader(reader);
            String line = is.readLine();
            while (line != null) {
                if (is.getLineNumber() % 2 != 0) {
                    String key = line.split(":")[0];
                    String message = line.split(":")[1];
                    subMenu.addSelection(key, message, new Task<Void>() {
                        //reading the "level specifications" line.
                        private String file = is.readLine();
                        private List<LevelInformation> levels = null;
                        private LevelSpecificationReader
                                            levelSpecificationReader =
                                                new LevelSpecificationReader();
                        @Override
                        public Void run() {
                            //try {
                                levels = levelSpecificationReader.fromReader(
                                        new InputStreamReader(
                                                ClassLoader.
                                                        getSystemClassLoader().
                                                    getResourceAsStream(file)));
                            /*} catch (FileNotFoundException e5) {
                                System.out.println("LevelSetReader.120: error");
                            }*/
                            HighScoresAnimation highScoresAnimation =
                                new HighScoresAnimation(highScoresTable,
                                               KeyboardSensor.SPACE_KEY,
                                                keyboard);
                                        final Counter score = new Counter();
                            ScoreTrackingListener scoreTrackingListener =
                                               new ScoreTrackingListener(score);
                            Counter lives = new Counter(Ass6Game.LIVES);
                            ScoreIndicator scoreIndicator =
                                                    new ScoreIndicator(score);
                            LivesIndicator liveIndicator =
                                                new LivesIndicator(d, lives);
                            final GameFlow game = new GameFlow(runner,
                                                                keyboard,
                                                                d,
                                                                liveIndicator,
                                                                scoreIndicator,
                                                         scoreTrackingListener);
                            game.runLevels(levels);
                            if (highScoresTable.getRank(score.getValue())
                                          <= Ass6Game.HIGH_SCORE_TABLE_SIZE) {
                DialogManager dialog = GuiManager.getDialogManager();

                String name = dialog.showQuestionDialog("Name",
                                              "What is your name?", "");
                highScoresTable.add(new ScoreInfo(name,
                                                    score.getValue()));
                try {
                    highScoresTable.save(fileName);
                } catch (IOException e) {
                    System.out.println("error saving file");
                }
            }
            KeyPressStoppableAnimation endScreenK =
                                new KeyPressStoppableAnimation(keyboard,
                                KeyboardSensor.SPACE_KEY,
                                highScoresAnimation);
            runner.run(endScreenK);
                            return null;
                        }
                    });
                 }
                line = is.readLine();
            }
        } catch (IOException e) {
            System.out.println("FAIL Reading levelset File");
        }
        return subMenu;
    }

}
