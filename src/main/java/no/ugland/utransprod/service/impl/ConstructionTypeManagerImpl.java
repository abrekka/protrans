package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.ProTransRuntimeException;
import no.ugland.utransprod.dao.ConstructionTypeDAO;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;
import no.ugland.utransprod.model.ConstructionTypeAttribute;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeAttributeEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.hibernate.Hibernate;

import com.google.common.collect.Sets;
import com.google.inject.internal.Lists;

/**
 * Implementasjon av manager for garasjetype.
 * 
 * @author atle.brekka
 */
public class ConstructionTypeManagerImpl extends ManagerImpl<ConstructionType>
		implements ConstructionTypeManager {
	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#
	 *      saveConstructionType(no.ugland.utransprod.model.ConstructionType)
	 */
	public final void saveConstructionType(
			final ConstructionType constructionType) {
		dao.saveObject(constructionType);

	}

	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#removeAll()
	 */
	public final void removeAll() {
		dao.removeAll();

	}

	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#findAll()
	 */
	public final List<ConstructionType> findAll() {
		return ((ConstructionTypeDAO) dao).findAllExceptMaster();
	}

	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#findByName(java.lang.String)
	 */
	public final ConstructionType findByName(final String aName) {
		ConstructionType constructionType = new ConstructionType();
		constructionType.setName(aName);
		List<ConstructionType> types = dao.findByExample(constructionType);
		if (types == null || types.size() != 1) {
			return null;
		}
		return types.get(0);
	}

	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#
	 *      removeConstructionType(no.ugland.utransprod.model.ConstructionType)
	 */
	public final void removeConstructionType(
			final ConstructionType constructionType) {
		dao.removeObject(constructionType.getConstructionTypeId());

	}

	/**
	 * @param object
	 * @return garasjetyper
	 * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
	 */
	public final List<ConstructionType> findByObject(
			final ConstructionType object) {

		return dao.findByExampleLike(object);
	}

	/**
	 * @param object
	 * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
	 */
	public final void removeObject(final ConstructionType object) {
		removeConstructionType(object);

	}

	/**
	 * @param object
	 * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
	 */
	public final void saveObject(final ConstructionType object) {
		saveConstructionType(object);

	}

	/**
	 * @param constructionType
	 * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
	 */
	public final void refreshObject(final ConstructionType constructionType) {
		((ConstructionTypeDAO) dao).refreshObject(constructionType);

	}

	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#
	 *      findMaster(no.ugland.utransprod.model.ProductArea)
	 */
	public final ConstructionType findMaster(final ProductArea productArea) {
		List<ConstructionType> types = ((ConstructionTypeDAO) dao)
				.findMasterByProductArea(productArea);
		if (types != null && types.size() == 1) {
			return types.get(0);
		} else if (types != null && types.size() > 1) {
			throw new ProTransRuntimeException("Finnes mer enn en master");
		}
		return null;
	}

	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#
	 *      lazyLoad(no.ugland.utransprod.model.ConstructionType,
	 *      no.ugland.utransprod.service.enums.LazyLoadConstructionTypeEnum[])
	 */
	public final void lazyLoad(final ConstructionType constructionType,
			final LazyLoadConstructionTypeEnum[] enums) {
		((ConstructionTypeDAO) dao).lazyLoad(constructionType, enums);

	}

	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#
	 *      lazyLoadAttribute(no.ugland.utransprod.model.ConstructionTypeAttribute,
	 *      no.ugland.utransprod.service.enums.LazyLoadConstructionTypeAttributeEnum[])
	 */
	public final void lazyLoadAttribute(
			final ConstructionTypeAttribute constructionTypeAttribute,
			final LazyLoadConstructionTypeAttributeEnum[] enums) {
		((ConstructionTypeDAO) dao).lazyLoadAttribute(
				constructionTypeAttribute, enums);

	}

	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#
	 *      lazyLoadArticle(no.ugland.utransprod.model.ConstructionTypeArticle,
	 *      no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum[])
	 */
	public final void lazyLoadArticle(final ConstructionTypeArticle article,
			final LazyLoadConstructionTypeArticleEnum[] enums) {
		((ConstructionTypeDAO) dao).lazyLoadArticle(article, enums);

	}

	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#
	 *      lazyLoadTree(no.ugland.utransprod.model.ConstructionType)
	 */
	public final void lazyLoadTree(final ConstructionType constructionType) {
		((ConstructionTypeDAO) dao).lazyLoadTree(constructionType);

	}

	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#findAllIncludeMaster()
	 */
	public final List<ConstructionType> findAllIncludeMaster() {
		return dao.getObjects();
	}

	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#
	 *      findByProductArea(no.ugland.utransprod.model.ProductArea)
	 */
	public final List<ConstructionType> findByProductArea(
			final ProductArea productArea) {
		return ((ConstructionTypeDAO) dao).findByProductArea(productArea);
	}

	/**
	 * @see no.ugland.utransprod.service.ConstructionTypeManager#findAllMasters()
	 */
	public final List<ConstructionType> findAllMasters() {
		return ((ConstructionTypeDAO) dao).findAllMasters();
	}

	@SuppressWarnings("unchecked")
	public void lazyLoad(ConstructionType object, Enum[] enums) {
		lazyLoad(object, (LazyLoadConstructionTypeEnum[]) enums);

	}

	@Override
	protected Serializable getObjectId(ConstructionType object) {
		return object.getConstructionTypeId();
	}

	public Set<OrderLine> getOrderLinesForNewConstructionType(
			Collection<OrderLine> originalOrderLines,
			ConstructionType newConstructionType, Order order,
			Deviation deviation) {

		if (newConstructionType != null) {
			return setupAndGetOrderLinesForConstructionType(originalOrderLines,
					newConstructionType, order, deviation);
		}
		return originalOrderLines != null ? Sets.newHashSet(originalOrderLines)
				: null;
	}

	private Set<OrderLine> setupAndGetOrderLinesForConstructionType(
			Collection<OrderLine> originalOrderLines,
			ConstructionType newConstructionType, Order order,
			Deviation deviation) {
		Set<OrderLine> orderLines;
		List<OrderLine> vismaOrderLines = extractVismaOrderLines(originalOrderLines);

		// Henter ut grasjeattributter
		Set<ConstructionTypeAttribute> attributes = newConstructionType
				.getConstructionTypeAttributes();

		// Setter ordrelinje og attributter for garasjeattributter
		orderLines = attributes != null && attributes.size() != 0 ? setOrderLineAndAttributes(
				attributes, order, deviation)
				: new LinkedHashSet<OrderLine>();

		// Henter ut garasjeartikler
		Set<ConstructionTypeArticle> articles = newConstructionType
				.getConstructionTypeArticles();

		articles = removeNotImportedArticles(articles, vismaOrderLines);

		if (articles != null && articles.size() != 0) {
			setArticles(orderLines, articles, order, deviation);
		}
		
		List<OrderLine> pureVismaOrderLines =getPureVismaOrderLines(orderLines, vismaOrderLines);
		orderLines = updateOrderLinesFromVisma(orderLines, vismaOrderLines);
		orderLines.addAll(pureVismaOrderLines);
		return orderLines;
	}

	private Set<ConstructionTypeArticle> removeNotImportedArticles(
			Set<ConstructionTypeArticle> articles,
			List<OrderLine> vismaOrderLines) {
		Set<ConstructionTypeArticle> returnArticles = new HashSet<ConstructionTypeArticle>();
		for (ConstructionTypeArticle article : articles) {
			addArticle(vismaOrderLines, returnArticles, article);
		}
		return returnArticles;
	}

	private void addArticle(List<OrderLine> vismaOrderLines,
			Set<ConstructionTypeArticle> returnArticles,
			ConstructionTypeArticle article) {
		if (articleMustBeImported(article)) {
			if (articleIsImported(article, vismaOrderLines)) {
				returnArticles.add(article);
			}
		} else {
			returnArticles.add(article);
		}
	}

	private boolean articleIsImported(ConstructionTypeArticle article,
			List<OrderLine> vismaOrderLines) {
		if (vismaOrderLines != null) {
			for (OrderLine orderLine : vismaOrderLines) {
				if (orderLine.getArticleType() != null
						&& article.getArticleType() != null
						&& orderLine.getArticleType().equals(
								article.getArticleType())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean articleMustBeImported(ConstructionTypeArticle article) {
		return article.getArticleType() != null
				&& article.getArticleType().forceImport();
	}
	
	
	/**
	 * Henter ut odrelinjer som kun kommer fra visma, og som ikke er definert på konstruksjonstype
	 * @param orderLines
	 * @param vismaOrderLines
	 * @return
	 */
	private List<OrderLine> getPureVismaOrderLines(
			Set<OrderLine> orderLines, Collection<OrderLine> vismaOrderLines){
		List<OrderLine> notConstructionOrderLines = Lists.newArrayList();
		Set<String> usedOrderLineNames = Sets.newHashSet();

		if (vismaOrderLines != null) {
			for (OrderLine orderLine : vismaOrderLines) {
				OrderLine line = getOrderLine(orderLine, orderLines,
						usedOrderLineNames);
				if (line == null) {
					notConstructionOrderLines.add(orderLine);
				}else{
					usedOrderLineNames.add(orderLine.getArticleName());
				}
			}
		}
		return notConstructionOrderLines;
	}

	public Set<OrderLine> updateOrderLinesFromVisma(
			Set<OrderLine> orderLines, Collection<OrderLine> vismaOrderLines) {
		Set<String> usedOrderLineNames = Sets.newHashSet();

		if (vismaOrderLines != null) {
			for (OrderLine orderLine : vismaOrderLines) {
				OrderLine line = getOrderLine(orderLine, orderLines,
						usedOrderLineNames);
				if (line != null) {
					Set<OrderLine> orderLineRefList=updateOrderLinesFromVisma(line.getOrderLines(), orderLine.getOrderLines());
					line.setOrderLines(orderLineRefList);
					line.setOrdln(orderLine.getOrdln());
					line.setNumberOfItems(orderLine.getNumberOfItems());
					line.setAttributeValuesFromOrderLine(Util.getSet(orderLine
							.getOrderLineAttributes()));
					line.setOrdNo(orderLine.getOrdNo());
					line.setLnNo(orderLine.getLnNo());
					usedOrderLineNames.add(line.getArticleName());
				}
			}
		}
		return orderLines;
	}
	
	

	private OrderLine getOrderLine(OrderLine orderLine,
			Set<OrderLine> orderLines, Set<String> usedOrderLineNames) {
		if (orderLines != null && orderLine != null) {
			for (OrderLine line : orderLines) {
				if (!usedOrderLineNames.contains(line.getArticleName())
						&& line.getArticleName().equalsIgnoreCase(
								orderLine.getArticleName())) {
					return line;
				}
			}
		}
		return null;
	}

	private void setArticles(

	final Set<OrderLine> orderLines,
			final Set<ConstructionTypeArticle> articles, final Order order,
			final Deviation deviation) {
		ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
				.getBean("constructionTypeManager");

		if (articles.size() != 0) {
			for (ConstructionTypeArticle article : articles) {
				createAndAddOrderLine(orderLines, constructionTypeManager,
						article, order, deviation);
			}
		}
	}

	private void createAndAddOrderLine(final Set<OrderLine> orderLines,
			final ConstructionTypeManager constructionTypeManager,
			final ConstructionTypeArticle article, final Order order,
			final Deviation deviation) {
		OrderLine orderLineMain;
		orderLineMain = OrderLine.getInstance(order, article, article
				.getNumberOfItems(), article.getDialogOrder(), deviation,
				article.getOrdNo(), article.getLnNo());

		if (!Hibernate.isInitialized(article.getAttributes())) {
			constructionTypeManager
					.lazyLoadArticle(
							article,
							new LazyLoadConstructionTypeArticleEnum[] { LazyLoadConstructionTypeArticleEnum.ATTRIBUTES });
		}

		createAndAddAttributes(article, orderLineMain);
		setOrderLineConstructionRefs(article, orderLineMain, order, deviation);
		if (orderLines != null) {
			orderLines.add(orderLineMain);
		}
		orderLineMain.setArticlePath(orderLineMain.getGeneratedArticlePath());
	}

	private void setOrderLineConstructionRefs(
			ConstructionTypeArticle constructionArticle,
			OrderLine orderLineMain, Order order, Deviation deviation) {
		ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
				.getBean("constructionTypeManager");
		constructionTypeManager
				.lazyLoadArticle(
						constructionArticle,
						new LazyLoadConstructionTypeArticleEnum[] { LazyLoadConstructionTypeArticleEnum.CONSTRUCTION_TYPE_ARTICLES });
		Set<ConstructionTypeArticle> articleRefs = constructionArticle
				.getConstructionTypeArticles();
		OrderLine orderLine;
		Set<OrderLineAttribute> orderLineAttributes;
		Set<OrderLine> orderLineRefs = new LinkedHashSet<OrderLine>();
		if (articleRefs != null) {
			for (ConstructionTypeArticle articleRef : articleRefs) {
				orderLine = OrderLine.getInstance(order, articleRef,
						orderLineMain, articleRef.getNumberOfItems(),
						articleRef.getDialogOrder(), deviation);

				constructionTypeManager
						.lazyLoadArticle(
								articleRef,
								new LazyLoadConstructionTypeArticleEnum[] { LazyLoadConstructionTypeArticleEnum.ATTRIBUTES });
				Set<ConstructionTypeArticleAttribute> attributes = articleRef
						.getAttributes();

				if (attributes != null) {
					orderLineAttributes = new LinkedHashSet<OrderLineAttribute>();

					for (ConstructionTypeArticleAttribute attribute : attributes) {
						orderLineAttributes.add(new OrderLineAttribute(null,
								orderLine, attribute, null, null, attribute
										.getAttributeValue(), attribute
										.getDialogOrder(), attribute
										.getAttributeName()));
					}

					orderLine.setOrderLineAttributes(orderLineAttributes);

					setOrderLineConstructionRefs(articleRef, orderLine, order,
							deviation);
				}
				orderLineRefs.add(orderLine);
				orderLine.setArticlePath(orderLine.getGeneratedArticlePath());
			}
			orderLineMain.setOrderLines(orderLineRefs);
		}
	}

	private void createAndAddAttributes(final ConstructionTypeArticle article,
			final OrderLine orderLineMain) {
		Set<OrderLineAttribute> orderLineAttributes;
		Set<ConstructionTypeArticleAttribute> articleAttributes;
		articleAttributes = article.getAttributes();

		if (articleAttributes != null && articleAttributes.size() != 0) {
			orderLineAttributes = new LinkedHashSet<OrderLineAttribute>();
			for (ConstructionTypeArticleAttribute articleAttribute : articleAttributes) {
				createAndAddAttribute(orderLineAttributes, orderLineMain,
						articleAttribute);
			}
			orderLineMain.setOrderLineAttributes(orderLineAttributes);
			orderLineMain.setArticlePath(orderLineMain
					.getGeneratedArticlePath());
		}
	}

	private void createAndAddAttribute(
			Set<OrderLineAttribute> orderLineAttributes,
			OrderLine orderLineMain,
			ConstructionTypeArticleAttribute articleAttribute) {
		OrderLineAttribute orderLineAttribute = new OrderLineAttribute();
		orderLineAttribute.setOrderLine(orderLineMain);
		orderLineAttribute
				.setConstructionTypeArticleAttribute(articleAttribute
						.getConstructionTypeArticleAttributeId() != null ? articleAttribute
						: null);
		orderLineAttribute
				.setArticleTypeAttribute(articleAttribute
						.getConstructionTypeArticleAttributeId() == null ? articleAttribute
						.getArticleTypeAttribute()
						: null);
		orderLineAttribute.setAttributeValue(articleAttribute
				.getAttributeValue());
		orderLineAttribute.setDialogOrder(articleAttribute.getDialogOrder());
		orderLineAttribute.setOrderLineAttributeName(articleAttribute
				.getAttributeName());
		
		setDefault(orderLineAttribute,orderLineMain);

		orderLineAttributes.add(orderLineAttribute);
	}

	private void setDefault(OrderLineAttribute orderLineAttribute, OrderLine orderLine) {
		if("Egenordre".equalsIgnoreCase(orderLineAttribute.getOrderLineAttributeName())&&!"Nei".equalsIgnoreCase(orderLineAttribute.getOrderLineAttributeValue())){
			orderLine.setIsDefault(0);
		}
		
	}

	private List<OrderLine> extractVismaOrderLines(
			Collection<OrderLine> orderLines) {
		List<OrderLine> vismaOrderLines = null;
		if (orderLines != null) {
			vismaOrderLines = new ArrayList<OrderLine>();
			for (OrderLine orderLine : orderLines) {
				if (orderLine.getOrdNo() != null) {
					vismaOrderLines.add(orderLine);
				}
			}
		}
		return vismaOrderLines;
	}

	private Set<OrderLine> setOrderLineAndAttributes(
			Set<ConstructionTypeAttribute> attributes, Order orderLineOrder,
			Deviation deviation) {
		Set<OrderLine> orderLines = new LinkedHashSet<OrderLine>();
		Set<OrderLineAttribute> orderLineAttributes;
		OrderLine orderLineMain;
		orderLineMain = OrderLine.getInstance(orderLineOrder, 0, deviation);
		orderLineAttributes = new LinkedHashSet<OrderLineAttribute>();

		for (ConstructionTypeAttribute attribute : attributes) {
			orderLineAttributes.add(new OrderLineAttribute(null, orderLineMain,
					null, attribute, null, attribute.getAttributeValue(),
					attribute.getDialogOrder(), attribute.getAttributeName()));
		}
		orderLineMain.setOrderLineAttributes(orderLineAttributes);
		orderLines.add(orderLineMain);
		orderLineMain.setArticlePath(orderLineMain.getGeneratedArticlePath());
		return orderLines;
	}

}
