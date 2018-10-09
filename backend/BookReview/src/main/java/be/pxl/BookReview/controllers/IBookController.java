package be.pxl.bookreview.controllers;

import be.pxl.bookreview.models.Book;

import java.util.List;
import java.util.Optional;


public interface IBookController {

    List<Book> getAllBooks();
    Optional<Book> getBookById(int id);
    void addBook(Book book);
    Book editBook(Book book);
}
