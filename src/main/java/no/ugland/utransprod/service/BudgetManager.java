
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import javax.swing.JLabel;
import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.util.Periode;

public interface BudgetManager extends OverviewManager<Budget> {
   String FILE_HEADER_IMPORT_BUDGET = "Uke;Budsjettall;Timer;Avdeling;År;";
   String FILE_HEADER_IMPORT_BUDGET_SALESMAN = "Selger;Salgsmål ordre;Avdeling;År;Salgsmål tilbud;";
   int COLUMN_WEEK = 0;
   int COLUMN_BUDGET = 1;
   int COLUMN_HOURS = 2;
   int COLUMN_PRODUCT_AREA = 3;
   int COLUMN_PRODUCT_AREA_BUDGET_SALESMAN = 2;
   int COLUMN_BUDGET_OFFER = 4;
   int COLUMN_SALESMAN = 0;
   String MANAGER_NAME = "budgetManager";

   List<Budget> findByYearAndWeek(Integer var1, Integer var2, Integer var3, ProductArea var4, BudgetType var5);

   Budget findByYearAndWeekPrProductAreaGroup(Integer var1, Integer var2, BudgetType var3);

   List<Budget> findByYear(Integer var1, ProductArea var2, BudgetType var3);

   void removeForYearProductArea(Integer var1, ProductArea var2, BudgetType var3);

   void setLabelInfo(JLabel var1);

   Budget findByYearAndWeek(Integer var1, Integer var2, ProductArea var3, BudgetType var4);

   void importBudget(String var1, BudgetType var2) throws ProTransException;

   Budget findSumPrProductAreaAndPeriode(Periode var1, ProductArea var2, BudgetType var3);

   Budget findByYearAndSalesman(Integer var1, String var2, ProductArea var3, BudgetType var4);
}
