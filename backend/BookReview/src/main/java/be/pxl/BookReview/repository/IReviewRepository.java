package be.pxl.bookreview.repository;

import be.pxl.bookreview.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("reviewRepository")
public interface IReviewRepository extends JpaRepository<Review, Integer> {

}
