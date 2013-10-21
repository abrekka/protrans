package no.ugland.utransprod.dao.hibernate;

/**
 * Enum som brukes for å vite hvilken dato som skal brukes ved statistikk
 * @author atle.brekka
 *
 */
public enum DateEnum {
	/**
	 * Fakturadato
	 */
	INVOICE_DATE("INVOICE_DATE","Fakturadato"),
	/**
	 * Ordredato
	 */
	ORDER_DATE("ORDER_DATE","Ordredato"),
	/**
	 * Transportuke
	 */
	TRANSPORT_WEEK("TRANSPORT_WEEK","Transportuke");
	
	/**
	 * 
	 */
	String dateEnumString;
	/**
	 * 
	 */
	String toString;
	
	/**
	 * @param aDateEnumString
	 * @param aToString
	 */
	private DateEnum(String aDateEnumString,String aToString){
		dateEnumString=aDateEnumString;
		toString = aToString;
	}
	/**
	 * Henter ut strengrepresentasjon
	 * @return streng
	 */
	public String getDateEnumString(){
		return dateEnumString;
	}
	
	/**
	 * Finner enum basert på streng
	 * @param dateEnumString
	 * @return enum
	 */
	public static DateEnum getDateEnum(String dateEnumString){
		if(dateEnumString.equalsIgnoreCase("INVOICE_DATE")){
			return INVOICE_DATE;
		}else if(dateEnumString.equalsIgnoreCase("ORDER_DATE")){
			return ORDER_DATE;
		}else if(dateEnumString.equalsIgnoreCase("TRANSPORT_WEEK")){
			return TRANSPORT_WEEK;
		}
		return null;
	}
	
	/**
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString(){
		return toString;
	}
}
