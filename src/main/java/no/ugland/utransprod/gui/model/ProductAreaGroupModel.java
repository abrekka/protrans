package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.ProductAreaGroup;

import com.jgoodies.binding.PresentationModel;

/**
 * Modellklasse for produktområdegruppe
 * 
 * @author atle.brekka
 * 
 */
public class ProductAreaGroupModel extends
		AbstractModel<ProductAreaGroup, ProductAreaGroupModel> {
	/**
	 * @param object
	 */
	public ProductAreaGroupModel(ProductAreaGroup object) {
		super(object);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_PRODUCT_AREA_GROUP = "productAreaGroup";

	/**
	 * @return produktområdegruppe
	 */
	public ProductAreaGroup getProductAreaGroup() {
		return object;
	}

	/**
	 * @param group
	 */
	public void setProductAreaGroup(ProductAreaGroup group) {
		ProductAreaGroup oldGroup = getProductAreaGroup();
		object = group;
		firePropertyChange(PROPERTY_PRODUCT_AREA_GROUP, oldGroup, group);
	}

	/**
	 * Gjør ingenting
	 * 
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
	}

	/**
	 * Gjør ingenting
	 * 
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public ProductAreaGroupModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		return null;
	}
}