
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.DokumentV;
import no.ugland.utransprod.model.DrawerV;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.DrawerGroup;

public interface DokumentVDAO extends DAO<DokumentV> {

	List<DokumentV> finnDokumenter(String orderNr);

}
