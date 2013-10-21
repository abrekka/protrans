package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.Set;

import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.model.ExternalOrderLine;
import no.ugland.utransprod.model.Supplier;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for ekstern ordre
 * 
 * @author atle.brekka
 * 
 */
public class ExternalOrderModel extends
		AbstractModel<ExternalOrder, ExternalOrderModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_SUPPLIER = "supplier";

	/**
	 * 
	 */
	public static final String PROPERTY_EXTERNAL_ORDER_NR = "externalOrderNr";

	/**
	 * 
	 */
	public static final String PROPERTY_DELIVERY_DATE = "deliveryDate";

	/**
	 * 
	 */
	public static final String PROPERTY_EXTERNAL_ORDER_LINES = "externalOrderLines";

	/**
	 * 
	 */
	public static final String PROPERTY_ATT = "att";

	/**
	 * 
	 */
	public static final String PROPERTY_OUR_REF = "ourRef";

	/**
	 * 
	 */
	public static final String PROPERTY_MARKED_WITH = "markedWith";

	/**
	 * 
	 */
	public static final String PROPERTY_PHONE_NR = "phoneNr";

	/**
	 * 
	 */
	public static final String PROPERTY_FAX_NR = "faxNr";

	/**
	 * @param object
	 */
	public ExternalOrderModel(ExternalOrder object) {
		super(object);
	}

	/**
	 * @return faxnummer
	 */
	public String getFaxNr() {
		return object.getFaxNr();
	}

	/**
	 * @param faxNr
	 */
	public void setFaxNr(String faxNr) {
		String oldfax = getFaxNr();
		object.setFaxNr(faxNr);
		firePropertyChange(PROPERTY_FAX_NR, oldfax, faxNr);
	}

	/**
	 * @return telefonnummer
	 */
	public String getPhoneNr() {
		return object.getPhoneNr();
	}

	/**
	 * @param phoneNr
	 */
	public void setPhoneNr(String phoneNr) {
		String oldPhone = getPhoneNr();
		object.setPhoneNr(phoneNr);
		firePropertyChange(PROPERTY_PHONE_NR, oldPhone, phoneNr);
	}

	/**
	 * @return att
	 */
	public String getAtt() {
		return object.getAtt();
	}

	/**
	 * @param att
	 */
	public void setAtt(String att) {
		String oldAtt = getAtt();
		object.setAtt(att);
		firePropertyChange(PROPERTY_ATT, oldAtt, att);
	}

	/**
	 * @return vår referanse
	 */
	public String getOurRef() {
		return object.getOurRef();
	}

	/**
	 * @param ourRef
	 */
	public void setOurRef(String ourRef) {
		String oldRef = getOurRef();
		object.setOurRef(ourRef);
		firePropertyChange(PROPERTY_OUR_REF, oldRef, ourRef);
	}

	/**
	 * @return merket med
	 */
	public String getMarkedWith() {
		return object.getMarkedWith();
	}

	/**
	 * @param markedWith
	 */
	public void setMarkedWith(String markedWith) {
		String oldMarked = getMarkedWith();
		object.setMarkedWith(markedWith);
		firePropertyChange(PROPERTY_MARKED_WITH, oldMarked, markedWith);
	}

	/**
	 * @return ordrelinjer
	 */
	public Set<ExternalOrderLine> getExternalOrderLines() {
		return object.getExternalOrderLines();
	}

	/**
	 * @param externalOrderLines
	 */
	public void setExternalOrderLines(Set<ExternalOrderLine> externalOrderLines) {
		Set<ExternalOrderLine> oldLines = getExternalOrderLines();
		object.setExternalOrderLines(externalOrderLines);
		firePropertyChange(PROPERTY_EXTERNAL_ORDER_LINES, oldLines,
				externalOrderLines);
	}

	/**
	 * @return leveringsdato
	 */
	public Date getDeliveryDate() {
		return object.getDeliveryDate();
	}

	/**
	 * @param deliveryDate
	 */
	public void setDeliveryDate(Date deliveryDate) {
		Date oldDate = getDeliveryDate();
		object.setDeliveryDate(deliveryDate);
		firePropertyChange(PROPERTY_DELIVERY_DATE, oldDate, deliveryDate);
	}

	/**
	 * @return ordrenummer
	 */
	public String getExternalOrderNr() {
		return object.getExternalOrderNr();
	}

	/**
	 * @param externalOrderNr
	 */
	public void setExternalOrderNr(String externalOrderNr) {
		String oldNr = getExternalOrderNr();
		object.setExternalOrderNr(externalOrderNr);
		firePropertyChange(PROPERTY_EXTERNAL_ORDER_NR, oldNr, externalOrderNr);
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
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_SUPPLIER)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_DELIVERY_DATE)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_EXTERNAL_ORDER_NR)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_EXTERNAL_ORDER_LINES)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_ATT)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_OUR_REF)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_MARKED_WITH)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PHONE_NR)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_FAX_NR)
				.addValueChangeListener(listener);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExternalOrderModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		ExternalOrderModel model = new ExternalOrderModel(new ExternalOrder());
		model.setSupplier((Supplier) presentationModel
				.getBufferedValue(PROPERTY_SUPPLIER));
		model.setDeliveryDate((Date) presentationModel
				.getBufferedValue(PROPERTY_DELIVERY_DATE));
		model.setExternalOrderNr((String) presentationModel
				.getBufferedValue(PROPERTY_EXTERNAL_ORDER_NR));
		model.setExternalOrderLines((Set<ExternalOrderLine>) presentationModel
				.getBufferedValue(PROPERTY_EXTERNAL_ORDER_LINES));
		model.setAtt((String) presentationModel.getBufferedValue(PROPERTY_ATT));
		model.setOurRef((String) presentationModel
				.getBufferedValue(PROPERTY_OUR_REF));
		model.setMarkedWith((String) presentationModel
				.getBufferedValue(PROPERTY_MARKED_WITH));
		model.setPhoneNr((String) presentationModel
				.getBufferedValue(PROPERTY_PHONE_NR));
		model.setFaxNr((String) presentationModel
				.getBufferedValue(PROPERTY_FAX_NR));
		return model;
	}

}
