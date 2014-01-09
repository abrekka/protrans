package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.List;

import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.OrderLine;

import org.apache.commons.lang.StringUtils;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.ArrayListModel;

/**
 * GUI-modell for kolli
 * 
 * @author atle.brekka
 * 
 */
public class ColliModel extends AbstractModel<Colli, ColliModel> {
    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_COLLI_NAME = "colliName";
    public static final String PROPERTY_HEIGHT = "height";
    public static final String PROPERTY_LENGHT = "lenght";
    public static final String PROPERTY_WIDHT = "widht";

    public static final String PROPERTY_ORDER_LINES = "orderLines";

    public static final String PROPERTY_NUMBER_OF_COLLIES = "numberOfCollies";
    public static final String PROPERTY_COLLI_NAME_AND_NUMBER = "colliNameAndNumber";
    public static final String PROPERTY_COLLI_HEIGHT = "colliHeightWidhtLenght";

    private final ArrayListModel orderLineList;

    /**
     * @param colli
     */
    public ColliModel(Colli colli) {
	super(colli);

	orderLineList = new ArrayListModel();
	if (object.getOrderLines() != null) {
	    orderLineList.addAll(object.getOrderLines());
	}

    }

    public String getColliName() {
	return object.getColliName();
    }

    public void setColliName(String colliName) {
	String oldName = getColliName();
	object.setColliName(colliName);
	firePropertyChange(PROPERTY_COLLI_NAME, oldName, colliName);
    }

    public String getHeight() {
	return object.getHeight() == null ? null : object.getHeight().toString();
    }

    public void setHeight(String height) {
	String oldHeight = getHeight();
	object.setHeight(StringUtils.isEmpty(height) ? null : Integer.valueOf(height));
	firePropertyChange(PROPERTY_HEIGHT, oldHeight, height);
    }

    public String getWidht() {
	return object.getWidht() == null ? null : object.getWidht().toString();
    }

    public void setWidht(String widht) {
	String oldWidht = getWidht();
	object.setWidht(StringUtils.isEmpty(widht) ? null : Integer.valueOf(widht));
	firePropertyChange(PROPERTY_WIDHT, oldWidht, widht);
    }

    public String getLenght() {
	return object.getLenght() == null ? null : object.getLenght().toString();
    }

    public void setLenght(String lenght) {
	String oldLenght = getLenght();
	object.setLenght(StringUtils.isEmpty(lenght) ? null : Integer.valueOf(lenght));
	firePropertyChange(PROPERTY_LENGHT, oldLenght, lenght);
    }

    public String getColliNameAndNumber() {
	return object.getColliName() + (object.getNumberOfCollies() != null ? " - " + object.getNumberOfCollies() : "");
    }

    public String getColliHeightWidhtLenght() {
	return "L:" + (object.getLenght() == null ? "0" : object.getLenght()) + " B:" + (object.getWidht() == null ? "0" : object.getWidht()) + " H:"
		+ (object.getHeight() == null ? "0" : object.getHeight());
    }

    public String getNumberOfCollies() {
	return object.getNumberOfCollies() != null ? String.valueOf(object.getNumberOfCollies()) : null;
    }

    /**
     * @param colliName
     */
    public void setNumberOfCollies(String numberOfCollies) {
	String oldNumber = getNumberOfCollies();
	object.setNumberOfCollies(!StringUtils.isEmpty(numberOfCollies) ? Integer.valueOf(numberOfCollies) : null);
	firePropertyChange(PROPERTY_NUMBER_OF_COLLIES, oldNumber, numberOfCollies);
    }

    /**
     * @return ordrelinjer
     */
    @SuppressWarnings("unchecked")
    public List<OrderLine> getOrderLines() {
	return orderLineList;
    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
     *      com.jgoodies.binding.PresentationModel)
     */
    @Override
    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
	presentationModel.getBufferedModel(PROPERTY_COLLI_NAME).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_ORDER_LINES).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_HEIGHT).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_LENGHT).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_WIDHT).addValueChangeListener(listener);

    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
     */
    @Override
    public ColliModel getBufferedObjectModel(PresentationModel presentationModel) {
	ColliModel colliModel = new ColliModel(new Colli());
	colliModel.setColliName((String) presentationModel.getBufferedValue(PROPERTY_COLLI_NAME));
	colliModel.setHeight((String) presentationModel.getBufferedValue(PROPERTY_HEIGHT));
	colliModel.setLenght((String) presentationModel.getBufferedValue(PROPERTY_LENGHT));
	colliModel.setWidht((String) presentationModel.getBufferedValue(PROPERTY_WIDHT));
	return colliModel;
    }

    public void firePropertyChanged(String propertyName) {
	firePropertyChange(propertyName, null, getColliNameAndNumber());

    }

}
