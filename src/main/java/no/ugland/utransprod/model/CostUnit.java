package no.ugland.utransprod.model;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klassesom representerer tabell COST_UNIT
 * 
 * @author atle.brekka
 * 
 */
public class CostUnit extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer costUnitId;

	/**
	 * 
	 */
	private String costUnitName;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private Set<OrderCost> orderCosts;

	/**
	 * 
	 */
	public CostUnit() {
		super();
	}

	/**
	 * @param costUnitId
	 * @param costUnitName
	 * @param description
	 * @param orderCosts
	 */
	public CostUnit(Integer costUnitId, String costUnitName,
			String description, Set<OrderCost> orderCosts) {
		super();
		this.costUnitId = costUnitId;
		this.costUnitName = costUnitName;
		this.description = description;
		this.orderCosts = orderCosts;
	}

	/**
	 * @return id
	 */
	public Integer getCostUnitId() {
		return costUnitId;
	}

	/**
	 * @param costUnitId
	 */
	public void setCostUnitId(Integer costUnitId) {
		this.costUnitId = costUnitId;
	}

	/**
	 * @return navn
	 */
	public String getCostUnitName() {
		return costUnitName;
	}

	/**
	 * @param costUnitName
	 */
	public void setCostUnitName(String costUnitName) {
		this.costUnitName = costUnitName;
	}

	/**
	 * @return beskrivelse
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof CostUnit))
			return false;
		CostUnit castOther = (CostUnit) other;
		return new EqualsBuilder().append(costUnitName, castOther.costUnitName)
				.isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(costUnitName).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return costUnitName;
	}

	/**
	 * @return ordrekostnader
	 */
	public Set<OrderCost> getOrderCosts() {
		return orderCosts;
	}

	/**
	 * @param orderCosts
	 */
	public void setOrderCosts(Set<OrderCost> orderCosts) {
		this.orderCosts = orderCosts;
	}
}
