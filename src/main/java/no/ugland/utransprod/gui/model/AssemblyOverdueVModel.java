package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.AssemblyOverdueV;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for monteringer som er på overtid
 * 
 * @author atle.brekka
 * 
 */
public class AssemblyOverdueVModel extends
		AbstractModel<AssemblyOverdueV, AssemblyOverdueVModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_YEAR_WEEK = "yearWeek";

	/**
	 * @param object
	 */
	public AssemblyOverdueVModel(AssemblyOverdueV object) {
		super(object);
	}

	/**
	 * @return år og uke
	 */
	public String getYearWeek() {
		return object.getYearWeek();
	}

	/**
	 * @param yearWeek
	 */
	public void setYearWeek(String yearWeek) {
		String oldYear = getYearWeek();
		if (yearWeek != null) {
			Integer year = Integer.valueOf(yearWeek.substring(0, yearWeek
					.indexOf(" ")));
			Integer week = Integer.valueOf(yearWeek.substring(yearWeek
					.indexOf(" ") + 1));
			object.setAssemblyYear(year);
			object.setAssemblyWeek(week);
		} else {
			object.setAssemblyYear(null);
			object.setAssemblyWeek(null);
		}
		firePropertyChange(PROPERTY_YEAR_WEEK, oldYear, yearWeek);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public AssemblyOverdueVModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		return null;
	}

}
