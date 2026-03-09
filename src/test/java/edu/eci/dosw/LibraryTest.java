package edu.eci.dosw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.eci.dosw.library.Library;
import edu.eci.dosw.library.book.Book;
import edu.eci.dosw.library.user.User;

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

    @Test
    void ShouldPassWhenDoesntExit(){ 
        Book newBook = new Book("1984", "George Orwell", "9876543210");
        boolean result = library.addBook(newBook);
        // The book is added correctly. 
        assertTrue(result);
        assertEquals(1, library.getAvailableBooks(newBook));
    }

    @Test
    void addBookShouldPassAndIncreaseCountWhenBookAlreadyExists() {
        Book bookToAdd = new Book("Divine Comedy","Dante Alighieri", "1234567890");
        boolean result = library.addBook(bookToAdd);
        assertTrue(result);
        assertEquals(2, library.getAvailableBooks(bookToAdd));
    }
}
