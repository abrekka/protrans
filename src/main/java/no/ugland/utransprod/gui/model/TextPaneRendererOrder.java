/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.JTextPane;
/*    */ import javax.swing.table.TableCellRenderer;
/*    */ import no.ugland.utransprod.gui.IconEnum;
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
/*    */ public class TextPaneRendererOrder extends JTextPane implements TableCellRenderer {
/*    */    private static final long serialVersionUID = 1L;
/*    */ 
/*    */    public Component getTableCellRendererComponent(JTable table, Object object, boolean isSelected, boolean hasFocus, int row, int column) {
/* 28 */       this.setText(((TextRenderable)object).getOrderString());
/* 29 */       StringBuffer toolTip = new StringBuffer();
/*    */ 
/* 31 */       if (((TextRenderable)object).getComment() != null && ((TextRenderable)object).getComment().length() != 0) {
/*    */ 
/* 33 */          this.insertIcon(IconEnum.ICON_COMMENT.getIcon());
/* 34 */          if (((TextRenderable)object).getComment() != null) {
/*    */ 
/* 36 */             toolTip.append(((TextRenderable)object).getComment());
/*    */ 
/*    */          }
/*    */       }
/*    */ 
/* 41 */       this.setToolTipText(toolTip.toString());
/* 42 */       if (isSelected) {
/* 43 */          this.setBackground(table.getSelectionBackground());
/* 44 */          this.setForeground(table.getSelectionForeground());
/*    */ 
/*    */       } else {
/* 47 */          this.setBackground(table.getBackground());
/* 48 */          this.setForeground(table.getForeground());
/*    */       }
/*    */ 
/* 51 */       return this;
/*    */    }
/*    */ }
