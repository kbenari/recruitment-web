package fr.d2factory.libraryapp.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.repository.BookRepository;
import fr.d2factory.libraryapp.repository.MemberRepository;

/**
 * contract of Data access object to books, members and operations done by
 * members on books
 * 
 * @author kben
 *
 */
public interface LibraryDao {

	/**
	 * set a {@link BookRepository}
	 * 
	 * @param bookRepository
	 */
	void setBookRepository(BookRepository bookRepository);

	/**
	 * set a {@link MemberRepository}
	 * 
	 * @param memberRepository
	 */
	void setMemberRepository(MemberRepository memberRepository);

	/**
	 * getter of {@link BookRepository}
	 * 
	 * @return {@link BookRepository}
	 */
	BookRepository getBookRepository();

	/**
	 * getter of {@link MemberRepository}
	 * 
	 * @return {@link MemberRepository}
	 */
	MemberRepository getMemberRepository();

	/**
	 * operations on database to borrow agiven Book by a given member
	 * 
	 * @param {@link Member}
	 * @param {@link Book }
	 * @param {@link LocalDate}
	 * @return
	 */
	Book borrowBook(Member member, Book book, LocalDate borrowedAt);

	/**
	 * Find a book on the database with a given isbn code
	 * 
	 * @param isbnCode
	 * @return optional if book
	 */
	Optional<Book> findBook(long isbnCode);

	/**
	 * operations on database to return a given Book by a given member
	 * 
	 * @param member
	 * @param book
	 * @return
	 */
	LocalDate returnBook(Member member, Book book);

	/**
	 * find list of book borrowed by a given member with late date
	 * 
	 * @param member
	 * @return list<book>
	 */
	List<Book> findLateBooksBorowedByMember(Member member);

	/**
	 * check if given book is borrowed by a given member
	 * 
	 * @param member
	 * @param book
	 * @return
	 */
	Optional<Book> checkBookBorrowedByMember(Member member, Book book);

}
