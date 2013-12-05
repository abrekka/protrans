package no.ugland.utransprod.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Document extends BaseObject{
    private Integer documentId;
    private Date registered;
    public Document() {
        super();
    }
    public Document(Integer documentId, Date registered) {
        super();
        this.documentId = documentId;
        this.registered = registered;
    }
    public Integer getDocumentId() {
        return documentId;
    }
    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }
    public Date getRegistered() {
        return registered;
    }
    public void setRegistered(Date registered) {
        this.registered = registered;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Document))
            return false;
        Document castOther = (Document) other;
        return new EqualsBuilder().append(documentId, castOther.documentId)
                .isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(documentId).toHashCode();
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "documentId", documentId).append("registered", registered)
                .toString();
    }
}
