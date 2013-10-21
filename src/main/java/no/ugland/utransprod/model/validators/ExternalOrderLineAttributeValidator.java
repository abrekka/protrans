package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.ExternalOrderLineAttributeModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for ekstern ordrelinje
 * @author atle.brekka
 */
public class ExternalOrderLineAttributeValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private ExternalOrderLineAttributeModel externalOrderLineAttributeModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aExternalOrderLineAttributeModel
     */
    public ExternalOrderLineAttributeValidator(
            final ExternalOrderLineAttributeModel aExternalOrderLineAttributeModel) {
        this.externalOrderLineAttributeModel = aExternalOrderLineAttributeModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                externalOrderLineAttributeModel, "Attributt");

        if (ValidationUtils.isBlank(ModelUtil
                .nullToString(externalOrderLineAttributeModel
                        .getExternalOrderLineAttributeName()))) {
            support.addError("navn", "må settes");
        }
        if (ValidationUtils.isBlank(ModelUtil
                .nullToString(externalOrderLineAttributeModel
                        .getExternalOrderLineAttributeValue()))) {
            support.addError("verdi", "må settes");
        }

        return support.getResult();
    }

}
