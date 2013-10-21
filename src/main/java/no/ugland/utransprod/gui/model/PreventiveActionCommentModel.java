package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.IComment;
import no.ugland.utransprod.model.PreventiveActionComment;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for kommentar for prevantidt tiltak
 * 
 * @author atle.brekka
 * 
 */
public class PreventiveActionCommentModel extends
		AbstractModel<IComment, ICommentModel>
		implements ICommentModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * 
	 */
	public static final String PROPERTY_COMMENT_TYPE = "commentType";

	/**
	 * @param comment
	 */
	public PreventiveActionCommentModel(PreventiveActionComment comment) {
		super(comment);
	}

	/**
	 * @return brukernavn
	 */
	public String getUserName() {
		return object.getUserName();
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		String oldName = getUserName();
		object.setUserName(userName);
		firePropertyChange(PROPERTY_USER_NAME, oldName, userName);
	}

	/**
	 * @return kommentar
	 */
	public String getComment() {
		return object.getComment();
	}

	/**
	 * @param comment
	 */
	public void setComment(String comment) {
		String oldComment = getComment();
		object.setComment(comment);
		firePropertyChange(PROPERTY_COMMENT, oldComment, comment);
	}

	/**
	 * @return kommentartype
	 */
	/*public Integer getCommentType() {
		return object.getCommentType();
	}*/

	/**
	 * @param commentType
	 */
	/*public void setCommentType(Integer commentType) {
		Integer oldCommentType = getCommentType();
		object.setCommentType(commentType);
		firePropertyChange(PROPERTY_COMMENT_TYPE, oldCommentType, commentType);
	}*/

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_USER_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_COMMENT)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_COMMENT_TYPE)
				.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public PreventiveActionCommentModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		PreventiveActionCommentModel model = new PreventiveActionCommentModel(
				new PreventiveActionComment());
		model.setUserName((String) presentationModel
				.getBufferedValue(PROPERTY_USER_NAME));
		model.setComment((String) presentationModel
				.getBufferedValue(PROPERTY_COMMENT));
		//model.setCommentType((Integer) presentationModel.getBufferedValue(PROPERTY_COMMENT_TYPE));
		return model;
	}

}
