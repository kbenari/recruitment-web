/**
 * 
 */
package fr.d2factory.libraryapp.parser;

import java.math.BigDecimal;

import org.json.simple.JSONObject;

import fr.d2factory.libraryapp.member.FirstYearStudent;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;

/**
 * define parsing of {@link Member} json file
 * 
 * @author kben
 *
 */
public class MemberDataParser extends IDataParser<Member> {
	/**
	 * transform json object to Book object
	 * 
	 * @param {@link JSONObject}
	 * @return {@link Member}
	 */
	protected Member transform(JSONObject memberJson) {
		String memberId = (String) memberJson.get("memberId");
		BigDecimal wallet = new BigDecimal((String) memberJson.get("wallet"));
		Member member;
		switch ((String) memberJson.get("type")) {
		case "student": {
			long year = (long) memberJson.get("year");
			if (year == 1)
				member = new FirstYearStudent(memberId, wallet);
			else
				member = new Student(memberId, wallet);
			break;
		}
		default:

			member = new Resident(memberId, wallet);
		}

		return member;

	}

}
