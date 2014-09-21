package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.AssemblyV;

public interface AssemblyVManager {

    String MANAGER_NAME = "assemblyVManager";

    List<AssemblyV> findByYear(int aar);

}
