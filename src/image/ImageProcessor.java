package image;

import java.awt.Color;

/**
 * Handles image processing operations required for the ASCII art algorithm:
 * padding, splitting into sub-images, and brightness calculation.
 *
 * @author Diana Bsil and Meital Lubarski
 */
public class ImageProcessor {
    private static final int POWER_BASE = 2;
    private static final int RGB_MAX_VALUE = 255;

    private static final double RED_GREYSCALE = 0.2126;
    private static final double GREEN_GREYSCALE = 0.7152;
    private static final double BLUE_GREYSCALE = 0.0722;

    /**
     * Pads the given image with white pixels so its width and height are powers of two.
     *
     * @param image The image to pad.
     * @return A new padded image, or the original image if no padding is needed.
     */
    public Image padImage(Image image) {
        int oldWidth = image.getWidth();
        int oldHeight = image.getHeight();
        int newWidth = getNextPowerOfTwo(oldWidth);
        int newHeight = getNextPowerOfTwo(oldHeight);
        if (oldWidth == newWidth && oldHeight == newHeight) {
            return image;
        }
        Color[][] padPixels = createPaddedPixels(image, newWidth, newHeight);
        return new Image(padPixels, newWidth, newHeight);
    }

    /**
     * Splits the image into square sub-images according to the requested resolution.
     * The resolution represents the number of sub-images in each row.
     *
     * @param image      The image to split.
     * @param resolution Number of sub-images in each row.
     * @return A two-dimensional array of sub-images.
     */
    public Image[][] splitToSubImages(Image image, int resolution) {
        int subImageSize = image.getWidth() / resolution;
        int rows = image.getHeight() / subImageSize;
        Image[][] subImages = new Image[rows][resolution];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < resolution; col++) {
                subImages[row][col] = createSubImage(image, row, col, subImageSize);
            }
        }
        return subImages;
    }

    /**
     * Calculates the normalized brightness of an image.
     *
     * @param image The image whose brightness should be calculated.
     * @return Brightness value between 0 and 1.
     */
    public double calculateBrightness(Image image) {
        double greySum = 0;
        int width = image.getWidth();
        int height = image.getHeight();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                greySum += calculatePixelGreyScale(image.getPixel(row, col));
            }
        }
        return greySum / (width * height * RGB_MAX_VALUE);
    }

    private Color[][] createPaddedPixels(Image image, int newWidth, int newHeight) {
        int oldWidth = image.getWidth();
        int oldHeight = image.getHeight();
        int rowOffset = (newHeight - oldHeight) / POWER_BASE;
        int colOffset = (newWidth - oldWidth) / POWER_BASE;
        Color[][] pixels = new Color[newHeight][newWidth]; //create new matrix
        for (int row = 0; row < newHeight; row++) {
            for (int col = 0; col < newWidth; col++) {
                pixels[row][col] = getPaddedPixel(image, row, col, rowOffset, colOffset);
            }
        }
        return pixels;
    }

    private Color getPaddedPixel(Image image, int row, int col, int rowOffset, int colOffset) {
        int originalRow = row - rowOffset;
        int originalCol = col - colOffset;
        if (originalRow < 0 || originalRow >= image.getHeight() || originalCol < 0 || originalCol >= image.getWidth()) {
            return Color.WHITE;
        }
        return image.getPixel(originalRow, originalCol);
    }

    private Image createSubImage(Image image, int subImageRow, int subImageCol, int subImageSize) {
        Color[][] pixels = new Color[subImageSize][subImageSize];
        int startRow = subImageRow * subImageSize;
        int startCol = subImageCol * subImageSize;
        for (int row = 0; row < subImageSize; row++) {
            for (int col = 0; col < subImageSize; col++) {
                pixels[row][col] = image.getPixel(startRow + row, startCol + col);
            }
        }
        return new Image(pixels, subImageSize, subImageSize);
    }

    private double calculatePixelGreyScale(Color color) {
        return color.getRed() * RED_GREYSCALE + color.getGreen() * GREEN_GREYSCALE + color.getBlue() * BLUE_GREYSCALE;
    }

    private int getNextPowerOfTwo(int number) {
        int power = 1;
        while (power < number) {
            power *= POWER_BASE;
        }
        return power;
    }
}
