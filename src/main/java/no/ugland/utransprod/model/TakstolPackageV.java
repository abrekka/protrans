package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.TextRenderable;

/**
 * Domeneklasse for view TAKSTOL_PACKAGE_V
 * 
 * @author atle.brekka
 */
public class TakstolPackageV extends PackageProduction implements TextRenderable, PackableListItem, TakstolV {
    private static final long serialVersionUID = 1L;

    private List<Applyable> relatedArticles;

    private Integer defaultArticle;

    private Integer purcno;

    private Integer rest;
    private Integer productionWeek;

    public TakstolPackageV() {
	super();
    }

    public TakstolPackageV(final Integer aOrderLineId, final Integer aCustomerNr, final String someCustomerDetails, final String aOrderNr,
	    final String aAddress, final String someInfo, final String aConstructionTypeName, final String aArticleName,
	    final String someAttributeInfo, final Integer aNumberOfItems, final Date aLoadingDate, final Colli aColli,
	    final String someTransportDetails, final String aComment, final Integer aTransportYear, final Integer aTransportWeek,
	    final String aLoadTime, final Integer aPostShipmentId, final String aProductAreaGroupName, final Date actionStartedDate) {
	super(aOrderLineId, aCustomerNr, someCustomerDetails, aOrderNr, aAddress, someInfo, aConstructionTypeName, aArticleName, someAttributeInfo,
		aNumberOfItems, aLoadingDate, someTransportDetails, aComment, aTransportYear, aTransportWeek, aLoadTime, aPostShipmentId,
		aProductAreaGroupName, actionStartedDate, aColli);
    }
    
    public Integer getProductionWeek(){
    	return productionWeek;
    }
    
    public void setProductionWeek(Integer productionWeek) {
		this.productionWeek = productionWeek;
	}

    public List<Applyable> getRelatedArticles() {
	return relatedArticles;
    }

    public void setRelatedArticles(List<Applyable> someRelatedArticles) {
	relatedArticles = someRelatedArticles;

    }

    public boolean isRelatedArticlesComplete() {
	return relatedArticles == null || relatedArticles.size() == 0 ? true : checkIfIsComplete();
    }

    private boolean checkIfIsComplete() {
	for (Applyable item : relatedArticles) {
	    if (item.getColli() == null) {
		return false;
	    }
	}
	return true;
    }

    public Date getProduced() {
	// TODO Auto-generated method stub
	return null;
    }

    public void setProduced(Date produced) {
	// TODO Auto-generated method stub

    }

    @Override
    public String toString() {
	return articleName + " " + numberOfItems;
    }

    public TakstolPackageV clone() {
	TakstolPackageV tmpObject = new TakstolPackageV();
	tmpObject.setActionStarted(this.getActionStarted());
	tmpObject.setAddress(this.getAddress());
	tmpObject.setArticleName(this.getArticleName());
	// tmpObject.setOrderLineId(this.getOrderLineId());
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

    public void addRelatedArticle(PackableListItem item) {
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
	    if (item.getColli() != null) {
		return true;
	    }
	}
	return false;
    }

    public String getProductionUnitName() {
	// TODO Auto-generated method stub
	return null;
    }

    public BigDecimal getRealProductionHours() {
	// TODO Auto-generated method stub
	return null;
    }

    public Integer getPurcno() {
	return purcno;
    }

    public Integer getRest() {
	return rest;
    }

    public void setPurcno(Integer purcno) {
	this.purcno = purcno;
    }

    public void setRest(Integer rest) {
	this.rest = rest;
    }


    public Date getPacklistReady() {
	// TODO Auto-generated method stub
	return null;
    }

    public Integer getDoAssembly() {
	// TODO Auto-generated method stub
	return null;
    }

    public Integer getAssemblyWeek() {
	// TODO Auto-generated method stub
	return null;
    }
}
