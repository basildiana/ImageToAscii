package image_char_matching;

import java.util.HashSet;
import java.util.TreeMap;

/**
 * Matches sub images brightness levels to the closest ASCII character brightness
 * @author Diana Basil TODO
 */
public class SubImgCharMatcher {
    private final BrightnessAscii asciiBrightness;
    private final HashSet<Character> characters; // active chars are in this set
    // tree map for fast searching, key=NormBrightness, value is the character
    private final TreeMap<Double, Character> NormBToCharTreeMap;
    private boolean changedCharacters;

    /**
     * Constructs a character matcher initialized with a specific starting set of characters.
     * @param charset The initial array of characters to be utilized by the algorithm.
     */
    public SubImgCharMatcher(char[] charset) {
        this.asciiBrightness = new BrightnessAscii();
        this.characters = new HashSet<>();
        this.NormBToCharTreeMap = new TreeMap<>();
        for (char c : charset) {
            this.addChar(c);
        }
    }

    /** Adds a character to the active set
     * @param c The character to add*/
    public void addChar(char c) {
        if (characters.add(c)) {
            changedCharacters = true; // changed the set
        }
    }
    /** Removes a character from the active set
     * @param c The character to remove */
    public void removeChar(char c) {
        if (characters.remove(c)) {
            changedCharacters = true; // changed the set
        }
    }

    /**
     * given brightness of an image we return the char of the norm brightness which is closest.
     * @param brightness The  brightness of an image
     * @return The best matching character.
     */
    public char getCharByImageBrightness(double brightness){
        if (changedCharacters) {
            updateTreeMap();
        }
        // the most close key (NormBrightness) to image brightness value
        Double closeUp = NormBToCharTreeMap.ceilingKey(brightness);
        Double closeDown = NormBToCharTreeMap.floorKey(brightness);

        if (closeUp == null){
            return NormBToCharTreeMap.get(closeDown);
        }
        if (closeDown == null){
            return NormBToCharTreeMap.get(closeUp);
        }
        // compare diff, if the same take the down
        double diffUp = Math.abs(closeUp - brightness);
        double diffDown = Math.abs(brightness - closeDown);

        if (diffDown < diffUp) {
            char charDown = NormBToCharTreeMap.get(closeDown);
            return charDown;
        }
        else if (diffUp < diffDown) {
            char charUp = NormBToCharTreeMap.get(closeUp);
            return charUp;
        }
        else {//  (diffUp == diffDown) {
            char charDown = NormBToCharTreeMap.get(closeDown);
            char charUp = NormBToCharTreeMap.get(closeUp);
            char finalChar = (char) Math.min(charDown, charUp); // lower ASCII character value
            return finalChar;
        }
    }
    /**
     * Helper method to find the minimum and maximum raw brightness values
     * within the currently active character set.
     * * @return A double array, index 0 is the minBrightness, index 1 is the maxBrightness.
     */
    private double[] findMinMax() {
        double minB = Double.MAX_VALUE;
        double maxB = -Double.MAX_VALUE;
        double[] solution = new double[2];

        for (char c : characters) {
            double rawB = asciiBrightness.getAsciiBrightness(c);
            if (rawB < minB) {
                minB = rawB;
            }
            if (rawB > maxB) {
                maxB = rawB;
            }
        }
        solution[0]=minB;
        solution[1]=maxB;
        return solution;
    }

    /**
     * Performs linear stretch which normalize the brightnesses.
     * update the tree with the new norm brightness (keys) and their chars (values)
     */
    private void updateTreeMap() {
        NormBToCharTreeMap.clear();
        if (characters.isEmpty()) {
            changedCharacters = false;
            return;
        }
        // min/max  brightness within active set
        double[] minMax = findMinMax();
        double minB = minMax[0];
        double maxB = minMax[1];

        // Linear stretch formula: (charBrightness - min) / (max - min)
        double denominator = maxB - minB; // assuming this is not zero
        for (char c : characters) {
            double brightness = asciiBrightness.getAsciiBrightness(c);
            double normBrightness = (brightness - minB) / denominator;

            // assuming all char normBrightness are different
            NormBToCharTreeMap.put(normBrightness, c);
        }
        changedCharacters = false; // the tree is most updated, so flag is false
    }
}
