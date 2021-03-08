/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import com.jgoodies.binding.list.ArrayListModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.ArticleType;
/*     */ import no.ugland.utransprod.model.Colli;
/*     */ import no.ugland.utransprod.model.Deviation;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.OrderComment;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.PostShipment;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PostShipmentModel extends AbstractOrderModel<PostShipment, PostShipmentModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_ORDER = "order";
/*     */    private String orderNr;
/*     */ 
/*     */    public PostShipmentModel(PostShipment object, boolean lettvekt) {
/*  34 */       super(object, lettvekt);
/*     */ 
/*  36 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Order getOrder() {
/*  42 */       return ((PostShipment)this.object).getOrder();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOrder(Order order) {
/*  49 */       Order oldOrder = this.getOrder();
/*  50 */       ((PostShipment)this.object).setOrder(order);
/*  51 */       this.firePropertyChange("order", oldOrder, order);
/*  52 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/*  60 */       presentationModel.getBufferedModel("colliList").addValueChangeListener(listener);
/*  61 */       presentationModel.getBufferedModel("order").addValueChangeListener(listener);
/*  62 */       presentationModel.getBufferedModel("orderCompleteBool").addValueChangeListener(listener);
/*  63 */       presentationModel.getBufferedModel("orderLines").addValueChangeListener(listener);
/*  64 */       presentationModel.getBufferedModel("orderNr").addValueChangeListener(listener);
/*  65 */       presentationModel.getBufferedModel("orderReadyVeggBool").addValueChangeListener(listener);
/*  66 */       presentationModel.getBufferedModel("orderReadyTakstolBool").addValueChangeListener(listener);
/*  67 */       presentationModel.getBufferedModel("orderReadyPakkBool").addValueChangeListener(listener);
/*  68 */       presentationModel.getBufferedModel("packedBy").addValueChangeListener(listener);
/*  69 */       presentationModel.getBufferedModel("packedByTross").addValueChangeListener(listener);
/*  70 */       presentationModel.getBufferedModel("packedByPack").addValueChangeListener(listener);
/*  71 */       presentationModel.getBufferedModel("garageColliHeight").addValueChangeListener(listener);
/*     */ 
/*  73 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public PostShipmentModel getBufferedObjectModel(PresentationModel presentationModel) {
/*  80 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void firePropertiesChanged() {
/*  87 */       this.fireMultiplePropertiesChanged();
/*  88 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public static List<OrderComment> getPostShipmentComments(List<OrderComment> comments) {
/*  95 */       if (comments != null) {
/*  96 */          List<OrderComment> postShipmentComments = new ArrayList();         Iterator var2 = comments.iterator();         while(var2.hasNext()) {
/*  97 */             OrderComment comment = (OrderComment)var2.next();
/*  98 */             if (comment.getDeviation() != null) {
/*  99 */                postShipmentComments.add(comment);
/*     */             }         }
/*     */ 
/* 102 */          return postShipmentComments;
/*     */       } else {
/* 104 */          return null;
/*     */       }
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getComment() {
/* 112 */       return ((PostShipment)this.object).getComment();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setColliesDone(Integer colliesDone) {
/* 120 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Date getOrderComplete() {
/* 126 */       return ((PostShipment)this.object).getPostShipmentComplete();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Date getOrderReady() {
/* 133 */       return ((PostShipment)this.object).getPostShipmentReady();
/*     */    }
/*     */ 
/*     */    public Date getOrderReadyWall() {
/* 137 */       return ((PostShipment)this.object).getPostShipmentReady();
/*     */    }
/*     */ 
/*     */    public Date getOrderReadyTross() {
/* 141 */       return ((PostShipment)this.object).getPostShipmentReady();
/*     */    }
/*     */ 
/*     */    public Date getOrderReadyPack() {
/* 145 */       return ((PostShipment)this.object).getPostShipmentReady();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getOrderNr() {
/* 152 */       return this.orderNr;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOrderNr(String orderNr) {
/* 159 */       String oldNr = this.getOrderNr();
/* 160 */       this.orderNr = orderNr;
/* 161 */       this.firePropertyChange("orderNr", oldNr, orderNr);
/* 162 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<ArticleType> getArticles() {
/* 168 */       return ((PostShipment)this.object).getArticles();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ArrayListModel getCostList() {
/* 175 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getCustomerFirstName() {
/* 182 */       return ((PostShipment)this.object).getOrder().getCustomer().getFirstName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getCustomerLastName() {
/* 189 */       return ((PostShipment)this.object).getOrder().getCustomer().getLastName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDeliveryAddress() {
/* 196 */       return ((PostShipment)this.object).getOrder().getDeliveryAddress();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Deviation getDeviation() {
/* 203 */       return ((PostShipment)this.object).getDeviation();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ArrayListModel getOrderLineArrayListModel() {
/* 210 */       return ((PostShipment)this.object).getOrderLines() != null ? new ArrayListModel(((PostShipment)this.object).getOrderLines()) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ArrayListModel getOrderLineList() {
/* 217 */       return this.getOrderLineArrayListModel();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPostOffice() {
/* 224 */       return ((PostShipment)this.object).getOrder().getPostOffice();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPostalCode() {
/* 231 */       return ((PostShipment)this.object).getOrder().getPostalCode();
/*     */    }
/*     */ 
/*     */    public Transportable getTransportable() {
/* 235 */       return (Transportable)this.object;
/*     */    }
/*     */ 
/*     */    public List<OrderLine> getOwnOrderLines() {
/* 239 */       List<OrderLine> orderLines = new ArrayList();
/* 240 */       List<OrderLine> allOrderLines = this.getOrderLines();
/* 241 */       if (allOrderLines != null) {         Iterator var3 = allOrderLines.iterator();         while(var3.hasNext()) {
/* 242 */             OrderLine orderLine = (OrderLine)var3.next();
/* 243 */             if (orderLine.belongTo((Transportable)this.object)) {
/* 244 */                orderLines.add(orderLine);
/*     */             }         }
/*     */       }
/*     */ 
/* 248 */       return orderLines;
/*     */    }
/*     */ 
/*     */ 
/*     */    public String getManagerName() {
/* 253 */       return "postShipmentManager";
/*     */    }
/*     */ 
/*     */ 
/*     */    public PostShipment getOrderModelPostShipment() {
/* 258 */       return (PostShipment)this.object;
/*     */    }
/*     */ 
/*     */    public Order getOrderModelOrder() {
/* 262 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public Enum[] getEnums() {
/* 268 */       return new LazyLoadPostShipmentEnum[]{LazyLoadPostShipmentEnum.COLLIES, LazyLoadPostShipmentEnum.ORDER_LINES, LazyLoadPostShipmentEnum.ORDER_COMMENTS};
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public Serializable getObjectId() {
/* 274 */       return ((PostShipment)this.object).getPostShipmentId();
/*     */    }
/*     */ 
/*     */    public Integer getProbability() {
/* 278 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public Integer getDefaultColliesGenerated() {
/* 283 */       return ((PostShipment)this.object).getDefaultColliesGenerated();
/*     */    }
/*     */ 
/*     */    public void setDefaultColliesGenerated(Integer defaultGenerated) {
/* 287 */       Integer oldGenerated = this.getDefaultColliesGenerated();
/* 288 */       ((PostShipment)this.object).setDefaultColliesGenerated(defaultGenerated);
/* 289 */       this.firePropertyChange("defaultColliesGenerated", oldGenerated, defaultGenerated);
/* 290 */    }
/*     */ 
/*     */    public PostShipment getPostShipment() {
/* 293 */       return (PostShipment)this.object;
/*     */    }
/*     */ 
/*     */    public Set<Colli> getCollies() {
/* 297 */       return ((PostShipment)this.object).getCollies();
/*     */    }
/*     */ }
