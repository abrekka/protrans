package no.ugland.utransprod.gui.model;

import java.util.Comparator;

import no.ugland.utransprod.model.Assembly;

/**
 * Klasse som brukes for å sammenlikne montering
 * 
 * @author atle.brekka
 * 
 */
public class AssemblyComparator implements Comparator<Assembly> {

	/**
	 * @param o1
	 * @param o2
	 * @return int
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Assembly o1, Assembly o2) {
		if (o1.getAssemblyId() > o2.getAssemblyId()) {
			return -1;
		} else if (o1.getAssemblyId() < o2.getAssemblyId()) {
			return 1;
		}
		return 0;
	}

}