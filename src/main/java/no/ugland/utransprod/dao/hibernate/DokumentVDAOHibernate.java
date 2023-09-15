package no.ugland.utransprod.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import no.ugland.utransprod.dao.DokumentVDAO;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.DokumentV;
import no.ugland.utransprod.model.DrawerV;

public class DokumentVDAOHibernate extends BaseDAOHibernate<DokumentV> implements DokumentVDAO {
	public DokumentVDAOHibernate() {
		super(DokumentV.class);
	}

	public List<DokumentV> finnDokumenter(final String orderNr) {
		return (List<DokumentV>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				return session.createCriteria(DokumentV.class)
						.add(Restrictions.eq("projectNumber", orderNr)).list();
			}

		});
	}

}
