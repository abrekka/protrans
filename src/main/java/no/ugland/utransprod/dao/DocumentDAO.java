
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.Document;
import no.ugland.utransprod.util.Periode;

public interface DocumentDAO extends DAO<Document> {
   List<Integer> getDocumentIdByPeriode(Periode var1);
}
