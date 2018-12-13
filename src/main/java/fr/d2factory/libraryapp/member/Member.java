package fr.d2factory.libraryapp.member;

import java.math.BigDecimal;

import fr.d2factory.libraryapp.library.Library;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
	
	


	/**
	 * Member id 
	 */
	protected String memberId;
    
	/**
     * An initial sum of money the member has
     */
    protected BigDecimal wallet;

   
    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract void payBook(long numberOfDays);

    public abstract boolean isLateDate(long daysduration);
    
    
    public BigDecimal getWallet() {
        return wallet;
    }



	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String idMember) {
		memberId = idMember;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		return true;
	}
}
