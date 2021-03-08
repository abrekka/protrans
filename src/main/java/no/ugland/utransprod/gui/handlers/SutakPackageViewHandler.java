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
/*    */ public class SutakPackageViewHandler extends PackageViewHandler {
/*    */    public SutakPackageViewHandler(ApplyListInterface<PackableListItem> packageInterface, Login login, String mainArticleName, ManagerRepository managerRepository, DeviationViewHandlerFactory deviationViewHandlerFactory) {
/* 16 */       super(login, managerRepository, deviationViewHandlerFactory, packageInterface, "Pakking av sutaksplater", TableEnum.TABLEPACKAGESUTAK, mainArticleName);
/*    */ 
/*    */ 
/* 19 */    }
/*    */ 
/*    */ 
/*    */    void initColumnWidthExt() {
/* 23 */       DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
/* 24 */       tableCellRenderer.setHorizontalAlignment(0);
/* 25 */       this.table.getColumnExt(3).setPreferredWidth(200);
/*    */ 
/* 27 */       this.table.getColumnExt(8).setCellRenderer(tableCellRenderer);
/* 28 */    }
/*    */ }
