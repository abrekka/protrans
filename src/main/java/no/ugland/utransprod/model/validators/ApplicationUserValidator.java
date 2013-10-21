package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.ApplicationUserModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for bruker
 * @author abr99
 */
public class ApplicationUserValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private ApplicationUserModel applicationUserModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aApplicationUserModel
     */
    public ApplicationUserValidator(final ApplicationUserModel aApplicationUserModel) {
        this.applicationUserModel = aApplicationUserModel;
    }

    // Validation *************************************************************

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                applicationUserModel, "Bruker");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(applicationUserModel
                .getFirstName()))) {
            support.addError("fornavn", "må settes");
        }

        if (ValidationUtils.isBlank(ModelUtil.nullToString(applicationUserModel
                .getUserName()))) {
            support.addError("brukernavn", "må settes");
        }

        if (ValidationUtils.isBlank(ModelUtil.nullToString(applicationUserModel
                .getLastName()))) {
            support.addError("etternavn", "må settes");
        }

        if (ValidationUtils.isBlank(ModelUtil.nullToString(applicationUserModel
                .getPassword()))) {
            support.addError("passord", "må settes");
        }

        if (ValidationUtils.isBlank(ModelUtil.nullToString(applicationUserModel
                .getGroupUser()))) {
            support.addError("gruppebruker", "må settes");
        }

        if (applicationUserModel.getUserRoleList().size() == 0) {
            support.addError("rolle", "må ha minst en rolle");
        }
        if (applicationUserModel.getProductArea() == null) {
            support.addError("produktområde", "må settes");
        }

        return support.getResult();
    }

}
