/**
 * 
 */
package fr.d2factory.libraryapp.payment;

import java.math.BigDecimal;

import fr.d2factory.libraryapp.member.Member;


/**
 * @author kben
 *
 * @param <? implements {@link Member}>
 */
public interface Payemnt {
	
	/** paye for book borrowing dyring days
	 * @param days
	 * @return
	 */
	BigDecimal paye ( long days);

}
