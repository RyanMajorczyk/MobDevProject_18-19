package be.pxl.BookReview.controllers;

import be.pxl.BookReview.models.Review;
import be.pxl.BookReview.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("reviewController")
public class ReviewController implements IReviewController {

    private IReviewRepository reviewRepository;

    @Autowired
    public ReviewController(@Qualifier("reviewRepository") IReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review getReview(int id) {
        return reviewRepository.getOne((long) id);
    }

    @Override
    public Review editReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List getAllReviews() {
        return reviewRepository.findAll();
    }
}
