package fi.haagahelia.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;

import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;

import fi.haagahelia.bookstore.domain.AppUser;
import fi.haagahelia.bookstore.domain.AppUserRepository;

@SpringBootApplication
public class BookstoreApplication {

	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookDemo(BookRepository repository, CategoryRepository crepository,
			AppUserRepository urepository) {
		return (args) -> {
			log.info("save some books");

			Category c1 = new Category("Horror");
			Category c2 = new Category("Sci-Fi");
			Category c3 = new Category("Mystery");

			crepository.save(c1);
			crepository.save(c2);
			crepository.save(c3);

			repository.save(new Book("Moby Dick", "Herman Melville", 1851, "54796213578", c1));
			repository.save(new Book("White Fang", "Jack London", 1906, "12359756311", c2));
			repository.save(new Book("Crime and Punishment", "Fyodor Dostoevsky", 1866, "964721357", c3));

			// Create users: admin/admin user/user
			AppUser user1 = new AppUser("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			AppUser user2 = new AppUser("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C",
					"ADMIN");
			// pass: 123
			AppUser user3 = new AppUser("ledo", "$2a$12$58wtf3KYthqONyy2DDXzaeZ9rRB4heLmNVPco/KFDCU5fGm1voE2C", "USER");
			urepository.save(user1);
			urepository.save(user2);
			urepository.save(user3);

			log.info("fetch all books");
			for (Book book : repository.findAll()) {
				log.info(book.toString());
			}

			log.info("find books in year 1906");
			for (Book book : repository.findByYears(1906)) {
				log.info(book.toString());
			}

			log.info("fetch all categories");
			for (Category cat : crepository.findAll()) {
				log.info(cat.toString());
			}

		};
	}

}
