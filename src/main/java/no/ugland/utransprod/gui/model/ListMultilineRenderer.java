/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.JTextPane;
/*    */ import javax.swing.ListCellRenderer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListMultilineRenderer extends JTextPane implements ListCellRenderer {
/*    */    private static final long serialVersionUID = 1L;
/*    */    private int lineSize = 120;
/*    */ 
/*    */    public ListMultilineRenderer(int aLineSize) {
/* 32 */       this.lineSize = aLineSize;
/* 33 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 42 */       String allText = value.toString();
/* 43 */       String[] lines = allText.split("\\n");
/* 44 */       StringBuilder builder = new StringBuilder();
/*    */ 
/* 46 */       if (lines == null || lines.length == 0) {
/* 47 */          lines = new String[]{allText};
/*    */       }
/* 49 */       for(int j = 0; j < lines.length; ++j) {
/* 50 */          if (j > 0) {
/* 51 */             builder.append("\n");
/*    */          }
/* 53 */          String text = lines[j];
/* 54 */          if (text != null && text.length() > this.lineSize) {
/* 55 */             int times = text.length() / this.lineSize;
/* 56 */             for(int i = 0; i <= times; ++i) {
/*    */                int stopIndex;
/* 58 */                if (text.length() > i * this.lineSize + this.lineSize) {
/* 59 */                   stopIndex = i * this.lineSize + this.lineSize;
/*    */                } else {
/* 61 */                   stopIndex = text.length();
/*    */                }
/*    */ 
/* 64 */                builder.append(text.substring(i * this.lineSize, stopIndex)).append("\n");
/*    */             }
/*    */ 
/* 67 */             builder.deleteCharAt(builder.length() - 1);
/*    */          } else {
/* 69 */             builder.append(text);
/*    */          }
/*    */       }
/*    */ 
/* 73 */       this.setText(builder.toString());
/*    */ 
/* 75 */       if (isSelected) {
/* 76 */          this.setBackground(list.getSelectionBackground());
/* 77 */          this.setForeground(list.getSelectionForeground());
/*    */ 
/*    */       } else {
/* 80 */          this.setBackground(list.getBackground());
/* 81 */          this.setForeground(list.getForeground());
/*    */       }
/*    */ 
/* 84 */       return this;
/*    */    }
/*    */ }
