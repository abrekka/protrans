package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import com.jgoodies.binding.beans.Observable;

import no.ugland.utransprod.model.OrderLineAttribute;

/**
 * Klasse som arver fra ordrelinjeattributt for å brukes som kriterie ved artikkelstatistikk
 * @author atle.brekka
 *
 */
public class OrderLineAttributeCriteria extends OrderLineAttribute implements Observable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static final String PROPERTY_ATTRIBUTE_VALUE_BOOLEAN="attributeValueBoolean";
	/**
	 * 
	 */
	public static final String PROPERTY_ATTRIBUTE_VALUE_FROM="attributeValueFrom";
	/**
	 * 
	 */
	public static final String PROPERTY_ATTRIBUTE_VALUE_TO="attributeValueTo";
	/**
	 * 
	 */
	public static final String PROPERTY_ATTRIBUTE_NAME="attributeName";
	/**
	 * 
	 */
	private String attributeValueFrom;
	/**
	 * 
	 */
	private String attributeValueTo;
	
	/**
	 * @return verdi fra
	 */
	public String getAttributeValueFrom() {
		return attributeValueFrom;
	}
	/**
	 * @param attributeValueFrom
	 */
	public void setAttributeValueFrom(String attributeValueFrom) {
		this.attributeValueFrom = attributeValueFrom;
	}
	/**
	 * @return verdi til
	 */
	public String getAttributeValueTo() {
		return attributeValueTo;
	}
	/**
	 * @param attributeValueTo
	 */
	public void setAttributeValueTo(String attributeValueTo) {
		this.attributeValueTo = attributeValueTo;
	}
	
	/**
	 * @return verdi som boolsk verdi
	 */
	public Boolean getAttributeValueBoolean() {
		if(attributeValueFrom !=null && attributeValueFrom.equalsIgnoreCase("Ja")){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	/**
	 * @param bool
	 */
	public void setAttributeValueBoolean(Boolean bool) {
		if(bool){
			this.attributeValueFrom = "Ja";
		}else{
			this.attributeValueFrom = "Nei";
		}
	}
    public void addPropertyChangeListener(PropertyChangeListener arg0) {
        // TODO Auto-generated method stub
        
    }
    public void removePropertyChangeListener(PropertyChangeListener arg0) {
        // TODO Auto-generated method stub
        
    }


}
