package no.ugland.utransprod.dao.hibernate;

/**
 * Enum som brukes for vite hvilken operator som skal brukes ved statistikk
 * 
 * @author atle.brekka
 * 
 */
public enum OperandEnum {
	/**
	 * 
	 */
	AND(" and ", "AND"), 
	/**
	 * 
	 */
	OR(" or ", "OR");

	/**
	 * 
	 */
	String operandString;

	/**
	 * 
	 */
	String operandEnumString;

	/**
	 * @param aOperandString
	 * @param enumString
	 */
	private OperandEnum(String aOperandString, String enumString) {
		operandString = aOperandString;
		operandEnumString = enumString;
	}

	/**
	 * Henter ut operatorstreng
	 * @return operatorstreng
	 */
	public String getOperandString() {
		return operandString;
	}

	/**
	 * Henter ut enumstreng
	 * @return enumstreng
	 */
	public String getOperandEnumString() {
		return operandEnumString;
	}

	/**
	 * Finner operatorenum basert p[ streng
	 * @param enumString
	 * @return operatorenum
	 */
	public static OperandEnum getOperandEnum(String enumString) {
		if (enumString.equalsIgnoreCase("AND")) {
			return AND;
		} else if (enumString.equalsIgnoreCase("OR")) {
			return OR;
		}
		return null;
	}
}
