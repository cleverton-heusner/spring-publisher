package cleverton.heusner.exception.resource;

public class ExistingResourceException extends RuntimeException {

    public ExistingResourceException(String message) {
        super(message);
    }
}