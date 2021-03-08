/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.table.TableCellRenderer;
/*    */ import no.ugland.utransprod.util.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextPaneRendererTransportSent extends JTextField implements TableCellRenderer {
/*    */    public final Component getTableCellRendererComponent(JTable table, Object object, boolean isSelected, boolean hasFocus, int row, int column) {
/* 17 */       Transportable transportable = (Transportable)object;
/* 18 */       this.setBorder(BorderFactory.createEmptyBorder());
/* 19 */       if (transportable.getSent() != null) {
/* 20 */          this.setText("Ja");
/* 21 */          this.setToolTipText(Util.SHORT_DATE_FORMAT.format(transportable.getSent()));
/*    */       } else {
/* 23 */          this.setText("Nei");
/* 24 */          this.setToolTipText("");
/*    */       }
/*    */ 
/* 27 */       this.setBackgrounAndForeground(table, isSelected);
/* 28 */       return this;
/*    */    }
/*    */ 
/*    */    private void setBackgrounAndForeground(JTable table, boolean isSelected) {
/* 32 */       if (isSelected) {
/* 33 */          this.setBackground(table.getSelectionBackground());
/* 34 */          this.setForeground(table.getSelectionForeground());
/*    */ 
/*    */       } else {
/* 37 */          this.setBackground(table.getBackground());
/* 38 */          this.setForeground(table.getForeground());
/*    */       }
/* 40 */    }
/*    */ }
