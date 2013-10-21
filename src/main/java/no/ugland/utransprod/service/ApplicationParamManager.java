package no.ugland.utransprod.service;

import java.util.List;
import java.util.Map;

import no.ugland.utransprod.ProTransRuntimeException;
import no.ugland.utransprod.model.ApplicationParam;

/**
 * Interface for serviceklasse mot tabell APPLICTION_PARAM.
 * @author atle.brekka
 */
public interface ApplicationParamManager extends
        OverviewManager<ApplicationParam> {
    String MANAGER_NAME = "applicationParamManager";
	/**
     * Finner parameter med gitt navn.
     * @param paramName
     * @return parameter
     * @throws ProTransRuntimeException
     *             dersom parameter ikke finnes
     */
    String findByName(String paramName);

    /**
     * Finner parameter med gitt navn, og gir ikke feilmelding dersom parameter
     * ikke finnes bare en null verdi.
     * @param paramName
     * @return parameter
     */
    String findByNameSilent(String paramName);

    /**
     * Finner parametre med oppsett av kollier.
     * @return oppsett av kollier
     */
    Map<String, String> getColliSetup();

    /**
     * Finner paramatre basert på eksempel.
     * @param param
     * @return parametre
     */
    List<ApplicationParam> findByExampleLike(ApplicationParam param);

    /**
     * Lagrer parameter.
     * @param param
     */
    void saveApplicationParam(ApplicationParam param);
    ApplicationParam findParam(String paramName);
}
