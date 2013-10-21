package no.ugland.utransprod.model.validators;

import java.util.List;

import no.ugland.utransprod.gui.model.DeviationModel;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for avvik
 * 
 * @author atle.brekka
 */
public class DeviationValidator implements Validator {

	/**
	 * Holds the order to be validated.
	 */
	private DeviationModel deviationModel;
	private boolean search = false;

	/**
	 * Constructs an OrderValidator on the given Order.
	 * 
	 * @param aDeviationModel
	 */
	public DeviationValidator(final DeviationModel aDeviationModel,
			final boolean search) {
		this.deviationModel = aDeviationModel;
		this.search = search;
	}

	/**
	 * Validates this Validator's Order and returns the result as an instance of
	 * {@link ValidationResult}.
	 * 
	 * @return the ValidationResult of the order validation
	 */
	public final ValidationResult validate() {
		if (search) {
			return validateSearchDeviation();
		}
		return validateDeviation();
	}

	private ValidationResult validateSearchDeviation() {
		PropertyValidationSupport support = new PropertyValidationSupport(
				deviationModel, "Avvik");

		boolean criteriaSet = false;

		if (!ValidationUtils.isBlank(ModelUtil.nullToString(deviationModel
				.getUserName()))) {
			criteriaSet = true;
		}

		if (isOrderNrAndProjectNrValid()) {
			criteriaSet = true;
		}

		if (!ValidationUtils.isBlank(ModelUtil.nullToString(deviationModel
				.getInitiatedBy()))) {
			criteriaSet = true;
		}

		if (!ValidationUtils.isBlank(ModelUtil.nullToString(deviationModel
				.getResponsible()))) {
			criteriaSet = true;
		}

		if (deviationModel.getDeviationFunction() != null) {
			criteriaSet = true;
		}
		if (deviationModel.getFunctionCategory() != null) {
			criteriaSet = true;
		}

		if (deviationModel.getDeviationStatus() != null) {
			criteriaSet = true;
		}

		if (deviationModel.getDateFrom() != null) {
			criteriaSet = true;
		}

		if (deviationModel.getDateTo() != null) {
			criteriaSet = true;
		}

		if (!criteriaSet) {
			support.addError("navn", "kriterier må settes");
		}

		return support.getResult();
	}

	private ValidationResult validateDeviation() {
		PropertyValidationSupport support = new PropertyValidationSupport(
				deviationModel, "Avvik");

		if (ValidationUtils.isBlank(ModelUtil.nullToString(deviationModel
				.getUserName()))) {
			support.addError("navn", "må settes");
		}

		if (!isOrderNrAndProjectNrValid()) {
			support.addError("ordrenr", "ordrenr eller prosjektnr må settes");
		}

		if (ValidationUtils.isBlank(ModelUtil.nullToString(deviationModel
				.getInitiatedBy()))) {
			support.addError("initiert av", "må settes");
		}

		if (ValidationUtils.isBlank(ModelUtil.nullToString(deviationModel
				.getResponsible()))) {
			support.addError("behandlingsansvarlig", "må settes");
		}

		if (deviationModel.getDeviationFunction() == null) {
			support.addError("avviksfunksjon", "må settes");
		}
		if (deviationModel.getFunctionCategory() == null) {
			support.addError("kategori", "må settes");
		}

		if (deviationModel.getDeviationStatus() == null) {
			support.addError("status", "må settes");
		}

		if (!validateNewComments()) {
			support.addError("kommentar", "må settes");
		}

		return support.getResult();
	}

	private boolean isOrderNrAndProjectNrValid() {
		boolean isValid = true;
		if (ValidationUtils.isBlank(ModelUtil.nullToString(deviationModel
				.getOrderNr()))
				&& ValidationUtils.isBlank(ModelUtil
						.nullToString(deviationModel.getProjectNr()))) {
			isValid = false;
		}
		return isValid;
	}

	private boolean validateNewComments() {
		List<OrderComment> comments = deviationModel.getComments();
		boolean hasNewComment = false;
		if (comments != null) {
			hasNewComment = checkIfNewComment(comments);
		}
		return hasNewComment;
	}

	private boolean checkIfNewComment(final List<OrderComment> orderComments) {
		boolean hasNewComment = false;
		for (OrderComment orderComment : orderComments) {
			if (orderComment.getOrderCommentId() == null) {
				hasNewComment = true;
				break;
			}
		}
		return hasNewComment;
	}

}
