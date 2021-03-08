/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import com.jgoodies.binding.PresentationModel;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.model.OrderCost;
/*    */ import no.ugland.utransprod.model.TransportCostBasis;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransportCostBasisModel extends AbstractModel<TransportCostBasis, TransportCostBasisModel> {
/*    */    public static final String PROPERTY_ORDER_COSTS = "orderCosts";
/*    */    private static final long serialVersionUID = 1L;
/*    */ 
/*    */    public TransportCostBasisModel(TransportCostBasis object) {
/* 19 */       super(object);
/* 20 */    }
/*    */ 
/*    */    public final List<OrderCost> getOrderCosts() {
/* 23 */       return ((TransportCostBasis)this.object).getOrderCosts() != null ? new ArrayList(((TransportCostBasis)this.object).getOrderCosts()) : null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 35 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final TransportCostBasisModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 41 */       return null;
/*    */    }
/*    */ }
