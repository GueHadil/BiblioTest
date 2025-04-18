package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LibServiceTest {

    private LibRepository mockRepo;
    private LibService service;

    @BeforeEach
    void setUp() {
        // Création d’un mock pour le repository et injection dans le service
        mockRepo = mock(LibRepository.class);
        service = new LibService() {
            {
                this.library = mockRepo;
            }
        };
    }

    // -------------------------------------------------------------------
    // 🔹 TESTS POUR L’AJOUT DE LIVRE (registerBook)
    // -------------------------------------------------------------------

    @Test
    void testRegisterBook_Success() {

        Book book = new Book("Effective Java", "Joshua Bloch", "123");
        doNothing().when(mockRepo).addBook(book);

        service.registerBook(book);

        verify(mockRepo).addBook(book);
    }

    @Test
    void testRegisterBook_NullPointer() {

        Book book = new Book("Clean Code", "Robert C. Martin", "456");
        doThrow(new NullPointerException("Book is null")).when(mockRepo).addBook(book);

        service.registerBook(book);

        verify(mockRepo).addBook(book);
    }

    // -------------------------------------------------------------------
    // 🔹 TESTS POUR LA SUPPRESSION DE LIVRE PAR ISBN (deleteBookByIsbn)
    // -------------------------------------------------------------------

    @Test
    void testDeleteBookByIsbn_Success() {

        doNothing().when(mockRepo).removeBook("123");

        service.deleteBookByIsbn("123");

        verify(mockRepo).removeBook("123");
    }

    @Test
    void testDeleteBookByIsbn_NullPointer() {

        doThrow(new NullPointerException("Book not found")).when(mockRepo).removeBook("123");

        service.deleteBookByIsbn("123");

        verify(mockRepo).removeBook("123");
    }

    // -------------------------------------------------------------------
    // 🔹 TESTS POUR LA RECHERCHE PAR ISBN (findBookByIsbn)
    // -------------------------------------------------------------------

    @Test
    void testFindBookByIsbn_Found() {

        Book book = new Book("Head First Java", "Kathy Sierra", "789");
        when(mockRepo.searchBook("789")).thenReturn(book);

        Book result = service.findBookByIsbn("789");

        assertNotNull(result);
        assertEquals("Head First Java", result.getTitle());
    }

    @Test
    void testFindBookByIsbn_NotFound() {

        when(mockRepo.searchBook("000")).thenReturn(null);

        Book result = service.findBookByIsbn("000");

        assertNull(result);
    }

    @Test
    void testFindBookByIsbn_NullPointer() {

        when(mockRepo.searchBook("000")).thenThrow(new NullPointerException("Search error"));

        Book result = service.findBookByIsbn("000");

        assertNull(result);
    }

    // -------------------------------------------------------------------
    // 🔹 TESTS POUR LA RECHERCHE PAR AUTEUR (findBookByAuthor)
    // -------------------------------------------------------------------

    @Test
    void testFindBookByAuthor_Found() {

        Book book = new Book("Java Puzzlers", "Joshua Bloch", "321");
        when(mockRepo.searchBook("Joshua Bloch")).thenReturn(book);

        Book result = service.findBookByAuthor("Joshua Bloch");

        assertNotNull(result);
    }

    @Test
    void testFindBookByAuthor_NotFound() {

        when(mockRepo.searchBook("Unknown Author")).thenReturn(null);

        Book result = service.findBookByAuthor("Unknown Author");

        assertNull(result);
    }

    @Test
    void testFindBookByAuthor_NullPointer() {

        when(mockRepo.searchBook("Joshua Bloch")).thenThrow(new NullPointerException("Error"));

        Book result = service.findBookByAuthor("Joshua Bloch");

        assertNull(result);
    }

    // -------------------------------------------------------------------
    // 🔹 TESTS POUR L’AFFICHAGE DE TOUS LES LIVRES (listAllBooks)
    // -------------------------------------------------------------------

    @Test
    void testListAllBooks_NonEmpty() {
        List<Book> books = Arrays.asList(
                new Book("Book A", "Author A", "1"),
                new Book("Book B", "Author B", "2")
        );
        when(mockRepo.getBooks()).thenReturn(books);

        List<Book> result = service.listAllBooks();

        assertEquals(2, result.size());
    }

    @Test
    void testListAllBooks_Empty() {
        when(mockRepo.getBooks()).thenReturn(Collections.emptyList());

        List<Book> result = service.listAllBooks();

        assertTrue(result.isEmpty());
    }
}
