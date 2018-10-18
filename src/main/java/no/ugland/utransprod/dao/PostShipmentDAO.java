package no.ugland.utransprod.dao;

import java.util.Date;
import java.util.List;

import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.Periode;

/**
 * Interface for DAO mot tabell POST_SHIPMENT
 * 
 * @author atle.brekka
 * 
 */
public interface PostShipmentDAO extends DAO<PostShipment> {
	/**
	 * Finner alle uten transport
	 * 
	 * @return etterleveringer
	 */
	List<PostShipment> findAllWithoutTransport();

	/**
	 * Finner alle som ikke er sent
	 * 
	 * @return etterleveringer
	 */
	List<PostShipment> findAllNotSent();

	/**
	 * Lazy laster
	 * 
	 * @param postShipment
	 * @param enums
	 */
	void lazyLoad(PostShipment postShipment, LazyLoadPostShipmentEnum[] enums);

	/**
	 * Oppdaterer
	 * 
	 * @param postShipment
	 */
	void refreshPostShipment(PostShipment postShipment);

	/**
	 * Finner alle
	 * 
	 * @return etterleveringer
	 */
	List<PostShipment> findAll();

	/**
	 * Lazy laster tre
	 * 
	 * @param postShipment
	 */
	void lazyLoadTree(PostShipment postShipment);

	/**
	 * Finner basert på ordrenummer
	 * 
	 * @param orderNr
	 * @return etterleveringer
	 */
	List<PostShipment> findByOrderNr(String orderNr);

	/**
	 * Finner basert på kundenummer
	 * 
	 * @param customerNr
	 * @return etterleveringer
	 */
	List<PostShipment> findByCustomerNr(Integer customerNr);

	/**
	 * Laster etterlevering
	 * 
	 * @param postShipment
	 */
	void load(PostShipment postShipment);
    List<PostShipment> findSentInPeriod(Periode periode,String productAreraGroupName) ;

	void settSentDato(PostShipment postShipment, Date sentDate);

	void settLevert(PostShipment postShipment, Date levertDate);
}
