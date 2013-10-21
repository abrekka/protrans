package no.ugland.utransprod.model;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klasse som represnterer tabell CONSTRUCTION_TYPE
 * @author atle.brekka
 */
public class ConstructionTypeArticle extends BaseObject implements IArticle,Cloneable {
    private static final long serialVersionUID = 1L;

    private Integer constructionTypeArticleId;

    private ConstructionType constructionType;

    private ArticleType articleType;

    private Set<ConstructionTypeArticleAttribute> attributes;

    private ConstructionTypeArticle constructionTypeArticleRef;

    private Set<ConstructionTypeArticle> constructionTypeArticles;

    private Integer numberOfItems;

    private Set<OrderLine> orderLines;

    private Integer dialogOrder;
    /**
     * Brukes bare for mellomlagring for ordrelinje ved skifte av
     * konstruksjonstype
     */
    private Integer ordNo;
    /**
     * Brukes bare for mellomlagring for ordrelinje ved skifte av
     * konstruksjonstype
     */
    private Integer lnNo;

    public ConstructionTypeArticle() {
        super();
    }

    /**
     * @param constructionTypeArticleId
     * @param constructionType
     * @param articleType
     * @param attributes
     * @param constructionTypeArticleRef
     * @param constructionTypeArticles
     * @param numberOfItems
     * @param orderLines
     * @param dialogOrder
     */
    public ConstructionTypeArticle(Integer constructionTypeArticleId, ConstructionType constructionType,
            ArticleType articleType, Set<ConstructionTypeArticleAttribute> attributes,
            ConstructionTypeArticle constructionTypeArticleRef,
            Set<ConstructionTypeArticle> constructionTypeArticles, Integer numberOfItems,
            Set<OrderLine> orderLines, Integer dialogOrder) {
        super();
        this.constructionTypeArticleId = constructionTypeArticleId;
        this.constructionType = constructionType;
        this.articleType = articleType;
        this.attributes = attributes;
        this.constructionTypeArticleRef = constructionTypeArticleRef;
        this.constructionTypeArticles = constructionTypeArticles;
        this.numberOfItems = numberOfItems;
        this.orderLines = orderLines;
        this.dialogOrder = dialogOrder;
    }

    /**
     * @return artikkeltype
     */
    public ArticleType getArticleType() {
        return articleType;
    }

    /**
     * @param articleType
     */
    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    /**
     * @return garasjetype
     */
    public ConstructionType getConstructionType() {
        return constructionType;
    }

    /**
     * @param constructionType
     */
    public void setConstructionType(ConstructionType constructionType) {
        this.constructionType = constructionType;
    }

    /**
     * @return id
     */
    public Integer getConstructionTypeArticleId() {
        return constructionTypeArticleId;
    }

