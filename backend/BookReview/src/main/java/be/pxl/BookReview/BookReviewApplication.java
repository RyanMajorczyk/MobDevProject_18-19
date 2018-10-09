package be.pxl.bookreview;

import be.pxl.bookreview.controllers.BookController;
import be.pxl.bookreview.repository.IBookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookReviewApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BookReviewApplication.class, args);
		IBookRepository repo = context.getBean("bookRepository", IBookRepository.class);
		BookController controller = new BookController(repo);

	}
}
