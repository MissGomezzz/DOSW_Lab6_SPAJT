package edu.eci.dosw;

public class LibraryTest {    
	@Test
	void loanABookShouldReturnNullIfUserDoesNotExist() {
		Loan loan = library.loanABook("999", "1234567890");
		AssertEquals(null, loan);
	}
}
