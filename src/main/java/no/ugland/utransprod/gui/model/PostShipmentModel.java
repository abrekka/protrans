package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.ArrayListModel;

/**
 * GUI-modell for ettersending
 * @author atle.brekka
 */
public class PostShipmentModel extends AbstractOrderModel<PostShipment, PostShipmentModel>{ 
    private static final long serialVersionUID = 1L;
    public static final String PROPERTY_ORDER = "order";
    
    private String orderNr;
    public PostShipmentModel(PostShipment object) {
        super(object);
        
    }

    /**
     * @return ordre
     */
    public Order getOrder() {
        return object.getOrder();
    }

    /**
     * @param order
     */
    public void setOrder(Order order) {
        Order oldOrder = getOrder();
        object.setOrder(order);
        firePropertyChange(PROPERTY_ORDER, oldOrder, order);
    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
     *      com.jgoodies.binding.PresentationModel)
     */
    @Override
    public void addBufferChangeListener(PropertyChangeListener listener,
            PresentationModel presentationModel) {
        presentationModel.getBufferedModel(PROPERTY_COLLI_LIST)
                .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_ORDER)
                .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_ORDER_COMPLETE_BOOL)
                .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_ORDER_LINES)
                .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_ORDER_NR)
                .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_ORDER_READY_BOOL)
                .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_PACKED_BY)
                .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_GARAGE_COLLI_HEIGHT)
                .addValueChangeListener(listener);

    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
     */
    @Override
    public PostShipmentModel getBufferedObjectModel(
            PresentationModel presentationModel) {
        return null;
    }

    /**
     * Fyrer at alle egenskaper har endret seg
     */
    public void firePropertiesChanged() {
        fireMultiplePropertiesChanged();
    }

    /**
     * @param comments
     * @return kommentarer
     */
    public static List<OrderComment> getPostShipmentComments(
            List<OrderComment> comments) {
        if (comments != null) {
            List<OrderComment> postShipmentComments = new ArrayList<OrderComment>();
            for (OrderComment comment : comments) {
                if (comment.getDeviation() != null) {
                    postShipmentComments.add(comment);
                }
            }
            return postShipmentComments;
        }
        return null;

    }

    
    /**
     * @return kommentar
     */
    public String getComment() {
        return object.getComment();

    }

   

    /**
     * @see no.ugland.utransprod.gui.model.Packable#setColliesDone(java.lang.Integer)
     */
    public void setColliesDone(Integer colliesDone) {
    }

    /**
     * @see no.ugland.utransprod.gui.model.Packable#getOrderComplete()
     */
    public Date getOrderComplete() {
        return object.getPostShipmentComplete();
    }

    /**
     * @see no.ugland.utransprod.gui.model.Packable#getOrderReady()
     */
    public Date getOrderReady() {
        return object.getPostShipmentReady();
    }

   

    /**
     * @return ordrenummer
     */
    public String getOrderNr() {
        return orderNr;
    }

    /**
     * @param orderNr
     */
    public void setOrderNr(String orderNr) {
        String oldNr = getOrderNr();
        this.orderNr = orderNr;
        firePropertyChange(PROPERTY_ORDER_NR, oldNr, orderNr);
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getArticles()
     */
    public List<ArticleType> getArticles() {
        return object.getArticles();
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getCostList()
     */
    public ArrayListModel getCostList() {
        return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getCustomerFirstName()
     */
    public String getCustomerFirstName() {
        return object.getOrder().getCustomer().getFirstName();
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getCustomerLastName()
     */
    public String getCustomerLastName() {
        return object.getOrder().getCustomer().getLastName();
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getDeliveryAddress()
     */
    public String getDeliveryAddress() {
        return object.getOrder().getDeliveryAddress();
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getDeviation()
     */
    public Deviation getDeviation() {
        return object.getDeviation();
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getOrderLineList()
     */
    public ArrayListModel getOrderLineArrayListModel() {
        if (object.getOrderLines() != null) {
            return new ArrayListModel(object.getOrderLines());
        }
        return null;
    }
    
    public ArrayListModel getOrderLineList() {
        return getOrderLineArrayListModel();
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getPostOffice()
     */
    public String getPostOffice() {
        return object.getOrder().getPostOffice();
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getPostalCode()
     */
    public String getPostalCode() {
        return object.getOrder().getPostalCode();
    }

    
    public Transportable getTransportable() {
        return object;
    }

    public List<OrderLine> getOwnOrderLines() {
        List<OrderLine> orderLines = new ArrayList<OrderLine>();
        List<OrderLine> allOrderLines = getOrderLines();
        if(allOrderLines!=null){
            for(OrderLine orderLine:allOrderLines){
                if(orderLine.belongTo(object)){
                    orderLines.add(orderLine);
                }
            }
        }
        return orderLines;
    }

    @Override
    public String getManagerName() {
        return "postShipmentManager";
    }

    @Override
    public PostShipment getOrderModelPostShipment() {
        return object;
    }
    public Order getOrderModelOrder() {
        return null;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Enum[] getEnums() {
        return new LazyLoadPostShipmentEnum[] {
                LazyLoadPostShipmentEnum.COLLIES,
                LazyLoadPostShipmentEnum.ORDER_LINES,
                LazyLoadPostShipmentEnum.ORDER_COMMENTS};
    }

    @Override
    public Serializable getObjectId() {
        return object.getPostShipmentId();
    }

	public Integer getProbability() {
		return null;
	}

	@Override
	public Integer getDefaultColliesGenerated() {
		return object.getDefaultColliesGenerated();
	}

	public void setDefaultColliesGenerated(Integer defaultGenerated) {
		Integer oldGenerated = getDefaultColliesGenerated();
		object.setDefaultColliesGenerated(defaultGenerated);
		firePropertyChange(PROPERTY_DEFAULT_COLLIES_GENERATED, oldGenerated,
				defaultGenerated);
	}

	public PostShipment getPostShipment() {
		return object;
	}

	public Set<Colli> getCollies() {
		return object.getCollies();
	}

}
