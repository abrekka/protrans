package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.util.YesNoInteger;

import com.jgoodies.binding.PresentationModel;

/**
 * Modellklasse for ordrekostnad
 * 
 * @author atle.brekka
 * 
 */
public class OrderCostModel extends AbstractModel<OrderCost, OrderCostModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_ORDER_COST_ID = "orderCostId";

	/**
	 * 
	 */
	public static final String PROPERTY_COST_TYPE = "costType";

	/**
	 * 
	 */
	public static final String PROPERTY_COST_UNIT = "costUnit";

	/**
	 * 
	 */
	public static final String PROPERTY_COST_AMOUNT = "costAmount";

	/**
	 * 
	 */
	public static final String PROPERTY_INCL_VAT = "inclVat";

	/**
	 * 
	 */
	public static final String PROPERTY_IS_INCL_VAT = "isInclVat";

	/**
	 * 
	 */
	public static final String PROPERTY_SUPPLIER = "supplier";

	/**
	 * 
	 */
	public static final String PROPERTY_INVOICE_NR = "invoiceNr";

	/**
	 * @param orderCost
	 */
	public OrderCostModel(OrderCost orderCost) {
		super(orderCost);
	}

	/**
	 * @return id
	 */
	public Integer getOrderCostId() {
		return object.getOrderCostId();
	}

	/**
	 * @param orderCostId
	 */
	public void setOrderCostId(Integer orderCostId) {
		Integer oldId = getOrderCostId();
		object.setOrderCostId(orderCostId);
		firePropertyChange(PROPERTY_ORDER_COST_ID, oldId, orderCostId);
	}

	/**
	 * @return kostnadstype
	 */
	public CostType getCostType() {
		return object.getCostType();
	}

	/**
	 * @param costType
	 */
	public void setCostType(CostType costType) {
		CostType oldType = getCostType();
		object.setCostType(costType);
		firePropertyChange(PROPERTY_COST_TYPE, oldType, costType);
	}

	/**
	 * @return kostnadsenhet
	 */
	public CostUnit getCostUnit() {
		return object.getCostUnit();
	}

	/**
	 * @param costUnit
	 */
	public void setCostUnit(CostUnit costUnit) {
		CostUnit oldUnit = getCostUnit();
		object.setCostUnit(costUnit);
		firePropertyChange(PROPERTY_COST_UNIT, oldUnit, costUnit);
	}

	/**
	 * @return beløp
	 */
	public BigDecimal getCostAmount() {
		return object.getCostAmount();
	}

	/**
	 * @param costAmount
	 */
	public void setCostAmount(BigDecimal costAmount) {
		BigDecimal oldAmount = getCostAmount();
		object.setCostAmount(costAmount);
		firePropertyChange(PROPERTY_COST_AMOUNT, oldAmount, costAmount);
	}

	/**
	 * @return moms
	 */
	public Integer getInclVat() {
		return object.getInclVat();
	}

	/**
	 * @param inclVat
	 */
	public void setInclVat(Integer inclVat) {
		Integer oldVat = getInclVat();
		object.setInclVat(inclVat);
		firePropertyChange(PROPERTY_INCL_VAT, oldVat, inclVat);
	}

	/**
	 * @return moms
	 */
	public YesNoInteger getIsInclVat() {
		return object.isInclVat();
	}

	/**
	 * @param inclVat
	 */
	public void setIsInclVat(YesNoInteger inclVat) {
		YesNoInteger oldIsVat = getIsInclVat();
		object.setIsInclVat(inclVat);
		firePropertyChange(PROPERTY_IS_INCL_VAT, oldIsVat, inclVat);
	}

	/**
	 * @return leverandør
	 */
	public Supplier getSupplier() {
		return object.getSupplier();
	}

	/**
	 * @param supplier
	 */
	public void setSupplier(Supplier supplier) {
		Supplier oldSupplier = getSupplier();
		object.setSupplier(supplier);
		firePropertyChange(PROPERTY_SUPPLIER, oldSupplier, supplier);
	}

	/**
	 * @return fakturanummer
	 */
	public String getInvoiceNr() {
		return object.getInvoiceNr();
	}

	/**
	 * @param invoiceNr
	 */
	public void setInvoiceNr(String invoiceNr) {
		String oldNr = getInvoiceNr();
		object.setInvoiceNr(invoiceNr);
		firePropertyChange(PROPERTY_INVOICE_NR, oldNr, invoiceNr);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_ORDER_COST_ID)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_COST_TYPE)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_COST_UNIT)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_COST_AMOUNT)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_INCL_VAT)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_IS_INCL_VAT)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_SUPPLIER)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_INVOICE_NR)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public OrderCostModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		OrderCostModel orderCostModel = new OrderCostModel(new OrderCost());
		orderCostModel.setOrderCostId((Integer) presentationModel
				.getBufferedValue(PROPERTY_ORDER_COST_ID));
		orderCostModel.setCostType((CostType) presentationModel
				.getBufferedValue(PROPERTY_COST_TYPE));
		orderCostModel.setCostUnit((CostUnit) presentationModel
				.getBufferedValue(PROPERTY_COST_UNIT));
		orderCostModel.setCostAmount((BigDecimal) presentationModel
				.getBufferedValue(PROPERTY_COST_AMOUNT));
		orderCostModel.setInclVat((Integer) presentationModel
				.getBufferedValue(PROPERTY_INCL_VAT));
		orderCostModel.setIsInclVat((YesNoInteger) presentationModel
				.getBufferedValue(PROPERTY_IS_INCL_VAT));
		orderCostModel.setSupplier((Supplier) presentationModel
				.getBufferedValue(PROPERTY_SUPPLIER));
		orderCostModel.setInvoiceNr((String) presentationModel
				.getBufferedValue(PROPERTY_INVOICE_NR));
		return orderCostModel;
	}
}
