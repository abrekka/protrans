package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.OrderCostDAO;
import no.ugland.utransprod.dao.SupplierDAO;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.service.SupplierManager;

/**
 * Implementasjon av serviceklasse for tabell SUPPLIER.
 * @author atle.brekka
 */
public class SupplierManagerImpl extends ManagerImpl<Supplier> implements SupplierManager {
    private OrderCostDAO orderCostDAO;

    public final void setOrderCostDAO(final OrderCostDAO aDao) {
        this.orderCostDAO = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.SupplierManager#findAll()
     */
    public final List<Supplier> findAll() {
        return dao.getObjects("supplierName");
    }

    /**
     * Finner basert på eksempel.
     * @param supplier
     * @return leverandører
     */
    public final List<Supplier> findBySupplier(final Supplier supplier) {
        return dao.findByExampleLike(supplier);
    }

    /**
     * @param object
     * @return leverandører
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<Supplier> findByObject(final Supplier object) {
        return findBySupplier(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final Supplier object) {
        ((SupplierDAO)dao).refreshObject(object);

    }

    /**
     * Fjerner leverandør.
     * @param supplier
     */
    public final void removeSupplier(final Supplier supplier) {
        dao.removeObject(supplier.getSupplierId());

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final Supplier object) {
        removeSupplier(object);

    }

    /**
     * Lagrer leverandør.
     * @param supplier
     */
    public final void saveSupplier(final Supplier supplier) {
        dao.saveObject(supplier);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final Supplier object) {
        saveSupplier(object);

    }

    /**
     * @see no.ugland.utransprod.service.SupplierManager#findByTypeString(java.lang.String,
     *      java.lang.String)
     */
    public final List<Supplier> findByTypeName(final String typeString, final String orderBy) {
        return ((SupplierDAO)dao).findByTypeName(typeString, orderBy);
    }

    

    public final Supplier findByName(final String name) {
        return ((SupplierDAO)dao).findByName(name);
    }

    public List<Supplier> findActiveByTypeName(String typeString, String orderBy,ProductAreaGroup productAreaGroup) {
        return ((SupplierDAO)dao).findActiveByTypeName(typeString, orderBy,productAreaGroup);
    }


    public List<Supplier> findHavingAssembly(Integer year, Integer fromWeek, Integer toWeek,ProductAreaGroup productAreaGroup) {
        return ((SupplierDAO)dao).findHavingAssembly(year, fromWeek, toWeek,productAreaGroup);
    }

    public Boolean hasOrderCosts(Supplier supplier) {
        List<OrderCost> orderCosts = orderCostDAO.findBySupplier(supplier);
        if(orderCosts!=null&&orderCosts.size()!=0){
            return true;
        }
        return false;
    }
   

    @Override
    protected Serializable getObjectId(Supplier object) {
        return object.getSupplierId();
    }

}
