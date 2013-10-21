package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.InfoModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for info
 * @author atle.brekka
 */
public class InfoValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private InfoModel infoModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aInfoModel
     */
    public InfoValidator(final InfoModel aInfoModel) {
        this.infoModel = aInfoModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                infoModel, "Informasjon");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(infoModel
                .getDateFrom()))) {
            support.addError("fra dato", "må settes");
        }
        if (ValidationUtils.isBlank(ModelUtil.nullToString(infoModel
                .getDateTo()))) {
            support.addError("til dato", "må settes");
        }
        if (ValidationUtils.isBlank(ModelUtil.nullToString(infoModel
                .getInfoText()))) {
            support.addError("informasjonstekst", "må settes");
        }

        return support.getResult();
    }

}
