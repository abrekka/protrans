package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klasse for tabell ATTRIBUTE_CHOICE
 * 
 * @author atle.brekka
 * 
 */
public class AttributeChoice extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Integer attributeChoiceId;

	private String choiceValue;

	private Attribute attribute;

	private String comment;
	private Integer prodCatNo;
	private Integer prodCatNo2;

	public AttributeChoice() {
		super();
	}

	/**
	 * @param attributeChoiceId
	 * @param choiceValue
	 * @param attribute
	 * @param comment
	 */
	public AttributeChoice(Integer attributeChoiceId, String choiceValue,
			Attribute attribute, String comment,final Integer aProdCatNo,final Integer aProdCatNo2) {
		super();
		this.attributeChoiceId = attributeChoiceId;
		this.choiceValue = choiceValue;
		this.attribute = attribute;
		this.comment = comment;
	}

	/**
	 * @return attributt
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute
	 */
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return id
	 */
	public Integer getAttributeChoiceId() {
		return attributeChoiceId;
	}

	/**
	 * @param attributeChoiceId
	 */
	public void setAttributeChoiceId(Integer attributeChoiceId) {
		this.attributeChoiceId = attributeChoiceId;
	}

	/**
	 * @return verdi
	 */
	public String getChoiceValue() {
		return choiceValue;
	}

	/**
	 * @param choiceValue
	 */
	public void setChoiceValue(String choiceValue) {
		this.choiceValue = choiceValue;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof AttributeChoice))
			return false;
		AttributeChoice castOther = (AttributeChoice) other;
		return new EqualsBuilder().append(choiceValue, castOther.choiceValue)
				.append(attribute, castOther.attribute).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(choiceValue).append(attribute)
				.toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return choiceValue;
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
    public boolean hasProdCatNo(){
        return prodCatNo!=null||prodCatNo2!=null?true:false;
    }
}
