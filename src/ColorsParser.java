
import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;


/**
 *
 */
public class ColorsParser {

    private Map<String, Color> colorsMap =
                    new TreeMap<String, Color>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Creating new Color Parser with mapped strings to colors.
     */
    public ColorsParser() {
        colorsMap.put("black", Color.black);
        colorsMap.put("blue", Color.blue);
        colorsMap.put("cyan", Color.cyan);
        colorsMap.put("darkGray", Color.darkGray);
        colorsMap.put("gray", Color.gray);
        colorsMap.put("green", Color.green);
        colorsMap.put("lightGray", Color.lightGray);
        colorsMap.put("magenta", Color.magenta);
        colorsMap.put("orange", Color.orange);
        colorsMap.put("pink", Color.pink);
        colorsMap.put("red", Color.red);
        colorsMap.put("white", Color.white);
        colorsMap.put("yellow", Color.yellow);
    }
    /**
     * parse color definition and return the specified color.
     * @param s string to transform to a color.
     * @return the matched color.
     */
    public Color colorFromString(String s) {
        if (s.contains("RGB")) {
            return new Color(Integer.parseInt(s.split("\\(")[2].split(",")[0]),
                            Integer.parseInt(s.split("\\(")[2].split(",")[1]),
                            Integer.parseInt(s.split("\\(")[2].split(",")[2].
                                                            split("\\)")[0]));
        } else if (s.contains("image")) {
            return null;
        } else {
            return colorsMap.get(s.split("\\(")[1].split("\\)")[0]);
        }
   }
}
