package no.ugland.utransprod.model;

/**
 * Interface for artikler (ConstructionTypeArticle og OrderLine)
 * 
 * @author atle.brekka
 * 
 */
public interface IArticle {
	/**
	 * @return artikkelnavn
	 */
	String getArticleName();

	/**
	 * @param articleName
	 */
	void setArticleName(String articleName);

	/**
	 * @return antall som long
	 */
	Long getNumberOfItemsLong();

	/**
	 * @param numberOfItems
	 */
	void setNumberOfItemsLong(Long numberOfItems);

	/**
	 * @return rekkefølge
	 */
	Integer getDialogOrder();

	/**
	 * @param dialogOrder
	 */
	void setDialogOrder(Integer dialogOrder);

	/**
	 * @return betegnelse
	 */
	String getMetric();

	/**
	 * Oppretter instans av klasse som er implementasjon av dette interfacet
	 * 
	 * @return ny instans
	 */
	IArticle getNewInstance();
}
