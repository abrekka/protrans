/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import com.google.inject.Inject;
/*    */ import no.ugland.utransprod.gui.Login;
/*    */ import no.ugland.utransprod.model.Deviation;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.service.ManagerRepository;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeviationViewHandlerFactoryImpl implements DeviationViewHandlerFactory {
/*    */    private Login login;
/*    */    private ManagerRepository managerRepository;
/*    */    private PreventiveActionViewHandler preventiveActionViewHandler;
/*    */ 
/*    */    @Inject
/*    */    public DeviationViewHandlerFactoryImpl(Login aLogin, ManagerRepository aManagerRepository, PreventiveActionViewHandler aPreventiveActionViewHandler) {
/* 23 */       this.preventiveActionViewHandler = aPreventiveActionViewHandler;
/* 24 */       this.login = aLogin;
/* 25 */       this.managerRepository = aManagerRepository;
/* 26 */    }
/*    */ 
/*    */ 
/*    */    public DeviationViewHandler2 create(Order aOrder, boolean doSeAll, boolean forOrderInfo, boolean isForRegisterNew, Deviation notDisplayDeviation, boolean isDeviationTableEditable, boolean brukOrdrelinjelinjer) {
/* 30 */       return new DeviationViewHandler2(this.login, this.managerRepository, this.preventiveActionViewHandler, aOrder, doSeAll, forOrderInfo, isForRegisterNew, notDisplayDeviation, isDeviationTableEditable, brukOrdrelinjelinjer);
/*    */    }
/*    */ }
