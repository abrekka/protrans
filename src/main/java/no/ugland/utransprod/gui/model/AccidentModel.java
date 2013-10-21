package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import no.ugland.utransprod.model.Accident;
import no.ugland.utransprod.model.AccidentParticipant;
import no.ugland.utransprod.model.DeviationStatus;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.ArrayListModel;

public class AccidentModel extends AbstractModel<Accident, AccidentModel> {
    public static final String PROPERTY_ACCIDENT_ID = "accidentId";
    public static final String PROPERTY_REGISTERED_BY = "registeredBy";
    public static final String PROPERTY_REGISTRATION_DATE = "registrationDate";
    public static final String PROPERTY_JOB_FUNCTION = "jobFunction";
    public static final String PROPERTY_ACCIDENT_DATE = "accidentDate";
    public static final String PROPERTY_ACCIDENT_DESCRIPTION = "accidentDescription";
    public static final String PROPERTY_ACCIDENT_CAUSE = "accidentCause";
    public static final String PROPERTY_PERSONAL_INJURY = "personalInjury";
    public static final String PROPERTY_PARTICIPANT_LIST = "participantList";
    public static final String PROPERTY_TIME = "time";
    public static final String PROPERTY_REPORTED_LEADER_BOOL = "reportedLeaderBool";
    public static final String PROPERTY_REPORTED_POLICE_BOOL = "reportedPoliceBool";
    public static final String PROPERTY_REPORTED_SOCIAL_SECURITY_BOOL = "reportedSocialSecurityBool";
	public static final String PROPERTY_PREVENTIVE_ACTION_COMMENT = "preventiveActionComment";
	public static final String PROPERTY_RESPONSIBLE = "responsible";
	public static final String PROPERTY_ABSENT_DAYS_STRING = "absentDaysString";
	public static final String PROPERTY_DONE_DATE = "doneDate";
	public static final String PROPERTY_DEVIATION_STATUS = "deviationStatus";
    
    private final ArrayListModel participantList;

    public AccidentModel(Accident accident) {
        super(accident);
        participantList = new ArrayListModel();
        if(accident.getAccidentParticipants()!=null){
            participantList.addAll(accident.getAccidentParticipants());
        }
    }
    
    public final Integer getAccidentId() {
        return object.getAccidentId();
    }

    public final void setAccidentId(Integer accidentId) {
        Integer oldId = getAccidentId();
        object.setAccidentId(accidentId);
        firePropertyChange(PROPERTY_ACCIDENT_ID, oldId, accidentId);
    }

    public final String getRegisteredBy() {
        return object.getRegisteredBy();
    }

    public final void setRegisteredBy(String registeredBy) {
        String oldBy = getRegisteredBy();
        object.setRegisteredBy(registeredBy);
        firePropertyChange(PROPERTY_REGISTERED_BY, oldBy, registeredBy);
    }
    
    public final Date getRegistrationDate() {
        return object.getRegistrationDate();
    }

    public final void setRegistrationDate(Date registrationDate) {
        Date oldDate = getRegistrationDate();
        object.setRegistrationDate(Util.convertDate(registrationDate, Util.SHORT_DATE_FORMAT));
        firePropertyChange(PROPERTY_REGISTRATION_DATE, oldDate, registrationDate);
    }
    public final JobFunction getJobFunction() {
        return object.getJobFunction();
    }

    public final void setJobFunction(JobFunction jobFunction) {
        JobFunction oldFunction = getJobFunction();
        object.setJobFunction(jobFunction);
        firePropertyChange(PROPERTY_JOB_FUNCTION, oldFunction, jobFunction);
    }
    public final Date getAccidentDate() {
        return object.getAccidentDate();
    }

    public final void setAccidentDate(Date accidentDate) {
        Date oldDate = getAccidentDate();
        object.setAccidentDate(Util.convertDate(accidentDate, Util.SHORT_DATE_FORMAT));
        firePropertyChange(PROPERTY_ACCIDENT_DATE, oldDate, accidentDate);
    }
    public final String getAccidentDescription() {
        return object.getAccidentDescription();
    }

    public final void setAccidentDescription(String accidentDescription) {
        String oldDesc = getAccidentDescription();
        object.setAccidentDescription(accidentDescription);
        firePropertyChange(PROPERTY_ACCIDENT_DESCRIPTION, oldDesc, accidentDescription);
    }
    public final String getAccidentCause() {
        return object.getAccidentCause();
    }

