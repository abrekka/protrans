package no.ugland.utransprod.service;

import java.util.List;

import javax.swing.JLabel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.util.Periode;

/**
 * Interface for serviceklasse mot tabell PRODUCTION_BUDGET
 * @author atle.brekka
 */
public interface BudgetManager extends
        OverviewManager<Budget> {
    public String FILE_HEADER_IMPORT_BUDGET = "Uke;Budsjettall;Timer;Avdeling;År;";
    public static final String FILE_HEADER_IMPORT_BUDGET_SALESMAN = "Selger;Salgsmål ordre;Avdeling;År;Salgsmål tilbud;";

    public static final int COLUMN_WEEK = 0;

    public static final int COLUMN_BUDGET = 1;
    public static final int COLUMN_HOURS = 2;

    public static final int COLUMN_PRODUCT_AREA = 3;
    public static final int COLUMN_PRODUCT_AREA_BUDGET_SALESMAN=2;

    public static final int COLUMN_BUDGET_OFFER = 4;
    public static final int COLUMN_SALESMAN = 0;

	String MANAGER_NAME = "budgetManager";
	

    /**
     * Finner basert på år, uke, ikke id og produktområde
     * @param year
     * @param week
     * @param notId
     * @param productArea
     * @return budsjett
     */
    List<Budget> findByYearAndWeek(Integer year, Integer week,Integer notId, ProductArea productArea,BudgetType budgetType);

    /**
     * Finner basert på år, uke og produktområde
     * @param year
     * @param week
     * @param productArea
     * @return budsjett
     */
    //Budget findByYearAndWeek(Integer year, Integer week,ProductArea productArea);

    /**
     * Finner basert på år,uke og produktområdegruppe
     * @param year
     * @param week
     * @param productAreaGroup
     * @return budsjett
     */
    Budget findByYearAndWeekPrProductAreaGroup(Integer year,Integer week, ProductAreaGroup productAreaGroup,BudgetType budgetType);


    List<Budget> findByYear(Integer year, ProductArea productArea,BudgetType budgetType);

    void removeForYearProductArea(Integer year, ProductArea productArea,BudgetType budgetType);

    void setLabelInfo(JLabel aLabel);

	Budget findByYearAndWeek(Integer year, Integer week,ProductArea productArea, BudgetType budgetType);

	void importBudget(String file, BudgetType budgetType)throws ProTransException;

	Budget findSumPrProductAreaAndPeriode(Periode periode,
			ProductArea productArea, BudgetType sale);

	 Budget findByYearAndSalesman(final Integer year, final String salesman,
				final ProductArea productArea, final BudgetType budgetType);
}
