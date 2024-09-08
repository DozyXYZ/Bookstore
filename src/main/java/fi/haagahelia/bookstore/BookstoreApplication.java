package fi.haagahelia.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookDemo(BookRepository repository) {
		return (args) -> {
			log.info("save some books");
			repository.save(new Book("Moby Dick", "Herman Melville", 1851, "54796213578"));
			repository.save(new Book("White Fang", "Jack London", 1906, "12359756311"));
			repository.save(new Book("Crime and Punishment", "Fyodor Dostoevsky", 1866, "964721357"));

			log.info("fetch all books");
			for (Book book : repository.findAll()) {
				log.info(book.toString());
			}

			log.info("find books in year 1906");
			for (Book book : repository.findByYears(1906)) {
				log.info(book.toString());
			}
		};
	}

}
