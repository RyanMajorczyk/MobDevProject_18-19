package be.pxl.BookReview.restControllers;

import be.pxl.BookReview.controllers.BookController;
import be.pxl.BookReview.models.Book;
import be.pxl.BookReview.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
class BookRestController {

    @Autowired
    private BookController bookController;

    @GetMapping("/getAll")
    public Book getAllBooks() {
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review(2,"Good Book", "HarryPotter"));
        reviewList.add(new Review(3,"Super Book", "Lord Of The Rings"));
        reviewList.add(new Review(1,"Bad Book", "Java for dummys"));
        Book book = new Book("Star Wars", "BE548522","Frank","Book about a dark man", reviewList);

        //bookController.createBook(book);
        return book; //bookController.getAllBooks();
    }
}
