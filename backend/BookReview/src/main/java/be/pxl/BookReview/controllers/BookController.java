package be.pxl.bookreview.controllers;

import be.pxl.bookreview.models.Book;
import be.pxl.bookreview.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/book")
public class BookController implements IBookController {

    private IBookRepository bookRepository;

    @Autowired
    public BookController(@Qualifier("bookRepository") IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable("id") int id) {
        return bookRepository.findById(id);
    }

    @Override
    @PutMapping
    public void addBook(@RequestBody Book book) {
        bookRepository.save(book);
    }

    @Override
    @PostMapping
    public Book editBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }
}
