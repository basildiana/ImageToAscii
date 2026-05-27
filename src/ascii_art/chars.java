package ascii_art;
import  image_char_matching.SubImgCharMatcher;

import java.util.TreeSet;

/**
 * Handles printing the active character set in ASCII order.
 * @author Diana Basil and Meital Lubarski
 */
public class chars implements feature{
    private static  final  String CHAR_SEPARATOR = " ";
    private final SubImgCharMatcher charMatcher;

    /**
     * Constructs a chars feature.
     * @param charMatcher the active character matcher.
     */
    public chars(SubImgCharMatcher charMatcher){
        this.charMatcher = charMatcher;
    }

    /**
     * Prints the active character set.
     *
     * @param args user args after the command name.
     */
    @Override
    public void execute(String args){
        TreeSet<Character> charset = charMatcher.getCharset();
        for(char c : charset){
            System.out.print(c + CHAR_SEPARATOR);
        }
        System.out.println();
    }

}
