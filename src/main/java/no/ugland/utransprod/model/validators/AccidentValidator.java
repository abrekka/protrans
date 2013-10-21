package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.AccidentModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

public class AccidentValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private AccidentModel accidentModel;

    /**
     * @param aArticleTypeModel
     */
    public AccidentValidator(final AccidentModel aAccidentModel) {
        this.accidentModel = aAccidentModel;
    }

    // Validation *************************************************************

    /**
     * @see com.jgoodies.validation.Validator#validate()
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                accidentModel, "Ulykke");

        
        
        if (ValidationUtils.isBlank(ModelUtil.nullToString(accidentModel
                .getRegisteredBy()))) {
            support.addError("registert av", "m� settes");
        }
        
        if (accidentModel.getRegistrationDate()==null) {
            support.addError("registreringsdato", "m� settes");
        }
        
        if (accidentModel.getJobFunction()==null) {
            support.addError("funksjon", "m� settes");
        }
        
        if (accidentModel.getPersonalInjury()==null) {
            support.addError("type", "m� settes");
        }
        
        if (accidentModel.getAccidentDate()==null) {
            support.addError("ulykkesdato", "m� settes");
        }
        
        if (ValidationUtils.isBlank(ModelUtil.nullToString(accidentModel
                .getAccidentDescription()))) {
            support.addError("beskrivelse", "m� settes");
        }
        
        if (ValidationUtils.isBlank(ModelUtil.nullToString(accidentModel
                .getAccidentCause()))) {
            support.addError("�rsak", "m� settes");
        }

        return support.getResult();
    }

}
