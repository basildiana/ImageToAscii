package ascii_art;

import image.Image;

/**
 * Handles the resolution command.
 * Keeps the current number of characters in each row and updates it
 * according to the user's input.
 *
 * @author Diana Basil and Meital Lubarski
 */
public class res implements feature{
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String SPACE_REGEX = "\\s+";
    private static final String MESSAGE_PREFIX = "Resolution set to ";
    private static final String DOT = ".";

    private static final int DEFAULT_RESOLUTION = 2;
    private static final int RESOLUTION_FACTOR = 2;
    private static final int MIN_RESOLUTION = 1;

    private final Image image;
    private int currentResolution;

    /**
     * Constructs a resolution feature.
     *
     * @param image the padded image used to calculate resolution boundaries.
     */
    public res(Image image){
        this.image = image;
        this.currentResolution = DEFAULT_RESOLUTION;
    }

    /**
     * Executes the res command.
     *
     * @param args user args after the res command.
     * @throws AsciiExceptions if the format is invalid or boundaries are exceeded.
     */
    @Override
    public void execute(String args) throws AsciiExceptions {
        if(args == null || args.trim().isEmpty()){
            System.out.println(MESSAGE_PREFIX + currentResolution + DOT);
            return;
        }
        String firstArg = args.trim().split(SPACE_REGEX)[0];
        if(firstArg.equals(UP)){
            increaseResolution();
        }
        else if(firstArg.equals(DOWN)){
            decreaseResolution();
        }
        else{
            throw AsciiExceptions.exResFormat();
        }
        System.out.println(MESSAGE_PREFIX + currentResolution + DOT);
    }

    /**
     * Gets the current resolution.
     *
     * @return the current resolution.
     */
    public int getCurrentResolution(){
        return currentResolution;
    }

    private void increaseResolution() throws  AsciiExceptions{
        int newRes = currentResolution * RESOLUTION_FACTOR;
        if(newRes > image.getWidth()){
            throw AsciiExceptions.exResBounds();
        }
        currentResolution = newRes;
    }

    private void decreaseResolution() throws  AsciiExceptions{
        int newRes = currentResolution / RESOLUTION_FACTOR;
        int minCharsInRow = Math.max(MIN_RESOLUTION, image.getWidth()/ image.getHeight());
        if(newRes < minCharsInRow){
            throw  AsciiExceptions.exResBounds();
        }
        currentResolution = newRes;
    }
}
