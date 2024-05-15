package com.example.librarymanagev2api.archive;
import com.example.librarymanagev2api.classes.Book;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BookFileManipulation implements ArchiveManipulation {
    private static final String DEFAULT_PATH = "C:\\Users\\augus\\IdeaProjects\\LibraryManageV2-api\\src\\documents\\books.txt";
    private String path;
    private Book book;

    public BookFileManipulation(Book book) {
        this.path = DEFAULT_PATH;
        this.book = book;
    }

    @Override
    public void reader() throws IOException {
        try (BufferedReader buffRead = new BufferedReader(new FileReader(path))) {
            String linha;
            while ((linha = buffRead.readLine()) != null) {
                System.out.println(linha);
            }
        }
    }

    @Override
    public void writer() throws IOException {
        try (BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path, true))) {
            // Escreve o nome e o ISBN do livro no arquivo em uma nova linha
            buffWrite.append("title: ").append(book.getTitle()).append(", ISBN: ").append(book.getIsbn()).append(";\n");
        }
    }

    @Override
    public void delete() throws IOException {
        //pendente
    }
}