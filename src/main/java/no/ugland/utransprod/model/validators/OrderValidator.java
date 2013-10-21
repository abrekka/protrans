package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Klassesom håndterer validering av ordre
 * 
 * @author atle.brekka
 */
public class OrderValidator implements Validator {

	/**
	 * Holds the order to be validated.
	 */
	private OrderModel orderModel;
	private boolean search = false;

	/**
	 * Constructs an OrderValidator on the given Order.
	 * 
	 * @param aOrderModel
	 */
	public OrderValidator(final OrderModel aOrderModel, final boolean search) {
		this.orderModel = aOrderModel;
		this.search = search;
	}

	/**
	 * Validates this Validator's Order and returns the result as an instance of
	 * {@link ValidationResult}.
	 * 
	 * @return the ValidationResult of the order validation
	 */
	public final ValidationResult validate() {

		if (!search) {

			return validateOrder();
		}
		return validateSearchOrder();
	}

	private ValidationResult validateOrder() {
		PropertyValidationSupport support = new PropertyValidationSupport(
				orderModel, "Ordre");
		if (ValidationUtils.isBlank(ModelUtil.nullToString(orderModel
				.getOrderNr()))) {
			support.addError("ordrenummer", "må settes");
		}

		if (ValidationUtils.isBlank(orderModel.getDeliveryAddress())) {
			support.addError("adresse", "må settes");
		}

		if (ValidationUtils.isBlank(orderModel.getPostalCode())) {
			support.addError("postnummer", "må settes");
		} else if (!ValidationUtils.isNumeric(orderModel.getPostalCode())) {
			support.addError("postnummer", "må være et tall");
		} else if (orderModel.getPostalCode().length() > 4) {
			support.addError("postnummer", "kan ikke være mer enn 4 tall");
		}

		if (ValidationUtils.isBlank(orderModel.getPostOffice())) {
			support.addError("poststed", "må settes");
		}
		if (orderModel.getCustomer() == null
				&& (ValidationUtils.isBlank(ModelUtil.nullToString(orderModel
						.getCustomerNr()))
						|| ValidationUtils
								.isBlank(ModelUtil.nullToString(orderModel
										.getCustomerFirstName())) || ValidationUtils
						.isBlank(ModelUtil.nullToString(orderModel
								.getCustomerLastName())))) {
			support.addError("kunde", "må settes");
		}
		if (orderModel.getConstructionType() == null) {
			support.addError("garasjetype", "må settes");
		}

		if (orderModel.getOrderDate() == null) {
			support.addError("ordredato", "må settes");
		}

		if (ValidationUtils.isBlank(ModelUtil.nullToString(orderModel
				.getProductArea()))) {
			support.addError("produktområde", "må settes");
		}
		return support.getResult();
	}

	private ValidationResult validateSearchOrder() {
		PropertyValidationSupport support = new PropertyValidationSupport(
				orderModel, "Ordre");
		boolean criteriaSet = false;
		if (!ValidationUtils.isBlank(ModelUtil.nullToString(orderModel
				.getOrderNr()))) {
			criteriaSet = true;
		}

		if (!ValidationUtils.isBlank(ModelUtil.nullToString(orderModel
				.getCustomerFirstName()))) {
			criteriaSet = true;
		}

		if (!ValidationUtils.isBlank(ModelUtil.nullToString(orderModel
				.getCustomerNr()))
				|| !ValidationUtils.isBlank(ModelUtil.nullToString(orderModel
						.getCustomerFirstName()))
				|| !ValidationUtils.isBlank(ModelUtil.nullToString(orderModel
						.getCustomerLastName()))
				|| !ValidationUtils.isBlank(ModelUtil.nullToString(orderModel
						.getTelephoneNr()))
				|| !ValidationUtils.isBlank(ModelUtil.nullToString(orderModel
						.getTelephoneNrSite()))) {
			criteriaSet = true;
		}

		if (!ValidationUtils.isBlank(orderModel.getDeliveryAddress())) {
			criteriaSet = true;
		}

		if (!ValidationUtils.isBlank(orderModel.getPostalCode())) {
			criteriaSet = true;
			if (!ValidationUtils.isNumeric(orderModel.getPostalCode())) {
				support.addError("postnummer", "må være et tall");
			} else if (orderModel.getPostalCode().length() > 4) {
				support.addError("postnummer", "kan ikke være mer enn 4 tall");
			}
		}

		if (!ValidationUtils.isBlank(orderModel.getPostOffice())) {
			criteriaSet = true;
		}
		if (!ValidationUtils.isBlank(orderModel.getSalesman())) {
			criteriaSet = true;
		}

		if (!ValidationUtils.isBlank(ModelUtil.nullToString(orderModel
				.getProductArea()))) {
			criteriaSet = true;
		}

		if (orderModel.getOrderDate() != null) {
			criteriaSet = true;
		}

		if (orderModel.getAgreementDate() != null) {
			criteriaSet = true;
		}
		if (orderModel.getDeliveryWeek() != null) {
			criteriaSet = true;
		}

		if (orderModel.getConstructionType() != null) {
			criteriaSet = true;
		}
		if (orderModel.getTransport() != null) {
			criteriaSet = true;
		}
		if (orderModel.getInvoiceDate() != null) {
			criteriaSet = true;
		}
		if (orderModel.getPacklistReady() != null) {
			criteriaSet = true;
		}
		if (orderModel.getPaidDate() != null) {
			criteriaSet = true;
		}
		if (orderModel.getProductionDate() != null) {
			criteriaSet = true;
		}

		if (!criteriaSet) {
			support.addError("ordrenummer",
					"må sette inn søkekriteria i ett av feltene");
		}

		return support.getResult();
	}

}
