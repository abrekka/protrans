/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.JLabel;
/*    */ import no.ugland.utransprod.ProTransException;
/*    */ import no.ugland.utransprod.gui.WindowInterface;
/*    */ import no.ugland.utransprod.gui.model.BudgetType;
/*    */ import no.ugland.utransprod.service.BudgetManager;
/*    */ import no.ugland.utransprod.util.FileExtensionFilter;
/*    */ import no.ugland.utransprod.util.ModelUtil;
/*    */ import no.ugland.utransprod.util.Threadable;
/*    */ import no.ugland.utransprod.util.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProductionBudgetImportHandler implements Threadable {
/*    */   private Component parentComponent;
/*    */   private WindowInterface waitWindow;
/*    */   private String openDir;
/*    */   private BudgetType budgetType;
/*    */ 
/*    */   public ProductionBudgetImportHandler(Component parent, String aOpenDir, BudgetType aBudgetType) {
/* 24 */      this.budgetType = aBudgetType;
/* 25 */      this.parentComponent = parent;
/* 26 */      this.openDir = aOpenDir;
/* 27 */   }
/*    */ 
/*    */   public void doWhenFinished(Object object) {
/* 30 */      if (object != null) {
/* 31 */         Util.showErrorDialog(this.parentComponent, "Feil", object.toString());
/*    */      } else {
/* 33 */         Util.showMsgDialog(this.parentComponent, "Importert", "Alle budsjettall er importert");
/*    */ 
/*    */      }
/*    */ 
/* 37 */      if (this.waitWindow != null) {
/* 38 */         this.waitWindow.dispose();
/*    */      }
/*    */ 
/* 41 */   }
/*    */ 
/*    */   public Object doWork(Object[] params, JLabel labelInfo) {
/* 44 */      String errorMsg = null;
/*    */      try {
/* 46 */         if (params != null) {
/* 47 */            this.waitWindow = (WindowInterface)params[0];
/*    */         }
/* 49 */         labelInfo.setText("Importerer budsjettall...");
/* 50 */         this.importBudget(labelInfo, this.waitWindow);
/*    */ 
/* 52 */      } catch (ProTransException var5) {
/* 53 */         var5.printStackTrace();
/* 54 */         errorMsg = var5.getMessage();
/*    */      }
/* 56 */      return errorMsg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   public void enableComponents(boolean enable) {
/* 62 */   }
/*    */ 
/*    */   public final void importBudget(JLabel label, WindowInterface window) throws ProTransException {
/* 65 */      BudgetManager productionBudgetManager = (BudgetManager)ModelUtil.getBean("budgetManager");
/*    */ 
/* 67 */      productionBudgetManager.setLabelInfo(label);
/* 68 */      String excelFileName = Util.getFileName(this.parentComponent, new FileExtensionFilter("xls", "Excel"), this.openDir);
/*    */ 
/*    */ 
/* 71 */      if (excelFileName != null) {
/* 72 */         productionBudgetManager.importBudget(excelFileName, this.budgetType);
/*    */      }
/*    */ 
/* 75 */   }
/*    */ }
