package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Klasse for tabell APPLICATION_PARAM
 * @author atle.brekka
 */
public class ApplicationParam extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Integer paramId;

    private String paramName;

    private String paramValue;

    private String paramType;

	private Integer userAccessible;

    public ApplicationParam() {
        super();
    }

    /**
     * @param aParamId
     * @param aParamName
     * @param aParamValue
     * @param aParamType
     */
    public ApplicationParam(final Integer aParamId, final String aParamName,
            final String aParamValue, final String aParamType) {
        super();
        this.paramId = aParamId;
        this.paramName = aParamName;
        this.paramValue = aParamValue;
        this.paramType = aParamType;
    }

    /**
     * @return id
     */
    public final Integer getParamId() {
        return paramId;
    }

    /**
     * @param aParamId
     */
    public final void setParamId(final Integer aParamId) {
        this.paramId = aParamId;
    }

    /**
     * @return prameternavn
     */
    public final String getParamName() {
        return paramName;
    }

    /**
     * @param aParamName
     */
    public final void setParamName(final String aParamName) {
        this.paramName = aParamName;
    }

    /**
     * @return parameterverdi
     */
    public final String getParamValue() {
        return paramValue;
    }

    /**
     * @param aParamValue
     */
    public final void setParamValue(final String aParamValue) {
        this.paramValue = aParamValue;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof ApplicationParam)){
            return false;
        }
        ApplicationParam castOther = (ApplicationParam) other;
        return new EqualsBuilder().append(paramName, castOther.paramName)
                .isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(paramName).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public final String toString() {
        if(paramName.indexOf("kolli_")!=-1||paramName.indexOf("takstol_artikkel_")!=-1){
            return paramValue;
        }
        return new ToStringBuilder(this).append("paramName", paramName)
                .toString();
    }

    /**
     * @return parametertype
     */
    public final String getParamType() {
        return paramType;
    }

    /**
     * @param aParamType
     */
    public final void setParamType(final String aParamType) {
        this.paramType = aParamType;
    }

	public void setUserAccessible(Integer accessible) {
		this.userAccessible=accessible;
		
	}

	public Integer getUserAccessible() {
		return userAccessible;
	}

	

}
