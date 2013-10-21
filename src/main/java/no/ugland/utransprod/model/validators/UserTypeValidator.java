package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.UserTypeModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for brukertype
 * @author atle.brekka
 */
public class UserTypeValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private UserTypeModel userTypeModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aUserTypeModel
     */
    public UserTypeValidator(final UserTypeModel aUserTypeModel) {
        this.userTypeModel = aUserTypeModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                userTypeModel, "Brukertype");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(userTypeModel
                .getDescription()))) {
            support.addError("beskrivelse", "må settes");
        }

        if (ValidationUtils.isBlank(ModelUtil.nullToString(userTypeModel
                .getStartupWindowEnum()))) {
            support.addError("oppstartsvindu", "må settes");
        }

        return support.getResult();
    }

}
