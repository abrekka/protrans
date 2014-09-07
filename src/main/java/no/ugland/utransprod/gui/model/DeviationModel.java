package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.DeviationStatus;
import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.PreventiveAction;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.ArrayListModel;

/**
 * Viewmodell for avvik
 * 
 * @author atle.brekka
 */
public class DeviationModel extends AbstractModel<Deviation, DeviationModel> implements ICostableModel<Deviation, DeviationModel> {
    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_DEVIATION_ID = "deviationId";

    public static final String PROPERTY_USER_NAME = "userName";

    public static final String PROPERTY_DESCRIPTION = "description";

    public static final String PROPERTY_OWN_FUNCTION = "ownFunction";

    public static final String PROPERTY_DEVIATION_FUNCTION = "deviationFunction";

    public static final String PROPERTY_CUSTOMER_NR = "customerNr";

    public static final String PROPERTY_CUSTOMER_NAME = "customerName";

    public static final String PROPERTY_ORDER_NR = "orderNr";

    public static final String PROPERTY_DEVIATION_STATUS = "deviationStatus";

    public static final String PROPERTY_DATE_CLOSED = "dateClosed";

    public static final String PROPERTY_PRODUCT_AREA = "productArea";

    public static final String PROPERTY_FUNCTION_CATEGORY = "functionCategory";

    public static final String PROPERTY_COMMENTS = "comments";

    public static final String PROPERTY_POST_SHIPMENT_BOOL = "postShipmentBool";

    public static final String PROPERTY_CAN_SET_POST_SHIPMENT = "canSetPostShipment";

    public static final String PROPERTY_ORDER = "order";

    public static final String PROPERTY_POST_SHIPMENT = "postShipment";

    public static final String PROPERTY_CAN_ADD_ORDER_LINE = "canAddOrderLine";

    public static final String PROPERTY_IS_POST_SHIPMENT = "isPostShipment";

    public static final String PROPERTY_PREVENTIVE_ACTION = "preventiveAction";

    public static final String PROPERTY_LAST_COMMENT = "lastComment";

    public static final String PROPERTY_DO_ASSEMBLY_BOOL = "doAssemblyBool";

    public static final String PROPERTY_RESPONSIBLE = "responsible";

    public static final String PROPERTY_DATE_FROM = "dateFrom";

    public static final String PROPERTY_DATE_TO = "dateTo";

    public static final String PROPERTY_CHECKED_BOOL = "checkedBool";

    public static final String PROPERTY_INITIATED_BY = "initiatedBy";

    public static final String PROPERTY_MOD_COUNT = "modCount";

    public static final String PROPERTY_REGISTRATION_DATE_STRING = "registrationDateString";

    public static final String PROPERTY_PROJECT_NR = "projectNr";
    public static final String PROPERTY_PROCEDURE_CHECK = "procedureCheck";
    public static final String PROPERTY_DATE_CLOSED_STRING = "dateClosedString";
    public static final String PROPERTY_PROBABILITY = "probability";

    private final List<OrderComment> commentList;

    private Boolean canSetPostShipment = false;

    private Boolean canAddOrderLine = false;

    private final ArrayListModel orderLineList;

    private boolean isPostShipment = false;

    private ArrayListModel costList = null;

    private Integer modCount;

    /**
     * @param deviation
     * @param isPostShipment
     */
    public DeviationModel(Deviation deviation, boolean isPostShipment) {
	super(deviation);
	this.isPostShipment = isPostShipment;
	commentList = new ArrayList<OrderComment>();
	if (deviation.getOrderComments() != null) {
	    commentList.addAll(deviation.getOrderComments());
	}
	orderLineList = new ArrayListModel();
	if (deviation.getPostShipment() != null && deviation.getPostShipment().getOrderLines() != null) {
	    orderLineList.addAll(deviation.getPostShipment().getOrderLines());
	} else if (deviation.getOrderLines() != null) {
	    orderLineList.addAll(deviation.getOrderLines());
	}
	if (object.getOrder() != null && orderLineList.size() != 0 && (deviation.getDeviationId() == null || deviation.getPostShipment() == null)) {
	    canSetPostShipment = true;
	}
	if (object.getOrder() != null) {
	    canAddOrderLine = true;
	}

    }

