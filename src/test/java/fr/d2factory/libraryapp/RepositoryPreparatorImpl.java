/**
 * 
 */
package fr.d2factory.libraryapp;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.google.inject.Singleton;

import fr.d2factory.libraryapp.parser.BookDataParser;
import fr.d2factory.libraryapp.parser.MemberDataParser;
import fr.d2factory.libraryapp.repository.BookRepository;
import fr.d2factory.libraryapp.repository.MemberRepository;

/**
 * class used to prepar repositories 
 * @author kben
 *
 */
@Singleton
public class RepositoryPreparatorImpl implements RepositoryPreparator {
	private static final String BOOKS_JSON = "books.json";
	private static final String MEMBERS_JSON = "members.json";
	
	/* (non-Javadoc)
	 * @see fr.d2factory.libraryapp.RepositoryPreparator#prepareBookRepository()
	 */
	@Override
	public BookRepository prepareBookRepository() throws IOException, ParseException {
		BookRepository bookRepository = new BookRepository();
		
		bookRepository.addBooks(new BookDataParser().getData(BOOKS_JSON));
		
		return bookRepository;
	}

	/* (non-Javadoc)
	 * @see fr.d2factory.libraryapp.RepositoryPreparator#prepareMemberRepository()
	 */
	@Override
	public  MemberRepository prepareMemberRepository() throws  IOException, ParseException {
		MemberRepository memberRepository = new MemberRepository();
		memberRepository.addMember(new MemberDataParser().getData(MEMBERS_JSON));
		return memberRepository;
	}
	

}
