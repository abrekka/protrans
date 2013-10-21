package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.SupplierProductAreaGroup;
import no.ugland.utransprod.model.SupplierType;
import no.ugland.utransprod.model.UserProductAreaGroup;
import no.ugland.utransprod.model.UserRole;
import no.ugland.utransprod.util.Util;

import com.google.common.collect.Lists;
import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for leverandør
 * 
 * @author atle.brekka
 * 
 */
public class SupplierModel extends AbstractModel<Supplier, SupplierModel> {
	private static final long serialVersionUID = 1L;

	public static final String PROPERTY_SUPPLIER_ID = "supplierId";

	public static final String PROPERTY_SUPPLIER_NAME = "supplierName";

	public static final String PROPERTY_SUPPLIER_DESCRIPTION = "supplierDescription";

	public static final String PROPERTY_PHONE = "phone";

	public static final String PROPERTY_FAX = "fax";

	public static final String PROPERTY_ADDRESS = "address";

	public static final String PROPERTY_POSTAL_CODE = "postalCode";

	public static final String PROPERTY_POST_OFFICE = "postOffice";

	public static final String PROPERTY_SUPPLIER_TYPE = "supplierType";
    public static final String PROPERTY_INACTIVE_BOOL = "inactiveBool";

	public static final String PROPERTY_SUPPLIER_PRODUCT_AREA_GROUPS_LIST = "supplierProductAreaGroupsList";

	private List<SupplierProductAreaGroup> supplierProductAreaGroups;
	/**
	 * @param object
	 */
	public SupplierModel(Supplier object) {
		super(object);
		
		supplierProductAreaGroups = new ArrayList<SupplierProductAreaGroup>();

		if (object.getSupplierProductAreaGroups() != null) {
			supplierProductAreaGroups.addAll(object.getSupplierProductAreaGroups());
		}
	}

	/**
	 * @return id
	 */
	public Integer getSupplierId() {
		return object.getSupplierId();
	}

	/**
	 * @param supplierId
	 */
	public void setSupplierId(Integer supplierId) {
		Integer oldId = getSupplierId();
		object.setSupplierId(supplierId);
		firePropertyChange(PROPERTY_SUPPLIER_ID, oldId, supplierId);
	}

	/**
	 * @return leverandørnavn
	 */
	public String getSupplierName() {
		return object.getSupplierName();
	}

	/**
	 * @param supplierName
	 */
	public void setSupplierName(String supplierName) {
		String oldName = getSupplierName();
		object.setSupplierName(supplierName);
		firePropertyChange(PROPERTY_SUPPLIER_NAME, oldName, supplierName);
	}

	/**
	 * @return beskrivelse
	 */
	public String getSupplierDescription() {
		return object.getSupplierDescription();
	}

	/**
	 * @param supplierDescription
	 */
	public void setSupplierDescription(String supplierDescription) {
		String oldDesc = getSupplierDescription();
		object.setSupplierDescription(supplierDescription);
		firePropertyChange(PROPERTY_SUPPLIER_DESCRIPTION, oldDesc,
				supplierDescription);
	}

	/**
	 * @return telefon
	 */
	public String getPhone() {
		return object.getPhone();
	}

	/**
	 * @param phone
	 */
	public void setPhone(String phone) {
		String oldPhone = getPhone();
		object.setPhone(phone);
		firePropertyChange(PROPERTY_PHONE, oldPhone, phone);
	}

	/**
	 * @return fax
	 */
	public String getFax() {
		return object.getFax();
	}

	/**
	 * @param fax
	 */
	public void setFax(String fax) {
		String oldFax = getFax();
		object.setFax(fax);
		firePropertyChange(PROPERTY_FAX, oldFax, fax);
	}

	/**
	 * @return adresse
	 */
	public String getAddress() {
		return object.getAddress();
	}

	/**
	 * @param address
	 */
	public void setAddress(String address) {
		String oldAddress = getAddress();
		object.setAddress(address);
		firePropertyChange(PROPERTY_ADDRESS, oldAddress, address);
	}

	/**
	 * @return postnummer
	 */
	public String getPostalCode() {
		return object.getPostalCode();
	}

	/**
	 * @param postalCode
	 */
	public void setPostalCode(String postalCode) {
		String oldCode = getPostalCode();
		object.setPostalCode(postalCode);
		firePropertyChange(PROPERTY_POSTAL_CODE, oldCode, postalCode);
	}

