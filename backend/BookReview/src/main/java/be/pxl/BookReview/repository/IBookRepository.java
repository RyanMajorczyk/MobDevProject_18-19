package be.pxl.bookreview.repository;

import be.pxl.bookreview.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("bookRepository")
public interface IBookRepository extends JpaRepository<Book, Integer> {
}
