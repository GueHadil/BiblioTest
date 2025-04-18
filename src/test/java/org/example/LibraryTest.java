package org.example;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.*;
class LibraryTest {

    LibRepository library;
    Book book;

    // Initialisation avant chaque test
    @BeforeEach
    void init() {
        library = new LibRepository();
        book = new Book("Java premier langage", "Anne Tasso", "123456789");
    }

    /* --------------------------- TESTS BASIQUES --------------------------- */

    @Test
    void testAdd() {
        library.addBook(book);
        assertEquals(1, library.getBooks().size());
        assertTrue(library.getBooks().contains(book));
    }

    @Test
    void testRemove() {
        library.addBook(book);
        library.removeBook("123456789");
        assertEquals(0, library.getBooks().size());
    }

    @Test
    void testSearch() {
        library.addBook(book);
        Book result = library.searchBook("123456789");
        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());

        Book result2 = library.searchBook("123");
        assertNull(result2);
    }

    @Test
    void testAddDuplicateBook() {
        library.addBook(book);
        int initialSize = library.getBooks().size();
        library.addBook(book); // Ne doit pas augmenter la taille
        assertEquals(initialSize, library.getBooks().size());
    }

    @Test
    void testAddMultipleBooks() {
        Book book2 = new Book("Clean Code", "Robert C. Martin", "987654321");
        Book book3 = new Book("Effective Java", "Joshua Bloch", "111222333");

        library.addBook(book);
        library.addBook(book2);
        library.addBook(book3);

        assertEquals(3, library.getBooks().size());
    }

    @Test
    void testRemoveNonExistentBook() {
        library.addBook(book);
        int initialSize = library.getBooks().size();
        library.removeBook("999999");
        assertEquals(initialSize, library.getBooks().size());
    }

    @Test
    void testAddDifferentObjectSameIsbn() {
        Book anotherBook = new Book("Another Title", "Another Author", "123456789");
        library.addBook(book);
        library.addBook(anotherBook);
        assertEquals(1, library.getBooks().size());
    }

    @Test
    void testGetBooksDirectAccess() {
        library.addBook(book);
        assertTrue(library.getBooks().contains(book));
    }

    @Test
    void testAddBookWithEmptyIsbn() {
        Book invalidBook = new Book("Invalid", "Author", "");
        library.addBook(invalidBook);
        assertTrue(library.getBooks().contains(invalidBook));
    }

    @Test
    void testAddBookWithNullTitle() {
        Book invalidBook = new Book(null, "Author", "321321");
        library.addBook(invalidBook);
        assertTrue(library.getBooks().contains(invalidBook));
    }

    @Test
    void stressTestAddManyBooks() {
        for (int i = 0; i < 1000; i++) {
            Book book = new Book("Title " + i, "Author " + i, String.valueOf(i));
            library.addBook(book);
        }
        assertEquals(1000, library.getBooks().size());
    }

    /* --------------------------- TESTS SUR L'AUTEUR --------------------------- */

    @Test
    void testSearchBookByAuthor_Found() {
        library.addBook(book);
        Book result = library.searchBookByAuthor("Anne Tasso");
        assertNotNull(result);
        assertEquals("Anne Tasso", result.getAuthor());
    }

    @Nested
    class AuthorTests {
        @BeforeEach
        void setupAuthorBooks() {
            library.addBook(new Book("Intro to Java", "Anne Tasso", "111"));
            library.addBook(new Book("Advanced Java", "Anne Tasso", "222"));
        }

        @Test
        void testSearchBookByAuthor() {
            Book result = library.searchBookByAuthor("Anne Tasso");
            assertNotNull(result);
        }

        @Nested
        class RemoveAuthorBooksTests {
            @Test
            void removeBookByIsbn() {
                library.removeBook("111");
                assertNull(library.searchBook("111"));
            }
        }
    }

    /* --------------------------- TESTS AVEC @NESTED --------------------------- */

    @Nested
    @Tag("null-operations")
    class NullOperations {
        private LibRepository nullLibrary;
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
            assertThrows(NullPointerException.class,
                    () -> library.searchBook(null));
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
        }
    }

    @Nested
    @Tag("add/remove_book")
    class AddBookTests {

        @BeforeEach
        void addSampleBook() {
            library.addBook(book);
        }

        @Test
        void findAddedBookByIsbn() {
            Book foundBook = library.searchBook(book.getIsbn());
            assertNotNull(foundBook);
        }

        @Nested
        @Tag("Supression-livre")
        class RemoveBookTests {

            @Test
            void removeBookByIsbn() {
                library.removeBook(book.getIsbn());
                assertNull(library.searchBook(book.getIsbn()));
            }

            @Test
            void RemoveWithWrongIsbn() {
                library.removeBook("999999999");
                assertNull(library.searchBook("999999999"));
            }
        }
    }

    /* --------------------------- TESTS REPETES --------------------------- */

    @Nested
    @Tag("repetition")
    class RepetitionTests {

        @RepeatedTest(5)
        void testAjoutRepete(RepetitionInfo repetitionInfo) {
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

    /* --------------------------- TESTS PARAMETRES VIA CSV --------------------------- */

    @ParameterizedTest
    @CsvFileSource(resources = "/books.csv", numLinesToSkip = 1)
    void testAddBookFromCsv(String title, String author, String isbn) {
        Book book = new Book(title, author, isbn);
        library.addBook(book);
        assertEquals(1, library.getBooks().size());
        assertTrue(library.getBooks().contains(book));
        library.removeBook(isbn);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/books.csv", numLinesToSkip = 1)
    void testSearchBookFromCsv(String title, String author, String isbn) {
        Book bookToAdd = new Book(title, author, isbn);
        library.addBook(bookToAdd);
        Book foundBook = library.searchBook(isbn);
        assertNotNull(foundBook);
        assertEquals(title, foundBook.getTitle());
        library.removeBook(isbn);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/books.csv", numLinesToSkip = 1)
    void testRemoveBookFromCsv(String title, String author, String isbn) {
        Book bookToRemove = new Book(title, author, isbn);
        library.addBook(bookToRemove);
        library.removeBook(isbn);
        assertEquals(0, library.getBooks().size());
        assertNull(library.searchBook(isbn));
    }

    /* --------------------------- TEST DYNAMIQUE --------------------------- */

    @TestFactory
    Collection<DynamicTest> testRechercheParAuteur() {
        Map<String, Book> livresParAuteur = Map.of(
                "Anne Tasso", new Book("Java Facile", "Anne Tasso", "111"),
                "Marc Dubois", new Book("Spring Boot", "Marc Dubois", "222"),
                "Lina Bell", new Book("Kotlin Pro", "Lina Bell", "333")
        );

        livresParAuteur.values().forEach(book -> library.addBook(book));

        return livresParAuteur.entrySet().stream()
                .map(entry -> DynamicTest.dynamicTest(
                        "Recherche par auteur : " + entry.getKey(),
                        () -> {
                            Book result = library.searchBookByAuthor(entry.getKey());
                            assertNotNull(result);
                            assertEquals(entry.getKey(), result.getAuthor());
                        }
                )).toList();
    }
}
