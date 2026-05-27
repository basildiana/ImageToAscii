package ascii_art;

import image.Image;
import image.ImageProcessor;
import image_char_matching.SubImgCharMatcher;

import java.util.Arrays;

/**
 * creates ASCII art for a single run.
 * @authors Diana Basil and Meital Lubarski
 */
public class AsciiArtAlgorithm {
    private final Image image;
    private final int resolution;
    private final SubImgCharMatcher charManager;
    private final ImageProcessor imageProcessor;

    /**
     * Constructs an AsciiArtAlgorithm instance for a single run
     * @param image       The image.
     * @param resolution  The resolution
     * @param subImgCharMatcher the active character set
     */
    public AsciiArtAlgorithm(Image image, int resolution, SubImgCharMatcher subImgCharMatcher, ImageProcessor imageProcessor) {
        this.image = image;
        this.resolution = resolution;
        this.charManager = subImgCharMatcher;
        this.imageProcessor = imageProcessor;
    }

    /**
     * Runs the algorithm to transform the given image into a 2D array of ASCII characters.
     * @return A 2D array of characters representing the ASCII art.
     */
    public char [][] run(){
        Image padImage = imageProcessor.padImage(this.image);
        Image[][] subImages = imageProcessor.splitToSubImages(padImage, this.resolution);
        int rows = subImages.length;
        int cols = subImages[0].length;
        char[][] asciiArtResult = new char[rows][cols];

        // each subimage gets its closest brightness character
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double subImageBrightness = imageProcessor.calculateBrightness(subImages[r][c]);
                asciiArtResult[r][c] = charManager.getCharByImageBrightness(subImageBrightness);
            }
        }
        return asciiArtResult;
    }
}

