package fr.d2factory.libraryapp.payment;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class PaymentTest {

	private Payemnt firstYearStudentPayment = new FirstYearStudentPayment();
	private Payemnt studentPayment = new StudentPayment();
	private Payemnt residentPayment = new ResidentPayment();

	@Test
	public void testPaymentFirstYearStudent() {
		// when first year student paye for 15 days the amount is equal to zero
		assertTrue(firstYearStudentPayment.paye(15).compareTo(BigDecimal.ZERO) == 0);

		// when first year student paye for 30 days the amount is equal to 1.5 (0.1
		// *15days)
		assertTrue(firstYearStudentPayment.paye(30).compareTo(new BigDecimal("1.5")) == 0);

		// when first year student paye for 39 days the amount is equal to 2.85 ((0.1 *
		// 15days)+(0.15 *9 days))
		assertTrue(firstYearStudentPayment.paye(39).compareTo(new BigDecimal("2.85")) == 0);

	}

	@Test
	public void testPaymentStudent() {

		// when student paye for 15 days the amount is equal to 1.4 (0.1
		// *15days)
		assertTrue(studentPayment.paye(15).compareTo(new BigDecimal("1.5")) == 0);

		// when student paye for 30 days the amount is equal to 3.0 (0.1 * 30days)
		assertTrue(studentPayment.paye(30).compareTo(new BigDecimal("3.0")) == 0);

		// when student paye for 39 days the amount is equal to 4.45 ((0.1 *
		// 30days)+(0.15 * 9days))
		assertTrue(studentPayment.paye(39).compareTo(new BigDecimal("4.35")) == 0);

	}

	@Test
	public void testPaymentResident() {

		// when resident paye for 30 days the amount is equal to 3.0 (0.1 * 30days)
		assertTrue(residentPayment.paye(30).compareTo(new BigDecimal("3.0")) == 0);

		// when resident paye for 60 days the amount is equal to 6.0 (0.1 * 60days)
		assertTrue(residentPayment.paye(60).compareTo(new BigDecimal("6.0")) == 0);

		// when resident paye for 69 days the amount is equal to 7.8 ((0.1
		// * 60 days)+(0.2*9 )
		assertTrue(residentPayment.paye(69).compareTo(new BigDecimal("7.8")) == 0);

	}

}
