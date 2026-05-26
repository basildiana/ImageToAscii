//package ascii_art;
//
//import image.Image;
//import image_char_matching.SubImgCharMatcher;
//import java.io.IOException;
//import java.util.Arrays;
//
//public class SaintyCheckPartA {
//    public static void main(String[] args) {
//        System.out.println("--- Running Sanity Check with File ---");
//
//        try {
//            // 1. Call your file-loading constructor here!
//            // Make sure "board.jpeg" is sitting in the root folder of your project
//            Image realBoardImage = new Image("src/board.jpeg");
//
//            // 2. Initialize the matcher with charset ['m', 'o']
//            char[] charset = {'m', 'o'};
//            SubImgCharMatcher matcher = new SubImgCharMatcher(charset);
//
//            // 3. Run the algorithm at resolution 2
//            int resolution = 2;
//            AsciiArtAlgorithm algorithm = new AsciiArtAlgorithm(realBoardImage, resolution, matcher);
//
//            char[][] result = algorithm.run();
//
//            // 4. Print and check
//            System.out.println(Arrays.deepToString(result));
//
//        } catch (IOException e) {
//            System.err.println("Could not find or read 'board.jpeg'. " +
//                    "Make sure it's placed in the correct directory!");
//            e.printStackTrace();
//        }
//    }
//}