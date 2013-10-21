package no.ugland.utransprod.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.ProTransRuntimeException;
import no.ugland.utransprod.model.ApplicationParam;
import no.ugland.utransprod.service.ApplicationParamManager;

/**
 * Hjelpeklasse til å finne frem parametre.
 * @author atle.brekka
 */
public final class ApplicationParamUtil {
    private static ApplicationParamManager applicationParamManager;

    private static List<String> notPackageList;
    private static Map<String, String> colliSetup = null;

    private ApplicationParamUtil() {

    }

    public static void setApplicationParamManger(ApplicationParamManager aManager) {
        applicationParamManager = aManager;
    }

    /**
     * Initierer.
     */
    private static void init() {
        if (applicationParamManager == null) {
            applicationParamManager = (ApplicationParamManager) ModelUtil.getBean("applicationParamManager");
        }
    }

    public static ApplicationParam findParam(String paramName) {
        init();
        return applicationParamManager.findParam(paramName);
    }

    /**
     * Finner parameter med gitt navn.
     * @param paramName
     * @return parameterverdi
     * @throws ProTransRuntimeException
     */
    public static String findParamByName(final String paramName) {
        init();
        return applicationParamManager.findByName(paramName);
    }

    /**
     * Finner parameter med gitt navn uten å kaste exception dersom parameter
     * ikke finnes.
     * @param paramName
     * @return parameterverdi eller null dersom parameter ikke finnes
     * @throws ProTransRuntimeException
     */
    public static String findParamByNameSilent(final String paramName) {
        init();
        return applicationParamManager.findByNameSilent(paramName);
    }

    /**
     * Henter oppsett for kollier.
     * @return oppsett for kollier
     */
    public static Map<String, String> getColliSetup() {
        if (colliSetup == null) {
            List<ApplicationParam> params = getColliSetupParams();

            colliSetup = new Hashtable<String, String>();
            String paramValue;
            String[] paramValueSplit;
            for (ApplicationParam param : params) {
                paramValue = param.getParamValue();
                paramValueSplit = paramValue.split(";");
                colliSetup.put(paramValueSplit[0], paramValueSplit[1]);
            }
        }
        return colliSetup;
    }

    public static List<ApplicationParam> getColliSetupParams() {
        init();
        ApplicationParam example = new ApplicationParam();
        example.setParamName("kolli__");
        return applicationParamManager.findByExampleLike(example);
    }
    public static List<ApplicationParam> getTakstolArticleSetupParams() {
        init();
        ApplicationParam example = new ApplicationParam();
        example.setParamName("takstol_artikkel__");
        return applicationParamManager.findByExampleLike(example);
    }

    /**
     * Finner parametre for en gitt type.
     * @param type
     * @return parametre
     */
    public static List<String> findParamListByType(final String type) {
        List<String> paramList = new ArrayList<String>();
        init();
        ApplicationParam example = new ApplicationParam();
        example.setParamType(type);
        List<ApplicationParam> params = applicationParamManager.findByExampleLike(example);

        if (params != null) {
            for (ApplicationParam param : params) {
                paramList.add(param.getParamValue());
            }
        }
        return paramList;
    }

    /**
     * Henter mailoppsett.
     * @return mailoppsett
     */
    public static Map<String, ApplicationParam> getMailSetup() {
        init();
        ApplicationParam example = new ApplicationParam();
        example.setParamName("mail_%");
        List<ApplicationParam> params = applicationParamManager.findByExampleLike(example);

        Map<String, ApplicationParam> mailSetup = new Hashtable<String, ApplicationParam>();
        if (params != null) {
            for (ApplicationParam param : params) {
                mailSetup.put(param.getParamName(), param);
            }
        }
        return mailSetup;
    }

    /**
     * Henter parameter for avviksansavarlig.
     * @return parameter
     */
    public static ApplicationParam getDeviationManager() {
        init();
        ApplicationParam example = new ApplicationParam();
        example.setParamName("deviation_manager");
        List<ApplicationParam> params = applicationParamManager.findByExampleLike(example);
        if (params != null && params.size() == 1) {
            return params.get(0);
        }
        return null;
    }
    public static ApplicationParam getHmsManager() {
        init();
        ApplicationParam example = new ApplicationParam();
        example.setParamName("hms_leder");
        List<ApplicationParam> params = applicationParamManager.findByExampleLike(example);
        if (params != null && params.size() == 1) {
            return params.get(0);
        }
        return null;
    }

    /**
     * Henter navn på avviksansvarlig.
     * @return navn
     */
    public static String getDeviationManagerName() {
        ApplicationParam deviationManager = getDeviationManager();
        if (deviationManager != null) {
            return deviationManager.getParamValue();
        }
        return null;
    }

    /**
     * Henter default avvikskostnader.
     * @return navn på kostnader
     */
    public static List<String> getDefaultDeviationCosts() {
        String defaultCostsString = ApplicationParamUtil.findParamByName("default_avvik_kostnader");
        String[] defaultCosts = defaultCostsString.split(";");

        return Arrays.asList(defaultCosts);
    }

    public static List<String> getNotPackageList() {
        if (notPackageList == null) {
            String notPackageString = ApplicationParamUtil.findParamByName("not_package");
            String[] split = notPackageString.split(";");
            if (split != null) {
                notPackageList = new ArrayList<String>(Arrays.asList(split));
            }
        }
        return notPackageList;
    }

    public static ApplicationParam getNotPackageParam() {
        init();
        ApplicationParam example = new ApplicationParam();
        example.setParamName("not_package");
        List<ApplicationParam> list=applicationParamManager.findByExampleLike(example);
        return list!=null&&list.size()==1?list.get(0):null;
    }
}
