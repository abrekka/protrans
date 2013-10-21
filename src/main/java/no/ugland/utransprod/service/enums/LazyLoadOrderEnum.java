package no.ugland.utransprod.service.enums;

/**
 * Lazy lasting av ordre.
 * @author atle.brekka
 */
public enum LazyLoadOrderEnum {
    /**
     * Ordrelinjer.
     */
    ORDER_LINES,
    /**
     * Kostnader.
     */
    ORDER_COSTS,

    /**
     * Ordrelinjer til ordrelinjer.
     */
    ORDER_LINE_ORDER_LINES,
    /**
     * Kollier.
     */
    COLLIES,
    /**
     * Etterleveringer.
     */
    POST_SHIPMENTS,
    /**
     * Eksterne ordre.
     */
    EXTERNAL_ORDER,
    /**
     * Kommentarer.
     */
    COMMENTS,
    DEVIATIONS,
    ORDER_LINE_ATTRIBUTES,
    PROCENT_DONE;
}
