/*     */ package no.ugland.utransprod.gui.handlers;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import com.jgoodies.binding.adapter.BasicComponentFactory;
/*     */ import java.awt.Dimension;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableModel;
/*     */ import no.ugland.utransprod.gui.Login;
/*     */ import no.ugland.utransprod.gui.WindowInterface;
/*     */ import no.ugland.utransprod.gui.edit.AbstractEditView;
/*     */ import no.ugland.utransprod.gui.edit.EditCostTypeView;
/*     */ import no.ugland.utransprod.gui.model.CostTypeModel;
/*     */ import no.ugland.utransprod.model.CostType;
/*     */ import no.ugland.utransprod.service.CostTypeManager;
/*     */ import no.ugland.utransprod.util.UserUtil;
/*     */ import org.jdesktop.swingx.JXTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CostTypeViewHandler extends DefaultAbstractViewHandler<CostType, CostTypeModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */ 
/*     */    public CostTypeViewHandler(Login login, CostTypeManager costTypeManager) {
/*  41 */       super("Kostnadstyper", costTypeManager, login.getUserType(), true);
/*  42 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public JTextField getTextFieldName(PresentationModel presentationModel) {
/*  51 */       JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel("costTypeName"));
/*     */ 
/*     */ 
/*  54 */       textField.setName("CostTypeName");
/*  55 */       textField.setEnabled(this.hasWriteAccess());
/*  56 */       return textField;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public JTextField getTextFieldDescription(PresentationModel presentationModel) {
/*  67 */       JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel("description"));
/*     */ 
/*     */ 
/*  70 */       textField.setName("Description");
/*  71 */       textField.setEnabled(this.hasWriteAccess());
/*  72 */       return textField;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public CheckObject checkDeleteObject(CostType object) {
/*  82 */       return object.getOrderCosts() != null && object.getOrderCosts().size() != 0 ? new CheckObject("Kan ikke slette kostnadstype som brukes av ordre", false) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public CheckObject checkSaveObject(CostTypeModel object, PresentationModel presentationModel, WindowInterface window) {
/* 101 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getAddRemoveString() {
/* 109 */       return "kostnadstype";
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getClassName() {
/* 117 */       return "CostType";
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public CostType getNewObject() {
/* 125 */       return new CostType();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public TableModel getTableModel(WindowInterface window) {
/* 133 */       return new CostTypeTableModel(this.objectSelectionList);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getTableWidth() {
/* 141 */       return "140dlu";
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getTitle() {
/* 149 */       return "Kostnadstyper";
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Dimension getWindowSize() {
/* 157 */       return new Dimension(420, 260);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setColumnWidth(JXTable table) {
/* 165 */       if (table.getColumnCount() > 0) {
/*     */ 
/* 167 */          TableColumn col = table.getColumnModel().getColumn(0);
/* 168 */          col.setPreferredWidth(80);
/*     */ 
/*     */ 
/* 171 */          col = table.getColumnModel().getColumn(1);
/* 172 */          col.setPreferredWidth(150);
/*     */       }
/*     */ 
/* 175 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean hasWriteAccess() {
/* 182 */       return UserUtil.hasWriteAccess(this.userType, "Kostnadstyper");
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    protected AbstractEditView<CostTypeModel, CostType> getEditView(AbstractViewHandler<CostType, CostTypeModel> handler, CostType object, boolean searching) {
/* 197 */       return new EditCostTypeView(this, object, searching);
/*     */    }
/*     */ }
