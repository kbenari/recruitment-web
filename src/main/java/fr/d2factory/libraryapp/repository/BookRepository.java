package fr.d2factory.libraryapp.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.Isbn;

/**
 * The book repository emulates a database via 4 HashMaps
 */
public class BookRepository {
	
	private Map<Isbn, Book> availableBooks = new HashMap<>();
	private Map<Book, LocalDate> borrowedBooks = new HashMap<>();
	

	// ******************************************
	// ***** available Books Map Operations *****
	// ******************************************

	public void addBooks(List<Book> books) {
		availableBooks.putAll(books.stream().collect(Collectors.toMap(Book::getIsbn, Function.identity())));
	}


	public Optional<Book> findBook(long isbnCode) {

		return availableBooks.entrySet().stream().filter(e -> e.getKey().getIsbnCode() ==isbnCode).map(e ->e.getValue()).findFirst();
		 
	}

	
	public void removeBook(long isbnCode) {

		availableBooks.remove(new  Isbn(isbnCode));
	}

	// *****************************************
	// ***** Borrowed Books Map Operations *****
	// *****************************************

	public void saveBookBorrow(Book book, LocalDate borrowedAt) {
		borrowedBooks.put(book, borrowedAt);
	}

	
	public LocalDate findBorrowedBookDate(Book book) {

		return borrowedBooks.get(book);
	}
	
	public Map<Book, LocalDate> findBorrowedBooks(List<Book> booksToFind){
		
		return borrowedBooks.entrySet().stream().filter(
				t->booksToFind.stream().anyMatch(
						e->e.getIsbn().equals(t.getKey().getIsbn())
						))
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}


	public LocalDate removeBorrowedBook(Book book) {
		return borrowedBooks.remove(book);
		
	}



}
