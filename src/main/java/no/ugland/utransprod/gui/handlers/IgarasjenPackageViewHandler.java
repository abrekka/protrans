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
/*    */ public class IgarasjenPackageViewHandler extends PackageViewHandler {
/*    */    public IgarasjenPackageViewHandler(ApplyListInterface<PackableListItem> packageInterface, Login login, String mainArticleName, ManagerRepository managerRepository, DeviationViewHandlerFactory deviationViewHandlerFactory) {
/* 15 */       super(login, managerRepository, deviationViewHandlerFactory, packageInterface, "Pakking av igarasjen", TableEnum.TABLEPACKAGEIGARASJEN, mainArticleName);
/*    */ 
/*    */ 
/* 18 */    }
/*    */ 
/*    */ 
/*    */    void initColumnWidthExt() {
/* 22 */       DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
/* 23 */       tableCellRenderer.setHorizontalAlignment(0);
/* 24 */       this.table.getColumnExt(3).setPreferredWidth(200);
/*    */ 
/* 26 */       this.table.getColumnExt(8).setCellRenderer(tableCellRenderer);
/* 27 */    }
/*    */ }
