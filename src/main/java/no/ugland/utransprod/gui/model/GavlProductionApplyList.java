/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import java.math.BigDecimal;
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
/*     */ import no.ugland.utransprod.model.Ordln;
/*     */ import no.ugland.utransprod.model.Produceable;
/*     */ import no.ugland.utransprod.service.IApplyListManager;
/*     */ import no.ugland.utransprod.service.ManagerRepository;
/*     */ import no.ugland.utransprod.service.OrderLineManager;
/*     */ import no.ugland.utransprod.service.VismaFileCreator;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
/*     */ import no.ugland.utransprod.util.Tidsforbruk;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ import org.apache.commons.lang.StringUtils;
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
/*     */ public class GavlProductionApplyList extends ProductionApplyList {
/*     */    private static Logger LOGGER = Logger.getLogger(GavlProductionApplyList.class);
/*     */ 
/*     */    public GavlProductionApplyList(Login login, IApplyListManager<Produceable> manager, String aColliName, String aWindowName, Integer[] somInvisibleCells, ManagerRepository aManagerRepository, VismaFileCreator vismaFileCreator) {
/*  45 */       super(login, manager, aColliName, aWindowName, somInvisibleCells, aManagerRepository, vismaFileCreator);
/*  46 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    protected void handleApply(Produceable object, boolean applied, WindowInterface window, String aColliName) {
/*  51 */       OrderLineManager orderLineManager = this.managerRepository.getOrderLineManager();
/*  52 */       OrderLine orderLine = orderLineManager.findByOrderLineId(object.getOrderLineId());
/*  53 */       if (orderLine != null) {
/*     */          try {
/*  55 */             Order order = this.managerRepository.getOrderManager().findByOrderNr(object.getOrderNr());
/*  56 */             this.managerRepository.getOrderManager().lazyLoadOrder(order, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.ORDER_LINES});
/*     */ 
/*  58 */             List<OrderLine> orderLineList = order.getOrderLineList("Gavl");
/*     */ 
/*  60 */             OrderLine orderLineGavl = null;
/*  61 */             int antall = 0;
/*  62 */             BigDecimal duration = null;
/*  63 */             String doneBy = null;            OrderLine gavl;
/*  64 */             for(Iterator var13 = orderLineList.iterator(); var13.hasNext(); this.managerRepository.getOrderLineManager().saveOrderLine(gavl)) {               gavl = (OrderLine)var13.next();
/*  65 */                if (gavl.getOrdNo() != null && gavl.getOrdNo() != 0 && gavl.getLnNo() != null) {
/*  66 */                   orderLineGavl = gavl;
/*     */                }
/*  68 */                ++antall;
/*  69 */                if (applied) {
/*  70 */                   gavl.setProduced(object.getProduced());
/*  71 */                   this.setProducableApplied(gavl, aColliName);
/*     */ 
/*  73 */                   BigDecimal tidsbruk = gavl.getRealProductionHours();
/*     */ 
/*  75 */                   if (tidsbruk == null) {
/*  76 */                      tidsbruk = Tidsforbruk.beregnTidsforbruk(gavl.getActionStarted(), gavl.getProduced());
/*     */                   }
/*     */ 
/*  79 */                   if (antall == 1 && window != null) {
/*  80 */                      EditPacklistView editPacklistView = new EditPacklistView(this.login, false, tidsbruk, gavl.getDoneBy());
/*     */ 
/*     */ 
/*  83 */                      JDialog dialog = Util.getDialog(window, "Gavl produsert", true);
/*  84 */                      WindowInterface window1 = new JDialogAdapter(dialog);
/*  85 */                      window1.add(editPacklistView.buildPanel(window1));
/*  86 */                      window1.pack();
/*  87 */                      Util.locateOnScreenCenter(window1);
/*  88 */                      window1.setVisible(true);
/*     */ 
/*  90 */                      if (!editPacklistView.isCanceled()) {
/*  91 */                         duration = editPacklistView.getPacklistDuration();
/*  92 */                         doneBy = editPacklistView.getDoneBy();
/*  93 */                         gavl.setRealProductionHours(editPacklistView.getPacklistDuration());
/*  94 */                         gavl.setDoneBy(editPacklistView.getDoneBy());
/*     */                      }                  }
/*     */ 
/*  97 */                   gavl.setRealProductionHours(duration);
/*  98 */                   gavl.setDoneBy(doneBy);
/*     */                } else {
/* 100 */                   this.setProducableUnapplied(gavl);
/* 101 */                   duration = null;
/* 102 */                   doneBy = null;
/* 103 */                   gavl.setRealProductionHours((BigDecimal)null);
/* 104 */                   gavl.setDoneBy((String)null);
/*     */ 
/*     */                }
/*     */             }
/*     */ 
/* 109 */             if (orderLineGavl != null) {
/* 110 */                orderLineGavl.setRealProductionHours(duration);
/* 111 */                orderLineGavl.setDoneBy(doneBy);
/* 112 */                this.lagFerdigmelding(order, orderLineGavl, !applied, "Gavl");
/*     */             } else {
/* 114 */                LOGGER.info("Lager ikke ferdigmelding fordi ordrelinje mangler ordnno");
/*     */             }
/* 116 */          } catch (ProTransException var19) {
/* 117 */             Util.showErrorDialog(window, "Feil", var19.getMessage());
/* 118 */             var19.printStackTrace();
/*     */          }
/* 120 */          this.managerRepository.getOrderManager().settStatus(orderLine.getOrder().getOrderId(), (String)null);
/*     */ 
/* 122 */          this.applyListManager.refresh(object);
/*     */       }
/* 124 */    }
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
/*     */    private String lagLinje(Ordln vegg, boolean minus) {
/* 161 */       StringBuilder stringBuilder = new StringBuilder();
/* 162 */       stringBuilder.append("L;$lnNo").append(StringUtils.leftPad("", 71, ";")).append("1;1;$lineStatus;3");
/*     */ 
/* 164 */       String lineString = StringUtils.replaceOnce(stringBuilder.toString(), "$lnNo", vegg.getLnno() != null ? vegg.getLnno().toString() : "");
/*     */ 
/* 166 */       return StringUtils.replaceOnce(lineString, "$lineStatus", (minus ? "-" : "") + vegg.getNoinvoab().toString());
/*     */    }
/*     */ }
