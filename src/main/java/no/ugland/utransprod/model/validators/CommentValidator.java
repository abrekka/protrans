package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.ICommentModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

public class CommentValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private ICommentModel commentModel;

    public CommentValidator(final ICommentModel iCommentModel) {
        this.commentModel = iCommentModel;
    }

    /**
     * @see com.jgoodies.validation.Validator#validate()
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                commentModel, "Kommentar");

        if(ValidationUtils.isEmpty(commentModel.getComment())){
        	support.addError("kommentar", "kan ikke være tom");
        }
        if (ModelUtil.nullToString(commentModel.getComment()).length() >= ICommentModel.MAX_COMMENT_LENGTH) {
            support.addError("kommentar", "kan ikke være lengre enn "
                    + ICommentModel.MAX_COMMENT_LENGTH);
        }

        return support.getResult();
    }

}
