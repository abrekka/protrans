package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.JobFunction;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for funksjon
 * 
 * @author atle.brekka
 * 
 */
public class JobFunctionModel extends
		AbstractModel<JobFunction, JobFunctionModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_JOB_FUNCTION_NAME = "jobFunctionName";

	/**
	 * 
	 */
	public static final String PROPERTY_JOB_FUNCTION_DESCRIPTION = "jobFunctionDescription";

	/**
	 * 
	 */
	public static final String PROPERTY_MANAGER = "manager";

	/**
	 * @param jobFunction
	 */
	public JobFunctionModel(JobFunction jobFunction) {
		super(jobFunction);
	}

	/**
	 * @return navn
	 */
	public String getJobFunctionName() {
		return object.getJobFunctionName();
	}

	/**
	 * @param jobFunctionName
	 */
	public void setJobFunctionName(String jobFunctionName) {
		String oldName = getJobFunctionName();
		object.setJobFunctionName(jobFunctionName);
		firePropertyChange(PROPERTY_JOB_FUNCTION_NAME, oldName, jobFunctionName);
	}

	/**
	 * @return beskrivelse
	 */
	public String getJobFunctionDescription() {
		return object.getJobFunctionDescription();
	}

	/**
	 * @param jobFunctionDescription
	 */
	public void setJobFunctionDescription(String jobFunctionDescription) {
		String oldDesc = getJobFunctionDescription();
		object.setJobFunctionDescription(jobFunctionDescription);
		firePropertyChange(PROPERTY_JOB_FUNCTION_DESCRIPTION, oldDesc,
				jobFunctionDescription);
	}

	/**
	 * @return leder
	 */
	public ApplicationUser getManager() {
		return object.getManager();
	}

	/**
	 * @param manager
	 */
	public void setManager(ApplicationUser manager) {
		ApplicationUser oldManager = getManager();
		object.setManager(manager);
		firePropertyChange(PROPERTY_MANAGER, oldManager, manager);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_JOB_FUNCTION_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_JOB_FUNCTION_DESCRIPTION)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_MANAGER)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public JobFunctionModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		JobFunctionModel model = new JobFunctionModel(new JobFunction());
		model.setJobFunctionName((String) presentationModel
				.getBufferedValue(PROPERTY_JOB_FUNCTION_NAME));
		model.setJobFunctionDescription((String) presentationModel
				.getBufferedValue(PROPERTY_JOB_FUNCTION_DESCRIPTION));
		model.setManager((ApplicationUser) presentationModel
				.getBufferedValue(PROPERTY_MANAGER));
		return model;
	}

}
