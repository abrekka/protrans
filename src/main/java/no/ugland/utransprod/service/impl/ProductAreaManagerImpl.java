package no.ugland.utransprod.service.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.dao.ProductAreaDAO;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.ProductAreaManager;

/**
 * Implementasjon av serviceklasse for tabell PRODUCT_AREA.
 * @author atle.brekka
 */
public class ProductAreaManagerImpl implements ProductAreaManager {
    private ProductAreaDAO dao;
    // private Map<ProductArea, List<Integer>> productAreaGroupIdxList;
    private Map<Integer, ProductArea> productAreaNrProductArea;
    private Map<String, ProductArea> productAreaNameProductArea;

    /**
     * @param aDao
     */
    public final void setProductAreaDAO(final ProductAreaDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.ProductAreaManager#findAll()
     */
    public final List<ProductArea> findAll() {
        return dao.getObjects("sortNr");
    }

    /**
     * @see no.ugland.utransprod.service.ProductAreaManager#findByName(java.lang.String)
     */
    public final ProductArea findByName(final String name) {
        ProductArea productArea = new ProductArea();
        productArea.setProductArea(name);
        List<ProductArea> list = dao.findByExample(productArea);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @see no.ugland.utransprod.service.ProductAreaManager#
     *      removeProductArea(no.ugland.utransprod.model.ProductArea)
     */
    public final void removeProductArea(final ProductArea productArea) {
        dao.removeObject(productArea.getProductAreaId());

    }

    /**
     * @see no.ugland.utransprod.service.ProductAreaManager#
     *      saveProductArea(no.ugland.utransprod.model.ProductArea)
     */
    public final void saveProductArea(final ProductArea productArea) {
        dao.saveObject(productArea);

    }

    public ProductArea getProductAreaForProductAreaNr(Integer productAreaNr,boolean initCache) {
        return initCache?getAndInitCache(productAreaNr):findProductAreaByProductAreaNr(productAreaNr);
    }

	private ProductArea findProductAreaByProductAreaNr(Integer productAreaNr) {
		return dao.findByProductAreaNr(productAreaNr);
	}

	private ProductArea getAndInitCache(Integer productAreaNr) {
		initProductAreaNr();
        return productAreaNr!=null?productAreaNrProductArea.get(productAreaNr):null;
	}

    private void initProductAreaNr() {
        if (productAreaNrProductArea == null) {
            productAreaNrProductArea = new Hashtable<Integer, ProductArea>();
            productAreaNameProductArea = new Hashtable<String, ProductArea>();

            List<ProductArea> productAreas = findAll();

            if (productAreas != null) {
                initForAllProductAreas(productAreas);
            }
        }
    }

    private void initForAllProductAreas(List<ProductArea> productAreas) {
        for (ProductArea productArea : productAreas) {
            List<Integer> productAreaNrList = productArea.getProductAreaNrList();
            if (productAreaNrList != null) {

                productAreaNameProductArea.put(productArea.getProductArea(), productArea);
                for (Integer productAreaNr : productAreaNrList) {
                    productAreaNrProductArea.put(productAreaNr, productArea);
                }
            }
        }
    }

    public List<Integer> getProductAreaNrListFromAreaName(String productAreaName) {
        initProductAreaNr();
        ProductArea productArea = productAreaNameProductArea.get(productAreaName);
        if (productArea != null) {
            return productArea.getProductAreaNrList();
        }
        return null;
    }

	public List<String> getAllNames() {
		return dao.findAllNames();
	}

}