    public final void setAccidentCause(String accidentCause) {
        String oldCause = getAccidentCause();
        object.setAccidentCause(accidentCause);
        firePropertyChange(PROPERTY_ACCIDENT_CAUSE, oldCause, accidentCause);
    }
    public final Integer getPersonalInjury() {
        return object.getPersonalInjury();
    }

    public final void setPersonalInjury(Integer personalInjury) {
        Integer oldPersonal = getPersonalInjury();
        object.setPersonalInjury(personalInjury);
        firePropertyChange(PROPERTY_PERSONAL_INJURY, oldPersonal, personalInjury);
    }
    
        
    public ArrayListModel getParticipantList() {
        return new ArrayListModel(participantList);
    }

    public void setParticipantList(ArrayListModel parList) {
        ArrayListModel oldList = getParticipantList();
        this.participantList.clear();
        if(parList!=null){
        this.participantList.addAll(parList);
        }
        firePropertyChange(PROPERTY_PARTICIPANT_LIST, oldList, parList);
    }
    
    public final String getTime() {
        return object.getAccidentTime();
    }

    public final void setTime(String time) {
        String oldTime = getTime();
        object.setAccidentTime(time);
        firePropertyChange(PROPERTY_TIME, oldTime, time);
    }
    
    public final Boolean getReportedLeaderBool() {
        return Util.convertNumberToBoolean(object.getReportedLeader());
    }

    public final void setReportedLeaderBool(Boolean reportedLeader) {
        Boolean oldReported = getReportedLeaderBool();
        object.setReportedLeader(Util.convertBooleanToNumber(reportedLeader));
        firePropertyChange(PROPERTY_REPORTED_LEADER_BOOL, oldReported, reportedLeader);
    }
    
    public final Boolean getReportedPoliceBool() {
        return Util.convertNumberToBoolean(object.getReportedPolice());
    }

    public final void setReportedPoliceBool(Boolean reportedPolice) {
        Boolean oldReported = getReportedPoliceBool();
        object.setReportedPolice(Util.convertBooleanToNumber(reportedPolice));
        firePropertyChange(PROPERTY_REPORTED_POLICE_BOOL, oldReported, reportedPolice);
    }
    public final Boolean getReportedSocialSecurityBool() {
        return Util.convertNumberToBoolean(object.getReportedSocialSecurity());
    }

    public final void setReportedSocialSecurityBool(Boolean reportedSocialSecurity) {
        Boolean oldReported = getReportedSocialSecurityBool();
        object.setReportedSocialSecurity(Util.convertBooleanToNumber(reportedSocialSecurity));
        firePropertyChange(PROPERTY_REPORTED_SOCIAL_SECURITY_BOOL, oldReported, reportedSocialSecurity);
    }
    
    public final String getPreventiveActionComment() {
        return object.getPreventiveActionComment();
    }

    public final void setPreventiveActionComment(String comment) {
        String oldComment = getPreventiveActionComment();
        object.setPreventiveActionComment(comment);
        firePropertyChange(PROPERTY_PREVENTIVE_ACTION_COMMENT, oldComment, comment);
    }
    
    public final String getResponsible() {
        return object.getResponsible();
    }

    public final void setResponsible(String responsible) {
        String oldResponsible = getResponsible();
        object.setResponsible(responsible);
        firePropertyChange(PROPERTY_RESPONSIBLE, oldResponsible, responsible);
    }
    
    public final String getAbsentDaysString() {
        return object.getAbsentDays()!=null?String.format("%1.1f", object.getAbsentDays()):null;
    }

    public final void setAbsentDaysString(String days) {
        String oldDays = getAbsentDaysString();
        object.setAbsentDays(Util.convertStringToBigDecimal(days));
        firePropertyChange(PROPERTY_ABSENT_DAYS_STRING, oldDays, days);
    }
    
    public final Date getDoneDate() {
        return object.getDoneDate();
    }

    public final void setDoneDate(Date doneDate) {
        Date oldDate = getDoneDate();
        object.setDoneDate(Util.convertDate(doneDate, Util.SHORT_DATE_FORMAT));
        firePropertyChange(PROPERTY_DONE_DATE, oldDate, doneDate);
    }
    
