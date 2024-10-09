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

import fi.haagahelia.bookstore.domain.AppUser;
import fi.haagahelia.bookstore.domain.AppUserRepository;
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

    @Autowired
    private AppUserRepository urep;

    // test search book
    @Test
    public void findByTitleReturnsBook() {
        List<Book> books = rep.findByTitle("Moby Dick");

        assertThat(books).hasSize(1);
        assertThat(books.get(0).getYears()).isEqualTo(1851);
    }

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

    // test search category
    @Test
    public void findByNameReturnsCategory() {
        List<Category> categories = crep.findByName("Horror");

        assertThat(categories).hasSize(1);
    }

    // test add category
    @Test
    public void createNewCategory() {
        Category category = new Category("18+");
        crep.save(category);
        assertThat(category.getCategoryId()).isNotNull();
    }

    // test delete category
    @Test
    public void deleteCategory() {
        Category category = new Category("18+");
        crep.save(category);
        List<Category> categories = crep.findByName("18+");
        Category category2 = categories.get(0);
        crep.delete(category2);
        List<Category> newCategories = crep.findByName("18+");
        assertThat(newCategories).hasSize(0);
    }

    // test search user
    @Test
    public void findByUsernameReturnsUser() {
        AppUser user = urep.findByUsername("ledo");
        assertThat(user).isNotNull();
    }

    // test add user
    @Test
    public void createNewUser() {
        // alecto / alecto
        AppUser user = new AppUser("alecto", "$2a$12$ekKPNTACNTjQ2PlKJ7Ll3.CxJWcWt7R6kJXCWzoCkaM79x8zgLvWS", "USER");
        urep.save(user);
        AppUser testUser = urep.findByUsername("alecto");
        assertThat(testUser).isNotNull();
    }

    // test delete user
    @Test
    public void deleteUser() {
        AppUser user = urep.findByUsername("ledo");
        urep.delete(user);
        AppUser testUser = urep.findByUsername("ledo");
        assertThat(testUser).isNull();
    }

}
