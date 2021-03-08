/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.adapter.AbstractTableAdapter;
/*     */ import com.jgoodies.binding.list.ArrayListModel;
/*     */ import java.util.Map;
/*     */ import javax.swing.ListModel;
/*     */ import no.ugland.utransprod.gui.OrderPanelTypeEnum;
/*     */ import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.PostShipment;
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
/*     */ public final class OrderTableModel extends AbstractTableAdapter {
/*     */    private final ArrayListModel list;
/*     */    private OrderPanelTypeEnum orderPanelType;
/*     */    private StatusCheckerInterface<Transportable> steinChecker;
/*     */    private static final long serialVersionUID = 1L;
/*     */ 
/*     */    public OrderTableModel(ListModel listModel, OrderPanelTypeEnum orderPanelTypeEnum) {
/*  47 */       this(listModel, new ArrayListModel(), orderPanelTypeEnum, (StatusCheckerInterface)null);
/*  48 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public OrderTableModel(ListModel listModel, ArrayListModel aOrderList, OrderPanelTypeEnum orderPanelTypeEnum, StatusCheckerInterface<Transportable> aSteinChecker) {
/*  58 */       super(listModel);
/*  59 */       this.list = aOrderList;
/*  60 */       this.orderPanelType = orderPanelTypeEnum;
/*  61 */       this.steinChecker = aSteinChecker;
/*  62 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Object getValueAt(int rowIndex, int columnIndex) {
/*  68 */       Transportable transportable = (Transportable)this.getRow(rowIndex);
/*  69 */       Map<String, String> statusMap = Util.createStatusMap(transportable.getStatus());
/*  70 */       return this.orderPanelType.getValueAt(transportable, columnIndex, this.steinChecker, statusMap);
/*     */    }
/*     */ 
/*     */ 
/*     */    private Object getValueAtConfirmReport(int rowIndex, int columnIndex) {
/*  75 */       Order order = (Order)this.getRow(rowIndex);
/*     */ 
/*  77 */       switch(columnIndex) {
/*     */       case 0:
/*  79 */          return order.getCustomer();
/*     */       case 1:
/*  81 */          return order.getOrderNr();
/*     */       case 2:
/*  83 */          return order.getDeliveryAddress();
/*     */       case 3:
/*  85 */          return order.getPostalCode();
/*     */       case 4:
/*  87 */          return order.getPostOffice();
/*     */ 
/*     */       case 5:
/*  90 */          return order.getConstructionType();
/*     */       case 6:
/*  92 */          return order.getTransport();
/*     */       case 7:
/*  94 */          return order.getCost("Egenproduksjon", "Kunde");
/*     */       case 8:
/*  96 */          return order.getCost("Frakt", "Kunde");
/*     */       case 9:
/*  98 */          return order.getCost("Montering", "Kunde");
/*     */       case 10:
/* 100 */          return order.getCost("Egenproduksjon", "Intern");
/*     */       case 11:
/* 102 */          return order.getContributionRate();
/*     */       default:
/* 104 */          throw new IllegalStateException("Unknown column");
/*     */       }
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Object getValueAtOrder(int rowIndex, int columnIndex) {
/* 117 */       Transportable transportable = (Transportable)this.getRow(rowIndex);
/*     */ 
/* 119 */       switch(columnIndex) {
/*     */       case 0:
/* 121 */          return transportable.getCustomer();
/*     */       case 1:
/* 123 */          String orderNr = transportable.getOrder().getOrderNr();
/* 124 */          if (transportable instanceof PostShipment) {
/* 125 */             orderNr = orderNr + " - etterlevering";
/*     */          }
/* 127 */          return orderNr;
/*     */       case 2:
/* 129 */          return transportable.getOrder().getDeliveryAddress();
/*     */       case 3:
/* 131 */          return transportable.getOrder().getPostalCode();
/*     */       case 4:
/* 133 */          return transportable.getOrder().getPostOffice();
/*     */ 
/*     */       case 5:
/* 136 */          return transportable.getConstructionType();
/*     */       case 6:
/* 138 */          return transportable.getTransport();
/*     */ 
/*     */       case 7:
/* 141 */          if (transportable.getOrder().getProductArea() != null) {
/* 142 */             return transportable.getOrder().getProductArea().getProductAreaGroup();
/*     */          }
/* 144 */          return null;
/*     */       default:
/* 146 */          throw new IllegalStateException("Unknown column");
/*     */       }
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Object getValueAtAssembly(int rowIndex, int columnIndex) {
/* 159 */       Order order = (Order)this.getRow(rowIndex);
/* 160 */       switch(columnIndex) {
/*     */       case 0:
/* 162 */          return order.getConstructionType();
/*     */       case 1:
/* 164 */          return order.getPostalCode();
/*     */       case 2:
/* 166 */          return order.getPostOffice();
/*     */       case 3:
/* 168 */          return order;
/*     */       case 4:
/* 170 */          return order.getOrderNr();
/*     */       case 5:
/* 172 */          return order.getDeliveryAddress();
/*     */       case 6:
/* 174 */          return order.getTransport();
/*     */ 
/*     */       default:
/* 177 */          throw new IllegalStateException("Unknown column");
/*     */       }
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Object getValueAtTransport(int rowIndex, int columnIndex) {
/* 190 */       Order order = (Order)this.getRow(rowIndex);
/*     */ 
/* 192 */       Map<String, String> statusMap = Util.createStatusMap(order.getStatus());
/* 193 */       switch(columnIndex) {
/*     */       case 0:
/* 195 */          return order.getDeliveryWeek();
/*     */       case 1:
/* 197 */          return order.getConstructionType();
/*     */       case 2:
/* 199 */          if (order.doAssembly()) {
/* 200 */             if (order.getAssembly() != null) {
/* 201 */                return "M" + order.getAssembly().getAssemblyWeek();
/*     */             }
/* 203 */             return "M";
/*     */          }
/* 205 */          return null;
/*     */       case 3:
/* 207 */          return order.getPostalCode();
/*     */       case 4:
/* 209 */          return order.getPostOffice();
/*     */       case 5:
/* 211 */          return statusMap.get(this.steinChecker.getArticleName());
/*     */       case 6:
/* 213 */          return order;
/*     */       case 7:
/* 215 */          return order.getOrderNr();
/*     */       case 8:
/* 217 */          return order.getDeliveryAddress();
/*     */       case 9:
/* 219 */          return order.getProductAreaGroup().getProductAreaGroupName();
/*     */ 
/*     */ 
/*     */       default:
/* 223 */          throw new IllegalStateException("Unknown column");
/*     */       }
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Order getOrder(int rowIndex) {
/* 235 */       return (Order)this.getRow(rowIndex);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void insertRow(int index, Order order) {
/* 245 */       this.list.add(index, order);
/* 246 */       this.fireTableDataChanged();
/* 247 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void removeRow(int index) {
/* 255 */       if (index < this.list.size()) {
/* 256 */          this.list.remove(index);
/* 257 */          this.fireTableDataChanged();
/*     */       }
/* 259 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getColumnName(int column) {
/* 266 */       return this.orderPanelType.getColumnName(column);
/*     */    }
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
/*     */    public int getColumnCount() {
/* 280 */       return this.orderPanelType.getColumCount();
/*     */    }
/*     */ }
