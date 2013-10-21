package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.PreventiveAction;
import no.ugland.utransprod.model.PreventiveActionComment;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for prevantivt tiltak
 * 
 * @author atle.brekka
 * 
 */
public class PreventiveActionModel extends
		AbstractModel<PreventiveAction, PreventiveActionModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_PREVENTIVE_ACTION_ID = "preventiveActionId";

	/**
	 * 
	 */
	public static final String PROPERTY_MANAGER = "manager";

	/**
	 * 
	 */
	public static final String PROPERTY_JOB_FUNCTION = "jobFunction";

	/**
	 * 
	 */
	public static final String PROPERTY_FUNCTION_CATEGORY = "functionCategory";

	/**
	 * 
	 */
	public static final String PROPERTY_DESCRIPTION = "description";

	/**
	 * 
	 */
	public static final String PROPERTY_EXPECTED_OUTCOME = "expectedOutcome";

	/**
	 * 
	 */
	public static final String PROPERTY_COMMENTS = "comments";

	/**
	 * 
	 */
	public static final String PROPERTY_PROJECT_CLOSED_BOOLEAN = "projectClosedBoolean";

	/**
	 * 
	 */
	public static final String PROPERTY_PREVENTIVE_ACTION_NAME = "preventiveActionName";

	/**
	 * 
	 */
	private final List<PreventiveActionComment> commentList;

	/**
	 * @param object
	 */
	public PreventiveActionModel(PreventiveAction object) {
		super(object);
		commentList = new ArrayList<PreventiveActionComment>();
		if (object.getPreventiveActionComments() != null) {
			commentList.addAll(object.getPreventiveActionComments());
		}
	}

	/**
	 * @return prosjektnummer
	 */
	public String getPreventiveActionId() {
		if(object.getPreventiveActionId()!=null){
		return String.valueOf(object.getPreventiveActionId());
		}
		return null;
	}

	/**
	 * @param preventiveActionId
	 */
	public void setPreventiveActionId(String preventiveActionId) {
		String oldPreventiveActionId = getPreventiveActionId();
		if(preventiveActionId!=null){
		object.setPreventiveActionId(Integer.valueOf(preventiveActionId));
		}else{
			object.setPreventiveActionId(null);
		}
		firePropertyChange(PROPERTY_PREVENTIVE_ACTION_ID,
				oldPreventiveActionId, preventiveActionId);
	}

	/**
	 * @return prosjektleder
	 */
	public String getManager() {
		return object.getManager();
	}

	/**
	 * @param manager
	 */
	public void setManager(String manager) {
		String oldManager = getManager();
		object.setManager(manager);
		firePropertyChange(PROPERTY_MANAGER, oldManager, manager);
	}

	/**
	 * @return funksjon
	 */
	public JobFunction getJobFunction() {
		return object.getJobFunction();
	}

	/**
	 * @param jobFunction
	 */
	public void setJobFunction(JobFunction jobFunction) {
		JobFunction oldFunction = getJobFunction();
		object.setJobFunction(jobFunction);
		firePropertyChange(PROPERTY_JOB_FUNCTION, oldFunction, jobFunction);
	}

	/**
	 * @return kategori
	 */
	public FunctionCategory getFunctionCategory() {
		return object.getFunctionCategory();
	}

	/**
	 * @param functionCategory
	 */
	public void setFunctionCategory(FunctionCategory functionCategory) {
		FunctionCategory oldCategory = getFunctionCategory();
		object.setFunctionCategory(functionCategory);
		firePropertyChange(PROPERTY_FUNCTION_CATEGORY, oldCategory,
				functionCategory);
	}

	/**
	 * @return beskrivelse
	 */
	public String getDescription() {
		return object.getDescription();
	}

	/**
	 * @param desc
	 */
	public void setDescription(String desc) {
		String oldDesc = getDescription();
		object.setDescription(desc);
		firePropertyChange(PROPERTY_DESCRIPTION, oldDesc, desc);
	}

	/**
	 * @return forventet resultat
	 */
	public String getExpectedOutcome() {
		return object.getExpectedOutcome();
	}

	/**
	 * @param outcome
	 */
	public void setExpectedOutcome(String outcome) {
		String oldOutcome = getExpectedOutcome();
		object.setExpectedOutcome(outcome);
		firePropertyChange(PROPERTY_EXPECTED_OUTCOME, oldOutcome, outcome);
	}

	/**
	 * @return kommentarer
	 */
	public List<PreventiveActionComment> getComments() {
		return new ArrayList<PreventiveActionComment>(commentList);
	}

	/**
	 * @param comments
	 */
	public void setComments(List<PreventiveActionComment> comments) {
		List<PreventiveActionComment> oldList = new ArrayList<PreventiveActionComment>(
				commentList);
		commentList.clear();
		commentList.addAll(comments);
		firePropertyChange(PROPERTY_COMMENTS, oldList, comments);
	}

	/**
	 * @param comment
	 */
	public void addComment(PreventiveActionComment comment) {
		if (comment != null) {
			comment.setPreventiveAction(object);

			List<PreventiveActionComment> oldList = new ArrayList<PreventiveActionComment>(
					commentList);
			commentList.add(comment);
			firePropertyChange(PROPERTY_COMMENTS, oldList, commentList);
		}
	}

	/**
	 * @return true dersom lukket
	 */
	public Boolean getProjectClosedBoolean() {
		if(object.getClosedDate()!=null)
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

	/**
	 * @param closed
	 */
	public void setProjectClosedBoolean(Boolean closed) {
		Boolean oldClosed = getProjectClosedBoolean();
		if(closed){
			object.setClosedDate(Util.getCurrentDate());
		}else{
			object.setClosedDate(null);
		}
		
		firePropertyChange(PROPERTY_PROJECT_CLOSED_BOOLEAN, oldClosed, closed);
	}

	/**
	 * @return prosjektnavn
	 */
	public String getPreventiveActionName() {
		return object.getPreventiveActionName();
	}

	/**
	 * @param name
	 */
	public void setPreventiveActionName(String name) {
		String oldName = getPreventiveActionName();
		object.setPreventiveActionName(name);
		firePropertyChange(PROPERTY_PREVENTIVE_ACTION_NAME, oldName, name);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_PREVENTIVE_ACTION_ID).addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_MANAGER)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_JOB_FUNCTION)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_FUNCTION_CATEGORY)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_DESCRIPTION)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_EXPECTED_OUTCOME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PROJECT_CLOSED_BOOLEAN)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PREVENTIVE_ACTION_NAME)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public PreventiveActionModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		PreventiveActionModel model = new PreventiveActionModel(
				new PreventiveAction());
		model.setPreventiveActionId((String) presentationModel.getBufferedValue(PROPERTY_PREVENTIVE_ACTION_ID));
		model.setManager((String) presentationModel
				.getBufferedValue(PROPERTY_MANAGER));
		model.setJobFunction((JobFunction) presentationModel
				.getBufferedValue(PROPERTY_JOB_FUNCTION));
		model.setFunctionCategory((FunctionCategory) presentationModel
				.getBufferedValue(PROPERTY_FUNCTION_CATEGORY));
		model.setDescription((String) presentationModel
				.getBufferedValue(PROPERTY_DESCRIPTION));
		model.setExpectedOutcome((String) presentationModel
				.getBufferedValue(PROPERTY_EXPECTED_OUTCOME));
		model.setProjectClosedBoolean((Boolean) presentationModel
				.getBufferedValue(PROPERTY_PROJECT_CLOSED_BOOLEAN));
		model.setPreventiveActionName((String) presentationModel
				.getBufferedValue(PROPERTY_PREVENTIVE_ACTION_NAME));
		return model;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#viewToModel()
	 */
	@Override
	public void viewToModel() {
		Set<PreventiveActionComment> comments = object
				.getPreventiveActionComments();
		if (comments == null) {
			comments = new HashSet<PreventiveActionComment>();
		}
		comments.clear();
		comments.addAll(commentList);
		object.setPreventiveActionComments(comments);
	}
}
