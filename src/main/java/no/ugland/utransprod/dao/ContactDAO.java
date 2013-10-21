package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.Contact;


/**
 * Interface for DAO mot tabell CONTACT
 * @author atle.brekka
 *
 */
public interface ContactDAO extends DAO<Contact>{
	/**
	 * Finner alle kontakter med ordre til avrop
	 * @param category
	 * @return kontakter
	 */
	List<Contact> findAllTilAvrop(Integer category);
	/**
	 * Lazy laster
	 * @param contact
	 * @param enums
	 */
	//void lazyLoad(Contact contact,LazyLoadContactEnum[] enums);
}
