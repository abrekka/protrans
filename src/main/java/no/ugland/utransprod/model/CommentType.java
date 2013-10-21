package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.CompareToBuilder;

public class CommentType extends BaseObject implements Comparable<CommentType> {
    private Integer commentTypeId;

    private String commentTypeName;
    private String commentTypeComment;

    public CommentType() {
        super();
    }

    public CommentType(final Integer aCommentTypeId,
            final String aCommentTypeName,final String aCommentTypeComment) {
        super();
        this.commentTypeId = aCommentTypeId;
        this.commentTypeName = aCommentTypeName;
        this.commentTypeComment=aCommentTypeComment;
    }

    public final Integer getCommentTypeId() {
        return commentTypeId;
    }

    public final void setCommentTypeId(final Integer aCommentTypeId) {
        this.commentTypeId = aCommentTypeId;
    }

    public final String getCommentTypeName() {
        return commentTypeName;
    }

    public final void setCommentTypeName(final String aCommentTypeName) {
        this.commentTypeName = aCommentTypeName;
    }

    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof CommentType)) {
            return false;
        }
        CommentType castOther = (CommentType) other;
        return new EqualsBuilder().append(commentTypeName,
                castOther.commentTypeName).isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(commentTypeName).toHashCode();
    }

    @Override
    public final String toString() {
        return commentTypeName;
    }

    public String getCommentTypeComment() {
        return commentTypeComment;
    }

    public void setCommentTypeComment(String commentTypeComment) {
        this.commentTypeComment = commentTypeComment;
    }

    public int compareTo(final CommentType other) {
        return new CompareToBuilder().append(commentTypeName,
                other.commentTypeName).toComparison();
    }
}
