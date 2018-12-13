/**
 * 
 */
package fr.d2factory.libraryapp.payment;

import static fr.d2factory.libraryapp.library.Constants.STUDENT_DURATION_LIMIT_PENALITY;
import static fr.d2factory.libraryapp.library.Constants.STUDENT_NORMAL_PRICE;
import static fr.d2factory.libraryapp.library.Constants.STUDENT_PENALITY_PRICE;

import java.math.BigDecimal;

/**
 * @author kben
 *
 */
public class StudentPayment implements Payemnt {

	/**
	 * amount of normal charge + amount of penality charge
	 * 
	 * @see fr.d2factory.libraryapp.payment.Payemnt#paye(long)
	 */
	@Override
	public BigDecimal paye(long days) {

		BigDecimal amount = calculateAmount30Firstdays(
				days >= STUDENT_DURATION_LIMIT_PENALITY ? STUDENT_DURATION_LIMIT_PENALITY : days);
		if (days > STUDENT_DURATION_LIMIT_PENALITY)
			amount = amount.add(calculatePenality(days - STUDENT_DURATION_LIMIT_PENALITY));
		return amount;

	}

	/**
	 * calculate for penality
	 * 
	 * @param days (loan duration)
	 * @return amount in bigDecimal
	 */
	private BigDecimal calculatePenality(long days) {

		return STUDENT_PENALITY_PRICE.multiply(new BigDecimal(days));
	}

	/**
	 * calculate for normal coast
	 * 
	 * @param days loan duration
	 * @return
	 */
	private BigDecimal calculateAmount30Firstdays(long days) {

		return STUDENT_NORMAL_PRICE.multiply(new BigDecimal(days));
	}

}
