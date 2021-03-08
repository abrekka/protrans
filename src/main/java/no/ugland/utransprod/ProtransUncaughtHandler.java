/*    */ package no.ugland.utransprod;
/*    */ 
/*    */ import java.lang.Thread.UncaughtExceptionHandler;
/*    */ import org.apache.log4j.Logger;
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
/*    */ 
/*    */ 
/*    */ public class ProtransUncaughtHandler implements UncaughtExceptionHandler {
/*    */    private static final Logger logger = Logger.getLogger(ProtransUncaughtHandler.class);
/*    */ 
/*    */    public void uncaughtException(Thread t, Throwable e) {
/* 26 */       logger.error(String.format("Feil som ikke er håndtert, bruker: %s", System.getProperty("user.name")), e);
/*    */ 
/* 28 */    }
/*    */ }
