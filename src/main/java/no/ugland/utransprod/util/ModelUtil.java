package no.ugland.utransprod.util;

import java.math.BigDecimal;

import no.ugland.utransprod.service.impl.BaseManagerImpl;

/**
 * Hjelpeklasse for å hente ut DAO-klasser.
 * 
 * @author abr99
 */
public final class ModelUtil {
	private ModelUtil() {

	}

	/**
	 * Gjør om null-verdier til tom streng.
	 * 
	 * @param object
	 * @return tom streng dersom null, ellers object.toString()
	 */
	public static String nullToString(final Object object) {
		if (object == null) {
			return "";
		}
		return object.toString();
	}

	/**
	 * Formaterer BigDecimal.
	 * 
	 * @param number
	 * @return formatert BigDecimal
	 */
	public static BigDecimal formatBigDecimal(final BigDecimal number) {
		if (number != null) {
			return number.setScale(5, BigDecimal.ROUND_HALF_UP);
		}
		return null;
	}

	/**
	 * Henter ut bean fra applicationContext basert på navn.
	 * 
	 * @param beanName
	 * @return bean
	 */
	public static Object getBean(final String beanName) {
		return BaseManagerImpl.getApplicationContext().getBean(beanName);
	}

}
