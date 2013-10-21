package no.ugland.utransprod.gui.model;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.Icon;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import no.ugland.utransprod.model.CustTr;
import no.ugland.utransprod.util.Util;

public class TextPaneRendererCustTr extends TextPaneRenderer<Transportable> {
	private static final long serialVersionUID = 1L;
	private static final String PAID_STRING = "Ja";
	private static final String NOT_PAID_STRING = "Nei";
	private InvoiceDetails invoiceDetails;

	protected void setCenteredAlignment() {
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setAlignment(set, StyleConstants.ALIGN_CENTER);
		setParagraphAttributes(set, false);
		repaint();

	}

	@Override
	protected List<Icon> getIcons(Transportable object) {
		return null;
	}

	@Override
	protected String getPaneText(Transportable transportable) {
		String text = null;
		if (hasCustomerTransactions(transportable)) {
			text = getInvoiceDetailsText(transportable);
		} else {
			text = getPaidAsText(transportable);
		}
		return text;
	}

	private String getPaidAsText(Transportable transportable) {
		return transportable.getPaidDate() != null ? NOT_PAID_STRING
				: PAID_STRING;
	}

	private String getInvoiceDetailsText(Transportable transportable) {
		invoiceDetails = getInvoiceDetails(transportable.getCustTrs());
		return Util.convertBigDecimalToString(invoiceDetails.getRestAmount());
	}

	@Override
	protected StringBuffer getPaneToolTipText(Transportable transportable) {
		StringBuffer stringBuffer = new StringBuffer();
		if (hasCustomerTransactions(transportable)) {
			invoiceDetails = invoiceDetails == null ? getInvoiceDetails(transportable
					.getCustTrs())
					: invoiceDetails;
			stringBuffer.append(invoiceDetails.getSummary());
		}
		return stringBuffer;
	}

	private boolean hasCustomerTransactions(Transportable transportable) {
		return transportable.getCustTrs() != null
				&& transportable.getCustTrs().size() > 0;
	}

	private InvoiceDetails getInvoiceDetails(List<CustTr> custTrSet) {
		StringBuilder toolTipText = new StringBuilder();
		BigDecimal restAmount = BigDecimal.ZERO;
		for (CustTr custTr : custTrSet) {
			toolTipText.append("Fakturabeløp:").append(
					Util.convertBigDecimalToString(custTr.getInvoiceAmount()))
					.append(" Restbeløp:").append(
							Util.convertBigDecimalToString(custTr
									.getRestAmount())).append(" Forfallsdato:")
					.append(
							Util.formatDate(custTr.getDueDate(),
									Util.SHORT_DATE_FORMAT)).append("<br>");
			restAmount = restAmount.add(custTr.getRestAmount() != null ? custTr
					.getRestAmount() : BigDecimal.ZERO);
		}

		return new InvoiceDetails(restAmount, toolTipText.toString());
	}

	private class InvoiceDetails {
		private BigDecimal restAmount;
		private String summary;

		public InvoiceDetails(BigDecimal aRestAmount, String aSummary) {
			restAmount = aRestAmount;
			summary = aSummary;
		}

		public BigDecimal getRestAmount() {
			return restAmount;
		}

		public String getSummary() {
			return summary;
		}
	}

}
