package no.ugland.utransprod.model;

import java.util.List;

/**
 * Interface for attributter som skal vises i editeringsvindu
 * 
 * @author atle.brekka
 * 
 */
public interface IArticleAttribute {
	/**
	 * Henter attributtnavn
	 * 
	 * @return navn
	 */
	String getAttributeName();

	/**
	 * Henter attributtverdi
	 * 
	 * @return verdi
	 */
	String getAttributeValue();

	/**
	 * Setter attributtverdi
	 * 
	 * @param aValue
	 */
	void setAttributeValue(String aValue);

	/**
	 * Henter navn på artikkel
	 * 
	 * @return navn på artikkel
	 */
	String getArticleName();

	/**
	 * Henter antall
	 * 
	 * @return antall
	 */
	Integer getNumberOfItems();

	/**
	 * Setter antall
	 * 
	 * @param numberOfItems
	 */
	void setNumberOfItems(Integer numberOfItems);

	/**
	 * @return antall som Long
	 */
	Long getNumberOfItemsLong();

	/**
	 * Setter antall
	 * 
	 * @param numberOfItems
	 */
	void setNumberOfItemsLong(Long numberOfItems);

	/**
	 * @return true dersom attributt er valg ja/nei
	 */
	Boolean isYesNo();

	/**
	 * @return valg
	 */
	List<String> getChoices();

	/**
	 * @param bool
	 */
	Boolean setAttributeValueBool(Boolean bool);

	/**
	 * @return attributtverdi som boolsk verdi
	 */
	Boolean getAttributeValueBool();

	/**
	 * @param dialogOrder
	 */
	void setDialogOrderArticle(Integer dialogOrder);

	/**
	 * @return rekkefølge for artikkel
	 */
	Integer getDialogOrderArticle();

	/**
	 * @param dialogOrder
	 */
	void setDialogOrderAttribute(Integer dialogOrder);

	/**
	 * @return rekkefølge for attributt
	 */
	Integer getDialogOrderAttribute();

	/**
	 * @return ordrelinje
	 */
	OrderLine getOrderLine();

	/**
	 * Oppretter ny instans av implementasjon av dette interfacet
	 * 
	 * @return ny instans
	 */
	IArticleAttribute getNewInstance();

    String getAttributeDataType();
}
