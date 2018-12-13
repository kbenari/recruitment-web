/**
 * 
 */
package fr.d2factory.libraryapp.library;

import java.math.BigDecimal;

/**
 * @author kben
 *
 */
public class Constants {
	
	public static final BigDecimal STUDENT_NORMAL_PRICE = new BigDecimal("0.1");
	public static final BigDecimal RESIDENT_NORMAL_PRICE =new BigDecimal("0.1");
	public static final BigDecimal STUDENT_PENALITY_PRICE = new BigDecimal("0.15");
	public static final BigDecimal RESIDENT_PENALITY_PRICE = new BigDecimal("0.20");
	
	public static final int RESIDENT_DURATION_LIMIT_PENALITY = 60;
	public static final int STUDENT_DURATION_LIMIT_PENALITY = 30;
	public static final int FIRST_YEAR_STUDENT_FREE_DURATION= 15;
	
}
