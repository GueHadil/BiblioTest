package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @Test
    void testAdd() {
        Library lib = new Library();
        Book book = new Book("Java premier langage", "Anne Tasso", "123456789");
        System.out.println("Nombre de livres avant ajout : " + lib.getBooks().size());
        System.out.println("Ajout du livre : " + book.getTitle());
        lib.addBook(book);
        System.out.println("Nombre de livres après ajout : " + lib.getBooks().size());
        assertEquals(1, lib.getBooks().size());
        assertTrue(lib.getBooks().contains(book));
    }

    @Test
    void testRemove() {
        Library lib = new Library();
        Book book = new Book("Java premier langage", "Anne Tasso", "123456789");

        lib.addBook(book);
        System.out.println("nb livres avant suppression : " + lib.getBooks().size());

        lib.removeBook("123456789");

        System.out.println("nb livres apres suppression : " + lib.getBooks().size());
        assertEquals(0, lib.getBooks().size());
    }

    @Test
    void testSearch() {
        Library lib = new Library();
        Book book = new Book("Java premier langage", "Anne Tasso", "123456789");

        lib.addBook(book);
        // Rechercher un livre existant
        Book result = lib.searchBook("123456789");
        System.out.println("Recherche du livre avec ISBN 123456789...");
        assertNotNull(result);
        System.out.println("Livre trouvé : " + result.getTitle());

        // Rechercher un livre inexistant
        Book result2 = lib.searchBook("123");
        System.out.println("Recherche du livre avec ISBN 123...");
        assertNull(result2);
        System.out.println("book not found ");
    }
}
