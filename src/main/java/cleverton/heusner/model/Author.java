package cleverton.heusner.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Entity
@Table(name = "author")
@EqualsAndHashCode(callSuper = true)
@Data
public class Author extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String name;

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