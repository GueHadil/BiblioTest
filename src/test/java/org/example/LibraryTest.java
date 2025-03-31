package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    Library library;
    Book book;

    @BeforeEach
    void init() {
        library = new Library();
        book = new Book("Java premier langage", "Anne Tasso", "123456789");
    }


    @Test
    void testAdd() {
        System.out.println("Nombre de livres avant ajout : " + library.getBooks().size());
        System.out.println("Ajout du livre : " + book.getTitle());
        library.addBook(book);
        System.out.println("Nombre de livres après ajout : " + library.getBooks().size());
        assertEquals(1, library.getBooks().size());
        assertTrue(library.getBooks().contains(book));
    }

    @Test
    void testRemove() {
        library.addBook(book);
        System.out.println("nb livres avant suppression : " + library.getBooks().size());

        library.removeBook("123456789");

        System.out.println("nb livres apres suppression : " + library.getBooks().size());
        assertEquals(0, library.getBooks().size());
    }

    @Test
    void testSearch() {
        library.addBook(book);
        // Rechercher un livre existant
        Book result = library.searchBook("123456789");
        System.out.println("Recherche du livre avec ISBN 123456789...");
        assertNotNull(result);
        System.out.println("Livre trouvé : " + result.getTitle());

        // Rechercher un livre inexistant
        Book result2 = library.searchBook("123");
        System.out.println("Recherche du livre avec ISBN 123...");
        assertNull(result2);
        System.out.println("book not found ");
    }


    /* ===== @NESTED + @TAGS POUR LA CATEGORISER LES TESTS ===== */

    @Nested
    @Tag("null-operations")
    class NullOperations {
        private Library nullLibrary;
        private Book nullBook;

        @BeforeEach
        void setUpNullEnv() {
            nullLibrary = null;
            nullBook = null;
        }

        @Test
        void addNullBook() {
            assertThrows(NullPointerException.class,
                    () -> library.addBook(nullBook));
        }

        @Test
        void searchWithNull() {
            Library lib = new Library();
            assertThrows(NullPointerException.class,
                    () -> lib.searchBook(null));
        }

        @Test
        void removeWithNullIsbn() {
            assertThrows(NullPointerException.class,
                    () -> library.removeBook(null));
        }

        @Test
        void nullLibrary() {
            assertThrows(NullPointerException.class,
                    () -> nullLibrary.addBook(new Book("Title", "Author", "123")));

            assertThrows(NullPointerException.class,
                    () -> nullLibrary.searchBook("123"));

            assertThrows(NullPointerException.class,
                    () -> nullLibrary.removeBook("123"));
        }
    }


    @Nested
    @Tag("ajout-livre")
    class AddBookTests {
        private final String testIsbn = "123456789";

        @BeforeEach
        void addSampleBook() {
            library.addBook(book);
        }

        @Test
        void findAddedBookByIsbn() {
            Book foundBook = library.searchBook(book.getIsbn());
            assertNotNull(foundBook, "Book Found byISBN");
        }

        @Nested
        @Tag("Supression-livre")
        class RemoveBookTests {
            @Test
            void removeBookByIsbn() {
                library.removeBook(book.getIsbn());
                assertNull(library.searchBook(book.getIsbn()),
                        "Book Removed");
            }

            @Test
            void RemoveWithWrongIsbn() {
                String wrongIsbn = "999999999";
                library.removeBook(wrongIsbn);
                assertNull(library.searchBook(wrongIsbn),
                        "Book Not Found");
            }

        }
    }


    /* ===== EXEMPLE SUR LES TESTS REPETES ===== */

    @Nested
    @Tag("repetition")
    class RepetitionTests {

        @RepeatedTest(5)
        void testAjoutRepete(RepetitionInfo repetitionInfo) {
            System.out.println("Exécution #" + repetitionInfo.getCurrentRepetition());
            library.addBook(book);
            assertEquals(1, library.getBooks().size());
            library.removeBook(book.getIsbn());
        }

        @RepeatedTest(5)
        void testRechercheRepetee(TestInfo testInfo) {
            library.addBook(book);
            Book result = library.searchBook(book.getIsbn());
            assertNotNull(result);
            library.removeBook(book.getIsbn());
        }
    }


}