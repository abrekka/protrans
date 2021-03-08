/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import com.jgoodies.binding.adapter.AbstractTableAdapter;
/*    */ import javax.swing.ListModel;
/*    */ import no.ugland.utransprod.model.CostType;
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
/*    */ 
/*    */ class CostTypeTableModel extends AbstractTableAdapter {
/*    */    private static final long serialVersionUID = 1L;
/*    */ 
/*    */    CostTypeTableModel(ListModel listModel) {
/* 31 */       super(listModel, new String[]{"Navn", "Beskrivelse"});
/* 32 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public Object getValueAt(int rowIndex, int columnIndex) {
/* 42 */       CostType costType = (CostType)this.getRow(rowIndex);
/* 43 */       switch(columnIndex) {
/*    */       case 0:
/* 45 */          return costType.getCostTypeName();
/*    */       case 1:
/* 47 */          return costType.getDescription();
/*    */       default:
/* 49 */          throw new IllegalStateException("Unknown column");
/*    */       }
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public Class<?> getColumnClass(int columnIndex) {
/* 59 */       switch(columnIndex) {
/*    */       case 0:
/*    */       case 1:
/* 62 */          return String.class;
/*    */       default:
/* 64 */          throw new IllegalStateException("Unknown column");
/*    */       }
/*    */    }
/*    */ }
