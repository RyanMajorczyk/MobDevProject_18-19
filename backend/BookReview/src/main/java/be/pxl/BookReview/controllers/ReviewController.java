package be.pxl.bookreview.controllers;

import be.pxl.bookreview.models.Review;
import be.pxl.bookreview.repository.IReviewRepository;
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
    public Review createReview(Review person) {
        return reviewRepository.save(person);
    }

    @Override
    public Review getReview(int id) {
        return reviewRepository.getOne(id);
    }

    @Override
    public Review editReview(Review person) {
        return reviewRepository.save(person);
    }

    @Override
    public List getAllReviews() {
        return reviewRepository.findAll();
    }
}
