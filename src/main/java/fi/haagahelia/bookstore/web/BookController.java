package fi.haagahelia.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// import org.springframework.ui.Model;

// import fi.haagahelia.bookstore.domain.Book;

@Controller
@ResponseBody
public class BookController {

    @GetMapping("/index")
    public String showindex() {
        return "This is the index page of the Bookstore";
    }

}
