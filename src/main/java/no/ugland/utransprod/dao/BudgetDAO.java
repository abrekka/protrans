
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.util.Periode;

public interface BudgetDAO extends DAO<Budget> {
   List<Budget> findAll();

   void refreshProductionBudget(Budget var1);

   List<Budget> findByYearAndWeek(Integer var1, Integer var2, Integer var3, ProductArea var4, BudgetType var5);

   Budget findByYearAndWeek(Integer var1, Integer var2, ProductArea var3, BudgetType var4);

   Budget findByYearAndWeekPrProductAreaGroup(Integer var1, Integer var2, ProductAreaGroup var3, BudgetType var4);

   Budget findByYearAndWeek(Integer var1, Integer var2, BudgetType var3);

   List<Budget> findByYear(Integer var1, ProductArea var2, BudgetType var3);

   void removeForYearProductArea(Integer var1, ProductArea var2, BudgetType var3);

   Budget findSumPrProductAreaAndPeriode(Periode var1, ProductArea var2, BudgetType var3);

   Budget findByYearAndSalesman(Integer var1, String var2, ProductArea var3, BudgetType var4);
}
