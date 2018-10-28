package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {
    @JsonProperty("reviewId")
    private Long reviewId;

    @JsonProperty("score")
    private double score;

    @JsonProperty("reviewText")
    private String reviewText;

    @JsonProperty("name")
    private String name;

    public Review(double score, String reviewText, String name) {
        this.score = score;
        this.reviewText = reviewText;
        this.name = name;
    }

    public Review() {
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", score=" + score +
                ", reviewText='" + reviewText + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
