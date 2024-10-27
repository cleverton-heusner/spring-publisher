package cleverton.heusner.port.shared;

public interface MessageComponent {
    String getMessage(final String key, final Object... args);
}
