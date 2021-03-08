/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import no.ugland.utransprod.gui.WindowInterface;
/*    */ import no.ugland.utransprod.model.UserType;
/*    */ import no.ugland.utransprod.service.OverviewManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DefaultAbstractViewHandler<T, E> extends AbstractViewHandler<T, E> {
/*    */    private static final long serialVersionUID = 1L;
/*    */ 
/*    */    public DefaultAbstractViewHandler(String aHeading, OverviewManager<T> aOverviewManager, boolean oneObject, UserType aUserType, boolean useDisposeOnClose) {
/* 15 */       super(aHeading, aOverviewManager, oneObject, aUserType, useDisposeOnClose);
/*    */ 
/* 17 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public DefaultAbstractViewHandler(String aHeading, OverviewManager<T> aOverviewManager, UserType aUserType, boolean useDisposeOnClose) {
/* 22 */       super(aHeading, aOverviewManager, aUserType, useDisposeOnClose);
/* 23 */    }
/*    */ 
/*    */ 
/*    */    final String getAddString() {
/* 27 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    void afterSaveObject(T object, WindowInterface window) {
/* 32 */    }
/*    */ }
