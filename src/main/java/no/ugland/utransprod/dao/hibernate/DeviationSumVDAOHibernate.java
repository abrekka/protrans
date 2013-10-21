package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.DeviationSumVDAO;
import no.ugland.utransprod.model.DeviationSumV;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view DEVIATION_SUM_V
 * @author atle.brekka
 */
public class DeviationSumVDAOHibernate extends BaseDAOHibernate<DeviationSumV> implements DeviationSumVDAO {
    /**
     * Konstruktør
     */
    public DeviationSumVDAOHibernate() {
        super(DeviationSumV.class);
    }

    /**
     * @see no.ugland.utransprod.dao.DeviationSumVDAO#findByParams(no.ugland.utransprod.util.excel.ExcelReportSettingDeviation)
     */
    @SuppressWarnings("unchecked")
    public List<DeviationSumV> findByParams(final ExcelReportSettingDeviation params) {
        return (List<DeviationSumV>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {
                StringBuilder sql = new StringBuilder(
                        "select new DeviationSumV(deviationSumV.deviationSumVPK.jobFunctionName,"
                                + "								deviationSumV.deviationSumVPK.functionCategoryName,"
                                + "								deviationSumV.deviationSumVPK.deviationStatusName,"
                                + "								sum(deviationSumV.numberOfDeviations),"
                                + "								sum(deviationSumV.internalCost),"
                                + "								sum(deviationSumV.externalCost),"
                                + "                               deviationSumV.productArea) "
                                + "			from DeviationSumV deviationSumV "
                                + "			where deviationSumV.deviationSumVPK.registrationYear=:year");

                if (params.getWeekFrom() != null && params.getWeekTo() == null) {
                    sql.append(" and deviationSumV.deviationSumVPK.registrationWeek >=:fromWeek");
                } else if (params.getWeekFrom() == null && params.getWeekTo() != null) {
                    sql.append(" and deviationSumV.deviationSumVPK.registrationWeek <=:toWeek");
                } else {
                    sql
                            .append(" and deviationSumV.deviationSumVPK.registrationWeek between :fromWeek and :toWeek");
                }

                if (params.getDeviationFunction() != null) {
                    sql.append(" and deviationSumV.deviationSumVPK.jobFunctionName=:jobFunctionName");
                }

                if (params.getFunctionCategory() != null) {
                    sql
                            .append(" and deviationSumV.deviationSumVPK.functionCategoryName=:functionCategoryName");
                }
                if (params.getDeviationStatus() != null) {
                    sql.append(" and deviationSumV.deviationSumVPK.deviationStatusName=:deviationStatusName");
                }

                sql
                        .append("			group by deviationSumV.deviationSumVPK.jobFunctionName,"
                                + "								deviationSumV.deviationSumVPK.functionCategoryName,"
                                + "								deviationSumV.deviationSumVPK.deviationStatusName,deviationSumV.productArea");

                Query query = session.createQuery(sql.toString()).setParameter("year", params.getYear())
                        .setParameter("fromWeek", params.getWeekFrom()).setParameter("toWeek",
                                params.getWeekTo());

                if (params.getDeviationFunction() != null) {
                    query.setParameter("jobFunctionName", params.getDeviationFunction().getJobFunctionName());
                }
                if (params.getFunctionCategory() != null) {
                    query.setParameter("functionCategoryName", params.getFunctionCategory()
                            .getFunctionCategoryName());
                }
                if (params.getDeviationStatus() != null) {
                    query.setParameter("deviationStatusName", params.getDeviationStatus()
                            .getDeviationStatusName());
                }
                return query.list();
            }

        });
    }

}
