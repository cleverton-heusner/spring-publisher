package cleverton.heusner.adapter.output.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static cleverton.heusner.adapter.input.constant.validation.BookValidationErrorMessage.BOOK_ISBN_SIZE;
import static cleverton.heusner.adapter.input.constant.validation.BookValidationErrorMessage.BOOK_TITLE_MAX_SIZE;

@Entity
@Table(name = "book")
@EqualsAndHashCode(callSuper = true)
@Data
public class BookEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = BOOK_ISBN_SIZE)
    private String isbn;

    @Column(nullable = false, length = BOOK_TITLE_MAX_SIZE)
    private String title;

    @OneToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity authorEntity;

    @PrePersist
    @PreUpdate
    public void stripTitle() {
        setTitle(title.strip());
    }
}