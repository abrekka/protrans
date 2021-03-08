/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.jgoodies.binding.beans.Observable;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import no.ugland.utransprod.model.OrderLineAttribute;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OrderLineAttributeCriteria extends OrderLineAttribute implements Observable {
/*    */    private static final long serialVersionUID = 1L;
/*    */    public static final String PROPERTY_ATTRIBUTE_VALUE_BOOLEAN = "attributeValueBoolean";
/*    */    public static final String PROPERTY_ATTRIBUTE_VALUE_FROM = "attributeValueFrom";
/*    */    public static final String PROPERTY_ATTRIBUTE_VALUE_TO = "attributeValueTo";
/*    */    public static final String PROPERTY_ATTRIBUTE_NAME = "attributeName";
/*    */    private String attributeValueFrom;
/*    */    private String attributeValueTo;
/*    */ 
/*    */    public String getAttributeValueFrom() {
/* 48 */       return this.attributeValueFrom;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public void setAttributeValueFrom(String attributeValueFrom) {
/* 54 */       this.attributeValueFrom = attributeValueFrom;
/* 55 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public String getAttributeValueTo() {
/* 60 */       return this.attributeValueTo;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public void setAttributeValueTo(String attributeValueTo) {
/* 66 */       this.attributeValueTo = attributeValueTo;
/* 67 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public Boolean getAttributeValueBoolean() {
/* 73 */       return this.attributeValueFrom != null && this.attributeValueFrom.equalsIgnoreCase("Ja") ? Boolean.TRUE : Boolean.FALSE;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void setAttributeValueBoolean(Boolean bool) {
/* 82 */       if (bool) {
/* 83 */          this.attributeValueFrom = "Ja";
/*    */       } else {
/* 85 */          this.attributeValueFrom = "Nei";
/*    */       }
/* 87 */    }
/*    */ 
/*    */ 
/*    */    public void addPropertyChangeListener(PropertyChangeListener arg0) {
/* 91 */    }
/*    */ 
/*    */ 
/*    */    public void removePropertyChangeListener(PropertyChangeListener arg0) {
/* 95 */    }
/*    */ }
