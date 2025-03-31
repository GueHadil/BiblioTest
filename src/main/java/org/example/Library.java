package org.example;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new NullPointerException("Book cannot be null");
        }
        if (!books.contains(book)) {
            books.add(book);
        }
    }

    public void removeBook(String isbn) {
        if (isbn == null) {
            throw new NullPointerException("ISBN cannot be null");
        }
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) {
                books.remove(i);
            }
        }
    }

    public Book searchBook(String isbn) {
        if (isbn == null) {
            throw new NullPointerException("ISBN cannot be null");
        }
        Book book = null;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) {
                book = books.get(i);
            }
        }
        return book;
    }

    public List<Book> getBooks() {
        return books;
    }
}