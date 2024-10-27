package cleverton.heusner.adapter.input.constant.validation;

public class BookValidationErrorMessage {

    public static final byte BOOK_ISBN_SIZE = 13;
    public static final byte BOOK_TITLE_MIN_SIZE = 1;
    public static final byte BOOK_TITLE_MAX_SIZE = 30;

    public static final String NOT_BLANK_BOOK_ISBN = "NotBlank.book.isbn";
    public static final String INVALID_BOOK_ISBN = "Invalid.book.isbn";
    public static final String SIZE_BOOK_TITLE = "${Size.book.title}";
}