    public Integer getProbability() {
	return 100;
    }

    /**
     * Brukes for å sette at modell har buffering ved setting av beløp direkte i
     * tabell for kostnader
     * 
     * @return endringsantall
     */
    public Integer getModCount() {
	return modCount;
    }

    /**
     * @param modCount
     */
    public void setModCount(Integer modCount) {
	Integer oldMod = getModCount();
	this.modCount = modCount;
	firePropertyChange(PROPERTY_MOD_COUNT, oldMod, modCount);
    }

    /**
     * @return brukernavn
     */
    public String getUserName() {
	return object.getUserName();
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
	String oldName = getUserName();
	object.setUserName(userName);
	firePropertyChange(PROPERTY_USER_NAME, oldName, userName);

    }

    /**
     * @return avviksid
     */
    public String getDeviationId() {
	Integer id = object.getDeviationId();
	if (id != null) {
	    return id.toString();
	}
	return null;
    }

    /**
     * @param deviationId
     */
    public void setDeviationId(String deviationId) {
	String oldId = getDeviationId();
	if (deviationId != null) {
	    object.setDeviationId(Integer.valueOf(deviationId));
	} else {
	    object.setDeviationId(null);
	}
	firePropertyChange(PROPERTY_DEVIATION_ID, oldId, deviationId);
    }

    /**
     * @return beskrivelse
     */
    public String getDescription() {
	return object.getDescription();
    }

    /**
     * @param desc
     */
    public void setDescription(String desc) {
	String oldDesc = getDescription();
	object.setDescription(desc);
	firePropertyChange(PROPERTY_DESCRIPTION, oldDesc, desc);
    }

    /**
     * @return egen funksjon
     */
    public JobFunction getOwnFunction() {
	return object.getOwnFunction();
    }

    /**
     * @param ownFunction
     */
    public void setOwnFunction(JobFunction ownFunction) {
	JobFunction oldFunction = getOwnFunction();
	object.setOwnFunction(ownFunction);
	firePropertyChange(PROPERTY_OWN_FUNCTION, oldFunction, ownFunction);
    }

    /**
     * @return avviksfunksjon
     */
    public JobFunction getDeviationFunction() {
	return object.getDeviationFunction();
    }

    /**
     * @param deviationFunction
     */
    public void setDeviationFunction(JobFunction deviationFunction) {
	JobFunction oldFunction = getDeviationFunction();
	object.setDeviationFunction(deviationFunction);
	firePropertyChange(PROPERTY_DEVIATION_FUNCTION, oldFunction, deviationFunction);
    }

    /**
     * @return kundenr
     */
    public Integer getCustomerNr() {
	return object.getCustomerNr();
    }

    /**
     * @param customerNr
     */
    public void setCustomerNr(Integer customerNr) {
	Integer oldNr = getCustomerNr();
	object.setCustomerNr(customerNr);
	firePropertyChange(PROPERTY_CUSTOMER_NR, oldNr, customerNr);
    }

    /**
     * @return kundenavn
     */
    public String getCustomerName() {
	return object.getCustomerName();
    }

    /**
     * @param customerName
     */
    public void setCustomerName(String customerName) {
	String oldName = getCustomerName();
	object.setCustomerName(customerName);
	firePropertyChange(PROPERTY_CUSTOMER_NAME, oldName, customerName);
    }

    /**
     * @return ordrenummer
     */
    public String getOrderNr() {
	return object.getOrderNr();
    }

    /**
     * @param orderNr
     */
    public void setOrderNr(String orderNr) {
	String oldNr = getOrderNr();

	object.setOrderNr(orderNr);
	firePropertyChange(PROPERTY_ORDER_NR, oldNr, orderNr);
    }

    /**
     * @return avviksstatus
     */
    public DeviationStatus getDeviationStatus() {
	return object.getDeviationStatus();
    }

    /**
     * @param deviationStatus
     */
    public void setDeviationStatus(DeviationStatus deviationStatus) {
	DeviationStatus oldStatus = getDeviationStatus();
	object.setDeviationStatus(deviationStatus);
	firePropertyChange(PROPERTY_DEVIATION_STATUS, oldStatus, deviationStatus);
    }

