
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.DokumentV;

public interface DokumentVManager {
	String MANAGER_NAME = "dokumentVManager";

	List<DokumentV> finnDokumenter(String orderNr);
}
