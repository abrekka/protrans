package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.gui.handlers.TransportLetterObject;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.util.Calculator;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.inject.internal.Sets;

/**
 * Klasse som representerer tabell ORDER_LINE
 * 
 * @author atle.brekka
 */
public class OrderLine extends BaseObject implements Comparable<OrderLine>, IArticle, TransportLetterObject, PackableListItem {
    private static final long serialVersionUID = 1L;

    private Integer orderLineId;

    private Order order;

    private ConstructionTypeArticle constructionTypeArticle;

    private ArticleType articleType;

    private Set<OrderLineAttribute> orderLineAttributes = Sets.newHashSet();

    private OrderLine orderLineRef;

    private Set<OrderLine> orderLines = Sets.newHashSet();

    private Integer numberOfItems;

    private Integer dialogOrder;

    private Date produced;

    private String articlePath;

    private Colli colli;

    private Integer hasArticle;

    private String attributeInfo;

    private Integer isDefault;

    private PostShipment postShipment;

    private ExternalOrderLine externalOrderLine;

    private Deviation deviation;
    private Date actionStarted;
    private ProductionUnit productionUnit;
    /**
     * Brukes til ordrenr hentet fra visma
     */
    private Integer ordNo;
    /**
     * Brukes til linjenummer hentet fra visma
     */
    private Integer lnNo;
    /**
     * Brukes til kostpris hentet fra visma
     */
    private BigDecimal cstpr;
    public static final OrderLine UNKNOWN = new OrderLine() {

	private static final long serialVersionUID = 1L;
    };
    /**
     * Brukes til å lagre info fra visma
     */
    private Ordln ordln;

    private Date cuttingStarted;

    private Date cuttingDone;

    private BigDecimal realProductionHours;

    private String doneBy;

    public OrderLine() {
	super();
    }

    /**
     * @param aOrderLineId
     * @param aOrder
     * @param aConstructionTypeArticle
     * @param aArticleType
     * @param aOrderLineAttributes
     * @param aOrderLineRef
     * @param someOrderLines
     * @param aNumberOfItems
     * @param aDialogOrder
     * @param producedDate
     * @param aArticlePath
     * @param aColli
     * @param doHasArticle
     * @param aAttributeInfo
     * @param thisIsDefault
     * @param aPostShipment
     * @param aExternalOrderLine
     * @param aDeviation
     */
    public OrderLine(final Integer aOrderLineId, final Order aOrder, final ConstructionTypeArticle aConstructionTypeArticle,
	    final ArticleType aArticleType, final Set<OrderLineAttribute> aOrderLineAttributes, final OrderLine aOrderLineRef,
	    final Set<OrderLine> someOrderLines, final Integer aNumberOfItems, final Integer aDialogOrder, final Date producedDate,
	    final String aArticlePath, final Colli aColli, final Integer doHasArticle, final String aAttributeInfo, final Integer thisIsDefault,
	    final PostShipment aPostShipment, final ExternalOrderLine aExternalOrderLine, final Deviation aDeviation, final Date actionStartedDate,
	    final ProductionUnit aProductionUnit, final Integer aOrdNo, final Integer aLnNo

    ) {
	super();
	this.orderLineId = aOrderLineId;
	this.order = aOrder;
	this.constructionTypeArticle = aConstructionTypeArticle;
	this.articleType = aArticleType;
	this.orderLineAttributes = aOrderLineAttributes;
	this.orderLineRef = aOrderLineRef;
	this.orderLines = someOrderLines;
	this.numberOfItems = aNumberOfItems;
	this.dialogOrder = aDialogOrder;
	this.produced = producedDate;
	this.articlePath = aArticlePath;
	this.colli = aColli;
	this.hasArticle = doHasArticle;
	this.attributeInfo = aAttributeInfo;
	this.isDefault = thisIsDefault;
	this.postShipment = aPostShipment;
	this.externalOrderLine = aExternalOrderLine;
	this.deviation = aDeviation;
	this.actionStarted = actionStartedDate;
	this.productionUnit = aProductionUnit;
	this.ordNo = aOrdNo;
	this.lnNo = aLnNo;

    }

    /**
     * @return artikkel
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
     * @return garasjeartikkel
     */
    public ConstructionTypeArticle getConstructionTypeArticle() {
	return constructionTypeArticle;
    }

