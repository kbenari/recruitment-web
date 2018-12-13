package fr.d2factory.libraryapp.book;

public class Isbn {
  
	private long isbnCode;

    public Isbn(long isbnCode) {
        this.setIsbnCode(isbnCode);
    }

	public long getIsbnCode() {
		return isbnCode;
	}

	public void setIsbnCode(long isbnCode) {
		this.isbnCode = isbnCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (isbnCode ^ (isbnCode >>> 32));
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
		Isbn other = (Isbn) obj;
		if (isbnCode != other.isbnCode)
			return false;
		return true;
	}
	

}
