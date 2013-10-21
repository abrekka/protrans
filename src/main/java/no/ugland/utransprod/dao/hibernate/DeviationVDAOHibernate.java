package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.DeviationVDAO;
import no.ugland.utransprod.model.DeviationV;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class DeviationVDAOHibernate extends BaseDAOHibernate<DeviationV> implements DeviationVDAO {
    /**
     * Konstruktør
     */
    public DeviationVDAOHibernate() {
        super(DeviationV.class);
    }

    @SuppressWarnings("unchecked")
	public List<DeviationV> findByParams(final ExcelReportSettingDeviation params) {
        return (List<DeviationV>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(DeviationV.class).add(
                        Restrictions.eq("registrationYear", params.getYear()));

                if (params.getWeekFrom() != null && params.getWeekTo() == null) {
                    criteria.add(Restrictions.ge("registrationWeek", params.getWeekFrom()));
                } else if (params.getWeekFrom() == null && params.getWeekTo() != null) {
                    criteria.add(Restrictions.le("registrationWeek", params.getWeekTo()));
                } else {
                    criteria.add(Restrictions.between("registrationWeek", params.getWeekFrom(), params
                            .getWeekTo()));
                }

                if (params.getDeviationFunction() != null) {
                    criteria.add(Restrictions.eq("deviationFunction", params.getDeviationFunction()
                            .getJobFunctionName()));
                }

                if (params.getFunctionCategory() != null) {
                    criteria.add(Restrictions.eq("functionCategoryName", params.getFunctionCategory()
                            .getFunctionCategoryName()));
                }
                if (params.getDeviationStatus() != null) {
                    criteria.add(Restrictions.eq("deviationStatusName", params.getDeviationStatus()
                            .getDeviationStatusName()));
                }

                return criteria.list();
            }

        });

    }

}
