package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.ExternalOrderLineDAO;
import no.ugland.utransprod.model.ExternalOrderLine;

/**
 * Implementasjon av DAO mot tabell EXTERNAL_ORDER_LINE
 * 
 * @author atle.brekka
 * 
 */
public class ExternalOrderLineDAOHibernate extends
		BaseDAOHibernate<ExternalOrderLine> implements ExternalOrderLineDAO {
	/**
	 * 
	 */
	public ExternalOrderLineDAOHibernate() {
		super(ExternalOrderLine.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.ExternalOrderLineDAO#lazyLoad(no.ugland.utransprod.model.ExternalOrderLine,
	 *      no.ugland.utransprod.service.enums.LazyLoadExternalOrderLineEnum[])
	 */
	/*public void lazyLoad(final ExternalOrderLine externalOrderLine,
			final LazyLoadExternalOrderLineEnum[] enums) {
		if (externalOrderLine != null
				&& externalOrderLine.getExternalOrderLineId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session)
						throws HibernateException {
					if (!session.contains(externalOrderLine)) {
						session.load(externalOrderLine, externalOrderLine
								.getExternalOrderLineId());
					}
					Set<?> set;

					for (LazyLoadExternalOrderLineEnum lazyEnum : enums) {
						switch (lazyEnum) {
						case EXTERNAL_ORDER_LINE_ATTRIBUTE:
							set = externalOrderLine
									.getExternalOrderLineAttributes();
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