    /**
     * @return avsluttet dato
     */
    public Date getDateClosed() {
	return object.getDateClosed();
    }

    /**
     * @param dateClosed
     */
    public void setDateClosed(Date dateClosed) {
	Date oldDate = getDateClosed();
	object.setDateClosed(dateClosed);
	firePropertyChange(PROPERTY_DATE_CLOSED, oldDate, dateClosed);
    }

    /**
     * @return produktområde
     */
    public ProductArea getProductArea() {
	return object.getProductArea();
    }

    /**
     * @param productArea
     */
    public void setProductArea(ProductArea productArea) {
	ProductArea oldArea = getProductArea();
	object.setProductArea(productArea);
	firePropertyChange(PROPERTY_PRODUCT_AREA, oldArea, productArea);
    }

    /**
     * @return kategori
     */
    public FunctionCategory getFunctionCategory() {
	return object.getFunctionCategory();
    }

    /**
     * @param functionCategory
     */
    public void setFunctionCategory(FunctionCategory functionCategory) {
	FunctionCategory oldCategory = getFunctionCategory();
	object.setFunctionCategory(functionCategory);
	firePropertyChange(PROPERTY_FUNCTION_CATEGORY, oldCategory, functionCategory);
    }

    /**
     * @return prevantivt tiltak
     */
    public PreventiveAction getPreventiveAction() {
	return object.getPreventiveAction();
    }

    /**
     * @param preventiveAction
     */
    public void setPreventiveAction(PreventiveAction preventiveAction) {
	PreventiveAction oldAction = getPreventiveAction();
	object.setPreventiveAction(preventiveAction);

	firePropertyChange(PROPERTY_PREVENTIVE_ACTION, oldAction, preventiveAction);
    }

    /**
     * @return kostnader
     */
    public ArrayListModel getCostList() {
	if (costList == null) {
	    costList = new ArrayListModel();

	    if (object.getOrderCosts() != null) {
		costList.addAll(object.getOrderCosts());
	    }
	}

	return new ArrayListModel(costList);
    }

    /**
     * @param costs
     */
    public void setCostList(ArrayListModel costs) {
	ArrayListModel oldCosts = getCostList();
	if (costList == null) {
	    costList = new ArrayListModel();
	}
	this.costList.clear();
	this.costList.addAll(costs);
	firePropertyChange(PROPERTY_COSTS, oldCosts, costs);
    }

    /**
     * @return kommentarer
     */
    public List<OrderComment> getComments() {
	return new ArrayList<OrderComment>(commentList);
    }

    /**
     * @param comments
     */
    public void setComments(List<OrderComment> comments) {
	List<OrderComment> oldList = new ArrayList<OrderComment>(commentList);
	commentList.clear();
	commentList.addAll(comments);
	firePropertyChange(PROPERTY_COMMENTS, oldList, comments);
    }

    /**
     * @param orderComment
     */
    public void addComment(OrderComment orderComment) {
	if (orderComment != null) {
	    orderComment.setDeviation(object);
	    orderComment.setOrder(object.getOrder());
	    List<OrderComment> oldList = new ArrayList<OrderComment>(commentList);
	    commentList.add(orderComment);
	    firePropertyChange(PROPERTY_COMMENTS, oldList, commentList);
	}
    }

    /**
     * @return true dersom etterlevering
     */
    public Boolean getPostShipmentBool() {
	if (object.getPostShipment() != null) {
	    return Boolean.TRUE;
	}
	return Boolean.FALSE;
    }

    /**
     * @param postShipmentBool
     */
    public void setPostShipmentBool(Boolean postShipmentBool) {
	if (postShipmentBool) {
	    if (object.getPostShipment() == null) {
		object.setPostShipment(new PostShipment(null, null, object.getOrder(), null, object, null, null, null));
	    }
	} else {
	    object.setPostShipment(null);
	}
    }

    /**
     * @return true dersom avvik kan settes som etterlevering
     */
    public Boolean getCanSetPostShipment() {
	if (!isPostShipment) {
	    return canSetPostShipment;
	}
	return false;
    }

