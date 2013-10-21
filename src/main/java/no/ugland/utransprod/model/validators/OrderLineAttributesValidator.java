package no.ugland.utransprod.model.validators;

import java.util.List;

import no.ugland.utransprod.gui.AttributeDataType;
import no.ugland.utransprod.model.OrderLineAttribute;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;

public class OrderLineAttributesValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private List<OrderLineAttribute> attributes;

    /**
     * @param aOrderCostModel
     */
    public OrderLineAttributesValidator(final List<OrderLineAttribute> orderLineAttributes) {
        attributes=orderLineAttributes;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                attributes, "Attributt");
        
        for(OrderLineAttribute attribute:attributes){
            AttributeDataType dataType = attribute.getAttributeDataType()!=null?AttributeDataType.valueOf(attribute.getAttributeDataType()):AttributeDataType.TEKST;
            dataType.addError(attribute,support);
        }


        return support.getResult();
    }

}
