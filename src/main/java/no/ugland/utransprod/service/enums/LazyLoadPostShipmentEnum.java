package no.ugland.utransprod.service.enums;

/**
 * Lazy lasting av etterlevering.
 * @author atle.brekka
 */
public enum LazyLoadPostShipmentEnum {
    /**
     * Kollier.
     */
    COLLIES,
    /**
     * Ordrelinjer.
     */
    ORDER_LINES,
    ORDER_LINE_ATTRIBUTES,
    /**
     * Referanser til etterlevering.
     */
    POST_SHIPMENT_REFS,
    /**
     * Ordrelinjer til ordrelinjer.
     */
    ORDER_LINE_ORDER_LINES,
    /**
     * Kommentarer.
     */
    ORDER_COMMENTS
}
