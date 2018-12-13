/**
 * 
 */
package fr.d2factory.libraryapp.member;

import static fr.d2factory.libraryapp.library.Constants.RESIDENT_DURATION_LIMIT_PENALITY;

import java.math.BigDecimal;

import fr.d2factory.libraryapp.payment.ResidentPayment;

/**
 * @author kben
 *
 */
public class Resident extends Member {

	
	/**
	 * constructor using parameters
	 * 
	 * @param memberId
	 * @param wallet
	 */
	public Resident(String memberId, BigDecimal wallet) {

		this.memberId = memberId;
		this.wallet = wallet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.d2factory.libraryapp.member.Member#payBook(log)
	 */
	@Override
	public void payBook(long days) {
	
		wallet= this.getWallet().subtract( new ResidentPayment().paye(days));

	}

	@Override
	public boolean isLateDate(long value) {

		return 	value>= RESIDENT_DURATION_LIMIT_PENALITY;
	}
}
