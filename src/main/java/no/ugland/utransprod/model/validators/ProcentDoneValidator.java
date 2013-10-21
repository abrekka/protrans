package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.ProcentDoneModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

public class ProcentDoneValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private ProcentDoneModel procentDoneModel;

    public ProcentDoneValidator(final ProcentDoneModel aProcentDoneModel) {
        this.procentDoneModel = aProcentDoneModel;
    }

    /**
     * @see com.jgoodies.validation.Validator#validate()
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                procentDoneModel, "Prosent ferdig");
        
        if (!ValidationUtils.isAlphanumeric(ModelUtil.nullToString(procentDoneModel
                .getProcentString()))) {
            support.addError("prosent", "m� v�re tall");
        }

        if (ValidationUtils.isBlank(ModelUtil.nullToString(procentDoneModel
                .getProcentString()))) {
            support.addError("prosent", "m� settes");
        }
        
        if(procentDoneModel.getProcent()!=null&&procentDoneModel.getProcent()>100){
            support.addError("prosent", "kan ikke v�re st�rre enn 100");
        }

        return support.getResult();
    }

}
