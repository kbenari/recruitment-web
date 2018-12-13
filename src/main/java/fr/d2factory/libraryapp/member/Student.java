/**
 * 
 */
package fr.d2factory.libraryapp.member;

import static fr.d2factory.libraryapp.library.Constants.STUDENT_DURATION_LIMIT_PENALITY;

import java.math.BigDecimal;

import fr.d2factory.libraryapp.payment.StudentPayment;

/**
 * @author kben
 *
 */
public class Student extends Member {

	/**
	 * constructor with field
	 * 
	 * @param memberId
	 * @param wallet
	 */
	public Student(String memberId, BigDecimal wallet) {
		this.memberId = memberId;
		this.wallet = wallet;
	}

	@Override
	public void payBook(long days) {
		
		wallet = wallet.subtract( new StudentPayment().paye(days));
		
	}

	@Override
	public boolean isLateDate(long value) {
		
		return value >= STUDENT_DURATION_LIMIT_PENALITY;
	}

	

}
