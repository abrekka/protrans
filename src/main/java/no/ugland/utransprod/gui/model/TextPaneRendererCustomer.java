/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.JTextPane;
/*    */ import javax.swing.table.TableCellRenderer;
/*    */ import no.ugland.utransprod.gui.IconEnum;
/*    */ import no.ugland.utransprod.model.Customer;
/*    */ import no.ugland.utransprod.util.Util;
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
/*    */ public class TextPaneRendererCustomer extends JTextPane implements TableCellRenderer {
/*    */    private static final long serialVersionUID = 1L;
/*    */    private static final int MAX_LENGHT = 50;
/*    */ 
/*    */    public final Component getTableCellRendererComponent(JTable table, Object object, boolean isSelected, boolean hasFocus, int row, int column) {
/* 31 */       Transportable transportable = (Transportable)object;
/*    */ 
/* 33 */       Customer customer = transportable.getCustomer();
/* 34 */       this.setText(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getCustomerNr());
/*    */ 
/*    */ 
/* 37 */       StringBuffer toolTip = new StringBuffer();
/*    */ 
/* 39 */       this.setCommentAsTooltip(object, toolTip);
/*    */ 
/* 41 */       this.setWarningAsTooltip(object, toolTip);
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
/* 54 */       if (toolTip.length() > 0) {
/* 55 */          toolTip.insert(0, "<html>");
/* 56 */          toolTip.append("</html>");
/* 57 */          this.setToolTipText(toolTip.toString());
/*    */       } else {
/* 59 */          this.setToolTipText("");
/*    */       }
/* 61 */       if (isSelected) {
/* 62 */          this.setBackground(table.getSelectionBackground());
/* 63 */          this.setForeground(table.getSelectionForeground());
/*    */ 
/*    */       } else {
/* 66 */          this.setBackground(table.getBackground());
/* 67 */          this.setForeground(table.getForeground());
/*    */       }
/* 69 */       return this;
/*    */    }
/*    */ 
/*    */    private void setCommentAsTooltip(Object object, StringBuffer toolTip) {
/* 73 */       String comment = ((Transportable)object).getComment();
/* 74 */       if (comment != null && comment.length() != 0) {
/* 75 */          this.addCommentToTooltip(toolTip, comment);
/*    */       }
/* 77 */    }
/*    */ 
/*    */    private void setWarningAsTooltip(Object object, StringBuffer toolTip) {
/* 80 */       if (((Transportable)object).getSpecialConcern() != null) {
/* 81 */          this.insertIcon(IconEnum.ICON_WARNING.getIcon());
/* 82 */          toolTip.append(((Transportable)object).getSpecialConcern());
/*    */       }
/* 84 */    }
/*    */ 
/*    */    private void addCommentToTooltip(StringBuffer toolTip, String comment) {
/* 87 */       this.insertIcon(IconEnum.ICON_COMMENT.getIcon());
/*    */ 
/* 89 */       String multilineComment = Util.splitLongStringIntoLinesWithBr(comment, 50);
/*    */ 
/*    */ 
/* 92 */       toolTip.append(multilineComment);
/* 93 */    }
/*    */ }
