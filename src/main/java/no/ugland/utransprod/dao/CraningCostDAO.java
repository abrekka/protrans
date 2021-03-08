
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.CraningCost;

public interface CraningCostDAO extends DAO<CraningCost> {
   CraningCost findByRuleId(String var1);
}
