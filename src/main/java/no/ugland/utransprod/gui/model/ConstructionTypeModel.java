package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;
import no.ugland.utransprod.model.ConstructionTypeAttribute;
import no.ugland.utransprod.model.ProductArea;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.ArrayListModel;

/**
 * Modellklasse for garasjetype
 * 
 * @author atle.brekka
 * 
 */
public class ConstructionTypeModel extends
		AbstractModel<ConstructionType, ConstructionTypeModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String PROPERTY_CONSTRUCTION_TYPE_ID = "constructionTypeId";

	/**
	 * 
	 */
	public static final String PROPERTY_NAME = "name";

	/**
	 * 
	 */
	public static final String PROPERTY_DESCRIPTION = "description";

	/**
	 * 
	 */
	public static final String PROPERTY_CONSTRUCTION_TYPE_ATTRIBUTES = "constructionTypeAttributes";

	/**
	 * 
	 */
	public static final String PROPERTY_ATTRIBUTES = "attributes";

	/**
	 * 
	 */
	public static final String PROPERTY_ARTICLES = "articles";

	/**
	 * 
	 */
	public static final String PROPERTY_CONSTRUCTION_TYPE_ARTICLES = "constructionTypeArticles";

	/**
	 * 
	 */
	public static final String PROPERTY_SKETCH_NAME = "sketchName";

	/**
	 * 
	 */
	public static final String PROPERTY_PRODUCT_AREA = "productArea";

	/**
	 * 
	 */
	public static final String PROPERTY_IS_MASTER = "isMaster";

	/**
	 * 
	 */
	private ArrayListModel constructionTypeAttributes;

	/**
	 * 
	 */
	private ArrayListModel constructionTypeArticles;

	/**
	 * @param aConstructionType
	 */
	public ConstructionTypeModel(ConstructionType aConstructionType) {
		super(aConstructionType);
		if (object.getConstructionTypeAttributes() != null) {
			constructionTypeAttributes = new ArrayListModel(object
					.getConstructionTypeAttributes());
		} else {
			constructionTypeAttributes = new ArrayListModel();
		}

		if (object.getConstructionTypeArticles() != null) {
			constructionTypeArticles = new ArrayListModel(object
					.getConstructionTypeArticles());
		} else {
			constructionTypeArticles = new ArrayListModel();
		}
	}

	/**
	 * @return attributter
	 */
	public List<Attribute> getAttributes() {
		return object.getAttributes();
	}

	/**
	 * @return artikler
	 */
	public List<ArticleType> getArticles() {
		return object.getArticles();
	}

	/**
	 * @return attributter
	 */
	public ArrayListModel getConstructionTypeAttributes() {
		return constructionTypeAttributes;
	}

	/**
	 * @return artikler
	 */
	public ArrayListModel getConstructionTypeArticles() {
		return constructionTypeArticles;
	}

	/**
	 * @param constructionTypeAttributes
	 */
	public void setConstructionTypeAttributes(
			ArrayListModel constructionTypeAttributes) {
		ArrayListModel oldConstructionTypeAttributes = getConstructionTypeAttributes();
		this.constructionTypeAttributes = constructionTypeAttributes;
		firePropertyChange(PROPERTY_CONSTRUCTION_TYPE_ATTRIBUTES,
				oldConstructionTypeAttributes, constructionTypeAttributes);
	}

	/**
	 * @param constructionTypeArticles
	 */
	public void setConstructionTypeArticles(
			ArrayListModel constructionTypeArticles) {
		ArrayListModel oldConstructionTypeArticles = getConstructionTypeArticles();
		this.constructionTypeArticles = constructionTypeArticles;
		firePropertyChange(PROPERTY_CONSTRUCTION_TYPE_ARTICLES,
				oldConstructionTypeArticles, constructionTypeArticles);
	}

	/**
	 * @return id
	 */
	public Integer getConstructionTypeId() {
		return object.getConstructionTypeId();
	}

	/**
	 * @param constructionTypeId
	 */
	public void setConstructionTypeId(Integer constructionTypeId) {
		Integer oldId = getConstructionTypeId();
		object.setConstructionTypeId(constructionTypeId);
		firePropertyChange(PROPERTY_CONSTRUCTION_TYPE_ID, oldId,
				constructionTypeId);
	}

	/**
	 * @return navn
	 */
	public String getName() {
		return object.getName();
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		String oldName = getName();
		object.setName(name);
		firePropertyChange(PROPERTY_NAME, oldName, name);
	}

	/**
	 * @return beskrivelse
	 */
	public String getDescription() {
		return object.getDescription();
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		String oldDesc = getDescription();
		object.setDescription(description);
		firePropertyChange(PROPERTY_DESCRIPTION, oldDesc, description);
	}

	/**
	 * @return skissenavn
	 */
	public SketchEnum getSketchName() {
		return SketchEnum.getSketchEnum(object.getSketchName());

	}

	/**
	 * @param sketchEnum
	 */
	public void setSketchName(SketchEnum sketchEnum) {

		SketchEnum oldName = getSketchName();
		if (sketchEnum != null) {
			object.setSketchName(sketchEnum.getFileName());
		}
		firePropertyChange(PROPERTY_SKETCH_NAME, oldName, sketchEnum);
	}

	/**
	 * @return produktområde
	 */
	public ProductArea getProductArea() {
		return object.getProductArea();

	}

	/**
	 * @param productArea
	 */
	public void setProductArea(ProductArea productArea) {

		ProductArea oldArea = getProductArea();
		object.setProductArea(productArea);

		firePropertyChange(PROPERTY_PRODUCT_AREA, oldArea, productArea);
	}

	/**
	 * @return 1 dersom master
	 */
	public Integer getIsMaster() {
		return object.getIsMaster();

	}

	/**
	 * @param isMaster
	 */
	public void setIsMaster(Integer isMaster) {

		Integer oldMaster = getIsMaster();
		object.setIsMaster(isMaster);

		firePropertyChange(PROPERTY_IS_MASTER, oldMaster, isMaster);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_DESCRIPTION)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(
				PROPERTY_CONSTRUCTION_TYPE_ATTRIBUTES).addValueChangeListener(
				listener);
		presentationModel.getBufferedModel(PROPERTY_CONSTRUCTION_TYPE_ARTICLES)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_SKETCH_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PRODUCT_AREA)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_IS_MASTER)
				.addValueChangeListener(listener);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public ConstructionTypeModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		ConstructionTypeModel constructionTypeModel = new ConstructionTypeModel(
				new ConstructionType());
		constructionTypeModel.setConstructionTypeId((Integer) presentationModel
				.getBufferedValue(PROPERTY_CONSTRUCTION_TYPE_ID));
		constructionTypeModel.setName((String) presentationModel
				.getBufferedValue(PROPERTY_NAME));
		constructionTypeModel.setDescription((String) presentationModel
				.getBufferedValue(PROPERTY_DESCRIPTION));
		constructionTypeModel
				.setConstructionTypeAttributes((ArrayListModel) presentationModel
						.getBufferedValue(PROPERTY_CONSTRUCTION_TYPE_ATTRIBUTES));
		constructionTypeModel
				.setConstructionTypeArticles(((ArrayListModel) presentationModel
						.getBufferedValue(PROPERTY_CONSTRUCTION_TYPE_ARTICLES)));
		constructionTypeModel.setSketchName(((SketchEnum) presentationModel
				.getBufferedValue(PROPERTY_SKETCH_NAME)));
		constructionTypeModel.setProductArea(((ProductArea) presentationModel
				.getBufferedValue(PROPERTY_PRODUCT_AREA)));
		constructionTypeModel.setIsMaster(((Integer) presentationModel
				.getBufferedValue(PROPERTY_IS_MASTER)));
		return constructionTypeModel;
	}

	/**
	 * Opprette attributt for grasjetype
	 * 
	 * @param attribute
	 * @param attributeValue
	 * @param dialogOrder
	 * @return attributt for artikkel
	 */
	public ConstructionTypeAttribute getArticleAttribute(Attribute attribute,
			String attributeValue, Integer dialogOrder) {
		if (attribute != null) {

			return new ConstructionTypeAttribute(null, object, attribute,
					attributeValue, null, dialogOrder);
		}
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#viewToModel()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void viewToModel() {

		if (constructionTypeAttributes != null) {
			Set<ConstructionTypeAttribute> attributes = object
					.getConstructionTypeAttributes();
			if (attributes == null) {
				attributes = new HashSet<ConstructionTypeAttribute>();
			}
			attributes.clear();
			attributes.addAll(constructionTypeAttributes);
			object.setConstructionTypeAttributes(attributes);
		}

		if (constructionTypeArticles != null) {
			Set<ConstructionTypeArticle> articles = object
					.getConstructionTypeArticles();
			if (articles == null) {
				articles = new HashSet<ConstructionTypeArticle>();
			}
			articles.clear();
			articles.addAll(constructionTypeArticles);
			object.setConstructionTypeArticles(articles);
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#modelToView()
	 */
	@Override
	public void modelToView() {
		if (object.getConstructionTypeAttributes() != null) {
			constructionTypeAttributes.clear();
			constructionTypeAttributes.addAll(object
					.getConstructionTypeAttributes());
		}

		if (object.getConstructionTypeArticles() != null) {
			constructionTypeArticles.clear();
			constructionTypeArticles.addAll(object
					.getConstructionTypeArticles());
		}
	}

	/**
	 * Kloner garasjetype
	 * 
	 * @param original
	 * @return klonet garasjetype
	 */
	public static ConstructionType cloneConstructionType(
			ConstructionType original) {
		ConstructionType cloned = new ConstructionType();
		cloned.setConstructionTypeId(original.getConstructionTypeId());
		cloned
				.setConstructionTypeArticles(clonedConstructionTypeArticles(original
						.getConstructionTypeArticles()));
		return cloned;
	}

	/**
	 * Kloner artikler for grasjetype
	 * 
	 * @param articles
	 * @return klonede artikler
	 */
	public static Set<ConstructionTypeArticle> clonedConstructionTypeArticles(
			Set<ConstructionTypeArticle> articles) {
		HashSet<ConstructionTypeArticle> clonedSet = new HashSet<ConstructionTypeArticle>();
		if (articles != null) {
			for (ConstructionTypeArticle article : articles) {
				clonedSet.add(new ConstructionTypeArticle(article
						.getConstructionTypeArticleId(), article
						.getConstructionType(), article.getArticleType(),
						clonedAttributes(article.getAttributes()), article
								.getConstructionTypeArticleRef(),
						clonedConstructionTypeArticles(article
								.getConstructionTypeArticles()), article
								.getNumberOfItems(), article.getOrderLines(),
						article.getDialogOrder()));
			}
		}
		return clonedSet;
	}

	/**
	 * Kloner attributter for srtikkel
	 * 
	 * @param orgSet
	 * @return klone attributter
	 */
	private static Set<ConstructionTypeArticleAttribute> clonedAttributes(
			Set<ConstructionTypeArticleAttribute> orgSet) {
		HashSet<ConstructionTypeArticleAttribute> clonedSet = new HashSet<ConstructionTypeArticleAttribute>();

		if (orgSet != null) {
			for (ConstructionTypeArticleAttribute attribute : orgSet) {
				clonedSet.add(new ConstructionTypeArticleAttribute(attribute
						.getConstructionTypeArticleAttributeId(), attribute
						.getConstructionTypeArticle(), attribute
						.getArticleTypeAttribute(), attribute
						.getConstructionTypeArticleValue(), attribute
						.getDialogOrder()));
			}
		}
		return clonedSet;
	}

	/**
	 * Kopierer attributter
	 * 
	 * @param constructionType
	 * @param attributes
	 * @return garasjeattributter
	 */
	public static Set<ConstructionTypeAttribute> copyConstructionTypeAttributes(
			ConstructionType constructionType,
			Set<ConstructionTypeAttribute> attributes) {
		HashSet<ConstructionTypeAttribute> newAttributes = null;
		if (attributes != null) {
			newAttributes = new HashSet<ConstructionTypeAttribute>();
			for (ConstructionTypeAttribute attribute : attributes) {
				newAttributes.add(new ConstructionTypeAttribute(null,
						constructionType, attribute.getAttribute(), attribute
								.getAttributeValue(), attribute
								.getOrderLineAttributes(), attribute
								.getDialogOrder()));
			}
		}
		return newAttributes;
	}

	/**
	 * Kopierer artikler
	 * 
	 * @param newConstructionType
	 * @param orgArticles
	 * @param constructionTypeArticleRef
	 * @return garasjeartikler
	 */
	public static Set<ConstructionTypeArticle> copyConstructionTypeArticles(
			ConstructionType newConstructionType,
			Set<ConstructionTypeArticle> orgArticles,
			ConstructionTypeArticle constructionTypeArticleRef) {
		HashSet<ConstructionTypeArticle> newArticles = null;

		if (orgArticles != null && orgArticles.size() != 0) {
			newArticles = new HashSet<ConstructionTypeArticle>();

			ConstructionTypeArticle newConstructionTypeArticle;
			for (ConstructionTypeArticle article : orgArticles) {
				newConstructionTypeArticle = new ConstructionTypeArticle(null,
						newConstructionType, article.getArticleType(), null,
						constructionTypeArticleRef, null, article
								.getNumberOfItems(), null, article
								.getDialogOrder());

				newConstructionTypeArticle
						.setConstructionTypeArticles(copyConstructionTypeArticles(
								null, article.getConstructionTypeArticles(),
								newConstructionTypeArticle));
				newConstructionTypeArticle
						.setAttributes(copyConstructionTypeArticleAttributes(
								newConstructionTypeArticle, article
										.getAttributes()));

				newArticles.add(newConstructionTypeArticle);
			}
		}
		return newArticles;
	}

	/**
	 * Kopierer artikkelattributter
	 * 
	 * @param constructionTypeArticle
	 * @param attributes
	 * @return artikkelattributter
	 */
	private static Set<ConstructionTypeArticleAttribute> copyConstructionTypeArticleAttributes(
			ConstructionTypeArticle constructionTypeArticle,
			Set<ConstructionTypeArticleAttribute> attributes) {
		HashSet<ConstructionTypeArticleAttribute> newAttributes = null;
		if (attributes != null && attributes.size() != 0) {
			newAttributes = new HashSet<ConstructionTypeArticleAttribute>();

			for (ConstructionTypeArticleAttribute attribute : attributes) {
				newAttributes.add(new ConstructionTypeArticleAttribute(null,
						constructionTypeArticle, attribute
								.getArticleTypeAttribute(), attribute
								.getConstructionTypeArticleValue(), attribute
								.getDialogOrder()));
			}
		}
		return newAttributes;
	}
}
