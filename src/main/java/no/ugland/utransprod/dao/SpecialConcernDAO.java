
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.SpecialConcern;

public interface SpecialConcernDAO extends DAO<SpecialConcern> {
   SpecialConcern findByDescription(String var1) throws ProTransException;

   void removeAll();
}
