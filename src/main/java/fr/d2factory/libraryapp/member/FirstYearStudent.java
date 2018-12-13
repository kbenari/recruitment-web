package fr.d2factory.libraryapp.member;

import static fr.d2factory.libraryapp.library.Constants.STUDENT_DURATION_LIMIT_PENALITY;

import java.math.BigDecimal;

import fr.d2factory.libraryapp.payment.FirstYearStudentPayment;

public class FirstYearStudent extends Member {

	public FirstYearStudent(String memberId, BigDecimal wallet) {
		this.memberId = memberId;
		this.wallet = wallet;
	}

	@Override
	public void payBook(long days) {
		new FirstYearStudentPayment().paye(days);

	}

	@Override
	public boolean isLateDate(long value) {

		return value >= STUDENT_DURATION_LIMIT_PENALITY;
	}

}
