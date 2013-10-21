package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.util.Periode;

/**
 * Interface for dao mot tabell PRODUCTION_BUDGET
 * 
 * @author atle.brekka
 * 
 */
public interface BudgetDAO extends DAO<Budget> {
	/**
	 * Finner alle budsjetter
	 * 
	 * @return budsjett
	 */
	List<Budget> findAll();

	/**
	 * Oppdaterer budsjettobjekt
	 * 
	 * @param productionBudget
	 */
	void refreshProductionBudget(Budget productionBudget);

	/**
	 * Finner basert på år og uke
	 * 
	 * @param year
	 * @param week
	 * @param notId
	 * @param productArea
	 * @return budsjett
	 */
	List<Budget> findByYearAndWeek(Integer year, Integer week,
			Integer notId, ProductArea productArea,BudgetType budgetType);

	/**
	 * Finner produksjonsbudsjett for gitt uke og produktområde
	 * 
	 * @param year
	 * @param week
	 * @param productArea
	 * @return produksjonsbudsjett
	 */
	Budget findByYearAndWeek(Integer year, Integer week,
			ProductArea productArea,BudgetType budgetType);

	/**
	 * Finner basert på år,uke og produktområdegruppe
	 * 
	 * @param year
	 * @param week
	 * @param productAreaGroup
	 * @return budsjett
	 */
	Budget findByYearAndWeekPrProductAreaGroup(Integer year,
			Integer week, ProductAreaGroup productAreaGroup,BudgetType budgetType);

	/**
	 * Finner basert på år og uke
	 * 
	 * @param year
	 * @param week
	 * @return budsjett
	 */
	Budget findByYearAndWeek(Integer year, Integer week,BudgetType budgetType);
    List<Budget> findByYear(Integer year, ProductArea productArea,BudgetType budgetType);
    void removeForYearProductArea(Integer year, ProductArea productArea,BudgetType budgetType);

	Budget findSumPrProductAreaAndPeriode(Periode periode,
			ProductArea productArea, BudgetType budgetType);

	Budget findByYearAndSalesman(Integer year, String salesman,
			ProductArea productArea, BudgetType budgetType);

	

}
