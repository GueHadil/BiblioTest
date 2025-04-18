package org.example;

import java.util.List;

public class LibService {
    LibRepository library;
    public LibService() {
        this.library = new LibRepository();
    }

    public void registerBook(Book book) {
        try {
            library.addBook(book);
            System.out.println("Book added successfully: " + book.getTitle());
        } catch (NullPointerException e) {
            System.err.println("Error while adding book: " + e.getMessage());
        }
    }

    public void deleteBookByIsbn(String isbn) {
        try {
            library.removeBook(isbn);
            System.out.println("Book removed successfully (ISBN: " + isbn + ")");
        } catch (NullPointerException e) {
            System.err.println("Error while removing book: " + e.getMessage());
        }
    }

    public Book findBookByIsbn(String isbn) {
        try {
            Book book = library.searchBook(isbn);
            if (book != null) {
                System.out.println("Book found: " + book.getTitle());
            } else {
                System.out.println("No book found with ISBN: " + isbn);
            }
            return book;
        } catch (NullPointerException e) {
            System.err.println("Error while searching for book: " + e.getMessage());
            return null;
        }
    }
    public Book findBookByAuthor(String author) {
        try {
            Book book = library.searchBook(author);
            if (book != null) {
                System.out.println("Book found: " + book.getTitle());
            } else {
                System.out.println("No book found with Author: " + author);
            }
            return book;
        } catch (NullPointerException e) {
            System.err.println("Error while searching for book: " + e.getMessage());
            return null;
        }
    }

    public List<Book> listAllBooks() {
        return library.getBooks();
    }

}
