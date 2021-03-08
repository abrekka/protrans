/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.table.TableCellRenderer;
/*    */ import no.ugland.utransprod.model.ProcentDone;
/*    */ import no.ugland.utransprod.util.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextPaneRendererProcentDone extends JTextField implements TableCellRenderer {
/*    */    private static final int MAX_LENGHT = 50;
/*    */ 
/*    */    public final Component getTableCellRendererComponent(JTable table, Object object, boolean isSelected, boolean hasFocus, int row, int column) {
/* 22 */       this.setBorder(BorderFactory.createEmptyBorder());
/* 23 */       if (object != null) {
/* 24 */          ProcentDone procentDone = (ProcentDone)object;
/*    */ 
/* 26 */          StringBuffer toolTip = new StringBuffer();
/* 27 */          this.setText(Util.convertIntegerToString(procentDone.getProcent()) + "%");
/* 28 */          this.setCommentAsTooltip(procentDone.getProcentDoneComment(), toolTip);
/*    */ 
/* 30 */          if (toolTip.length() > 0) {
/* 31 */             toolTip.insert(0, "<html>");
/* 32 */             toolTip.append("</html>");
/* 33 */             this.setToolTipText(toolTip.toString());
/*    */          } else {
/* 35 */             this.setToolTipText("");
/*    */          }
/*    */ 
/* 38 */          this.setBackgrounAndForeground(table, isSelected);
/*    */       } else {
/* 40 */          this.setText("");
/* 41 */          this.setToolTipText("");
/* 42 */          this.setBackgrounAndForeground(table, isSelected);
/*    */       }
/* 44 */       return this;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    private void setCommentAsTooltip(String comment, StringBuffer toolTip) {
/* 50 */       if (comment != null && comment.length() != 0) {
/* 51 */          this.addCommentToTooltip(toolTip, comment);
/*    */       }
/* 53 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    private void addCommentToTooltip(StringBuffer toolTip, String comment) {
/* 58 */       String multilineComment = Util.splitLongStringIntoLinesWithBr(comment, 50);
/*    */ 
/*    */ 
/* 61 */       toolTip.append(multilineComment);
/* 62 */    }
/*    */ 
/*    */ 
/*    */    private void setBackgrounAndForeground(JTable table, boolean isSelected) {
/* 66 */       if (isSelected) {
/* 67 */          this.setBackground(table.getSelectionBackground());
/* 68 */          this.setForeground(table.getSelectionForeground());
/*    */ 
/*    */       } else {
/* 71 */          this.setBackground(table.getBackground());
/* 72 */          this.setForeground(table.getForeground());
/*    */       }
/* 74 */    }
/*    */ }
