package no.ugland.utransprod.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.beans.Model;

/**
 * Klasse som brukes for å sette utvlag ved statistikk
 * @author atle.brekka
 *
 */
public class QuerySettings extends Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static final String PROPERTY_DATE_ENUM_STRING = "dateEnumString";
	/**
	 * 
	 */
	public static final String PROPERTY_OPERAND_ENUM_STRING = "operandEnumString";
	/**
	 * 
	 */
	public static final String PROPERTY_USE_DATES = "useDates";
	/**
	 * 
	 */
	public static final String PROPERTY_USE_WEEKS = "useWeeks";
	/**
	 * 
	 */
	public static final String PROPERTY_DATE_FROM="dateFrom";
	/**
	 * 
	 */
	public static final String PROPERTY_DATE_TO="dateTo";
	/**
	 * 
	 */
	public static final String PROPERTY_YEAR_FROM="yearFrom";
	/**
	 * 
	 */
	public static final String PROPERTY_WEEK_FROM="weekFrom";
	/**
	 * 
	 */
	public static final String PROPERTY_YEAR_TO="yearTo";
	/**
	 * 
	 */
	public static final String PROPERTY_WEEK_TO="weekTo";
	/**
	 * 
	 */
	private DateEnum dateEnum;
	/**
	 * 
	 */
	private OperandEnum operandEnum;
	
	/**
	 * 
	 */
	private Date dateFrom;
	/**
	 * 
	 */
	private Date dateTo;
	/**
	 * 
	 */
	private Integer yearFrom;
	/**
	 * 
	 */
	private Integer weekFrom;
	/**
	 * 
	 */
	private Integer yearTo;
	/**
	 * 
	 */
	private Integer weekTo;
	/**
	 * 
	 */
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 
	 */
	public QuerySettings(){
		this(DateEnum.INVOICE_DATE,OperandEnum.AND);
		
	}
	
	/**
	 * @param dateEnum
	 * @param operandEnum
	 */
	public QuerySettings(DateEnum dateEnum, OperandEnum operandEnum) {
		super();
		this.dateEnum = dateEnum;
		this.operandEnum = operandEnum;
		this.yearFrom = Calendar.getInstance().get(Calendar.YEAR);
		this.yearTo = Calendar.getInstance().get(Calendar.YEAR);
	}
	/**
	 * Henter datoutvalg
	 * @return datoutvalg
	 */
	public DateEnum getDateEnum() {
		return dateEnum;
	}
	/**
	 * @param dateEnum
	 */
	public void setDateEnum(DateEnum dateEnum) {
		this.dateEnum = dateEnum;
	}
	/**
	 * Henter operator
	 * @return operator
	 */
	public OperandEnum getOperandEnum() {
		return operandEnum;
	}
	/**
	 * @param operandEnum
	 */
	public void setOperandEnum(OperandEnum operandEnum) {
		this.operandEnum = operandEnum;
	}
	
	/**
	 * Henter datoenumstreng
	 * @return datoenumstreng
	 */
	public String getDateEnumString(){
		if(dateEnum!=null){
		return dateEnum.getDateEnumString();
		}
		return "";
	}
	
	/**
	 * @param dateEnumString
	 */
	public void setDateEnumString(String dateEnumString){
		String oldString = getDateEnumString();
		Boolean oldBool = getUseDates();
		Boolean oldBoolWeeks = getUseWeeks();
		dateEnum = DateEnum.getDateEnum(dateEnumString);
		Boolean newBool = getUseDates();
		Boolean newBoolWeeks = getUseWeeks();
		firePropertyChange(PROPERTY_DATE_ENUM_STRING, oldString, dateEnumString);
		firePropertyChange(PROPERTY_USE_DATES, oldBool, newBool);
		firePropertyChange(PROPERTY_USE_WEEKS, oldBoolWeeks, newBoolWeeks);
	}
	
	/**
	 * Henter operatorenumstreng
	 * @return operatorenumstreng
	 */
	public String getOperandEnumString(){
		if(operandEnum!=null){
		return operandEnum.getOperandEnumString();
		}
		return "";
	}
	
	/**
	 * @param operandEnumString
	 */
	public void setOperandEnumString(String operandEnumString){
		String oldString = getOperandEnumString();
		operandEnum = OperandEnum.getOperandEnum(operandEnumString);
		firePropertyChange(PROPERTY_OPERAND_ENUM_STRING, oldString, operandEnumString);
	}
	
	/**
	 * Sjekker om det er datoer som brukes
	 * @return true dersom datoer brukes
	 */
	public Boolean getUseDates(){
		switch(dateEnum){
		case INVOICE_DATE:
		case ORDER_DATE:
			return Boolean.TRUE;
			default:
				return Boolean.FALSE;
		}
	}
	
	/**
	 * Sjekker om transportuke brukes
	 * @return true dersom transportuke brukes
	 */
	public Boolean getUseWeeks(){
		switch(dateEnum){
		case INVOICE_DATE:
		case ORDER_DATE:
			return Boolean.FALSE;
			default:
				return Boolean.TRUE;
		}
	}

	/**
	 * Henter dato fra
	 * @return dato
	 */
	public Date getDateFrom() {
		return Util.cloneDate(dateFrom);
	}

	/**
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		Date oldDate = getDateFrom();
		this.dateFrom = Util.cloneDate(dateFrom);
		firePropertyChange(PROPERTY_DATE_FROM, oldDate, dateFrom);
	}

	/**
	 * Henter dato til
	 * @return dato
	 */
	public Date getDateTo() {
		return Util.cloneDate(dateTo);
	}

	/**
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		Date oldDate = getDateTo();
		this.dateTo = Util.cloneDate(dateTo);
		firePropertyChange(PROPERTY_DATE_TO,oldDate, dateTo);
	}

	/**
	 * Henter uke fra
	 * @return uke
	 */
	public Integer getWeekFrom() {
		return weekFrom;
	}

	/**
	 * @param weekFrom
	 */
	public void setWeekFrom(Integer weekFrom) {
		Integer oldWeek = getWeekFrom();
		this.weekFrom = weekFrom;
		firePropertyChange(PROPERTY_WEEK_FROM, oldWeek, weekFrom);
	}

	/**
	 * Henter uke til
	 * @return uke
	 */
	public Integer getWeekTo() {
		return weekTo;
	}

	/**
	 * @param weekTo
	 */
	public void setWeekTo(Integer weekTo) {
		Integer oldWeek = getWeekTo();
		this.weekTo = weekTo;
		firePropertyChange(PROPERTY_WEEK_TO, oldWeek, weekTo);
	}

	/**
	 * Henter fra år
	 * @return år
	 */
	public Integer getYearFrom() {
		return yearFrom;
	}

	/**
	 * @param yearFrom
	 */
	public void setYearFrom(Integer yearFrom) {
		Integer oldYear = getYearFrom();
		this.yearFrom = yearFrom;
		firePropertyChange(PROPERTY_YEAR_FROM, oldYear, yearFrom);
	}

	/**
	 * Henter år til
	 * @return år
	 */
	public Integer getYearTo() {
		return yearTo;
	}

	/**
	 * @param yearTo
	 */
	public void setYearTo(Integer yearTo) {
		Integer oldYear = getYearTo();
		this.yearTo = yearTo;
		firePropertyChange(PROPERTY_YEAR_TO, oldYear, yearTo);
	}
	
	/**
	 * Sjekker om utvalg er riktig satt
	 * @return feilmelding dersom noe er galt
	 */
	public String checkSettings(){
		String errorString = null;
		switch(dateEnum){
		case INVOICE_DATE:
		case ORDER_DATE:
			if(dateFrom==null||dateTo==null){
				errorString = "Dato fra og til må settes";
			}
			break;
		case TRANSPORT_WEEK:
			if(yearFrom==null||yearTo==null||weekFrom==null||weekTo==null){
				errorString = "Uke fra og til må settes";
			}
			break;
		}
		return errorString;
	}
	
	/**
	 * Henter formatert streng for transoprtuke fra
	 * @return streng
	 */
	public String getTransportFrom(){
		return String.format("%1$d%2$02d", yearFrom,weekFrom+10);
	}
	
	/**
	 * Henter formatert streng for transport uke til
	 * @return streng
	 */
	public String getTransportTo(){
		return String.format("%1$d%2$02d", yearTo,weekTo+10);
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String dateString = "";
		switch(dateEnum){
		case INVOICE_DATE:
		case ORDER_DATE:
			if(dateFrom!=null&&dateTo!=null){
			dateString = dateFormat.format(dateFrom)+"-"+dateFormat.format(dateTo);
			}
			break;
		case TRANSPORT_WEEK:
			dateString = yearFrom.toString()+" "+weekFrom+"-"+yearTo+" "+weekTo;
			break;
		}
		return dateEnum.toString()+" "+dateString + " "+operandEnum.getOperandEnumString();
	}
	
}
