package be.pxl.bookreview.controllers;

import be.pxl.bookreview.models.Book;
import be.pxl.bookreview.models.Review;

import java.util.List;

public interface IReviewController {
    Book createReview(Review person);
    Review getReview(int id);
    Review editReview(Review person);
    List getAllReviews();

}
