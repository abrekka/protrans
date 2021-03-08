/*     */ package no.ugland.utransprod.gui.handlers;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import com.jgoodies.binding.adapter.BasicComponentFactory;
/*     */ import com.jgoodies.binding.adapter.ComboBoxAdapter;
/*     */ import com.toedter.calendar.JYearChooser;
/*     */ import java.awt.Dimension;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.table.TableModel;
/*     */ import no.ugland.utransprod.gui.WindowInterface;
/*     */ import no.ugland.utransprod.gui.edit.AbstractEditView;
/*     */ import no.ugland.utransprod.gui.model.ProcentDoneModel;
/*     */ import no.ugland.utransprod.model.ProcentDone;
/*     */ import no.ugland.utransprod.model.UserType;
/*     */ import no.ugland.utransprod.service.ProcentDoneManager;
/*     */ import no.ugland.utransprod.util.UserUtil;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ import org.jdesktop.swingx.JXTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProcentDoneViewHandler extends DefaultAbstractViewHandler<ProcentDone, ProcentDoneModel> {
/*     */    public ProcentDoneViewHandler(UserType aUserType, ProcentDoneManager procentDoneManager) {
/*  30 */       super("Prosent ferdig", procentDoneManager, aUserType, true);
/*  31 */    }
/*     */ 
/*     */ 
/*     */    public ProcentDone checkSave(ProcentDoneModel procentDoneModel, WindowInterface window) {
/*  35 */       ProcentDone procentDone = (ProcentDone)procentDoneModel.getObject();
/*  36 */       CheckObject checkObject = this.checkSaveObject((ProcentDoneModel)procentDoneModel, (PresentationModel)null, window);
/*     */ 
/*     */ 
/*  39 */       if (checkObject != null && checkObject.getMsg() != null) {
/*  40 */          if (this.handleSaveCheckObject(window, checkObject)) {
/*  41 */             procentDone = (ProcentDone)checkObject.getRefObject();
/*     */          } else {
/*  43 */             procentDone = null;
/*     */          }      }
/*     */ 
/*  46 */       return procentDone;
/*     */    }
/*     */ 
/*     */    public JYearChooser getYearChooser(PresentationModel presentationModel) {
/*  50 */       JYearChooser yearChooser = Util.createYearChooser(presentationModel.getBufferedModel("procentDoneYear"));
/*     */ 
/*  52 */       yearChooser.setName("YearChooser");
/*  53 */       return yearChooser;
/*     */    }
/*     */ 
/*     */ 
/*     */    public JComboBox getComboBoxWeek(PresentationModel presentationModel) {
/*  58 */       JComboBox comboBox = new JComboBox(new ComboBoxAdapter(Util.getWeeks(), presentationModel.getModel("procentDoneWeek")));
/*     */ 
/*     */ 
/*  61 */       comboBox.setName("ComboBoxWeek");
/*  62 */       comboBox.setSelectedItem(Util.getCurrentWeek());
/*  63 */       return comboBox;
/*     */    }
/*     */ 
/*     */    public JTextField getTextFieldProcent(PresentationModel presentationModel) {
/*  67 */       JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel("procentString"));
/*     */ 
/*     */ 
/*  70 */       textField.setName("TextFieldProcent");
/*  71 */       return textField;
/*     */    }
/*     */ 
/*     */    public JTextArea getTextAreaComment(PresentationModel presentationModel) {
/*  75 */       JTextArea textArea = BasicComponentFactory.createTextArea(presentationModel.getBufferedModel("procentDoneComment"));
/*     */ 
/*     */ 
/*  78 */       textArea.setName("TextAreaProcentDoneComment");
/*  79 */       return textArea;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public CheckObject checkDeleteObject(ProcentDone object) {
/*  87 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public CheckObject checkSaveObject(ProcentDoneModel object, PresentationModel presentationModel, WindowInterface window) {
/*  98 */       String warning = null;
/*  99 */       ProcentDone procentDone = (ProcentDone)object.getObject();
/* 100 */       if (object.getProcentDoneId() == null) {
/* 101 */          procentDone = ((ProcentDoneManager)this.overviewManager).findByYearWeekOrder(object.getProcentDoneYear(), object.getProcentDoneWeek(), object.getOrder());
/*     */ 
/*     */ 
/*     */ 
/* 105 */          if (procentDone != null) {
/* 106 */             warning = "Det finnes allerede en prosent for denne uken!";
/*     */          }      }
/*     */ 
/* 109 */       return new CheckObject(warning, true, procentDone);
/*     */    }
/*     */ 
/*     */ 
/*     */    public String getAddRemoveString() {
/* 114 */       return "prosent ferdig";
/*     */    }
/*     */ 
/*     */ 
/*     */    public String getClassName() {
/* 119 */       return "ProcentDone";
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    protected AbstractEditView<ProcentDoneModel, ProcentDone> getEditView(AbstractViewHandler<ProcentDone, ProcentDoneModel> handler, ProcentDone object, boolean searching) {
/* 126 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public ProcentDone getNewObject() {
/* 131 */       return new ProcentDone();
/*     */    }
/*     */ 
/*     */ 
/*     */    public TableModel getTableModel(WindowInterface window) {
/* 136 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public String getTableWidth() {
/* 141 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public String getTitle() {
/* 146 */       return "Prosent ferdig";
/*     */    }
/*     */ 
/*     */ 
/*     */    public Dimension getWindowSize() {
/* 151 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public Boolean hasWriteAccess() {
/* 156 */       return UserUtil.hasWriteAccess(this.userType, "Prosent ferdig");
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setColumnWidth(JXTable table) {
/* 162 */    }
/*     */ }
