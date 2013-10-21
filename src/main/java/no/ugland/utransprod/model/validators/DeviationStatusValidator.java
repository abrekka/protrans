package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.DeviationStatusModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for avviksstatus
 * @author atle.brekka
 */
public class DeviationStatusValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private DeviationStatusModel deviationStatusModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aDeviationStatusModel
     */
    public DeviationStatusValidator(final DeviationStatusModel aDeviationStatusModel) {
        this.deviationStatusModel = aDeviationStatusModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                deviationStatusModel, "Avvikstatus");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(deviationStatusModel
                .getDeviationStatusName()))) {
            support.addError("navn", "må settes");
        }

        return support.getResult();
    }

}
