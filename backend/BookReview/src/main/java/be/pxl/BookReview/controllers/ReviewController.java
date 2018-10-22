package be.pxl.bookreview.controllers;

import be.pxl.bookreview.models.Book;
import be.pxl.bookreview.models.Review;
import be.pxl.bookreview.repository.IBookRepository;
import be.pxl.bookreview.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/review")
public class ReviewController implements IReviewController {

    private IReviewRepository reviewRepository;
    private IBookRepository bookRepository;

    @Autowired
    public ReviewController(@Qualifier("reviewRepository") IReviewRepository reviewRepository, @Qualifier("bookRepository") IBookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    @PutMapping
    public Book createReview(@RequestBody Review review) {

       return null;
    }

    @Override
    public Review getReview(int id) {
        return reviewRepository.getOne(id);
    }

    @Override
    @PostMapping
    public Review editReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }

    @Override
    @GetMapping
    public List getAllReviews() {
        return reviewRepository.findAll();
    }
}
