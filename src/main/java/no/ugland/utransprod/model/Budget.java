package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for tabell PRODUCTION_BUDGET
 * 
 * @author atle.brekka
 * 
 */
public class Budget extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Integer budgetYear;

	private Integer budgetWeek;

	private Integer budgetId;

	private ProductArea productArea;
	private Integer budgetType;

	private BigDecimal budgetValue;
	private ApplicationUser applicationUser;

	private BigDecimal budgetValueOffer;

	private BigDecimal budgetHours;
	public static final Budget UNKNOWN = new Budget() {

		@Override
		public BigDecimal getBudgetValue() {
			return BigDecimal.ZERO;
		}

	};

	public Budget() {
		super();
	}

	/**
	 * @param budgetYear
	 * @param budgetWeek
	 * @param budgetValue
	 */
	public Budget(Integer budgetYear, Integer budgetWeek, BigDecimal budgetValue) {
		this(budgetYear, budgetWeek, null, budgetValue, null, null);

	}

	/**
	 * @param budgetYear
	 * @param budgetWeek
	 * @param budgetId
	 * @param budgetValue
	 * @param productArea
	 */
	public Budget(Integer budgetYear, Integer budgetWeek, Integer budgetId,
			BigDecimal budgetValue, ProductArea productArea, Integer budgetType) {
		super();
		this.budgetYear = budgetYear;
		this.budgetWeek = budgetWeek;
		this.budgetId = budgetId;
		this.budgetValue = budgetValue;
		this.productArea = productArea;
		this.budgetType = budgetType;
	}

	/**
	 * @return id
	 */
	public Integer getBudgetId() {
		return budgetId;
	}

	/**
	 * @param budgetId
	 */
	public void setBudgetId(Integer budgetId) {
		this.budgetId = budgetId;
	}

	/**
	 * @return budsjettverdi
	 */
	public BigDecimal getBudgetValue() {
		return budgetValue;
	}

	/**
	 * @return budjsettverdi som streng
	 */
	public String getBudgetValueString() {
		if (budgetValue != null) {
			return budgetValue.toString();
		}
		return null;
	}

	/**
	 * @param budgetValue
	 */
	public void setBudgetValue(BigDecimal budgetValue) {
		this.budgetValue = budgetValue;
	}

	/**
	 * @return uke
	 */
	public Integer getBudgetWeek() {
		return budgetWeek;
	}

	/**
	 * @return år
	 */
	public Integer getBudgetYear() {
		return budgetYear;
	}

	/**
	 * @param budgetWeek
	 */
	public void setBudgetWeek(Integer budgetWeek) {
		this.budgetWeek = budgetWeek;
	}

	/**
	 * @param budgetYear
	 */
	public void setBudgetYear(Integer budgetYear) {
		this.budgetYear = budgetYear;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Budget))
			return false;
		Budget castOther = (Budget) other;
		return new EqualsBuilder().append(budgetWeek, castOther.budgetWeek)
				.append(budgetId, castOther.budgetId).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(budgetWeek).append(budgetId)
				.toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
				.append("budgetYear", budgetYear)
				.append("budgetWeek", budgetWeek).append("budgetId", budgetId)
				.append("budgetValue", budgetValue).toString();
	}

	/**
	 * @return produktområde
	 */
	public ProductArea getProductArea() {
		return productArea;
	}

	/**
	 * @param productArea
	 */
	public void setProductArea(ProductArea productArea) {
		this.productArea = productArea;
	}

	public Integer getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(Integer budgetType) {
		this.budgetType = budgetType;
	}

	public ApplicationUser getApplicationUser() {
		return applicationUser;
	}

	public void setApplicationUser(ApplicationUser applicationUser) {
		this.applicationUser = applicationUser;
	}

	public BigDecimal getBudgetValueOffer() {
		return budgetValueOffer != null ? budgetValueOffer : BigDecimal.ZERO;
	}

	public void setBudgetValueOffer(BigDecimal budgetValueOffer) {
		this.budgetValueOffer = budgetValueOffer;
	}

	public BigDecimal getBudgetHours() {
		return this.budgetHours;
	}

	public void setBudgetHours(BigDecimal hours) {
		this.budgetHours = hours;
	}

}
