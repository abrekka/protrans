package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.CostTypeModel;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for kostnadstype
 * @author atle.brekka
 */
public class CostTypeValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private CostTypeModel costTypeModel;

    /**
     * @param aCostTypeModel
     */
    public CostTypeValidator(final CostTypeModel aCostTypeModel) {
        this.costTypeModel = aCostTypeModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                costTypeModel, "Kostnadstype");

        if (ValidationUtils.isBlank(costTypeModel.getCostTypeName())) {
            support.addError("navn", "må settes");
        }

        return support.getResult();
    }

}