    public DeviationStatus getDeviationStatus(){
    	return object.getDeviationStatus();
    }
    public void setDeviationStatus(DeviationStatus status){
    	DeviationStatus oldStatus=getDeviationStatus();
    	object.setDeviationStatus(status);
    	firePropertyChange(PROPERTY_DEVIATION_STATUS, oldStatus, status);
    }


    @Override
    public final void addBufferChangeListener(
            final PropertyChangeListener listener,
            final PresentationModel presentationModel) {
        presentationModel.getBufferedModel(PROPERTY_ACCIDENT_ID).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_REGISTERED_BY).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_REGISTRATION_DATE).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_JOB_FUNCTION).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_ACCIDENT_DATE).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_ACCIDENT_DESCRIPTION).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_ACCIDENT_CAUSE).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_PERSONAL_INJURY).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_PARTICIPANT_LIST).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_TIME).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_REPORTED_LEADER_BOOL).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_REPORTED_POLICE_BOOL).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_REPORTED_SOCIAL_SECURITY_BOOL).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_PREVENTIVE_ACTION_COMMENT).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_ABSENT_DAYS_STRING).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_DONE_DATE).addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_DEVIATION_STATUS).addValueChangeListener(listener);
    }

    @Override
    public final AccidentModel getBufferedObjectModel(
            final PresentationModel presentationModel) {
        AccidentModel accidentModel = new AccidentModel(
                new Accident());
        accidentModel.setAccidentId((Integer) presentationModel
                .getBufferedValue(PROPERTY_ACCIDENT_ID));
        accidentModel.setRegisteredBy((String) presentationModel
                .getBufferedValue(PROPERTY_REGISTERED_BY));
        accidentModel.setRegistrationDate((Date) presentationModel
                .getBufferedValue(PROPERTY_REGISTRATION_DATE));
        accidentModel.setJobFunction((JobFunction) presentationModel
                .getBufferedValue(PROPERTY_JOB_FUNCTION));
        accidentModel.setAccidentDate((Date) presentationModel
                .getBufferedValue(PROPERTY_ACCIDENT_DATE));
        accidentModel.setAccidentDescription((String) presentationModel
                .getBufferedValue(PROPERTY_ACCIDENT_DESCRIPTION));
        accidentModel.setAccidentCause((String) presentationModel
                .getBufferedValue(PROPERTY_ACCIDENT_CAUSE));
        accidentModel.setPersonalInjury((Integer) presentationModel
                .getBufferedValue(PROPERTY_PERSONAL_INJURY));
        accidentModel.setParticipantList((ArrayListModel) presentationModel
                .getBufferedValue(PROPERTY_PARTICIPANT_LIST));
        accidentModel.setTime((String) presentationModel
                .getBufferedValue(PROPERTY_TIME));
        accidentModel.setReportedLeaderBool((Boolean) presentationModel
                .getBufferedValue(PROPERTY_REPORTED_LEADER_BOOL));
        accidentModel.setReportedPoliceBool((Boolean) presentationModel
                .getBufferedValue(PROPERTY_REPORTED_POLICE_BOOL));
        accidentModel.setReportedSocialSecurityBool((Boolean) presentationModel
                .getBufferedValue(PROPERTY_REPORTED_SOCIAL_SECURITY_BOOL));
        accidentModel.setPreventiveActionComment((String) presentationModel
                .getBufferedValue(PROPERTY_PREVENTIVE_ACTION_COMMENT));
        accidentModel.setAbsentDaysString((String) presentationModel
                .getBufferedValue(PROPERTY_ABSENT_DAYS_STRING));
        accidentModel.setDoneDate((Date) presentationModel
                .getBufferedValue(PROPERTY_DONE_DATE));
        accidentModel.setDeviationStatus((DeviationStatus) presentationModel
                .getBufferedValue(PROPERTY_DEVIATION_STATUS));
        return accidentModel;
    }

    @Override
    public void viewToModel() {
        Set<AccidentParticipant> participants = object.getAccidentParticipants();
        if(participants==null){
            participants=new HashSet<AccidentParticipant>();
        }
        participants.clear();
        participants.addAll(participantList);
        object.setAccidentParticipants(participants);
    }

}
