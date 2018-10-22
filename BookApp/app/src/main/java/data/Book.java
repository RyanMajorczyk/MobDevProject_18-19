package data;

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
