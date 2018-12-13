/**
 * 
 */
package fr.d2factory.libraryapp.parser;

import org.json.simple.JSONObject;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.Isbn;

/**
 * define parsing of {@link Book} json file
 * 
 * @author kben
 *
 */
public class BookDataParser extends IDataParser<Book> {

	/**
	 * transform json object to Book object
	 * 
	 * @param {@link JSONObject}
	 * @return {@link Book}
	 */
	@Override
	protected Book transform(JSONObject bookJson) {
		String title = (String) bookJson.get("title");
		String author = (String) bookJson.get("author");
		Isbn isbn = new Isbn((long) ((JSONObject) bookJson.get("isbn")).get("isbnCode"));
		return new Book(title, author, isbn);

	}

}
