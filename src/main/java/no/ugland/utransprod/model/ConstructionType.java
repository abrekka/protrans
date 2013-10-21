package no.ugland.utransprod.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * Klasse som representerer tabell CONSTRUCTION_TYPE
 * 
 * @author atle.brekka
 * 
 */
public class ConstructionType extends BaseObject implements Comparable<ConstructionType> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer constructionTypeId;

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private Set<ConstructionTypeAttribute> constructionTypeAttributes;

	/**
	 * 
	 */
	private Set<ConstructionTypeArticle> constructionTypeArticles;

	/**
	 * 
	 */
	private Integer isMaster;

	/**
	 * 
	 */
	private Set<Order> orders;

	/**
	 * 
	 */
	private String sketchName;

	/**
	 * 
	 */
	// private Integer villa;
	private ProductArea productArea;
	
	public static final ConstructionType UNKNOWN = new ConstructionType() {};

	/**
	 * 
	 */
	public ConstructionType() {
		super();
	}

	/**
	 * @param constructionTypeId
	 * @param name
	 * @param desrcription
	 * @param constructionTypeAttributes
	 * @param constructionTypeArticles
	 * @param isMaster
	 * @param orders
	 * @param sketchName
	 * @param productArea
	 */
	public ConstructionType(Integer constructionTypeId, String name,
			String desrcription,
			Set<ConstructionTypeAttribute> constructionTypeAttributes,
			Set<ConstructionTypeArticle> constructionTypeArticles,
			Integer isMaster, Set<Order> orders, String sketchName,
			ProductArea productArea) {
		super();
		this.constructionTypeId = constructionTypeId;
		this.name = name;
		this.description = desrcription;
		this.constructionTypeAttributes = constructionTypeAttributes;
		this.constructionTypeArticles = constructionTypeArticles;
		this.isMaster = isMaster;
		this.orders = orders;
		this.sketchName = sketchName;
		this.productArea = productArea;
		
	}

	/**
	 * @return id
	 */
	public Integer getConstructionTypeId() {
		return constructionTypeId;
	}

	/**
	 * @param constructionTypeId
	 */
	public void setConstructionTypeId(Integer constructionTypeId) {
		this.constructionTypeId = constructionTypeId;
	}

	/**
	 * @return navn
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ConstructionType))
			return false;
		ConstructionType castOther = (ConstructionType) other;
		return new EqualsBuilder().append(name, castOther.name).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return beskrivelse
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return attributter
	 */
	public Set<ConstructionTypeAttribute> getConstructionTypeAttributes() {
		return constructionTypeAttributes;
	}

	/**
	 * @param constructionTypeAttributes
	 */
	public void setConstructionTypeAttributes(
			Set<ConstructionTypeAttribute> constructionTypeAttributes) {
		this.constructionTypeAttributes = constructionTypeAttributes;
	}

	/**
	 * @return attributter
	 */
	public List<Attribute> getAttributes() {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		if (constructionTypeAttributes != null) {
			for (ConstructionTypeAttribute attribute : constructionTypeAttributes) {
				attributes.add(attribute.getAttribute());
			}
		}
		return attributes;
	}

	/**
	 * @return artikler
	 */
	public List<ArticleType> getArticles() {
		ArrayList<ArticleType> articleTypes = new ArrayList<ArticleType>();
		if (constructionTypeArticles != null) {
			for (ConstructionTypeArticle article : constructionTypeArticles) {
				articleTypes.add(article.getArticleType());
			}
		}
		return articleTypes;
	}

	/**
	 * @return artikler
	 */
	public Set<ConstructionTypeArticle> getConstructionTypeArticles() {
		return constructionTypeArticles;
	}
	public Set<ConstructionTypeArticle> getClonedConstructionTypeArticles(){
	    Set<ConstructionTypeArticle> list = new LinkedHashSet<ConstructionTypeArticle>();
	    for(ConstructionTypeArticle article:constructionTypeArticles){
	        list.add(article.cloneObject());
	    }
	    return list;
	}

	/**
	 * @return klonede artikler
	 */

	/**
	 * @param orgSet
	 * @return klonede attributter
	 */

	/**
	 * @param constructionTypeArticles
	 */
	public void setConstructionTypeArticles(
			Set<ConstructionTypeArticle> constructionTypeArticles) {
		this.constructionTypeArticles = constructionTypeArticles;
	}

	/**
	 * @return om garasjetype er master
	 */
	public Integer getIsMaster() {
		return isMaster;
	}

	/**
	 * @param isMaster
	 */
	public void setIsMaster(Integer isMaster) {
		this.isMaster = isMaster;
	}

	/**
	 * @return ordre
	 */
	public Set<Order> getOrders() {
		return orders;
	}

	/**
	 * @param orders
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	/**
	 * @param article
	 */
	public void addConstructionTypeArticle(ConstructionTypeArticle article) {
		if (constructionTypeArticles == null) {
			constructionTypeArticles = new HashSet<ConstructionTypeArticle>();
		}
		article.setConstructionType(this);
		constructionTypeArticles.add(article);
	}

	/**
	 * @param article
	 */
	public void removeConstructionArticle(ConstructionTypeArticle article) {
		constructionTypeArticles.remove(article);
		article.setConstructionType(null);
	}

	/**
	 * @return skissenavn
	 */
	public String getSketchName() {
		return sketchName;
	}

	/**
	 * @param sketchName
	 */
	public void setSketchName(String sketchName) {
		this.sketchName = sketchName;
	}

	/**
	 * @return produktområde
	 */
	public ProductArea getProductArea() {
		return productArea;
	}

	/**
	 * @param productArea
	 */
	public void setProductArea(ProductArea productArea) {
		this.productArea = productArea;
	}

	/**
	 * @return true dersom master
	 */
	public Boolean isMaster() {
		return Util.convertNumberToBoolean(isMaster);
	}

	public int compareTo(final ConstructionType other) {
		return new CompareToBuilder().append(name, other.name).toComparison();
	}

	
}
