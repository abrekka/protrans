/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import no.ugland.utransprod.gui.Login;
/*    */ import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
/*    */ import no.ugland.utransprod.gui.model.ApplyListInterface;
/*    */ import no.ugland.utransprod.model.ArticleType;
/*    */ import no.ugland.utransprod.model.Produceable;
/*    */ import no.ugland.utransprod.service.ManagerRepository;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaksteinSkarpnesViewHandler extends ProductionViewHandler {
/*    */    public TaksteinSkarpnesViewHandler(ApplyListInterface<Produceable> productionInterface, Login login, ManagerRepository managerRepository, DeviationViewHandlerFactory deviationViewHandlerFactory, SetProductionUnitActionFactory setProductionUnitActionFactory, ArticleType articleType) {
/* 18 */       super(productionInterface, "Takstein", login, "bestilt", (String)null, TableEnum.TABLETAKSTEIN, articleType, managerRepository, deviationViewHandlerFactory, setProductionUnitActionFactory);
/*    */ 
/* 20 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    void initColumnWidthExt() {
/* 25 */       this.table.getColumnExt("Transport").setPreferredWidth(100);
/*    */ 
/* 27 */       this.table.getColumnExt("Ordre").setPreferredWidth(200);
/* 28 */       this.table.getColumnExt("Prod.dato").setPreferredWidth(60);
/*    */ 
/* 30 */       this.table.getColumnExt("Antall").setPreferredWidth(50);
/*    */ 
/*    */ 
/* 33 */       this.table.getColumnExt("Spesifikasjon").setPreferredWidth(400);
/*    */ 
/* 35 */    }
/*    */ }
