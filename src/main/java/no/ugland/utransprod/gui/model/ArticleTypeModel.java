package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ArticleTypeArticleType;
import no.ugland.utransprod.model.ArticleTypeAttribute;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.ArrayListModel;

/**
 * Modellklasse for artikkeltype
 * @author atle.brekka
 */
public class ArticleTypeModel extends
        AbstractModel<ArticleType, ArticleTypeModel> {
    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_ARTICLE_TYPE_ID = "articleTypeId";

    /**
     * 
     */
    public static final String PROPERTY_ARTICLE_TYPE_NAME = "articleTypeName";

    /**
     * 
     */
    public static final String PROPERTY_DESCRIPTION = "description";

    /**
     * 
     */
    public static final String PROPERTY_ARTICLE_TYPE_ATTRIBUTES = "articleTypeAttributes";

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
    public static final String PROPERTY_ARTICLE_TYPE_ARTICLE_TYPES = "articleTypeArticleTypes";

    /**
     * 
     */
    public static final String PROPERTY_TOP_LEVEL_BOOL = "topLevelBoolean";

    /**
     * 
     */
    public static final String PROPERTY_METRIC = "metric";
    public static final String PROPERTY_PROD_CAT_NO = "prodCatNo";
    public static final String PROPERTY_PROD_CAT_NO_2 = "prodCatNo2";
    public static final String PROPERTY_FORCE_IMPORT_BOOL = "forceImportBoolean";

    /**
     * 
     */
    private ArrayListModel articleTypeAttributes;

    /**
     * 
     */
    private ArrayListModel articleTypeArticleTypes;

    /**
     * @param object
     */
    public ArticleTypeModel(ArticleType object) {
        super(object);
        if (object.getArticleTypeAttributes() != null) {
            articleTypeAttributes = new ArrayListModel(object
                    .getArticleTypeAttributes());
        } else {
            articleTypeAttributes = new ArrayListModel();
        }

        if (object.getArticleTypeArticleTypes() != null) {
            articleTypeArticleTypes = new ArrayListModel(object
                    .getArticleTypeArticleTypes());
        } else {
            articleTypeArticleTypes = new ArrayListModel();
        }
    }

    /**
     * Henter artikler
     * @return artikler
     */
    public List<ArticleType> getArticles() {
        return object.getArticles();
    }

    /**
     * @return id
     */
    public Integer getArticleTypeId() {
        return object.getArticleTypeId();
    }

    /**
     * @param articleTypeId
     */
    public void setArticleTypeId(Integer articleTypeId) {
        Integer oldId = getArticleTypeId();
        object.setArticleTypeId(articleTypeId);
        firePropertyChange(PROPERTY_ARTICLE_TYPE_ID, oldId, articleTypeId);
    }

    /**
     * @return navn
     */
    public String getArticleTypeName() {
        return object.getArticleTypeName();
    }

    /**
     * @param articleTypeName
     */
    public void setArticleTypeName(String articleTypeName) {
        String oldName = getArticleTypeName();
        object.setArticleTypeName(articleTypeName);
        firePropertyChange(PROPERTY_ARTICLE_TYPE_NAME, oldName, articleTypeName);
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
     * @return attributter for artikkeltype
     */
    public ArrayListModel getArticleTypeAttributes() {
        return articleTypeAttributes;
    }

    /**
     * @param articleTypeAttributes
     */
    public void setArticleTypeAttributes(ArrayListModel articleTypeAttributes) {
        ArrayListModel oldArticleTypeAttributes = getArticleTypeAttributes();
        this.articleTypeAttributes = articleTypeAttributes;
        firePropertyChange(PROPERTY_ARTICLE_TYPE_ATTRIBUTES,
                oldArticleTypeAttributes, articleTypeAttributes);
    }

    /**
     * Henter artikler som referer til artikkel
     * @return artikler
     */
    public ArrayListModel getArticleTypeArticleTypes() {
        return articleTypeArticleTypes;
    }

    /**
     * @param articleTypeArticleTypes
     */
    public void setArticleTypeArticleTypes(
            ArrayListModel articleTypeArticleTypes) {
        ArrayListModel oldArticleTypeArticles = getArticleTypeArticleTypes();
        this.articleTypeArticleTypes = articleTypeArticleTypes;
        firePropertyChange(PROPERTY_ARTICLE_TYPE_ARTICLE_TYPES,
                oldArticleTypeArticles, articleTypeArticleTypes);
    }

    /**
     * @return attributter
     */
    public List<Attribute> getAttributes() {
        return object.getAttributes();
    }

    /**
     * @param attributes
     */
    public void setAttributes(List<Attribute> attributes) {
        if (attributes != null) {
            ArrayListModel oldAttributes = getArticleTypeAttributes();
            if (oldAttributes == null) {
                oldAttributes = new ArrayListModel();
            }
            ArticleTypeAttribute articleTypeAttribute;
            for (Attribute attribute : attributes) {
                articleTypeAttribute = new ArticleTypeAttribute(null, object,
                        attribute, null, null);
                oldAttributes.add(articleTypeAttribute);
            }
            setArticleTypeAttributes(oldAttributes);
        }
    }

    /**
     * @param attribute
     */
    public void removeAttribute(ArticleTypeAttribute attribute) {
        ArrayListModel oldAttributes = new ArrayListModel(
                getArticleTypeAttributes());
        oldAttributes.remove(attribute);
        setArticleTypeAttributes(oldAttributes);
    }
    
    public String getProdCatNo(){
        return Util.convertIntegerToString(object.getProdCatNo());
    }
    public void setProdCatNo(String aNo){
        String oldNo=getProdCatNo();
        object.setProdCatNo(Util.convertStringToInteger(aNo));
        firePropertyChange(PROPERTY_PROD_CAT_NO, oldNo, aNo);
    }
    public String getProdCatNo2(){
        return Util.convertIntegerToString(object.getProdCatNo2());
    }
    public void setProdCatNo2(String aNo){
        String oldNo=getProdCatNo2();
        object.setProdCatNo2(Util.convertStringToInteger(aNo));
        firePropertyChange(PROPERTY_PROD_CAT_NO_2, oldNo, aNo);
    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
     */
    @Override
    public ArticleTypeModel getBufferedObjectModel(
            PresentationModel presentationModel) {
        ArticleTypeModel articleTypeModel = new ArticleTypeModel(
                new ArticleType());
        articleTypeModel.setArticleTypeId((Integer) presentationModel
                .getBufferedValue(PROPERTY_ARTICLE_TYPE_ID));
        articleTypeModel.setArticleTypeName((String) presentationModel
                .getBufferedValue(PROPERTY_ARTICLE_TYPE_NAME));
        articleTypeModel.setDescription((String) presentationModel
                .getBufferedValue(PROPERTY_DESCRIPTION));
        articleTypeModel
                .setArticleTypeAttributes((ArrayListModel) presentationModel
                        .getBufferedValue(PROPERTY_ARTICLE_TYPE_ATTRIBUTES));
        articleTypeModel
        .setProdCatNo((String) presentationModel
                .getBufferedValue(PROPERTY_PROD_CAT_NO));
        articleTypeModel
        .setProdCatNo2((String) presentationModel
                .getBufferedValue(PROPERTY_PROD_CAT_NO_2));
        articleTypeModel.setForceImportBoolean((Boolean)presentationModel
                .getBufferedValue(PROPERTY_FORCE_IMPORT_BOOL));
        return articleTypeModel;
    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
     *      com.jgoodies.binding.PresentationModel)
     */
    @Override
    public void addBufferChangeListener(PropertyChangeListener listener,
            PresentationModel presentationModel) {
        presentationModel.getBufferedModel(PROPERTY_ARTICLE_TYPE_ID)
                .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_ARTICLE_TYPE_NAME)
                .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_DESCRIPTION)
                .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_ARTICLE_TYPE_ATTRIBUTES)
                .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_PROD_CAT_NO)
        .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_PROD_CAT_NO_2)
        .addValueChangeListener(listener);
        presentationModel.getBufferedModel(PROPERTY_FORCE_IMPORT_BOOL)
        .addValueChangeListener(listener);
    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#viewToModel()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void viewToModel() {
        if (articleTypeAttributes != null) {
            Set<ArticleTypeAttribute> attributes = object
                    .getArticleTypeAttributes();
            if (attributes == null) {
                attributes = new HashSet<ArticleTypeAttribute>();
            }
            attributes.clear();
            attributes.addAll(articleTypeAttributes);
            object.setArticleTypeAttributes(attributes);
        }

        if (articleTypeArticleTypes != null) {
            Set<ArticleTypeArticleType> refs = object
                    .getArticleTypeArticleTypes();
            if (refs == null) {
                refs = new HashSet<ArticleTypeArticleType>();
            }
            refs.clear();
            refs.addAll(articleTypeArticleTypes);
            object.setArticleTypeArticleTypes(refs);
        }

    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#modelToView()
     */
    @Override
    public void modelToView() {
        if (object.getArticleTypeAttributes() != null) {
            articleTypeAttributes.clear();
            articleTypeAttributes.addAll(object.getArticleTypeAttributes());
        }

        if (object.getArticleTypeArticleTypes() != null) {
            articleTypeArticleTypes.clear();
            articleTypeArticleTypes.addAll(object.getArticleTypeArticleTypes());
        }
    }

    /**
     * Henter attributter
     * @param attributes
     * @return attributter
     */
    @SuppressWarnings("unchecked")
    public List<ArticleTypeAttribute> getArticleAttributes(
            List<Attribute> attributes) {
        ArrayListModel tmpArticleTypeAttributes = new ArrayListModel();
        if (attributes != null) {

            for (Attribute attribute : attributes) {
                tmpArticleTypeAttributes.add(new ArticleTypeAttribute(null,
                        object, attribute, null, null));
            }
        }
        return tmpArticleTypeAttributes;
    }

    /**
     * Gir beskjed om at properties er endret
     */
    public void firePropertyChange() {
        fireMultiplePropertiesChanged();
    }

    /**
     * Kloner artikkel
     * @param orgArticle
     * @return klonet artikkel
     */
    public static ArticleType cloneArticleType(ArticleType orgArticle) {
        ArticleType clonedArticle = new ArticleType();
        clonedArticle.setArticleTypeId(orgArticle.getArticleTypeId());
        clonedArticle
                .setArticleTypeArticleTypes(cloneArticleTypeArticleTypes(orgArticle
                        .getArticleTypeArticleTypes()));
        clonedArticle.setArticleTypeName(orgArticle.getArticleTypeName());
        return clonedArticle;
    }

    /**
     * Kloner artikkelreferanser
     * @param refs
     * @return klonede artikkelreferanser
     */
    public static Set<ArticleTypeArticleType> cloneArticleTypeArticleTypes(
            Set<ArticleTypeArticleType> refs) {
        HashSet<ArticleTypeArticleType> clonedRefs = new HashSet<ArticleTypeArticleType>();
        if (refs != null) {
            for (ArticleTypeArticleType articleType : refs) {
                clonedRefs.add(new ArticleTypeArticleType(articleType
                        .getArticleTypeArticleTypeId(), articleType
                        .getArticleType(), articleType.getArticleTypeRef()));
            }
        }
        return clonedRefs;
    }

    /**
     * @return true dersom artikkel er en toppartikkel
     */
    public Boolean getTopLevelBoolean() {
        return object.getTopLevelBoolean();
    }

    /**
     * @param topLevel
     */
    public void setTopLevelBoolean(Boolean topLevel) {
        Boolean oldBool = getTopLevelBoolean();
        object.setTopeLevelBoolean(topLevel);
        firePropertyChange(PROPERTY_TOP_LEVEL_BOOL, oldBool, topLevel);
    }
    
    public Boolean getForceImportBoolean() {
        return object.forceImport();
    }

    /**
     * @param topLevel
     */
    public void setForceImportBoolean(Boolean force) {
        Boolean oldBool = getForceImportBoolean();
        object.setForceImportBoolean(force);
        firePropertyChange(PROPERTY_FORCE_IMPORT_BOOL, oldBool, force);
    }

    /**
     * @return betegnelse
     */
    public String getMetric() {
        return object.getMetric();
    }

    /**
     * @param metric
     */
    public void setMetric(String metric) {
        String oldMetric = getMetric();
        object.setMetric(metric);
        firePropertyChange(PROPERTY_METRIC, oldMetric, metric);
    }
}
