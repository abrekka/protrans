package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Klasse for view ASSEMBLY_OVERDUE_V
 * 
 * @author atle.brekka
 * 
 */
public class AssemblyOverdueV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer assemblyYear;

	/**
	 * 
	 */
	private Integer assemblyWeek;

	/**
	 * 
	 */
	public AssemblyOverdueV() {
		super();
	}

	/**
	 * @param assemblyYear
	 * @param assemblyWeek
	 */
	public AssemblyOverdueV(Integer assemblyYear, Integer assemblyWeek) {
		super();
		this.assemblyYear = assemblyYear;
		this.assemblyWeek = assemblyWeek;
	}

	/**
	 * @return monteringsuke
	 */
	public Integer getAssemblyWeek() {
		return assemblyWeek;
	}

	/**
	 * @param assemblyWeek
	 */
	public void setAssemblyWeek(Integer assemblyWeek) {
		this.assemblyWeek = assemblyWeek;
	}

	/**
	 * @return monteringsår
	 */
	public Integer getAssemblyYear() {
		return assemblyYear;
	}

	/**
	 * @param assemblyYear
	 */
	public void setAssemblyYear(Integer assemblyYear) {
		this.assemblyYear = assemblyYear;
	}

	/**
	 * @return år og uke som streng
	 */
	public String getYearWeek() {
		if (assemblyYear != null) {
			return String.valueOf(assemblyYear) + " " + assemblyWeek;
		}
		return null;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof AssemblyOverdueV))
			return false;
		AssemblyOverdueV castOther = (AssemblyOverdueV) other;
		return new EqualsBuilder().append(assemblyYear, castOther.assemblyYear)
				.append(assemblyWeek, castOther.assemblyWeek).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(assemblyYear).append(assemblyWeek)
				.toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"assemblyYear", assemblyYear).append("assemblyWeek",
				assemblyWeek).toString();
	}
}
