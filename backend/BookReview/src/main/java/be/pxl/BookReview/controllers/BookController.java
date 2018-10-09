package be.pxl.BookReview.controllers;

import be.pxl.BookReview.models.Book;
import be.pxl.BookReview.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookController implements  IBookController{


    private IBookRepository bookRepository;

    @Autowired
    public BookController(@Qualifier("bookRepository") IBookRepository iBookRepository) {
        this.bookRepository = iBookRepository;
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBook(int id) {
        return bookRepository.getOne((long)id);
    }

    @Override
    public Book editBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List getAllBooks() {
        return bookRepository.findAll();
    }

}
