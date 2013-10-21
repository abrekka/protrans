package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.IComment;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.util.CommentTypeUtil;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for orderekommentar
 * @author atle.brekka
 */
public class OrderCommentModel extends AbstractModel<IComment, ICommentModel>
        implements ICommentModel {
    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_FOR_TRANSPORT_BOOL = "forTransportBool";

    public static final String PROPERTY_FOR_PACKAGE_BOOL = "forPackageBool";

    /**
     * @param orderComment
     */
    public OrderCommentModel(OrderComment orderComment) {
        super(orderComment);
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
     * @return true dersom kommetar er for transport
     */
    public Boolean getForTransportBool() {
        return CommentTypeUtil.hasCommentType(object.getCommentTypes(), "Transport");
        /*if(object.getCommentType().getCommentTypeName().equalsIgnoreCase("Transport")){
            return Boolean.TRUE;
        }
        //return Util.convertNumberToBoolean(object.getForTransport());
        return Boolean.FALSE;*/
    }
    /**
     * @param forTransport
     */
    public void setForTransportBool(Boolean forTransport) {
        Boolean oldTransport = getForTransportBool();

        if(forTransport){
            object.addCommentType(CommentTypeUtil.getCommentType("Transport"));
        }else{
            object.removeCommentType(CommentTypeUtil.getCommentType("Transport"));
        }
        //object.setForTransport(Util.convertBooleanToNumber(forTransport));
        firePropertyChange(PROPERTY_FOR_TRANSPORT_BOOL, oldTransport,
                forTransport);
    }

    public void setForPackageBool(Boolean forPackage) {
        Boolean oldPackage = getForPackageBool();

        if(forPackage){
            object.addCommentType(CommentTypeUtil.getCommentType("Pakking"));
        }else{
            object.removeCommentType(CommentTypeUtil.getCommentType("Pakking"));
        }
        
        //object.setForPackage(Util.convertBooleanToNumber(forPackage));
        firePropertyChange(PROPERTY_FOR_PACKAGE_BOOL, oldPackage, forPackage);
    }

    public Boolean getForPackageBool() {
        return CommentTypeUtil.hasCommentType(object.getCommentTypes(), "Pakking");
        //return Util.convertNumberToBoolean(object.getForPackage());
    }

    

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
        presentationModel.getBufferedModel(PROPERTY_FOR_TRANSPORT_BOOL)
                .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_FOR_PACKAGE_BOOL)
        .addValueChangeListener(listener);

    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
     */
    @Override
    public OrderCommentModel getBufferedObjectModel(
            PresentationModel presentationModel) {
        OrderCommentModel model = new OrderCommentModel(new OrderComment());
        model.setUserName((String) presentationModel
                .getBufferedValue(PROPERTY_USER_NAME));
        model.setComment((String) presentationModel
                .getBufferedValue(PROPERTY_COMMENT));
        model.setForTransportBool((Boolean) presentationModel
                .getBufferedValue(PROPERTY_FOR_TRANSPORT_BOOL));
        model.setForPackageBool((Boolean) presentationModel
                .getBufferedValue(PROPERTY_FOR_PACKAGE_BOOL));
        return model;
    }

    public static OrderComment createOrderCommentWithUserAndDate(
            ApplicationUser applicationUser) {

        OrderComment orderComment = new OrderComment();
        orderComment.setUserName(applicationUser.getUserName());
        orderComment.setCommentDate(Util.getCurrentDate());
        orderComment.addCommentType(CommentTypeUtil.getCommentType("Ordre"));
        return orderComment;
    }
}
