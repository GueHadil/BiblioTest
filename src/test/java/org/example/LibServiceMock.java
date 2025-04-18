package org.example;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.Test;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
@ExtendWith(MockitoExtension.class)
public class LibServiceMock {
        @Mock
        LibService libService;
        @Test
        void testGetBookByAuthor() {
            Book mockedBook = new Book("Java", "Anne Tasso", "123456789");
            when(libService.findBookByAuthor("Anne Tasso")).thenReturn(mockedBook);
            Book result = libService.findBookByAuthor("Anne Tasso");
            assertNotNull(result);
            assertEquals(mockedBook, result);
            assertEquals("Anne Tasso", result.getAuthor());
            assertEquals("Java", result.getTitle());
        }
}


