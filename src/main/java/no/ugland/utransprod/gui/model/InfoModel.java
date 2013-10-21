package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.Date;

import no.ugland.utransprod.model.Info;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for info
 * 
 * @author atle.brekka
 * 
 */
public class InfoModel extends AbstractModel<Info, InfoModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_INFO_TEXT = "infoText";

	/**
	 * 
	 */
	public static final String PROPERTY_DATE_FROM = "dateFrom";

	/**
	 * 
	 */
	public static final String PROPERTY_DATE_TO = "dateTo";

	/**
	 * @param object
	 */
	public InfoModel(Info object) {
		super(object);
	}

	/**
	 * @return tekst
	 */
	public String getInfoText() {
		return object.getInfoText();
	}

	/**
	 * @param infoText
	 */
	public void setInfoText(String infoText) {
		String oldInfo = getInfoText();
		object.setInfoText(infoText);
		firePropertyChange(PROPERTY_INFO_TEXT, oldInfo, infoText);
	}

	/**
	 * @return fradato
	 */
	public Date getDateFrom() {
		return object.getDateFrom();
	}

	/**
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		Date oldDate = getDateFrom();
		object.setDateFrom(dateFrom);
		firePropertyChange(PROPERTY_DATE_FROM, oldDate, dateFrom);
	}

	/**
	 * @return tildato
	 */
	public Date getDateTo() {
		return object.getDateTo();
	}

	/**
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		Date oldDate = getDateTo();
		object.setDateTo(dateTo);
		firePropertyChange(PROPERTY_DATE_TO, oldDate, dateTo);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_DATE_FROM)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_DATE_TO)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_INFO_TEXT)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public InfoModel getBufferedObjectModel(PresentationModel presentationModel) {
		InfoModel infoModel = new InfoModel(new Info());
		infoModel.setDateFrom((Date) presentationModel
				.getBufferedValue(PROPERTY_DATE_FROM));
		infoModel.setDateTo((Date) presentationModel
				.getBufferedValue(PROPERTY_DATE_TO));
		infoModel.setInfoText((String) presentationModel
				.getBufferedValue(PROPERTY_INFO_TEXT));

		return infoModel;
	}

}
