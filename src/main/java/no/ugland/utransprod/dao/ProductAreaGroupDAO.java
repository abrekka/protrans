
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.ProductAreaGroup;

public interface ProductAreaGroupDAO extends DAO<ProductAreaGroup> {
   ProductAreaGroup findByName(String var1);
}
