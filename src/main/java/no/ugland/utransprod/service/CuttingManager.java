package no.ugland.utransprod.service;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.Cutting;

public interface CuttingManager extends Manager<Cutting>{

    public static final String MANAGER_NAME = "cuttingManager";

    void saveCutting(Cutting cutting,Boolean overwrite)throws ProTransException;

    Cutting findByProId(String string);

    void removeCutting(Cutting cutting);

}
