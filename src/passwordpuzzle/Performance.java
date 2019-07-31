package passwordpuzzle;

/**
 * A class for assessing performance of an application.
 * @author Dominic
 */
public class Performance {
    private static long timeStamp;
    
    public static void tic(){
        timeStamp = System.currentTimeMillis();
    }
    
    public static void toc(){
        System.out.println("" + (System.currentTimeMillis() - timeStamp));
    }
}
