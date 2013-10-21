package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.ExternalOrderModel;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;

/**
 * Validator for ekstern ordre
 * @author atle.brekka
 */
public class ExternalOrderValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private ExternalOrderModel externalOrderModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aExternalOrderModel
     */
    public ExternalOrderValidator(final ExternalOrderModel aExternalOrderModel) {
        this.externalOrderModel = aExternalOrderModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                externalOrderModel, "Bestilling");

        if (externalOrderModel.getSupplier() == null) {
            support.addError("leverandør", "må settes");
        }

        return support.getResult();
    }

}
