package cleverton.heusner.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import static cleverton.heusner.constant.message.validation.BookMessageValidation.BOOK_ISBN_SIZE;
import static cleverton.heusner.constant.message.validation.BookMessageValidation.BOOK_TITLE_MAX_SIZE;

@Entity
@Table(name = "book")
@EqualsAndHashCode(callSuper = true)
@Data
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true, nullable = false, length = BOOK_ISBN_SIZE)
    private String isbn;

    @Column(nullable = false, length = BOOK_TITLE_MAX_SIZE)
    private String title;

    @OneToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @PrePersist
    @PreUpdate
    public void stripTitle() {
        setTitle(title.strip());
    }
}