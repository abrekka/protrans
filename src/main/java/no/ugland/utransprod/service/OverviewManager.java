package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.ProTransException;

/**
 * Interface for manger som skal brukes i visning av objekter
 * @author atle.brekka
 * @param <E>
 */
public interface OverviewManager<E>  extends Manager<E>{
    /**
     * Hent alle
     * @return objekter
     */
    List<E> findAll();

    /**
     * Finn basert på eksempel
     * @param object
     * @return objekter
     */
    List<E> findByObject(E object);

    /**
     * Fjern objekt
     * @param object
     */
    void removeObject(E object);

    /**
     * Lagre objekt
     * @param object
     * @throws ProTransException
     */
    void saveObject(E object) throws ProTransException;

    /**
     * Oppdaterer objekt
     * @param object
     */
    void refreshObject(E object);
    //void lazyLoad(Object object,Enum[] enums);
}
