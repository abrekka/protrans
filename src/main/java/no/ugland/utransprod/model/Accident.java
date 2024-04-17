package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Accident extends BaseObject {
	private Integer accidentId;

	private String registeredBy;

	private Date registrationDate;

	private JobFunction jobFunction;

//    private Integer personalInjury;

	private Date accidentDate;

	private String accidentDescription;

	private String accidentCause;

	private Integer reportedLeader;

	private Integer reportedPolice;
	private Integer reportedArbeidstilsynet;

	private Integer reportedSocialSecurity;

	private Set<AccidentParticipant> accidentParticipants;
	private String accidentTime;

	private String preventiveActionComment;

	private String responsible;

	private BigDecimal absentDays;

	private Date doneDate;

	private DeviationStatus deviationStatus;

	private Integer personalInjuryOver24;
	private Integer personalInjuryUnder24;
	private Integer personalInjuryNotAbsent;
	private Integer notPersonalInjury;
	private Integer numberOfOwnEmployees;

	public Accident() {
		super();
	}

	public Accident(final Integer aAccidentId, final String isRegisteredBy, final Date aRegistrationDate,
			final JobFunction aJobFunction,
//            final Integer hasPersonalDamage,
			final Date aAccidentDate, final String aAccidentDescription, final String aAccidentCause,
			final Integer isReportedLeader, final Integer isReportedPolice, final Integer isReportedSocialService,
			final Set<AccidentParticipant> hasAccidentPartisipants, final String aTime) {
		super();
		this.accidentId = aAccidentId;
		this.registeredBy = isRegisteredBy;
		this.registrationDate = aRegistrationDate;
		this.jobFunction = aJobFunction;
//        this.personalInjury = hasPersonalDamage;
		this.accidentDate = aAccidentDate;
		this.accidentDescription = aAccidentDescription;
		this.accidentCause = aAccidentCause;
		this.reportedLeader = isReportedLeader;
		this.reportedPolice = isReportedPolice;
		this.reportedSocialSecurity = isReportedSocialService;
		this.accidentParticipants = hasAccidentPartisipants;
		this.accidentTime = aTime;
	}

	public Integer getNumberOfOwnEmployees() {
		return numberOfOwnEmployees;
	}

	public void setNumberOfOwnEmployees(Integer numberOfOwnEmployees) {
		this.numberOfOwnEmployees = numberOfOwnEmployees;
	}

	public Integer getPersonalInjuryOver24() {
		return personalInjuryOver24;
	}

	public void setPersonalInjuryOver24(Integer personalInjuryOver24) {
		this.personalInjuryOver24 = personalInjuryOver24;
	}

	public void setPersonalInjuryOver24(Boolean personalInjuryOver24) {
		this.personalInjuryOver24 = personalInjuryOver24 != null && personalInjuryOver24 ? 1 : 0;
	}

	public Integer getPersonalInjuryUnder24() {
		return personalInjuryUnder24;
	}

	public void setPersonalInjuryUnder24(Integer personalInjuryUnder24) {
		this.personalInjuryUnder24 = personalInjuryUnder24;
	}

	public Integer getPersonalInjuryNotAbsent() {
		return personalInjuryNotAbsent;
	}

	public void setPersonalInjuryNotAbsent(Integer personalInjuryNotAbsent) {
		this.personalInjuryNotAbsent = personalInjuryNotAbsent;
	}

	public Integer getNotPersonalInjury() {
		return notPersonalInjury;
	}

	public void setNotPersonalInjury(Integer notPersonalInjury) {
		this.notPersonalInjury = notPersonalInjury;
	}

	public final String getAccidentCause() {
		return accidentCause;
	}

	public final void setAccidentCause(final String aAccidentCause) {
		this.accidentCause = aAccidentCause;
	}

	public final Date getAccidentDate() {
		return accidentDate;
	}

	public final void setAccidentDate(final Date aAccidentDate) {
		this.accidentDate = aAccidentDate;
	}

	public final String getAccidentDescription() {
		return accidentDescription;
	}

	public final void setAccidentDescription(final String aAccidentDescription) {
		this.accidentDescription = aAccidentDescription;
	}

	public final Integer getAccidentId() {
		return accidentId;
	}

	public final void setAccidentId(final Integer aAccidentId) {
		this.accidentId = aAccidentId;
	}

	public final Set<AccidentParticipant> getAccidentParticipants() {
		return accidentParticipants;
	}

	public final void setAccidentParticipants(final Set<AccidentParticipant> hasAccidentPartisipants) {
		this.accidentParticipants = hasAccidentPartisipants;
	}

	public final JobFunction getJobFunction() {
		return jobFunction;
	}

	public final void setJobFunction(final JobFunction aJobFunction) {
		this.jobFunction = aJobFunction;
	}

//    public final Integer getPersonalInjury() {
//        return personalInjury;
//    }
//
//    public final void setPersonalInjury(final Integer wasPersonalInjury) {
//        this.personalInjury = wasPersonalInjury;
//    }

	public final String getRegisteredBy() {
		return registeredBy;
	}

	public final void setRegisteredBy(final String isRegisteredBy) {
		this.registeredBy = isRegisteredBy;
	}

	public final Date getRegistrationDate() {
		return registrationDate;
	}

	public final void setRegistrationDate(final Date aRegistrationDate) {
		this.registrationDate = aRegistrationDate;
	}

	public final Integer getReportedLeader() {
		return reportedLeader;
	}

	public final void setReportedLeader(final Integer isReportedLeader) {
		this.reportedLeader = isReportedLeader;
	}

	public final Integer getReportedPolice() {
		return reportedPolice;
	}

	public final void setReportedPolice(final Integer isReportedPolice) {
		this.reportedPolice = isReportedPolice;
	}

	public Integer getReportedArbeidstilsynet() {
		return reportedArbeidstilsynet;
	}

	public void setReportedArbeidstilsynet(Integer reportedArbeidstilsynet) {
		this.reportedArbeidstilsynet = reportedArbeidstilsynet;
	}

	public final Integer getReportedSocialSecurity() {
		return reportedSocialSecurity;
	}

	public final void setReportedSocialSecurity(final Integer isReportedSocialService) {
		this.reportedSocialSecurity = isReportedSocialService;
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof Accident)) {
			return false;
		}
		Accident castOther = (Accident) other;
		return new EqualsBuilder().append(registeredBy, castOther.registeredBy)
				.append(registrationDate, castOther.registrationDate).append(jobFunction, castOther.jobFunction)
				.append(personalInjuryOver24, castOther.personalInjuryOver24)
				.append(personalInjuryUnder24, castOther.personalInjuryUnder24)
				.append(personalInjuryNotAbsent, castOther.personalInjuryNotAbsent)
				.append(notPersonalInjury, castOther.notPersonalInjury).append(accidentDate, castOther.accidentDate)
				.append(accidentDescription, castOther.accidentDescription)
				.append(accidentCause, castOther.accidentCause).append(reportedLeader, castOther.reportedLeader)
				.append(reportedPolice, castOther.reportedPolice)
				.append(reportedSocialSecurity, castOther.reportedSocialSecurity).isEquals();
	}

	@Override
	public final int hashCode() {
		return new HashCodeBuilder().append(registeredBy).append(registrationDate).append(jobFunction)
				.append(personalInjuryOver24).append(personalInjuryUnder24).append(personalInjuryNotAbsent)
				.append(notPersonalInjury).append(accidentDate).append(accidentDescription).append(accidentCause)
				.append(reportedLeader).append(reportedPolice).append(reportedSocialSecurity).toHashCode();
	}

	@Override
	public final String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("accidentId", accidentId)
				.append("registeredBy", registeredBy).append("registrationDate", registrationDate)
				.append("jobFunction", jobFunction).append("personalDamage", personalInjuryOver24)
				.append("accidentDate", accidentDate).append("accidentDescription", accidentDescription)
				.append("accidentCause", accidentCause).append("reportedLeader", reportedLeader)
				.append("reportedPolice", reportedPolice).append("reportedSocialService", reportedSocialSecurity)
				.append("accidentPartisipants", accidentParticipants).toString();
	}

	public String getAccidentTime() {
		return accidentTime;
	}

	public void setAccidentTime(String time) {
		this.accidentTime = time;
	}