    /**
     * @param constructionTypeArticle
     */
    public void setConstructionTypeArticle(ConstructionTypeArticle constructionTypeArticle) {
	this.constructionTypeArticle = constructionTypeArticle;
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
     * @return id
     */
    public Integer getOrderLineId() {
	return orderLineId;
    }

    /**
     * @param orderLineId
     */
    public void setOrderLineId(Integer orderLineId) {
	this.orderLineId = orderLineId;
    }

    /**
     * @return attributter
     */
    public Set<OrderLineAttribute> getOrderLineAttributes() {
	return orderLineAttributes;
    }

    /**
     * @param orderLineAttributes
     */
    public void setOrderLineAttributes(Set<OrderLineAttribute> orderLineAttributes) {
	this.orderLineAttributes = orderLineAttributes;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public String toString() {
	return getArticleName();
    }

    /**
     * @return ordrelinje som denn ordrelinjen tilhører
     */
    public OrderLine getOrderLineRef() {
	return orderLineRef;
    }

    /**
     * @param orderLineRef
     */
    public void setOrderLineRef(OrderLine orderLineRef) {
	this.orderLineRef = orderLineRef;
    }

    /**
     * @return alle ordrelinjer som refererer til denne ordrelinjen
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
     * @return antall
     */
    public Integer getNumberOfItems() {
	return this.numberOfItems;
    }

    /**
     * @param numberOfItems
     */
    public void setNumberOfItems(Integer numberOfItems) {
	this.numberOfItems = numberOfItems;
    }

    /**
     * @return attributter som semikolonseparert streng
     */
    public String getAttributesAsString() {
	return getOrderLineAttributesAsString(this);
    }

    /**
     * @param orderLine
     * @return attributter som semikolonseparert streng
     */
    private String getOrderLineAttributesAsString(OrderLine orderLine) {
	Set<OrderLineAttribute> attributes = orderLine.getOrderLineAttributes();
	StringBuffer tmpBuffer = new StringBuffer("");

	if (orderLine.getOrdln() != null) {
	    tmpBuffer.append(orderLine.getOrdln().getDescription()).append(";");
	}

	if (attributes != null) {
	    for (OrderLineAttribute attribute : attributes) {
		tmpBuffer.append(attribute.getAttributeName()).append(":").append(attribute.getAttributeValue()).append(";");
	    }
	}

	Set<OrderLine> orderLines1 = orderLine.getOrderLines();

	if (orderLines1 != null) {
	    for (OrderLine line : orderLines1) {
		tmpBuffer.append(getOrderLineAttributesAsString(line));
	    }
	}
	return tmpBuffer.toString();
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
     * @return produsert dato
     */
    public Date getProduced() {
	return produced;
    }

    /**
     * @param produced
     */
    public void setProduced(Date produced) {
	this.produced = produced;
    }

    /**
     * @see no.ugland.utransprod.model.IArticle#getArticleName()
     */
    public String getArticleName() {
	if (constructionTypeArticle != null) {
	    return constructionTypeArticle.getArticleType().getArticleTypeName();
	} else if (articleType != null) {
	    return articleType.getArticleTypeName();
	} else {
	    return getProductAreaGroupName() + "type";
	}
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
	if (numberOfItems != null) {
	    DecimalFormat decimalFormat = new DecimalFormat();
	    decimalFormat.setDecimalSeparatorAlwaysShown(false);
	    decimalFormat.setParseIntegerOnly(true);
	    return Long.valueOf(numberOfItems.longValue());
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
     * @return artikkelpath
     */
    public String getGeneratedArticlePath() {
	StringBuffer buffer = new StringBuffer();
	if (orderLineRef != null) {
	    buffer.append(orderLineRef.getGeneratedArticlePath()).append("$");
	}
	buffer.append(getArticleName());

	return buffer.toString();
    }

    /**
     * @param articlePath
     */
    public void setArticlePath(String articlePath) {
	this.articlePath = articlePath;
    }

    /**
     * @return artikkelpath
     */
    public String getArticlePath() {
	return this.articlePath;
    }

    /**
     * @return kolli
     */
    public Colli getColli() {
	return colli;
    }

    /**
     * @param colli
     */
    public void setColli(Colli colli) {
	this.colli = colli;
    }

    /**
     * @return true dersom ordrelinje er for en toppartikkel
     */
    public boolean hasTopLevelArticle() {
	if (constructionTypeArticle != null) {
	    return constructionTypeArticle.getArticleType().getTopLevelBoolean();
	} else if (articleType != null) {
	    return articleType.getTopLevelBoolean();
	}
	return false;
    }

    /**
     * @return true dersom har artikkel
     */
    public Boolean hasArticle() {
	Boolean returnValue = false;
	if (hasArticle != null) {
	    if (hasArticle == 1) {
		returnValue = true;
	    } else {
		returnValue = false;
	    }
	} else {
	    String articleName = getArticlePath();
	    if (articleName.indexOf("$") != -1) {
		articleName = articleName.substring(articleName.lastIndexOf("$"));
	    }
	    String attributeName = "Har " + articleName;
	    returnValue = true;
	    hasArticle = 1;
	    if (orderLineAttributes != null) {
		for (OrderLineAttribute orderLineAttribute : orderLineAttributes) {
		    if (orderLineAttribute.getOrderLineAttributeName().equalsIgnoreCase(attributeName)) {
			if (orderLineAttribute.getOrderLineAttributeValue().equalsIgnoreCase("Nei")) {
			    returnValue = Boolean.FALSE;
			    hasArticle = 0;
			}
		    }
		}

	    }
	}
	return returnValue;
    }

    /**
     * @return 1 dersom har artikkel
     */
    public Integer getHasArticle() {
	return hasArticle;
    }

    /**
     * @param hasArticle
     */
    public void setHasArticle(Integer hasArticle) {
	this.hasArticle = hasArticle;
    }

    /**
     * @return attributtinfo
     */
    public String getAttributeInfo() {
	return attributeInfo;
    }

    public String getAttributeInfoWithoutNo() {
	return Util.removeNoAttributes(getAttributeInfo());
    }

    /**
     * @param attributeInfo
     */
    public void setAttributeInfo(String attributeInfo) {
	this.attributeInfo = attributeInfo;
    }

    /**
     * @return 1 dersom default verdi
     */
    public Integer getIsDefault() {
	return isDefault;
    }

    /**
     * @param isDefault
     */
    public void setIsDefault(Integer isDefault) {
	this.isDefault = isDefault;
    }

    /**
     * @return true dersom default verdi
     */
    public boolean isDefault() {
	boolean returnValue = true;

	if (isDefault != null) {

	    returnValue = Util.convertNumberToBoolean(isDefault);
	} else if (orderLineAttributes != null) {
	    ConstructionTypeArticleAttribute constructionAttribute;
	    for (OrderLineAttribute attribute : orderLineAttributes) {
		constructionAttribute = attribute.getConstructionTypeArticleAttribute();
		if (constructionAttribute == null) {
		    returnValue = false;
		    break;
		}
		if ("Egenordre".equalsIgnoreCase(attribute.getOrderLineAttributeName())
			&& !"Nei".equalsIgnoreCase(attribute.getOrderLineAttributeValue())) {
		    returnValue = false;
		    break;
		}

		if (constructionAttribute.isYesNo()) {
		    if (constructionAttribute.getAttributeValueBool() != attribute.getAttributeValueBool()) {
			returnValue = false;
			break;
		    }
		} else if (constructionAttribute.getAttributeValue() != null
			&& !constructionAttribute.getAttributeValue().equalsIgnoreCase(attribute.getAttributeValue())) {
		    returnValue = false;
		    break;
		}

	    }
	    isDefault = Util.convertBooleanToNumber(returnValue);
	}

	return returnValue;
    }

    /**
     * @param orderLines
     * @return toppartikler
     */
    public static List<OrderLine> getTopArticles(Collection<OrderLine> orderLines) {
	List<OrderLine> topArticles = null;
	if (orderLines != null) {
	    topArticles = new ArrayList<OrderLine>();

	    for (OrderLine orderLine : orderLines) {
		if (orderLine.hasTopLevelArticle()) {
		    topArticles.add(orderLine);
		}
	    }
	}
	return topArticles;
    }

    /**
     * @return spesielle hensyn
     */
    public String getSpecialConcerns() {
	StringBuffer buffer = new StringBuffer();
	if (orderLineAttributes != null) {
	    for (OrderLineAttribute attribute : orderLineAttributes) {
		if (attribute.getAttributeValue() != null && attribute.getAttributeValue().equalsIgnoreCase("Ja") && attribute.isSpecialConcern()) {
		    buffer.append(attribute.getAttributeName());
		    buffer.append("-");
		}
	    }
	}
	if (buffer.length() != 0) {
	    return buffer.toString();
	}
	return "";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.TransportLetterObject#getPostShipment()
     */
    public PostShipment getPostShipment() {
	return postShipment;
    }

    /**
     * @param postShipment
     */
    public void setPostShipment(PostShipment postShipment) {
	this.postShipment = postShipment;
    }

    /**
     * @param attributeName
     * @return finner attributt ved navn
     */
    public OrderLineAttribute getAttributeByName(String attributeName) {
	if (orderLineAttributes != null) {
	    for (OrderLineAttribute attribute : orderLineAttributes) {
		if (attribute.getOrderLineAttributeName().equalsIgnoreCase(attributeName)) {
		    return attribute;
		}
	    }

	}
	return OrderLineAttribute.UNKNOWN;
    }

    public String getAttributeValue(String attributeName) {
	return getAttributeByName(attributeName).getAttributeValue();
    }

    public void setAttributeValue(String attributeName, String attributeValue) {
	getAttributeByName(attributeName).setAttributeValue(attributeValue);
    }

    /**
     * @return ekstern ordrelinje
     */
    public ExternalOrderLine getExternalOrderLine() {
	return externalOrderLine;
    }

    /**
     * @param externalOrderLine
     */
    public void setExternalOrderLine(ExternalOrderLine externalOrderLine) {
	this.externalOrderLine = externalOrderLine;
    }

    /**
     * @param aOrder
     * @param aArticleType
     * @param deviation
     * @return ny instans
     */
    public static OrderLine getInstance(final Order aOrder, final ArticleType aArticleType, final Deviation deviation, final PostShipment postShipment) {
	return new OrderLine(null, aOrder, null, aArticleType, null, null, null, null, null, null, null, null, null, null, null, postShipment, null,
		deviation, null, null, null, null);
    }

    /**
     * @param aOrder
     * @param aArticleType
     * @param aOrderLineRef
     * @param deviation
     * @return ny instans
     */
    public static OrderLine getInstance(final Order aOrder, final ArticleType aArticleType, final OrderLine aOrderLineRef, final Deviation deviation) {
	return new OrderLine(null, aOrder, null, aArticleType, null, aOrderLineRef, null, null, null, null, null, null, null, null, null, null, null,
		deviation, null, null, null, null);
    }

    /**
     * @param aOrder
     * @param aDialogOrder
     * @param deviation
     * @return ny instans
     */
    public static OrderLine getInstance(final Order aOrder, final Integer aDialogOrder, final Deviation deviation) {
	return new OrderLine(null, aOrder, null, null, null, null, null, null, aDialogOrder, null, null, null, null, null, null, null, null,
		deviation, null, null, null, null);
    }

    /**
     * @param aOrder
     * @param aConstructionTypeArticle
     * @param aNumberOfItems
     * @param aDialogOrder
     * @param deviation
     * @return ny instans
     */
    public static OrderLine getInstance(final Order aOrder, final ConstructionTypeArticle aConstructionTypeArticle, final Integer aNumberOfItems,
	    final Integer aDialogOrder, final Deviation deviation, final Integer prodNo, final Integer lnNo) {
	return new OrderLine(null, aOrder, aConstructionTypeArticle, null, null, null, null, aNumberOfItems, aDialogOrder, null, null, null, null,
		null, null, null, null, deviation, null, null, prodNo, lnNo);
    }

    /**
     * @param aOrder
     * @param aConstructionTypeArticle
     * @param aOrderLineRef
     * @param aNumberOfItems
     * @param aDialogOrder
     * @param deviation
     * @return ny instans
     */
    public static OrderLine getInstance(final Order aOrder, final ConstructionTypeArticle aConstructionTypeArticle, final OrderLine aOrderLineRef,
	    final Integer aNumberOfItems, final Integer aDialogOrder, final Deviation deviation) {
	return new OrderLine(null, aOrder, aConstructionTypeArticle, null, null, aOrderLineRef, null, aNumberOfItems, aDialogOrder, null, null, null,
		null, null, null, null, null, deviation, null, null, null, null);
    }

    /**
     * @param aOrder
     * @param aArticleType
     * @param aArticlePath
     * @param hasArticle
     * @return ny instans
     */
    public static OrderLine getInstance(final Order aOrder, final ArticleType aArticleType, final String aArticlePath, final Integer hasArticle) {
	return new OrderLine(null, aOrder, null, aArticleType, null, null, null, null, null, null, aArticlePath, null, hasArticle, null, null, null,
		null, null, null, null, null, null);
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof OrderLine)) {
	    return false;
	}
	OrderLine castOther = (OrderLine) other;
	boolean test = new EqualsBuilder().append(orderLineId, castOther.orderLineId).append(order, castOther.order)
		.append(constructionTypeArticle, castOther.constructionTypeArticle).append(articleType, castOther.articleType)
		.append(orderLineRef, castOther.orderLineRef).append(numberOfItems, castOther.numberOfItems).append(produced, castOther.produced)
		.append(colli, castOther.colli).append(lnNo, castOther.lnNo).isEquals();
	return test;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(orderLineId).append(order).append(constructionTypeArticle).append(articleType).append(orderLineRef)
		.append(numberOfItems).append(produced).append(colli).append(lnNo).toHashCode();
    }

    public String getDetails() {
	return getDetails(AttributeInfo.WITH_ALL_ATTRIBUTES);
    }

    /**
     * Denne metoden kjøres ved utskrift av fraktbrev og vil bare returnere noe
     * dersom order er takstein eller takstol
     * 
     * @see no.ugland.utransprod.gui.handlers.TransportLetterObject#getDetails()
     */
    public String getDetails(AttributeInfo attributeInfo) {
	StringBuilder stringBuilder = new StringBuilder();

	ArticleEnum articleEnum = ArticleEnum.hent(getArticleName());

	if (articleEnum.shouldBeShown(this)) {

	    stringBuilder.append(articleEnum.lagAntallDetaljer(this));

	    // if (getArticleName().equalsIgnoreCase("Takstol")) {
	    // if (getNumberOfItemsLong() != null) {
	    // String metric = getMetric();
	    // stringBuilder.append("Antall: ").append(getNumberOfItemsLong());
	    // if (metric != null) {
	    // stringBuilder.append(" ").append(metric);
	    // }
	    //
	    // }
	    // }

	    if (stringBuilder.length() != 0) {
		stringBuilder.append(",");
	    }

	    stringBuilder.append(articleEnum.lagDetaljer(this));

	    if (getArticleName().equalsIgnoreCase("Takstein")) {
		// String taksteinDetails = generateTaksteinDetails();
		// if (taksteinDetails == null) {
		// stringBuilder = null;
		// } else {
		// stringBuilder.append(" ").append(taksteinDetails);
		stringBuilder.append(" ").append(ordln == null ? "" : ordln.getDescription());
	    }
	    // }
	    // } else if (getArticleName().equalsIgnoreCase("Takstol")) {
	    // String takstolDetails = generateTakstolDetails();
	    // if (takstolDetails == null) {
	    // stringBuilder = null;
	    // } else {
	    // stringBuilder.append(" ,").append(takstolDetails);
	    // }
	    // } else {
	    // if (stringBuilder.length() != 0) {
	    // stringBuilder.append(":");
	    // }
	    // stringBuilder.append(attributeInfo.getAttributeInfo(this));
	    // }
	    // String details = getDetailsString(stringBuilder);
	    return StringUtils.removeEnd(stringBuilder.toString(), ",");
	}
	return null;

	// return stringBuilder != null
	// && StringUtils.isNotBlank(stringBuilder.toString()) ? stringBuilder
	// .toString()
	// : "";
    }

    private String getDetailsString(StringBuilder stringBuilder) {
	String details = null;
	if (stringBuilder != null) {
	    if (!getProductAreaGroupName().equalsIgnoreCase("Garasje")) {
		details = StringUtils.replace(stringBuilder.toString(), "Egenordre:Annet", "");
	    } else {
		details = stringBuilder.toString();
	    }
	}
	return details;
    }

    public String getDetailsWithoutNoAttributes() {
	return getDetails(AttributeInfo.WITHOUT_NO_ATTRIBUTES);
    }

    /**
     * Genererer detaljer om takstein
     * 
     * @param builder
     */
    private String generateTaksteinDetails() {
	StringBuilder detailsBuilder = new StringBuilder();
	if (orderLineAttributes != null) {

	    for (OrderLineAttribute attribute : orderLineAttributes) {
		if (attribute.getAttributeName().equalsIgnoreCase("Sendes fra GG")) {
		    if (attribute.getAttributeValue().equalsIgnoreCase("Nei")) {
			detailsBuilder = null;
			break;
		    }
		} else if ("Taksteintype".equalsIgnoreCase(attribute.getAttributeName())) {
		    detailsBuilder.append(attribute.getAttributeName()).append(": ").append(attribute.getAttributeValue());
		    // break;
		}
	    }
	}
	return detailsBuilder != null ? detailsBuilder.toString() : null;
    }

    private String generateTakstolDetails() {
	StringBuilder detailsBuilder = new StringBuilder();
	if (orderLineAttributes != null) {

	    for (OrderLineAttribute attribute : orderLineAttributes) {
		if ("Stående tak".equalsIgnoreCase(attribute.getAttributeName()) && "Ja".equalsIgnoreCase(attribute.getAttributeValue())) {
		    detailsBuilder.append("Stående tak");
		    // break;
		}
	    }
	}
	return detailsBuilder != null ? detailsBuilder.toString() : null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.TransportLetterObject#getName()
     */
    public String getName() {
	return getArticleName();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.TransportLetterObject#getTypeName()
     */
    public String getTypeName() {
	return "Mangler";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.TransportLetterObject#getLetterOrder()
     */
    public Order getLetterOrder() {
	return order;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.TransportLetterObject#isNotPostShipment()
     */
    public Boolean isNotPostShipment() {
	if (postShipment != null) {
	    return Boolean.FALSE;
	}

	return Boolean.TRUE;
    }

    /**
     * @return alle ordrelinjeattributter
     */
    public List<OrderLineAttribute> getAllOrderLineAttributes() {
	List<OrderLineAttribute> attributes = new ArrayList<OrderLineAttribute>();
	if (orderLines != null) {
	    for (OrderLine orderLine : orderLines) {
		attributes.addAll(orderLine.getAllOrderLineAttributes());
	    }
	}

	if (orderLineAttributes != null) {
	    attributes.addAll(orderLineAttributes);
	}
	return attributes;
    }

    /**
     * @param conditions
     * @param attributes
     * @return true dersom ordrelinje har betingelser
     */
    private boolean hasConditions(String[] conditions, List<OrderLineAttribute> attributes) {
	if (conditions != null) {
	    for (int i = 0; i < conditions.length; i++) {
		String condition = conditions[i];
		String articleName = condition.substring(0, condition.indexOf("#"));
		String attributeName = condition.substring(condition.indexOf("#") + 1, condition.indexOf("@"));
		String attributeValue = condition.substring(condition.indexOf("@") + 1);

		for (OrderLineAttribute attribute : attributes) {
		    if (attribute.getAttributeName().equalsIgnoreCase(attributeName) && attribute.getArticleName().equalsIgnoreCase(articleName)) {
			if (attribute.getAttributeValue() == null || !attribute.getAttributeValue().equalsIgnoreCase(attributeValue)) {
			    return false;
			}
		    }

		}
	    }
	}
	return true;
    }

    /**
     * Kalkulerere attributter som har formel satt. Formel er på formen
     * artikkelnavn#attributtnavn@attributtverdi;[formel] Dersom formel
     * inneholder betingelser for attributtverdi sjekkes det først om disse
     * kriteriene er overholdt. Dersom de er det kjøres formelen som er satt i
     * [formel]
     */
    public void calculateAttributes() {
	List<OrderLineAttribute> attributes = getAllOrderLineAttributes();
	if (attributes != null) {

	    Double value;
	    Integer integer;
	    for (OrderLineAttribute attribute : attributes) {
		if (attribute.getAttributeFormula() != null) {
		    String allFormula = attribute.getAttributeFormula();
		    boolean runFormula = true;
		    // henter ut eventuelle betingelser satt på attributter eks
		    // $$Kledning#Retning@Stående$$
		    if (allFormula.indexOf(";") != -1) {
			String conditionString = allFormula.substring(0, allFormula.lastIndexOf(";"));
			String[] conditions = conditionString.split(";");

			if (conditions != null && conditions.length != 0) {
			    if (!hasConditions(conditions, attributes)) {
				runFormula = false;
			    }
			}
		    }
		    if (runFormula && allFormula.indexOf("[") != -1) {
			String formula = allFormula.substring(allFormula.indexOf("[") + 1, allFormula.length() - 1);
			value = Calculator.calculate(formula, attributes);
			if (value != null) {
			    integer = value.intValue();

			    attribute.setAttributeValueBool(Util.convertNumberToBoolean(integer));

			}

		    } else {
			attribute.setAttributeValueBool(false);
		    }
		}
	    }
	}
    }

    /**
     * Fjerner all attributtinfo
     */
    public void removeAllOrderLineAttributeInfo() {
	attributeInfo = null;
	if (orderLines != null) {
	    for (OrderLine orderLine : orderLines) {
		orderLine.setAttributeInfo(null);
	    }
	}
	if (orderLineRef != null) {
	    orderLineRef.removeAllOrderLineAttributeInfo();
	}
    }

    /**
     * Legger på detaljer for ordrelinje
     * 
     * @param builder
     */
    public void generateDetails(StringBuilder builder) {
	String details = getDetails();
	if (details != null) {
	    builder.append(getArticleName()).append(":").append(details);
	}
    }

    public boolean generateDetailsWithoutNoAttributes(StringBuilder builder, String colliName) {
	String details = getDetailsWithoutNoAttributes();
	if (StringUtils.isNotBlank(details)) {
	    // if (!colliName.equalsIgnoreCase(getArticleName())) {
	    // builder.append(getArticleName()).append(":");
	    // }
	    builder.append(details);
	    return true;
	}
	return false;
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
     * @see no.ugland.utransprod.model.IArticle#getMetric()
     */
    public String getMetric() {
	String metric = null;
	if (articleType != null) {
	    metric = articleType.getMetric();
	} else if (constructionTypeArticle != null) {
	    metric = constructionTypeArticle.getArticleType().getMetric();
	}
	return metric;
    }

    /**
     * @see no.ugland.utransprod.model.IArticle#getNewInstance()
     */
    public IArticle getNewInstance() {
	return new OrderLine();
    }

    /**
     * @see no.ugland.utransprod.model.PackableListItem#getLoadingDate()
     */
    public Date getLoadingDate() {
	if (postShipment != null && postShipment.getTransport() != null) {
	    return postShipment.getTransport().getLoadingDate();
	} else if (order.getTransport() != null) {
	    return order.getTransport().getLoadingDate();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.model.PackableListItem#getTransportDetails()
     */
    public String getTransportDetails() {
	if (postShipment != null) {
	    return postShipment.getTransportDetails();
	}
	return order.getTransportDetails();
    }

    public Transport getTransport() {
	if (postShipment != null) {
	    return postShipment.getTransport();
	}
	return order.getTransport();
    }

    /**
     * @see no.ugland.utransprod.gui.model.TextRenderable#getComment()
     */
    public String getComment() {
	return order.getComment();
    }

    /**
     * @see no.ugland.utransprod.gui.model.TextRenderable#getOrderString()
     */
    public String getOrderString() {
	return order.getOrderString();
    }

    /**
     * @see no.ugland.utransprod.gui.model.Applyable#getOrderNr()
     */
    public String getOrderNr() {
	return order.getOrderNr();
    }

    /**
     * @see no.ugland.utransprod.gui.model.Applyable#isForPostShipment()
     */
    public Boolean isForPostShipment() {
	if (postShipment != null) {
	    return Boolean.TRUE;
	}
	return Boolean.FALSE;
    }

    /**
     * @see no.ugland.utransprod.model.PackableListItem#getProductAreaGroupName()
     */
    public String getProductAreaGroupName() {
	return order != null ? order.getProductArea().getProductAreaGroup().getProductAreaGroupName() : "";
    }

    public final void setOrderLineAttributeValue(final String attributeName, final String attributeValue) {
	if (orderLineAttributes != null) {
	    for (OrderLineAttribute attribute : orderLineAttributes) {
		if (attribute.getAttributeName().equalsIgnoreCase(attributeName)) {
		    attribute.setAttributeValue(attributeValue);
		    break;
		}
	    }
	}
    }

    public final String getOrderLineAttributeValue(final String attributeName) {
	if (orderLineAttributes != null) {
	    for (OrderLineAttribute attribute : orderLineAttributes) {
		if (attribute.getAttributeName().equalsIgnoreCase(attributeName)) {
		    return attribute.getAttributeValue();
		}
	    }
	}
	return null;
    }

    public Date getActionStarted() {
	return actionStarted;
    }

    public void setActionStarted(Date actionStarted) {
	this.actionStarted = actionStarted;
    }

    public void addAttribute(OrderLineAttribute attribute) {
	if (orderLineAttributes == null) {
	    orderLineAttributes = new HashSet<OrderLineAttribute>();
	}
	attribute.setOrderLine(this);
	orderLineAttributes.add(attribute);
    }

    public boolean belongTo(Transportable transportable) {
	return postShipment != null ? transportable.equals(postShipment) : transportable.equals(order);
    }

    public ProductionUnit getProductionUnit() {
	return productionUnit;
    }

    public void setProductionUnit(ProductionUnit productionUnit) {
	this.productionUnit = productionUnit;
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

    public Ordln getOrdln() {
	return ordln;
    }

    public void setOrdln(Ordln ordln) {
	this.ordln = ordln;
    }

    public BigDecimal getCstpr() {
	return cstpr;
    }

    public void setCstpr(BigDecimal cstpr) {
	this.cstpr = cstpr;
    }

    public void setAttributeValuesFromOrderLine(Set<OrderLineAttribute> orderLineAttributes) {
	for (OrderLineAttribute att : orderLineAttributes) {
	    if (att.getAttributeValue() != null && att.getAttributeValue().length() != 0) {
		setAttributeValue(att);
	    }
	}
    }

    public final void setAttributeValue(final OrderLineAttribute orderLineAttribute) {
	if (orderLineAttributes != null) {
	    boolean found = false;
	    for (OrderLineAttribute attribute : orderLineAttributes) {
		if (attribute.getAttributeName().equalsIgnoreCase(orderLineAttribute.getAttributeName())) {
		    attribute.setAttributeValue(orderLineAttribute.getAttributeValue());
		    found = true;
		    break;
		}
	    }
	    found = !found && orderLineAttribute.getAttributeValue().length() != 0 ? orderLineAttributes.add(createNewAttribute(orderLineAttribute))
		    : false;

	}
    }

    private OrderLineAttribute createNewAttribute(final OrderLineAttribute orderLineAttribute) {
	OrderLineAttribute newAttribute = new OrderLineAttribute();
	newAttribute.setArticleTypeAttribute(orderLineAttribute.getArticleTypeAttribute());
	newAttribute.setAttributeValue(orderLineAttribute.getAttributeValue());
	newAttribute.setOrderLine(this);
	newAttribute.setOrderLineAttributeName(orderLineAttribute.getOrderLineAttributeName());
	return newAttribute;
    }

    public boolean isRelatedArticlesComplete() {
	return false;
    }

    public int compareTo(final OrderLine other) {
	return new CompareToBuilder().append(numberOfItems, other.numberOfItems).toComparison();
    }

    public static class OrderLineNumberComparator implements Comparator<OrderLine> {

	public int compare(OrderLine orderLine1, OrderLine orderLine2) {
	    return new CompareToBuilder().append(orderLine2.getNumberOfItems(), orderLine1.getNumberOfItems()).toComparison();
	}

    }

    public void setRelatedArticles(List<Applyable> relatedArticles) {
    }

    public List<Applyable> getRelatedArticles() {
	return null;
    }

    public boolean isSent() {
	return colli != null ? colli.getSent() != null : false;
    }

    public boolean hasDefaultSet() {
	return isDefault != null;
    }

    public String getLoadTime() {
	return order.getTransport() != null ? order.getTransport().getLoadTime() : null;
    }

    public Integer getTransportWeek() {
	return order.getTransport() != null ? order.getTransport().getTransportWeek() : null;
    }

    public Integer getTransportYear() {
	return order.getTransport() != null ? order.getTransport().getTransportYear() : null;
    }

    public boolean isRelatedArticlesStarted() {
	return false;
    }

    public String getProductionUnitName() {
	return productionUnit != null ? productionUnit.getProductionUnitName() : null;
    }

    public Integer getProbability() {
	return null;
    }

    public Integer getNumberOf() {
	return null;
    }

    public void addOrderLine(OrderLine orderLine) {
	if (orderLines == null) {
	    orderLines = Sets.newHashSet();
	}
	orderLine.setOrderLineRef(this);
	orderLines.add(orderLine);

    }

    public static enum AttributeInfo {
	WITH_ALL_ATTRIBUTES {
	    @Override
	    public String getAttributeInfo(OrderLine orderLine) {
		return orderLine.getAttributeInfo();
	    }
	},
	WITHOUT_NO_ATTRIBUTES {
	    @Override
	    public String getAttributeInfo(OrderLine orderLine) {
		return orderLine.getAttributeInfoWithoutNo();
	    }
	};

	public abstract String getAttributeInfo(OrderLine orderLine);
    }

    private enum ArticleEnum {
	TAKSTOLER {
	    @Override
	    public String lagAntallDetaljer(OrderLine orderLine) {
		// StringBuilder stringBuilder = new StringBuilder();
		// if (orderLine.getNumberOfItemsLong() != null) {
		// String metric = orderLine.getMetric();
		// stringBuilder.append("Antall: ").append(
		// orderLine.getNumberOfItemsLong());
		// if (metric != null) {
		// stringBuilder.append(" ").append(metric);
		// }
		// stringBuilder.append(" ");
		//
		// }
		// return stringBuilder.toString();
		return "";
	    }

	    @Override
	    public String lagDetaljer(OrderLine orderLine) {
		// String takstolDetails = orderLine.generateTakstolDetails();
		// if (takstolDetails != null) {
		// takstolDetails += " ";
		// }
		// return StringUtils.isNotBlank(takstolDetails) ?
		// takstolDetails
		// : "";
		return "";
	    }

	    @Override
	    public boolean shouldBeShown(OrderLine orderLine) {
		return true;
	    }
	}, //
	TAKSTEIN {
	    @Override
	    public String lagAntallDetaljer(OrderLine orderLine) {
		return "";
	    }

	    @Override
	    public String lagDetaljer(OrderLine orderLine) {
		return "";
		// String taksteinDetails = orderLine.generateTaksteinDetails();
		// if (taksteinDetails != null) {
		// taksteinDetails += " ";
		// }
		// return StringUtils.isNotBlank(taksteinDetails) ?
		// taksteinDetails
		// : null;
	    }

	    @Override
	    public boolean shouldBeShown(OrderLine orderLine) {
		String taksteinDetails = orderLine.generateTaksteinDetails();
		return taksteinDetails != null;
	    }
	},
	GAVL {
	    @Override
	    public String lagAntallDetaljer(OrderLine orderLine) {
		return "";
	    }

	    @Override
	    public String lagDetaljer(OrderLine orderLine) {
		// for (OrderLine orderLineSub : orderLine.getOrderLines()) {
		// if ("Kledning".equalsIgnoreCase(orderLineSub
		// .getArticleName())) {
		// return "Kledning: "
		// + orderLineSub.getAttributeInfoWithoutNo();
		// }
		// }
		return "";
	    }

	    @Override
	    public boolean shouldBeShown(OrderLine orderLine) {
		return true;
	    }
	}, //
	UKJENT {
	    @Override
	    public String lagAntallDetaljer(OrderLine orderLine) {
		return "";
	    }

	    @Override
	    public String lagDetaljer(OrderLine orderLine) {
		return "";
	    }

	    @Override
	    public boolean shouldBeShown(OrderLine orderLine) {
		return true;
	    }
	};

	public static ArticleEnum hent(String articleName) {
	    ArticleEnum article = UKJENT;
	    try {
		article = ArticleEnum.valueOf(articleName.toUpperCase());
	    } catch (Exception e) {
	    }
	    return article;
	}

	public abstract boolean shouldBeShown(OrderLine orderLine);

	public abstract String lagDetaljer(OrderLine orderLine);

	public abstract String lagAntallDetaljer(OrderLine orderLine);

    }

    public void setCuttingStarted(Date cuttingStarted) {
	this.cuttingStarted = cuttingStarted;
    }

    public Date getCuttingStarted() {
	return this.cuttingStarted;
    }

    public Date getCuttingDone() {
	return cuttingDone;
    }

    public void setCuttingDone(Date cuttingDone) {
	this.cuttingDone = cuttingDone;
    }

    public BigDecimal getSpentTime() {
	return realProductionHours == null ? Tidsforbruk.beregnTidsforbruk(actionStarted, produced) : realProductionHours;
    }

    public void setRealProductionHours(BigDecimal realProductionHours) {
	this.realProductionHours = realProductionHours;
    }

    public BigDecimal getRealProductionHours() {
	return realProductionHours;
    }

    public Integer getRest() {
	// TODO Auto-generated method stub
	return null;
    }

    public void setDoneBy(String doneBy) {
	this.doneBy = doneBy;
    }

    public String getDoneBy() {
	return doneBy;
    }

    public Integer getLenght() {
	return null;
    }

    public Integer getWidht() {
	return null;
    }

    public Integer getHeight() {
	return null;
    }
}
