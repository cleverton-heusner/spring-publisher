package cleverton.heusner.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Entity
@Table(name = "book")
@EqualsAndHashCode(callSuper = true)
@Data
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true, nullable = false, length = 13)
    private String isbn;

    @Column(nullable = false, length = 30)
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