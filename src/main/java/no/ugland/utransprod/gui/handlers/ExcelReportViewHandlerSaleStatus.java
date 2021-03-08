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
/*    */ import no.ugland.utransprod.util.Util;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportEnum;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportSettingSaleStatus;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExcelReportViewHandlerSaleStatus extends ExcelReportViewHandler {
/*    */    private JCheckBox checkBoxOrder;
/*    */    private JCheckBox checkBoxOffer;
/*    */ 
/*    */    public ExcelReportViewHandlerSaleStatus() {
/* 27 */       super(ExcelReportEnum.SALE_STATUS_REPORT, new Dimension(320, 130));
/* 28 */       this.presentationModel = new PresentationModel(new ExcelReportSettingSaleStatus());
/* 29 */    }
/*    */ 
/*    */    public JPanel buildConstraintPanel(WindowInterface window, boolean addEmpty) {
/* 32 */       this.initComponents(window, addEmpty);
/* 33 */       FormLayout layout = new FormLayout("p,3dlu,70dlu", "p,3dlu,p");
/* 34 */       PanelBuilder builder = new PanelBuilder(layout);
/*    */ 
/* 36 */       CellConstraints cc = new CellConstraints();
/*    */ 
/* 38 */       builder.add(this.checkBoxOffer, cc.xy(1, 1));
/* 39 */       builder.add(this.checkBoxOrder, cc.xy(3, 1));
/*    */ 
/* 41 */       builder.addLabel("Produktområde:", cc.xy(1, 3));
/* 42 */       builder.add(this.comboBoxProductArea, cc.xy(3, 3));
/*    */ 
/* 44 */       return builder.getPanel();
/*    */    }
/*    */ 
/*    */ 
/*    */    protected void initComponents(WindowInterface window, boolean addEmpty) {
/* 49 */       super.initComponents(window, addEmpty);
/* 50 */       this.checkBoxOffer = this.getCheckBoxOffer();
/* 51 */       this.checkBoxOrder = this.getCheckBoxOrder();
/* 52 */    }
/*    */ 
/*    */    private JCheckBox getCheckBoxOffer() {
/* 55 */       JCheckBox checkBox = BasicComponentFactory.createCheckBox(this.presentationModel.getModel("useOffer"), "Tilbud");
/*    */ 
/* 57 */       checkBox.setName("CheckBoxOffer");
/* 58 */       return checkBox;
/*    */    }
/*    */ 
/*    */    private JCheckBox getCheckBoxOrder() {
/* 62 */       JCheckBox checkBox = BasicComponentFactory.createCheckBox(this.presentationModel.getModel("useOrder"), "Ordre");
/*    */ 
/* 64 */       checkBox.setName("CheckBoxOrder");
/* 65 */       return checkBox;
/*    */    }
/*    */ 
/*    */    protected boolean checkParameters(ExcelReportSetting setting, WindowInterface window) {
/* 69 */       String errorString = !((ExcelReportSettingSaleStatus)setting).getUseOffer() && !((ExcelReportSettingSaleStatus)setting).getUseOrder() ? "Det må velges tilbud,ordre eller begge" : null;
/*    */ 
/*    */ 
/*    */ 
/* 73 */       if (errorString != null) {
/* 74 */          Util.showErrorDialog(window, "Rapportparametre", errorString);
/*    */       }
/* 76 */       return errorString == null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public Dimension getWindowSize() {
/* 85 */       return new Dimension(250, 130);
/*    */    }
/*    */ }
