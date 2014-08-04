package no.ugland.utransprod.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.dao.TakstolPackageVDAO;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TakstolPackageV;
import no.ugland.utransprod.service.TakstolPackageVManager;

/**
 * Implementasjon av serviceklasse for view TAKSTOL_PACKAGE_V.
 * 
 * @author atle.brekka
 */
public class TakstolPackageVManagerImpl extends AbstractApplyListManager<PackableListItem> implements TakstolPackageVManager {
    private TakstolPackageVDAO dao;
    private static final String MAIN_ARTICLE_NAME = "Takstoler";

    /**
     * Setter dao for kunder.
     * 
     * @param aDao
     */
    public final void setTakstolPackageVDAO(final TakstolPackageVDAO aDao) {
	this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findAllApplyable()
     */
    public final List<PackableListItem> findAllApplyable() {
	List<PackableListItem> items = dao.findAll();
	return setRelated(items);
    }

    private List<PackableListItem> setRelated(List<PackableListItem> items) {
	List<PackableListItem> packable = new ArrayList<PackableListItem>();
	if (items != null && items.size() != 0) {
	    Map<String, TakstolPackageV> defaultTakstolMap = new HashMap<String, TakstolPackageV>();
	    PackableListItem currentTakstol = null;// new TakstolProductionV();
	    List<Applyable> relatedArticles = null;

	    for (PackableListItem item : items) {
		if (currentTakstol == null) {// dersom første ordrelinje
		    currentTakstol = item;
		}
		if (!item.getOrderNr().equalsIgnoreCase(currentTakstol.getOrderNr())) {// dersom
		    // ny
		    // ordre
		    handleCurrentTakstol(defaultTakstolMap, currentTakstol, relatedArticles, packable);
		    relatedArticles = null;// nullstiller relaterte artikler

		    currentTakstol = item;// setter ny current
		} else {
		    if (!currentTakstol.equals(item)) {// kan ikke sett relatert
						       // til
			// seg selv
			if (canHaveRelatedArticles(currentTakstol)) {// gjeldende
			    // takstol
			    // må kunne
			    // ha
			    // relaterte
			    relatedArticles = relatedArticles == null ? new ArrayList<Applyable>() : relatedArticles;
			    relatedArticles.add(item);
			} else {// dersom gjeldende takstol ikke kan ha
				// relaterte
			    // legges den under default takstol
			    addArticleToDefaultTakstol(item, defaultTakstolMap);
			}
		    }
		}
	    }
	    handleCurrentTakstol(defaultTakstolMap, currentTakstol, relatedArticles, packable);

	    packable.addAll(defaultTakstolMap.values());
	    Collections.sort(packable, new PackableListItemComparator());
	}
	return packable;
    }

    private void handleCurrentTakstol(Map<String, TakstolPackageV> defaultTakstolMap, PackableListItem currentTakstol,
	    List<Applyable> relatedArticles, List<PackableListItem> producables) {
	if (canHaveRelatedArticles(currentTakstol)) {
	    currentTakstol.setRelatedArticles(relatedArticles);// setter
	    // relaterte
	    // ordrelinjer
	    boolean success = currentTakstol.getOrderNr() != null ? producables.add(currentTakstol) : false;// dersom
													    // takstol
													    // har
													    // ordrenummer
	} else {
	    addArticleToDefaultTakstol(currentTakstol, defaultTakstolMap);
	}
    }

    /*
     * private List<PackableListItem> setRelated(List<PackableListItem> items) {
     * Map<String, TakstolPackageV> defaultTakstolMap = new HashMap<String,
     * TakstolPackageV>(); PackableListItem currentTakstol=new
     * TakstolPackageV(); List<Applyable> relatedArticles=null;
     * List<PackableListItem> producables=new ArrayList<PackableListItem>(); for
     * (PackableListItem item : items) { if(canHaveRelatedArticles(item)){
     * currentTakstol.setRelatedArticles(relatedArticles); relatedArticles=null;
     * boolean
     * success=currentTakstol.getOrderNr()!=null?producables.add(currentTakstol
     * ):false; currentTakstol=item; }else{
     * if(item.getOrderNr().equalsIgnoreCase(currentTakstol.getOrderNr())){
     * relatedArticles=relatedArticles==null?new
     * ArrayList<Applyable>():relatedArticles; relatedArticles.add(item); }else
     * if(isTakstol(item)){ producables.add(item); }else{
     * 
     * addArticleToDefaultTakstol(item,defaultTakstolMap); } }
     * 
     * 
     * 
     * } boolean
     * success=currentTakstol.getOrderNr()!=null?producables.add(currentTakstol
     * ):false; producables.addAll(defaultTakstolMap.values());
     * Collections.sort(producables, new PackableListItemComparator()); return
     * producables; }
     */

    private boolean isTakstol(PackableListItem item) {
	return item.getArticleName().equalsIgnoreCase(MAIN_ARTICLE_NAME);
    }

    private Map<String, TakstolPackageV> addArticleToDefaultTakstol(PackableListItem item, Map<String, TakstolPackageV> defaultTakstolMap) {
	TakstolPackageV defaultTakstol = defaultTakstolMap.get(item.getOrderNr());
	defaultTakstol = defaultTakstol == null ? createDefaultTakstol(item) : defaultTakstol;
	defaultTakstol.addRelatedArticle(item);
	addRelatedAttributeInfo(defaultTakstol, item);
	defaultTakstolMap.put(item.getOrderNr(), defaultTakstol);
	return defaultTakstolMap;
    }

    private void addRelatedAttributeInfo(TakstolPackageV defaultTakstol, PackableListItem item) {
	StringBuilder stringBuilder = new StringBuilder(defaultTakstol.getAttributeInfo() != null ? defaultTakstol.getAttributeInfo() : "");
	stringBuilder = stringBuilder.length() != 0 ? stringBuilder.append(",") : stringBuilder;
	stringBuilder.append(item.getArticleName()).append("-").append(item.getAttributeInfo());
	defaultTakstol.setAttributeInfo(stringBuilder.toString());

    }

    private TakstolPackageV createDefaultTakstol(PackableListItem item) {
	TakstolPackageV defaultTakstol = ((TakstolPackageV) item).clone();
	defaultTakstol.setArticleName(MAIN_ARTICLE_NAME);
	return defaultTakstol;
    }

    private boolean haveNotRelatedTakstol(PackableListItem item) {
	return !item.getArticleName().equalsIgnoreCase(MAIN_ARTICLE_NAME) && !haveRelatedTakstol(item) ? true : false;
    }

    private boolean haveRelatedTakstol(PackableListItem item) {
	List<PackableListItem> list = dao.findApplyableByOrderNrAndArticleName(item.getOrderNr(), MAIN_ARTICLE_NAME);
	return list != null && list.size() != 0;
    }

    private void setRelatedArticles(List<PackableListItem> items, PackableListItem item) {
	List<Applyable> relatedArticles = checkAndGetRelatedArticles(item, items);
	item.setRelatedArticles(relatedArticles);
    }

    private List<Applyable> checkAndGetRelatedArticles(PackableListItem item, List<PackableListItem> items) {

	return item.getNumberOfItems() != null && item.getNumberOfItems() > 3 ? getRelatedArticles(item, items) : new ArrayList<Applyable>();
    }

    private boolean canHaveRelatedArticles(PackableListItem item) {
	return item.getArticleName().equalsIgnoreCase(MAIN_ARTICLE_NAME) && item.getNumberOfItems() != null && item.getNumberOfItems() > 3 ? true
		: false;
    }

    private List<Applyable> getRelatedArticles(final PackableListItem packable, final List<PackableListItem> items) {
	List<Applyable> relatedArticles = new ArrayList<Applyable>();

	for (PackableListItem item : items) {
	    if (!item.getArticleName().equalsIgnoreCase(MAIN_ARTICLE_NAME) && item.getOrderNr().equalsIgnoreCase(packable.getOrderNr())
		    && item.getOrderLineId() != packable.getOrderLineId()) {
		relatedArticles.add(item);
	    }
	}
	return relatedArticles;
    }

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByOrderNr(java.lang.String)
     */
    public final List<PackableListItem> findApplyableByOrderNr(final String orderNr) {
	List<PackableListItem> items = dao.findByOrderNr(orderNr);
	return setRelated(items);

    }

    /**
     * @see no.ugland.utransprod.service.TakstolPackageVManager#
     *      refresh(no.ugland.utransprod.model.TakstolPackageV)
     */
    public final void refresh(final TakstolPackageV productionV) {
	dao.refresh(productionV);

    }

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByCustomerNr(java.lang.Integer)
     */
    public final List<PackableListItem> findApplyableByCustomerNr(final Integer customerNr) {
	List<PackableListItem> items = dao.findByCustomerNr(customerNr);
	return setRelated(items);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.IApplyListManager#refresh(java.lang.Object)
     */
    public final void refresh(final PackableListItem object) {
	refresh((TakstolPackageV) object);

    }

    public TakstolPackageV findByOrderNrAndOrderLineId(String orderNr, Integer orderLineId) {
	List<PackableListItem> list = dao.findByOrderNr(orderNr);
	TakstolPackageV takstolPackageV = findByOrderLineId(orderLineId, list);
	if (takstolPackageV != null) {
	    takstolPackageV
		    .setRelatedArticles(takstolPackageV.getNumberOfItems() != null && takstolPackageV.getNumberOfItems() > 3 ? getRelatedArticles(
			    takstolPackageV, list) : new ArrayList<Applyable>());
	}
	return takstolPackageV;
    }

    private TakstolPackageV findByOrderLineId(Integer orderLineId, List<PackableListItem> list) {
	for (PackableListItem item : list) {
	    if (item.getOrderLineId().equals(orderLineId)) {
		return (TakstolPackageV) item;
	    }
	}
	return null;
    }

    public List<PackableListItem> findApplyableByOrderNrAndArticleName(String orderNr, String mainArticleName) {
	List<PackableListItem> items = dao.findApplyableByOrderNrAndArticleName(orderNr, mainArticleName);
	return setRelated(items);
    }

    public List<PackableListItem> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
	List<PackableListItem> items = dao.findByCustomerNrAndProductAreaGroup(customerNr, productAreaGroup);
	return setRelated(items);
    }

    public List<PackableListItem> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
	List<PackableListItem> items = dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
	return setRelated(items);
    }

}
