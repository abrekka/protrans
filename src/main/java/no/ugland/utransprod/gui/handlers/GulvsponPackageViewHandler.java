/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import javax.swing.table.DefaultTableCellRenderer;
/*    */ import no.ugland.utransprod.gui.Login;
/*    */ import no.ugland.utransprod.gui.model.ApplyListInterface;
/*    */ import no.ugland.utransprod.model.PackableListItem;
/*    */ import no.ugland.utransprod.service.ManagerRepository;
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
/*    */ public class GulvsponPackageViewHandler extends PackageViewHandler {
/*    */    public GulvsponPackageViewHandler(ApplyListInterface<PackableListItem> packageInterface, Login login, String mainArticleName, ManagerRepository managerRepository, DeviationViewHandlerFactory deviationViewHandlerFactory) {
/* 27 */       super(login, managerRepository, deviationViewHandlerFactory, packageInterface, "Pakking av gulvspon", TableEnum.TABLEPACKAGEGULVSPON, mainArticleName);
/*    */ 
/*    */ 
/* 30 */    }
/*    */ 
/*    */ 
/*    */    void initColumnWidthExt() {
/* 34 */       DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
/* 35 */       tableCellRenderer.setHorizontalAlignment(0);
/* 36 */       this.table.getColumnExt(3).setPreferredWidth(100);
/* 37 */       this.table.getColumnExt(9).setPreferredWidth(100);
/* 38 */       this.table.getColumnExt(8).setCellRenderer(tableCellRenderer);
/* 39 */    }
/*    */ }
