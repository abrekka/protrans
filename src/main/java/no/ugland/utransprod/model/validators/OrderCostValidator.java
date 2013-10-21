package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.OrderCostModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for o
 * @author atle.brekka
 */
public class OrderCostValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private OrderCostModel orderCostModel;

    /**
     * @param aOrderCostModel
     */
    public OrderCostValidator(final OrderCostModel aOrderCostModel) {
        this.orderCostModel = aOrderCostModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                orderCostModel, "Kostnad");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(orderCostModel
                .getCostType()))) {
            support.addError("type", "må settes");
        }
        
        if (ValidationUtils.isBlank(ModelUtil.nullToString(orderCostModel
                .getCostUnit()))) {
            support.addError("enhet", "må settes");
        }

        if (ValidationUtils.isBlank(ModelUtil.nullToString(orderCostModel
                .getCostAmount()))) {
            support.addError("beløp", "må settes");
        }

        return support.getResult();
    }

}
