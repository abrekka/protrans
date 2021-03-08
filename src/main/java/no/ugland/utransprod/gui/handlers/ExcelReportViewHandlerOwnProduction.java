/*     */ package no.ugland.utransprod.gui.handlers;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import com.jgoodies.binding.adapter.ComboBoxAdapter;
/*     */ import com.jgoodies.forms.builder.PanelBuilder;
/*     */ import com.jgoodies.forms.layout.CellConstraints;
/*     */ import com.jgoodies.forms.layout.FormLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.util.List;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JPanel;
/*     */ import no.ugland.utransprod.gui.WindowInterface;
/*     */ import no.ugland.utransprod.model.ProductAreaGroup;
/*     */ import no.ugland.utransprod.service.ProductAreaGroupManager;
/*     */ import no.ugland.utransprod.util.ModelUtil;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportEnum;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportSettingOwnProduction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExcelReportViewHandlerOwnProduction extends ExcelReportViewHandler {
/*     */    private JComboBox comboBoxProductAreaGroup;
/*     */    private List<ProductAreaGroup> areas;
/*     */ 
/*     */    public ExcelReportViewHandlerOwnProduction(ExcelReportEnum excelReportType) {
/*  32 */       super(excelReportType, new Dimension(250, 130));
/*  33 */       this.presentationModel = new PresentationModel(new ExcelReportSettingOwnProduction(excelReportType));
/*     */ 
/*  35 */       ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager)ModelUtil.getBean("productAreaGroupManager");
/*     */ 
/*  37 */       this.areas = productAreaGroupManager.findAll();
/*  38 */    }
/*     */ 
/*     */    public JPanel buildConstraintPanel(WindowInterface window, boolean addEmpty) {
/*  41 */       this.initComponents(window, addEmpty);
/*  42 */       FormLayout layout = new FormLayout("p,3dlu,30dlu,3dlu,p:grow,3dlu,30dlu,3dlu,p,3dlu,30dlu", "p,3dlu,p,3dlu,p");
/*     */ 
/*  44 */       PanelBuilder builder = new PanelBuilder(layout);
/*     */ 
/*  46 */       CellConstraints cc = new CellConstraints();
/*     */ 
/*  48 */       if (this.excelReportEnum.useFrom()) {
/*  49 */          builder.addLabel("År:", cc.xy(1, 1));
/*  50 */          builder.add(this.yearChooser, cc.xy(3, 1));
/*  51 */          if (this.excelReportEnum.useTo()) {
/*  52 */             builder.addLabel("Fra uke:", cc.xy(5, 1));
/*  53 */             builder.addLabel("Til uke:", cc.xy(9, 1));
/*  54 */             builder.add(this.comboBoxWeekTo, cc.xy(11, 1));
/*     */          } else {
/*  56 */             builder.addLabel("Uke:", cc.xy(5, 1));
/*     */          }
/*  58 */          builder.add(this.comboBoxWeekFrom, cc.xy(7, 1));
/*     */       }
/*     */ 
/*  61 */       if (this.comboBoxReportType != null) {
/*  62 */          builder.addLabel("Rapport:", cc.xyw(1, 3, 3));
/*  63 */          builder.add(this.comboBoxReportType, cc.xyw(5, 3, 7));
/*     */       }
/*  65 */       builder.addLabel("Produktområde:", cc.xyw(1, 5, 5));
/*  66 */       builder.add(this.comboBoxProductAreaGroup, cc.xyw(6, 5, 6));
/*     */ 
/*  68 */       return builder.getPanel();
/*     */    }
/*     */ 
/*     */ 
/*     */    protected void initComponents(WindowInterface window, boolean addEmpty) {
/*  73 */       super.initComponents(window, addEmpty);
/*  74 */       this.comboBoxProductAreaGroup = this.getComboBoxProductAreaGroup();
/*  75 */    }
/*     */ 
/*     */    private JComboBox getComboBoxProductAreaGroup() {
/*  78 */       return new JComboBox(new ComboBoxAdapter(this.areas, this.presentationModel.getModel("productAreaGroup")));
/*     */    }
/*     */ 
/*     */ 
/*     */    protected boolean checkParameters(ExcelReportSetting setting, WindowInterface window) {
/*  83 */       String errorString = null;
/*  84 */       if (this.excelReportEnum.useFrom() && setting.getWeekFrom() == null) {
/*  85 */          errorString = "Det må velges fra uke";
/*  86 */       } else if (this.excelReportEnum.useTo() && setting.getWeekTo() == null) {
/*  87 */          errorString = "Det må velges til uke";
/*  88 */       } else if (((ExcelReportSettingOwnProduction)setting).getProductAreaGroup() == null) {
/*  89 */          errorString = "Det må velges produktområde";
/*     */       }
/*     */ 
/*  92 */       if (errorString != null) {
/*  93 */          Util.showErrorDialog(window, "Rapportparametre", errorString);
/*  94 */          return false;
/*     */       } else {
/*  96 */          return true;
/*     */       }
/*     */    }
/*     */ }
