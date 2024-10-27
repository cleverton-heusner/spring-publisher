package cleverton.heusner.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import static java.time.LocalDate.now;

public class Author {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private Book book;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public boolean hasBook() {
        return book != null;
    }

    public boolean isBirthDateInFuture() {
        return birthDate.isAfter(LocalDate.now());
    }

    public boolean isOfLegalAge(final int ageOfMajority) {
        return Period.between(birthDate, now()).getYears() >= ageOfMajority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}