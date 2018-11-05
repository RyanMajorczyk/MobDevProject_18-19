package be.pxl.bookreview.controllers;

import be.pxl.bookreview.models.Book;
import be.pxl.bookreview.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return bookRepository.findAll().stream().limit(10).collect(Collectors.toList());
    }

    @GetMapping("/name/{name}")
    public List<Book> getBooksByName(@PathVariable("name") String name) {
        return bookRepository.findByTitleContaining(name);
    }

    @Override
    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable("id") int id) {
        return bookRepository.findById(id);
    }

    @Override
    @PutMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @Override
    @PostMapping
    public Book editBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PostMapping("/addCover/{id}")
    public Book addCoverWithBookId (@RequestBody byte[] image, @PathVariable("id") int id) {
        Book bookToChange = bookRepository.getOne(id);
        bookToChange.setFront_cover(image);
        return bookRepository.save(bookToChange);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") int id) {
        bookRepository.deleteById(id);
    }
}
