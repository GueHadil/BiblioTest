package org.example;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookTest {
    @Test
    public void testCreation() {
        Book book = new Book("Java premier langage","Anne Tasso","123456789");
        assertEquals("Java premier langage",book.getTitle());
        assertEquals ("Anne Tasso",book.getAuthor());
        assertEquals(123456789,book.getIsbn());

    }
    @Test
    public void testEquals() {
        Book book1 = new Book("book1","Author1","123");
        Book book2 = new Book("book2","Author2","123");
        Book book3 = new Book("book3","Author3","456");
        assertEquals(book1,book2);
        assertEquals(book1,book3);
        assertEquals(book1.hashCode(),book2.hashCode());
        assertEquals(book1.hashCode(),book3.hashCode());
    }


}