    /**
     * @param canSetPostShipment
     */
    public void setCanSetPostShipment(Boolean canSetPostShipment) {
	if (isPostShipment) {
	    this.canSetPostShipment = false;
	    firePropertyChange(PROPERTY_CAN_SET_POST_SHIPMENT, false, false);
	} else {
	    Boolean oldCanSetPostShipment = getCanSetPostShipment();
	    this.canSetPostShipment = canSetPostShipment;
	    firePropertyChange(PROPERTY_CAN_SET_POST_SHIPMENT, oldCanSetPostShipment, canSetPostShipment);
	}
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getOrder()
     */
    public Order getOrder() {
	return object.getOrder();
    }

    /**
     * @param order
     */
    public void setOrder(Order order) {
	Order oldOrder = getOrder();
	object.setOrder(order);
	if ((order != null && object.getPostShipment() == null && orderLineList.size() != 0)) {
	    setCanSetPostShipment(true);
	} else {
	    setCanSetPostShipment(false);
	}

	if (order != null) {
	    setCanAddOrderLine(true);
	} else {
	    setCanAddOrderLine(false);
	}

	firePropertyChange(PROPERTY_ORDER, oldOrder, order);
    }

    /**
     * @return ordrelinjer
     */
    public ArrayListModel getOrderLineArrayListModel() {
	return new ArrayListModel(orderLineList);
    }

    /**
     * Setter ordrelinjer
     * 
     * @param orderLines
     */
    public void setOrderLineArrayListModel(ArrayListModel orderLines) {
	ArrayListModel oldOrderLines = getOrderLineArrayListModel();
	this.orderLineList.clear();
	if (orderLines != null) {
	    this.orderLineList.addAll(orderLines);
	}
	firePropertyChange(PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL, oldOrderLines, orderLines);
    }

    /**
     * @return etterlevering
     */
    public PostShipment getPostShipment() {
	return object.getPostShipment();
    }

    /**
     * @param postShipment
     */
    public void setPostShipment(PostShipment postShipment) {
	PostShipment oldShipment = getPostShipment();
	object.setPostShipment(postShipment);
	firePropertyChange(PROPERTY_POST_SHIPMENT, oldShipment, postShipment);
    }

    /**
     * @param orderLines
     */
    public void setOrderLines(ArrayListModel orderLines) {
	ArrayListModel oldines = new ArrayListModel(getOrderLineArrayListModel());
	this.orderLineList.clear();
	if (orderLines != null) {
	    this.orderLineList.addAll(orderLines);
	}
	firePropertyChange(PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL, oldines, orderLines);
    }

    /**
     * @return true dersom man kan legge til ordrelinjer
     */
    public Boolean getCanAddOrderLine() {
	return canAddOrderLine;
    }

    /**
     * @param canAddOrderLine
     */
    public void setCanAddOrderLine(Boolean canAddOrderLine) {
	Boolean oldCanAddOrderLine = getCanAddOrderLine();
	this.canAddOrderLine = canAddOrderLine;
	firePropertyChange(PROPERTY_CAN_ADD_ORDER_LINE, oldCanAddOrderLine, canAddOrderLine);
    }

    /**
     * @return true dersom avvik er etterlevering
     */
    public Boolean getIsPostShipment() {
	return isPostShipment;
    }

    /**
     * @param isPostShipment
     */
    public void setIsPostShipment(Boolean isPostShipment) {
	Boolean oldShipment = getIsPostShipment();
	this.isPostShipment = isPostShipment;
	firePropertyChange(PROPERTY_IS_POST_SHIPMENT, oldShipment, isPostShipment);
    }

    /**
     * @return true dersom avvik skal monteres
     */
    public Boolean getDoAssemblyBool() {
	return Util.convertNumberToBoolean(object.getDoAssembly());
    }

    /**
     * @param doAssembly
     */
    public void setDoAssemblyBool(Boolean doAssembly) {
	Boolean oldAssembly = getDoAssemblyBool();
	object.setDoAssembly(Util.convertBooleanToNumber(doAssembly));
	firePropertyChange(PROPERTY_DO_ASSEMBLY_BOOL, oldAssembly, doAssembly);
    }

    /**
     * @return behandlingsansvarlig
     */
    public String getResponsible() {
	return object.getResponsible();
    }

    /**
     * @param responsible
     */
    public void setResponsible(String responsible) {
	String oldResponsible = getResponsible();
	object.setResponsible(responsible);
	firePropertyChange(PROPERTY_RESPONSIBLE, oldResponsible, responsible);
    }

    /**
     * @return fra dato
     */
    public Date getDateFrom() {
	return object.getDateFrom();
    }

    /**
     * @param dateFrom
     */
    public void setDateFrom(Date dateFrom) {
	Date oldDate = getDateFrom();
	object.setDateFrom(dateFrom);
	firePropertyChange(PROPERTY_DATE_FROM, oldDate, dateFrom);
    }

    /**
     * @return til dato
     */
    public Date getDateTo() {
	return object.getDateTo();
    }

    /**
     * @param dateTo
     */
    public void setDateTo(Date dateTo) {
	Date oldDate = getDateTo();
	object.setDateTo(dateTo);
	firePropertyChange(PROPERTY_DATE_TO, oldDate, dateTo);
    }

    /**
     * @return true dersom kontrollert
     */
    public Boolean getCheckedBool() {
	return Util.convertNumberToBoolean(object.getChecked());
    }

    /**
     * @param checked
     */
    public void setCheckedBool(Boolean checked) {
	Boolean oldChecked = getCheckedBool();
	object.setChecked(Util.convertBooleanToNumber(checked));
	firePropertyChange(PROPERTY_CHECKED_BOOL, oldChecked, checked);
    }

    /**
     * @return initiert av
     */
    public String getInitiatedBy() {
	return object.getInitiatedBy();
    }

    /**
     * @param initiatedBy
     */
    public void setInitiatedBy(String initiatedBy) {
	String oldInitiated = getInitiatedBy();
	object.setInitiatedBy(initiatedBy);
	firePropertyChange(PROPERTY_INITIATED_BY, oldInitiated, initiatedBy);
    }

    public String getProjectNr() {
	return object.getProjectNr();
    }

    public void setProjectNr(String projectNr) {
	String oldNr = getProjectNr();
	object.setProjectNr(projectNr);
	firePropertyChange(PROPERTY_PROJECT_NR, oldNr, projectNr);
    }

    public Date getProcedureCheck() {
	return object.getProcedureCheck();
    }

    public void setProcedureCheck(Date procedureCheck) {
	Date oldCheck = getProcedureCheck();
	object.setProcedureCheck(procedureCheck);
	firePropertyChange(PROPERTY_PROCEDURE_CHECK, oldCheck, procedureCheck);
    }

    /**
     * @return registreringsdato som dtreng
     */
    public String getRegistrationDateString() {
	if (object.getRegistrationDate() != null) {
	    return Util.SHORT_DATE_FORMAT.format(object.getRegistrationDate());
	}
	return null;
    }

    /**
     * @param stringDate
     */
    public void setRegistrationDateString(String stringDate) {
	String oldDate = getRegistrationDateString();
	if (stringDate != null) {
	    try {
		object.setRegistrationDate(Util.SHORT_DATE_FORMAT.parse(stringDate));
	    } catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	} else {
	    object.setRegistrationDate(null);
	}

	firePropertyChange(PROPERTY_REGISTRATION_DATE_STRING, oldDate, stringDate);
    }

    public String getDateClosedString() {
	if (object.getDateClosed() != null) {
	    return Util.SHORT_DATE_FORMAT.format(object.getDateClosed());
	}
	return null;
    }

    /**
     * @param stringDate
     */
    public void setDateClosedString(String stringDate) {
	String oldDate = getDateClosedString();
	if (stringDate != null) {
	    try {
		object.setDateClosed(Util.SHORT_DATE_FORMAT.parse(stringDate));
	    } catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	} else {
	    object.setDateClosed(null);
	}

	firePropertyChange(PROPERTY_DATE_CLOSED_STRING, oldDate, stringDate);
    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#viewToModel()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void viewToModel() {
	Set<OrderComment> comments = object.getOrderComments();
	if (comments == null) {
	    comments = new HashSet<OrderComment>();
	}
	comments.clear();
	comments.addAll(commentList);
	object.setOrderComments(comments);

	// dersom avvik ikke har etterlevering, dersom den har det må
	// ordrelinjer ligge på etterlevering
	if (object.getPostShipment() == null) {
	    Set<OrderLine> orderLines = object.getOrderLines();
	    if (orderLines == null) {
		orderLines = new HashSet<OrderLine>();
	    }
	    orderLines.clear();
	    orderLines.addAll(orderLineList);
	    object.setOrderLines(orderLines);

	} else {
	    PostShipment postShipment = object.getPostShipment();
	    Set<OrderLine> orderLines = postShipment.getOrderLines();
	    if (orderLines == null) {
		orderLines = new HashSet<OrderLine>();
	    }
	    orderLines.clear();
	    orderLines.addAll(orderLineList);
	    postShipment.setOrderLines(orderLines);
	}

	if (costList != null) {
	    Iterator<OrderCost> it = costList.iterator();
	    while (it.hasNext()) {
		it.next().setOrder(object.getOrder());
	    }
	    Set<OrderCost> lines = object.getOrderCosts();
	    if (lines == null) {
		lines = new HashSet<OrderCost>();
	    }
	    lines.clear();
	    lines.addAll(costList);
	    object.setOrderCosts(lines);
	}

	super.viewToModel();
    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
     *      com.jgoodies.binding.PresentationModel)
     */
    @Override
    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
	presentationModel.getBufferedModel(PROPERTY_USER_NAME).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_DESCRIPTION).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_OWN_FUNCTION).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_DEVIATION_FUNCTION).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_CUSTOMER_NR).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_ORDER_NR).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_DEVIATION_STATUS).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_DATE_CLOSED).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_PRODUCT_AREA).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_FUNCTION_CATEGORY).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_PREVENTIVE_ACTION).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_CUSTOMER_NAME).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_DEVIATION_ID).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_POST_SHIPMENT_BOOL).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_ORDER).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_CAN_SET_POST_SHIPMENT).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_POST_SHIPMENT).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_CAN_ADD_ORDER_LINE).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_IS_POST_SHIPMENT).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_DO_ASSEMBLY_BOOL).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_RESPONSIBLE).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_DATE_FROM).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_DATE_TO).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_CHECKED_BOOL).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_INITIATED_BY).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_REGISTRATION_DATE_STRING).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_COMMENTS).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_PROJECT_NR).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_PROCEDURE_CHECK).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_DATE_CLOSED_STRING).addValueChangeListener(listener);
    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
     */
    @Override
    public DeviationModel getBufferedObjectModel(PresentationModel presentationModel) {
	DeviationModel deviationModel = new DeviationModel(new Deviation(), isPostShipment);
	deviationModel.setUserName((String) presentationModel.getBufferedValue(PROPERTY_USER_NAME));
	deviationModel.setDescription((String) presentationModel.getBufferedValue(PROPERTY_DESCRIPTION));
	deviationModel.setOwnFunction((JobFunction) presentationModel.getBufferedValue(PROPERTY_OWN_FUNCTION));
	deviationModel.setDeviationFunction((JobFunction) presentationModel.getBufferedValue(PROPERTY_DEVIATION_FUNCTION));
	deviationModel.setCustomerNr((Integer) presentationModel.getBufferedValue(PROPERTY_CUSTOMER_NR));
	deviationModel.setOrderNr((String) presentationModel.getBufferedValue(PROPERTY_ORDER_NR));
	// deviationModel.setOrderComplete((String)
	// presentationModel.getBufferedValue(PROPERTY_ORDER_COMPLETE));
	deviationModel.setDeviationStatus((DeviationStatus) presentationModel.getBufferedValue(PROPERTY_DEVIATION_STATUS));
	deviationModel.setDateClosed((Date) presentationModel.getBufferedValue(PROPERTY_DATE_CLOSED));
	deviationModel.setProductArea((ProductArea) presentationModel.getBufferedValue(PROPERTY_PRODUCT_AREA));
	deviationModel.setFunctionCategory((FunctionCategory) presentationModel.getBufferedValue(PROPERTY_FUNCTION_CATEGORY));
	deviationModel.setPreventiveAction((PreventiveAction) presentationModel.getBufferedValue(PROPERTY_PREVENTIVE_ACTION));
	deviationModel.setCustomerName((String) presentationModel.getBufferedValue(PROPERTY_CUSTOMER_NAME));
	deviationModel.setDeviationId((String) presentationModel.getBufferedValue(PROPERTY_DEVIATION_ID));
	deviationModel.setPostShipmentBool((Boolean) presentationModel.getBufferedValue(PROPERTY_POST_SHIPMENT_BOOL));
	deviationModel.setOrder((Order) presentationModel.getBufferedValue(PROPERTY_ORDER));
	deviationModel.setCanSetPostShipment((Boolean) presentationModel.getBufferedValue(PROPERTY_CAN_SET_POST_SHIPMENT));
	deviationModel.setPostShipment((PostShipment) presentationModel.getBufferedValue(PROPERTY_POST_SHIPMENT));
	deviationModel.setCanAddOrderLine((Boolean) presentationModel.getBufferedValue(PROPERTY_CAN_ADD_ORDER_LINE));
	deviationModel.setIsPostShipment((Boolean) presentationModel.getBufferedValue(PROPERTY_IS_POST_SHIPMENT));
	deviationModel.setDoAssemblyBool((Boolean) presentationModel.getBufferedValue(PROPERTY_DO_ASSEMBLY_BOOL));
	deviationModel.setResponsible((String) presentationModel.getBufferedValue(PROPERTY_RESPONSIBLE));
	deviationModel.setDateFrom((Date) presentationModel.getBufferedValue(PROPERTY_DATE_FROM));
	deviationModel.setDateTo((Date) presentationModel.getBufferedValue(PROPERTY_DATE_TO));
	deviationModel.setCheckedBool((Boolean) presentationModel.getBufferedValue(PROPERTY_CHECKED_BOOL));
	deviationModel.setInitiatedBy((String) presentationModel.getBufferedValue(PROPERTY_INITIATED_BY));
	deviationModel.setRegistrationDateString((String) presentationModel.getBufferedValue(PROPERTY_REGISTRATION_DATE_STRING));
	deviationModel.setComments((List<OrderComment>) presentationModel.getBufferedValue(PROPERTY_COMMENTS));
	deviationModel.setProjectNr((String) presentationModel.getBufferedValue(PROPERTY_PROJECT_NR));
	deviationModel.setProcedureCheck((Date) presentationModel.getBufferedValue(PROPERTY_PROCEDURE_CHECK));
	deviationModel.setDateClosedString((String) presentationModel.getBufferedValue(PROPERTY_DATE_CLOSED_STRING));
	return deviationModel;
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getDeviation()
     */
    public Deviation getDeviation() {
	return object;
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getArticles()
     */
    public List<ArticleType> getArticles() {
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getCustomerFirstName()
     */
    public String getCustomerFirstName() {
	if (object.getOrder() != null) {
	    return object.getOrder().getCustomer().getFirstName();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getCustomerLastName()
     */
    public String getCustomerLastName() {
	if (object.getOrder() != null) {
	    return object.getOrder().getCustomer().getLastName();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getDeliveryAddress()
     */
    public String getDeliveryAddress() {
	if (object.getOrder() != null) {
	    return object.getOrder().getDeliveryAddress();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getPostOffice()
     */
    public String getPostOffice() {
	if (object.getOrder() != null) {
	    return object.getOrder().getPostOffice();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.ICostableModel#getPostalCode()
     */
    public String getPostalCode() {
	if (object.getOrder() != null) {
	    return object.getOrder().getPostalCode();
	}
	return null;
    }

    /**
     * @return siste kommentar
     */
    public String getLastComment() {
	if (commentList.size() > 0) {
	    OrderComment comment = commentList.get(0);

	    return comment.getComment();
	}
	return null;
    }

    public static void setRegistrationAndChangeDate(Deviation deviation, ApplicationUser applicationUser) {
	if (deviation.getDeviationId() == null) {
	    deviation.setRegistrationDate(Calendar.getInstance().getTime());
	}
	deviation.setLastChanged(Util.getCurrentDateAsDateString() + ":" + applicationUser.getUserName());
    }
}
