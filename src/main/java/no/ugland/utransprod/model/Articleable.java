package no.ugland.utransprod.model;

/**
 * Interface for klasser som kan har artikler
 * @author atle.brekka
 */
public interface Articleable {
    /**
     * Henter ordre
     * @return ordre
     */
    Order getOrder();

    /**
     * Henter avvik dersom det finnes
     * @return avvik
     */
    Deviation getDeviation();
    PostShipment getPostShipment();
}
