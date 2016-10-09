package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.AccidentParticipantModel;
import no.ugland.utransprod.gui.model.ColliModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

public class ColliValidator implements Validator {

	/**
	 * Holds the order to be validated.
	 */
	private ColliModel colliModel;

	/**
	 * @param aArticleTypeModel
	 */
	public ColliValidator(final ColliModel aColliModel) {
		this.colliModel = aColliModel;
	}

	// Validation *************************************************************

	/**
	 * @see com.jgoodies.validation.Validator#validate()
	 */
	public final ValidationResult validate() {
		PropertyValidationSupport support = new PropertyValidationSupport(colliModel, "Kolli");

		if (!ValidationUtils.isEmpty(colliModel.getNumberOfCollies())
				&& !ValidationUtils.isNumeric(colliModel.getNumberOfCollies())) {
			support.addError("antall", "må settes");
		}

		return support.getResult();
	}

}
