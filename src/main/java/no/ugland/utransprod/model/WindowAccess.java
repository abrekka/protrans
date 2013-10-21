package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for tabell WINDOW_ACCESS
 * 
 * @author atle.brekka
 * 
 */
public class WindowAccess extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer windowAccessId;

	/**
	 * 
	 */
	private String windowName;
    private String tableNames;

	/**
	 * 
	 */
	public WindowAccess() {
		super();
	}

	/**
	 * @param windowAccessId
	 * @param windowName
	 */
	public WindowAccess(Integer windowAccessId, String windowName,final String someTableName) {
		super();
		this.windowAccessId = windowAccessId;
		this.windowName = windowName;
        this.tableNames=someTableName;
	}

	/**
	 * @return id
	 */
	public Integer getWindowAccessId() {
		return windowAccessId;
	}

	/**
	 * @param windowAccessId
	 */
	public void setWindowAccessId(Integer windowAccessId) {
		this.windowAccessId = windowAccessId;
	}

	/**
	 * @return vindusnavn
	 */
	public String getWindowName() {
		return windowName;
	}

	/**
	 * @param windowName
	 */
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof WindowAccess))
			return false;
		WindowAccess castOther = (WindowAccess) other;
		return new EqualsBuilder().append(windowName, castOther.windowName)
				.isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(windowName).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"windowName", windowName).toString();
	}

    public String getTableNames() {
        return tableNames;
    }

    public void setTableNames(String tableNames) {
        this.tableNames = tableNames;
    }
}
