package no.ugland.utransprod.util;

import java.util.Comparator;

import no.ugland.utransprod.model.PacklistV;

import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * Komparator for pakkliste.
 * @author atle.brekka
 */
public class PacklistVComparator implements Comparator<PacklistV> {
    /**
     * @param packlist1
     * @param packlist2
     * @return int
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public final int compare(final PacklistV packlist1, final PacklistV packlist2) {
        if (packlist1.getLoadingDate() != null
                && packlist2.getLoadingDate() != null) {
            return new CompareToBuilder().append(packlist1.getLoadingDate(),
                    packlist2.getLoadingDate()).toComparison();

        } else if (packlist1.getLoadingDate() != null) {
            return -1;
        } else if (packlist2.getLoadingDate() != null) {
            return 1;
        }
        return 0;
    }
}