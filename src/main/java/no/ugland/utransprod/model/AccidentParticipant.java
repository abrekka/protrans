package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AccidentParticipant extends BaseObject {
    private Integer accidentParticipantId;

    private Accident accident;

    private String firstName;

    private String lastName;

    private EmployeeType employeeType;

    public AccidentParticipant() {
        super();
    }

    public AccidentParticipant(final Integer aAccidentPartisipantId,
            final Accident aAccident, final String aFirstName, final String aLastName,
            final EmployeeType aEmployeeType) {
        super();
        this.accidentParticipantId = aAccidentPartisipantId;
        this.accident = aAccident;
        this.firstName = aFirstName;
        this.lastName = aLastName;
        this.employeeType = aEmployeeType;
    }

    public final Accident getAccident() {
        return accident;
    }

    public final void setAccident(final Accident aAccident) {
        this.accident = aAccident;
    }

    public final Integer getAccidentParticipantId() {
        return accidentParticipantId;
    }

    public final void setAccidentParticipantId(final Integer aAccidentPartisipantId) {
        this.accidentParticipantId = aAccidentPartisipantId;
    }

    public final EmployeeType getEmployeeType() {
        return employeeType;
    }

    public final void setEmployeeType(final EmployeeType aEmployeeType) {
        this.employeeType = aEmployeeType;
    }

    public final String getFirstName() {
        return firstName;
    }

    public final void setFirstName(final String aFirstName) {
        this.firstName = aFirstName;
    }

    public final String getLastName() {
        return lastName;
    }

    public final void setLastName(final String aLastName) {
        this.lastName = aLastName;
    }

    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof AccidentParticipant)){
            return false;
        }
        AccidentParticipant castOther = (AccidentParticipant) other;
        return new EqualsBuilder().append(accident, castOther.accident).append(
                firstName, castOther.firstName).append(lastName,
                castOther.lastName)
                .append(employeeType, castOther.employeeType).isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(accident).append(firstName).append(
                lastName).append(employeeType).toHashCode();
    }

    @Override
    public final String toString() {
        if(firstName!=null&&lastName!=null){
        StringBuilder stringBuilder = new StringBuilder(firstName);
        stringBuilder.append(" ").append(lastName);
        if(employeeType!=null){
            stringBuilder.append("-").append(employeeType.getEmployeeTypeName());
        }
        return stringBuilder.toString();
        }
        return "";
    }
    
    public String getObjectString(){
        return toString();
    }
}
