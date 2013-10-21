package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.TransportModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for transport
 * @author atle.brekka
 */
public class TransportValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private TransportModel transportModel;

    /**
     * @param aTransportModel
     */
    public TransportValidator(final TransportModel aTransportModel) {
        this.transportModel = aTransportModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                transportModel, "Transport");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(transportModel
                .getTransportYear()))) {
            support.addError("år", "må settes");
        }

        if (ValidationUtils.isBlank(ModelUtil.nullToString(transportModel
                .getTransportWeek()))) {
            support.addError("uke", "må settes");
        }

        if (ValidationUtils.isBlank(ModelUtil.nullToString(transportModel
                .getTransportName()))) {
            support.addError("navn", "må settes");
        }

        return support.getResult();
    }

}
