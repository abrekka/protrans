package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.FunctionCategoryModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for kategori
 * @author atle.brekka
 */
public class FunctionCategoryValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private FunctionCategoryModel functionCategoryModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aFunctionCategoryModel
     */
    public FunctionCategoryValidator(final FunctionCategoryModel aFunctionCategoryModel) {
        this.functionCategoryModel = aFunctionCategoryModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                functionCategoryModel, "Kategori");

        if (ValidationUtils.isBlank(ModelUtil
                .nullToString(functionCategoryModel.getFunctionCategoryName()))) {
            support.addError("navn", "må settes");
        }

        if (functionCategoryModel.getJobFunction() == null) {
            support.addError("funksjon", "må settes");
        }

        return support.getResult();
    }

}
