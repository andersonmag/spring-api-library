package com.api.library.controller;

import java.util.List;
import java.util.Optional;

import com.api.library.model.Book;
import com.api.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = bookRepository.findAll();

        if (books.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getOne(@PathVariable("id") Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (!bookOptional.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
    }

}