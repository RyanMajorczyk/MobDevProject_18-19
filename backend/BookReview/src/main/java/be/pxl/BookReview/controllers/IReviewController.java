package be.pxl.BookReview.controllers;

import be.pxl.BookReview.models.Book;
import be.pxl.BookReview.models.Review;

import java.util.List;

public interface IReviewController {
    Review createReview(Review review);
    Review getReview(int id);
    Review editReview(Review review);
    List getAllReviews();

}
