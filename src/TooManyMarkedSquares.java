/**
 * Exception Alias for when a MarupekeGrid has too many marked squares (greater than half its total tiles [size^2])
 */
public class TooManyMarkedSquares extends Exception{
    public TooManyMarkedSquares(String message) {
        super(message);
    }
}
