package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.SupplierType;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for leverandørtype
 * 
 * @author atle.brekka
 * 
 */
public class SupplierTypeModel extends
		AbstractModel<SupplierType, SupplierTypeModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_SUPPLIER_TYPE_ID = "supplierTypeId";

	/**
	 * 
	 */
	public static final String PROPERTY_SUPPLIER_TYPE_NAME = "supplierTypeName";

	/**
	 * 
	 */
	public static final String PROPERTY_DESCRIPTION = "description";

	/**
	 * @param object
	 */
	public SupplierTypeModel(SupplierType object) {
		super(object);
	}

	/**
	 * @return id
	 */
	public Integer getSupplierTypeId() {
		return object.getSupplierTypeId();
	}

	/**
	 * @param supplierTypeId
	 */
	public void setSupplierTypeId(Integer supplierTypeId) {
		Integer oldId = getSupplierTypeId();
		object.setSupplierTypeId(supplierTypeId);
		firePropertyChange(PROPERTY_SUPPLIER_TYPE_ID, oldId, supplierTypeId);
	}

	/**
	 * @return typenavn
	 */
	public String getSupplierTypeName() {
		return object.getSupplierTypeName();
	}

	/**
	 * @param supplierTypeName
	 */
	public void setSupplierTypeName(String supplierTypeName) {
		String oldName = getSupplierTypeName();
		object.setSupplierTypeName(supplierTypeName);
		firePropertyChange(PROPERTY_SUPPLIER_TYPE_NAME, oldName,
				supplierTypeName);
	}

	/**
	 * @return beskrivelse
	 */
	public String getDescription() {
		return object.getDescription();
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		String oldDesc = getDescription();
		object.setDescription(description);
		firePropertyChange(PROPERTY_DESCRIPTION, oldDesc, description);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_SUPPLIER_TYPE_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_DESCRIPTION)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public SupplierTypeModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		SupplierTypeModel supplierTypeModel = new SupplierTypeModel(
				new SupplierType());
		supplierTypeModel.setSupplierTypeId((Integer) presentationModel
				.getBufferedValue(PROPERTY_SUPPLIER_TYPE_ID));
		supplierTypeModel.setSupplierTypeName((String) presentationModel
				.getBufferedValue(PROPERTY_SUPPLIER_TYPE_NAME));
		supplierTypeModel.setDescription((String) presentationModel
				.getBufferedValue(PROPERTY_DESCRIPTION));

		return supplierTypeModel;
	}

}
