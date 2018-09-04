package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import no.ugland.utransprod.model.Employee;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;

/**
 * Modellklasse for transport
 * @author atle.brekka
 *
 */
/**
 * @author atle.brekka
 * 
 */
public class TransportModel extends AbstractModel<Transport, TransportModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_TRANSPORT_ID = "transportId";

	/**
	 * 
	 */
	public static final String PROPERTY_TRANSPORT_NAME = "transportName";

	/**
	 * 
	 */
	public static final String PROPERTY_LOADING_DATE = "loadingDate";

	/**
	 * 
	 */
	public static final String PROPERTY_TRANSPORT_YEAR = "transportYear";

	/**
	 * 
	 */
	public static final String PROPERTY_TRANSPORT_WEEK = "transportWeek";

	/**
	 * 
	 */
	public static final String PROPERTY_ORDERS = "orders";

	/**
	 * 
	 */
	public static final String PROPERTY_SENT_BOOL = "sentBool";
	public static final String PROPERTY_LEVERT_BOOL = "levertBool";

	/**
	 * 
	 */
	public static final String PROPERTY_TRANSPORTABLES = "transportables";

	/**
	 * 
	 */
	public static final String PROPERTY_SUPPLIER = "supplier";

	/**
	 * 
	 */
	public static final String PROPERTY_EMPLOYEE = "employee";

	/**
	 * 
	 */
	public static final String PROPERTY_LOAD_TIME = "loadTime";
    public static final String PROPERTY_SENT_STRING = "sentString";
    public static final String PROPERTY_LEVERT_STRING = "levertString";
    public static final String PROPERTY_TROLLEY = "trolley";
    public static final String PROPERTY_TRANSPORT_COMMENT = "transportComment";

	/**
	 * @param transport
	 */
	public TransportModel(Transport transport) {
		super(transport);
	}

	/**
	 * @return id
	 */
	public Integer getTransportId() {
		return object.getTransportId();
	}

	/**
	 * @param transportId
	 */
	public void setTransportId(Integer transportId) {
		Integer oldId = getTransportId();
		object.setTransportId(transportId);
		firePropertyChange(PROPERTY_TRANSPORT_ID, oldId, transportId);
	}

	/**
	 * @return navn
	 */
	public String getTransportName() {
		return object.getTransportName();
	}

	/**
	 * @param transportName
	 */
	public void setTransportName(String transportName) {
		String oldName = getTransportName();
		object.setTransportName(transportName);
		firePropertyChange(PROPERTY_TRANSPORT_NAME, oldName, transportName);
	}

	/**
	 * @return opplastingsdato
	 */
	public Date getLoadingDate() {
		return Util.convertDate(object.getLoadingDate(),Util.SHORT_DATE_FORMAT);
	}

	/**
	 * @param loadingDate
	 */
	public void setLoadingDate(Date loadingDate) {
		Date oldDate = getLoadingDate();
		object.setLoadingDate(Util.convertDate(loadingDate,Util.SHORT_DATE_FORMAT));
		firePropertyChange(PROPERTY_LOADING_DATE, oldDate, loadingDate);
	}

	/**
	 * @return år
	 */
	public Integer getTransportYear() {
		if (object.getTransportYear() == null) {
			return 0;
		}
		return object.getTransportYear();
	}

	/**
	 * @param transportYear
	 */
	public void setTransportYear(Integer transportYear) {
		Integer oldYear = getTransportYear();
		object.setTransportYear(transportYear);
		firePropertyChange(PROPERTY_TRANSPORT_YEAR, oldYear, transportYear);
	}

	/**
	 * @return uke
	 */
	public Integer getTransportWeek() {
		return object.getTransportWeek();
	}

	/**
	 * @param transportWeek
	 */
	public void setTransportWeek(Integer transportWeek) {
		Integer oldWeek = getTransportWeek();
		object.setTransportWeek(transportWeek);
		firePropertyChange(PROPERTY_TRANSPORT_WEEK, oldWeek, transportWeek);
	}

	/**
	 * @return ordre
	 */
	public Set<Order> getOrders() {
		return object.getOrders();
	}

	/**
	 * @param orders
	 */
	public void setOrders(Set<Order> orders) {
		Set<Order> oldOrders = getOrders();
		object.setOrders(orders);
		firePropertyChange(PROPERTY_ORDERS, oldOrders, orders);
	}
    
    public String getTrolley() {
        return object.getTrolley();
    }

    public void setTrolley(final String aTrolley) {
        String oldTrolley = getTrolley();
        object.setTrolley(aTrolley);
        firePropertyChange(PROPERTY_TROLLEY, oldTrolley, aTrolley);
    }

	/**
	 * @return ordre
	 */
	public Set<Transportable> getTransportables() {
		Set<Transportable> transportables = new HashSet<Transportable>();
		if (object.getOrders() != null) {
			transportables.addAll(object.getOrders());
		}
		if (object.getPostShipments() != null) {
			transportables.addAll(object.getPostShipments());
		}
		return transportables;
	}
	
	public String getTransportComment(){
		return object.getTransportComment();
	}
	public void setTransportComment(String aComment){
		String oldcomment=getTransportComment();
		object.setTransportComment(aComment);
		firePropertyChange(PROPERTY_TRANSPORT_COMMENT, oldcomment, aComment);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_LOADING_DATE)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_ORDERS)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_TRANSPORT_ID)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_TRANSPORT_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_TRANSPORT_WEEK)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_TRANSPORT_YEAR)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_SENT_BOOL)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_LEVERT_BOOL)
		.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_SUPPLIER)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_LOAD_TIME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_EMPLOYEE)
				.addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_TROLLEY)
        .addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TransportModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		TransportModel transportModel = new TransportModel(new Transport());
		transportModel.setTransportId((Integer) presentationModel
				.getBufferedValue(PROPERTY_TRANSPORT_ID));
		transportModel.setTransportName((String) presentationModel
				.getBufferedValue(PROPERTY_TRANSPORT_NAME));
		transportModel.setTransportWeek((Integer) presentationModel
				.getBufferedValue(PROPERTY_TRANSPORT_WEEK));
		transportModel.setTransportYear((Integer) presentationModel
				.getBufferedValue(PROPERTY_TRANSPORT_YEAR));
		transportModel.setLoadingDate((Date) presentationModel
				.getBufferedValue(PROPERTY_LOADING_DATE));
		transportModel.setOrders((Set<Order>) presentationModel
				.getBufferedValue(PROPERTY_ORDERS));
		transportModel.setSentBool((Boolean) presentationModel
				.getBufferedValue(PROPERTY_SENT_BOOL));
		transportModel.setSentBool((Boolean) presentationModel
				.getBufferedValue(PROPERTY_LEVERT_BOOL));
		transportModel.setSupplier((Supplier) presentationModel
				.getBufferedValue(PROPERTY_SUPPLIER));
		transportModel.setLoadTime((String) presentationModel
				.getBufferedValue(PROPERTY_LOAD_TIME));
		transportModel.setEmployee((Employee) presentationModel
				.getBufferedValue(PROPERTY_EMPLOYEE));
        transportModel.setTrolley((String) presentationModel
                .getBufferedValue(PROPERTY_TROLLEY));
		return transportModel;
	}

	/**
	 * @return true dersom sendt
	 */
	public Boolean getSentBool() {
		if (object.getSent() != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	public Boolean getLevertBool() {
		if (object.getLevert() != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * @param sent
	 */
	public void setSentBool(Boolean sent) {
		Boolean oldBool = getSentBool();
		if (sent) {
			object.setSent(Calendar.getInstance().getTime());
            setSentString(Util.SHORT_DATE_FORMAT.format(object.getSent()));
		} else {
			object.setSent(null);
            setSentString(null);
		}
		firePropertyChange(PROPERTY_SENT_BOOL, oldBool, sent);
        
        
	}
	
	public void setLevertBool(Boolean levert) {
		Boolean oldBool = getLevertBool();
		if (levert) {
			object.setLevert(Calendar.getInstance().getTime());
            setLevertString(Util.SHORT_DATE_FORMAT.format(object.getLevert()));
		} else {
			object.setLevert(null);
            setLevertString(null);
		}
		firePropertyChange(PROPERTY_LEVERT_BOOL, oldBool, levert);
        
        
	}
    
    public final String getSentString() {
        if (object.getSent() != null) {
            return Util.SHORT_DATE_FORMAT.format(object.getSent());
        }
        return "";
    }
    public final String getLevertString() {
        if (object.getLevert() != null) {
            return Util.SHORT_DATE_FORMAT.format(object.getLevert());
        }
        return "";
    }
    public final void setSentString(String sentString) {
        String oldSent = getSentString();
        firePropertyChange(PROPERTY_SENT_STRING, oldSent, sentString);
    }
    public final void setLevertString(String levertString) {
        String oldLevert = getLevertString();
        firePropertyChange(PROPERTY_LEVERT_STRING, oldLevert, levertString);
    }

    

	/**
	 * @return leverandør
	 */
	public Supplier getSupplier() {
		return object.getSupplier();
	}

	/**
	 * @param supplier
	 */
	public void setSupplier(Supplier supplier) {
		Supplier oldSupplier = getSupplier();
		object.setSupplier(supplier);
		firePropertyChange(PROPERTY_SUPPLIER, oldSupplier, supplier);
	}

	/**
	 * @return sjåfør
	 */
	public Employee getEmployee() {
		return object.getEmployee();
	}

	/**
	 * @param employee
	 */
	public void setEmployee(Employee employee) {
		Employee oldEmployee = getEmployee();
		object.setEmployee(employee);
		firePropertyChange(PROPERTY_EMPLOYEE, oldEmployee, employee);
	}

	/**
	 * @return opplastingstid
	 */
	public String getLoadTime() {
		return object.getLoadTime();
	}

	/**
	 * @param loadTime
	 */
	public void setLoadTime(String loadTime) {
		String oldTime = getLoadTime();
		object.setLoadTime(loadTime);
		firePropertyChange(PROPERTY_LOAD_TIME, oldTime, loadTime);
	}

}
