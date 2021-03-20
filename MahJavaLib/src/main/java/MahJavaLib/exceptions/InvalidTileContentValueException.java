package MahJavaLib.exceptions;

public class InvalidTileContentValueException extends RuntimeException {
    public InvalidTileContentValueException() {
    }

    public InvalidTileContentValueException(String message) {
        super(message);
    }
}
