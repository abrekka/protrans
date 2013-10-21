package no.ugland.utransprod.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Domeneklasse for tabell PREVENTIVE_COMMENT
 * @author atle.brekka
 */
public class PreventiveActionComment extends BaseObject implements IComment {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Integer preventiveActionCommentId;

    /**
     * 
     */
    private String userName;

    /**
     * 
     */
    private Date commentDate;

    /**
     * 
     */
    private String comment;

    /**
     * 
     */
    //private Integer commentType;

    /**
     * 
     */
    private PreventiveAction preventiveAction;
    private Set<PreventiveActionCommentCommentType> preventiveActionCommentCommentTypes;

    /**
     * 
     */
    public PreventiveActionComment() {
        super();
    }

    /**
     * @param preventiveActionCommentId
     * @param userName
     * @param commentDate
     * @param comment
     * @param commentType
     * @param preventiveAction
     */
    public PreventiveActionComment(Integer preventiveActionCommentId,
            String userName, Date commentDate, String comment,
            //Integer commentType, 
            PreventiveAction preventiveAction,
            Set<PreventiveActionCommentCommentType> somePreventiveActionCommentCommentTypes) {
        super();
        this.preventiveActionCommentId = preventiveActionCommentId;
        this.userName = userName;
        this.commentDate = commentDate;
        this.comment = comment;
        //this.commentType = commentType;
        this.preventiveAction = preventiveAction;
        this.preventiveActionCommentCommentTypes=somePreventiveActionCommentCommentTypes;
    }

    /**
     * @return kommentar
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return kommentardato
     */
    public Date getCommentDate() {
        return commentDate;
    }

    /**
     * @param commentDate
     */
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * @return type
     */
    /*public Integer getCommentType() {
        return commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }*/
    public Set<PreventiveActionCommentCommentType> getPreventiveActionCommentCommentTypes() {
        return preventiveActionCommentCommentTypes;
    }

    public void setPreventiveActionCommentCommentTypes(Set<PreventiveActionCommentCommentType> somePreventiveActionCommentCommentTypes) {
        this.preventiveActionCommentCommentTypes = somePreventiveActionCommentCommentTypes;
    }

    /**
     * @return korrigrende tilltak
     */
    public PreventiveAction getPreventiveAction() {
        return preventiveAction;
    }

    /**
     * @param preventiveAction
     */
    public void setPreventiveAction(PreventiveAction preventiveAction) {
        this.preventiveAction = preventiveAction;
    }

    /**
     * @return id
     */
    public Integer getPreventiveActionCommentId() {
        return preventiveActionCommentId;
    }

    /**
     * @param preventiveActionCommentId
     */
    public void setPreventiveActionCommentId(Integer preventiveActionCommentId) {
        this.preventiveActionCommentId = preventiveActionCommentId;
    }

    /**
     * @return brukernavn
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof PreventiveActionComment))
            return false;
        PreventiveActionComment castOther = (PreventiveActionComment) other;
        return new EqualsBuilder().append(userName, castOther.userName).append(
                commentDate, castOther.commentDate).append(preventiveAction,
                castOther.preventiveAction).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(userName).append(commentDate)
                .append(preventiveAction).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public String toString() {
        return userName + ": " + Util.SHORT_DATE_FORMAT.format(commentDate)
                + ": " + comment;
    }

    public Integer getForTransport() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setForTransport(Integer forTransport) {
        // TODO Auto-generated method stub

    }

    public Integer getForPackage() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setForPackage(Integer forPackage) {
        // TODO Auto-generated method stub

    }
    
    public List<CommentType> getCommentTypes(){
        List<CommentType> commentTypes=new ArrayList<CommentType>();
        if(preventiveActionCommentCommentTypes!=null){
            for(PreventiveActionCommentCommentType type:preventiveActionCommentCommentTypes){
                commentTypes.add(type.getCommentType());
            }
        }
        return commentTypes;
    }
    public void setCommentTypes(List<CommentType> commentTypes){
        if(preventiveActionCommentCommentTypes==null){
            preventiveActionCommentCommentTypes=new HashSet<PreventiveActionCommentCommentType>();
        }
        if(commentTypes!=null){
            for(CommentType commentType:commentTypes){
                preventiveActionCommentCommentTypes.add(new PreventiveActionCommentCommentType(null,this,commentType));
            }
        }
    }
    
    public void addCommentType(CommentType commentType){
        if(preventiveActionCommentCommentTypes==null){
            preventiveActionCommentCommentTypes=new HashSet<PreventiveActionCommentCommentType>();
        }
        preventiveActionCommentCommentTypes.add(new PreventiveActionCommentCommentType(null,this,commentType));
    }
    public void removeCommentType(CommentType commentType){
        if(preventiveActionCommentCommentTypes!=null){
            PreventiveActionCommentCommentType comment=null;
            for(PreventiveActionCommentCommentType commentCommentType:preventiveActionCommentCommentTypes){
                if(commentCommentType.getCommentType().equals(commentType)){
                    comment=commentCommentType;
                    break;
                }
            }
            if(comment!=null){
                preventiveActionCommentCommentTypes.remove(comment);
                comment.setPreventiveActionComment(null);
            }
        }
    }
}
