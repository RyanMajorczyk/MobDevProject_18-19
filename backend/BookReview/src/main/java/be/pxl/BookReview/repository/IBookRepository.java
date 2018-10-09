package be.pxl.BookReview.repository;

import be.pxl.BookReview.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("bookRepository")
public interface IBookRepository extends JpaRepository<Book, Long> {

}