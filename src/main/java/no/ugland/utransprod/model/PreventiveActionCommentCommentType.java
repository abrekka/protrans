package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PreventiveActionCommentCommentType extends BaseObject{
    private Integer preventiveActionCommentCommentTypeId;
    private PreventiveActionComment preventiveActionComment;
    private CommentType commentType;
    public PreventiveActionCommentCommentType() {
        super();
    }
    public PreventiveActionCommentCommentType(Integer preventiveActionCommentCommentTypeId, PreventiveActionComment preventiveActionComment, CommentType commentType) {
        super();
        this.preventiveActionCommentCommentTypeId = preventiveActionCommentCommentTypeId;
        this.preventiveActionComment = preventiveActionComment;
        this.commentType = commentType;
    }
    public CommentType getCommentType() {
        return commentType;
    }
    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }
    public PreventiveActionComment getPreventiveActionComment() {
        return preventiveActionComment;
    }
    public void setPreventiveActionComment(
            PreventiveActionComment preventiveActionComment) {
        this.preventiveActionComment = preventiveActionComment;
    }
    public Integer getPreventiveActionCommentCommentTypeId() {
        return preventiveActionCommentCommentTypeId;
    }
    public void setPreventiveActionCommentCommentTypeId(
            Integer preventiveActionCommentCommentTypeId) {
        this.preventiveActionCommentCommentTypeId = preventiveActionCommentCommentTypeId;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof PreventiveActionCommentCommentType))
            return false;
        PreventiveActionCommentCommentType castOther = (PreventiveActionCommentCommentType) other;
        return new EqualsBuilder().append(preventiveActionComment,
                castOther.preventiveActionComment).append(commentType,
                castOther.commentType).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(preventiveActionComment).append(
                commentType).toHashCode();
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "preventiveActionCommentCommentTypeId",
                preventiveActionCommentCommentTypeId).append(
                "preventiveActionComment", preventiveActionComment).append(
                "commentType", commentType).toString();
    }
}
