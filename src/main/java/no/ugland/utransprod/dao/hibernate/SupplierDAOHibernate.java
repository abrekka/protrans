package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.SupplierDAO;
import no.ugland.utransprod.model.Supplier;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot tabell SUPPLIER
 * 
 * @author atle.brekka
 */
public class SupplierDAOHibernate extends BaseDAOHibernate<Supplier> implements SupplierDAO {
    public SupplierDAOHibernate() {
	super(Supplier.class);
    }

    /**
     * @see no.ugland.utransprod.dao.SupplierDAO#refreshObject(no.ugland.utransprod.model.Supplier)
     */
    public final void refreshObject(final Supplier supplier) {
	getHibernateTemplate().load(supplier, supplier.getSupplierId());

    }

    @SuppressWarnings("unchecked")
    public final List<Supplier> findByTypeName(final String typeString, final String orderBy) {
	return (List<Supplier>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		Criteria crit = session.createCriteria(Supplier.class);

		if (orderBy != null) {
		    crit.addOrder(Order.asc(orderBy));
		}

		crit = crit.createCriteria("supplierType").add(Restrictions.ilike("supplierTypeName", typeString));

		return crit.list();
	    }

	});
    }

    public final Supplier findByName(final String name) {
	return (Supplier) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(final Session session) {
		List<Supplier> list = session.createCriteria(Supplier.class).add(Restrictions.ilike("supplierName", name)).list();
		if (list != null && list.size() == 1) {
		    return list.get(0);
		}
		return null;
	    }

	});
    }

    @SuppressWarnings("unchecked")
    public final List<Supplier> findActiveByTypeName(final String typeString, final String orderBy) {// ,
												     // final
												     // ProductAreaGroup
												     // productAreaGroup)
												     // {
	return (List<Supplier>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		Criteria crit = session.createCriteria(Supplier.class).add(
			Restrictions.or(Restrictions.isNull("inactive"), Restrictions.eq("inactive", 0)));

		if (orderBy != null) {
		    crit.addOrder(Order.asc(orderBy));
		}

		crit.createAlias("supplierType", "supplierType");
		crit.add(Restrictions.ilike("supplierType.supplierTypeName", typeString));

		// if (productAreaGroup != null &&
		// !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle"))
		// {
		//
		// crit =
		// crit.createCriteria("supplierProductAreaGroups").add(Restrictions.eq("productAreaGroup",
		// productAreaGroup));
		// }

		return crit.list();
	    }

	});
    }

    @SuppressWarnings("unchecked")
    public final List<Supplier> findHavingAssembly(final Integer year, final Integer fromWeek, final Integer toWeek) {
	return (List<Supplier>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		// String sqlProductAreaGroup =
		// "select supplier from Supplier supplier "
		// + "      where exists(select 1 from Assembly assembly "
		// + "                     where (assembly.inactive is null or "
		// + "                      assembly.inactive=0) and "
		// +
		// "                           assembly.order.productArea.productAreaGroup=:productAreaGroup and"
		// +
		// "                            assembly.supplier=supplier and "
		// +
		// "                           assembly.assemblyYear=:year and "
		// +
		// "                           assembly.assemblyWeek between :fromWeek and "
		// + "                     :toWeek) "
		// + " union "
		// + "select supplier from Supplier supplier "
		// + "      where exists(select 1 from Assembly assembly "
		// + "                     where (assembly.inactive is null or "
		// + "                      assembly.inactive=0) and "
		// +
		// "                     assembly.deviation.productArea.productAreaGroup=:productAreaGroup and "
		// +
		// "                            assembly.supplier=supplier and "
		// +
		// "                           assembly.assemblyYear=:year and "
		// +
		// "                           assembly.assemblyWeek between :fromWeek and "
		// + "                     :toWeek)";

		String sql = "select supplier from Supplier supplier " + "      where exists(select 1 from Assembly assembly "
			+ "                     where (assembly.inactive is null or " + "                      assembly.inactive=0) and "
			+ "                            assembly.supplier=supplier and "
			+ "                           assembly.assemblyYear=:year and "
			+ "                           assembly.assemblyWeek between :fromWeek and " + "                     :toWeek) ";

		Query query = null;
		// if (productAreaGroup.getProductAreaGroupName()
		// .equalsIgnoreCase("Alle")) {
		query = session.createQuery(sql).setParameter("year", year).setParameter("fromWeek", fromWeek).setParameter("toWeek", toWeek);
		// } else {
		// query = session.createQuery(sqlProductAreaGroup)
		// .setParameter("productAreaGroup",
		// productAreaGroup).setParameter(
		// "year", year).setParameter(
		// "fromWeek", fromWeek).setParameter(
		// "toWeek", toWeek);
		// }

		return query.list();
	    }

	});
    }

}
