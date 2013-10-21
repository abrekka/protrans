package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.ArticleTypeModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Klasse som validerer artikkeltype
 * @author atle.brekka
 */
public class ArticleTypeValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private ArticleTypeModel articleTypeModel;

    /**
     * @param aArticleTypeModel
     */
    public ArticleTypeValidator(final ArticleTypeModel aArticleTypeModel) {
        this.articleTypeModel = aArticleTypeModel;
    }

    // Validation *************************************************************

    /**
     * @see com.jgoodies.validation.Validator#validate()
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                articleTypeModel, "Artikkeltype");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(articleTypeModel
                .getArticleTypeName()))) {
            support.addError("navn", "må settes");
        }
        
        if(!ValidationUtils.isNumeric(ModelUtil.nullToString(articleTypeModel
                .getProdCatNo()))){
            support.addError("produktkategori", "må være tall");
        }

        return support.getResult();
    }

}
