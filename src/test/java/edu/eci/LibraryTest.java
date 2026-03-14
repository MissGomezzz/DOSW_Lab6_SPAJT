package edu.eci;

import edu.eci.dosw.App;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow; 
import java.time.LocalDateTime;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
		/* There are already two books and one user in the library */
		library.addBook(book_1);
		library.addBook(book_2);
		library.addUser(user);
	}

	@Test
	void loanABookShouldReturnNullIfUserDoesNotExist() {
		loan = library.loanABook("999", "1234567890");
		assertEquals(null, loan);
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
	void loanABookShouldReturnNullIfBookDoesNotExist() {
    	loan = library.loanABook("1", "0000000000");
    	assertEquals(null, loan);
	}

	@Test	
	void addBookShouldPassAndIncreaseCountWhenBookAlreadyExists() {
		Book bookToAdd = new Book("Divine Comedy","Dante Alighieri", "1234567890");
		boolean result = library.addBook(bookToAdd);
		assertTrue(result);
		assertEquals(2, library.getAvailableBooks(bookToAdd));
	}

	@Test
	void loanABookShouldNotAllowSameUserToBorrowSameBookTwice() {
		Loan firstLoan = library.loanABook("1", "0987654321");
		Loan secondLoan = library.loanABook("1", "0987654321");
		assertNotNull(firstLoan);
		assertEquals(null, secondLoan);
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
	void returnLoanShouldReturnNullIfLoanDoesNotExist() {
    	Loan fakeLoan = new Loan();
    	Loan result = library.returnLoan(fakeLoan);
    	assertEquals(null, result);
	}

	@Test
	void loanABookShouldDecreaseAvailableBooks() {
		library.loanABook("1", "0987654321");
		/* If a book is loaned, that book will not be available for the public temporarily. */
		assertEquals(0, library.getAvailableBooks(book_2));
	}

	@Test
	void getAvailableBooksShouldReturnZeroIfBookNotInLibrary() {
		Book nonExistingBook = new Book("Unknown", "Nobody", "111111");
		int available = library.getAvailableBooks(nonExistingBook);
		assertEquals(0, available);
	}

	@Test
	void returnLoanShouldIncreaseAvailableBooks() {
		/* Loan book_1, then return it — its count should go back to 1 */
		Loan activeLoan = library.loanABook("1", "1234567890");
		library.returnLoan(activeLoan);
		assertEquals(1, library.getAvailableBooks(book_1));
	}

	@Test
	void loanABookShouldSetLoanDate() {
		loan = library.loanABook("1", "0987654321");
		assertNotNull(loan.getLoanDate());
	}

	@Test
	void returnLoanShouldPassCurrentDateCorrectStatusAndExistence() {
		Loan activeLoan = library.loanABook("1", "1234567890");
		Loan returned = library.returnLoan(activeLoan);
		assertNotNull(returned);
		assertEquals(LoanStatus.RETURNED, returned.getStatus());
		assertNotNull(returned.getReturnDate());
		/* truncate to seconds to avoid nanosecond drift */
		assertEquals(
			LocalDateTime.now().withNano(0),
			returned.getReturnDate().withNano(0)
		);
	}

	@Test
	void addBookShouldStoreNewBookWithCountOfOne() {
		boolean result = library.addBook(book_3);
		assertTrue(result);
		assertEquals(1, library.getAvailableBooks(book_3));
	}

	@Test
	void loanABookShouldReturnActiveLoanWithCorrectBook() {
		Loan activeLoan = library.loanABook("1", "0987654321");
		assertNotNull(activeLoan);
		assertEquals(LoanStatus.ACTIVE, activeLoan.getStatus());
		assertEquals(book_2, activeLoan.getBook());
	}

	@Test
	void returnLoanShouldChangeStatusToReturned() {
		Loan activeLoan = library.loanABook("1", "0987654321");
		assertNotNull(activeLoan);
		Loan returnedLoan = library.returnLoan(activeLoan);
		assertNotNull(returnedLoan);
		assertEquals(LoanStatus.RETURNED, returnedLoan.getStatus());
	}

	@Test
	void addBookShouldHandleEmptyAuthorOrTitle() {
		Book strangeBook = new Book("", "", "1122334455");
		boolean result = library.addBook(strangeBook);
		assertTrue(result);
		assertEquals(1, library.getAvailableBooks(strangeBook));
	}

	@Test
	void loanABookShouldReturnNullIfAnotherUserHasTheOnlyCopy() {
		User user2 = new User();
		user2.setId("2");
		user2.setName("Juan Pérez");
		library.addUser(user2);
		library.loanABook("1", "0987654321");
		Loan failedLoan = library.loanABook("2", "0987654321");
		assertEquals(null, failedLoan);
	}

	@Test
	void returnLoanShouldKeepSameUserAndBook() {
		Loan activeLoan = library.loanABook("1", "1234567890");
		Loan returnedLoan = library.returnLoan(activeLoan);
		assertEquals(user, returnedLoan.getUser());
		assertEquals(book_1, returnedLoan.getBook());
	}


	@Test
	void appMainShouldRunWithoutErrors() {
		assertDoesNotThrow(() -> App.main(new String[]{}));
	}


	@Test
	void booksWithDifferentIsbnShouldNotBeEqual() {
		Book bookA = new Book("Clean Code", "Robert C. Martin", "111222333");
		Book bookB = new Book("Clean Code", "Robert C. Martin", "999888777");
		assertEquals(false, bookA.equals(bookB));
	}

	@Test
	void bookShouldNotBeEqualToNull() {
		Book book = new Book("Clean Code", "Robert C. Martin", "111222333");
		assertEquals(false, book.equals(null));
	}

	@Test
	void bookShouldNotBeEqualToDifferentObjectType() {
		Book book = new Book("Clean Code", "Robert C. Martin", "111222333");
		assertEquals(false, book.equals("not a book"));
	}


	@Test
	void bookShouldStoreTittleAuthorAndIsbnCorrectly() {
		Book book = new Book("Clean Code", "Robert C. Martin", "111222333");
		assertEquals("Clean Code", book.getTittle());
		assertEquals("Robert C. Martin", book.getAuthor());
		assertEquals("111222333", book.getIsbn());
	}


	@Test
	void appMainShouldPrintHelloWorld() {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    try {
        App.main(new String[]{});
        String printed = out.toString().toLowerCase();
        assertTrue(printed.contains("hello"));
    } finally {
        System.setOut(originalOut);
    }
	}
}
