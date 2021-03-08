/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.ExternalOrder;
/*     */ import no.ugland.utransprod.model.ExternalOrderLine;
/*     */ import no.ugland.utransprod.model.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExternalOrderModel extends AbstractModel<ExternalOrder, ExternalOrderModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_SUPPLIER = "supplier";
/*     */    public static final String PROPERTY_EXTERNAL_ORDER_NR = "externalOrderNr";
/*     */    public static final String PROPERTY_DELIVERY_DATE = "deliveryDate";
/*     */    public static final String PROPERTY_EXTERNAL_ORDER_LINES = "externalOrderLines";
/*     */    public static final String PROPERTY_ATT = "att";
/*     */    public static final String PROPERTY_OUR_REF = "ourRef";
/*     */    public static final String PROPERTY_MARKED_WITH = "markedWith";
/*     */    public static final String PROPERTY_PHONE_NR = "phoneNr";
/*     */    public static final String PROPERTY_FAX_NR = "faxNr";
/*     */ 
/*     */    public ExternalOrderModel(ExternalOrder object) {
/*  76 */       super(object);
/*  77 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getFaxNr() {
/*  83 */       return ((ExternalOrder)this.object).getFaxNr();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setFaxNr(String faxNr) {
/*  90 */       String oldfax = this.getFaxNr();
/*  91 */       ((ExternalOrder)this.object).setFaxNr(faxNr);
/*  92 */       this.firePropertyChange("faxNr", oldfax, faxNr);
/*  93 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPhoneNr() {
/*  99 */       return ((ExternalOrder)this.object).getPhoneNr();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPhoneNr(String phoneNr) {
/* 106 */       String oldPhone = this.getPhoneNr();
/* 107 */       ((ExternalOrder)this.object).setPhoneNr(phoneNr);
/* 108 */       this.firePropertyChange("phoneNr", oldPhone, phoneNr);
/* 109 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getAtt() {
/* 115 */       return ((ExternalOrder)this.object).getAtt();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAtt(String att) {
/* 122 */       String oldAtt = this.getAtt();
/* 123 */       ((ExternalOrder)this.object).setAtt(att);
/* 124 */       this.firePropertyChange("att", oldAtt, att);
/* 125 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getOurRef() {
/* 131 */       return ((ExternalOrder)this.object).getOurRef();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOurRef(String ourRef) {
/* 138 */       String oldRef = this.getOurRef();
/* 139 */       ((ExternalOrder)this.object).setOurRef(ourRef);
/* 140 */       this.firePropertyChange("ourRef", oldRef, ourRef);
/* 141 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getMarkedWith() {
/* 147 */       return ((ExternalOrder)this.object).getMarkedWith();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setMarkedWith(String markedWith) {
/* 154 */       String oldMarked = this.getMarkedWith();
/* 155 */       ((ExternalOrder)this.object).setMarkedWith(markedWith);
/* 156 */       this.firePropertyChange("markedWith", oldMarked, markedWith);
/* 157 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Set<ExternalOrderLine> getExternalOrderLines() {
/* 163 */       return ((ExternalOrder)this.object).getExternalOrderLines();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setExternalOrderLines(Set<ExternalOrderLine> externalOrderLines) {
/* 170 */       Set<ExternalOrderLine> oldLines = this.getExternalOrderLines();
/* 171 */       ((ExternalOrder)this.object).setExternalOrderLines(externalOrderLines);
/* 172 */       this.firePropertyChange("externalOrderLines", oldLines, externalOrderLines);
/*     */ 
/* 174 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Date getDeliveryDate() {
/* 180 */       return ((ExternalOrder)this.object).getDeliveryDate();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDeliveryDate(Date deliveryDate) {
/* 187 */       Date oldDate = this.getDeliveryDate();
/* 188 */       ((ExternalOrder)this.object).setDeliveryDate(deliveryDate);
/* 189 */       this.firePropertyChange("deliveryDate", oldDate, deliveryDate);
/* 190 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getExternalOrderNr() {
/* 196 */       return ((ExternalOrder)this.object).getExternalOrderNr();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setExternalOrderNr(String externalOrderNr) {
/* 203 */       String oldNr = this.getExternalOrderNr();
/* 204 */       ((ExternalOrder)this.object).setExternalOrderNr(externalOrderNr);
/* 205 */       this.firePropertyChange("externalOrderNr", oldNr, externalOrderNr);
/* 206 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Supplier getSupplier() {
/* 212 */       return ((ExternalOrder)this.object).getSupplier();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSupplier(Supplier supplier) {
/* 219 */       Supplier oldSupplier = this.getSupplier();
/* 220 */       ((ExternalOrder)this.object).setSupplier(supplier);
/* 221 */       this.firePropertyChange("supplier", oldSupplier, supplier);
/* 222 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 231 */       presentationModel.getBufferedModel("supplier").addValueChangeListener(listener);
/*     */ 
/* 233 */       presentationModel.getBufferedModel("deliveryDate").addValueChangeListener(listener);
/*     */ 
/* 235 */       presentationModel.getBufferedModel("externalOrderNr").addValueChangeListener(listener);
/*     */ 
/* 237 */       presentationModel.getBufferedModel("externalOrderLines").addValueChangeListener(listener);
/*     */ 
/* 239 */       presentationModel.getBufferedModel("att").addValueChangeListener(listener);
/*     */ 
/* 241 */       presentationModel.getBufferedModel("ourRef").addValueChangeListener(listener);
/*     */ 
/* 243 */       presentationModel.getBufferedModel("markedWith").addValueChangeListener(listener);
/*     */ 
/* 245 */       presentationModel.getBufferedModel("phoneNr").addValueChangeListener(listener);
/*     */ 
/* 247 */       presentationModel.getBufferedModel("faxNr").addValueChangeListener(listener);
/*     */ 
/* 249 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ExternalOrderModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 258 */       ExternalOrderModel model = new ExternalOrderModel(new ExternalOrder());
/* 259 */       model.setSupplier((Supplier)presentationModel.getBufferedValue("supplier"));
/*     */ 
/* 261 */       model.setDeliveryDate((Date)presentationModel.getBufferedValue("deliveryDate"));
/*     */ 
/* 263 */       model.setExternalOrderNr((String)presentationModel.getBufferedValue("externalOrderNr"));
/*     */ 
/* 265 */       model.setExternalOrderLines((Set)presentationModel.getBufferedValue("externalOrderLines"));
/*     */ 
/* 267 */       model.setAtt((String)presentationModel.getBufferedValue("att"));
/* 268 */       model.setOurRef((String)presentationModel.getBufferedValue("ourRef"));
/*     */ 
/* 270 */       model.setMarkedWith((String)presentationModel.getBufferedValue("markedWith"));
/*     */ 
/* 272 */       model.setPhoneNr((String)presentationModel.getBufferedValue("phoneNr"));
/*     */ 
/* 274 */       model.setFaxNr((String)presentationModel.getBufferedValue("faxNr"));
/*     */ 
/* 276 */       return model;
/*     */    }
/*     */ }
