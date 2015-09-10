package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.BudgetDAO;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.util.Periode;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av dao for hibernate
 * 
 * @author atle.brekka
 */
public class BudgetDAOHibernate extends BaseDAOHibernate<Budget> implements BudgetDAO {

    public BudgetDAOHibernate() {
	super(Budget.class);
    }

    /**
     * @see no.ugland.utransprod.dao.BudgetDAO#findAll()
     */
    @SuppressWarnings("unchecked")
    public final List<Budget> findAll() {
	return getHibernateTemplate().find("from Budget");
    }

    /**
     * @see no.ugland.utransprod.dao.BudgetDAO#
     *      refreshProductionBudget(no.ugland.utransprod.model.Budget)
     */
    public final void refreshProductionBudget(final Budget productionBudget) {
	getHibernateTemplate().load(productionBudget, productionBudget.getBudgetId());
	getHibernateTemplate().flush();

    }

    /**
     * @see no.ugland.utransprod.dao.BudgetDAO#findByYearAndWeek(java.lang.Integer,
     *      java.lang.Integer, java.lang.Integer,
     *      no.ugland.utransprod.model.ProductArea)
     */
    @SuppressWarnings("unchecked")
    public final List<Budget> findByYearAndWeek(final Integer year, final Integer week, final Integer notId, final ProductArea productArea,
	    final BudgetType budgetType) {
	return (List<Budget>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		Criteria crit = session.createCriteria(Budget.class).add(Restrictions.eq("productArea", productArea))
			.add(Restrictions.eq("budgetYear", year)).add(Restrictions.eq("budgetWeek", week))
			.add(Restrictions.eq("budgetType", budgetType.ordinal()));

		if (notId != null) {
		    crit.add(Restrictions.ne("budgetId", notId));
		}

		return crit.list();
	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.BudgetDAO#findByYearAndWeek(java.lang.Integer,
     *      java.lang.Integer, no.ugland.utransprod.model.ProductArea)
     */
    public final Budget findByYearAndWeek(final Integer year, final Integer week, final ProductArea productArea, final BudgetType budgetType) {
	return (Budget) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(final Session session) {
		List<Budget> list = session.createCriteria(Budget.class).add(Restrictions.eq("productArea", productArea))
			.add(Restrictions.eq("budgetType", budgetType.ordinal())).add(Restrictions.eq("budgetYear", year))
			.add(Restrictions.eq("budgetWeek", week)).list();

		if (list != null && list.size() == 1) {
		    return list.get(0);
		}
		return Budget.UNKNOWN;
	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.BudgetDAO#
     *      findByYearAndWeekPrProductAreaGroup(java.lang.Integer,
     *      java.lang.Integer, no.ugland.utransprod.model.ProductAreaGroup)
     */
    public final Budget findByYearAndWeekPrProductAreaGroup(final Integer year, final Integer week, final ProductAreaGroup productAreaGroup,
	    final BudgetType budgetType) {
	return (Budget) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(final Session session) {
		String sql = "select new Budget(budget.budgetYear," + "          budget.budgetWeek,sum(budget.budgetValue)) " + "from Budget budget "
			+ "where budget.budgetYear=:year and " + "      budget.budgetWeek=:week and "
			+ "      budget.productArea.productAreaGroup=:group and " + "budget.budgetType=:budgetType "
			+ "      group by budget.budgetYear,budget.budgetWeek";
		List<Budget> list = session.createQuery(sql).setParameter("year", year).setParameter("week", week)
			.setParameter("group", productAreaGroup).setParameter("budgetType", budgetType.ordinal()).list();

		if (list != null && list.size() == 1) {
		    return list.get(0);
		}
		return Budget.UNKNOWN;
	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.BudgetDAO#findByYearAndWeek(java.lang.Integer,
     *      java.lang.Integer)
     */
    public final Budget findByYearAndWeek(final Integer year, final Integer week, final BudgetType budgetType) {
	return (Budget) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(final Session session) {
		String sql = "select new Budget(budget.budgetYear," + "         budget.budgetWeek,sum(budget.budgetValue)) " + "from Budget budget "
			+ "where budget.budgetYear=:year and " + "      budget.budgetWeek=:week and " + "budget.budgetType=:budgetType"
			+ "      group by budget.budgetYear,budget.budgetWeek";
		List<Budget> list = session.createQuery(sql).setParameter("year", year).setParameter("week", week)
			.setParameter("budgetType", budgetType.ordinal()).list();

		if (list != null && list.size() == 1) {
		    return list.get(0);
		}
		return Budget.UNKNOWN;
	    }

	});
    }

    @SuppressWarnings("unchecked")
    public final List<Budget> findByYear(final Integer year, final ProductArea productArea, final BudgetType budgetType) {
	return (List<Budget>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		return session.createCriteria(Budget.class).add(Restrictions.eq("productArea", productArea)).add(Restrictions.eq("budgetYear", year))
			.add(Restrictions.eq("budgetType", budgetType.ordinal())).list();

	    }

	});
    }

    public final void removeForYearProductArea(final Integer year, final ProductArea productArea, final BudgetType budgetType) {
	getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		session.createQuery(
			"delete from Budget budget " + "where budget.budgetYear=:year and " + "      budget.productArea=:productArea and "
				+ "budget.budgetType=:budgetType").setParameter("year", year).setParameter("productArea", productArea)
			.setParameter("budgetType", budgetType.ordinal()).executeUpdate();

		return null;
	    }

	});

    }

    public Budget findSumPrProductAreaAndPeriode(final Periode periode, final ProductArea productArea, final BudgetType budgetType) {
	return (Budget) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(final Session session) {
		String sql = "select new Budget(budget.budgetYear," + "          1,sum(budget.budgetValue)) " + "from Budget budget "
			+ "where budget.budgetYear=:year and " + "      budget.budgetWeek between :weekFrom and :weekTo and "
			+ "      budget.productArea=:productArea and " + "budget.budgetType=:budgetType " + "      group by budget.budgetYear";
		List<Budget> list = session.createQuery(sql).setParameter("year", periode.getYear()).setParameter("weekFrom", periode.getWeek())
			.setParameter("weekTo", periode.getToWeek()).setParameter("productArea", productArea)
			.setParameter("budgetType", budgetType.ordinal()).list();

		if (list != null && list.size() == 1) {
		    return list.get(0);
		}
		return Budget.UNKNOWN;
	    }

	});
    }

    public Budget findByYearAndSalesman(final Integer year, final String salesman, final ProductArea productArea, final BudgetType budgetType) {
	return (Budget) getHibernateTemplate().execute(new HibernateCallback() {

	    @SuppressWarnings("unchecked")
	    public Object doInHibernate(final Session session) {

		String sql = "select budget " + "       from Budget budget,ApplicationUser applicationUser "
			+ "       where budget.budgetYear=:year and " + "             budget.productArea=:productArea and "
			+ "             budget.budgetType=:budgetType and " + "             budget.applicationUser=applicationUser and "
			+ "             applicationUser.firstName ||' '||applicationUser.lastName like :salesman";
		List<Budget> list = session.createQuery(sql).setParameter("year", year).setParameter("productArea", productArea)
			.setParameter("budgetType", budgetType.ordinal()).setParameter("salesman", salesman).list();

		if (list != null && list.size() == 1) {
		    return list.get(0);
		}
		return Budget.UNKNOWN;
	    }

	});
    }

}
