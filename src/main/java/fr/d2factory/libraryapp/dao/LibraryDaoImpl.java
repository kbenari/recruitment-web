/**
 * 
 */
package fr.d2factory.libraryapp.dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.repository.BookRepository;
import fr.d2factory.libraryapp.repository.MemberRepository;

/**
 * @author kben
 *
 */
@Singleton
public class LibraryDaoImpl implements LibraryDao {

	private BookRepository bookRepository;

	private MemberRepository memberRepository;

	/**
	 * @see fr.d2factory.libraryapp.dao.LibraryDao#setBookRepository(fr.d2factory.libraryapp.repository.BookRepository)
	 */
	@Override
	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	/**
	 * @see fr.d2factory.libraryapp.dao.LibraryDao#getBookRepository()
	 */
	@Override
	public BookRepository getBookRepository() {

		return this.bookRepository;
	}

	/**
	 * @see fr.d2factory.libraryapp.dao.LibraryDao#setMemberRepository(fr.d2factory.libraryapp.repository.MemberRepository)
	 */
	@Override
	public void setMemberRepository(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	/**
	 * @see fr.d2factory.libraryapp.dao.LibraryDao#getMemberRepository()
	 */
	@Override
	public MemberRepository getMemberRepository() {
		return this.memberRepository;
	}

	/**
	 * @see fr.d2factory.libraryapp.dao.LibraryDao#borrowBook(fr.d2factory.libraryapp.member.Member,
	 *      fr.d2factory.libraryapp.book.Book, java.time.LocalDate)
	 */
	@Override
	public Book borrowBook(Member member, Book book, LocalDate borrowedAt) {

		bookRepository.saveBookBorrow(book, borrowedAt);
		bookRepository.removeBook(book.getIsbn().getIsbnCode());
		memberRepository.addJoinBookMember(book, member);

		return book;
	}

	/**
	 * @see fr.d2factory.libraryapp.dao.LibraryDao#findBook(long)
	 */
	@Override
	public Optional<Book> findBook(long isbnCode) {

		return bookRepository.findBook(isbnCode);
	}

	/**
	 * @see fr.d2factory.libraryapp.dao.LibraryDao#returnBook(fr.d2factory.libraryapp.member.Member,
	 *      fr.d2factory.libraryapp.book.Book)
	 */
	@Override
	public LocalDate returnBook(Member member, Book book) {

		memberRepository.removeBookMember(book);
		bookRepository.addBooks(new ArrayList<Book>(Arrays.asList(book)));
		return bookRepository.removeBorrowedBook(book);

	}

	/**
	 * find list of book borrowed by a given member with late date
	 * 
	 * @param member
	 * @return list<book>
	 */
	@Override
	public List<Book> findLateBooksBorowedByMember(Member member) {

		List<Book> booksBorowedByMember = memberRepository.findBooksBorrowedByMember(member);

		Map<Book, LocalDate> borrowedBooksWithDate = bookRepository.findBorrowedBooks(booksBorowedByMember);

		return borrowedBooksWithDate.entrySet().stream()
				.filter(t -> member.isLateDate(ChronoUnit.DAYS.between(t.getValue(), LocalDate.now())))
				.map(entry -> entry.getKey()).collect(Collectors.toList());

	}

	/**
	 * @see fr.d2factory.libraryapp.dao.LibraryDao#findLateBooksBorowedByMember(fr.d2factory.libraryapp.member.Member)
	 */
	@Override
	public Optional<Book> checkBookBorrowedByMember(Member member, Book book) {

		List<Book> booksBorrowed = memberRepository.findBooksBorrowedByMember(member);
		return booksBorrowed.stream().filter(t -> t.getIsbn().getIsbnCode() == book.getIsbn().getIsbnCode())
				.findFirst();

	}

}
