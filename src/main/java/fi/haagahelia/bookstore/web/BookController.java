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

    @GetMapping("/booklist")
    public String listBook(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist"; // connect to booklist.html
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "addBook"; // connect to addbook.html
    }

    @PostMapping("/save")
    public String save(Book book) {
        System.out.println("Saved"); // testing
        repository.save(book);
        return "redirect:booklist"; // connect to booklist.html
    }

    // PathVariable get access to data when given id
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
        repository.deleteById(bookId);
        return "redirect:../booklist";
    }

}
