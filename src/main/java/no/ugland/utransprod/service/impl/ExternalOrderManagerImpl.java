package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.ExternalOrderDAO;
import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.model.ExternalOrderLine;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.ExternalOrderLineManager;
import no.ugland.utransprod.service.ExternalOrderManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;

/**
 * Implementasjon av serviceklasse for tabell EXTERNAL_ORDER.
 * @author atle.brekka
 */
public class ExternalOrderManagerImpl extends ManagerImpl<ExternalOrder>implements ExternalOrderManager {
    private ExternalOrderLineManager externalOrderLineManager;

    /**
     * @param aExternalOrderLineManager
     */
    public final void setExternalOrderLineManager(
            final ExternalOrderLineManager aExternalOrderLineManager) {
        this.externalOrderLineManager = aExternalOrderLineManager;
    }

    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    public final List<ExternalOrder> findAll() {
        return dao.getObjects("deliveryDate");
    }

    /**
     * @param externalOrder
     * @return eksterne ordre
     */
    public final List<ExternalOrder> findByExternalOrder(final ExternalOrder externalOrder) {
        return dao.findByExampleLike(externalOrder);
    }

    /**
     * @param object
     * @return eksterne ordre
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<ExternalOrder> findByObject(final ExternalOrder object) {
        return findByExternalOrder(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final ExternalOrder object) {
        ((ExternalOrderDAO)dao).refreshObject(object);

    }

    /**
     * @param externalOrder
     */
    public final void removeExternalOrder(final ExternalOrder externalOrder) {
        dao.removeObject(externalOrder.getExternalOrderId());

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final ExternalOrder object) {
        removeExternalOrder(object);

    }

    /**
     * @param externalOrder
     */
    public final void saveExternalOrder(final ExternalOrder externalOrder) {
        dao.saveObject(externalOrder);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final ExternalOrder object) {
        saveExternalOrder(object);

    }

    /**
     * @see no.ugland.utransprod.service.ExternalOrderManager#findByOrder(no.ugland.utransprod.model.Order)
     */
    public final List<ExternalOrder> findByOrder(final Order order) {
        return ((ExternalOrderDAO)dao).findByOrder(order);
    }

    /**
     * @see no.ugland.utransprod.service.ExternalOrderManager#
     * lazyLoad(no.ugland.utransprod.model.ExternalOrder,
     *      no.ugland.utransprod.service.enums.LazyLoadExternalOrderEnum[])
     */
    /*public final void lazyLoad(final ExternalOrder externalOrder,
            final LazyLoadExternalOrderEnum[] enums) {
        dao.lazyLoad(externalOrder, enums);

    }*/

    /**
     * @see no.ugland.utransprod.service.ExternalOrderManager#
     * lazyLoadExternalOrderLine(no.ugland.utransprod.model.ExternalOrderLine,
     *      no.ugland.utransprod.service.enums.LazyLoadExternalOrderLineEnum[])
     */
    public final void lazyLoadExternalOrderLine(final ExternalOrderLine externalOrderLine,
            final LazyLoadEnum[][] enums) {
        externalOrderLineManager.lazyLoad(externalOrderLine, enums);

    }

    
    @Override
    protected Serializable getObjectId(ExternalOrder object) {
        return object.getExternalOrderId();
    }

}
