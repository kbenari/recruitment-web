package fr.d2factory.libraryapp.payment;

import static fr.d2factory.libraryapp.library.Constants.FIRST_YEAR_STUDENT_FREE_DURATION;
import static fr.d2factory.libraryapp.library.Constants.STUDENT_DURATION_LIMIT_PENALITY;
import static fr.d2factory.libraryapp.library.Constants.STUDENT_NORMAL_PRICE;
import static fr.d2factory.libraryapp.library.Constants.STUDENT_PENALITY_PRICE;

import java.math.BigDecimal;

public class FirstYearStudentPayment implements Payemnt {

	/**
	 * free period 
	 *  + normal charge period
	 *  +penality period 
	 * @see fr.d2factory.libraryapp.payment.Payemnt#paye(long)
	 */
	@Override
	public BigDecimal paye( long days) {

		long daysLessThan15 = days > FIRST_YEAR_STUDENT_FREE_DURATION ? FIRST_YEAR_STUDENT_FREE_DURATION : days;
		BigDecimal amount = calculateAmount15Firstdays(daysLessThan15);
		if (days > FIRST_YEAR_STUDENT_FREE_DURATION) {
			BigDecimal daysBetween15And30 = calculateAmountBetween15And30(days>=STUDENT_DURATION_LIMIT_PENALITY? STUDENT_DURATION_LIMIT_PENALITY-FIRST_YEAR_STUDENT_FREE_DURATION:days - FIRST_YEAR_STUDENT_FREE_DURATION );
			amount = amount.add(daysBetween15And30);
		}
		if (days > STUDENT_DURATION_LIMIT_PENALITY)
			amount= amount.add(calculatePenality(days - STUDENT_DURATION_LIMIT_PENALITY));

		return amount;
	}
	
	
	/**
	 * amount based on penality charge 
	 * @param numberOfDays
	 * @return
	 */
	private BigDecimal calculatePenality(long numberOfDays) {

		return STUDENT_PENALITY_PRICE.multiply( new BigDecimal(numberOfDays));
	}

	/**
	 * amount based on normal charge 
	 * @param numberOfDays
	 * @return
	 */
	private BigDecimal calculateAmountBetween15And30(long numberOfDays) {

		return STUDENT_NORMAL_PRICE.multiply(new BigDecimal( numberOfDays));
	}

	
	/**
	 * freePeriod 
	 * @param numberOfDays
	 * @return
	 */
	protected BigDecimal calculateAmount15Firstdays(long numberOfDays) {
		return BigDecimal.ZERO;
	}


}
