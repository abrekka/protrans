package no.ugland.utransprod.dao;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.SpecialConcern;


/**
 * DAO for tabell SPECIAL_CONCERN
 * @author atb
 *
 */
public interface SpecialConcernDAO extends DAO<SpecialConcern>{
    /**
     * Finner spesielt hensyn basert på beskrivelse
     * @param description
     * @return spesielt hensyn
     * @throws ProTransException
     */
    SpecialConcern findByDescription(String description)throws ProTransException;
    /**
     * Fjerner alle spesielle hensyn
     */
    void removeAll();
}
