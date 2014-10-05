package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.DelAlt;

public interface DelAltManager {
    public static final String MANAGER_NAME = "delAltManager";

    List<DelAlt> finnForProdno(String string);
}
