/**
 * 
 */
package fr.d2factory.libraryapp.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.dao.LibraryDao;
import fr.d2factory.libraryapp.library.exception.BookNotFoundException;
import fr.d2factory.libraryapp.library.exception.HasLateBooksException;
import fr.d2factory.libraryapp.member.Member;

/**
 * @author kben
 *
 */
@Singleton
public class TownsvilleLibrary implements Library {
	@Inject private LibraryDao libraryDao;

	
	/**
	 * @see fr.d2factory.libraryapp.library.Library#borrowBook(long, fr.d2factory.libraryapp.member.Member, java.time.LocalDate)
	 */
	@Override
	public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException, BookNotFoundException {
		List<Book> lateBooksBorrowed = libraryDao.findLateBooksBorowedByMember(member);
		if (!lateBooksBorrowed.isEmpty()) 
			throw new HasLateBooksException("Member: "+member.getMemberId()+" has late book(s)");
		
		Optional<Book> bookToBorrow = libraryDao.findBook(isbnCode);
		if (! bookToBorrow.isPresent())
			throw new BookNotFoundException("Book: "+ isbnCode);
		
		return libraryDao.borrowBook(member, bookToBorrow.get(), borrowedAt);
	}

	/**
	 * 
	 * @see
	 * fr.d2factory.libraryapp.library.Library#returnBook(fr.d2factory.libraryapp.
	 * book.Book, fr.d2factory.libraryapp.member.Member)
	 */
	@Override
	public void returnBook(Book book, Member member) {
		
		Optional<Book> bookBorrowedOptional = libraryDao.checkBookBorrowedByMember(member, book);
		if (!bookBorrowedOptional.isPresent() )
			throw new BookNotFoundException("Book :" +book.getIsbn().getIsbnCode()+ "wasn't borrewed by member "+member.getMemberId() );
	
		LocalDate borrowedDate=libraryDao.returnBook(member, book);
		member.payBook(ChronoUnit.DAYS.between(borrowedDate, LocalDate.now()));
	}

}
