package fi.haagahelia.bookstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

// import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;

@Controller
public class BookController {
    // when Spring creates an instance of BookService, it automatically injects an
    // instance of BookRepository into repository field
    @Autowired
    private BookRepository repository;

    @GetMapping("/booklist")
    public String listBook(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }

}
