package cleverton.heusner.validation.isbn;

import cleverton.heusner.validation.Validator;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static cleverton.heusner.constant.message.validation.BookMessageValidation.INVALID_BOOK_ISBN;
import static cleverton.heusner.constant.message.validation.BookMessageValidation.NOT_BLANK_BOOK_ISBN;

@Component
public class Isbn13Validator extends Validator implements ConstraintValidator<Isbn13, String> {

    private static final Pattern ISBN13_PATTERN = Pattern.compile("^(978|979)\\d{10}$");
    private static final int CHECKSUM_LENGTH = 12;
    private static final int CHECKSUM_BASE = 10;
    private static final int EVEN_POSITION_MULTIPLIER = 3;

    @Override
    public boolean isValid(final String isbn, final ConstraintValidatorContext context) {
        this.context = context;

        if (StringUtils.isBlank(isbn)) {
            return createContext(NOT_BLANK_BOOK_ISBN);
        } else if (!isPatternValid(isbn) || !isChecksumValid(isbn)) {
            return createContext(INVALID_BOOK_ISBN, isbn);
        }

        return true;
    }

    private boolean isPatternValid(final String isbn) {
        return ISBN13_PATTERN.matcher(isbn).matches();
    }

    private boolean isChecksumValid(final String isbn) {
        int weightedSumOfDigits = IntStream.range(0, CHECKSUM_LENGTH)
                .map(i -> {
                    int digit = Character.getNumericValue(isbn.charAt(i));
                    return (i % 2 == 0) ? digit : digit * EVEN_POSITION_MULTIPLIER;
                })
                .sum();

        int checksum = (CHECKSUM_BASE - (weightedSumOfDigits % CHECKSUM_BASE)) % CHECKSUM_BASE;
        int lastDigit = Character.getNumericValue(isbn.charAt(CHECKSUM_LENGTH));

        return lastDigit == checksum;
    }
}