package be.pxl.bookreview.repository;

import be.pxl.bookreview.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bookRepository")
public interface IBookRepository extends JpaRepository<Book, Integer> {


    List<Book> findByTitleContaining(String name);
}
