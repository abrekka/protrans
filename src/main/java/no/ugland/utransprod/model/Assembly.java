package no.ugland.utransprod.model;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klasse som representerer tabell ASSEMBLY
 * 
 * @author atle.brekka
 */
public class Assembly extends BaseObject implements Comparable<Assembly> {
    private static final long serialVersionUID = 1L;

    private Integer assemblyId;

    // private AssemblyTeam assemblyTeam;
    private Supplier supplier;

    private Order order;

    private Integer assemblyYear;

    private Integer assemblyWeek;

    // private String comment;

    private String planned;

    private String firstPlanned;

    private Date assembliedDate;

    private Deviation deviation;

    private Integer inactive;

    private String sentBase = "0";

    private String assemblyComment;
    private Integer assemblyWeekTo;

    public static final Assembly UNKNOWN = new Assembly() {

	private static final long serialVersionUID = 7650049577311091406L;
    };

    public Assembly() {
	super();
    }

    /**
     * @param aAssemblyId
     * @param aAssemblyTeam
     * @param aOrder
     * @param aAssemblyYear
     * @param aAssemblyWeek
     * @param aComment
     * @param plannedDay
     * @param firstPlannedDay
     * @param aAssembliedDate
     * @param aDeviation
     * @param isInactive
     */
    public Assembly(final Integer aAssemblyId,
    // final AssemblyTeam aAssemblyTeam,
	    final Supplier aSupplier, final Order aOrder, final Integer aAssemblyYear, final Integer aAssemblyWeek,
	    // final String aComment,
	    final String plannedDay, final String firstPlannedDay, final Date aAssembliedDate, final Deviation aDeviation, final Integer isInactive) {
	super();
	this.assemblyId = aAssemblyId;
	// this.assemblyTeam = aAssemblyTeam;
	this.order = aOrder;
	this.assemblyYear = aAssemblyYear;
	this.assemblyWeek = aAssemblyWeek;
	// this.comment = aComment;
	this.planned = plannedDay;
	this.firstPlanned = firstPlannedDay;
	this.assembliedDate = aAssembliedDate;
	this.deviation = aDeviation;
	this.inactive = isInactive;
	this.supplier = aSupplier;
    }

    /**
     * @param aAssemblyId
     * @param aAssemblyTeam
     * @param aOrder
     * @param aAssemblyYear
     * @param aAssemblyWeek
     * @param aComment
     * @param plannedDay
     * @param firstPlannedDay
     * @param aAssembliedDate
     * @param isInactive
     */
    public Assembly(final Integer aAssemblyId,
    // final AssemblyTeam aAssemblyTeam,
	    final Supplier aSupplier, final Order aOrder, final Integer aAssemblyYear, final Integer aAssemblyWeek,
	    // final String aComment,
	    final String plannedDay, final String firstPlannedDay, final Date aAssembliedDate, final Integer isInactive) {
	this(aAssemblyId,
	// aAssemblyTeam,
		aSupplier, aOrder, aAssemblyYear, aAssemblyWeek,
		// aComment,
		plannedDay, firstPlannedDay, aAssembliedDate, null, isInactive);
    }
    
    public Integer getAssemblyWeekTo() {
		return assemblyWeekTo;
	}
    
    public void setAssemblyWeekTo(Integer assemblyWeekTo) {
		this.assemblyWeekTo = assemblyWeekTo;
	}

    /**
     * @return uke
     */
    public final Integer getAssemblyWeek() {
	return assemblyWeek;
    }

    /**
     * @param aAssemblyWeek
     */
    public final void setAssemblyWeek(final Integer aAssemblyWeek) {
	this.assemblyWeek = aAssemblyWeek;
    }

    /**
     * @return id
     */
    public final Integer getAssemblyId() {
	return assemblyId;
    }

    /**
     * @param aAssemblyId
     */
    public final void setAssemblyId(final Integer aAssemblyId) {
	this.assemblyId = aAssemblyId;
    }

    /**
     * @return monteringsteam
     */
    /*
     * public final AssemblyTeam getAssemblyTeam() { return assemblyTeam; }
     */

    /**
     * @param aAssemblyTeam
     */
    /*
     * public final void setAssemblyTeam(final AssemblyTeam aAssemblyTeam) {
     * this.assemblyTeam = aAssemblyTeam; }
     */

