
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProcentDone;

public interface ProcentDoneDAO extends DAO<ProcentDone> {
   ProcentDone findByYearWeekOrder(Integer var1, Integer var2, Order var3);
}
