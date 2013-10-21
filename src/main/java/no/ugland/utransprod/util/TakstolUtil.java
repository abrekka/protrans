package no.ugland.utransprod.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TakstolUtil {
	private static final String MAIN_ARTICLE_NAME = "Takstoler";

	public static List<TakstolInterface> setRelated(
			List<TakstolInterface> items, Comparator comparator,
			boolean addRelatedAttributeInfo) {
		Map<String, TakstolInterface> defaultTakstolMap = new HashMap<String, TakstolInterface>();
		TakstolInterface currentTakstol = null;// new TakstolProductionV();
		List<TakstolInterface> relatedArticles = null;
		List<TakstolInterface> takstoler = new ArrayList<TakstolInterface>();

		for (TakstolInterface item : items) {
			if (currentTakstol == null) {// dersom første ordrelinje
				currentTakstol = item;
			}
			if (!item.getOrderNr()
					.equalsIgnoreCase(currentTakstol.getOrderNr())) {
				handleCurrentTakstol(defaultTakstolMap, currentTakstol,
						relatedArticles, takstoler, addRelatedAttributeInfo);
				relatedArticles = null;

				currentTakstol = item;// setter ny current
			} else {
				if (!currentTakstol.equals(item)) {// kan ikke sett relatert til
					// seg selv
					if (canHaveRelatedArticles(currentTakstol)) {
						relatedArticles = relatedArticles == null ? new ArrayList<TakstolInterface>()
								: relatedArticles;
						relatedArticles.add(item);
					} else {
						addArticleToDefaultTakstol(item, defaultTakstolMap,
								addRelatedAttributeInfo);
					}
				}
			}
		}
		if(currentTakstol!=null){
		handleCurrentTakstol(defaultTakstolMap, currentTakstol,
				relatedArticles, takstoler, addRelatedAttributeInfo);
		}

		takstoler.addAll(defaultTakstolMap.values());
		if (comparator != null) {
			Collections.sort(takstoler, comparator);
		}
		return takstoler;
	}

	private static void handleCurrentTakstol(
			Map<String, TakstolInterface> defaultTakstolMap,
			TakstolInterface currentTakstol,
			List<TakstolInterface> relatedArticles,
			List<TakstolInterface> takstoler, boolean addRelatedAttributeInfo) {
		if (canHaveRelatedArticles(currentTakstol)) {
			currentTakstol.setRelatedArticles(relatedArticles);// setter
			// relaterte
			// ordrelinjer
			boolean success = currentTakstol.getOrderNr() != null ? takstoler
					.add(currentTakstol) : false;// dersom takstol har
			// ordrenummer
		} else {
			addArticleToDefaultTakstol(currentTakstol, defaultTakstolMap,
					addRelatedAttributeInfo);
		}
	}

	private static Map<String, TakstolInterface> addArticleToDefaultTakstol(
			TakstolInterface item,
			Map<String, TakstolInterface> defaultTakstolMap,
			boolean addRelatedAttributeInfo) {
		TakstolInterface defaultTakstol = defaultTakstolMap.get(item
				.getOrderNr());
		defaultTakstol = defaultTakstol == null ? createDefaultTakstol(item)
				: defaultTakstol;
		defaultTakstol.addRelatedArticle(item);
		if (addRelatedAttributeInfo) {
			addRelatedAttributeInfo(defaultTakstol, item);
		}
		defaultTakstolMap.put(item.getOrderNr(), defaultTakstol);
		return defaultTakstolMap;
	}

	private static TakstolInterface createDefaultTakstol(TakstolInterface item) {
		TakstolInterface defaultTakstol = ((TakstolInterface) item).clone();
		defaultTakstol.setArticleName(MAIN_ARTICLE_NAME);
		return defaultTakstol;
	}

	private static void addRelatedAttributeInfo(
			TakstolInterface defaultTakstol, TakstolInterface item) {
		StringBuilder stringBuilder = new StringBuilder(defaultTakstol
				.getAttributeInfo() != null ? defaultTakstol.getAttributeInfo()
				: "");
		stringBuilder = stringBuilder.length() != 0 ? stringBuilder.append(",")
				: stringBuilder;
		stringBuilder.append(item.getArticleName()).append(":").append(
				item.getAttributeInfo());
		defaultTakstol.setAttributeInfo(stringBuilder.toString());

	}

	private static boolean canHaveRelatedArticles(TakstolInterface item) {
		return item.getArticleName()!=null?item.getArticleName().equalsIgnoreCase(MAIN_ARTICLE_NAME):false;
	}
}
