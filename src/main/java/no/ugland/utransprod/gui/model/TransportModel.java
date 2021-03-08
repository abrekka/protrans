/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.Employee;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.Supplier;
/*     */ import no.ugland.utransprod.model.Transport;
/*     */ import no.ugland.utransprod.util.Util;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransportModel extends AbstractModel<Transport, TransportModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_TRANSPORT_ID = "transportId";
/*     */    public static final String PROPERTY_TRANSPORT_NAME = "transportName";
/*     */    public static final String PROPERTY_LOADING_DATE = "loadingDate";
/*     */    public static final String PROPERTY_TRANSPORT_YEAR = "transportYear";
/*     */    public static final String PROPERTY_TRANSPORT_WEEK = "transportWeek";
/*     */    public static final String PROPERTY_ORDERS = "orders";
/*     */    public static final String PROPERTY_SENT_BOOL = "sentBool";
/*     */    public static final String PROPERTY_LEVERT_BOOL = "levertBool";
/*     */    public static final String PROPERTY_TRANSPORTABLES = "transportables";
/*     */    public static final String PROPERTY_SUPPLIER = "supplier";
/*     */    public static final String PROPERTY_EMPLOYEE = "employee";
/*     */    public static final String PROPERTY_LOAD_TIME = "loadTime";
/*     */    public static final String PROPERTY_SENT_STRING = "sentString";
/*     */    public static final String PROPERTY_LEVERT_STRING = "levertString";
/*     */    public static final String PROPERTY_TROLLEY = "trolley";
/*     */    public static final String PROPERTY_TRANSPORT_COMMENT = "transportComment";
/*     */ 
/*     */    public TransportModel(Transport transport) {
/*  96 */       super(transport);
/*  97 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getTransportId() {
/* 103 */       return ((Transport)this.object).getTransportId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setTransportId(Integer transportId) {
/* 110 */       Integer oldId = this.getTransportId();
/* 111 */       ((Transport)this.object).setTransportId(transportId);
/* 112 */       this.firePropertyChange("transportId", oldId, transportId);
/* 113 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getTransportName() {
/* 119 */       return ((Transport)this.object).getTransportName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setTransportName(String transportName) {
/* 126 */       String oldName = this.getTransportName();
/* 127 */       ((Transport)this.object).setTransportName(transportName);
/* 128 */       this.firePropertyChange("transportName", oldName, transportName);
/* 129 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Date getLoadingDate() {
/* 135 */       return Util.convertDate(((Transport)this.object).getLoadingDate(), Util.SHORT_DATE_FORMAT);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setLoadingDate(Date loadingDate) {
/* 142 */       Date oldDate = this.getLoadingDate();
/* 143 */       ((Transport)this.object).setLoadingDate(Util.convertDate(loadingDate, Util.SHORT_DATE_FORMAT));
/* 144 */       this.firePropertyChange("loadingDate", oldDate, loadingDate);
/* 145 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getTransportYear() {
/* 151 */       return ((Transport)this.object).getTransportYear() == null ? 0 : ((Transport)this.object).getTransportYear();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setTransportYear(Integer transportYear) {
/* 161 */       Integer oldYear = this.getTransportYear();
/* 162 */       ((Transport)this.object).setTransportYear(transportYear);
/* 163 */       this.firePropertyChange("transportYear", oldYear, transportYear);
/* 164 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getTransportWeek() {
/* 170 */       return ((Transport)this.object).getTransportWeek();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setTransportWeek(Integer transportWeek) {
/* 177 */       Integer oldWeek = this.getTransportWeek();
/* 178 */       ((Transport)this.object).setTransportWeek(transportWeek);
/* 179 */       this.firePropertyChange("transportWeek", oldWeek, transportWeek);
/* 180 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Set<Order> getOrders() {
/* 186 */       return ((Transport)this.object).getOrders();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOrders(Set<Order> orders) {
/* 193 */       Set<Order> oldOrders = this.getOrders();
/* 194 */       ((Transport)this.object).setOrders(orders);
/* 195 */       this.firePropertyChange("orders", oldOrders, orders);
/* 196 */    }
/*     */ 
/*     */    public String getTrolley() {
/* 199 */       return ((Transport)this.object).getTrolley();
/*     */    }
/*     */ 
/*     */    public void setTrolley(String aTrolley) {
/* 203 */       String oldTrolley = this.getTrolley();
/* 204 */       ((Transport)this.object).setTrolley(aTrolley);
/* 205 */       this.firePropertyChange("trolley", oldTrolley, aTrolley);
/* 206 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Set<Transportable> getTransportables() {
/* 212 */       Set<Transportable> transportables = new HashSet();
/* 213 */       if (((Transport)this.object).getOrders() != null) {
/* 214 */          transportables.addAll(((Transport)this.object).getOrders());
/*     */       }
/* 216 */       if (((Transport)this.object).getPostShipments() != null) {
/* 217 */          transportables.addAll(((Transport)this.object).getPostShipments());
/*     */       }
/* 219 */       return transportables;
/*     */    }
/*     */ 
/*     */    public String getTransportComment() {
/* 223 */       return ((Transport)this.object).getTransportComment();
/*     */    }
/*     */    public void setTransportComment(String aComment) {
/* 226 */       String oldcomment = this.getTransportComment();
/* 227 */       ((Transport)this.object).setTransportComment(aComment);
/* 228 */       this.firePropertyChange("transportComment", oldcomment, aComment);
/* 229 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 238 */       presentationModel.getBufferedModel("loadingDate").addValueChangeListener(listener);
/*     */ 
/* 240 */       presentationModel.getBufferedModel("orders").addValueChangeListener(listener);
/*     */ 
/* 242 */       presentationModel.getBufferedModel("transportId").addValueChangeListener(listener);
/*     */ 
/* 244 */       presentationModel.getBufferedModel("transportName").addValueChangeListener(listener);
/*     */ 
/* 246 */       presentationModel.getBufferedModel("transportWeek").addValueChangeListener(listener);
/*     */ 
/* 248 */       presentationModel.getBufferedModel("transportYear").addValueChangeListener(listener);
/*     */ 
/* 250 */       presentationModel.getBufferedModel("sentBool").addValueChangeListener(listener);
/*     */ 
/* 252 */       presentationModel.getBufferedModel("levertBool").addValueChangeListener(listener);
/*     */ 
/* 254 */       presentationModel.getBufferedModel("supplier").addValueChangeListener(listener);
/*     */ 
/* 256 */       presentationModel.getBufferedModel("loadTime").addValueChangeListener(listener);
/*     */ 
/* 258 */       presentationModel.getBufferedModel("employee").addValueChangeListener(listener);
/*     */ 
/* 260 */       presentationModel.getBufferedModel("trolley").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 263 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public TransportModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 272 */       TransportModel transportModel = new TransportModel(new Transport());
/* 273 */       transportModel.setTransportId((Integer)presentationModel.getBufferedValue("transportId"));
/*     */ 
/* 275 */       transportModel.setTransportName((String)presentationModel.getBufferedValue("transportName"));
/*     */ 
/* 277 */       transportModel.setTransportWeek((Integer)presentationModel.getBufferedValue("transportWeek"));
/*     */ 
/* 279 */       transportModel.setTransportYear((Integer)presentationModel.getBufferedValue("transportYear"));
/*     */ 
/* 281 */       transportModel.setLoadingDate((Date)presentationModel.getBufferedValue("loadingDate"));
/*     */ 
/* 283 */       transportModel.setOrders((Set)presentationModel.getBufferedValue("orders"));
/*     */ 
/* 285 */       transportModel.setSentBool((Boolean)presentationModel.getBufferedValue("sentBool"));
/*     */ 
/* 287 */       transportModel.setSentBool((Boolean)presentationModel.getBufferedValue("levertBool"));
/*     */ 
/* 289 */       transportModel.setSupplier((Supplier)presentationModel.getBufferedValue("supplier"));
/*     */ 
/* 291 */       transportModel.setLoadTime((String)presentationModel.getBufferedValue("loadTime"));
/*     */ 
/* 293 */       transportModel.setEmployee((Employee)presentationModel.getBufferedValue("employee"));
/*     */ 
/* 295 */       transportModel.setTrolley((String)presentationModel.getBufferedValue("trolley"));
/*     */ 
/* 297 */       return transportModel;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getSentBool() {
/* 304 */       return ((Transport)this.object).getSent() != null ? Boolean.TRUE : Boolean.FALSE;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getLevertBool() {
/* 310 */       return ((Transport)this.object).getLevert() != null ? Boolean.TRUE : Boolean.FALSE;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSentBool(Boolean sent) {
/* 320 */       Boolean oldBool = this.getSentBool();
/* 321 */       if (sent) {
/* 322 */          ((Transport)this.object).setSent(Calendar.getInstance().getTime());
/* 323 */          this.setSentString(Util.SHORT_DATE_FORMAT.format(((Transport)this.object).getSent()));
/*     */       } else {
/* 325 */          ((Transport)this.object).setSent((Date)null);
/* 326 */          this.setSentString((String)null);
/*     */       }
/* 328 */       this.firePropertyChange("sentBool", oldBool, sent);
/*     */ 
/*     */ 
/* 331 */    }
/*     */ 
/*     */    public void setLevertBool(Boolean levert) {
/* 334 */       Boolean oldBool = this.getLevertBool();
/* 335 */       if (levert) {
/* 336 */          ((Transport)this.object).setLevert(Calendar.getInstance().getTime());
/* 337 */          this.setLevertString(Util.SHORT_DATE_FORMAT.format(((Transport)this.object).getLevert()));
/*     */       } else {
/* 339 */          ((Transport)this.object).setLevert((Date)null);
/* 340 */          this.setLevertString((String)null);
/*     */       }
/* 342 */       this.firePropertyChange("levertBool", oldBool, levert);
/*     */ 
/*     */ 
/* 345 */    }
/*     */ 
/*     */    public final String getSentString() {
/* 348 */       return ((Transport)this.object).getSent() != null ? Util.SHORT_DATE_FORMAT.format(((Transport)this.object).getSent()) : "";
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public final String getLevertString() {
/* 354 */       return ((Transport)this.object).getLevert() != null ? Util.SHORT_DATE_FORMAT.format(((Transport)this.object).getLevert()) : "";
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void setSentString(String sentString) {
/* 360 */       String oldSent = this.getSentString();
/* 361 */       this.firePropertyChange("sentString", oldSent, sentString);
/* 362 */    }
/*     */    public final void setLevertString(String levertString) {
/* 364 */       String oldLevert = this.getLevertString();
/* 365 */       this.firePropertyChange("levertString", oldLevert, levertString);
/* 366 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Supplier getSupplier() {
/* 374 */       return ((Transport)this.object).getSupplier();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSupplier(Supplier supplier) {
/* 381 */       Supplier oldSupplier = this.getSupplier();
/* 382 */       ((Transport)this.object).setSupplier(supplier);
/* 383 */       this.firePropertyChange("supplier", oldSupplier, supplier);
/* 384 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Employee getEmployee() {
/* 390 */       return ((Transport)this.object).getEmployee();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setEmployee(Employee employee) {
/* 397 */       Employee oldEmployee = this.getEmployee();
/* 398 */       ((Transport)this.object).setEmployee(employee);
/* 399 */       this.firePropertyChange("employee", oldEmployee, employee);
/* 400 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getLoadTime() {
/* 406 */       return ((Transport)this.object).getLoadTime();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setLoadTime(String loadTime) {
/* 413 */       String oldTime = this.getLoadTime();
/* 414 */       ((Transport)this.object).setLoadTime(loadTime);
/* 415 */       this.firePropertyChange("loadTime", oldTime, loadTime);
/* 416 */    }
/*     */ }
