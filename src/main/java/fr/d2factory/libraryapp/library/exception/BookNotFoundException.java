/**
 * 
 */
package fr.d2factory.libraryapp.library.exception;

/**
 * Exception thrown when book is not found 
 * @author kben
 *
 */
public class BookNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * @param string
	 */
	public BookNotFoundException(String string) {
		super(string);
	}
}
