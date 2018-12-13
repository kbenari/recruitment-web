package fr.d2factory.libraryapp.dao;

import static fr.d2factory.libraryapp.Mock.mockFoundBook;
import static fr.d2factory.libraryapp.Mock.mockResident;
import static fr.d2factory.libraryapp.Mock.mockStudent;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
import fr.d2factory.libraryapp.book.Isbn;
import fr.d2factory.libraryapp.member.Member;

/**
 * Test for library DAO
 * @author kben
 *
 */
public class LibraryDaoTest  {

	@Inject LibraryDao libraryDao;
	
	@Inject RepositoryPreparator repositoryPreparator;
	

	 protected Injector injector = Guice.createInjector(new AbstractModule() {
	        @Override
	        protected void configure() {
	        	
	            bind(LibraryDao.class).to(LibraryDaoImpl.class);
	            bind(RepositoryPreparator.class).to(RepositoryPreparatorImpl.class);
	            
	            
	            
	        }
	    });

	 
	 
	 
	@Before
	public void prepareTest() throws IOException, ParseException {
		
		injector.injectMembers(this);
		libraryDao.setBookRepository(repositoryPreparator.prepareBookRepository());
		libraryDao.setMemberRepository(repositoryPreparator.prepareMemberRepository());

	}

	@Test
	public void borrowBook_WhenMember_ThenBookInBrowedBook() {

		Member student = mockStudent();
		Book book = mockFoundBook();
		LocalDate borrowedAt = LocalDate.now().minusDays(16);

		libraryDao.borrowBook(student, book, borrowedAt);

		assertTrue(libraryDao.getBookRepository().findBorrowedBookDate(book).equals(borrowedAt));

	}

	@Test
	public void borrowBook_WhenMember_ThenBookIsNotAvailable() {

		Member student = mockStudent();
		Book book = mockFoundBook();
		LocalDate borrowedAt = LocalDate.now().minusDays(16);

		libraryDao.borrowBook(student, book, borrowedAt);

		assertFalse(libraryDao.getBookRepository().findBook(book.getIsbn().getIsbnCode()).isPresent());
	}

	@Test
	public void borrowBook_WhenMember_ThenBookIsJoinToMember() {

		Member student = mockStudent();
		Book book = mockFoundBook();
		LocalDate borrowedAt = LocalDate.now().minusDays(16);

		libraryDao.borrowBook(student, book, borrowedAt);

		List<Book> findBooksBorowedByMember = libraryDao.getMemberRepository().findBooksBorrowedByMember(student);
		assertTrue(findBooksBorowedByMember.size() == 1
				&& findBooksBorowedByMember.stream().anyMatch(t ->t.getIsbn().equals(new Isbn(book.getIsbn().getIsbnCode()))));

	}
	
	@Test
	public void findBook_WhenBooknotBorrowed_bookIsFound() {

		Book book = mockFoundBook();

		Optional<Book> bookFound = libraryDao.findBook(book.getIsbn().getIsbnCode());

		assertTrue(bookFound.isPresent());
	}

	@Test
	public void findBook_WhenBookBorrowed_bookIsNotFound() {

		Member student = mockStudent();
		Book book = mockFoundBook();
		LocalDate borrowedAt = LocalDate.now().minusDays(16);

		libraryDao.borrowBook(student, book, borrowedAt);

		Optional<Book> bookFound = libraryDao.findBook(book.getIsbn().getIsbnCode());

		assertFalse(bookFound.isPresent());
	}

	@Test
	public void returnBook_WhenBookIsBorrowed_ThenBookIsAvailableAndNotBorrowed() {

		Member student = mockStudent();
		Book book = mockFoundBook();
		LocalDate borrowedAt = LocalDate.now().minusDays(16);

		//Borrow the book , book is not available
		libraryDao.borrowBook(student, book, borrowedAt);
		assertFalse( libraryDao.findBook(book.getIsbn().getIsbnCode()).isPresent());
		
		//book is returnd
		libraryDao.returnBook(student, book);
		
		assertTrue( libraryDao.findBook(book.getIsbn().getIsbnCode()).isPresent());
		assertTrue(libraryDao.getMemberRepository().findBooksBorrowedByMember(student).isEmpty());
	}
	
	@Test
	public void findLateBooksBorowedByStudent_whenMemberhasLate_ThenThereIsLateBookFound() {
		
		Member student = mockStudent();
		Book book = mockFoundBook();
		LocalDate borrowedAt = LocalDate.now().minusDays(31);

		libraryDao.borrowBook(student, book, borrowedAt);
		
		List<Book> lateBooks=libraryDao.findLateBooksBorowedByMember(student);
		assertTrue(lateBooks.size()==1);
		
		
	}
	@Test
	public void findLateBooksBorowedByStudent_whenMemberhasnotLate_ThenThereNOIsLateBookFound() {
		
		Member student = mockStudent();
		Book book = mockFoundBook();
		LocalDate borrowedAt = LocalDate.now().minusDays(29);

		libraryDao.borrowBook(student, book, borrowedAt);
		
		List<Book> lateBooks=libraryDao.findLateBooksBorowedByMember(student);
		assertTrue(lateBooks.size()==0);

	}

	@Test
	public void findLateBooksBorowedByResident_whenHasLateDate_ThenThereIsLateBookFound() {
		
		Member resident = mockResident();
		Book book = mockFoundBook();
		LocalDate borrowedAt = LocalDate.now().minusDays(61);

		libraryDao.borrowBook(resident, book, borrowedAt);
		
		List<Book> lateBooks=libraryDao.findLateBooksBorowedByMember(resident);
		assertTrue(lateBooks.size()==1);
		
		
	}
	@Test
	public void findLateBooksBorowedByResident_whenHasNoLateDate_thenNotLatebookFound() {
		
		Member resident = mockResident();
		Book book = mockFoundBook();
		LocalDate borrowedAt = LocalDate.now().minusDays(29);

		libraryDao.borrowBook(resident, book, borrowedAt);
		
		List<Book> lateBooks=libraryDao.findLateBooksBorowedByMember(resident);
		assertTrue(lateBooks.size()==0);

	}
	
	@Test
	public void checkBookBorowedByMember_WHENMemberBorrowsBook_THENBookIsFound() {
		
		Member resident = mockResident();
		Book book = mockFoundBook();
		LocalDate borrowedAt = LocalDate.now().minusDays(29);
		
		libraryDao.borrowBook(resident, book, borrowedAt);
		
		Optional<Book> bookBorrowedOptional = libraryDao.checkBookBorrowedByMember(resident, book);
		
		assertTrue(bookBorrowedOptional.isPresent());
		
	}
	
	@Test
	public void checkBookBorowedByMember_WHENMemberNotBorrowsBook_THENBookIsNotFound() {
		
		Member resident = mockResident();
		Book book = mockFoundBook();
		
		book = mockFoundBook();
		
		Optional<Book> bookBorrowedOptional = libraryDao.checkBookBorrowedByMember(resident, book);
		
		assertFalse(bookBorrowedOptional.isPresent());
		
	}
}
