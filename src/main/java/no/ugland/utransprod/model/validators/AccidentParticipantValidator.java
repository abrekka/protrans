package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.AccidentModel;
import no.ugland.utransprod.gui.model.AccidentParticipantModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

public class AccidentParticipantValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private AccidentParticipantModel accidentParticipantModel;

    /**
     * @param aArticleTypeModel
     */
    public AccidentParticipantValidator(final AccidentParticipantModel aAccidentParticipantModel) {
        this.accidentParticipantModel = aAccidentParticipantModel;
    }

    // Validation *************************************************************

    /**
     * @see com.jgoodies.validation.Validator#validate()
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                accidentParticipantModel, "Involvert");

        
        
        if (ValidationUtils.isBlank(ModelUtil.nullToString(accidentParticipantModel
                .getFirstName()))) {
            support.addError("fornavn", "må settes");
        }
        if (ValidationUtils.isBlank(ModelUtil.nullToString(accidentParticipantModel
                .getLastName()))) {
            support.addError("etternavn", "må settes");
        }
        
      
        
        return support.getResult();
    }

}
