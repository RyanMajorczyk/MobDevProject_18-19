package be.pxl.BookReview.controllers;

import be.pxl.BookReview.models.Book;

import java.util.List;

public interface IBookController {
    Book createBook(Book book);
    Book getBook(int id);
    Book editBook(Book book);
    List getAllBooks();

}
