/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import com.jgoodies.binding.adapter.BasicComponentFactory;
/*    */ import com.jgoodies.forms.builder.PanelBuilder;
/*    */ import com.jgoodies.forms.layout.CellConstraints;
/*    */ import com.jgoodies.forms.layout.FormLayout;
/*    */ import java.awt.Dimension;
/*    */ import javax.swing.JCheckBox;
/*    */ import javax.swing.JPanel;
/*    */ import no.ugland.utransprod.gui.WindowInterface;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportEnum;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportSettingSales;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExcelReportViewHandlerSales extends ExcelReportViewHandler {
/*    */    private JCheckBox checkBoxSalesMan;
/*    */ 
/*    */    public ExcelReportViewHandlerSales(ExcelReportEnum excelReportType) {
/* 22 */       super(excelReportType, new Dimension(320, 130));
/* 23 */       this.presentationModel = new PresentationModel(new ExcelReportSettingSales(excelReportType));
/* 24 */    }
/*    */ 
/*    */    public JPanel buildConstraintPanel(WindowInterface window, boolean addEmpty) {
/* 27 */       this.initComponents(window, addEmpty);
/* 28 */       FormLayout layout = new FormLayout("p,3dlu,30dlu,3dlu,p:grow,3dlu,30dlu,3dlu,p,3dlu,30dlu,10dlu", "p,3dlu,p,3dlu,p");
/*    */ 
/*    */ 
/* 31 */       PanelBuilder builder = new PanelBuilder(layout);
/*    */ 
/* 33 */       CellConstraints cc = new CellConstraints();
/*    */ 
/* 35 */       if (this.excelReportEnum.useFrom()) {
/* 36 */          builder.addLabel("År:", cc.xy(1, 1));
/* 37 */          builder.add(this.yearChooser, cc.xy(3, 1));
/* 38 */          if (this.excelReportEnum.useTo()) {
/* 39 */             builder.addLabel("Fra uke:", cc.xy(5, 1));
/* 40 */             builder.addLabel("Til uke:", cc.xy(9, 1));
/* 41 */             builder.add(this.comboBoxWeekTo, cc.xy(11, 1));
/*    */          } else {
/* 43 */             builder.addLabel("Uke:", cc.xy(5, 1));
/*    */          }
/* 45 */          builder.add(this.comboBoxWeekFrom, cc.xy(7, 1));
/*    */       }
/*    */ 
/* 48 */       if (this.comboBoxReportType != null) {
/* 49 */          builder.addLabel("Rapport:", cc.xyw(1, 3, 3));
/* 50 */          builder.add(this.comboBoxReportType, cc.xyw(5, 3, 7));
/*    */       }
/* 52 */       if (this.excelReportEnum.useProductArea()) {
/* 53 */          builder.addLabel("Produktområde:", cc.xyw(1, 5, 5));
/* 54 */          builder.add(this.comboBoxProductArea, cc.xyw(6, 5, 4));
/*    */       }
/* 56 */       builder.add(this.checkBoxSalesMan, cc.xyw(11, 5, 2));
/*    */ 
/* 58 */       return builder.getPanel();
/*    */    }
/*    */ 
/*    */ 
/*    */    protected void initComponents(WindowInterface window, boolean addEmpty) {
/* 63 */       super.initComponents(window, addEmpty);
/* 64 */       this.checkBoxSalesMan = this.getCheckBoxSalesman();
/* 65 */    }
/*    */ 
/*    */    private JCheckBox getCheckBoxSalesman() {
/* 68 */       JCheckBox checkBox = BasicComponentFactory.createCheckBox(this.presentationModel.getModel("salesman"), "Selger");
/*    */ 
/* 70 */       checkBox.setName("CheckBoxSalesman");
/* 71 */       return checkBox;
/*    */    }
/*    */ }
