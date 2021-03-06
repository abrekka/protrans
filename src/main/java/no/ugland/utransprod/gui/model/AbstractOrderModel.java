package no.ugland.utransprod.gui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.list.ArrayListModel;

public abstract class AbstractOrderModel<T extends Transportable, E> extends AbstractModel<T, E>
		implements Packable, ICostableModel<T, E> {
	public static final String PROPERTY_ORDER_NR = "orderNr";
	public static final String PROPERTY_ORDER_READY_VEGG_BOOL = "orderReadyVeggBool";
	public static final String PROPERTY_ORDER_READY_TAKSTOL_BOOL = "orderReadyTakstolBool";
	public static final String PROPERTY_ORDER_READY_PAKK_BOOL = "orderReadyPakkBool";
	public static final String PROPERTY_ORDER_LINES = "orderLines";
	public static final String PROPERTY_ORDER_COMPLETE_BOOL = "orderCompleteBool";
	public static final String PROPERTY_COLLI_LIST = "colliList";
	public static final String PROPERTY_PACKED_BY = "packedBy";
	public static final String PROPERTY_PACKED_BY_TROSS = "packedByTross";
	public static final String PROPERTY_PACKED_BY_PACK = "packedByPack";
	public static final String PROPERTY_COMMENTS = "comments";
	public static final String PROPERTY_GARAGE_COLLI_HEIGHT = "garageColliHeight";
	public static final String PROPERTY_PACKAGE_STARTED = "packageStarted";
	public static final String PROPERTY_DEFAULT_COLLIES_GENERATED = "defaultColliesGenerated";

	protected List<Colli> colliList;
	protected final List<OrderComment> commentList;
	protected final ArrayListModel orderLineList;
	protected boolean lettvekt;

	public AbstractOrderModel(T object, boolean lettvekt) {
		super(object);
		this.lettvekt=lettvekt;
		commentList = new ArrayList<OrderComment>();
		orderLineList = new ArrayListModel();
		if (object.getOrderComments() != null) {
			commentList.addAll(object.getOrderComments());
		}
		if (!lettvekt && object.getOrderLines() != null) {
			orderLineList.addAll(object.getOrderLines());
		}
	}

	public abstract String getManagerName();

	public abstract PostShipment getOrderModelPostShipment();

	public abstract Order getOrderModelOrder();

	public abstract Enum[] getEnums();

	public abstract Serializable getObjectId();

	public String getOrderNr() {
		return object.getOrderNr();
	}

	/**
	 * Setter ordrenummer
	 * 
	 * @param orderNr
	 */
	public void setOrderNr(String orderNr) {
		String oldNr = getOrderNr();
		object.setOrderNr(orderNr);
		firePropertyChange(PROPERTY_ORDER_NR, oldNr, orderNr);
	}

	public Boolean getOrderReadyVeggBool() {
		if (object.getOrderReadyWall() != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean getOrderReadyTakstolBool() {
		if (object.getOrderReadyTross() != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean getOrderReadyPakkBool() {
		if (object.getOrderReadyPack() != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * @param orderReady
	 */
	public void setOrderReadyVeggBool(Boolean orderReady) {
		Boolean oldReady = getOrderReadyVeggBool();
		if (orderReady) {
			object.setOrderReadyWall(Calendar.getInstance().getTime());
		} else {
			object.setOrderReadyWall(null);
		}
		firePropertyChange(PROPERTY_ORDER_READY_VEGG_BOOL, oldReady, orderReady);
	}

	public void setOrderReadyTakstolBool(Boolean orderReady) {
		Boolean oldReady = getOrderReadyTakstolBool();
		if (orderReady) {
			object.setOrderReadyTross(Calendar.getInstance().getTime());
		} else {
			object.setOrderReadyTross(null);
		}
		firePropertyChange(PROPERTY_ORDER_READY_TAKSTOL_BOOL, oldReady, orderReady);
	}

	public void setOrderReadyPakkBool(Boolean orderReady) {
		Boolean oldReady = getOrderReadyPakkBool();
		if (orderReady) {
			object.setOrderReadyPack(Calendar.getInstance().getTime());
		} else {
			object.setOrderReadyPack(null);
		}
		firePropertyChange(PROPERTY_ORDER_READY_PAKK_BOOL, oldReady, orderReady);
	}

	public List<OrderLine> getOrderLines() {
		List<OrderLine> lines = new ArrayList<OrderLine>();
		boolean added = object != null && object.getOrderLines() != null ? lines.addAll(object.getOrderLines()) : false;
		return lines;
	}

	public Boolean getOrderCompleteBool() {
		if (object.getOrderComplete() != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * @param orderComplete
	 */
	public void setOrderCompleteBool(Boolean orderComplete) {
		Boolean oldComplete = getOrderCompleteBool();
		if (orderComplete) {
			object.setOrderComplete(Calendar.getInstance().getTime());
		} else {
			object.setOrderComplete(null);
		}

		firePropertyChange(PROPERTY_ORDER_COMPLETE_BOOL, oldComplete, orderComplete);
	}

	public List<Colli> getColliList() {
		if (colliList == null) {
			colliList = new ArrayList<Colli>();
			if (object.getCollies() != null) {
				colliList.addAll(object.getCollies());
			}
		}
		return colliList;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Packable#addColli(no.ugland.utransprod.model.Colli)
	 */
	public void addColli(Colli colli) {
		List<Colli> oldList = new ArrayList<Colli>(getColliList());
		colliList.add(colli);
		firePropertyChange(PROPERTY_COLLI_LIST, oldList, colliList);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Packable#removeColli(no.ugland.utransprod.model.Colli)
	 */
	public boolean removeColli(Colli colli) {
		List<Colli> oldList = new ArrayList<Colli>(getColliList());

		colliList.remove(colli);

		firePropertyChange(PROPERTY_COLLI_LIST, oldList, colliList);
		return true;
	}

	public String getPackedBy() {
		return object.getPackedBy();
	}

	public String getPackedByTross() {
		return object.getPackedByTross();
	}

	public String getPackedByPack() {
		return object.getPackedByPack();
	}

	/**
	 * @param packedBy
	 */
	public void setPackedBy(String packedBy) {
		String oldPacked = getPackedBy();
		object.setPackedBy(packedBy);
		firePropertyChange(PROPERTY_PACKED_BY, oldPacked, packedBy);
	}

	public void setPackedByTross(String packedBy) {
		String oldPacked = getPackedByTross();
		object.setPackedByTross(packedBy);
		firePropertyChange(PROPERTY_PACKED_BY_TROSS, oldPacked, packedBy);
	}

	public void setPackedByPack(String packedBy) {
		String oldPacked = getPackedByPack();
		object.setPackedByPack(packedBy);
		firePropertyChange(PROPERTY_PACKED_BY_PACK, oldPacked, packedBy);
	}

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

	public Integer getGarageColliHeight() {
		return object.getGarageColliHeight();
	}

	public void setGarageColliHeight(Integer garageColliHeight) {
		Integer oldHeight = getGarageColliHeight();
		object.setGarageColliHeight(garageColliHeight);
		firePropertyChange(PROPERTY_GARAGE_COLLI_HEIGHT, oldHeight, garageColliHeight);
	}

	/**
	 * @param orderLine
	 */
	public void removeOrderLine(OrderLine orderLine) {
		ArrayListModel oldOrderLines = new ArrayListModel(getOrderLineList());
		this.orderLineList.remove(orderLine);
		firePropertyChange(PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL, oldOrderLines, orderLineList);
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		object.setStatus(status);
	}

	public Date getPackageStarted() {
		return object.getPackageStarted();
	}

	/**
	 * @param packageStarted
	 */
	public void setPackageStarted(Date packageStarted) {
		Date oldDate = getPackageStarted();
		object.setPackageStarted(Util.convertDate(packageStarted, Util.SHORT_DATE_FORMAT));
		firePropertyChange(PROPERTY_PACKAGE_STARTED, oldDate, packageStarted);
	}

	/**
	 * @param orderLine
	 */
	public void addOrderLine(OrderLine orderLine) {
		List<OrderLine> oldList = new ArrayList<OrderLine>(getOrderLines());
		Set<OrderLine> orderLines = object.getOrderLines();
		if (orderLines == null) {
			orderLines = new HashSet<OrderLine>();
		}
		orderLines.add(orderLine);
		object.setOrderLines(orderLines);
		firePropertyChange(PROPERTY_ORDER_LINES, oldList, new ArrayList<OrderLine>(orderLines));
	}

	public void setOrderReadyVegg(Date date) {
		object.setOrderReadyWall(date);
	}

	public void setOrderReadyTakstol(Date date) {
		object.setOrderReadyTross(date);
	}

	public void setOrderReadyPakk(Date date) {
		object.setOrderReadyPack(date);
	}

	public void setOrderReady(Date date) {
		object.setOrderReady(date);
	}

	/**
	 * @return true dersom ordre er ferdig pakket
	 */
	public Boolean isDonePackage() {
		Integer done = object.getColliesDone();
		if (done == null) {
			if (object.isDonePackage()) {
				done = 1;
			} else {
				done = 0;
			}
			object.setColliesDone(done);
		}

		if (done == 1) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public void setOrderComplete(Date date) {
		object.setOrderComplete(date);
	}

	public abstract Integer getDefaultColliesGenerated();

	public abstract void setDefaultColliesGenerated(Integer i);
}
