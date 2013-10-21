package no.ugland.utransprod.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Base class for Business Services - use this class for utility methods and
 * generic CRUD methods.
 * <p>
 * <a href="BaseManager.java.html"><i>View Source</i></a>
 * </p>
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseManagerImpl {

    private static boolean test = false;

    private static String contextPath = "classpath*:/prod/applicationContext*.xml";

    static ApplicationContext applicationContext = null;

    /**
     * Initierer klasse.
     */
    private static ApplicationContext initApplicationContext() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(new String[] { contextPath });
        }
        return applicationContext;
    }

    /**
     * @return context
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext != null?applicationContext:initApplicationContext();
    }

    /**
     * @return Returns the test.
     */
    public static boolean isTest() {
        return test;
    }

    /**
     * @param isTest
     *            The test to set.
     */
    public static void setTest(final boolean isTest) {
        BaseManagerImpl.test = isTest;
    }

}
