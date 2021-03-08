/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.DefaultTableCellRenderer;
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
/*    */ 
/*    */ public class TextPaneStringRenderer extends DefaultTableCellRenderer {
/*    */    private static final long serialVersionUID = 1L;
/*    */ 
/*    */    public Component getTableCellRendererComponent(JTable table, Object object, boolean isSelected, boolean hasFocus, int row, int column) {
/* 27 */       this.setText((String)object);
/* 28 */       this.setOpaque(true);
/* 29 */       if (isSelected) {
/* 30 */          this.setBackground(table.getSelectionBackground());
/* 31 */          this.setForeground(table.getSelectionForeground());
/*    */ 
/*    */       } else {
/* 34 */          this.setBackground(table.getBackground());
/* 35 */          this.setForeground(table.getForeground());
/*    */       }
/*    */ 
/* 38 */       return this;
/*    */    }
/*    */ }
