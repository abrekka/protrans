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
 * Domeneklasse for ORDER_COMMENT
 * @author atle.brekka
 */
public class OrderComment extends BaseObject implements IComment {
    private static final long serialVersionUID = 1L;

    private Integer orderCommentId;

    private String userName;

    private Date commentDate;

    private String comment;

    private Order order;

    private Deviation deviation;

    //private Integer forTransport;
    //private Integer forPackage;
    
    private Set<OrderCommentCommentType> orderCommentCommentTypes;

    public OrderComment() {
        super();
    }

    public OrderComment(final Integer aOrderCommentId, final String aUserName,
            final Date aCommentDate, final String aComment, final Deviation aDeviation, final Order aOrder,
            final Set<OrderCommentCommentType> someOrderCommentCommentTypes
            //final Integer isForTransport,final Integer isForPackage
            ) {
        super();
        this.orderCommentId = aOrderCommentId;
        this.userName = aUserName;
        this.commentDate = aCommentDate;
        this.comment = aComment;
        this.deviation = aDeviation;
        this.order = aOrder;
        //this.forTransport = isForTransport;
        //this.forTransport=isForPackage;
        this.orderCommentCommentTypes=someOrderCommentCommentTypes;
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
     * @return dato
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
     * @return avvik
     */
    public Deviation getDeviation() {
        return deviation;
    }

    /**
     * @param deviation
     */
    public void setDeviation(Deviation deviation) {
        this.deviation = deviation;
    }

    /**
     * @return id
     */
    public Integer getOrderCommentId() {
        return orderCommentId;
    }

    /**
     * @param orderCommentId
     */
    public void setOrderCommentId(Integer orderCommentId) {
        this.orderCommentId = orderCommentId;
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
        if (!(other instanceof OrderComment))
            return false;
        OrderComment castOther = (OrderComment) other;
        return new EqualsBuilder().append(userName, castOther.userName).append(
                commentDate, castOther.commentDate).append(comment,
                castOther.comment).append(deviation, castOther.deviation)
                .append(order, castOther.order).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(userName).append(commentDate)
                .append(comment).append(deviation).append(order).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public String toString() {
        return userName + ": " + Util.SHORT_DATE_FORMAT.format(commentDate)
                + ": " + comment;
    }

    /**
     * @return ordre
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @param order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * @return 1 dersom kommentar er for transport
     */
    /*public Integer getForTransport() {
        return forTransport;
    }*/

    /**
     * @param forTransport
     */
    /*public void setForTransport(Integer forTransport) {
        this.forTransport = forTransport;
    }*/

    public boolean isNew() {
        if (orderCommentId != null) {
            return false;
        }
        return true;
    }


    /*public Integer getForPackage() {
        return forPackage;
    }*/

    /*public void setForPackage(Integer forPackage) {
        this.forPackage = forPackage;
    }*/

    public void setOrderCommentCommentTypes(Set<OrderCommentCommentType> someOrderCommentCommentTypes) {
        this.orderCommentCommentTypes = someOrderCommentCommentTypes;
    }
    public Set<OrderCommentCommentType> getOrderCommentCommentTypes() {
        return orderCommentCommentTypes;
    }
    
    public List<CommentType> getCommentTypes(){
        List<CommentType> commentTypes=new ArrayList<CommentType>();
        if(orderCommentCommentTypes!=null){
            for(OrderCommentCommentType type:orderCommentCommentTypes){
                commentTypes.add(type.getCommentType());
            }
        }
        return commentTypes;
    }
    public void setCommentTypes(List<CommentType> commentTypes){
        if(orderCommentCommentTypes==null){
            orderCommentCommentTypes=new HashSet<OrderCommentCommentType>();
        }
        if(commentTypes!=null){
            for(CommentType commentType:commentTypes){
                orderCommentCommentTypes.add(new OrderCommentCommentType(null,this,commentType));
            }
        }
    }
    public void addCommentType(CommentType commentType){
        if(orderCommentCommentTypes==null){
            orderCommentCommentTypes=new HashSet<OrderCommentCommentType>();
        }
        orderCommentCommentTypes.add(new OrderCommentCommentType(null,this,commentType));
    }
    public void removeCommentType(CommentType commentType){
        if(orderCommentCommentTypes!=null){
            OrderCommentCommentType comment=null;
            for(OrderCommentCommentType orderCommentCommentType:orderCommentCommentTypes){
                if(orderCommentCommentType.getCommentType().equals(commentType)){
                    comment=orderCommentCommentType;
                    break;
                }
            }
            if(comment!=null){
                orderCommentCommentTypes.remove(comment);
                comment.setOrderComment(null);
            }
        }
    }
}
