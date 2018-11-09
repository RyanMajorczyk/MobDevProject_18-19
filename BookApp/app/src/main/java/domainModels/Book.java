package domainModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Book {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("auteur")
    private String auteur;

    @JsonProperty("description")
    private String description;

    @JsonProperty("reviews")
    private List<Review> reviews;

    @JsonProperty("front_cover")
    private byte[] front_cover;

    public Book() {}

    public Book(String title, String isbn, String auteur, String description) {
        this.title = title;
        this.isbn = isbn;
        this.auteur = auteur;
        this.description = description;
    }
    public Book(Long id, String title, String isbn, String auteur, String description) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.auteur = auteur;
        this.description = description;
    }


    public byte[] getFront_cover() {
        return front_cover;
    }

    public void setFront_cover(byte[] front_cover) {
        this.front_cover = front_cover;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getDescription() {
        return description;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", auteur='" + auteur + '\'' +
                ", description='" + description + '\'' +
                ", reviews=" + reviews +
                '}';
    }
}
