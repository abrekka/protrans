package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.ProductionUnitModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

public class ProductionUnitValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private ProductionUnitModel productionUnitModel;

    /**
     * @param aArticleTypeModel
     */
    public ProductionUnitValidator(final ProductionUnitModel aProductionUnitModel) {
        this.productionUnitModel = aProductionUnitModel;
    }

    // Validation *************************************************************

    /**
     * @see com.jgoodies.validation.Validator#validate()
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                productionUnitModel, "Produksjonsenhet");

        
        
        if (ValidationUtils.isBlank(ModelUtil.nullToString(productionUnitModel
                .getProductionUnitName()))) {
            support.addError("navn", "må settes");
        }
        
        if (productionUnitModel.getArticleType()==null) {
            support.addError("artikkel", "må settes");
        }
        
        if (productionUnitModel.getProductionUnitProductAreaGroupList()==null||productionUnitModel.getProductionUnitProductAreaGroupList().size()==0) {
            support.addError("produktområde", "må settes");
        }
        

        return support.getResult();
    }

}