    /**
     * @param constructionTypeArticleId
     */
    public void setConstructionTypeArticleId(Integer constructionTypeArticleId) {
        this.constructionTypeArticleId = constructionTypeArticleId;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ConstructionTypeArticle))
            return false;
        ConstructionTypeArticle castOther = (ConstructionTypeArticle) other;
        return new EqualsBuilder().append(constructionType, castOther.constructionType).append(articleType,
                castOther.articleType).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(constructionType).append(articleType).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public String toString() {
        return articleType.getArticleTypeName();
    }

    /**
     * @return attributter
     */
    public Set<ConstructionTypeArticleAttribute> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes
     */
    public void setAttributes(Set<ConstructionTypeArticleAttribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return artikkel det refereres til
     */
    public ConstructionTypeArticle getConstructionTypeArticleRef() {
        return constructionTypeArticleRef;
    }

    /**
     * @param constructionTypeArticleRef
     */
    public void setConstructionTypeArticleRef(ConstructionTypeArticle constructionTypeArticleRef) {
        this.constructionTypeArticleRef = constructionTypeArticleRef;
    }

    /**
     * @return artikler som hører til artikkel for garasjetype
     */
    public Set<ConstructionTypeArticle> getConstructionTypeArticles() {
        return constructionTypeArticles;
    }

    /**
     * @param constructionTypeArticles
     */
    public void setConstructionTypeArticles(Set<ConstructionTypeArticle> constructionTypeArticles) {
        this.constructionTypeArticles = constructionTypeArticles;
    }

    /**
     * @return antall
     */
    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * @param numberOfItems
     */
    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    /**
     * @return ordrelinjer
     */
    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    /**
     * @param orderLines
     */
    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    /**
     * @see no.ugland.utransprod.model.IArticle#getDialogOrder()
     */
    public Integer getDialogOrder() {
        return dialogOrder;
    }

    /**
     * @see no.ugland.utransprod.model.IArticle#setDialogOrder(java.lang.Integer)
     */
    public void setDialogOrder(Integer dialogOrder) {
        this.dialogOrder = dialogOrder;
    }

    /**
     * @see no.ugland.utransprod.model.IArticle#getArticleName()
     */
    public String getArticleName() {
        if (articleType != null) {
            return articleType.getArticleTypeName();
        }
        return "";
    }

    /**
     * @see no.ugland.utransprod.model.IArticle#setArticleName(java.lang.String)
     */
    public void setArticleName(String articleName) {
    }

    /**
     * @see no.ugland.utransprod.model.IArticle#getNumberOfItemsLong()
     */
    public Long getNumberOfItemsLong() {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalSeparatorAlwaysShown(false);
        decimalFormat.setParseIntegerOnly(true);
        if (getNumberOfItems() != null) {
            return Long.valueOf(decimalFormat.format(numberOfItems));
        }
        return null;

    }

    /**
     * @see no.ugland.utransprod.model.IArticle#setNumberOfItemsLong(java.lang.Long)
     */
    public void setNumberOfItemsLong(Long numberOfItems) {
        if (numberOfItems != null) {
            setNumberOfItems(Integer.valueOf(numberOfItems.intValue()));
        } else {
            setNumberOfItems(null);
        }

    }

    /**
     * @param orderLine
     */
    public void removeOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine);
        orderLine.setConstructionTypeArticle(null);
    }

    /**
     * @param attribute
     */
    public void addAttribute(ConstructionTypeArticleAttribute attribute) {
        if (attributes == null) {
            attributes = new HashSet<ConstructionTypeArticleAttribute>();
        }
        attribute.setConstructionTypeArticle(this);
        attributes.add(attribute);
    }

    /**
     * @see no.ugland.utransprod.model.IArticle#getMetric()
     */
    public String getMetric() {
        return articleType.getMetric();
    }

    /**
     * @see no.ugland.utransprod.model.IArticle#getNewInstance()
     */
    public IArticle getNewInstance() {
        return new ConstructionTypeArticle();
    }

    public void setAttributeValuesFromOrderLine(Set<OrderLineAttribute> orderLineAttributes) {
        for (OrderLineAttribute att : orderLineAttributes) {
            setAttributeValue(att);
        }
    }

    public final void setAttributeValue(final OrderLineAttribute orderLineAttribute) {
        if (attributes != null) {
            boolean found=false;
            for (ConstructionTypeArticleAttribute attribute : attributes) {
                if (attribute.getAttributeName().equalsIgnoreCase(orderLineAttribute.getAttributeName())) {
                    attribute.setAttributeValue(orderLineAttribute.getAttributeValue());
                    found=true;
                    break;
                }
            }
            found=!found&&orderLineAttribute.getAttributeValue().length()!=0?attributes.add(createNewAttribute(orderLineAttribute)):false;
            
        }
    }
    private ConstructionTypeArticleAttribute createNewAttribute(final OrderLineAttribute orderLineAttribute){
        ConstructionTypeArticleAttribute newAttribute = new ConstructionTypeArticleAttribute();
        newAttribute.setArticleTypeAttribute(orderLineAttribute.getArticleTypeAttribute());
        newAttribute.setConstructionTypeArticle(this);
        newAttribute.setAttributeValue(orderLineAttribute.getAttributeValue());
        return newAttribute;
    }

    public Integer getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(Integer aOrdNo) {
        this.ordNo = aOrdNo;
    }

    public Integer getLnNo() {
        return lnNo;
    }

    public void setLnNo(Integer lnNo) {
        this.lnNo = lnNo;
    }
    
    public ConstructionTypeArticle cloneObject(){
        try {
            return (ConstructionTypeArticle) clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
