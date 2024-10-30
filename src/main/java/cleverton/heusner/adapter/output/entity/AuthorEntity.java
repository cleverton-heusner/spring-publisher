package cleverton.heusner.adapter.output.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

import static cleverton.heusner.adapter.input.constant.validation.AuthorValidationErrorMessage.AUTHOR_NAME_MAX_SIZE;

@Entity
@Table(name = "author")
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = AUTHOR_NAME_MAX_SIZE, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    @OneToOne(mappedBy = "authorEntity")
    private BookEntity book;

    public boolean hasBook() {
        return book != null;
    }

    @PrePersist
    @PreUpdate
    public void stripName() {
        setName(name.strip());
    }
}