package no.ugland.utransprod.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.apache.commons.lang.StringUtils;

import no.ugland.utransprod.model.OrderLineAttribute;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

public enum AttributeDataType {
    TEKST("Tekst") {
        @Override
        public JComponent createTexField(ValueModel valueModel) {
            return BasicComponentFactory.createTextField(valueModel);
        }

        @Override
        public void addError(OrderLineAttribute attribute, PropertyValidationSupport support) {
        }

		@Override
		public boolean isValid(String value) {
			return true;
		}
    },
    TALL("Tall") {
        @Override
        public JComponent createTexField(ValueModel valueModel) {
            return BasicComponentFactory.createTextField(valueModel);
        }

        @Override
        public void addError(OrderLineAttribute attribute, PropertyValidationSupport support) {
            if(!ValidationUtils.isNumeric(attribute.getAttributeValue())){
                support.addError(attribute.getAttributeName(), "Må være tall");
            }
        }

		@Override
		public boolean isValid(String value) {
			return StringUtils.isNumeric(value)&&value.length()>0;
		}

        
    };
    private String info;
    private static List<String> stringValues;
    public abstract JComponent createTexField(ValueModel valueModel);
    public abstract void addError(OrderLineAttribute attribute, PropertyValidationSupport support);
    public abstract boolean isValid(String value);

    private AttributeDataType(String aInfo){
        info=aInfo;
    }
    @Override
    public String toString() {
        return info;
    }

    public static List<String> getStringValues() {
        return stringValues!=null?stringValues:createStringValues();
    }

    private static List<String> createStringValues() {
        stringValues=new ArrayList<String>();
        AttributeDataType[] types = AttributeDataType.values();
        for(AttributeDataType type:types){
            stringValues.add(type.getInfo());
        }
        return stringValues;
    }

    private String getInfo() {
        return info;
    }

    
}
