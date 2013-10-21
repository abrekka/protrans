package no.ugland.utransprod.model;

import java.math.BigDecimal;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CraningCost extends BaseObject {
    private Integer craningCostId;

    private String ruleId;

    private String description;

    private BigDecimal costValue;

    public CraningCost() {
        super();
    }

    public CraningCost(Integer craningCostId, String ruleId,
            String description, BigDecimal costValue) {
        super();
        this.craningCostId = craningCostId;
        this.ruleId = ruleId;
        this.description = description;
        this.costValue = costValue;
    }

    public BigDecimal getCostValue() {
        return costValue;
    }

    public void setCostValue(BigDecimal costValue) {
        this.costValue = costValue;
    }

    public Integer getCraningCostId() {
        return craningCostId;
    }

    public void setCraningCostId(Integer craningCostId) {
        this.craningCostId = craningCostId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof CraningCost))
            return false;
        CraningCost castOther = (CraningCost) other;
        return new EqualsBuilder().append(ruleId, castOther.ruleId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ruleId).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "ruleId", ruleId).toString();
    }
}
