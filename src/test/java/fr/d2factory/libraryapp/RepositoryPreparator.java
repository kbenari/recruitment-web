package fr.d2factory.libraryapp;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import fr.d2factory.libraryapp.repository.BookRepository;
import fr.d2factory.libraryapp.repository.MemberRepository;

public interface RepositoryPreparator {

	/**
	 * recover  books from json file <b>books.json</b>
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	BookRepository prepareBookRepository() throws IOException, ParseException;

	/**
	 * recover  members from json file <b>members.json</b>
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	MemberRepository prepareMemberRepository() throws IOException, ParseException;

}