package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Klasse for tabell DEVIATION_STATUS
 * @author atle.brekka
 */
public class DeviationStatus extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Integer deviationStatusId;

    private String deviationStatusName;

    private String deviationStatusDescription;

    private Integer forManager;
    private Integer deviationDone;
    private Integer forDeviation;

	private Integer forAccident;

    public DeviationStatus() {
        super();
    }

    public DeviationStatus(final Integer aDeviationStatusId,
            final String aDeviationStatusDescription, final String aDeviationStatusName,
            final Integer isForManager,final Integer isForDeviationDone) {
        super();
        this.deviationStatusId = aDeviationStatusId;
        this.deviationStatusDescription = aDeviationStatusDescription;
        this.deviationStatusName = aDeviationStatusName;
        this.forManager = isForManager;
        this.deviationDone=isForDeviationDone;
    }

    /**
     * @return beskrivelse
     */
    public String getDeviationStatusDescription() {
        return deviationStatusDescription;
    }

    /**
     * @param deviationStatusDescription
     */
    public void setDeviationStatusDescription(String deviationStatusDescription) {
        this.deviationStatusDescription = deviationStatusDescription;
    }

    /**
     * @return id
     */
    public Integer getDeviationStatusId() {
        return deviationStatusId;
    }

    /**
     * @param deviationStatusId
     */
    public void setDeviationStatusId(Integer deviationStatusId) {
        this.deviationStatusId = deviationStatusId;
    }

    /**
     * @return navn
     */
    public String getDeviationStatusName() {
        return deviationStatusName;
    }

    /**
     * @param deviationStatusName
     */
    public void setDeviationStatusName(String deviationStatusName) {
        this.deviationStatusName = deviationStatusName;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof DeviationStatus))
            return false;
        DeviationStatus castOther = (DeviationStatus) other;
        return new EqualsBuilder().append(deviationStatusName,
                castOther.deviationStatusName).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(deviationStatusName).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "deviationStatusName", deviationStatusName).toString();
    }

    /**
     * @return 1 dersom status er for leder
     */
    public Integer getForManager() {
        return forManager;
    }

    /**
     * @param forManager
     */
    public void setForManager(Integer forManager) {
        this.forManager = forManager;
    }

    public Integer getDeviationDone() {
        return deviationDone;
    }

    public void setDeviationDone(Integer deviationDone) {
        this.deviationDone = deviationDone;
    }

	public Integer getForDeviation() {
		return forDeviation;
	}

	public void setForDeviation(Integer forDeviation) {
		this.forDeviation = forDeviation;
	}

	public Integer getForAccident() {
		return forAccident;
	}

	public void setForAccident(Integer forAccident) {
		this.forAccident = forAccident;
	}
}
