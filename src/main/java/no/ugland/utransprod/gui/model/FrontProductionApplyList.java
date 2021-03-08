/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.swing.JDialog;
/*     */ import no.ugland.utransprod.ProTransException;
/*     */ import no.ugland.utransprod.gui.JDialogAdapter;
/*     */ import no.ugland.utransprod.gui.Login;
/*     */ import no.ugland.utransprod.gui.WindowInterface;
/*     */ import no.ugland.utransprod.gui.edit.EditPacklistView;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.Produceable;
/*     */ import no.ugland.utransprod.service.IApplyListManager;
/*     */ import no.ugland.utransprod.service.ManagerRepository;
/*     */ import no.ugland.utransprod.service.OrderManager;
/*     */ import no.ugland.utransprod.service.VismaFileCreator;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
/*     */ import no.ugland.utransprod.util.ModelUtil;
/*     */ import no.ugland.utransprod.util.Tidsforbruk;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class FrontProductionApplyList extends ProductionApplyList {
/*     */    private static Logger LOGGER = Logger.getLogger(GavlProductionApplyList.class);
/*     */ 
/*     */    public FrontProductionApplyList(Login login, IApplyListManager<Produceable> manager, String aColliName, String aWindowName, Integer[] somInvisibleCells, ManagerRepository aManagerRepository, VismaFileCreator vismaFileCreator) {
/*  47 */       super(login, manager, aColliName, aWindowName, somInvisibleCells, aManagerRepository, vismaFileCreator);
/*  48 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    protected void handleApply(Produceable object, boolean applied, WindowInterface window, String aColliName) {
/*  53 */       OrderManager orderManager = this.managerRepository.getOrderManager();
/*  54 */       Order order = orderManager.findByOrderNr(object.getOrderNr());
/*  55 */       orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.ORDER_LINES});
/*  56 */       if (order != null) {
/*  57 */          List fronter = order.getOrderLineList("Front");
/*     */ 
/*     */          try {
/*  60 */             int antall = 0;
/*  61 */             OrderLine orderLineFront = null;
/*     */ 
/*     */ 
/*  64 */             EditPacklistView editPacklistView = null;
/*  65 */             BigDecimal duration = null;
/*  66 */             String doneBy = null;            OrderLine front;
/*  67 */             for(Iterator var13 = fronter.iterator(); var13.hasNext(); this.managerRepository.getOrderLineManager().saveOrderLine(front)) {               front = (OrderLine)var13.next();
/*  68 */                if (front.getOrdNo() != null && front.getOrdNo() != 0 && front.getLnNo() != null) {
/*  69 */                   orderLineFront = front;
/*     */ 
/*     */                }
/*     */ 
/*  73 */                ++antall;
/*  74 */                if (applied) {
/*     */ 
/*  76 */                   front.setProduced(object.getProduced());
/*  77 */                   this.setProducableApplied(front, aColliName);
/*     */ 
/*  79 */                   BigDecimal tidsbruk = front.getRealProductionHours();
/*     */ 
/*  81 */                   if (tidsbruk == null) {
/*  82 */                      tidsbruk = Tidsforbruk.beregnTidsforbruk(front.getActionStarted(), front.getProduced());
/*     */                   }
/*     */ 
/*  85 */                   if (antall == 1 && window != null) {
/*  86 */                      editPacklistView = new EditPacklistView(this.login, false, tidsbruk, front.getDoneBy());
/*  87 */                      JDialog dialog = Util.getDialog(window, "Front produsert", true);
/*  88 */                      WindowInterface window1 = new JDialogAdapter(dialog);
/*  89 */                      window1.add(editPacklistView.buildPanel(window1));
/*  90 */                      window1.pack();
/*  91 */                      Util.locateOnScreenCenter(window1);
/*  92 */                      window1.setVisible(true);
/*     */                   }
/*     */ 
/*  95 */                   if (editPacklistView != null && !editPacklistView.isCanceled()) {
/*  96 */                      duration = editPacklistView.getPacklistDuration();
/*  97 */                      doneBy = editPacklistView.getDoneBy();
/*  98 */                      front.setRealProductionHours(editPacklistView.getPacklistDuration());
/*  99 */                      front.setDoneBy(editPacklistView.getDoneBy());
/*     */                   }
/* 101 */                   front.setRealProductionHours(duration);
/* 102 */                   front.setDoneBy(doneBy);
/*     */                } else {
/* 104 */                   this.setProducableUnapplied(front);
/* 105 */                   duration = null;
/* 106 */                   doneBy = null;
/* 107 */                   front.setRealProductionHours((BigDecimal)null);
/* 108 */                   front.setDoneBy((String)null);
/*     */ 
/*     */                }
/*     */             }
/*     */ 
/* 113 */             if (orderLineFront != null) {
/* 114 */                orderLineFront.setRealProductionHours(duration);
/* 115 */                orderLineFront.setDoneBy(doneBy);
/* 116 */                this.lagFerdigmelding(order, orderLineFront, !applied, "Front");
/*     */             }
/* 118 */          } catch (ProTransException var18) {
/* 119 */             Util.showErrorDialog(window, "Feil", var18.getMessage());
/* 120 */             var18.printStackTrace();
/*     */          }
/*     */ 
/* 123 */          this.managerRepository.getOrderManager().refreshObject(order);
/* 124 */          this.managerRepository.getOrderManager().settStatus(order.getOrderId(), (String)null);
/*     */ 
/*     */ 
/* 127 */          this.applyListManager.refresh(object);
/*     */       }
/* 129 */    }
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
/*     */    public void setStarted(Produceable object, boolean started) {
/* 215 */       if (object != null) {
/* 216 */          OrderManager orderManager = (OrderManager)ModelUtil.getBean("orderManager");
/*     */ 
/*     */ 
/* 219 */          Order order = orderManager.findByOrderNr(object.getOrderNr());
/* 220 */          orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.ORDER_LINES});
/*     */ 
/*     */ 
/*     */ 
/* 224 */          if (order != null) {
/* 225 */             List<OrderLine> vegger = order.getOrderLineList("Front");
/* 226 */             int antall = 0;
/* 227 */             String gjortAv = "";
/* 228 */             Date startedDate = Util.getCurrentDate();
/* 229 */             Iterator var9 = vegger.iterator();            while(var9.hasNext()) {               OrderLine vegg = (OrderLine)var9.next();
/* 230 */                ++antall;
/* 231 */                if (vegg != null) {
/* 232 */                   if (started) {
/* 233 */                      vegg.setActionStarted(startedDate);
/* 234 */                      if (antall == 1) {
/* 235 */                         gjortAv = Util.showInputDialogWithdefaultValue((WindowInterface)null, "Gjøres av", "Gjøres av", this.login.getApplicationUser().getFullName());
/*     */                      }
/*     */ 
/* 238 */                      vegg.setDoneBy(gjortAv);
/*     */                   } else {
/* 240 */                      vegg.setActionStarted((Date)null);
/* 241 */                      vegg.setDoneBy((String)null);
/*     */                   }
/* 243 */                   this.managerRepository.getOrderLineManager().saveOrderLine(vegg);
/*     */ 
/*     */ 
/*     */                }
/*     */             }
/*     */ 
/* 249 */             this.managerRepository.getOrderManager().refreshObject(order);
/*     */          }
/* 251 */          this.applyListManager.refresh(object);
/*     */       }
/* 253 */    }
/*     */ }
