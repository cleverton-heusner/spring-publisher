package cleverton.heusner.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.time.LocalDate;

import static cleverton.heusner.constant.message.validation.AuthorMessageValidation.AUTHOR_NAME_MAX_SIZE;

@Entity
@Table(name = "author")
@EqualsAndHashCode(callSuper = true)
@Data
public class Author extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, length = AUTHOR_NAME_MAX_SIZE, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    @OneToOne(mappedBy = "author")
    private Book book;

    public boolean hasBook() {
        return book != null;
    }

    @PrePersist
    @PreUpdate
    public void stripName() {
        setName(name.strip());
    }
}