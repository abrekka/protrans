package no.ugland.utransprod.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import no.ugland.utransprod.importing.CuttingType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Cutting extends BaseObject{
    
    private Integer cuttingId;
    private String version;
    private String proSign;
    private String proId;
    private Set<CuttingLine> cuttingLines;
    private Order order;
    
    public static final Cutting UNKNOWN = new Cutting(){};

    public void setVersion(String aVersion) {
        version=aVersion;
        
    }

    public void setProSign(String aProSign) {
        proSign=aProSign;
        
    }

    public void setProId(String aProId) {
        proId=aProId;
        
    }

   

    public void addCuttingLine(CuttingLine cuttingLine) {
        cuttingLines=cuttingLines==null?new HashSet<CuttingLine>():cuttingLines;
        cuttingLine.setCutting(this);
        cuttingLines.add(cuttingLine);
    }

    public String getVersion() {
        return version;
    }

    public String getProSign() {
        return proSign;
    }

    public String getProId() {
        return proId;
    }

    public Set<CuttingLine> getCuttingLines() {
        return cuttingLines;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Cutting))
            return false;
        Cutting castOther = (Cutting) other;
        return new EqualsBuilder().append(proId, castOther.proId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(proId).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("version", version).append("proSign", proSign).append(
                "proId", proId).append("cuttingLines", cuttingLines).toString();
    }

    public Integer getCuttingId() {
        return cuttingId;
    }
    public void setCuttingId(Integer aId) {
        cuttingId=aId;
    }

    public void setCuttingLines(Set<CuttingLine> cuttingLines) {
        this.cuttingLines = cuttingLines;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String toFileContent() {
        StringBuilder fileBuilder = new StringBuilder();
        fileBuilder.append(CuttingType.VERSION.toFileLine(this,null));
        fileBuilder.append(CuttingType.PRO_SIGN.toFileLine(this,null));
        fileBuilder.append(CuttingType.PRO_ID.toFileLine(this,null));
        
        for(CuttingLine cuttingLine:cuttingLines){
            CuttingType cuttingType = CuttingType.valueOf(cuttingLine.getName());
            fileBuilder.append(cuttingType.toFileLine(this,cuttingLine));
        }
        return fileBuilder.toString();
    }

}
