/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BaseManagerImpl {
/*    */    private static boolean test = false;
/*    */    private static String contextPath = "classpath*:/prod/applicationContext*.xml";
/*    */    static ApplicationContext applicationContext = null;
/*    */ 
/*    */    private static ApplicationContext initApplicationContext() {
/* 26 */       if (applicationContext == null) {
/* 27 */          applicationContext = new ClassPathXmlApplicationContext(new String[]{contextPath});
/*    */       }
/* 29 */       return applicationContext;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public static ApplicationContext getApplicationContext() {
/* 36 */       return applicationContext != null ? applicationContext : initApplicationContext();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public static boolean isTest() {
/* 43 */       return test;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public static void setTest(boolean isTest) {
/* 51 */       test = isTest;
/* 52 */    }
/*    */ }
