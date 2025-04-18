package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        LibService libService = new LibService();

        Book book1 = new Book("12345", "Java Programming", "John Doe");
        Book book2 = new Book("67890", "Advanced Java", "Jane Smith");

        libService.registerBook(book1);
        libService.registerBook(book2);

        System.out.println("\nList of all books:");
        List<Book> books = libService.listAllBooks();
        for (Book book : books) {
            System.out.println(book.getTitle());
        }

        System.out.println("\nSearching for book with ISBN 12345:");
        libService.findBookByIsbn("12345");


        System.out.println("\nSearching for book by author 'Jane Smith':");
        libService.findBookByAuthor("Jane Smith");


        System.out.println("\nDeleting book with ISBN 12345:");
        libService.deleteBookByIsbn("12345");


        System.out.println("\nList of all books after deletion:");
        books = libService.listAllBooks();
        for (Book book : books) {
            System.out.println(book.getTitle());
        }
    }
}
