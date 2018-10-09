package be.pxl.BookReview.repository;

import be.pxl.BookReview.models.Book;
import be.pxl.BookReview.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("reviewRepository")
public interface IReviewRepository extends JpaRepository<Review, Long> {

}