package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.ContactDAO;
import no.ugland.utransprod.model.Contact;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot tabell CONTACT
 * 
 * @author atle.brekka
 * 
 */
public class ContactDAOHibernate extends BaseDAOHibernate<Contact> implements
		ContactDAO {
	/**
	 * 
	 */
	public ContactDAOHibernate() {
		super(Contact.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.ContactDAO#findAllTilAvrop(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<Contact> findAllTilAvrop(final Integer category) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException {
				return session.createCriteria(Contact.class).add(
						Restrictions.eq("categoryIdx", category)).list();
			}

		});

	}

	/**
	 * @see no.ugland.utransprod.dao.ContactDAO#lazyLoad(no.ugland.utransprod.model.Contact,
	 *      no.ugland.utransprod.service.enums.LazyLoadContactEnum[])
	 */
	/*public void lazyLoad(final Contact contact,
			final LazyLoadContactEnum[] enums) {
		if (contact != null && contact.getContactId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session)
						throws HibernateException {
					if (!session.contains(contact)) {
						session.load(contact, contact.getContactId());
					}
					Set<?> set;

					for (LazyLoadContactEnum lazyEnum : enums) {
						switch (lazyEnum) {
						case ADDRESSES:
							set = contact.getAddresses();
							set.iterator();
							break;
						}
					}
					return null;
				}

			});
		}

	}*/

}
