package fi.haagahelia.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;

//@DataJpaTest
//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookstoreApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository rep;

    @Autowired
    private CategoryRepository crep;

    // test add book
    @Test
    public void createNewBook() {
        Category category = new Category("18+");
        crep.save(category);
        Book book = new Book("Kama Sutra", "Ancient Warlock", 199, "367810367810", category);
        rep.save(book);
        assertThat(book.getId()).isNotNull();
    }

    // test delete book
    @Test
    public void deleteBook() {
        List<Book> books = rep.findByTitle("Moby Dick");
        Book book = books.get(0);
        rep.delete(book);
        List<Book> newBooks = rep.findByTitle("Moby Dick");
        assertThat(newBooks).hasSize(0);
    }

    // test edit book
    @Test
    public void editBook() {
        // get exist book
        Book existingBook = rep.findByTitle("Moby Dick").get(0);
        // make some changes
        existingBook.setTitle("Son of Moby Dick");
        existingBook.setYears(1951);
        // save changes
        rep.save(existingBook);
        // get that book
        Book updatedBook = rep.findById(existingBook.getId()).orElse(null);
        // test the changes
        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook.getTitle()).isEqualTo("Son of Moby Dick");
        assertThat(updatedBook.getYears()).isEqualTo(1951);
    }

}
