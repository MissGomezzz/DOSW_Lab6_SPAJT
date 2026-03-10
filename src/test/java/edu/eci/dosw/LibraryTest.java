package edu.eci.dosw;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.eci.dosw.library.Library;
import edu.eci.dosw.library.book.Book;
import edu.eci.dosw.library.loan.Loan;
import edu.eci.dosw.library.loan.LoanStatus;
import edu.eci.dosw.library.user.User;

public class LibraryTest {
	private Library library;
	private User user;
	private Book book_1;
	private Book book_2;
	private Book book_3;
	private Loan loan; 

	/* Since we are going to test several methods, we can create some shared
	 * objects here to avoid repeating code in every test. */
	@BeforeEach 
	void setUp() {
		library = new Library();
		user = new User();
		user.setId("1");
		user.setName("Esteban Garzón");
		book_1 = new Book("Divine Comedy","Dante Alighieri", "1234567890");
		book_2 = new Book("The Odyssey","Homer", "0987654321");
		book_3 = new Book("50 sombras ...", "Samuel Castelblanco", "78564867");
		loan = new Loan();
		/* There are already two books, one user and one loan in the library */
		library.addBook(book_1);
		library.addBook(book_2);
		library.addUser(user);
		library.loanABook("1", "1234567890");
	}

	@Test
	void ShouldPassWhenDoesntExit(){ 
		Book newBook = new Book("1984", "George Orwell", "9876543210");
		boolean result = library.addBook(newBook);
		/* The book is added correctly. */
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
		/* the book is available */
		assertEquals(1, library.getAvailableBooks(book_2));
		loan = library.loanABook("1", "0987654321");
		assertNotNull(loan);
		/* User is valid */
		assertEquals(user, loan.getUser());
		assertEquals(LoanStatus.ACTIVE, loan.getStatus());
	}

	@Test
	void loanABookShouldDecreaseAvailableBooks() {
		library.loanABook("1", "0987654321");
		/* If a book is loaned, that book will not be available for the public temporarily. */
		assertEquals(0, library.getAvailableBooks(book_2));
	}

	@Test
	void returnLoanShouldIncreaseAvailableBooks() {
		library.returnLoan(loan);
		/* If a book is returned, the book will be available to the public. */
		assertEquals(1, library.getAvailableBooks(book_2));
	}

	@Test
	void returnLoanShouldPassCurrentDateCorrectStatusAndExistence() {
		library.returnLoan(loan);
		assertNotNull(loan);
		assertEquals(LoanStatus.RETURNED, loan.getStatus());
		assertNotNull(loan.getReturnDate());
		assertEquals(LocalDateTime.now(), loan.getReturnDate());
	}
}
