package com.example.bookshop.controller;

import com.example.bookshop.dao.BookDao;
import com.example.bookshop.entity.BookId;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.CartService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    private final CartService cartService;

    //book/list_book
    @GetMapping("/list-books")
    public String listBooks(Model model){
        model.addAttribute("books",
                bookService.listBooks());
        return "listbooks";
    }

    @ModelAttribute("cartSize")
    public Integer  cartSize() {
        return cartService.cartSize();
    }

    //book/book-details?i=1&isbn=ISBN-1234
    @GetMapping("/book-details")
    public String bookDetails(@RequestParam("id") int id,
                              @RequestParam("isbn") String isbn, Model model) {

        BookId bookId = null;
        if(Objects.nonNull(id) && Objects.nonNull(isbn)) {
            bookId = new BookId();
            bookId.setId(id);
            bookId.setIsbn(isbn);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        model.addAttribute("book",
        bookService.findBookById(bookId));
        return "bookdetails";
    }

}