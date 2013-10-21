package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.CompareToBuilder;

public class OrderCommentCommentType extends BaseObject implements Comparable<OrderCommentCommentType> {
    private Integer orderCommentCommentTypeId;
    private OrderComment orderComment;
    private CommentType commentType;
    public OrderCommentCommentType() {
        super();
    }
    public OrderCommentCommentType(Integer orderCommentCommentTypeId, OrderComment orderComment, CommentType commentType) {
        super();
        this.orderCommentCommentTypeId = orderCommentCommentTypeId;
        this.orderComment = orderComment;
        this.commentType = commentType;
    }
    public CommentType getCommentType() {
        return commentType;
    }
    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }
    public OrderComment getOrderComment() {
        return orderComment;
    }
    public void setOrderComment(OrderComment orderComment) {
        this.orderComment = orderComment;
    }
    public Integer getOrderCommentCommentTypeId() {
        return orderCommentCommentTypeId;
    }
    public void setOrderCommentCommentTypeId(Integer orderCommentCommentTypeId) {
        this.orderCommentCommentTypeId = orderCommentCommentTypeId;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof OrderCommentCommentType))
            return false;
        OrderCommentCommentType castOther = (OrderCommentCommentType) other;
        return new EqualsBuilder().append(orderComment, castOther.orderComment)
                .append(commentType, castOther.commentType).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(orderComment).append(commentType)
                .toHashCode();
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "orderCommentCommentTypeId", orderCommentCommentTypeId).append(
                "orderComment", orderComment)
                .append("commentType", commentType).toString();
    }
    public int compareTo(final OrderCommentCommentType other) {
        return new CompareToBuilder().append(orderComment, other.orderComment)
                .append(commentType, other.commentType).toComparison();
    }
    
}
