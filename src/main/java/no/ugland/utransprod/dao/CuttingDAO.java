
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.Cutting;
import no.ugland.utransprod.model.Order;

public interface CuttingDAO extends DAO<Cutting> {
   Cutting findByOrder(Order var1);
}
