package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Doctmpl extends BaseObject{
    private Integer doctmplId;
    private String name;
    public Doctmpl() {
        super();
    }
    public Doctmpl(Integer doctmplId, String name) {
        super();
        this.doctmplId = doctmplId;
        this.name = name;
    }
    public Integer getDoctmplId() {
        return doctmplId;
    }
    public void setDoctmplId(Integer doctmplId) {
        this.doctmplId = doctmplId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Doctmpl))
            return false;
        Doctmpl castOther = (Doctmpl) other;
        return new EqualsBuilder().append(doctmplId, castOther.doctmplId)
                .append(name, castOther.name).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(doctmplId).append(name)
                .toHashCode();
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "doctmplId", doctmplId).append("name", name).toString();
    }
}
