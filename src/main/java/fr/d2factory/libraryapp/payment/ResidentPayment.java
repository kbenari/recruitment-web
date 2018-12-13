/**
 * 
 */
package fr.d2factory.libraryapp.payment;

import static fr.d2factory.libraryapp.library.Constants.RESIDENT_DURATION_LIMIT_PENALITY;
import static fr.d2factory.libraryapp.library.Constants.RESIDENT_NORMAL_PRICE;
import static fr.d2factory.libraryapp.library.Constants.RESIDENT_PENALITY_PRICE;

import java.math.BigDecimal;

/**
 * payment for resident
 * @author kben
 *
 */
public class ResidentPayment implements Payemnt {

	/**
	 * return total amount
	 * calculate normal cost part
	 * plus calculate penality cost part 
	 * 
	 * @see fr.d2factory.libraryapp.payment.Payemnt#paye(long)
	 */
	@Override
	public BigDecimal paye(long days) {
		BigDecimal amount = calculateAmount60Firstdays(
				days <= RESIDENT_DURATION_LIMIT_PENALITY ? days : RESIDENT_DURATION_LIMIT_PENALITY);

		if (days > RESIDENT_DURATION_LIMIT_PENALITY)
			amount = amount.add(calculatePenality(days - RESIDENT_DURATION_LIMIT_PENALITY));

		return amount;

	}
	

	/**
	 * calculate for penality
	 * @param days (loan duration)
	 * @return amount in bigDecimal
	 */
	private BigDecimal calculatePenality(long days) {

		return RESIDENT_PENALITY_PRICE.multiply(new BigDecimal(days));
	}

	
	/**
	 * calculate for normal coast
	 * @param days loan duration
	 * @return
	 */
	private BigDecimal calculateAmount60Firstdays(long days) {

		return RESIDENT_NORMAL_PRICE.multiply(new BigDecimal(days));
	}

}
