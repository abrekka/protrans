package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.ProductionBudgetModel;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for budsjett
 * @author atle.brekka
 */
public class ProductionBudgetValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private ProductionBudgetModel productionBudgetModel;

    /**
     * @param aProductionBudgetModel
     */
    public ProductionBudgetValidator(final ProductionBudgetModel aProductionBudgetModel) {
        this.productionBudgetModel = aProductionBudgetModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                productionBudgetModel, "Produksjonsbudsjett");

        if (ValidationUtils.isBlank(ModelUtil
                .nullToString(productionBudgetModel.getBudgetYear()))) {
            support.addError("�r", "m� settes");
        }
        if (ValidationUtils.isBlank(ModelUtil
                .nullToString(productionBudgetModel.getBudgetWeek()))) {
            support.addError("uke", "m� settes");
        }
        if (!Util.isNumber(productionBudgetModel.getBudgetValue())) {
            support.addError("budsjett", "m� v�re tall");

        }
        if (ValidationUtils.isBlank(ModelUtil
                .nullToString(productionBudgetModel.getBudgetValue()))) {
            support.addError("budsjett", "m� settes");
        }
        if (ValidationUtils.isBlank(ModelUtil
                .nullToString(productionBudgetModel.getProductArea()))) {
            support.addError("produktomr�de", "m� settes");
        }

        return support.getResult();
    }

}
