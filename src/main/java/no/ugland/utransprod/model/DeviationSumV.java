package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.ugland.utransprod.util.Columnable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for view DEVIATION_SUM_V
 * @author atle.brekka
 */
public class DeviationSumV extends BaseObject implements Columnable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
	 * 
	 */
    private DeviationSumVPK deviationSumVPK;

    /**
	 * 
	 */
    private BigDecimal internalCost;

    /**
	 * 
	 */
    private BigDecimal externalCost;

    /**
	 * 
	 */
    private Integer numberOfDeviations;
    private String productArea;

    /**
	 * 
	 */
    private static final List<String> columnNames = new ArrayList<String>(
            Arrays.asList(new String[] { "Produktområde","Avviksfunksjon", "Kategori",
                    "Status", "Antall", "Intern kostnad", "Ekstern kostnad"
                     }));

    /**
	 * 
	 */
    public DeviationSumV() {
        super();
    }

    /**
     * @param deviationSumVPK
     * @param internalCost
     * @param externalCost
     * @param numberOfDeviations
     */
    public DeviationSumV(DeviationSumVPK deviationSumVPK,
            BigDecimal internalCost, BigDecimal externalCost,
            Integer numberOfDeviations, String aProductArea) {
        super();
        this.deviationSumVPK = deviationSumVPK;
        this.internalCost = internalCost;
        this.externalCost = externalCost;
        this.numberOfDeviations = numberOfDeviations;
        this.productArea = aProductArea;
    }

    /**
     * @param jobFunctionName
     * @param functionCategoryName
     * @param deviationStatusName
     * @param numberOfDeviations
     * @param internalCost
     * @param externalCost
     */
    public DeviationSumV(String jobFunctionName, String functionCategoryName,
            String deviationStatusName, Integer numberOfDeviations,
            BigDecimal internalCost, BigDecimal externalCost,
            String aProductArea) {
        this.deviationSumVPK = new DeviationSumVPK();
        deviationSumVPK.setDeviationStatusName(deviationStatusName);
        deviationSumVPK.setFunctionCategoryName(functionCategoryName);
        deviationSumVPK.setJobFunctionName(jobFunctionName);
        this.numberOfDeviations = numberOfDeviations;
        this.internalCost = internalCost;
        this.externalCost = externalCost;
        this.productArea = aProductArea;
    }

    /**
     * @return id
     */
    public DeviationSumVPK getDeviationSumVPK() {
        return deviationSumVPK;
    }

    /**
     * @param deviationSumVPK
     */
    public void setDeviationSumVPK(DeviationSumVPK deviationSumVPK) {
        this.deviationSumVPK = deviationSumVPK;
    }

    /**
     * @return status
     */
    public String getDeviationStatusName() {
        return deviationSumVPK.getDeviationStatusName();
    }

    /**
     * @return kategori
     */
    public String getFunctionCategoryName() {
        return deviationSumVPK.getFunctionCategoryName();
    }

    /**
     * @return funksjon
     */
    public String getJobFunctionName() {
        return deviationSumVPK.getJobFunctionName();
    }

    /**
     * @return registreingsuke
     */
    public Integer getRegistrationWeek() {
        return deviationSumVPK.getRegistrationWeek();
    }

    /**
     * @return registreringsår
     */
    public Integer getRegistrationYear() {
        return deviationSumVPK.getRegistrationYear();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof DeviationSumV))
            return false;
        DeviationSumV castOther = (DeviationSumV) other;
        return new EqualsBuilder().append(deviationSumVPK,
                castOther.deviationSumVPK).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(deviationSumVPK).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "deviationSumVPK", deviationSumVPK).append("internalCost",
                internalCost).append("externalCost", externalCost).append(
                "numberOfDeviations", numberOfDeviations).toString();
    }

    /**
     * @return ekstern kostnad
     */
    public BigDecimal getExternalCost() {
        return externalCost;
    }

    /**
     * @param externalCost
     */
    public void setExternalCost(BigDecimal externalCost) {
        this.externalCost = externalCost;
    }

    /**
     * @return intern kostnad
     */
    public BigDecimal getInternalCost() {
        return internalCost;
    }

    /**
     * @param internalCost
     */
    public void setInternalCost(BigDecimal internalCost) {
        this.internalCost = internalCost;
    }

    /**
     * @return antall avvik
     */
    public Integer getNumberOfDeviations() {
        return numberOfDeviations;
    }

    /**
     * @param numberOfDeviations
     */
    public void setNumberOfDeviations(Integer numberOfDeviations) {
        this.numberOfDeviations = numberOfDeviations;
    }

    /**
     * @see no.ugland.utransprod.util.Columnable#getColumnNames()
     */
    public String[] getColumnNames() {
        return columnNames.toArray(new String[] {});
    }

    /**
     * @see no.ugland.utransprod.util.Columnable#getValueForColumn(java.lang.Integer)
     */
    public Object getValueForColumn(Integer column) {
        switch (column) {
        case 0:
            return productArea;
        case 1:
            return deviationSumVPK.getJobFunctionName();
        case 2:
            return deviationSumVPK.getFunctionCategoryName();
        case 3:
            return deviationSumVPK.getDeviationStatusName();
        case 4:
            return numberOfDeviations;
        case 5:
            return internalCost;
        case 6:
            return externalCost;
        
        default:
            throw new IllegalStateException("Unknown column");
        }
    }

    public String getProductArea() {
        return productArea;
    }

    public void setProductArea(String productArea) {
        this.productArea = productArea;
    }

}
