package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.AttributeDAO;
import no.ugland.utransprod.model.Attribute;

/**
 * Implementasjon av DAO mot ATTRIBUTE for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class AttributeDAOHibernate extends BaseDAOHibernate<Attribute>
		implements AttributeDAO {
	/**
	 * 
	 */
	public AttributeDAOHibernate() {
		super(Attribute.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.AttributeDAO#refreshObject(no.ugland.utransprod.model.Attribute)
	 */
	public void refreshObject(Attribute attribute) {
		getHibernateTemplate().load(attribute, attribute.getAttributeId());

	}

	/**
	 * @see no.ugland.utransprod.dao.AttributeDAO#lazyLoad(no.ugland.utransprod.model.Attribute,
	 *      no.ugland.utransprod.service.enums.LazyLoadAttributeEnum[])
	 */
	/*public void lazyLoad(final Attribute attribute,
			final LazyLoadAttributeEnum[] enums) {
		if (attribute != null && attribute.getAttributeId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session)
						throws HibernateException {
					session.load(attribute, attribute.getAttributeId());
					Set<?> set;

					for (LazyLoadAttributeEnum lazyEnum : enums) {
						switch (lazyEnum) {
						case CHOICES:
							set = attribute.getAttributeChoices();
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
