/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import com.jgoodies.binding.list.ArrayListModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.Accident;
/*     */ import no.ugland.utransprod.model.AccidentParticipant;
/*     */ import no.ugland.utransprod.model.DeviationStatus;
/*     */ import no.ugland.utransprod.model.JobFunction;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AccidentModel extends AbstractModel<Accident, AccidentModel> {
/*     */    public static final String PROPERTY_ACCIDENT_ID = "accidentId";
/*     */    public static final String PROPERTY_REGISTERED_BY = "registeredBy";
/*     */    public static final String PROPERTY_REGISTRATION_DATE = "registrationDate";
/*     */    public static final String PROPERTY_JOB_FUNCTION = "jobFunction";
/*     */    public static final String PROPERTY_ACCIDENT_DATE = "accidentDate";
/*     */    public static final String PROPERTY_ACCIDENT_DESCRIPTION = "accidentDescription";
/*     */    public static final String PROPERTY_ACCIDENT_CAUSE = "accidentCause";
/*     */    public static final String PROPERTY_PERSONAL_INJURY = "personalInjury";
/*     */    public static final String PROPERTY_PARTICIPANT_LIST = "participantList";
/*     */    public static final String PROPERTY_TIME = "time";
/*     */    public static final String PROPERTY_REPORTED_LEADER_BOOL = "reportedLeaderBool";
/*     */    public static final String PROPERTY_REPORTED_POLICE_BOOL = "reportedPoliceBool";
/*     */    public static final String PROPERTY_REPORTED_SOCIAL_SECURITY_BOOL = "reportedSocialSecurityBool";
/*     */    public static final String PROPERTY_PREVENTIVE_ACTION_COMMENT = "preventiveActionComment";
/*     */    public static final String PROPERTY_RESPONSIBLE = "responsible";
/*     */    public static final String PROPERTY_ABSENT_DAYS_STRING = "absentDaysString";
/*     */    public static final String PROPERTY_DONE_DATE = "doneDate";
/*     */    public static final String PROPERTY_DEVIATION_STATUS = "deviationStatus";
/*     */    private final ArrayListModel participantList = new ArrayListModel();
/*     */ 
/*     */    public AccidentModel(Accident accident) {
/*  40 */       super(accident);
/*     */ 
/*  42 */       if (accident.getAccidentParticipants() != null) {
/*  43 */          this.participantList.addAll(accident.getAccidentParticipants());
/*     */       }
/*  45 */    }
/*     */ 
/*     */    public final Integer getAccidentId() {
/*  48 */       return ((Accident)this.object).getAccidentId();
/*     */    }
/*     */ 
/*     */    public final void setAccidentId(Integer accidentId) {
/*  52 */       Integer oldId = this.getAccidentId();
/*  53 */       ((Accident)this.object).setAccidentId(accidentId);
/*  54 */       this.firePropertyChange("accidentId", oldId, accidentId);
/*  55 */    }
/*     */ 
/*     */    public final String getRegisteredBy() {
/*  58 */       return ((Accident)this.object).getRegisteredBy();
/*     */    }
/*     */ 
/*     */    public final void setRegisteredBy(String registeredBy) {
/*  62 */       String oldBy = this.getRegisteredBy();
/*  63 */       ((Accident)this.object).setRegisteredBy(registeredBy);
/*  64 */       this.firePropertyChange("registeredBy", oldBy, registeredBy);
/*  65 */    }
/*     */ 
/*     */    public final Date getRegistrationDate() {
/*  68 */       return ((Accident)this.object).getRegistrationDate();
/*     */    }
/*     */ 
/*     */    public final void setRegistrationDate(Date registrationDate) {
/*  72 */       Date oldDate = this.getRegistrationDate();
/*  73 */       ((Accident)this.object).setRegistrationDate(Util.convertDate(registrationDate, Util.SHORT_DATE_FORMAT));
/*  74 */       this.firePropertyChange("registrationDate", oldDate, registrationDate);
/*  75 */    }
/*     */    public final JobFunction getJobFunction() {
/*  77 */       return ((Accident)this.object).getJobFunction();
/*     */    }
/*     */ 
/*     */    public final void setJobFunction(JobFunction jobFunction) {
/*  81 */       JobFunction oldFunction = this.getJobFunction();
/*  82 */       ((Accident)this.object).setJobFunction(jobFunction);
/*  83 */       this.firePropertyChange("jobFunction", oldFunction, jobFunction);
/*  84 */    }
/*     */    public final Date getAccidentDate() {
/*  86 */       return ((Accident)this.object).getAccidentDate();
/*     */    }
/*     */ 
/*     */    public final void setAccidentDate(Date accidentDate) {
/*  90 */       Date oldDate = this.getAccidentDate();
/*  91 */       ((Accident)this.object).setAccidentDate(Util.convertDate(accidentDate, Util.SHORT_DATE_FORMAT));
/*  92 */       this.firePropertyChange("accidentDate", oldDate, accidentDate);
/*  93 */    }
/*     */    public final String getAccidentDescription() {
/*  95 */       return ((Accident)this.object).getAccidentDescription();
/*     */    }
/*     */ 
/*     */    public final void setAccidentDescription(String accidentDescription) {
/*  99 */       String oldDesc = this.getAccidentDescription();
/* 100 */       ((Accident)this.object).setAccidentDescription(accidentDescription);
/* 101 */       this.firePropertyChange("accidentDescription", oldDesc, accidentDescription);
/* 102 */    }
/*     */    public final String getAccidentCause() {
/* 104 */       return ((Accident)this.object).getAccidentCause();
/*     */    }
/*     */ 
/*     */    public final void setAccidentCause(String accidentCause) {
/* 108 */       String oldCause = this.getAccidentCause();
/* 109 */       ((Accident)this.object).setAccidentCause(accidentCause);
/* 110 */       this.firePropertyChange("accidentCause", oldCause, accidentCause);
/* 111 */    }
/*     */    public final Integer getPersonalInjury() {
/* 113 */       return ((Accident)this.object).getPersonalInjury();
/*     */    }
/*     */ 
/*     */    public final void setPersonalInjury(Integer personalInjury) {
/* 117 */       Integer oldPersonal = this.getPersonalInjury();
/* 118 */       ((Accident)this.object).setPersonalInjury(personalInjury);
/* 119 */       this.firePropertyChange("personalInjury", oldPersonal, personalInjury);
/* 120 */    }
/*     */ 
/*     */ 
/*     */    public ArrayListModel getParticipantList() {
/* 124 */       return new ArrayListModel(this.participantList);
/*     */    }
/*     */ 
/*     */    public void setParticipantList(ArrayListModel parList) {
/* 128 */       ArrayListModel oldList = this.getParticipantList();
/* 129 */       this.participantList.clear();
/* 130 */       if (parList != null) {
/* 131 */          this.participantList.addAll(parList);
/*     */       }
/* 133 */       this.firePropertyChange("participantList", oldList, parList);
/* 134 */    }
/*     */ 
/*     */    public final String getTime() {
/* 137 */       return ((Accident)this.object).getAccidentTime();
/*     */    }
/*     */ 
/*     */    public final void setTime(String time) {
/* 141 */       String oldTime = this.getTime();
/* 142 */       ((Accident)this.object).setAccidentTime(time);
/* 143 */       this.firePropertyChange("time", oldTime, time);
/* 144 */    }
/*     */ 
/*     */    public final Boolean getReportedLeaderBool() {
/* 147 */       return Util.convertNumberToBoolean(((Accident)this.object).getReportedLeader());
/*     */    }
/*     */ 
/*     */    public final void setReportedLeaderBool(Boolean reportedLeader) {
/* 151 */       Boolean oldReported = this.getReportedLeaderBool();
/* 152 */       ((Accident)this.object).setReportedLeader(Util.convertBooleanToNumber(reportedLeader));
/* 153 */       this.firePropertyChange("reportedLeaderBool", oldReported, reportedLeader);
/* 154 */    }
/*     */ 
/*     */    public final Boolean getReportedPoliceBool() {
/* 157 */       return Util.convertNumberToBoolean(((Accident)this.object).getReportedPolice());
/*     */    }
/*     */ 
/*     */    public final void setReportedPoliceBool(Boolean reportedPolice) {
/* 161 */       Boolean oldReported = this.getReportedPoliceBool();
/* 162 */       ((Accident)this.object).setReportedPolice(Util.convertBooleanToNumber(reportedPolice));
/* 163 */       this.firePropertyChange("reportedPoliceBool", oldReported, reportedPolice);
/* 164 */    }
/*     */    public final Boolean getReportedSocialSecurityBool() {
/* 166 */       return Util.convertNumberToBoolean(((Accident)this.object).getReportedSocialSecurity());
/*     */    }
/*     */ 
/*     */    public final void setReportedSocialSecurityBool(Boolean reportedSocialSecurity) {
/* 170 */       Boolean oldReported = this.getReportedSocialSecurityBool();
/* 171 */       ((Accident)this.object).setReportedSocialSecurity(Util.convertBooleanToNumber(reportedSocialSecurity));
/* 172 */       this.firePropertyChange("reportedSocialSecurityBool", oldReported, reportedSocialSecurity);
/* 173 */    }
/*     */ 
/*     */    public final String getPreventiveActionComment() {
/* 176 */       return ((Accident)this.object).getPreventiveActionComment();
/*     */    }
/*     */ 
/*     */    public final void setPreventiveActionComment(String comment) {
/* 180 */       String oldComment = this.getPreventiveActionComment();
/* 181 */       ((Accident)this.object).setPreventiveActionComment(comment);
/* 182 */       this.firePropertyChange("preventiveActionComment", oldComment, comment);
/* 183 */    }
/*     */ 
/*     */    public final String getResponsible() {
/* 186 */       return ((Accident)this.object).getResponsible();
/*     */    }
/*     */ 
/*     */    public final void setResponsible(String responsible) {
/* 190 */       String oldResponsible = this.getResponsible();
/* 191 */       ((Accident)this.object).setResponsible(responsible);
/* 192 */       this.firePropertyChange("responsible", oldResponsible, responsible);
/* 193 */    }
/*     */ 
/*     */    public final String getAbsentDaysString() {
/* 196 */       return ((Accident)this.object).getAbsentDays() != null ? String.format("%1.1f", ((Accident)this.object).getAbsentDays()) : null;
/*     */    }
/*     */ 
/*     */    public final void setAbsentDaysString(String days) {
/* 200 */       String oldDays = this.getAbsentDaysString();
/* 201 */       ((Accident)this.object).setAbsentDays(Util.convertStringToBigDecimal(days));
/* 202 */       this.firePropertyChange("absentDaysString", oldDays, days);
/* 203 */    }
/*     */ 
/*     */    public final Date getDoneDate() {
/* 206 */       return ((Accident)this.object).getDoneDate();
/*     */    }
/*     */ 
/*     */    public final void setDoneDate(Date doneDate) {
/* 210 */       Date oldDate = this.getDoneDate();
/* 211 */       ((Accident)this.object).setDoneDate(Util.convertDate(doneDate, Util.SHORT_DATE_FORMAT));
/* 212 */       this.firePropertyChange("doneDate", oldDate, doneDate);
/* 213 */    }
/*     */ 
/*     */    public DeviationStatus getDeviationStatus() {
/* 216 */       return ((Accident)this.object).getDeviationStatus();
/*     */    }
/*     */    public void setDeviationStatus(DeviationStatus status) {
/* 219 */       DeviationStatus oldStatus = this.getDeviationStatus();
/* 220 */       ((Accident)this.object).setDeviationStatus(status);
/* 221 */       this.firePropertyChange("deviationStatus", oldStatus, status);
/* 222 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 229 */       presentationModel.getBufferedModel("accidentId").addValueChangeListener(listener);
/* 230 */       presentationModel.getBufferedModel("registeredBy").addValueChangeListener(listener);
/* 231 */       presentationModel.getBufferedModel("registrationDate").addValueChangeListener(listener);
/* 232 */       presentationModel.getBufferedModel("jobFunction").addValueChangeListener(listener);
/* 233 */       presentationModel.getBufferedModel("accidentDate").addValueChangeListener(listener);
/* 234 */       presentationModel.getBufferedModel("accidentDescription").addValueChangeListener(listener);
/* 235 */       presentationModel.getBufferedModel("accidentCause").addValueChangeListener(listener);
/* 236 */       presentationModel.getBufferedModel("personalInjury").addValueChangeListener(listener);
/* 237 */       presentationModel.getBufferedModel("participantList").addValueChangeListener(listener);
/* 238 */       presentationModel.getBufferedModel("time").addValueChangeListener(listener);
/* 239 */       presentationModel.getBufferedModel("reportedLeaderBool").addValueChangeListener(listener);
/* 240 */       presentationModel.getBufferedModel("reportedPoliceBool").addValueChangeListener(listener);
/* 241 */       presentationModel.getBufferedModel("reportedSocialSecurityBool").addValueChangeListener(listener);
/* 242 */       presentationModel.getBufferedModel("preventiveActionComment").addValueChangeListener(listener);
/* 243 */       presentationModel.getBufferedModel("absentDaysString").addValueChangeListener(listener);
/* 244 */       presentationModel.getBufferedModel("doneDate").addValueChangeListener(listener);
/* 245 */       presentationModel.getBufferedModel("deviationStatus").addValueChangeListener(listener);
/* 246 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public final AccidentModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 251 */       AccidentModel accidentModel = new AccidentModel(new Accident());
/*     */ 
/* 253 */       accidentModel.setAccidentId((Integer)presentationModel.getBufferedValue("accidentId"));
/*     */ 
/* 255 */       accidentModel.setRegisteredBy((String)presentationModel.getBufferedValue("registeredBy"));
/*     */ 
/* 257 */       accidentModel.setRegistrationDate((Date)presentationModel.getBufferedValue("registrationDate"));
/*     */ 
/* 259 */       accidentModel.setJobFunction((JobFunction)presentationModel.getBufferedValue("jobFunction"));
/*     */ 
/* 261 */       accidentModel.setAccidentDate((Date)presentationModel.getBufferedValue("accidentDate"));
/*     */ 
/* 263 */       accidentModel.setAccidentDescription((String)presentationModel.getBufferedValue("accidentDescription"));
/*     */ 
/* 265 */       accidentModel.setAccidentCause((String)presentationModel.getBufferedValue("accidentCause"));
/*     */ 
/* 267 */       accidentModel.setPersonalInjury((Integer)presentationModel.getBufferedValue("personalInjury"));
/*     */ 
/* 269 */       accidentModel.setParticipantList((ArrayListModel)presentationModel.getBufferedValue("participantList"));
/*     */ 
/* 271 */       accidentModel.setTime((String)presentationModel.getBufferedValue("time"));
/*     */ 
/* 273 */       accidentModel.setReportedLeaderBool((Boolean)presentationModel.getBufferedValue("reportedLeaderBool"));
/*     */ 
/* 275 */       accidentModel.setReportedPoliceBool((Boolean)presentationModel.getBufferedValue("reportedPoliceBool"));
/*     */ 
/* 277 */       accidentModel.setReportedSocialSecurityBool((Boolean)presentationModel.getBufferedValue("reportedSocialSecurityBool"));
/*     */ 
/* 279 */       accidentModel.setPreventiveActionComment((String)presentationModel.getBufferedValue("preventiveActionComment"));
/*     */ 
/* 281 */       accidentModel.setAbsentDaysString((String)presentationModel.getBufferedValue("absentDaysString"));
/*     */ 
/* 283 */       accidentModel.setDoneDate((Date)presentationModel.getBufferedValue("doneDate"));
/*     */ 
/* 285 */       accidentModel.setDeviationStatus((DeviationStatus)presentationModel.getBufferedValue("deviationStatus"));
/*     */ 
/* 287 */       return accidentModel;
/*     */    }
/*     */ 
/*     */ 
/*     */    public void viewToModel() {
/* 292 */       Set<AccidentParticipant> participants = ((Accident)this.object).getAccidentParticipants();
/* 293 */       if (participants == null) {
/* 294 */          participants = new HashSet();
/*     */       }
/* 296 */       ((Set)participants).clear();
/* 297 */       ((Set)participants).addAll(this.participantList);
/* 298 */       ((Accident)this.object).setAccidentParticipants((Set)participants);
/* 299 */    }
/*     */ }
