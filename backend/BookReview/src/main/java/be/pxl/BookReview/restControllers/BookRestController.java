package be.pxl.bookreview.restControllers;

import be.pxl.bookreview.controllers.BookController;
import be.pxl.bookreview.models.Book;
import be.pxl.bookreview.models.Review;
import be.pxl.bookreview.repository.IBookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
class BookRestController {

    private IBookRepository iBookRepository;
    private BookController bookController = new BookController(iBookRepository);

    @GetMapping("/getAll")
    public List getAllBooks() {
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review(2,"Good Book", "HarryPotter"));
        reviewList.add(new Review(3,"Super Book", "Lord Of The Rings"));
        reviewList.add(new Review(1,"Bad Book", "Java for dummys"));
        Book book = new Book("Star Wars", "BE548522","Frank","Book about a dark man", reviewList);

        //bookController.createBook(book);
        return  null;//bookController.getAllBooks();
    }
}
