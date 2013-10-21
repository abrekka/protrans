package no.ugland.utransprod.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Area extends BaseObject{
    private String areaCode;
    private String areaName;
    private County county;
	private BigDecimal snowloadBasicValue;
	private Integer heightLimit;
	private BigDecimal deltaSnowload;
	private BigDecimal snowloadMax;
	private String snowloadComment;
    public Area() {
        super();
    }
    public Area(final String aAreaCode, final String aAreaName, final County aCounty) {
        super();
        this.areaCode = aAreaCode;
        this.areaName = aAreaName;
        this.county = aCounty;
    }
    public final String getAreaCode() {
        return areaCode;
    }
    public final void setAreaCode(final String aAreaCode) {
        this.areaCode = aAreaCode;
    }
    public final String getAreaName() {
        return areaName;
    }
    public final void setAreaName(final String aAreaName) {
        this.areaName = aAreaName;
    }
    public final County getCounty() {
        return county;
    }
    public final void setCounty(County aCounty) {
        this.county = aCounty;
    }
    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof Area)){
            return false;
        }
        Area castOther = (Area) other;
        return new EqualsBuilder().append(areaCode, castOther.areaCode)
                .isEquals();
    }
    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(areaCode).toHashCode();
    }
    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "areaName", areaName).toString();
    }
	public void setSnowloadBasicValue(BigDecimal aSnowloadBasicValue) {
		snowloadBasicValue=aSnowloadBasicValue;
		
	}
	public BigDecimal getSnowloadBasicValue() {
		return snowloadBasicValue;
	}
	public void setHeightLimit(Integer aHeightLimit) {
		heightLimit=aHeightLimit;
		
	}
	public Integer getHeightLimit() {
		return heightLimit;
	}
	public void setDeltaSnowload(BigDecimal aDeltaSnowload) {
		deltaSnowload=aDeltaSnowload;
		
	}
	public BigDecimal getDeltaSnowload() {
		return deltaSnowload;
	}
	public void setSnowloadMax(BigDecimal aSnowloadMax) {
		snowloadMax=aSnowloadMax;
		
	}
	public BigDecimal getSnowloadMax() {
		return snowloadMax;
	}
	public void setSnowloadComment(String aComment) {
		snowloadComment=aComment;
		
	}
	public String getSnowloadComment() {
		return snowloadComment;
	}
}
