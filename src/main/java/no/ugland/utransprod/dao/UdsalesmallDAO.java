
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.Udsalesmall;

public interface UdsalesmallDAO extends DAO<Udsalesmall> {
   Udsalesmall findByOrderNr(String var1);
}
