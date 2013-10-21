package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.CostUnitModel;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for kostnadsenhet
 * @author atle.brekka
 */
public class CostUnitValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private CostUnitModel costUnitModel;

    /**
     * @param aCostUnitModel
     */
    public CostUnitValidator(final CostUnitModel aCostUnitModel) {
        this.costUnitModel = aCostUnitModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                costUnitModel, "Kostnadsenhet");

        if (ValidationUtils.isBlank(costUnitModel.getCostUnitName())) {
            support.addError("navn", "må settes");
        }

        return support.getResult();
    }

}
