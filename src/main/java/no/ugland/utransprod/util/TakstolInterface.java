package no.ugland.utransprod.util;

import java.util.List;

import no.ugland.utransprod.model.Produceable;

public interface TakstolInterface {

	String getOrderNr();

	void setRelatedArticles(List<TakstolInterface> relatedArticles);

	String getArticleName();

	void addRelatedArticle(TakstolInterface item);

	String getAttributeInfo();

	void setAttributeInfo(String string);

	TakstolInterface clone();

	void setArticleName(String mainArticleName);

	Integer getOrderLineId();

}
