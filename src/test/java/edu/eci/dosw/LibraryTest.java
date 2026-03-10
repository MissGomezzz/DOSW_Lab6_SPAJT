package edu.eci.dosw;

public class LibraryTest {    
	@Test
	void loanABookShouldReturnNullIfUserDoesNotExist() {
		Loan loan = library.loanABook("999", "1234567890");
		AssertEquals(null, loan);
	}

	@Test
	void loanABookShouldReturnNullIfBookDoesNotExist() {
    	Loan loan = library.loanABook("1", "0000000000");
    	assertEquals(null, loan);
	}

	@Test
	void loanABookShouldNotAllowSameUserToBorrowSameBookTwice() {
		Loan firstLoan = library.loanABook("1", "0987654321");
		Loan secondLoan = library.loanABook("1", "0987654321");
		assertNotNull(firstLoan);
		assertEquals(null, secondLoan);
	}

	@Test
	void returnLoanShouldReturnNullIfLoanDoesNotExist() {
    	Loan fakeLoan = new Loan();
    	Loan result = library.returnLoan(fakeLoan);
    	assertEquals(null, result);
	}

	@Test
	void getAvailableBooksShouldReturnZeroIfBookNotInLibrary() {
		Book nonExistingBook = new Book("Unknown", "Nobody", "111111");
		int available = library.getAvailableBooks(nonExistingBook);
		assertEquals(0, available);
	}

	@Test
	void loanABookShouldSetLoanDate() {
		Loan loan = library.loanABook("1", "0987654321");
		assertNotNull(loan.getLoanDate());
	}
}