	/**
	 * @return poststed
	 */
	public String getPostOffice() {
		return object.getPostOffice();
	}

	/**
	 * @param postOffice
	 */
	public void setPostOffice(String postOffice) {
		String oldOffice = getPostOffice();
		object.setPostOffice(postOffice);
		firePropertyChange(PROPERTY_POST_OFFICE, oldOffice, postOffice);
	}

	/**
	 * @return leverandørtype
	 */
	public SupplierType getSupplierType() {
		return object.getSupplierType();
	}

	/**
	 * @param supplierType
	 */
	public void setSupplierType(SupplierType supplierType) {
		SupplierType oldType = getSupplierType();
		object.setSupplierType(supplierType);
		firePropertyChange(PROPERTY_SUPPLIER_TYPE, oldType, supplierType);
	}
    public Boolean getInactiveBool() {
        return Util.convertNumberToBoolean(object.getInactive());
    }

    /**
     * @param supplierType
     */
    public void setInactiveBool(Boolean isInactive) {
        Boolean oldInactive = getInactiveBool();
        object.setInactive(Util.convertBooleanToNumber(isInactive));
        firePropertyChange(PROPERTY_INACTIVE_BOOL, oldInactive, isInactive);
    }
    
    public List<SupplierProductAreaGroup> getSupplierProductAreaGroupsList(){
    	return new ArrayList<SupplierProductAreaGroup>(supplierProductAreaGroups);
    }
    
    public void setSupplierProductAreaGroupsList(
			List<SupplierProductAreaGroup> supplierProductAreaGroupList) {
		List<SupplierProductAreaGroup> oldGroups = getSupplierProductAreaGroupsList();
		if (supplierProductAreaGroupList != null) {
			this.supplierProductAreaGroups = new ArrayList<SupplierProductAreaGroup>(
					supplierProductAreaGroupList);
		} else {
			this.supplierProductAreaGroups.clear();
		}
		firePropertyChange(PROPERTY_SUPPLIER_PRODUCT_AREA_GROUPS_LIST, oldGroups,
				supplierProductAreaGroupList);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_SUPPLIER_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_SUPPLIER_DESCRIPTION)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PHONE)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_FAX)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_ADDRESS)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_POSTAL_CODE)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_POST_OFFICE)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_SUPPLIER_TYPE)
				.addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_INACTIVE_BOOL)
        .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_SUPPLIER_PRODUCT_AREA_GROUPS_LIST)
        .addValueChangeListener(listener);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public SupplierModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		SupplierModel supplierModel = new SupplierModel(new Supplier());
		supplierModel.setSupplierId((Integer) presentationModel
				.getBufferedValue(PROPERTY_SUPPLIER_ID));
		supplierModel.setSupplierName((String) presentationModel
				.getBufferedValue(PROPERTY_SUPPLIER_NAME));
		supplierModel.setSupplierDescription((String) presentationModel
				.getBufferedValue(PROPERTY_SUPPLIER_DESCRIPTION));
		supplierModel.setPhone((String) presentationModel
				.getBufferedValue(PROPERTY_PHONE));
		supplierModel.setFax((String) presentationModel
				.getBufferedValue(PROPERTY_FAX));
		supplierModel.setAddress((String) presentationModel
				.getBufferedValue(PROPERTY_ADDRESS));
		supplierModel.setPostalCode((String) presentationModel
				.getBufferedValue(PROPERTY_POSTAL_CODE));
		supplierModel.setPostOffice((String) presentationModel
				.getBufferedValue(PROPERTY_POST_OFFICE));
		supplierModel.setSupplierType((SupplierType) presentationModel
				.getBufferedValue(PROPERTY_SUPPLIER_TYPE));
        supplierModel.setInactiveBool((Boolean) presentationModel
                .getBufferedValue(PROPERTY_INACTIVE_BOOL));
        supplierModel.setSupplierProductAreaGroupsList((List<SupplierProductAreaGroup>) presentationModel
                .getBufferedValue(PROPERTY_SUPPLIER_PRODUCT_AREA_GROUPS_LIST));

		return supplierModel;
	}

	public void viewToModel() {
	
		if (supplierProductAreaGroups != null) {
			Set<SupplierProductAreaGroup> groups = object
					.getSupplierProductAreaGroups();
			if (groups == null) {
				groups = new HashSet<SupplierProductAreaGroup>();
			}
			groups.clear();
			groups.addAll(supplierProductAreaGroups);
			object.setSupplierProductAreaGroups(groups);
		}
	}
}
