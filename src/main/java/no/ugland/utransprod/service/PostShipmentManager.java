package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.Periode;

/**
 * Interface for serviceklasse mot tabell POST_SHIPMENT
 * @author atle.brekka
 */
public interface PostShipmentManager extends OverviewManager<PostShipment> {
    public static final String MANAGER_NAME="postShipmentManager";
    /**
     * Finner alle som ikke har transport
     * @return etterleveringer
     */
    List<PostShipment> findAllWithoutTransport();

    /**
     * Lagrer etterlevering
     * @param postShipment
     */
    void savePostShipment(PostShipment postShipment);

    /**
     * Sletter etterlevering
     * @param postShipemt
     */
    void removePostShipment(PostShipment postShipemt);

    /**
     * Finn alle som ikke er sendt
     * @return etterleveringer
     */
    List<PostShipment> findAllNotSent();

    /**
     * Lazy laster etterlevering
     * @param postShipment
     * @param enums
     */
    void lazyLoad(PostShipment postShipment, LazyLoadPostShipmentEnum[] enums);

    /**
     * Lazy laster etterleveringstre
     * @param postShipment
     */
    void lazyLoadTree(PostShipment postShipment);

    /**
     * Finn basert på ordrenummer
     * @param orderNr
     * @return etterleveringer
     */
    List<PostShipment> findByOrderNr(String orderNr);

    /**
     * Finn basert på kundenummer
     * @param customerNr
     * @return etterleveringer
     */
    List<PostShipment> findByCustomerNr(Integer customerNr);

    /**
     * Last etterlevering
     * @param postShipment
     */
    void load(PostShipment postShipment);

    List<PostShipment> findSentInPeriod(Periode periode,
            String productAreaGroupName);
    PostShipment loadById(Integer postShipmentId);
}
