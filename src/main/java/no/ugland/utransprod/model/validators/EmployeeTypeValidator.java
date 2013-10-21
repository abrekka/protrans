package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.EmployeeTypeModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for ansatttype
 * @author atle.brekka
 */
public class EmployeeTypeValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private EmployeeTypeModel employeeTypeModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aEmployeeTypeModel
     */
    public EmployeeTypeValidator(final EmployeeTypeModel aEmployeeTypeModel) {
        this.employeeTypeModel = aEmployeeTypeModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                employeeTypeModel, "Ansattype");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(employeeTypeModel
                .getEmployeeTypeName()))) {
            support.addError("navn", "må settes");
        }

        return support.getResult();
    }

}
