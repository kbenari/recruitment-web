
package fr.d2factory.libraryapp.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * abstract class define contract of data parsing
 * 
 * @author kben
 *
 */
public abstract class IDataParser<T> {

	/**
	 * public methode of this contract recover informations from json file ton list
	 * of json list
	 * 
	 * @param booksSourceFileS
	 * @param result
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public List<T> getData(String booksSourceFile) throws FileNotFoundException, IOException, ParseException {
		List<T> result = new ArrayList<>();
		JSONArray jsonArray = ReadJsonResourceFile(booksSourceFile);

		jsonArray.forEach(t -> result.add(transform((JSONObject) t)));

		return result;
	}

	/**
	 * read json file and transform it to json array
	 * 
	 * @param booksSourceFile
	 * @param parser
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	protected JSONArray ReadJsonResourceFile(String booksSourceFile)
			throws FileNotFoundException, IOException, ParseException {
		FileReader fileReader = new FileReader(getClass().getClassLoader().getResource(booksSourceFile).getFile());
		return (JSONArray) new JSONParser().parse(fileReader);
	}

	/**
	 * recover informations from JSON File to (T)
	 * 
	 * @param bookJson
	 * @return
	 */
	protected abstract T transform(JSONObject bookJson);

}
