package no.ugland.utransprod.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProcentDone extends BaseObject {
    private Integer procentDoneId;

    private Integer procentDoneYear;

    private Integer procentDoneWeek;

    private Integer procent;

    private String procentDoneComment;

    private Order order;
    private Date changeDate;
    private String userName;
    public static final ProcentDone UNKNOWN = new ProcentDone() {};

    public ProcentDone() {
        super();
    }

    public ProcentDone(final Integer aProcentDoneId, final Integer aYear, final Integer aWeek,
            final Integer aProcent, final Order aOrder,final String aComment,final Date aChangeDate,final String aUserName) {
        super();
        this.procentDoneId = aProcentDoneId;
        this.procentDoneYear = aYear;
        this.procentDoneWeek = aWeek;
        this.procent = aProcent;
        this.order = aOrder;
        this.procentDoneComment=aComment;
        this.changeDate=aChangeDate;
        this.userName=aUserName;
    }

    public Integer getProcent() {
        return procent;
    }

    public void setProcent(Integer procent) {
        this.procent = procent;
    }

    public Integer getProcentDoneYear() {
        return procentDoneYear;
    }

    public void setProcentDoneYear(Integer year) {
        this.procentDoneYear = year;
    }

    public Integer getProcentDoneWeek() {
        return procentDoneWeek;
    }

    public void setProcentDoneWeek(Integer week) {
        this.procentDoneWeek = week;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getProcentDoneId() {
        return procentDoneId;
    }

    public void setProcentDoneId(Integer procentDoneId) {
        this.procentDoneId = procentDoneId;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ProcentDone))
            return false;
        ProcentDone castOther = (ProcentDone) other;
        return new EqualsBuilder().append(procentDoneYear, castOther.procentDoneYear).append(procentDoneWeek,
                castOther.procentDoneWeek).append(
                order, castOther.order).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(procentDoneYear).append(procentDoneWeek)
                .append(order).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "procentDoneId", procentDoneId).append("year", procentDoneYear).append(
                "week", procentDoneWeek).append("procent", procent).append("order", order)
                .toString();
    }

    public String getProcentDoneComment() {
        return procentDoneComment;
    }

    public void setProcentDoneComment(String procentDoneComment) {
        this.procentDoneComment = procentDoneComment;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
