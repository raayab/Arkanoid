

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

/**
 *
 */
public class BlocksDefinitionReader {

    /**
     * Creating a BlocksFromSymbolsFactory from the reader.
     * @param reader the reader from which
     * we generate the BlocksFromSymbolsFactory.
     * @return new BlocksFromSymbolsFactory.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        BufferedReader is = null;
        String[] definitions;
        Map<String, Integer> spacerWidths = new HashMap<String, Integer>();
        Map<String, BlockCreator> blockCreators =
                                       new HashMap<String, BlockCreator>();
        Map<String, String> defaults = new HashMap<String, String>();
        try {
             is = new BufferedReader(reader);
             String line = is.readLine();
             while (line != null) {
                if (line.startsWith("#")) {
                    line = is.readLine();
                    continue;
                }
                if (line.startsWith("default")) {
                    definitions = line.split(" ");
                    for (int i = 1; i < definitions.length; i++) {
                        defaults.put(definitions[i].split(":")[0],
                                    definitions[i].split(":")[1]);
                    }
                }
                if (line.startsWith("bdef")) {
                    readBdefLines(line, defaults, blockCreators);
                }
                if (line.startsWith("sdef")) {
                    readSdefLines(line, defaults, spacerWidths);
                }
                line = is.readLine();
             }
        } catch (IOException e) {
                System.out.println("Something went wrong with user input!");
        }
        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
   }
    /**
     * Reading information of the level's blocks from the line.
     * @param line the information line.
     * @param defaults the defaults values.
     * @param blockCreators the BlockCreators of the level.
     */
    private static void readBdefLines(String line,
                                    Map<String, String> defaults,
                                    final Map<String, BlockCreator>
                                                blockCreators) {
        final Map<String, String> definitionsValues =
                                    new TreeMap<String, String>();
        definitionsValues.putAll(defaults);
        String[] definitions = line.split(" ");
        for (int i = 1; i < definitions.length; i++) {
            definitionsValues.put(
                    definitions[i].split(":")[0],
                    definitions[i].split(":")[1]);
        }
        if ((!definitionsValues.containsKey("hit_points"))
            || (!definitionsValues.containsKey("width"))
            || (!definitionsValues.containsKey("height"))) {
            System.out.println("SOME BLOCK ARGUMENT MISSING");
            System.exit(1);
        } else {
        blockCreators.put(definitionsValues.get("symbol"), new BlockCreator() {
                private ColorsParser colorsParser = new ColorsParser();
                private int hits = Integer.parseInt(
                       definitionsValues.get("hit_points"));
                private int width = Integer.parseInt(
                       definitionsValues.get("width"));
                private int height = Integer.parseInt(
                        definitionsValues.get("height"));
                private Color stroke = stroke();
                //private Object fill = getFill(definitionsValues.get("fiil"));
                private Object[] fillKList = fillKList();
                @Override
                public Block create(int xpos, int ypos) {
                    return new Block(new Point(xpos, ypos),
                                    width,
                                    height,
                                    getFill(definitionsValues.get("fill")),
                                    stroke,
                                    hits,
                                    fillKList);
                }
                private Color stroke() {
                    Color stroke = null;
                    if (definitionsValues.get("stroke") != null) {
                        stroke = colorsParser.colorFromString(
                                            definitionsValues.get("stroke"));
                    }
                    return stroke;
                }
                private Object getFill(String s) {
                    Color fillDef = null;
                    BufferedImage imageGet = null;
                    if (s == null) {
                        return null;
                    }
                        if (colorsParser.colorFromString(s) == null) {
                            try {
                                imageGet =
                                 ImageIO.read(
                                         ClassLoader.
                                         getSystemClassLoader().
                         getResourceAsStream(s.substring(6, (s.length() - 1))));
                                return imageGet;
                            } catch (IOException e) {
                                System.out.println("BLOCKDEF.126: FAIL");
                            }
                        } else {
                            return colorsParser.colorFromString(s);
                        }
                    return fillDef;
                }
                private Object[] fillKList() {
                    Object[] fillK = new Object[hits];
                    /*
                    if (this.fill != null) {
                        for (Object obj: fillK) {
                            obj = fill;
                        }
                    }
                    */
                    for (int i = 0; i < fillK.length; i++) {
                        if (definitionsValues.containsKey(
                               "fill-".concat((i + 1) + ""))) {
                            fillK[i] = getFill(definitionsValues.get(
                                                "fill-".concat((i + 1) + "")));
                        } else {
                            fillK[i] = getFill(definitionsValues.get("fill"));
                        }
                    }
                    return fillK;
                }
            });
        }
    }
    /**
     * Reading information of the level's spaces from the line.
     * @param line the information line.
     * @param defaults the defaults values.
     * @param spacerWidths the information for the spaces.
     */
    private static void readSdefLines(String line,
                                    Map<String, String> defaults,
                                    Map<String, Integer> spacerWidths) {
        final Map<String, String> definitionsValues =
                                            new TreeMap<String, String>();
        String[] definitions = line.split(" ");
        for (int i = 1; i < definitions.length; i++) {
            definitionsValues.put(
                    definitions[i].split(":")[0],
                    definitions[i].split(":")[1]);
        }
        definitionsValues.putAll(defaults);
        spacerWidths.put(definitionsValues.get("symbol"),
                            Integer.parseInt(definitionsValues.get("width")));
    }
}
