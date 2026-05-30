package ascii_art;

import image.Image;
import image.ImageProcessor;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the user interface of the ASCII art program.
 * Reads user commands and delegates each command to its matching feature
 *
 * @author Diana Basil and Meital Lubarski
 */
public class Shell {
    private static final String PROMPT = ">>> ";
    private static final String EMPTY_STRING = "";
    private static final String SPACE_REGEX = "\\s+";
    private static final int COMMAND_INDEX = 0;

    private static final String ADD = "add";
    private static final String REMOVE = "remove";
    private static final String CHARS = "chars";
    private static final String OUTPUT = "output";
    private static final String REVERSE = "reverse";
    private static final String RES = "res";
    private static final String ASCII_ART = "asciiArt";
    private static final String EXIT = "exit";

    private static final char DEFAULT_FIRST_CHAR = '0';
    private static final char DEFAULT_LAST_CHAR = '9';
    private static final int MIN_CHARSET_SIZE = 2;

    private final Map<String, feature> featureMap;
    private final SubImgCharMatcher charMatcher;
    private final ImageProcessor imageProcessor;

    private Image image;
    private output output;
    private res res;


    /**
     * Constructs a shell with the default charset and resolution.
     */
    public Shell() {
        this.charMatcher = new SubImgCharMatcher(createDefaultCharset());
        this.imageProcessor = new ImageProcessor();
        this.featureMap = new HashMap<>();
    }

    /**
     * Runs the shell with the given image path.
     *
     * @param imageName the image file path.
     */
    public void run(String imageName) {
        try {
            image = imageProcessor.padImage(new Image(imageName));
            initFeatures();
            while (true) {
                System.out.print(PROMPT);
                String input = KeyboardInput.readLine();
                try {
                    if (!handleInput(input)) {
                        return;
                    }
                } catch (AsciiExceptions e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            return;
        }
    }

    /**
     * Handles and executes a single user command.
     *
     * @param input the full user input line.
     * @return false if the user requested to exit, true otherwise.
     * @throws AsciiExceptions if the command is invalid.
     */
    private boolean handleInput(String input) throws AsciiExceptions {
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            throw AsciiExceptions.exInvalidCommand();
        }
        String[] words = trimmedInput.split(SPACE_REGEX);
        String command = words[COMMAND_INDEX];
        String args = extractArgs(trimmedInput, command);
        if (command.equals(EXIT)) {
            return false;
        }
        if (command.equals(ASCII_ART)) {
            runAsciiArt();
            return true;
        }
        feature selectFeature = featureMap.get(command);
        if (selectFeature == null) {
            throw AsciiExceptions.exInvalidCommand();
        }
        selectFeature.execute(args);
        return true;
    }

    /**
     * Runs the ASCII art algorithm using the current settings.
     *
     * @throws AsciiExceptions if the charset is too small.
     */
    private void runAsciiArt() throws AsciiExceptions {
        if (charMatcher.getCharset().size() < MIN_CHARSET_SIZE) {
            throw AsciiExceptions.exSmallCharset();
        }
        AsciiArtAlgorithm algorithm = new AsciiArtAlgorithm(image, res.getCurrentResolution(), charMatcher,
                imageProcessor);
        output.getCurrentOutput().out(algorithm.run());
    }

    /**
     * Extracts the arguments part from the user input.
     *
     * @param input   the full user input.
     * @param command the command name.
     * @return the arguments after the command.
     */
    private String extractArgs(String input, String command) {
        if (input.length() == command.length()) {
            return EMPTY_STRING;
        }
        return input.substring(command.length()).trim();
    }

    /**
     * Initializes all shell features and stores them in the feature map.
     */
    private void initFeatures() {
        output = new output();
        res = new res(image);
        featureMap.put(ADD, new add(charMatcher));
        featureMap.put(REMOVE, new remove(charMatcher));
        featureMap.put(CHARS, new chars(charMatcher));
        featureMap.put(OUTPUT, output);
        featureMap.put(REVERSE, new reverse(charMatcher));
        featureMap.put(RES, res);
    }


    /**
     * Creates the default charset containing the digits 0-9.
     *
     * @return an array of default ASCII characters.
     */
    private char[] createDefaultCharset() {
        int size = DEFAULT_LAST_CHAR - DEFAULT_FIRST_CHAR + 1;
        char[] defaultCharset = new char[size];
        for (int i = 0; i < size; i++) {
            defaultCharset[i] = (char) (DEFAULT_FIRST_CHAR + i);
        }
        return defaultCharset;
    }

    /**
     * Program entry point.
     *
     * @param args command line arguments. args[0] is the image path.
     */
    public static void main(String[] args) {
        Shell shell = new Shell();
        shell.run(args[0]);
    }
}
