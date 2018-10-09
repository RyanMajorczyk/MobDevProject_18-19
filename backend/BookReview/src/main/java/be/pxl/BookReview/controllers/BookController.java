package be.pxl.bookreview.controllers;

import be.pxl.bookreview.models.Book;
import be.pxl.bookreview.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("bookController")
public class BookController implements IBookController {

    private IBookRepository bookRepository;

    @Autowired
    public BookController(@Qualifier("bookRepository") IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(int id) {
        return Optional.empty();
    }

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book editBook(Book book) {
        return bookRepository.save(book);
    }
}
