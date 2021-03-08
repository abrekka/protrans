/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import com.jgoodies.binding.list.ArrayListModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.ApplicationUser;
/*     */ import no.ugland.utransprod.model.ArticleType;
/*     */ import no.ugland.utransprod.model.Deviation;
/*     */ import no.ugland.utransprod.model.DeviationStatus;
/*     */ import no.ugland.utransprod.model.FunctionCategory;
/*     */ import no.ugland.utransprod.model.JobFunction;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.OrderComment;
/*     */ import no.ugland.utransprod.model.OrderCost;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.PostShipment;
/*     */ import no.ugland.utransprod.model.PreventiveAction;
/*     */ import no.ugland.utransprod.model.ProductArea;
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
/*     */ public class DeviationModel extends AbstractModel<Deviation, DeviationModel> implements ICostableModel<Deviation, DeviationModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_DEVIATION_ID = "deviationId";
/*     */    public static final String PROPERTY_USER_NAME = "userName";
/*     */    public static final String PROPERTY_DESCRIPTION = "description";
/*     */    public static final String PROPERTY_OWN_FUNCTION = "ownFunction";
/*     */    public static final String PROPERTY_DEVIATION_FUNCTION = "deviationFunction";
/*     */    public static final String PROPERTY_CUSTOMER_NR = "customerNr";
/*     */    public static final String PROPERTY_CUSTOMER_NAME = "customerName";
/*     */    public static final String PROPERTY_ORDER_NR = "orderNr";
/*     */    public static final String PROPERTY_DEVIATION_STATUS = "deviationStatus";
/*     */    public static final String PROPERTY_DATE_CLOSED = "dateClosed";
/*     */    public static final String PROPERTY_PRODUCT_AREA = "productArea";
/*     */    public static final String PROPERTY_FUNCTION_CATEGORY = "functionCategory";
/*     */    public static final String PROPERTY_COMMENTS = "comments";
/*     */    public static final String PROPERTY_POST_SHIPMENT_BOOL = "postShipmentBool";
/*     */    public static final String PROPERTY_CAN_SET_POST_SHIPMENT = "canSetPostShipment";
/*     */    public static final String PROPERTY_ORDER = "order";
/*     */    public static final String PROPERTY_POST_SHIPMENT = "postShipment";
/*     */    public static final String PROPERTY_CAN_ADD_ORDER_LINE = "canAddOrderLine";
/*     */    public static final String PROPERTY_IS_POST_SHIPMENT = "isPostShipment";
/*     */    public static final String PROPERTY_PREVENTIVE_ACTION = "preventiveAction";
/*     */    public static final String PROPERTY_LAST_COMMENT = "lastComment";
/*     */    public static final String PROPERTY_DO_ASSEMBLY_BOOL = "doAssemblyBool";
/*     */    public static final String PROPERTY_RESPONSIBLE = "responsible";
/*     */    public static final String PROPERTY_DATE_FROM = "dateFrom";
/*     */    public static final String PROPERTY_DATE_TO = "dateTo";
/*     */    public static final String PROPERTY_CHECKED_BOOL = "checkedBool";
/*     */    public static final String PROPERTY_INITIATED_BY = "initiatedBy";
/*     */    public static final String PROPERTY_MOD_COUNT = "modCount";
/*     */    public static final String PROPERTY_REGISTRATION_DATE_STRING = "registrationDateString";
/*     */    public static final String PROPERTY_PROJECT_NR = "projectNr";
/*     */    public static final String PROPERTY_PROCEDURE_CHECK = "procedureCheck";
/*     */    public static final String PROPERTY_DATE_CLOSED_STRING = "dateClosedString";
/*     */    public static final String PROPERTY_PROBABILITY = "probability";
/*     */    public static final String PROPERTY_CS_ID = "csId";
/*     */    private final List<OrderComment> commentList;
/*     */    private Boolean canSetPostShipment = false;
/*     */    private Boolean canAddOrderLine = false;
/*     */    private final ArrayListModel orderLineList;
/*     */    private boolean isPostShipment = false;
/*     */    private ArrayListModel costList = null;
/*     */    private Integer modCount;
/*     */ 
/*     */    public DeviationModel(Deviation deviation, boolean isPostShipment) {
/* 122 */       super(deviation);
/* 123 */       this.isPostShipment = isPostShipment;
/* 124 */       this.commentList = new ArrayList();
/* 125 */       if (deviation.getOrderComments() != null) {
/* 126 */          this.commentList.addAll(deviation.getOrderComments());
/*     */       }
/* 128 */       this.orderLineList = new ArrayListModel();
/* 129 */       if (deviation.getPostShipment() != null && deviation.getPostShipment().getOrderLines() != null) {
/* 130 */          this.orderLineList.addAll(deviation.getPostShipment().getOrderLines());
/* 131 */       } else if (deviation.getOrderLines() != null) {
/* 132 */          this.orderLineList.addAll(deviation.getOrderLines());
/*     */       }
/* 134 */       if (((Deviation)this.object).getOrder() != null && this.orderLineList.size() != 0 && (deviation.getDeviationId() == null || deviation.getPostShipment() == null)) {
/* 135 */          this.canSetPostShipment = true;
/*     */       }
/* 137 */       if (((Deviation)this.object).getOrder() != null) {
/* 138 */          this.canAddOrderLine = true;
/*     */       }
/*     */ 
/* 141 */    }
/*     */ 
/*     */    public Integer getProbability() {
/* 144 */       return 100;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getModCount() {
/* 154 */       return this.modCount;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setModCount(Integer modCount) {
/* 161 */       Integer oldMod = this.getModCount();
/* 162 */       this.modCount = modCount;
/* 163 */       this.firePropertyChange("modCount", oldMod, modCount);
/* 164 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getUserName() {
/* 170 */       return ((Deviation)this.object).getUserName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setUserName(String userName) {
/* 177 */       String oldName = this.getUserName();
/* 178 */       ((Deviation)this.object).setUserName(userName);
/* 179 */       this.firePropertyChange("userName", oldName, userName);
/*     */ 
/* 181 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDeviationId() {
/* 187 */       Integer id = ((Deviation)this.object).getDeviationId();
/* 188 */       return id != null ? id.toString() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDeviationId(String deviationId) {
/* 198 */       String oldId = this.getDeviationId();
/* 199 */       if (deviationId != null) {
/* 200 */          ((Deviation)this.object).setDeviationId(Integer.valueOf(deviationId));
/*     */       } else {
/* 202 */          ((Deviation)this.object).setDeviationId((Integer)null);
/*     */       }
/* 204 */       this.firePropertyChange("deviationId", oldId, deviationId);
/* 205 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDescription() {
/* 211 */       return ((Deviation)this.object).getDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDescription(String desc) {
/* 218 */       String oldDesc = this.getDescription();
/* 219 */       ((Deviation)this.object).setDescription(desc);
/* 220 */       this.firePropertyChange("description", oldDesc, desc);
/* 221 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public JobFunction getOwnFunction() {
/* 227 */       return ((Deviation)this.object).getOwnFunction();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOwnFunction(JobFunction ownFunction) {
/* 234 */       JobFunction oldFunction = this.getOwnFunction();
/* 235 */       ((Deviation)this.object).setOwnFunction(ownFunction);
/* 236 */       this.firePropertyChange("ownFunction", oldFunction, ownFunction);
/* 237 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public JobFunction getDeviationFunction() {
/* 243 */       return ((Deviation)this.object).getDeviationFunction();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDeviationFunction(JobFunction deviationFunction) {
/* 250 */       JobFunction oldFunction = this.getDeviationFunction();
/* 251 */       ((Deviation)this.object).setDeviationFunction(deviationFunction);
/* 252 */       this.firePropertyChange("deviationFunction", oldFunction, deviationFunction);
/* 253 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getCustomerNr() {
/* 259 */       return ((Deviation)this.object).getCustomerNr();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCustomerNr(Integer customerNr) {
/* 266 */       Integer oldNr = this.getCustomerNr();
/* 267 */       ((Deviation)this.object).setCustomerNr(customerNr);
/* 268 */       this.firePropertyChange("customerNr", oldNr, customerNr);
/* 269 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getCustomerName() {
/* 275 */       return ((Deviation)this.object).getCustomerName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCustomerName(String customerName) {
/* 282 */       String oldName = this.getCustomerName();
/* 283 */       ((Deviation)this.object).setCustomerName(customerName);
/* 284 */       this.firePropertyChange("customerName", oldName, customerName);
/* 285 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getOrderNr() {
/* 291 */       return ((Deviation)this.object).getOrderNr();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOrderNr(String orderNr) {
/* 298 */       String oldNr = this.getOrderNr();
/*     */ 
/* 300 */       ((Deviation)this.object).setOrderNr(orderNr);
/* 301 */       this.firePropertyChange("orderNr", oldNr, orderNr);
/* 302 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public DeviationStatus getDeviationStatus() {
/* 308 */       return ((Deviation)this.object).getDeviationStatus();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDeviationStatus(DeviationStatus deviationStatus) {
/* 315 */       DeviationStatus oldStatus = this.getDeviationStatus();
/* 316 */       ((Deviation)this.object).setDeviationStatus(deviationStatus);
/* 317 */       this.firePropertyChange("deviationStatus", oldStatus, deviationStatus);
/* 318 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Date getDateClosed() {
/* 324 */       return ((Deviation)this.object).getDateClosed();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDateClosed(Date dateClosed) {
/* 331 */       Date oldDate = this.getDateClosed();
/* 332 */       ((Deviation)this.object).setDateClosed(dateClosed);
/* 333 */       this.firePropertyChange("dateClosed", oldDate, dateClosed);
/* 334 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ProductArea getProductArea() {
/* 340 */       return ((Deviation)this.object).getProductArea();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setProductArea(ProductArea productArea) {
/* 347 */       ProductArea oldArea = this.getProductArea();
/* 348 */       ((Deviation)this.object).setProductArea(productArea);
/* 349 */       this.firePropertyChange("productArea", oldArea, productArea);
/* 350 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public FunctionCategory getFunctionCategory() {
/* 356 */       return ((Deviation)this.object).getFunctionCategory();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setFunctionCategory(FunctionCategory functionCategory) {
/* 363 */       FunctionCategory oldCategory = this.getFunctionCategory();
/* 364 */       ((Deviation)this.object).setFunctionCategory(functionCategory);
/* 365 */       this.firePropertyChange("functionCategory", oldCategory, functionCategory);
/* 366 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public PreventiveAction getPreventiveAction() {
/* 372 */       return ((Deviation)this.object).getPreventiveAction();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPreventiveAction(PreventiveAction preventiveAction) {
/* 379 */       PreventiveAction oldAction = this.getPreventiveAction();
/* 380 */       ((Deviation)this.object).setPreventiveAction(preventiveAction);
/*     */ 
/* 382 */       this.firePropertyChange("preventiveAction", oldAction, preventiveAction);
/* 383 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ArrayListModel getCostList() {
/* 389 */       if (this.costList == null) {
/* 390 */          this.costList = new ArrayListModel();
/*     */ 
/* 392 */          if (((Deviation)this.object).getOrderCosts() != null) {
/* 393 */             this.costList.addAll(((Deviation)this.object).getOrderCosts());
/*     */          }
/*     */       }
/*     */ 
/* 397 */       return new ArrayListModel(this.costList);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCostList(ArrayListModel costs) {
/* 404 */       ArrayListModel oldCosts = this.getCostList();
/* 405 */       if (this.costList == null) {
/* 406 */          this.costList = new ArrayListModel();
/*     */       }
/* 408 */       this.costList.clear();
/* 409 */       this.costList.addAll(costs);
/* 410 */       this.firePropertyChange("costList", oldCosts, costs);
/* 411 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<OrderComment> getComments() {
/* 417 */       return new ArrayList(this.commentList);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setComments(List<OrderComment> comments) {
/* 424 */       List<OrderComment> oldList = new ArrayList(this.commentList);
/* 425 */       this.commentList.clear();
/* 426 */       this.commentList.addAll(comments);
/* 427 */       this.firePropertyChange("comments", oldList, comments);
/* 428 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addComment(OrderComment orderComment) {
/* 434 */       if (orderComment != null) {
/* 435 */          orderComment.setDeviation((Deviation)this.object);
/* 436 */          orderComment.setOrder(((Deviation)this.object).getOrder());
/* 437 */          List<OrderComment> oldList = new ArrayList(this.commentList);
/* 438 */          this.commentList.add(orderComment);
/* 439 */          this.firePropertyChange("comments", oldList, this.commentList);
/*     */       }
/* 441 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getPostShipmentBool() {
/* 447 */       return ((Deviation)this.object).getPostShipment() != null ? Boolean.TRUE : Boolean.FALSE;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPostShipmentBool(Boolean postShipmentBool) {
/* 457 */       if (postShipmentBool) {
/* 458 */          if (((Deviation)this.object).getPostShipment() == null) {
/* 459 */             ((Deviation)this.object).setPostShipment(new PostShipment((Integer)null, (Date)null, ((Deviation)this.object).getOrder(), (Transport)null, (Deviation)this.object, (Date)null, (Date)null, (String)null));
/*     */          }
/*     */       } else {
/* 462 */          ((Deviation)this.object).setPostShipment((PostShipment)null);
/*     */       }
/* 464 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getCanSetPostShipment() {
/* 470 */       return !this.isPostShipment ? this.canSetPostShipment : false;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCanSetPostShipment(Boolean canSetPostShipment) {
/* 480 */       if (this.isPostShipment) {
/* 481 */          this.canSetPostShipment = false;
/* 482 */          this.firePropertyChange("canSetPostShipment", false, false);
/*     */       } else {
/* 484 */          Boolean oldCanSetPostShipment = this.getCanSetPostShipment();
/* 485 */          this.canSetPostShipment = canSetPostShipment;
/* 486 */          this.firePropertyChange("canSetPostShipment", oldCanSetPostShipment, canSetPostShipment);
/*     */       }
/* 488 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Order getOrder() {
/* 494 */       return ((Deviation)this.object).getOrder();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOrder(Order order) {
/* 501 */       Order oldOrder = this.getOrder();
/* 502 */       ((Deviation)this.object).setOrder(order);
/* 503 */       if (order != null && ((Deviation)this.object).getPostShipment() == null && this.orderLineList.size() != 0) {
/* 504 */          this.setCanSetPostShipment(true);
/*     */       } else {
/* 506 */          this.setCanSetPostShipment(false);
/*     */       }
/*     */ 
/* 509 */       if (order != null) {
/* 510 */          this.setCanAddOrderLine(true);
/*     */       } else {
/* 512 */          this.setCanAddOrderLine(false);
/*     */       }
/*     */ 
/* 515 */       this.firePropertyChange("order", oldOrder, order);
/* 516 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ArrayListModel getOrderLineArrayListModel() {
/* 522 */       return new ArrayListModel(this.orderLineList);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOrderLineArrayListModel(ArrayListModel orderLines) {
/* 531 */       ArrayListModel oldOrderLines = this.getOrderLineArrayListModel();
/* 532 */       this.orderLineList.clear();
/* 533 */       if (orderLines != null) {
/* 534 */          this.orderLineList.addAll(orderLines);
/*     */       }
/* 536 */       this.firePropertyChange("orderLineArrayListModel", oldOrderLines, orderLines);
/* 537 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public PostShipment getPostShipment() {
/* 543 */       return ((Deviation)this.object).getPostShipment();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPostShipment(PostShipment postShipment) {
/* 550 */       PostShipment oldShipment = this.getPostShipment();
/* 551 */       ((Deviation)this.object).setPostShipment(postShipment);
/* 552 */       this.firePropertyChange("postShipment", oldShipment, postShipment);
/* 553 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOrderLines(ArrayListModel orderLines) {
/* 559 */       ArrayListModel oldines = new ArrayListModel(this.getOrderLineArrayListModel());
/* 560 */       this.orderLineList.clear();
/* 561 */       if (orderLines != null) {
/* 562 */          this.orderLineList.addAll(orderLines);
/*     */       }
/* 564 */       this.firePropertyChange("orderLineArrayListModel", oldines, orderLines);
/* 565 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getCanAddOrderLine() {
/* 571 */       return this.canAddOrderLine;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCanAddOrderLine(Boolean canAddOrderLine) {
/* 578 */       Boolean oldCanAddOrderLine = this.getCanAddOrderLine();
/* 579 */       this.canAddOrderLine = canAddOrderLine;
/* 580 */       this.firePropertyChange("canAddOrderLine", oldCanAddOrderLine, canAddOrderLine);
/* 581 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getIsPostShipment() {
/* 587 */       return this.isPostShipment;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setIsPostShipment(Boolean isPostShipment) {
/* 594 */       Boolean oldShipment = this.getIsPostShipment();
/* 595 */       this.isPostShipment = isPostShipment;
/* 596 */       this.firePropertyChange("isPostShipment", oldShipment, isPostShipment);
/* 597 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getDoAssemblyBool() {
/* 603 */       return Util.convertNumberToBoolean(((Deviation)this.object).getDoAssembly());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDoAssemblyBool(Boolean doAssembly) {
/* 610 */       Boolean oldAssembly = this.getDoAssemblyBool();
/* 611 */       ((Deviation)this.object).setDoAssembly(Util.convertBooleanToNumber(doAssembly));
/* 612 */       this.firePropertyChange("doAssemblyBool", oldAssembly, doAssembly);
/* 613 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getResponsible() {
/* 619 */       return ((Deviation)this.object).getResponsible();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setResponsible(String responsible) {
/* 626 */       String oldResponsible = this.getResponsible();
/* 627 */       ((Deviation)this.object).setResponsible(responsible);
/* 628 */       this.firePropertyChange("responsible", oldResponsible, responsible);
/* 629 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Date getDateFrom() {
/* 635 */       return ((Deviation)this.object).getDateFrom();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDateFrom(Date dateFrom) {
/* 642 */       Date oldDate = this.getDateFrom();
/* 643 */       ((Deviation)this.object).setDateFrom(dateFrom);
/* 644 */       this.firePropertyChange("dateFrom", oldDate, dateFrom);
/* 645 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Date getDateTo() {
/* 651 */       return ((Deviation)this.object).getDateTo();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDateTo(Date dateTo) {
/* 658 */       Date oldDate = this.getDateTo();
/* 659 */       ((Deviation)this.object).setDateTo(dateTo);
/* 660 */       this.firePropertyChange("dateTo", oldDate, dateTo);
/* 661 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getCheckedBool() {
/* 667 */       return Util.convertNumberToBoolean(((Deviation)this.object).getChecked());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCheckedBool(Boolean checked) {
/* 674 */       Boolean oldChecked = this.getCheckedBool();
/* 675 */       ((Deviation)this.object).setChecked(Util.convertBooleanToNumber(checked));
/* 676 */       this.firePropertyChange("checkedBool", oldChecked, checked);
/* 677 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getInitiatedBy() {
/* 683 */       return ((Deviation)this.object).getInitiatedBy();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setInitiatedBy(String initiatedBy) {
/* 690 */       String oldInitiated = this.getInitiatedBy();
/* 691 */       ((Deviation)this.object).setInitiatedBy(initiatedBy);
/* 692 */       this.firePropertyChange("initiatedBy", oldInitiated, initiatedBy);
/* 693 */    }
/*     */ 
/*     */    public String getCsId() {
/* 696 */       return ((Deviation)this.object).getCsId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCsId(String csId) {
/* 703 */       String oldCsId = this.getCsId();
/* 704 */       ((Deviation)this.object).setCsId(csId);
/* 705 */       this.firePropertyChange("csId", oldCsId, csId);
/* 706 */    }
/*     */ 
/*     */    public String getProjectNr() {
/* 709 */       return ((Deviation)this.object).getProjectNr();
/*     */    }
/*     */ 
/*     */    public void setProjectNr(String projectNr) {
/* 713 */       String oldNr = this.getProjectNr();
/* 714 */       ((Deviation)this.object).setProjectNr(projectNr);
/* 715 */       this.firePropertyChange("projectNr", oldNr, projectNr);
/* 716 */    }
/*     */ 
/*     */    public Date getProcedureCheck() {
/* 719 */       return ((Deviation)this.object).getProcedureCheck();
/*     */    }
/*     */ 
/*     */    public void setProcedureCheck(Date procedureCheck) {
/* 723 */       Date oldCheck = this.getProcedureCheck();
/* 724 */       ((Deviation)this.object).setProcedureCheck(procedureCheck);
/* 725 */       this.firePropertyChange("procedureCheck", oldCheck, procedureCheck);
/* 726 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getRegistrationDateString() {
/* 732 */       return ((Deviation)this.object).getRegistrationDate() != null ? Util.SHORT_DATE_FORMAT.format(((Deviation)this.object).getRegistrationDate()) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setRegistrationDateString(String stringDate) {
/* 742 */       String oldDate = this.getRegistrationDateString();
/* 743 */       if (stringDate != null) {
/*     */          try {
/* 745 */             ((Deviation)this.object).setRegistrationDate(Util.SHORT_DATE_FORMAT.parse(stringDate));
/* 746 */          } catch (ParseException var4) {
/*     */ 
/* 748 */             var4.printStackTrace();
/*     */          }
/*     */       } else {
/* 751 */          ((Deviation)this.object).setRegistrationDate((Date)null);
/*     */       }
/*     */ 
/* 754 */       this.firePropertyChange("registrationDateString", oldDate, stringDate);
/* 755 */    }
/*     */ 
/*     */    public String getDateClosedString() {
/* 758 */       return ((Deviation)this.object).getDateClosed() != null ? Util.SHORT_DATE_FORMAT.format(((Deviation)this.object).getDateClosed()) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDateClosedString(String stringDate) {
/* 768 */       String oldDate = this.getDateClosedString();
/* 769 */       if (stringDate != null) {
/*     */          try {
/* 771 */             ((Deviation)this.object).setDateClosed(Util.SHORT_DATE_FORMAT.parse(stringDate));
/* 772 */          } catch (ParseException var4) {
/*     */ 
/* 774 */             var4.printStackTrace();
/*     */          }
/*     */       } else {
/* 777 */          ((Deviation)this.object).setDateClosed((Date)null);
/*     */       }
/*     */ 
/* 780 */       this.firePropertyChange("dateClosedString", oldDate, stringDate);
/* 781 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void viewToModel() {
/* 789 */       Set<OrderComment> comments = ((Deviation)this.object).getOrderComments();
/* 790 */       if (comments == null) {
/* 791 */          comments = new HashSet();
/*     */       }
/* 793 */       ((Set)comments).clear();
/* 794 */       ((Set)comments).addAll(this.commentList);
/* 795 */       ((Deviation)this.object).setOrderComments((Set)comments);
/*     */ 
/*     */ 
/*     */       Object lines;
/* 799 */       if (((Deviation)this.object).getPostShipment() == null) {
/* 800 */          Set<OrderLine> orderLines = ((Deviation)this.object).getOrderLines();
/* 801 */          if (orderLines == null) {
/* 802 */             orderLines = new HashSet();
/*     */          }
/* 804 */          ((Set)orderLines).clear();
/* 805 */          ((Set)orderLines).addAll(this.orderLineList);
/* 806 */          ((Deviation)this.object).setOrderLines((Set)orderLines);
/*     */ 
/*     */       } else {
/* 809 */          PostShipment postShipment = ((Deviation)this.object).getPostShipment();
/* 810 */          lines = postShipment.getOrderLines();
/* 811 */          if (lines == null) {
/* 812 */             lines = new HashSet();
/*     */          }
/* 814 */          ((Set)lines).clear();
/* 815 */          ((Set)lines).addAll(this.orderLineList);
/* 816 */          postShipment.setOrderLines((Set)lines);
/*     */       }
/*     */ 
/* 819 */       if (this.costList != null) {
/* 820 */          Iterator it = this.costList.iterator();
/* 821 */          while(it.hasNext()) {
/* 822 */             ((OrderCost)it.next()).setOrder(((Deviation)this.object).getOrder());
/*     */          }
/* 824 */          lines = ((Deviation)this.object).getOrderCosts();
/* 825 */          if (lines == null) {
/* 826 */             lines = new HashSet();
/*     */          }
/* 828 */          ((Set)lines).clear();
/* 829 */          ((Set)lines).addAll(this.costList);
/* 830 */          ((Deviation)this.object).setOrderCosts((Set)lines);
/*     */       }
/*     */ 
/* 833 */       super.viewToModel();
/* 834 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 842 */       presentationModel.getBufferedModel("userName").addValueChangeListener(listener);
/* 843 */       presentationModel.getBufferedModel("description").addValueChangeListener(listener);
/* 844 */       presentationModel.getBufferedModel("ownFunction").addValueChangeListener(listener);
/* 845 */       presentationModel.getBufferedModel("deviationFunction").addValueChangeListener(listener);
/* 846 */       presentationModel.getBufferedModel("customerNr").addValueChangeListener(listener);
/* 847 */       presentationModel.getBufferedModel("orderNr").addValueChangeListener(listener);
/* 848 */       presentationModel.getBufferedModel("deviationStatus").addValueChangeListener(listener);
/* 849 */       presentationModel.getBufferedModel("dateClosed").addValueChangeListener(listener);
/* 850 */       presentationModel.getBufferedModel("productArea").addValueChangeListener(listener);
/* 851 */       presentationModel.getBufferedModel("functionCategory").addValueChangeListener(listener);
/* 852 */       presentationModel.getBufferedModel("preventiveAction").addValueChangeListener(listener);
/* 853 */       presentationModel.getBufferedModel("customerName").addValueChangeListener(listener);
/* 854 */       presentationModel.getBufferedModel("deviationId").addValueChangeListener(listener);
/* 855 */       presentationModel.getBufferedModel("postShipmentBool").addValueChangeListener(listener);
/* 856 */       presentationModel.getBufferedModel("order").addValueChangeListener(listener);
/* 857 */       presentationModel.getBufferedModel("canSetPostShipment").addValueChangeListener(listener);
/* 858 */       presentationModel.getBufferedModel("postShipment").addValueChangeListener(listener);
/* 859 */       presentationModel.getBufferedModel("canAddOrderLine").addValueChangeListener(listener);
/* 860 */       presentationModel.getBufferedModel("isPostShipment").addValueChangeListener(listener);
/* 861 */       presentationModel.getBufferedModel("doAssemblyBool").addValueChangeListener(listener);
/* 862 */       presentationModel.getBufferedModel("responsible").addValueChangeListener(listener);
/* 863 */       presentationModel.getBufferedModel("dateFrom").addValueChangeListener(listener);
/* 864 */       presentationModel.getBufferedModel("dateTo").addValueChangeListener(listener);
/* 865 */       presentationModel.getBufferedModel("checkedBool").addValueChangeListener(listener);
/* 866 */       presentationModel.getBufferedModel("initiatedBy").addValueChangeListener(listener);
/* 867 */       presentationModel.getBufferedModel("registrationDateString").addValueChangeListener(listener);
/* 868 */       presentationModel.getBufferedModel("comments").addValueChangeListener(listener);
/* 869 */       presentationModel.getBufferedModel("projectNr").addValueChangeListener(listener);
/* 870 */       presentationModel.getBufferedModel("procedureCheck").addValueChangeListener(listener);
/* 871 */       presentationModel.getBufferedModel("dateClosedString").addValueChangeListener(listener);
/* 872 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public DeviationModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 879 */       DeviationModel deviationModel = new DeviationModel(new Deviation(), this.isPostShipment);
/* 880 */       deviationModel.setUserName((String)presentationModel.getBufferedValue("userName"));
/* 881 */       deviationModel.setDescription((String)presentationModel.getBufferedValue("description"));
/* 882 */       deviationModel.setOwnFunction((JobFunction)presentationModel.getBufferedValue("ownFunction"));
/* 883 */       deviationModel.setDeviationFunction((JobFunction)presentationModel.getBufferedValue("deviationFunction"));
/* 884 */       deviationModel.setCustomerNr((Integer)presentationModel.getBufferedValue("customerNr"));
/* 885 */       deviationModel.setOrderNr((String)presentationModel.getBufferedValue("orderNr"));
/*     */ 
/*     */ 
/* 888 */       deviationModel.setDeviationStatus((DeviationStatus)presentationModel.getBufferedValue("deviationStatus"));
/* 889 */       deviationModel.setDateClosed((Date)presentationModel.getBufferedValue("dateClosed"));
/* 890 */       deviationModel.setProductArea((ProductArea)presentationModel.getBufferedValue("productArea"));
/* 891 */       deviationModel.setFunctionCategory((FunctionCategory)presentationModel.getBufferedValue("functionCategory"));
/* 892 */       deviationModel.setPreventiveAction((PreventiveAction)presentationModel.getBufferedValue("preventiveAction"));
/* 893 */       deviationModel.setCustomerName((String)presentationModel.getBufferedValue("customerName"));
/* 894 */       deviationModel.setDeviationId((String)presentationModel.getBufferedValue("deviationId"));
/* 895 */       deviationModel.setPostShipmentBool((Boolean)presentationModel.getBufferedValue("postShipmentBool"));
/* 896 */       deviationModel.setOrder((Order)presentationModel.getBufferedValue("order"));
/* 897 */       deviationModel.setCanSetPostShipment((Boolean)presentationModel.getBufferedValue("canSetPostShipment"));
/* 898 */       deviationModel.setPostShipment((PostShipment)presentationModel.getBufferedValue("postShipment"));
/* 899 */       deviationModel.setCanAddOrderLine((Boolean)presentationModel.getBufferedValue("canAddOrderLine"));
/* 900 */       deviationModel.setIsPostShipment((Boolean)presentationModel.getBufferedValue("isPostShipment"));
/* 901 */       deviationModel.setDoAssemblyBool((Boolean)presentationModel.getBufferedValue("doAssemblyBool"));
/* 902 */       deviationModel.setResponsible((String)presentationModel.getBufferedValue("responsible"));
/* 903 */       deviationModel.setDateFrom((Date)presentationModel.getBufferedValue("dateFrom"));
/* 904 */       deviationModel.setDateTo((Date)presentationModel.getBufferedValue("dateTo"));
/* 905 */       deviationModel.setCheckedBool((Boolean)presentationModel.getBufferedValue("checkedBool"));
/* 906 */       deviationModel.setInitiatedBy((String)presentationModel.getBufferedValue("initiatedBy"));
/* 907 */       deviationModel.setRegistrationDateString((String)presentationModel.getBufferedValue("registrationDateString"));
/* 908 */       deviationModel.setComments((List)presentationModel.getBufferedValue("comments"));
/* 909 */       deviationModel.setProjectNr((String)presentationModel.getBufferedValue("projectNr"));
/* 910 */       deviationModel.setProcedureCheck((Date)presentationModel.getBufferedValue("procedureCheck"));
/* 911 */       deviationModel.setDateClosedString((String)presentationModel.getBufferedValue("dateClosedString"));
/* 912 */       return deviationModel;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Deviation getDeviation() {
/* 919 */       return (Deviation)this.object;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<ArticleType> getArticles() {
/* 926 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getCustomerFirstName() {
/* 933 */       return ((Deviation)this.object).getOrder() != null ? ((Deviation)this.object).getOrder().getCustomer().getFirstName() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getCustomerLastName() {
/* 943 */       return ((Deviation)this.object).getOrder() != null ? ((Deviation)this.object).getOrder().getCustomer().getLastName() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDeliveryAddress() {
/* 953 */       return ((Deviation)this.object).getOrder() != null ? ((Deviation)this.object).getOrder().getDeliveryAddress() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPostOffice() {
/* 963 */       return ((Deviation)this.object).getOrder() != null ? ((Deviation)this.object).getOrder().getPostOffice() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPostalCode() {
/* 973 */       return ((Deviation)this.object).getOrder() != null ? ((Deviation)this.object).getOrder().getPostalCode() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getLastComment() {
/* 983 */       if (this.commentList.size() > 0) {
/* 984 */          OrderComment comment = (OrderComment)this.commentList.get(0);
/*     */ 
/* 986 */          return comment.getComment();
/*     */       } else {
/* 988 */          return null;
/*     */       }
/*     */    }
/*     */    public static void setRegistrationAndChangeDate(Deviation deviation, ApplicationUser applicationUser) {
/* 992 */       if (deviation.getDeviationId() == null) {
/* 993 */          deviation.setRegistrationDate(Calendar.getInstance().getTime());
/*     */       }
/* 995 */       deviation.setLastChanged(Util.getCurrentDateAsDateString() + ":" + applicationUser.getUserName());
/* 996 */    }
/*     */ }
