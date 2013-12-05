package no.ugland.utransprod.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Appointment extends BaseObject{
    private Integer appointmentId;
    private Date registered;
    private Integer taskIdx;
    private Integer contactId;
    private Integer documentId;
    private Integer associateId;
    public Appointment() {
        super();
    }
    public Appointment(Integer appointmentId, Date registered, Integer taskIdx, Integer contactId, Integer documentId, Integer associateId) {
        super();
        this.appointmentId = appointmentId;
        this.registered = registered;
        this.taskIdx = taskIdx;
        this.contactId = contactId;
        this.documentId = documentId;
        this.associateId = associateId;
    }
    public Integer getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }
    public Integer getAssociateId() {
        return associateId;
    }
    public void setAssociateId(Integer associateId) {
        this.associateId = associateId;
    }
    public Integer getContactId() {
        return contactId;
    }
    public void setContactId(Integer contactId) {
        this.contactId = contactId;
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
    public Integer getTaskIdx() {
        return taskIdx;
    }
    public void setTaskIdx(Integer taskIdx) {
        this.taskIdx = taskIdx;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Appointment))
            return false;
        Appointment castOther = (Appointment) other;
        return new EqualsBuilder().append(appointmentId,
                castOther.appointmentId).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(appointmentId).toHashCode();
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "appointmentId", appointmentId)
                .append("registered", registered).append("taskIdx", taskIdx)
                .append("contactId", contactId)
                .append("documentId", documentId).append("associateId",
                        associateId).toString();
    }
    
}
