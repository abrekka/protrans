package no.ugland.utransprod.model;

import java.util.Map;

import com.google.common.collect.Maps;

public enum Packagetype {
    ALLE("Alle", 0), VEGG("Vegg", 10), TAKSTOL_GAVL("Takstol/gavl", 20), PAKK("Pakk", 30);

    private String beskrivelse;
    private Integer verdi;
    private static Map<Integer, Packagetype> PACKAGETYPES = Maps.newHashMap();

    static {
	for (Packagetype packagetype : Packagetype.values()) {
	    PACKAGETYPES.put(packagetype.getVerdi(), packagetype);
	}
    }

    private Packagetype(String beskrivelse, Integer verdi) {
	this.beskrivelse = beskrivelse;
	this.verdi = verdi;
    }

    public String toString() {
	return beskrivelse;
    }

    public Integer getVerdi() {
	return verdi;
    }

    public static Packagetype getPackageType(Integer packageType) {
	if (packageType == null) {
	    return ALLE;
	}
	return PACKAGETYPES.get(packageType);
    };
}
