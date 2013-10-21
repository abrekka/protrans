package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.math.MathContext;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view NOKKEL_PRODUKSJON_V
 * 
 * @author atle.brekka
 * 
 */
public class NokkelProduksjonV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private NokkelProduksjonVPK nokkelProduksjonVPK;

	/**
	 * 
	 */
	private Integer countOrderReady;

	/**
	 * 
	 */
	private BigDecimal packageSumWeek;

	/**
	 * 
	 */
	private Integer deviationCount;

	/**
	 * 
	 */
	private BigDecimal internalCost;

	/**
	 * 
	 */
	private BigDecimal budgetValue;

	/**
	 * 
	 */
	private BigDecimal budgetDeviation;

	/**
	 * 
	 */
	private BigDecimal budgetDeviationProc;
    

	/**
	 * 
	 */
	public NokkelProduksjonV() {
		super();
        
	}

	/**
	 * @param orderReadyYear
	 * @param orderReadyWeek
	 * @param countOrderReady
	 * @param budgetValue
	 * @param packageSumWeek
	 * @param productArea
	 * @param budgetDeviation 
	 */
	public NokkelProduksjonV(Integer orderReadyYear, Integer orderReadyWeek,
			Integer countOrderReady, BigDecimal budgetValue,
			BigDecimal packageSumWeek, String productArea,BigDecimal budgetDeviation) {
		nokkelProduksjonVPK = new NokkelProduksjonVPK(orderReadyYear,
				orderReadyWeek, productArea,null);
		this.countOrderReady = countOrderReady;
		this.budgetValue = budgetValue;
		this.packageSumWeek = packageSumWeek;
		this.budgetDeviation=budgetDeviation;
	}
    public NokkelProduksjonV(Integer orderReadyYear, Integer orderReadyWeek,
            Integer countOrderReady, BigDecimal budgetValue,
            BigDecimal packageSumWeek, String productArea, BigDecimal budgetDeviation,String productAreaGroupName) {
        nokkelProduksjonVPK = new NokkelProduksjonVPK(orderReadyYear,
                orderReadyWeek, productArea,productAreaGroupName);
        this.countOrderReady = countOrderReady;
        this.budgetValue = budgetValue;
        this.packageSumWeek = packageSumWeek;
        this.budgetDeviation=budgetDeviation;
    }

	/**
	 * @param nokkelProduksjonVPK
	 * @param countOrderReady
	 * @param packageSumWeek
	 * @param deviationCount
	 * @param internalCost
	 * @param budgetValue
	 * @param budgetDeviation
	 * @param budgetDeviationProc
	 */
	public NokkelProduksjonV(NokkelProduksjonVPK nokkelProduksjonVPK,
			Integer countOrderReady, BigDecimal packageSumWeek,
			Integer deviationCount, BigDecimal internalCost,
			BigDecimal budgetValue, BigDecimal budgetDeviation,
			BigDecimal budgetDeviationProc) {
		super();
		this.nokkelProduksjonVPK = nokkelProduksjonVPK;
		this.countOrderReady = countOrderReady;
		this.packageSumWeek = packageSumWeek;
		this.deviationCount = deviationCount;
		this.internalCost = internalCost;
		this.budgetValue = budgetValue;
		this.budgetDeviation = budgetDeviation;
		this.budgetDeviationProc = budgetDeviationProc;
	}

	/**
	 * @return år
	 */
	public Integer getOrderReadyYear() {
		return nokkelProduksjonVPK.getOrderReadyYear();
	}

	/**
	 * @return uke
	 */
	public Integer getOrderReadyWeek() {
		return nokkelProduksjonVPK.getOrderReadyWeek();
	}

	/**
	 * @return produktområde
	 */
	public String getProductArea() {
		return nokkelProduksjonVPK.getProductArea();
	}
    public String getProductAreaGroupName() {
        return nokkelProduksjonVPK.getProductAreaGroupName();
    }

	/**
	 * @return budsjettavvik
	 */
	public BigDecimal getBudgetDeviation() {
		return budgetDeviation;
	}

	/**
	 * @param budgetDeviation
	 */
	public void setBudgetDeviation(BigDecimal budgetDeviation) {
		this.budgetDeviation = budgetDeviation;
	}

	/**
	 * @return budsjettavvik i prosent
	 */
	public BigDecimal getBudgetDeviationProc() {
		return budgetDeviationProc;
	}

	/**
	 * @param budgetDeviationProc
	 */
	public void setBudgetDeviationProc(BigDecimal budgetDeviationProc) {
		this.budgetDeviationProc = budgetDeviationProc;
	}

	/**
	 * @return budsjett
	 */
	public BigDecimal getBudgetValue() {
		return budgetValue;
	}

	/**
	 * @param budgetValue
	 */
	public void setBudgetValue(BigDecimal budgetValue) {
		this.budgetValue = budgetValue;
	}

	/**
	 * @return antall ordre klar
	 */
	public Integer getCountOrderReady() {
		return countOrderReady;
	}

	/**
	 * @param countOrderReady
	 */
	public void setCountOrderReady(Integer countOrderReady) {
		this.countOrderReady = countOrderReady;
	}

	/**
	 * @return antall avvik
	 */
	public Integer getDeviationCount() {
		return deviationCount;
	}

	/**
	 * @param deviationCount
	 */
	public void setDeviationCount(Integer deviationCount) {
		this.deviationCount = deviationCount;
	}

	/**
	 * @return intern kostnad ved avvik
	 */
	public BigDecimal getInternalCost() {
		return internalCost;
	}

	/**
	 * @param internalCost
	 */
	public void setInternalCost(BigDecimal internalCost) {
		this.internalCost = internalCost;
	}

	/**
	 * @return id
	 */
	public NokkelProduksjonVPK getNokkelProduksjonVPK() {
		return nokkelProduksjonVPK;
	}

	/**
	 * @param nokkelProduksjonVPK
	 */
	public void setNokkelProduksjonVPK(NokkelProduksjonVPK nokkelProduksjonVPK) {
		this.nokkelProduksjonVPK = nokkelProduksjonVPK;
	}

	/**
	 * @return sum pakket
	 */
	public BigDecimal getPackageSumWeek() {
		return packageSumWeek;
	}

	/**
	 * @param packageSumWeek
	 */
	public void setPackageSumWeek(BigDecimal packageSumWeek) {
		this.packageSumWeek = packageSumWeek;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof NokkelProduksjonV))
			return false;
		NokkelProduksjonV castOther = (NokkelProduksjonV) other;
		return new EqualsBuilder().append(nokkelProduksjonVPK,
				castOther.nokkelProduksjonVPK).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(nokkelProduksjonVPK).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"nokkelProduksjonVPK", nokkelProduksjonVPK).append(
				"countOrderReady", countOrderReady).append("packageSumWeek",
				packageSumWeek).append("deviationCount", deviationCount)
				.append("internalCost", internalCost).append("budgetValue",
						budgetValue).append("budgetDeviation", budgetDeviation)
				.append("budgetDeviationProc", budgetDeviationProc).toString();
	}

	/**
	 * Kalkulerer avviksprosent for budsjett
	 */
	public void calculateDeviationProc() {
		if (budgetValue != null && budgetValue.intValue() != 0) {
			budgetDeviationProc = budgetDeviation.divide(budgetValue,
					new MathContext(100)).multiply(BigDecimal.valueOf(100),
					new MathContext(100));
		} else {
			budgetDeviationProc = BigDecimal.valueOf(100);
		}
	}


}
