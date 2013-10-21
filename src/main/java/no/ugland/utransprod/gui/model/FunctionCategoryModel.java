package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell
 * @author atle.brekka
 *
 */
public class FunctionCategoryModel extends AbstractModel<FunctionCategory,FunctionCategoryModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static final String PROPERTY_FUNCTION_CATEGORY_DESCRIPTION="functionCategoryDescription";
	/**
	 * 
	 */
	public static final String PROPERTY_FUNCTION_CATEGORY_NAME="functionCategoryName";
	/**
	 * 
	 */
	public static final String PROPERTY_JOB_FUNCTION="jobFunction";

	/**
	 * @param object
	 */
	public FunctionCategoryModel(FunctionCategory object) {
		super(object);
	}
	
	/**
	 * @return beskrivelse
	 */
	public String getFunctionCategoryDescription(){
		return object.getFunctionCategoryDescription();
	}
	/**
	 * @param functionCategoryDescription
	 */
	public void setFunctionCategoryDescription(String functionCategoryDescription){
		String oldDesc = getFunctionCategoryDescription();
		object.setFunctionCategoryDescription(functionCategoryDescription);
		firePropertyChange(PROPERTY_FUNCTION_CATEGORY_DESCRIPTION, oldDesc, functionCategoryDescription);
	}
	
	/**
	 * @return navn
	 */
	public String getFunctionCategoryName(){
		return object.getFunctionCategoryName();
	}
	/**
	 * @param functionCategoryName
	 */
	public void setFunctionCategoryName(String functionCategoryName){
		String oldName = getFunctionCategoryName();
		object.setFunctionCategoryName(functionCategoryName);
		firePropertyChange(PROPERTY_FUNCTION_CATEGORY_NAME, oldName, functionCategoryName);
	}
	
	/**
	 * @return funksjon
	 */
	public JobFunction getJobFunction(){
		return object.getJobFunction();
	}
	/**
	 * @param jobFunction
	 */
	public void setJobFunction(JobFunction jobFunction){
		JobFunction oldFunction = getJobFunction();
		object.setJobFunction(jobFunction);
		firePropertyChange(PROPERTY_JOB_FUNCTION, oldFunction, jobFunction);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener, com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_FUNCTION_CATEGORY_DESCRIPTION).addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_FUNCTION_CATEGORY_NAME).addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_JOB_FUNCTION).addValueChangeListener(listener);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public FunctionCategoryModel getBufferedObjectModel(PresentationModel presentationModel) {
		FunctionCategoryModel model = new FunctionCategoryModel(new FunctionCategory());
		model.setFunctionCategoryDescription((String)presentationModel.getBufferedValue(PROPERTY_FUNCTION_CATEGORY_DESCRIPTION));
		model.setFunctionCategoryName((String)presentationModel.getBufferedValue(PROPERTY_FUNCTION_CATEGORY_NAME));
		model.setJobFunction((JobFunction)presentationModel.getBufferedValue(PROPERTY_JOB_FUNCTION));
		return model;
	}

}
