package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.ProTransRuntimeException;
import no.ugland.utransprod.dao.ApplicationParamDAO;
import no.ugland.utransprod.model.ApplicationParam;
import no.ugland.utransprod.service.ApplicationParamManager;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * @author atle.brekka
 */
public class ApplicationParamManagerImpl extends ManagerImpl<ApplicationParam> implements ApplicationParamManager {

    /**
     * @see no.ugland.utransprod.service.ApplicationParamManager#findByName(java.lang.String)
     */
    public final String findByName(final String paramName) {
	ApplicationParam param = ((ApplicationParamDAO) dao).findByName(paramName);
	if (param != null) {
	    return param.getParamValue();
	}
	throw new ProTransRuntimeException("Fant ikke parameter " + paramName);
    }

    /**
     * @see no.ugland.utransprod.service.ApplicationParamManager#getColliSetup()
     */
    public final Multimap<String, String> getColliSetup() {
	ApplicationParam example = new ApplicationParam();
	example.setParamName("kolli_%");
	List<ApplicationParam> params = dao.findByExampleLike(example);
	Multimap<String, String> colliSetup = ArrayListMultimap.create();
	String paramValue;
	String[] paramValueSplit;
	for (ApplicationParam param : params) {
	    paramValue = param.getParamValue();
	    paramValueSplit = paramValue.split(";");
	    colliSetup.put(paramValueSplit[0], paramValueSplit[1]);
	}
	return colliSetup;
    }

    /**
     * @see no.ugland.utransprod.service.ApplicationParamManager#
     *      findByExampleLike(no.ugland.utransprod.model.ApplicationParam)
     */
    public final List<ApplicationParam> findByExampleLike(final ApplicationParam param) {
	return dao.findByExampleLike(param);
    }

    /**
     * @see no.ugland.utransprod.service.ApplicationParamManager#findByNameSilent(java.lang.String)
     */
    public final String findByNameSilent(final String paramName) {
	ApplicationParam param = ((ApplicationParamDAO) dao).findByName(paramName);
	if (param != null) {
	    return param.getParamValue();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    public final List<ApplicationParam> findAll() {
	// return dao.getObjects();
	ApplicationParam example = new ApplicationParam();
	example.setUserAccessible(1);
	return dao.findByExample(example);
    }

    /**
     * @param object
     * @return parametre
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<ApplicationParam> findByObject(final ApplicationParam object) {
	return dao.findByExampleLike(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final ApplicationParam object) {
	((ApplicationParamDAO) dao).refreshObject(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final ApplicationParam object) {
	dao.removeObject(object.getParamId());

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final ApplicationParam object) {
	dao.saveObject(object);

    }

    /**
     * @see no.ugland.utransprod.service.ApplicationParamManager#
     *      saveApplicationParam(no.ugland.utransprod.model.ApplicationParam)
     */
    public final void saveApplicationParam(final ApplicationParam param) {
	dao.saveObject(param);

    }

    public ApplicationParam findParam(String paramName) {
	return ((ApplicationParamDAO) dao).findByName(paramName);
    }

    public void lazyLoad(ApplicationParam object, Enum[] enums) {
    }

    @Override
    protected Serializable getObjectId(ApplicationParam object) {
	return object.getParamId();
    }

    public ApplicationParam merge(ApplicationParam object) {
	return dao.merge(object);
    }

}
