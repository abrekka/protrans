package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.TextRenderable;

/**
 * Domeneklasse for view TAKSTOL_PRODUCTION_V
 * 
 * @author atle.brekka
 */
public class TakstolProductionV extends Production implements TextRenderable, Produceable, TakstolV {
    private static final long serialVersionUID = 1L;
    private Integer defaultArticle;

    private List<Applyable> relatedArticles;
    private String trossDrawer;
    private Date cuttingStarted;
    private Date cuttingDone;

    public TakstolProductionV() {
	super();
    }

    public TakstolProductionV(Integer orderLineId, Integer customerNr, String someCustomerDetails, String orderNr, String address, String someInfo,
	    String constructionTypeName, String articleName, String someAttributeInfo, Integer numberOfItems, Date loadingDate,
	    String someTransportDetails, String comment, Integer transportYear, Integer transportWeek, String loadTime, Integer postShipmentId,
	    String productAreaGroupName, Date dateActionStarted, Date producedDate, Date productionDate, String productionUnitName, Colli aColli) {
	super(orderLineId, customerNr, someCustomerDetails, orderNr, address, someInfo, constructionTypeName, articleName, someAttributeInfo,
		numberOfItems, loadingDate, someTransportDetails, comment, transportYear, transportWeek, loadTime, postShipmentId,
		productAreaGroupName, dateActionStarted, producedDate, productionDate, productionUnitName, aColli);
    }

    public boolean isRelatedArticlesComplete() {
	return relatedArticles == null || relatedArticles.size() == 0 ? true : checkIfIsComplete();
    }

    public List<Applyable> getRelatedArticles() {
	return relatedArticles;
    }

    public void setRelatedArticles(List<Applyable> someRelatedArticles) {
	relatedArticles = someRelatedArticles;

    }

    private boolean checkIfIsComplete() {
	for (Applyable item : relatedArticles) {
	    if (item.getProduced() == null) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public String toString() {
	return articleName + " " + numberOfItems;
    }

    public TakstolProductionV clone() {
	TakstolProductionV tmpObject = new TakstolProductionV();
	tmpObject.setActionStarted(this.getActionStarted());
	tmpObject.setAddress(this.getAddress());
	tmpObject.setArticleName(this.getArticleName());

	tmpObject.setComment(this.getComment());
	tmpObject.setConstructionTypeName(this.getConstructionTypeName());
	tmpObject.setCustomerDetails(this.getCustomerDetails());
	tmpObject.setCustomerNr(this.getCustomerNr());
	tmpObject.setInfo(this.getInfo());
	tmpObject.setLoadingDate(this.getLoadingDate());
	tmpObject.setLoadTime(this.getLoadTime());
	tmpObject.setOrderNr(this.getOrderNr());
	tmpObject.setTransportDetails(this.getTransportDetails());
	tmpObject.setTransportWeek(this.getTransportWeek());
	tmpObject.setTransportYear(this.getTransportYear());
	tmpObject.setProductAreaGroupName(this.getProductAreaGroupName());
	tmpObject.setProduced(this.getProduced());
	tmpObject.setColli(this.getColli());
	return tmpObject;

    }

    public void addRelatedArticle(Produceable item) {
	relatedArticles = relatedArticles == null ? new ArrayList<Applyable>() : relatedArticles;
	relatedArticles.add(item);

    }

    public Integer getDefaultArticle() {
	return defaultArticle;
    }

    public void setDefaultArticle(Integer defaultArticle) {
	this.defaultArticle = defaultArticle;
    }

    public boolean isRelatedArticlesStarted() {
	return relatedArticles == null || relatedArticles.size() == 0 ? false : checkIfIsStarted();
    }

    private boolean checkIfIsStarted() {
	for (Applyable item : relatedArticles) {
	    if (item.getProduced() != null) {
		return true;
	    }
	}
	return false;
    }

    public Date getRelatedStartedDate() {
	return actionStarted != null ? actionStarted : getRelatedArticleStartedDate();
    }

    private Date getRelatedArticleStartedDate() {
	if (relatedArticles != null) {
	    for (Applyable item : relatedArticles) {
		if (((Produceable) item).getActionStarted() != null) {
		    return ((Produceable) item).getActionStarted();
		}
	    }
	}
	return null;
    }

    public String getTrossDrawer() {
	return trossDrawer;
    }

    public void setTrossDrawer(String trossDrawer) {
	this.trossDrawer = trossDrawer;
    }

    public Date getCuttingStarted() {
	return cuttingStarted;
    }

    public void setCuttingStarted(Date cuttingStarted) {
	this.cuttingStarted = cuttingStarted;
    }

    public Date getCuttingDone() {
	return cuttingDone;
    }

    public void setCuttingDone(Date cuttingDone) {
	this.cuttingDone = cuttingDone;
    }

    public BigDecimal getRealProductionHours() {
	// TODO Auto-generated method stub
	return null;
    }

}
