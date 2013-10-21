package no.ugland.utransprod.model;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klasse som representerer tabell COST_TYPE
 * 
 * @author atle.brekka
 * 
 */
public class CostType extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer costTypeId;

	/**
	 * 
	 */
	private String costTypeName;

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
	public CostType() {
		super();
	}

	/**
	 * @param costTypeId
	 * @param costTypeName
	 * @param description
	 * @param orderCosts
	 */
	public CostType(Integer costTypeId, String costTypeName,
			String description, Set<OrderCost> orderCosts) {
		super();
		this.costTypeId = costTypeId;
		this.costTypeName = costTypeName;
		this.description = description;
		this.orderCosts = orderCosts;
	}

	/**
	 * @return id
	 */
	public Integer getCostTypeId() {
		return costTypeId;
	}

	/**
	 * @param costTypeId
	 */
	public void setCostTypeId(Integer costTypeId) {
		this.costTypeId = costTypeId;
	}

	/**
	 * @return navn
	 */
	public String getCostTypeName() {
		return costTypeName;
	}

	/**
	 * @param costTypeName
	 */
	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
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
		if (!(other instanceof CostType))
			return false;
		CostType castOther = (CostType) other;
		return new EqualsBuilder().append(costTypeName, castOther.costTypeName)
				.isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(costTypeName).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return costTypeName;
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
