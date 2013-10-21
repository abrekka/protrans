package no.ugland.utransprod.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klasse som representerer tabell ATTRIBUTE
 * @author atle.brekka
 */
public class Attribute extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Integer attributeId;

    private String name;

    private String description;

    private Integer yesNo;

    private Set<ArticleTypeAttribute> articleTypeAttributes;

    private Set<AttributeChoice> attributeChoices;

    private Set<ConstructionTypeAttribute> constructionTypeAttributes;

    private Integer specialConcern;
    private Integer prodCatNo;
    private Integer prodCatNo2;

    private String attributeDataType;
    public static final Attribute UNKNOWN = new Attribute() {
    };

    /**
	 * 
	 */
    public Attribute() {
        super();
    }

    /**
     * @param attributeId
     * @param name
     * @param description
     * @param articleTypeAttributes
     * @param yesNo
     * @param attributeChoices
     * @param constructionTypeAttributes
     * @param specialConcern
     */
    public Attribute(Integer attributeId, String name, String description,
            Set<ArticleTypeAttribute> articleTypeAttributes, Integer yesNo,
            Set<AttributeChoice> attributeChoices, Set<ConstructionTypeAttribute> constructionTypeAttributes,
            Integer specialConcern, final Integer aProdCatNo, final Integer aProdCatNo2,final String aAttributeDataType) {
        super();
        this.attributeId = attributeId;
        this.name = name;
        this.description = description;
        this.articleTypeAttributes = articleTypeAttributes;
        this.yesNo = yesNo;
        this.attributeChoices = attributeChoices;
        this.constructionTypeAttributes = constructionTypeAttributes;
        this.specialConcern = specialConcern;
        this.prodCatNo = aProdCatNo;
        this.prodCatNo2 = aProdCatNo2;
        this.attributeDataType=aAttributeDataType;
    }

    /**
     * @return id
     */
    public Integer getAttributeId() {
        return attributeId;
    }

    /**
     * @param attributeId
     */
    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
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
        if (!(other instanceof Attribute))
            return false;
        Attribute castOther = (Attribute) other;
        return new EqualsBuilder().append(name.toUpperCase(), castOther.name.toUpperCase()).isEquals();

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
     * @return attributter for artikkel
     */
    public Set<ArticleTypeAttribute> getArticleTypeAttributes() {
        return articleTypeAttributes;
    }

    /**
     * @param articleTypeAttributes
     */
    public void setArticleTypeAttributes(Set<ArticleTypeAttribute> articleTypeAttributes) {
        this.articleTypeAttributes = articleTypeAttributes;
    }

    /**
     * @return om attributt er et ja/nei valg
     */
    public Integer getYesNo() {
        return yesNo;
    }

    /**
     * @param yesNo
     */
    public void setYesNo(Integer yesNo) {
        this.yesNo = yesNo;
    }

    /**
     * @return attributtvalg
     */
    public Set<AttributeChoice> getAttributeChoices() {
        return attributeChoices;
    }

    /**
     * @param attributeChoices
     */
    public void setAttributeChoices(Set<AttributeChoice> attributeChoices) {
        this.attributeChoices = attributeChoices;
    }

    /**
     * @return attributtvalg son strenger
     */
    public List<String> getChoices() {
        List<String> strings = new ArrayList<String>();
        if (attributeChoices != null && attributeChoices.size() != 0) {
            for (AttributeChoice choice : attributeChoices) {
                strings.add(choice.getChoiceValue());
            }
        }
        Collections.sort(strings);
        return strings;
    }

    /**
     * @return garasjetypeattributter
     */
    public Set<ConstructionTypeAttribute> getConstructionTypeAttributes() {
        return constructionTypeAttributes;
    }

    /**
     * @param constructionTypeAttributes
     */
    public void setConstructionTypeAttributes(Set<ConstructionTypeAttribute> constructionTypeAttributes) {
        this.constructionTypeAttributes = constructionTypeAttributes;
    }

    /**
     * @return om attributt har spesielt hensyn
     */
    public Integer getSpecialConcern() {
        return specialConcern;
    }

    /**
     * @param specialConcern
     */
    public void setSpecialConcern(Integer specialConcern) {
        this.specialConcern = specialConcern;
    }

    public Integer getProdCatNo() {
        return prodCatNo;
    }

    public void setProdCatNo(Integer prodCatNo) {
        this.prodCatNo = prodCatNo;
    }

    public Integer getProdCatNo2() {
        return prodCatNo2;
    }

    public void setProdCatNo2(Integer prodCatNo2) {
        this.prodCatNo2 = prodCatNo2;
    }

    public boolean hasProdCatNo() {
        return prodCatNo != null || prodCatNo2 != null ? true : false;
    }

    public void removeAttributeChoice(AttributeChoice choice) {
        if (attributeChoices != null) {
            attributeChoices.remove(choice);
            choice.setAttribute(null);
        }
    }

    public void addAttributeChoice(AttributeChoice choice) {
        attributeChoices = attributeChoices == null ? new HashSet<AttributeChoice>() : attributeChoices;
        choice.setAttribute(this);
        attributeChoices.add(choice);
    }

    public String getAttributeDataType() {
        return attributeDataType;
    }

    public void setAttributeDataType(String attributeDataType) {
        this.attributeDataType = attributeDataType;
    }
}
