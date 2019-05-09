
import Gui.DrawSurface;



import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * read level infomation.
 */
public class LevelSpecificationReader {

   /**
     * get a file name and returns a list of LevelInformation objects.
     *
     * @param reader the file to read
     * @return list of LevelInformation objects.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> levelInformationList =
                new ArrayList<LevelInformation>();
        BufferedReader is = null;

        try {
             is = new BufferedReader(reader);
             String line = is.readLine();
             while (line != null) {
                  Map<String, String> info = new HashMap<String, String>();
                 while (!line.matches("END_LEVEL")) {

                    if (line.startsWith("#")
                            || line.equals("")
                            || line.equals("START_LEVEL")) {

                        line = is.readLine();
                        continue;
                    }
                    if (line.contains(":")) {
                       info.put(line.split(":")[0], line.split(":")[1]);
                       line = is.readLine();
                    }
                    if (line.matches("START_BLOCKS")) {
                        int i = 0;
                        line = is.readLine();
                        Map<String, String> blockInfo =
                                            new HashMap<String, String>();
                        while (!line.matches("END_BLOCKS")) {
                            if (!line.equals("")) {
                                blockInfo.put(i + "line", line);
                                i++;
                            }
                            line = is.readLine();
                        }
                    levelInformationList.add(readInfo(info, blockInfo));
                        line = is.readLine();
                    }
                }
                 line = is.readLine();
            }
        } catch (IOException e) {
                System.out.println("Something went wrong with user input!");
        }
        return levelInformationList;
    }

    /**
     * return LevelInformation according to the given info.
     *
     * @param info map with level information.
     * @param blockInfo map with block info.
     * @return level information.
     */
    private LevelInformation readInfo(final Map<String, String> info,
                                    final Map<String, String> blockInfo) {

        LevelInformation levelInformation = new LevelInformation() {
            private String[] ballVelocities =
                                        info.get("ball_velocities").split(" ");

            @Override
            public int numberOfBalls() {
                return ballVelocities.length;
            }

            @Override
            public List<Velocity> initialBallVelocities() {
                List<Velocity> velocitiesList = new ArrayList<Velocity>();
                for (String s : ballVelocities) {
                    velocitiesList.add(Velocity.fromAngleAndSpeed(
                                          Double.parseDouble(s.split(",")[0]),
                                          Double.parseDouble(s.split(",")[1])));
                }
                return velocitiesList;
            }

            @Override
            public int paddleSpeed() {
                return Integer.parseInt(info.get("paddle_speed"));
            }

            @Override
            public int paddleWidth() {
                return Integer.parseInt(info.get("paddle_width"));
            }

            @Override
            public String levelName() {
                return info.get("level_name");
            }

            @Override
            public Sprite getBackground() {

                Sprite s = new Sprite() {
                    private ColorsParser colorsParser = new ColorsParser();
                    private boolean ifImage = info.get("background")
                                                            .contains("image");
                    private Image image = getImageFile();

                    private Image getImageFile() {
                        BufferedImage imageGet = null;
                        if (ifImage) {
                        try {
                                imageGet = ImageIO.read(
                                        ClassLoader.getSystemClassLoader().
                                        getResourceAsStream(
                                               info.get("background").substring(
                                    6, (info.get("background").length() - 1))));
                        } catch (IOException e) {
                            System.out.println("LEVELSPEC.139: FAIL");
                        }
                        }
                        return imageGet;
                    }
                    @Override
                    public void drawOn(DrawSurface d) {
                        if (colorsParser.colorFromString(
                                            info.get("background")) != null) {

                            d.setColor(colorsParser.colorFromString(
                                                    info.get("background")));
                   d.fillRectangle(0,
                                            0,
                                            Ass6Game.SCREEN_WIDTH,
                                            Ass6Game.SCREEN_HEIGHT);
                        } else {
                            d.drawImage(0, 0, image);
                        }
                    }

                    @Override
                    public void timePassed(double dt) {
                    }
                };
                return s;
            }

            @Override
            public List<Block> blocks() {
                List<Block> blocks = new ArrayList<Block>();
                String[] blocksLine = new String[blockInfo.keySet().size()];
                BlocksFromSymbolsFactory blocksFromSymbolsFactory = null;
                //try {
                    blocksFromSymbolsFactory = BlocksDefinitionReader
                            .fromReader(new InputStreamReader(ClassLoader.
                                                    getSystemClassLoader().
                                                    getResourceAsStream(
                                               info.get("block_definitions"))));
                /*} catch (IOException e1) {
                    System.out.println("LELELSPEC.177: file not found");
                }*/
                if (blocksFromSymbolsFactory != null) {
                    int y = Integer.parseInt(info.get("blocks_start_y"));
                    for (int i = 0; i < blockInfo.keySet().size(); i++) {
                        int x = Integer.parseInt(info.get("blocks_start_x"));
         blocksLine[i] = blockInfo.get(((i) + "").concat("line"));

                        for (int j = 0; j < blocksLine[i].length(); j++) {
                            if (blocksFromSymbolsFactory.isBlockSymbol(
                                    String.valueOf(blocksLine[i].charAt(j)))) {

                                blocks.add(blocksFromSymbolsFactory.getBlock(
                                        String.valueOf(blocksLine[i].charAt(j)),
                                                x, y));
                                x = x + (int) blocksFromSymbolsFactory.getBlock(
                                        String.valueOf(blocksLine[i].charAt(j)),
                                                x, y).getBlockRect().getWidth();
                            }
                            if (blocksFromSymbolsFactory.isSpaceSymbol(
                                    String.valueOf(blocksLine[i].charAt(j)))) {
                                x = x + blocksFromSymbolsFactory.getSpaceWidth(
                                       String.valueOf(blocksLine[i].charAt(j)));
                            }
                        }
                        y = y +  (Integer.parseInt(info.get("row_height")));
                    }
                }
                return blocks;
            }

            @Override
            public int numberOfBlocksToRemove() {
                return Integer.parseInt(info.get("num_blocks"));
            }
        };
    return levelInformation;
    }
}
