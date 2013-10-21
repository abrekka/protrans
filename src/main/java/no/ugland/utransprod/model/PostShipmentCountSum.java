package no.ugland.utransprod.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.util.Columnable;

/**
 * Samler data om ettersendinger og avvik per uke og funksjon
 * @author atle.brekka
 *
 */
/**
 * @author atle.brekka
 * 
 */
public class PostShipmentCountSum implements Columnable {
	/**
	 * 
	 */
	private Integer year;

	/**
	 * 
	 */
	private Integer week;

	/**
	 * 
	 */
	private final Map<String, Integer> functionCount = new Hashtable<String, Integer>();

	/**
	 * 
	 */
	private static final List<String> columnNames = new ArrayList<String>(Arrays.asList(new String[]{"År","Uke"}));

	/**
	 * 
	 */
	public PostShipmentCountSum() {

	}

	/**
	 * @param aYear
	 * @param aWeek
	 */
	public PostShipmentCountSum(Integer aYear, Integer aWeek) {
		year = aYear;
		week = aWeek;
	}
	/**
	 * @return år
	 */
	public Integer getYear(){
		return year;
	}
	/**
	 * @return uke
	 */
	public Integer getWeek(){
		return week;
	}

	/**
	 * Setter antall for en gitt funksjon
	 * 
	 * @param functionName
	 * @param count
	 */
	public void setFunctionCount(String functionName, Integer count) {
		functionCount.put(functionName, count);
		// dersom funksjon ikke finnes som kolonnenavn legges dette til
		if (!columnNames.contains(functionName)) {
			columnNames.add(functionName);
		}
	}

	/**
	 * @see no.ugland.utransprod.util.Columnable#getColumnNames()
	 */
	public String[] getColumnNames() {
		return columnNames.toArray(new String[]{});
	}

	/**
	 * @see no.ugland.utransprod.util.Columnable#getValueForColumn(java.lang.Integer)
	 */
	public Object getValueForColumn(Integer column) {
		switch (column) {
		case 0:
			return year;
		case 1:
			return week;
		default:
			return functionCount.get(columnNames.get(column));
		}
	}
	/**
	 * Resetter kolonnenavn
	 */
	public static void clearColumnNames(){
		columnNames.clear();
		columnNames.add("År");
		columnNames.add("Uke");
	}
}
