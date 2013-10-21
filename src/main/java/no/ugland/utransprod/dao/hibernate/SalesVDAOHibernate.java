package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.SalesVDAO;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SalesDataSnapshot;
import no.ugland.utransprod.model.SalesV;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.ProbabilityEnum;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class SalesVDAOHibernate extends SalesDataDao<SalesV> implements
        SalesVDAO {

    public SalesVDAOHibernate() {
        super(SalesV.class,"SalesV","saleId");
    }

	

    /*@SuppressWarnings("unchecked")
    public final List<SaleReportSum> groupSumCountyByProbabilityProductAreaPeriode(
            final ProbabilityEnum probabilityEnum, final ProductArea productArea,
            final Periode periode) {
        return (List<SaleReportSum>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        Integer startDate = Util.convertDateToInt(periode
                                .getStartDate());
                        Integer endDate = Util.convertDateToInt(periode
                                .getEndDate());
                        String sql = "select new no.ugland.utransprod.util.report.SaleReportSum("
                                + "               count(salesV.saleId),"
                                + "               sum(salesV.ownProductionCost),"
                                + "               sum(salesV.transportCost),"
                                + "               sum(salesV.assemblyCost),"
                                + "               sum(salesV.yesLines),"
                                + "               sum(salesV.contributionMargin),"
                                + "               salesV.countyName)"
                                + "   from SalesV salesV "
                                + "   where   salesV.productAreaNr in (:productAreaNr) and "
                                + "     salesV.probability in(:probabilityList) and "
                                + "           salesV."+probabilityEnum.getDateString()+" between :startDate and :endDate and "
                                + "     salesV.ownProductionCost is not null and "
                                + "     salesV.ownProductionCost > :ownProductionCostLimit"
                                + "   group by salesV.countyName";
                        return session.createQuery(sql).setParameterList("probabilityList",
                                probabilityEnum.getProbabilityList()).setParameterList(
                                "productAreaNr", productArea.getProductAreaNrList()).setParameter(
                                "startDate", startDate).setParameter("endDate",
                                endDate).setParameter("ownProductionCostLimit", productArea.getOwnProductionCostLimit()).list();
                    }

                });
    }*/
    
   /* @SuppressWarnings("unchecked")
    public final List<SaleReportSum> groupSumSalesmanByProbabilityProductAreaPeriode(
            final ProbabilityEnum probabilityEnum, final ProductArea productArea,
            final Periode periode) {
        return (List<SaleReportSum>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        Integer startDate = Util.convertDateToInt(periode
                                .getStartDate());
                        Integer endDate = Util.convertDateToInt(periode
                                .getEndDate());
                        String sql = "select new no.ugland.utransprod.util.report.SaleReportSum("
                                + "               count(salesV.saleId),"
                                + "               sum(salesV.ownProductionCost),"
                                + "               sum(salesV.transportCost),"
                                + "               sum(salesV.assemblyCost),"
                                + "               sum(salesV.yesLines),"
                                + "               sum(salesV.contributionMargin),"
                                + "               '',salesV.salesman)"
                                + "   from SalesV salesV "
                                + "   where   salesV.productAreaNr in (:productAreaNr) and "
                                + "     salesV.probability in(:probabilityList) and "
                                + "           salesV."+probabilityEnum.getDateString()+" between :startDate and :endDate and "
                                + "     salesV.ownProductionCost is not null and "
                                + "     salesV.ownProductionCost > :ownProductionCostLimit"
                                + "   group by salesV.salesman";
                        return session.createQuery(sql).setParameterList("probabilityList",
                                probabilityEnum.getProbabilityList()).setParameterList(
                                "productAreaNr", productArea.getProductAreaNrList()).setParameter(
                                "startDate", startDate).setParameter("endDate",
                                endDate).setParameter("ownProductionCostLimit", productArea.getOwnProductionCostLimit()).list();
                    }

                });
    }*/

   /* public final Integer countByProbabilityProductAreaPeriode(
            final ProbabilityEnum probabilityEnum, final ProductArea productArea,
            final Periode periode) {
        return (Integer) getHibernateTemplate().execute(
                new HibernateCallback() {

                    @SuppressWarnings("unchecked")
                    public Object doInHibernate(final Session session) {
                        Integer startDate = Util.convertDateToInt(periode
                                .getStartDate());
                        Integer endDate = Util.convertDateToInt(periode
                                .getEndDate());
                        String sql = "select count(salesV.saleId)"
                                + "   from SalesV salesV "
                                + "   where   salesV.productAreaNr in (:productAreaNr) and "
                                + "     salesV.probability in(:probabilityList) and "
                                + "           salesV."+probabilityEnum.getDateString()+" between :startDate and :endDate and "
                                + "     salesV.ownProductionCost is not null and "
                                + "     salesV.ownProductionCost > :ownProductionCostLimit";
                        List<Integer> countList = session.createQuery(sql).setParameterList("probabilityList",
                                probabilityEnum.getProbabilityList())
                                .setParameterList("productAreaNr", productArea.getProductAreaNrList())
                                .setParameter("startDate", startDate)
                                .setParameter("endDate", endDate).setParameter("ownProductionCostLimit", productArea.getOwnProductionCostLimit()).list();

                        if (countList != null && countList.size() == 1) {
                            return countList.get(0);
                        }
                        return 0;
                    }

                });
    }*/

   /* @SuppressWarnings("unchecked")
    public final List<SaleReportData> getSaleReportByProbabilityProductAreaPeriode(
            final ProbabilityEnum probabilityEnum, final ProductArea productArea,
            final Periode periode, final String typeName) {
        return (List<SaleReportData>) getHibernateTemplate().execute(
                new HibernateCallback() {

       	
                	public Object doInHibernate(final Session session) {
                        Integer startDate = Util.convertDateToInt(periode.getStartDate());
                        Integer endDate = Util.convertDateToInt(periode.getEndDate());
                        String sql = "select new no.ugland.utransprod.util.report.SaleReportData(salesV.probability,"
                                + "               salesV.countyName,"
                                + "               salesV.salesman,"
                                + "               salesV.customerNr,"
                                + "               salesV.customerName,"
                                + "               salesV.orderNr,"
                                + "               salesV.ownProductionCost,"
                                + "         salesV.transportCost,"
                                + "       salesV.assemblyCost," + "       salesV.yesLines,"
                                + "       salesV.contributionMargin,"
                                + "       salesV.saledate,salesV.registered,salesV.orderDate)"
                                + "   from SalesV salesV "
                                + "   where   salesV.productAreaNr in (:productAreaNr) and "
                                + "           salesV.saledate "
                                + " between :startDate and :endDate and "
                                + "     salesV.ownProductionCost is not null and "
                                + "     salesV.ownProductionCost > :ownProductionCostLimit";
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
                String sql = "select new no.ugland.utransprod.util.report.SaleReportData(salesV.probability,"
                        + "               salesV.countyName,"
                        + "               salesV.salesman,"
                        + "               salesV.customerNr,"
                        + "               salesV.customerName,"
                        + "               salesV.orderNr,"
                        + "               salesV.ownProductionCost,"
                        + "         salesV.transportCost,"
                        + "       salesV.assemblyCost," + "       salesV.yesLines,"
                        + "       salesV.contributionMargin,"
                        + "       salesV.saledate,salesV.registered,salesV.orderDate)"
                        + "   from SalesV salesV "
                        + "   where   salesV.productAreaNr in (:productAreaNr) and "
                        + "           salesV.saledate "
                        + " between :startDate and :endDate and "
                        + "     salesV.ownProductionCost is not null and "
                        + "     salesV.ownProductionCost > :ownProductionCostLimit";
                return session.createQuery(sql).setParameterList("productAreaNr",
                        productArea.getProductAreaNrList()).setParameter("startDate", startDate).setParameter(
                        "endDate", endDate).setParameter("ownProductionCostLimit",
                        productArea.getOwnProductionCostLimit()).list();
            }

        });
	}*/

}
