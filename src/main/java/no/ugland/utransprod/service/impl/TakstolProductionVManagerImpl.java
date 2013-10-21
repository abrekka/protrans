package no.ugland.utransprod.service.impl;

import static com.google.common.collect.Iterables.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.dao.TakstolProductionVDAO;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TakstolProductionV;
import no.ugland.utransprod.service.TakstolProductionVManager;

import com.google.common.base.Function;
import com.google.inject.internal.Lists;

/**
 * Implementasjon av serviceklasse for view TAKSTIL_PRODUCTION_V.
 * 
 * @author atle.brekka
 */
public class TakstolProductionVManagerImpl extends
		AbstractApplyListManager<Produceable> implements
		TakstolProductionVManager {
	private static final String MAIN_ARTICLE_NAME = "Takstoler";

	private TakstolProductionVDAO dao;

	/**
	 * @param aDao
	 */
	public final void setTakstolProductionVDAO(final TakstolProductionVDAO aDao) {
		this.dao = aDao;
	}

	/**
	 * @see no.ugland.utransprod.service.IApplyListManager#findAllApplyable()
	 */
	public final List<Produceable> findAllApplyable() {
		List<Produceable> items = dao.findAll();
		return setRelated(items);

	}

	public final List<Produceable> findApplyableByOrderNr(final String orderNr) {
		List<Produceable> items = dao.findByOrderNr(orderNr);
		return setRelated(items);

	}

	public final List<Produceable> findApplyableByOrderNrAndArticleName(
			final String orderNr, final String articleName) {
		List<Produceable> items = dao.findByOrderNrAndArticleName(orderNr,
				articleName);
		if(items.isEmpty()){
			return Lists.newArrayList();
		}
		return setRelated(items);

	}

	/**
	 * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByCustomerNr(java.lang.Integer)
	 */
	public final List<Produceable> findApplyableByCustomerNr(
			final Integer customerNr) {
		List<Produceable> items = dao.findByCustomerNr(customerNr);
		return setRelated(items);

	}

	/**
	 * @param productionV
	 * @see no.ugland.utransprod.service.IApplyListManager#refresh(java.lang.Object)
	 */
	public final void refresh(final Produceable productionV) {
		dao.refresh(productionV);

	}

	private List<Produceable> setRelated(List<Produceable> items) {
		Map<String, TakstolProductionV> defaultTakstolMap = new HashMap<String, TakstolProductionV>();
		Produceable currentTakstol = null;
		List<Applyable> relatedArticles = null;
		List<Produceable> producables = new ArrayList<Produceable>();

		for (Produceable item : items) {
			if (currentTakstol == null) {// dersom første ordrelinje
				currentTakstol = item;
			}
			if (!item.getOrderNr()
					.equalsIgnoreCase(currentTakstol.getOrderNr())) {// dersom
				// ny
				// ordre
				handleCurrentTakstol(defaultTakstolMap, currentTakstol,
						relatedArticles, producables);
				relatedArticles = null;// nullstiller relaterte artikler

				currentTakstol = item;// setter ny current
			} else {
				if (!currentTakstol.equals(item)) {// kan ikke sett relatert til
					// seg selv
					if (canHaveRelatedArticles(currentTakstol)) {// gjeldende
						// takstol
						// må kunne
						// ha
						// relaterte
						relatedArticles = relatedArticles == null ? new ArrayList<Applyable>()
								: relatedArticles;
						relatedArticles.add(item);
					} else {// dersom gjeldende takstol ikke kan ha relaterte
						// legges den under default takstol
						addArticleToDefaultTakstol(item, defaultTakstolMap);
					}
				}
			}
		}
		handleCurrentTakstol(defaultTakstolMap, currentTakstol,
				relatedArticles, producables);

		producables.addAll(defaultTakstolMap.values());
		Collections.sort(producables, new ProduceableComparator());
		return producables;
	}

	private void handleCurrentTakstol(
			Map<String, TakstolProductionV> defaultTakstolMap,
			Produceable currentTakstol, List<Applyable> relatedArticles,
			List<Produceable> producables) {
		if (canHaveRelatedArticles(currentTakstol)) {
			currentTakstol.setRelatedArticles(relatedArticles);// setter
			// relaterte
			// ordrelinjer
			@SuppressWarnings("unused")
			boolean success = currentTakstol.getOrderNr() != null ? producables
					.add(currentTakstol) : false;// dersom takstol har
			// ordrenummer
		} else {
			addArticleToDefaultTakstol(currentTakstol, defaultTakstolMap);
		}
	}

	private Map<String, TakstolProductionV> addArticleToDefaultTakstol(
			Produceable item, Map<String, TakstolProductionV> defaultTakstolMap) {
		TakstolProductionV defaultTakstol = defaultTakstolMap.get(item
				.getOrderNr());
		defaultTakstol = defaultTakstol == null ? createDefaultTakstol(item)
				: defaultTakstol;
		defaultTakstol.addRelatedArticle(item);
		addRelatedAttributeInfo(defaultTakstol, item);
		defaultTakstolMap.put(item.getOrderNr(), defaultTakstol);
		return defaultTakstolMap;
	}

	private void addRelatedAttributeInfo(TakstolProductionV defaultTakstol,
			Produceable item) {
		StringBuilder stringBuilder = new StringBuilder(defaultTakstol
				.getAttributeInfo() != null ? defaultTakstol.getAttributeInfo()
				: "");
		stringBuilder = stringBuilder.length() != 0 ? stringBuilder.append(",")
				: stringBuilder;
		stringBuilder.append(item.getArticleName()).append(":").append(
				item.getAttributeInfo());
		defaultTakstol.setAttributeInfo(stringBuilder.toString());

	}

	private TakstolProductionV createDefaultTakstol(Produceable item) {
		TakstolProductionV defaultTakstol = ((TakstolProductionV) item).clone();
		defaultTakstol.setArticleName(MAIN_ARTICLE_NAME);
		return defaultTakstol;
	}

	private boolean canHaveRelatedArticles(Produceable item) {
		if (item != null) {
			return item.getArticleName() != null ? item.getArticleName()
					.equalsIgnoreCase(MAIN_ARTICLE_NAME) : false;
		}
		return false;
	}

	private List<Applyable> getRelatedArticles(final Produceable produceable,
			final List<Produceable> items) {
		List<Applyable> relatedArticles = new ArrayList<Applyable>();

		for (Produceable item : items) {
			if (!item.getArticleName().equalsIgnoreCase(MAIN_ARTICLE_NAME)
					&& item.getOrderNr().equalsIgnoreCase(
							produceable.getOrderNr())
					&& item.getOrderLineId() != produceable.getOrderLineId()) {
				relatedArticles.add(item);
			}
		}
		return relatedArticles;
	}

	public TakstolProductionV findByOrderNrAndOrderLineId(String orderNr,
			Integer orderLineId) {
		List<Produceable> list = dao.findByOrderNr(orderNr);
		TakstolProductionV takstolProductionV = findByOrderLineId(orderLineId,
				list);
		if (takstolProductionV != null) {
			takstolProductionV
					.setRelatedArticles(takstolProductionV.getNumberOfItems() != null
							&& takstolProductionV.getNumberOfItems() > 3 ? getRelatedArticles(
							takstolProductionV, list)
							: new ArrayList<Applyable>());
		}
		return takstolProductionV;
	}

	private TakstolProductionV findByOrderLineId(Integer orderLineId,
			List<Produceable> list) {
		for (Produceable prod : list) {
			if (prod.getOrderLineId().equals(orderLineId)) {
				return (TakstolProductionV) prod;
			}
		}
		return null;
	}

	public List<Produceable> findApplyableByCustomerNrAndProductAreaGroup(
			Integer customerNr, ProductAreaGroup productAreaGroup) {
		List<Produceable> items = dao.findByCustomerNrAndProductAreaGroup(
				customerNr, productAreaGroup);
		if (items != null && items.size() != 0) {
			return setRelated(items);
		}
		return items;
	}

	public List<Produceable> findApplyableByOrderNrAndProductAreaGroup(
			String orderNr, ProductAreaGroup productAreaGroup) {
		List<Produceable> items = dao.findByOrderNrAndProductAreaGroup(orderNr,
				productAreaGroup);
		if (items != null && items.size() != 0) {
			return setRelated(items);
		}
		return items;

	}

	public List<TakstolProductionV> findByOrderNr(String orderNr) {
		return Lists.newArrayList(transform(dao.findByOrderNr(orderNr),
				prosucable2TakstolProductionV()));
	}

	private Function<Produceable, TakstolProductionV> prosucable2TakstolProductionV() {
		return new Function<Produceable, TakstolProductionV>() {

			public TakstolProductionV apply(Produceable produceable) {
				return (TakstolProductionV) produceable;
			}
		};
	}

}
