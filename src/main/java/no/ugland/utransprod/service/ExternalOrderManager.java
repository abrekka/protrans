package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.model.ExternalOrderLine;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.enums.LazyLoadEnum;

/**
 * Interface for serviceklasse mot tabell EXTERNAL_ORDER
 * @author atle.brekka
 */
public interface ExternalOrderManager extends OverviewManager<ExternalOrder> {
    String MANAGER_NAME = "externalOrderManager";

	/**
     * Finner eksterne order for gitt ordre
     * @param order
     * @return eksterne ordre
     */
    List<ExternalOrder> findByOrder(Order order);

    /**
     * LAzy laster ekstern ordre
     * @param externalOrder
     * @param enums
     */
    //void lazyLoad(ExternalOrder externalOrder, LazyLoadExternalOrderEnum[] enums);

    /**
     * Lazy laster ekstern ordrelinje
     * @param externalOrderLine
     * @param enums
     */
    void lazyLoadExternalOrderLine(ExternalOrderLine externalOrderLine,
            LazyLoadEnum[][] enums);
}
