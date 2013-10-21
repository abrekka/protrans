package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.DeviationStatus;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for avviksstatus
 * 
 * @author atle.brekka
 * 
 */
public class DeviationStatusModel extends
		AbstractModel<DeviationStatus, DeviationStatusModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_DEVIATION_STATUS_DESCRIPTION = "deviationStatusDescription";

	/**
	 * 
	 */
	public static final String PROPERTY_DEVIATION_STATUS_NAME = "deviationStatusName";
	/**
	 * 
	 */
	public static final String PROPERTY_FOR_MANAGER_BOOL = "forManagerBool";
    public static final String PROPERTY_DEVIATION_DONE_BOOL = "deviationDoneBool";

	public static final String PROPERTY_FOR_DEVIATION_BOOL = "forDeviationBool";

	public static final String PROPERTY_FOR_ACCIDENT_BOOL = "forAccidentBool";

	/**
	 * @param object
	 */
	public DeviationStatusModel(DeviationStatus object) {
		super(object);
	}

	/**
	 * @return beskrivelse
	 */
	public String getDeviationStatusDescription() {
		return object.getDeviationStatusDescription();
	}

	/**
	 * @param deviationStatusDescription
	 */
	public void setDeviationStatusDescription(String deviationStatusDescription) {
		String oldDesc = getDeviationStatusDescription();
		object.setDeviationStatusDescription(deviationStatusDescription);
		firePropertyChange(PROPERTY_DEVIATION_STATUS_DESCRIPTION, oldDesc,
				deviationStatusDescription);
	}

	/**
	 * @return navn
	 */
	public String getDeviationStatusName() {
		return object.getDeviationStatusName();
	}

	/**
	 * @param deviationStatusName
	 */
	public void setDeviationStatusName(String deviationStatusName) {
		String oldName = getDeviationStatusName();
		object.setDeviationStatusName(deviationStatusName);
		firePropertyChange(PROPERTY_DEVIATION_STATUS_NAME, oldName,
				deviationStatusName);
	}
	/**
	 * 
	 * @return true dersom status er for leder
	 */
	public Boolean getForManagerBool() {
		return Util.convertNumberToBoolean(object.getForManager());
	}

	/**
	 * @param forManager
	 */
	public void setForManagerBool(Boolean forManager) {
		Boolean oldManager = getForManagerBool();
		object.setForManager(Util.convertBooleanToNumber(forManager));
		firePropertyChange(PROPERTY_FOR_MANAGER_BOOL, oldManager ,
				forManager);
	}
    
    public Boolean getDeviationDoneBool() {
        return Util.convertNumberToBoolean(object.getDeviationDone());
    }

    /**
     * @param forManager
     */
    public void setDeviationDoneBool(Boolean deviationDone) {
        Boolean oldDone = getDeviationDoneBool();
        object.setDeviationDone(Util.convertBooleanToNumber(deviationDone));
        firePropertyChange(PROPERTY_DEVIATION_DONE_BOOL, oldDone ,
                deviationDone);
    }
    
    public Boolean getForDeviationBool() {
        return Util.convertNumberToBoolean(object.getForDeviation());
    }

    /**
     * @param forManager
     */
    public void setForDeviationBool(Boolean forDeviation) {
        Boolean oldforDeviation = getForDeviationBool();
        object.setForDeviation(Util.convertBooleanToNumber(forDeviation));
        firePropertyChange(PROPERTY_FOR_DEVIATION_BOOL, oldforDeviation,
                forDeviation);
    }
    public Boolean getForAccidentBool() {
        return Util.convertNumberToBoolean(object.getForAccident());
    }

    /**
     * @param forManager
     */
    public void setForAccidentBool(Boolean forAccident) {
        Boolean oldForAccidnet = getForAccidentBool();
        object.setForAccident(Util.convertBooleanToNumber(forAccident));
        firePropertyChange(PROPERTY_FOR_DEVIATION_BOOL, oldForAccidnet,
                forAccident);
    }

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(
				PROPERTY_DEVIATION_STATUS_DESCRIPTION).addValueChangeListener(
				listener);
		presentationModel.getBufferedModel(PROPERTY_DEVIATION_STATUS_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_FOR_MANAGER_BOOL)
		.addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_DEVIATION_DONE_BOOL)
        .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_FOR_DEVIATION_BOOL)
        .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_FOR_ACCIDENT_BOOL)
        .addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public DeviationStatusModel getBufferedObjectModel(
			PresentationModel presentationModel) {

		DeviationStatusModel model = new DeviationStatusModel(
				new DeviationStatus());
		model.setDeviationStatusDescription((String) presentationModel
				.getBufferedValue(PROPERTY_DEVIATION_STATUS_DESCRIPTION));
		model.setDeviationStatusName((String) presentationModel
				.getBufferedValue(PROPERTY_DEVIATION_STATUS_NAME));
		model.setForManagerBool((Boolean) presentationModel
				.getBufferedValue(PROPERTY_FOR_MANAGER_BOOL));
        model.setDeviationDoneBool((Boolean) presentationModel
                .getBufferedValue(PROPERTY_DEVIATION_DONE_BOOL));
        model.setForDeviationBool((Boolean) presentationModel
                .getBufferedValue(PROPERTY_FOR_DEVIATION_BOOL));
        model.setForAccidentBool((Boolean) presentationModel
                .getBufferedValue(PROPERTY_FOR_ACCIDENT_BOOL));
		return model;
	}

}
