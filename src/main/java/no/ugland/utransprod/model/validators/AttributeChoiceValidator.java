package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.AttributeChoiceModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

public class AttributeChoiceValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private AttributeChoiceModel attributeChoiceModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aAttributeModel
     */
    public AttributeChoiceValidator(final AttributeChoiceModel aAttributeChoiceModel) {
        this.attributeChoiceModel = aAttributeChoiceModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                attributeChoiceModel, "Valg");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(attributeChoiceModel
                .getChoiceValue()))) {
            support.addError("valg", "må settes");
        }
        

        return support.getResult();
    }

}
