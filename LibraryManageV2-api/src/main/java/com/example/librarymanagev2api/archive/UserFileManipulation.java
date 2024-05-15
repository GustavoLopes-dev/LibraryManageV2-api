package com.example.librarymanagev2api.archive;

import com.example.librarymanagev2api.classes.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserFileManipulation implements ArchiveManipulation{
    private static final String DEFAULT_PATH = "C:\\Users\\augus\\IdeaProjects\\LibraryManageV2-api\\src\\documents\\users.txt";
    private String path;
    private User user;

    public UserFileManipulation(User user) {
        this.path = DEFAULT_PATH;
        this.user = user;
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
            buffWrite.append("nome: ").append(user.getUser_name())
                     .append(", CPF: ").append(user.getUser_cpf())
                     .append(", Email: ").append(user.getUser_email())
                     .append(";\n");
        }
    }

    @Override
    public void delete() throws IOException {
        //sla
    }
}
