/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import no.ugland.utransprod.model.AssemblyOverdueV;
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
/*    */ public class AssemblyOverdueVModel extends AbstractModel<AssemblyOverdueV, AssemblyOverdueVModel> {
/*    */    private static final long serialVersionUID = 1L;
/*    */    public static final String PROPERTY_YEAR_WEEK = "yearWeek";
/*    */ 
/*    */    public AssemblyOverdueVModel(AssemblyOverdueV object) {
/* 31 */       super(object);
/* 32 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public String getYearWeek() {
/* 38 */       return ((AssemblyOverdueV)this.object).getYearWeek();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void setYearWeek(String yearWeek) {
/* 45 */       String oldYear = this.getYearWeek();
/* 46 */       if (yearWeek != null) {
/* 47 */          Integer year = Integer.valueOf(yearWeek.substring(0, yearWeek.indexOf(" ")));
/*    */ 
/* 49 */          Integer week = Integer.valueOf(yearWeek.substring(yearWeek.indexOf(" ") + 1));
/*    */ 
/* 51 */          ((AssemblyOverdueV)this.object).setAssemblyYear(year);
/* 52 */          ((AssemblyOverdueV)this.object).setAssemblyWeek(week);
/*    */       } else {
/* 54 */          ((AssemblyOverdueV)this.object).setAssemblyYear((Integer)null);
/* 55 */          ((AssemblyOverdueV)this.object).setAssemblyWeek((Integer)null);
/*    */       }
/* 57 */       this.firePropertyChange("yearWeek", oldYear, yearWeek);
/* 58 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 67 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public AssemblyOverdueVModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 75 */       return null;
/*    */    }
/*    */ }