//    public Boolean getPersonalInjuryBool(){
//        return Util.convertNumberToBoolean(personalInjury);
//    }

	public Boolean getPersonalInjuryOver24Bool() {
		return Util.convertNumberToBoolean(personalInjuryOver24);
	}

	public Boolean getPersonalInjuryUnder24Bool() {
		return Util.convertNumberToBoolean(personalInjuryUnder24);
	}

	public Boolean getPersonalInjuryNotAbsentBool() {
		return Util.convertNumberToBoolean(personalInjuryNotAbsent);
	}

	public Boolean getNotPersonalInjuryBool() {
		return Util.convertNumberToBoolean(notPersonalInjury);
	}

	public String getAccidentDateAndTime() {
		if (accidentDate != null) {

			StringBuilder builder = new StringBuilder(Util.SHORT_DATE_FORMAT.format(accidentDate));
			if (accidentTime != null) {
				builder.append("/").append(accidentTime);
			}
			return builder.toString();
		}
		return null;
	}

	public Boolean getReportedLeaderBool() {
		return Util.convertNumberToBoolean(reportedLeader);
	}

	public Boolean getReportedPoliceBool() {
		return Util.convertNumberToBoolean(reportedPolice);
	}

	public Boolean getReportedArbeidstilsynetBool() {
		return Util.convertNumberToBoolean(reportedArbeidstilsynet);
	}

	public Boolean getReportedSocialSecurityBool() {
		return Util.convertNumberToBoolean(reportedSocialSecurity);
	}

	public String getPreventiveActionComment() {
		return preventiveActionComment;
	}

	public void setPreventiveActionComment(String preventiveActionComment) {
		this.preventiveActionComment = preventiveActionComment;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public BigDecimal getAbsentDays() {
		return absentDays;
	}

	public void setAbsentDays(BigDecimal absentDays) {
		this.absentDays = absentDays;
	}

	public Date getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}

	public DeviationStatus getDeviationStatus() {
		return deviationStatus;
	}

	public void setDeviationStatus(DeviationStatus deviationStatus) {
		this.deviationStatus = deviationStatus;
	}
}
