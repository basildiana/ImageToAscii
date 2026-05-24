package image_char_matching;
/**
 * The BrightnessAscii class is responsible for calculating for the ASCII characters
 * their (raw) brightness values.
 * * @author Diana Basil TODO
 */
public class BrightnessAscii {
    private static final int NUM_PIXELS = 256; // 16x16
    private static final int START_CHAR = 32;
    private static final int END_CHAR = 126;
    private static final int NUM_CHARS = END_CHAR - START_CHAR + 1;
    /** array stores the raw brightness */
    private final double[] AsciiNumArray;
    /**
     * Constructs a new BrightnessAscii
     * Initializes the internal array and triggers the calculation of
     * brightness values for all ASCII characters.
     */
    public BrightnessAscii() {
        this.AsciiNumArray = new double[NUM_CHARS];
        updateArray();
    }
    /**
     * Calculates the raw brightness of a single character by counting its white pixels.
     * @param c The character whose brightness needs to be calculated.
     * @return A double (brightness)
     */
    private static double singleCharBrightness(char c) {
        // can do CharConverter.convertToBoolArray(c) because it is static method
        boolean[][] boolMatrix = image_char_matching.CharConverter.convertToBoolArray(c);
        int numWhite = 0;
        for (int row = 0; row < boolMatrix.length; row++) {
            for (int col = 0; col < boolMatrix[row].length; col++) {
                if (boolMatrix[row][col]) {
                    numWhite++;
                }
            }
        }
        double brightness =(double) numWhite / NUM_PIXELS;
        return brightness;
    }
    /**
     * updates array by iterating through all characters
     * and computing their raw brightness values.
     */
    private void updateArray() {
        for (int i = 0; i < NUM_CHARS; i++) {
            char c = (char) (i + START_CHAR);
            AsciiNumArray[i] = singleCharBrightness(c);
        }
    }
    /**
     * gets the raw brightness of a given character
     * @param c The character
     * @return The raw brightness value
     */
    public double getAsciiBrightness(char c) {
        if (c < START_CHAR || c > END_CHAR) {
            return (double)0;
        }
        // Shift the character's ASCII value to map into index range
        return AsciiNumArray[c - START_CHAR];
    }

}
