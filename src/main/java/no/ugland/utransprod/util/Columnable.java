package no.ugland.utransprod.util;

/**
 * Interface for klasser som skal kunne brukes til dynamiske kolonner i tabell.
 * @author atle.brekka
 */
public interface Columnable {
    /**
     * Henter kolonnenavn.
     * @return kolonnenavn
     */
    String[] getColumnNames();

    /**
     * Henter verdi for gitt kolonne.
     * @param column
     * @return verdi
     */
    Object getValueForColumn(Integer column);
}
