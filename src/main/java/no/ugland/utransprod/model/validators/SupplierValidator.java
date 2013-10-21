package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.SupplierModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for leverandør
 * @author atle.brekka
 */
public class SupplierValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private SupplierModel supplierModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aSupplierModel
     */
    public SupplierValidator(final SupplierModel aSupplierModel) {
        this.supplierModel = aSupplierModel;
    }

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                supplierModel, "Supplier");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(supplierModel
                .getSupplierName()))) {
            support.addError("navn", "må settes");
        }

        if (supplierModel.getPostalCode() != null
                && supplierModel.getPostalCode().length() > 4) {
            support.addError("postnr", "fire siffer");
        }

        if (!ValidationUtils.isBlank(ModelUtil.nullToString(supplierModel
                .getPostalCode()))
                && !ValidationUtils.isAlphanumeric(supplierModel
                        .getPostalCode())) {
            support.addError("postnr", "må være tall");
        }

        if (supplierModel.getSupplierType() == null) {
            support.addWarning("type", "bør registreres");
        }

        return support.getResult();
    }

}
