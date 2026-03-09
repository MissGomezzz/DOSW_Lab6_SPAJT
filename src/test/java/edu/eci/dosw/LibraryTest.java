package test.java.edu.eci.dosw;

import org.junit.jupiter.api.BeforeEach;

import edu.eci.dosw.library.Library;
import edu.eci.dosw.library.user.User;
import edu.eci.dosw.tdd.library.book.Book;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    
    private Library library;
    private User user;
    private Book book;
    
    // Since we are going to test several methods, we can create some shared objects
    // here to avoid repeating code in every test.
    @BeforeEach 
    void setUp() {
        library = new Library();
        user = new User();
        book = new Book("Divine Comedy","Dante Alighieri", "1234567890");

        // There is already one book in the library
        library.addBook(book);
    }

    // ShouldPassWhenDoesntExist() is left


    @Test
    void addBookShouldPassAndIncreaseCountWhenBookAlreadyExists() {
        Book bookToAdd = new Book("Divine Comedy","Dante Alighieri", "1234567890");
        boolean result = library.addBook(bookToAdd);
        assertTrue(result);
        assertEquals(2, library.getAvailableBooks(bookToAdd));
    }
}
