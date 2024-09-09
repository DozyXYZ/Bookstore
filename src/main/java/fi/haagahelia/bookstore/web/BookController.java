package fi.haagahelia.bookstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;

@Controller
public class BookController {
    // when Spring creates an instance of BookService, it automatically injects an
    // instance of BookRepository into repository field
    @Autowired
    private BookRepository repository;

    // get all the entries from the repository and display it with booklist.html
    @GetMapping({ "/booklist", "/" })
    public String listBook(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }

    // maps this method to /add URL, so it will be triggered when user sends a GET
    // request to /add
    @GetMapping("/add")
    public String addBook(Model model) {
        // add a new Book object to the model with the key book
        // allow the form in the addbook.html to bind to this object and send back data
        // for a new entry
        model.addAttribute("book", new Book());
        return "addbook"; // addbook.html will be rendered to the user when they visit /add
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
        Book book = repository.findById(bookId).orElse(null);
        if (book != null) {
            model.addAttribute("book", book);
            return "editbook";
        } else {
            return "redirect../booklist";
        }
    }

    // post the edited information to the database
    @PostMapping("/update/{id}")
    public String updateBook(Book book) {
        repository.save(book);
        return "redirect:/booklist";
    }
}
