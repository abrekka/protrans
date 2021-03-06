package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.PreventiveActionModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for prevantive tiltak
 * @author atle.brekka
 */
public class PreventiveActionValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private PreventiveActionModel preventiveActionModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aPreventiveActionModel
     */
    public PreventiveActionValidator(final PreventiveActionModel aPreventiveActionModel) {
        this.preventiveActionModel = aPreventiveActionModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                preventiveActionModel, "Prevantivt tiltak");

        if (ValidationUtils.isBlank(ModelUtil
                .nullToString(preventiveActionModel.getPreventiveActionName()))) {
            support.addError("prosjektnavn", "m� settes");
        }

        return support.getResult();
    }

}
