package com.example.librarymanagev2api.classes;

import org.springframework.web.client.RestTemplate;


public class BookRegister {
    private Book bookReg;

    private final RestTemplate restTemplate = new RestTemplate();

    public BookRegister(String isbn) {
        String url = "https://brasilapi.com.br/api/isbn/v1/" + isbn;
        bookReg = restTemplate.getForObject(url, Book.class);
    }

    public Book getBookReg() {
        return bookReg;
    }

}
