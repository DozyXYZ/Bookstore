package fi.haagahelia.bookstore.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.domain.CategoryRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookController {
    // when Spring creates an instance of BookService, it automatically injects an
    // instance of BookRepository into repository field
    @Autowired
    private BookRepository repository;

    @Autowired
    private CategoryRepository crepository;

    // get all the entries from the repository and display it with booklist.html
    @GetMapping({ "/booklist", "/" })
    public String listBook(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }

    /*
     * Spring Boot data rest will automatically create these links that return json
     * format
     * requirements: add path change to application.properties:
     * spring.data.rest.base-path=/api
     * add dependency to pom.xml
     * <dependency>
     * <groupId>org.springframework.boot</groupId>
     * <artifactId>spring-boot-starter-data-rest</artifactId>
     * </dependency>
     */

    // RESTful service to get all books
    @GetMapping("/books")
    public @ResponseBody List<Book> bookListRest() {
        return (List<Book>) repository.findAll();
    }

    // RESTful service to retrieve 1 book by id
    @GetMapping("/book/{id}")
    public @ResponseBody Optional<Book> findStudentRest(@PathVariable("id") Long bookId) {
        return repository.findById(bookId);
    }

    // maps this method to /add URL, so it will be triggered when user sends a GET
    // request to /add
    @GetMapping("/add")
    public String addBook(Model model) {
        // add a new Book object to the model with the key book
        // allow the form in the addbook.html to bind to this object and send back data
        // for a new entry
        model.addAttribute("book", new Book());
        // get the all category and display it on the dropdown
        model.addAttribute("categories", crepository.findAll());
        // addbook.html will be rendered to the user when they visit /add
        return "addbook";
    }

    @PostMapping("/save")
    public String save(Book book) {
        System.out.println("Saved"); // testing
        // save the Book object to the database using database operations
        repository.save(book);
        return "redirect:/booklist"; // after saving the book, redirects the user to the /booklist
    }

    // {id} is a placeholder for the book's ID that will be passed dynamically in
    // the URL
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
        // this method takes in the id as Long parameter
        // PathVariable annotation tells Spring to capture the value from the URL and
        // bind it to the bookID parameter
        repository.deleteById(bookId);
        return "redirect:../booklist";
    }

    // given id, get the information of that id and add to the editbook form
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long bookId, Model model) {
        model.addAttribute("book", repository.findById(bookId));
        model.addAttribute("categories", crepository.findAll());
        return "editbook";
    }

    // for edit and update: https://www.baeldung.com/spring-boot-crud-thymeleaf
}
