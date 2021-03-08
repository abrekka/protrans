
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.model.Order;

public interface ExternalOrderDAO extends DAO<ExternalOrder> {
   void refreshObject(ExternalOrder var1);

   List<ExternalOrder> findByOrder(Order var1);
}
