package test.java.edu.eci.dosw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.eci.dosw.library.Library;
import edu.eci.dosw.library.loan.Loan;
import edu.eci.dosw.library.user.User;
import edu.eci.dosw.tdd.library.book.Book;
import edu.eci.dosw.tdd.library.loan.LoanStatus;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LibraryTest {
    
    private Library library;
    private User user;
    private Book book_1;
    
    // Since we are going to test several methods, we can create some shared objects
    // here to avoid repeating code in every test.
    @BeforeEach 
    void setUp() {
        library = new Library();
        user = new User();
        user.setId("1");
        user.setName("Esteban Garzón");
        book_1 = new Book("Divine Comedy","Dante Alighieri", "1234567890");
        Book book_2 = new Book("The Odyssey","Homer", "0987654321");
        loan = new Loan();

        // There are already two books, one user and one loan in the library
        library.addBook(book_1);
        library.addBook(book_2);
        library.addUser(user);
        library.loanABook("1", "1234567890");
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

    @Test
    void loanABookShouldCreateAnActiveLoanWhenUserAndBookAreValid() {
        // the book is available
        assertEquals(1, library.getAvailableBook(book_2));
        Loan loan = library.loanABook("1", "0987654321");
        assertNotNull(loan);
        // User is valid
        assertEquals(user, loan.getUser());
        assertEquals(LoanStatus.ACTIVE, loan.getStatus());
    }

     @Test
    void returnLoanShouldPassCurrentDateCorrectStatusAndExistence() {
        Loan returnedLoan = library.returnLoan("1", "0987654321");
        
        assertNotNull(returnedLoan);
        assertEquals(LoanStatus.RETURNED, returnedLoan.getStatus());
        assertNotNull(returnedLoan.getReturnDate());
        assertEquals(LocalDateTime.now(), returnedLoan.getReturnDate());
    }

}