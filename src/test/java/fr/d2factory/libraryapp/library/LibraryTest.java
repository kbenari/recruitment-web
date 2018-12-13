package fr.d2factory.libraryapp.library;

import static fr.d2factory.libraryapp.Mock.mockFirstYearStudent;
import static fr.d2factory.libraryapp.Mock.mockFoundBook;
import static fr.d2factory.libraryapp.Mock.mockResident;
import static fr.d2factory.libraryapp.Mock.mockSecondBook;
import static fr.d2factory.libraryapp.Mock.mockStudent;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import fr.d2factory.libraryapp.RepositoryPreparator;
import fr.d2factory.libraryapp.RepositoryPreparatorImpl;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.dao.LibraryDao;
import fr.d2factory.libraryapp.dao.LibraryDaoImpl;
import fr.d2factory.libraryapp.library.exception.BookNotFoundException;
import fr.d2factory.libraryapp.library.exception.HasLateBooksException;
import fr.d2factory.libraryapp.member.Member;

public class LibraryTest {

	@Inject
	private LibraryDao libraryDao;
	@Inject
	RepositoryPreparator repositoryPreparator;
	@Inject
	private Library library;

	protected Injector injector = Guice.createInjector(new AbstractModule() {
		@Override
		protected void configure() {

			bind(LibraryDao.class).to(LibraryDaoImpl.class);
			bind(RepositoryPreparator.class).to(RepositoryPreparatorImpl.class);
			bind(Library.class).to(TownsvilleLibrary.class);

		}
	});

	@Before
	public void setup() throws IOException, ParseException {
		injector.injectMembers(this);

		libraryDao.setBookRepository(repositoryPreparator.prepareBookRepository());
		libraryDao.setMemberRepository(repositoryPreparator.prepareMemberRepository());

		

	}

	@Test
	public void member_can_borrow_a_book_if_book_is_available() {

		long isbnCode = mockFoundBook().getIsbn().getIsbnCode();
		Member member = mockStudent();

		Book borrowedBook = library.borrowBook(isbnCode, member, LocalDate.now());

		assertTrue(isbnCode == borrowedBook.getIsbn().getIsbnCode());

	}

	@Test(expected = BookNotFoundException.class)
	public void borrowed_book_is_no_longer_available() {
		long isbnCode = mockFoundBook().getIsbn().getIsbnCode();
		Member student = mockStudent();
		Member resident = mockResident();

		// WHEN : book borrowed by student (member number1)
		// Then :book borrowed
		Book borrowedBookFound = library.borrowBook(isbnCode, student, LocalDate.now());
		assertTrue(isbnCode == borrowedBookFound.getIsbn().getIsbnCode());

		// WHEN borrowed book is borrowed by another member
		// THEN bookis not available exception is thrown
		Book borrowedBooknotFound = library.borrowBook(isbnCode, resident, LocalDate.now());

		assertNull(borrowedBooknotFound);
	}

	@Test
	public void residents_are_taxed_10cents_for_each_day_they_keep_a_book() {
		Member resident = mockResident();
		long isbnCode = mockFoundBook().getIsbn().getIsbnCode();

		BigDecimal beforePay = resident.getWallet();
		// borrow book and return it 59 days
		Book borrowedBook = library.borrowBook(isbnCode, resident, LocalDate.now().minusDays(59));
		library.returnBook(borrowedBook, resident);
		
		BigDecimal afterPay = resident.getWallet();
	

		assertTrue(beforePay.subtract(afterPay)
				.compareTo(new BigDecimal("5.9")) == 0);

	}

	@Test
	public void students_pay_10_cents_the_first_30days() {
		
		Member student = mockStudent();
		long isbnCode = mockFoundBook().getIsbn().getIsbnCode();
		BigDecimal beforePay = student.getWallet();
		
		// borrow book and return it 29 days
		Book borrowedBook = library.borrowBook(isbnCode, student, LocalDate.now().minusDays(29));
		library.returnBook(borrowedBook, student);
		
		BigDecimal afterPay = student.getWallet();

		assertTrue(beforePay.compareTo(afterPay.add(new BigDecimal("2.9"))) == 0);

	}

	@Test
	public void students_in_1st_year_are_not_taxed_for_the_first_15days() {
		
		Member student = mockFirstYearStudent();
		long isbnCode = mockFoundBook().getIsbn().getIsbnCode();

		BigDecimal beforePay = student.getWallet();
		
		// borrow book and return it 14 days
		Book borrowedBook = library.borrowBook(isbnCode, student, LocalDate.now().minusDays(14));
		library.returnBook(borrowedBook, student);
		
		
		BigDecimal afterPay = student.getWallet();
		assertTrue(beforePay.compareTo(afterPay) == 0);
	}

	@Test
	public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days() {

		Member student = mockStudent();
		long isbnCode = mockFoundBook().getIsbn().getIsbnCode();

		BigDecimal beforePay = student.getWallet();
		// borrow book and return it 33 days
		Book borrowedBook = library.borrowBook(isbnCode, student, LocalDate.now().minusDays(33));
		library.returnBook(borrowedBook, student);

		BigDecimal afterPay = student.getWallet();
		// 3.45 = (0.1 $ * 30 days) + (0.15$ * 3 days penality)
		BigDecimal payment = new BigDecimal("3.45");

		assertTrue(beforePay.subtract(afterPay).compareTo(payment) == 0);
	}

	@Test
	public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days() {
		Member resident = mockResident();
		long isbnCode = mockFoundBook().getIsbn().getIsbnCode();

		BigDecimal beforePay = resident.getWallet();
		// borrow book and return it 63 days
		Book borrowedBook = library.borrowBook(isbnCode, resident, LocalDate.now().minusDays(63));
		library.returnBook(borrowedBook, resident);
		
		BigDecimal afterPay = resident.getWallet();
		// 6.60 = (0.1 $ *60 days) + (0.20$ * 3 days penality)
		BigDecimal paiement = new BigDecimal("6.60");

		assertTrue(beforePay.subtract(afterPay).compareTo(paiement) == 0);
	}

	// ************************************************************
	// ** members_cannot_borrow_book_if_they_have_late_books ******
	// ************************************************************

	@Test(expected = HasLateBooksException.class)
	public void resdent_cannot_borrow_book_if_they_have_late_books() {
		// resident
		Member resident = mockResident();
		Book book = mockFoundBook();
		library.borrowBook(book.getIsbn().getIsbnCode(), resident, LocalDate.now().minusDays(60));
		Book secondBook = mockSecondBook();
		library.borrowBook(secondBook.getIsbn().getIsbnCode(), resident, LocalDate.now());
	}

	@Test(expected = HasLateBooksException.class)
	public void student_cannot_borrow_book_if_they_have_late_books() {
		// student
		Member student = mockStudent();
		Book book = mockFoundBook();
		library.borrowBook(book.getIsbn().getIsbnCode(), student, LocalDate.now().minusDays(30));
		Book secondBook = mockSecondBook();
		library.borrowBook(secondBook.getIsbn().getIsbnCode(), student, LocalDate.now());
	}

	@Test(expected = HasLateBooksException.class)
	public void firstYearStudent_cannot_borrow_book_if_they_have_late_books() {
		// first year student
		Member firstYearStudent = mockFirstYearStudent();
		Book book = mockFoundBook();
		library.borrowBook(book.getIsbn().getIsbnCode(), firstYearStudent, LocalDate.now().minusDays(30));
		Book secondBook = mockSecondBook();
		library.borrowBook(secondBook.getIsbn().getIsbnCode(), firstYearStudent, LocalDate.now());
	}

}