    /**
     * @return år
     */
    public final Integer getAssemblyYear() {
	return assemblyYear;
    }

    /**
     * @param aAssemblyYear
     */
    public final void setAssemblyYear(final Integer aAssemblyYear) {
	this.assemblyYear = aAssemblyYear;
    }

    /**
     * @return kommentar
     */
    /*
     * public final String getComment() { return comment; }
     */

    /**
     * @param aComment
     */
    /*
     * public final void setComment(final String aComment) { this.comment =
     * aComment; }
     */

    /**
     * @return ordre
     */
    public final Order getOrder() {
	return order;
    }

    /**
     * @param aOrder
     */
    public final void setOrder(final Order aOrder) {
	this.order = aOrder;
    }

    /**
     * @param other
     * @return sammenlikning
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public final int compareTo(final Assembly other) {
	return new CompareToBuilder().append(supplier, other.supplier).append(assemblyYear, other.assemblyYear)
		.append(assemblyWeek, other.assemblyWeek)// .append(comment,
							 // other.comment)
		.toComparison();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public final String toString() {
	return assemblyWeek != null ? assemblyWeek.toString() : "" + "-" + assemblyYear + "-" + supplier;
    }

    /**
     * @return først planlagt
     */
    public final String getFirstPlanned() {
	return firstPlanned;
    }

    /**
     * @param firstPlannedDay
     */
    public final void setFirstPlanned(final String firstPlannedDay) {
	this.firstPlanned = firstPlannedDay;
    }

    /**
     * @return planlagt
     */
    public final String getPlanned() {
	return planned;
    }

    /**
     * @param plannedDay
     */
    public final void setPlanned(final String plannedDay) {
	this.planned = plannedDay;
    }

    /**
     * @return true dersom montert
     */
    public final Boolean getAssembliedBool() {
	if (assembliedDate != null) {
	    return true;
	}
	return false;
    }

    /**
     * @param assemblied
     */
    public final void setAssembliedBool(final Boolean assemblied) {
	if (assemblied) {
	    this.assembliedDate = Calendar.getInstance().getTime();
	} else {
	    this.assembliedDate = null;
	}
    }

    /**
     * @return år og uke som streng
     */
    public final String getYearWeekString() {
	return String.format("%1$d%2$02d", assemblyYear, assemblyWeek);
    }

    /**
     * @return dato for montering
     */
    public final Date getAssembliedDate() {
	return assembliedDate;
    }

    /**
     * @param aAssembliedDate
     */
    public final void setAssembliedDate(final Date aAssembliedDate) {
	this.assembliedDate = aAssembliedDate;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object other) {
	if (!(other instanceof Assembly)) {
	    return false;
	}
	Assembly castOther = (Assembly) other;
	return new EqualsBuilder().append(supplier, castOther.supplier).append(order, castOther.order).append(assemblyYear, castOther.assemblyYear)
		.append(assemblyWeek, castOther.assemblyWeek).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public final int hashCode() {
	return new HashCodeBuilder().append(supplier).append(order).append(assemblyYear).append(assemblyWeek).toHashCode();
    }

    /**
     * @return avvik
     */
    public final Deviation getDeviation() {
	return deviation;
    }

    /**
     * @param aDeviation
     */
    public final void setDeviation(final Deviation aDeviation) {
	this.deviation = aDeviation;
    }

    /**
     * @return 1 dersom inaktiv
     */
    public final Integer getInactive() {
	return inactive;
    }

    /**
     * @param isInactive
     */
    public final void setInactive(final Integer isInactive) {
	this.inactive = isInactive;
    }

    public Supplier getSupplier() {
	return supplier;
    }

    public void setSupplier(Supplier supplier) {
	this.supplier = supplier;
    }

    public boolean isForWeek(final Integer year, final Integer week) {
	return assemblyYear.equals(year) && assemblyWeek.equals(week);
    }

    public String getAssemblyTeamNr() {
	return supplier == null ? null : supplier.getSupplierNr();
    }

    public String getSentBase() {
	return sentBase;
    }

    public void setSentBase(String sentBase) {
	this.sentBase = sentBase;
    }

    public String getAssemblyteamName() {
	return supplier == null ? "" : supplier.getSupplierName();
    }

    public void setAssemblyComment(String assemblyComment) {
	this.assemblyComment = assemblyComment;
    }

    public String getAssemblyComment() {
	return assemblyComment;
    }

}
