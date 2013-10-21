package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.ConstructionTypeModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Klassesom validerer grasjetype
 * @author atle.brekka
 */
public class ConstructionTypeValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private ConstructionTypeModel constructionTypeModel;

    /**
     * @param aConstructionTypeModel
     */
    public ConstructionTypeValidator(final ConstructionTypeModel aConstructionTypeModel) {
        this.constructionTypeModel = aConstructionTypeModel;
    }

    /**
     * @see com.jgoodies.validation.Validator#validate()
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                constructionTypeModel, "Garasjetype");

        if (ValidationUtils.isBlank(ModelUtil
                .nullToString(constructionTypeModel.getName()))) {
            support.addError("navn", "må settes");
        }

        if (ValidationUtils.isBlank(ModelUtil
                .nullToString(constructionTypeModel.getProductArea()))) {
            support.addError("produktområde", "må settes");
        }

        return support.getResult();
    }

}
