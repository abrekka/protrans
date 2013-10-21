package no.ugland.utransprod.model.validators;

import java.util.Set;

import no.ugland.utransprod.model.IArticleAttribute;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for sett av attributter
 * @author atle.brekka
 */
public class AttributeSetValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private Set<IArticleAttribute> attributes;

    /**
     * @param atts
     */
    public AttributeSetValidator(final Set<IArticleAttribute> atts) {
        this.attributes = atts;
    }

    // Validation *************************************************************

    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                attributes, "Artikkel");

        if (attributes != null) {
            int count = 1;

            for (IArticleAttribute attribute : attributes) {
                validateAttribute(support, attribute);
                count++;
            }
        }

        return support.getResult();
    }

    private void validateAttribute(final PropertyValidationSupport support,
            final IArticleAttribute attribute) {
        if (ValidationUtils.isBlank(ModelUtil.nullToString(attribute
                .getAttributeValue()))) {
            support.addError(attribute.getAttributeName(), "må settes");
        }
    }
}