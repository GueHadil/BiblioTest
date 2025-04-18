package org.example;

import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.testng.AssertJUnit.assertEquals;

public class BookTest {

    // Test de création d'un objet Book et vérification de ses attributs
    @Test
    public void testCreation() {
        Book book = new Book("Java premier langage","Anne Tasso","123456789");
        assertEquals("Java premier langage", book.getTitle());
        assertEquals("Anne Tasso", book.getAuthor());
        assertEquals(123456789, book.getIsbn());
    }

    // Test d'égalité : deux livres avec le même ISBN sont considérés égaux (peu importe le titre ou l'auteur)
    @Test
    public void testEquals() {
        Book book1 = new Book("book1", "Author1", "123");
        Book book2 = new Book("book2", "Author2", "123");
        Book book3 = new Book("book3", "Author3", "456");
        assertEquals(book1, book2); // égalité basée sur le même ISBN
        assertEquals(book1, book3);
        assertEquals(book1.hashCode(), book2.hashCode());
        assertEquals(book1.hashCode(), book3.hashCode());
    }

    // Test des setters pour modifier les attributs du livre
    @Test
    public void testSetters() {
        Book book = new Book("Old Title", "Old Author", "Old ISBN");
        book.setTitle("New Title");
        book.setAuthor("New Author");
        book.setIsbn("New ISBN");
        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals("New ISBN", book.getIsbn());
    }

    // Test pour vérifier qu’un livre n’est pas égal à null
    @Test
    public void testEqualsWithNull() {
        Book book = new Book("Book", "Author", "ISBN");
        assertNotEquals(book, null);
    }

    // Test pour vérifier qu’un livre n’est pas égal à un objet d’une autre classe
    @Test
    public void testEqualsWithDifferentClass() {
        Book book = new Book("Book", "Author", "ISBN");
        String notABook = "Not a Book";
        assertNotEquals(book, notABook);
    }

    // Test de cohérence du hashCode : doit être constant à travers plusieurs appels
    @Test
    public void testHashCodeConsistency() {
        Book book = new Book("Book", "Author", "ISBN");
        int initialHashCode = book.hashCode();
        assertEquals(initialHashCode, book.hashCode());
        assertEquals(initialHashCode, book.hashCode());
    }

    // Test que deux livres avec des ISBN différents ne sont pas égaux
    @Test
    public void testEqualsWithDifferentISBN() {
        Book book1 = new Book("Book1", "Author1", "123");
        Book book2 = new Book("Book2", "Author2", "456");
        assertNotEquals(book1, book2);
    }

    // Test de réflexivité : un objet doit être égal à lui-même
    @Test
    public void testEqualsReflexive() {
        Book book = new Book("Book", "Author", "ISBN");
        assertEquals(book, book);
    }

    // Test que deux livres avec des ISBN différents ont des hashCodes différents
    @Test
    public void testHashCodeDifferentISBN() {
        Book book1 = new Book("Book1", "Author1", "123");
        Book book2 = new Book("Book2", "Author2", "456");
        assertNotEquals(book1.hashCode(), book2.hashCode());
    }
}
