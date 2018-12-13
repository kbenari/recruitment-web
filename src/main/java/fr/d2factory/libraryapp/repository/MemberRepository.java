/**
 * 
 */
package fr.d2factory.libraryapp.repository;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.member.Member;

/**
 * The member repository emulates a database via 2 HashMaps
 */
public class MemberRepository {
	
	
	private Map<String, Member> Members = new HashMap<>();
	private Map<Book, Member> BookMemberJoin = new HashMap<>();

	
	// ***************************
	// ***** Member *****
	// ***************************

	public void addMember(List<Member> membersToAdd) {
		Members.putAll(membersToAdd.stream().collect(Collectors.toMap(Member::getMemberId, Function.identity())));
	}

	public Member findMember(String memberId) {

		return Members.get(memberId);
	}

	// ***************************
	// ***** BookMemberJoin *****
	// ***************************

	
	public List<Book> findBooksBorowedByMember(Member member) {
		return findBooksBorrowedByMember(member);
	}

	public List<Book> findBooksBorrowedByMember(Member member) {
		return BookMemberJoin.entrySet().stream()
				.filter(entry -> entry.getValue().getMemberId().equals(member.getMemberId()))
				.map(entry -> entry.getKey()).collect(Collectors.toList());
		 
	
	}

	public void addJoinBookMember(Book book, Member member) {
		BookMemberJoin.put(book, member);
	}

	public void removeBookMember(Book book) {
		BookMemberJoin.remove(book);
	}

	

}
