package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.SalesDataSnapshotDAO;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SalesDataSnapshot;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.ProbabilityEnum;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class SalesDataSnapshotDAOHibernate extends SalesDataDao<SalesDataSnapshot> implements
        SalesDataSnapshotDAO {

    public SalesDataSnapshotDAOHibernate() {
        super(SalesDataSnapshot.class,"SalesDataSnapshot","snapshotId");
    }

    /*@SuppressWarnings("unchecked")
    public final List<SaleReportSum> groupSumCountyByProbabilityProductAreaPeriode(
            final ProbabilityEnum probabilityEnum, final ProductArea productArea, final Periode periode) {
        return (List<SaleReportSum>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {
                Integer startDate = Util.convertDateToInt(periode.getStartDate());
                Integer endDate = Util.convertDateToInt(periode.getEndDate());
                String sql = "select new no.ugland.utransprod.util.report.SaleReportSum("
                        + "               count(salesDataSnapshot.saleId),"
                        + "               sum(salesDataSnapshot.ownProductionCost),"
                        + "               sum(salesDataSnapshot.transportCost),"
                        + "               sum(salesDataSnapshot.assemblyCost),"
                        + "               sum(salesDataSnapshot.yesLines),"
                        + "               sum(salesDataSnapshot.contributionMargin),"
                        + "               salesDataSnapshot.countyName)"
                        + "   from SalesDataSnapshot salesDataSnapshot "
                        + "   where   salesDataSnapshot.productAreaNr in (:productAreaNr) and "
                        + "     salesDataSnapshot.probability in(:probabilityList) and "
                        + "           salesDataSnapshot." + probabilityEnum.getDateString()
                        + " between :startDate and :endDate and "
                        + "     salesDataSnapshot.ownProductionCost is not null and "
                        + "     salesDataSnapshot.ownProductionCost > :ownProductionCostLimit"
                        + "   group by salesDataSnapshot.countyName";
                return session.createQuery(sql).setParameterList("probabilityList",
                        probabilityEnum.getProbabilityList()).setParameterList("productAreaNr", productArea.getProductAreaNrList())
                        .setParameter("startDate", startDate).setParameter("endDate", endDate).setParameter(
                                "ownProductionCostLimit", productArea.getOwnProductionCostLimit()).list();
            }

        });
    }*/
    
   /* @SuppressWarnings("unchecked")
    public final List<SaleReportSum> groupSumSalesmanByProbabilityProductAreaPeriode(
            final ProbabilityEnum probabilityEnum, final ProductArea productArea, final Periode periode) {
        return (List<SaleReportSum>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {
                Integer startDate = Util.convertDateToInt(periode.getStartDate());
                Integer endDate = Util.convertDateToInt(periode.getEndDate());
                String sql = "select new no.ugland.utransprod.util.report.SaleReportSum("
                        + "               count(salesDataSnapshot.saleId),"
                        + "               sum(salesDataSnapshot.ownProductionCost),"
                        + "               sum(salesDataSnapshot.transportCost),"
                        + "               sum(salesDataSnapshot.assemblyCost),"
                        + "               sum(salesDataSnapshot.yesLines),"
                        + "               sum(salesDataSnapshot.contributionMargin),"
                        + "               '',salesDataSnapshot.salesman)"
                        + "   from SalesDataSnapshot salesDataSnapshot "
                        + "   where   salesDataSnapshot.productAreaNr in (:productAreaNr) and "
                        + "     salesDataSnapshot.probability in(:probabilityList) and "
                        + "           salesDataSnapshot." + probabilityEnum.getDateString()
                        + " between :startDate and :endDate and "
                        + "     salesDataSnapshot.ownProductionCost is not null and "
                        + "     salesDataSnapshot.ownProductionCost > :ownProductionCostLimit"
                        + "   group by salesDataSnapshot.salesman";
                return session.createQuery(sql).setParameterList("probabilityList",
                        probabilityEnum.getProbabilityList()).setParameterList("productAreaNr", productArea.getProductAreaNrList())
                        .setParameter("startDate", startDate).setParameter("endDate", endDate).setParameter(
                                "ownProductionCostLimit", productArea.getOwnProductionCostLimit()).list();
            }

        });
    }*/

   /* public final Integer countByProbabilityProductAreaPeriode(final ProbabilityEnum probabilityEnum,
            final ProductArea productArea, final Periode periode) {
        return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            @SuppressWarnings("unchecked")
            public Object doInHibernate(final Session session) {
                Integer startDate = Util.convertDateToInt(periode.getStartDate());
                Integer endDate = Util.convertDateToInt(periode.getEndDate());
                String sql = "select count(salesDataSnapshot.snapshotId)"
                        + "   from SalesDataSnapshot salesDataSnapshot "
                        + "   where   salesDataSnapshot.productAreaNr in (:productAreaNr) and "
                        + "     salesDataSnapshot.probability in(:probabilityList) and "
                        + "           salesDataSnapshot." + probabilityEnum.getDateString()
                        + " between :startDate and :endDate and "
                        + "     salesDataSnapshot.ownProductionCost is not null and "
                        + "     salesDataSnapshot.ownProductionCost > :ownProductionCostLimit";
                List<Integer> countList = session.createQuery(sql).setParameterList("probabilityList",
                        probabilityEnum.getProbabilityList()).setParameterList("productAreaNr",
                        productArea.getProductAreaNrList()).setParameter("startDate", startDate).setParameter(
                        "endDate", endDate).setParameter("ownProductionCostLimit",
                        productArea.getOwnProductionCostLimit()).list();

                if (countList != null && countList.size() == 1) {
                    return countList.get(0);
                }
                return 0;
            }

        });
    }*/

   /* @SuppressWarnings("unchecked")
    public final List<SaleReportData> getSaleReportByProbabilityProductAreaPeriode(
            final ProbabilityEnum probabilityEnum, final ProductArea productArea, final Periode periode,
            final String typeName) {
        return (List<SaleReportData>) getHibernateTemplate().execute(new HibernateCallback() {

         
        	 public Object doInHibernate(final Session session) {
                 Integer startDate = Util.convertDateToInt(periode.getStartDate());
                 Integer endDate = Util.convertDateToInt(periode.getEndDate());
                 String sql = "select new no.ugland.utransprod.util.report.SaleReportData(salesDataSnapshot.probability,"
                         + "               salesDataSnapshot.countyName,"
                         + "               salesDataSnapshot.salesman,"
                         + "               salesDataSnapshot.customerNr,"
                         + "               salesDataSnapshot.customerName,"
                         + "               salesDataSnapshot.orderNr,"
                         + "               salesDataSnapshot.ownProductionCost,"
                         + "         salesDataSnapshot.transportCost,"
                         + "       salesDataSnapshot.assemblyCost," + "       salesDataSnapshot.yesLines,"
                         + "       salesDataSnapshot.contributionMargin,"
                         + "       salesDataSnapshot.saledate,salesDataSnapshot.registered,salesDataSnapshot.orderDate)"
                         + "   from SalesDataSnapshot salesDataSnapshot "
                         + "   where   salesDataSnapshot.productAreaNr in (:productAreaNr) and "
                         + "           salesDataSnapshot.saledate "
                         + " between :startDate and :endDate and "
                         + "     salesDataSnapshot.ownProductionCost is not null and "
                         + "     salesDataSnapshot.ownProductionCost > :ownProductionCostLimit";
                 return session.createQuery(sql).setParameterList("productAreaNr",
                         productArea.getProductAreaNrList()).setParameter("startDate", startDate).setParameter(
                         "endDate", endDate).setParameter("ownProductionCostLimit",
                         productArea.getOwnProductionCostLimit()).list();
             }

         });
    }*/

	/*public List<SaleReportData> getSaleReportByProductAreaPeriode(
			final ProductArea productArea, final Periode periode) {
		return (List<SaleReportData>) getHibernateTemplate().execute(new HibernateCallback() {

	 
	        	 public Object doInHibernate(final Session session) {
	                 Integer startDate = Util.convertDateToInt(periode.getStartDate());
	                 Integer endDate = Util.convertDateToInt(periode.getEndDate());
	                 String sql = "select new no.ugland.utransprod.util.report.SaleReportData(salesDataSnapshot.probability,"
	                         + "               salesDataSnapshot.countyName,"
	                         + "               salesDataSnapshot.salesman,"
	                         + "               salesDataSnapshot.customerNr,"
	                         + "               salesDataSnapshot.customerName,"
	                         + "               salesDataSnapshot.orderNr,"
	                         + "               salesDataSnapshot.ownProductionCost,"
	                         + "         salesDataSnapshot.transportCost,"
	                         + "       salesDataSnapshot.assemblyCost," + "       salesDataSnapshot.yesLines,"
	                         + "       salesDataSnapshot.contributionMargin,"
	                         + "       salesDataSnapshot.saledate,salesDataSnapshot.registered,salesDataSnapshot.orderDate)"
	                         + "   from SalesDataSnapshot salesDataSnapshot "
	                         + "   where   salesDataSnapshot.productAreaNr in (:productAreaNr) and "
	                         + "           salesDataSnapshot.saledate "
	                         + " between :startDate and :endDate and "
	                         + "     salesDataSnapshot.ownProductionCost is not null and "
	                         + "     salesDataSnapshot.ownProductionCost > :ownProductionCostLimit";
	                 return session.createQuery(sql).setParameterList("productAreaNr",
	                         productArea.getProductAreaNrList()).setParameter("startDate", startDate).setParameter(
	                         "endDate", endDate).setParameter("ownProductionCostLimit",
	                         productArea.getOwnProductionCostLimit()).list();
	             }

	         });
	}*/

}
