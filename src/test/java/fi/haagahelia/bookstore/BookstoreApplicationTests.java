package fi.haagahelia.bookstore;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fi.haagahelia.bookstore.web.BookController;

@SpringBootTest
public class BookstoreApplicationTests {
	// Smoke testing: testing the major functions of software before carrying out
	// the formal testing
	// ensures that Spring Boot application context is properly loading and that the
	// BookController bean is being correctly initialized and injected

	@Autowired
	private BookController controller;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}
