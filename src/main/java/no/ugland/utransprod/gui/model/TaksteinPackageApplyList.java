/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.google.inject.Inject;
/*    */ import no.ugland.utransprod.gui.Login;
/*    */ import no.ugland.utransprod.service.ManagerRepository;
/*    */ import no.ugland.utransprod.service.OrderLineManager;
/*    */ import no.ugland.utransprod.service.VismaFileCreator;
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
/*    */ public class TaksteinPackageApplyList extends PackageApplyList {
/*    */    @Inject
/*    */    public TaksteinPackageApplyList(Login login, OrderLineManager manager, ManagerRepository aManagerRepository) {
/* 24 */       super(login, manager, "Takstein", "Takstein", (ReportEnum)null, (VismaFileCreator)null, aManagerRepository);
/* 25 */    }
/*    */ }
