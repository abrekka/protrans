package no.ugland.utransprod.model;

import java.util.Date;
import java.util.List;

import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.TextRenderable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view VEGG_PRODUCTION_V
 * @author atle.brekka
 */
public class VeggProductionV extends Production implements TextRenderable,
        Produceable {
    private static final long serialVersionUID = 1L;







    private String orderStatus;


    public VeggProductionV() {
        super();
    }

    public VeggProductionV(Integer orderLineId, Integer customerNr,
            String someCustomerDetails, String orderNr, String address,
            String someInfo, String constructionTypeName, String articleName,
            String someAttributeInfo, Integer numberOfItems, Date loadingDate,
            String someTransportDetails, String comment, Integer transportYear,
            Integer transportWeek, String loadTime, Integer postShipmentId,
            String productAreaGroupName, Date dateActionStarted,
            Date producedDate, Date productionDate, String productionUnitName,final String aOrderStatus,final Colli aColli) {
        super(orderLineId, customerNr, someCustomerDetails, orderNr, address, someInfo,
                constructionTypeName, articleName, someAttributeInfo, numberOfItems,
                loadingDate, someTransportDetails, comment, transportYear,
                transportWeek, loadTime, postShipmentId, productAreaGroupName,
                dateActionStarted, producedDate, productionDate, productionUnitName,aColli);
        this.orderStatus = aOrderStatus;
    }



   
    
    /**
     * @return ordreststatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Colli getColli() {
        return null;
    }

    public List<Applyable> getRelatedArticles() {
        return null;
    }

    public boolean isRelatedArticlesComplete() {
        return false;
    }

    public void setColli(Colli colli) {
    }

    public void setRelatedArticles(List<Applyable> relatedArticles) {
    }

	public boolean isRelatedArticlesStarted() {
		return false;
	}

	public Date getRelatedStartedDate() {
		return null;
	}

	public String getTrossDrawer() {
		return null;
	}

    
   }
