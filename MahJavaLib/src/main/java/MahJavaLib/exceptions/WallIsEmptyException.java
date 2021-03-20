package MahJavaLib.exceptions;

public class WallIsEmptyException extends Exception {
    public WallIsEmptyException() {
    }

    public WallIsEmptyException(String message) {
        super(message);
    }
}
