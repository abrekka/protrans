package no.ugland.utransprod.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import no.ugland.utransprod.dao.AttributeChoiceDAO;
import no.ugland.utransprod.dao.TakstolInfoVDAO;
import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum;
import no.ugland.utransprod.model.AttributeChoice;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.TakstolInfoV;

public class TakstolInfoVDAOHibernate extends BaseDAOHibernate<TakstolInfoV>
		implements TakstolInfoVDAO {
	public TakstolInfoVDAOHibernate() {
		super(TakstolInfoV.class);
	}

	public List<TakstolInfoV> findByOrderNr(final String orderNr) {
		return (List<TakstolInfoV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session) {
						return session
								.createCriteria(TakstolInfoV.class)
								.add(Restrictions.eq("takstolInfoVPK.ordernr",
										orderNr)).list();
					}
				});
	}

	public List<Object[]> summerArbeidsinnsats(final String fraAarUke,
			final String tilAarUke,
			final TransportConstraintEnum transportConstraintEnum,final String productArea) {
		return (List<Object[]>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session) {
						StringBuffer queryString = new StringBuffer(
								"select 0,       takstolInfoV.transportYear,"
										+ "       takstolInfoV.transportWeek,'Beregnet tid',"
										+ "       sum(takstolInfoV.beregnetTid),'Intern'"
										+ " from TakstolInfoV takstolInfoV "
										+ " where takstolInfoV.productArea=:productArea and "
										+ " cast(takstolInfoV.transportYear as string)"
										+ "    ||cast(takstolInfoV.transportWeek+10 as string) between  "
										+ "    :fromString and :toString");

						switch (transportConstraintEnum) {
						case TRANSPORTED:
							queryString
									.append(" and takstolInfoV.sent is not null");
							break;
						case TRANSPORT_PLANNED:
							queryString
									.append(" and takstolInfoV.sent is null");
							break;
						case ALL:
							break;
						default:
							break;
						}

						queryString
								.append(" group by takstolInfoV.transportYear,takstolInfoV.transportWeek");
						queryString
								.append(" order by takstolInfoV.transportYear,takstolInfoV.transportWeek");

						return session.createQuery(queryString.toString())
								.setParameter("fromString", fraAarUke)
								.setParameter("toString", tilAarUke)
								.setParameter("productArea", productArea)
								.list();
					}
				});
	}

	public List<Object[]> getBeregnetTidForOrdre(final String fraAarUke,
			final String tilAarUke, final TransportConstraintEnum transportConstraintEnum,
			final String productArea) {
		return (List<Object[]>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session) {
						StringBuffer queryString = new StringBuffer(
								"select takstolInfoV.orderId,"
										+ "takstolInfoV.takstolInfoVPK.ordernr,"
										+ "takstolInfoV.kundenr,"
										+ "takstolInfoV.navn,"
										+ "takstolInfoV.leveringsadresse,"
										+ "takstolInfoV.postnr,"
										+ "takstolInfoV.transportName,"
										+ "takstolInfoV.transportYear,"
										+ "takstolInfoV.transportWeek,"
										+ "'Beregnet tid',"
										+ "takstolInfoV.beregnetTid,"
										+ "'Intern',"
										+ "       -1 as postShipmentId "
										+ "from TakstolInfoV takstolInfoV"
										+ " where takstolInfoV.productArea=:productArea and "
										+ " cast(takstolInfoV.transportYear as string)||"
										+ "cast(takstolInfoV.transportWeek+10 as string) "
										+ " between  :fromString and :toString");

						switch (transportConstraintEnum) {
						case TRANSPORTED:
							queryString
									.append(" and takstolInfoV.sent is not null");
							break;
						case TRANSPORT_PLANNED:
							queryString
									.append(" and takstolInfoV.sent is null");
							break;
						case ALL:
							break;
						default:
							break;
						}

						queryString.append(" order by takstolInfoV.orderId");

						return session.createQuery(queryString.toString())
								.setParameter("fromString", fraAarUke)
								.setParameter("toString", tilAarUke)
								.setParameter("productArea", productArea)
								.list();

					}
				});
	}
}
