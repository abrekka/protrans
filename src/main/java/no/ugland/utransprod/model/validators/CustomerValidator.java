package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.CustomerModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for kunde
 * @author atle.brekka
 */
public class CustomerValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private CustomerModel customerModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aCustomerModel
     */
    public CustomerValidator(final CustomerModel aCustomerModel) {
        this.customerModel = aCustomerModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                customerModel, "Kunde");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(customerModel
                .getCustomerNr()))) {
            support.addError("nummer", "må settes");
        }

        if (ValidationUtils.isBlank(customerModel.getFirstName())) {
            support.addError("fornavn", "må settes");
        }

        if (ValidationUtils.isBlank(customerModel.getLastName())) {
            support.addError("etternavn", "må settes");
        }

        return support.getResult();
    }

}
