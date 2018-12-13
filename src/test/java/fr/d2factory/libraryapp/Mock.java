/**
 * 
 */
package fr.d2factory.libraryapp;

import java.math.BigDecimal;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.Isbn;
import fr.d2factory.libraryapp.member.FirstYearStudent;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;

/**
 * This class creates mock for Tests
 * @author kben
 *
 */
public class Mock {
	/**
	 * mock  book which exists  in json file
	 * @return {@link Book}
	 */
	public static  Book mockFoundBook() {
		return new Book("Harry Potter", "J.K. Rowling", new Isbn(46578964513l));
	}
	
	/**
	 * mock  second book which exists  in json file
	 * @return {@link Book}
	 */
	public static  Book mockSecondBook() {
		return new Book("Around the world in 80 days", "Jules Verne", new Isbn(3326456467846l));
	}
	
	/**
	 * mock  book which doesn't  exist  in json file
	 * @return {@link Book}
	 */
	public static  Book mockNotFoundBook() {
		return new Book("Lord of the Rings", "J.R.R Tolkien", new Isbn(22538364541l));
	}
	/**
	 * mock  student with wallet 135$ and id 001
	 * @return {@link Student}
	 */
	public static  Member mockStudent() {
		return new Student("001", new BigDecimal(135) );
	}
	
	/**
	 *  mock  Resident with wallet 250$ and id 002
	 * @return {@link Resident}
	 */
	public static  Member mockResident() {
		return new Resident("002",  new BigDecimal(250));
	}
	
	/**
	 *  mock first year student with wallet 80$ and id 003
	 * @return {@link FirstYearStudent}
	 */
	public static Member mockFirstYearStudent(){
		return new FirstYearStudent("003", new BigDecimal(80) );
	}
	
}
