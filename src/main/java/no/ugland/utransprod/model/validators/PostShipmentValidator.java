package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.PostShipmentModel;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;

/**
 * Validator for etterlevering
 * @author atle.brekka
 */
public class PostShipmentValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private PostShipmentModel postShipmentModel;

    /**
     * @param aPostShipmentModel
     */
    public PostShipmentValidator(final PostShipmentModel aPostShipmentModel) {
        this.postShipmentModel = aPostShipmentModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                postShipmentModel, "Ettersending");

        return support.getResult();
    }

}
