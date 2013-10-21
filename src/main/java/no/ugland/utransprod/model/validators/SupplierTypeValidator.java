package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.SupplierTypeModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for leverand�rtype
 * @author atle.brekka
 */
public class SupplierTypeValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private SupplierTypeModel supplierTypeModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aSupplierTypeModel
     */
    public SupplierTypeValidator(final SupplierTypeModel aSupplierTypeModel) {
        this.supplierTypeModel = aSupplierTypeModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                supplierTypeModel, "SupplierType");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(supplierTypeModel
                .getSupplierTypeName()))) {
            support.addError("navn", "m� settes");
        }

        return support.getResult();
    }

}
