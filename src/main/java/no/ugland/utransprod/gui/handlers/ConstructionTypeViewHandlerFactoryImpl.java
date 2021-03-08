/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import com.google.inject.Inject;
/*    */ import no.ugland.utransprod.gui.Login;
/*    */ import no.ugland.utransprod.service.ManagerRepository;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstructionTypeViewHandlerFactoryImpl implements ConstructionTypeViewHandlerFactory {
/*    */    private Login login;
/*    */    private ManagerRepository managerRepository;
/*    */ 
/*    */    @Inject
/*    */    public ConstructionTypeViewHandlerFactoryImpl(Login aLogin, ManagerRepository aManagerRepository) {
/* 16 */       this.login = aLogin;
/* 17 */       this.managerRepository = aManagerRepository;
/* 18 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public ConstructionTypeViewHandler create(boolean isMasterDialog, boolean masterOverview) {
/* 23 */       return new ConstructionTypeViewHandler(this.login, this.managerRepository, isMasterDialog, masterOverview);
/*    */    }
/*    */ }
