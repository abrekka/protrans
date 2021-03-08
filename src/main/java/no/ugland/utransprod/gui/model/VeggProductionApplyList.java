/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.ArrayList;
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
/*     */ import no.ugland.utransprod.model.Ordln;
/*     */ import no.ugland.utransprod.model.Produceable;
/*     */ import no.ugland.utransprod.service.IApplyListManager;
/*     */ import no.ugland.utransprod.service.ManagerRepository;
/*     */ import no.ugland.utransprod.service.OrderManager;
/*     */ import no.ugland.utransprod.service.VismaFileCreator;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
/*     */ import no.ugland.utransprod.util.ApplicationParamUtil;
/*     */ import no.ugland.utransprod.util.ModelUtil;
/*     */ import no.ugland.utransprod.util.Tidsforbruk;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VeggProductionApplyList extends ProductionApplyList {
/*     */    public VeggProductionApplyList(Login login, IApplyListManager<Produceable> manager, String aColliName, String aWindowName, Integer[] somInvisibleCells, ManagerRepository aManagerRepository, VismaFileCreator vismaFileCreator) {
/*  39 */       super(login, manager, aColliName, aWindowName, somInvisibleCells, aManagerRepository, vismaFileCreator);
/*  40 */    }
/*     */ 
/*     */ 
/*     */    public void setStarted(Produceable object, boolean started) {
/*  44 */       if (object != null) {
/*  45 */          OrderManager orderManager = (OrderManager)ModelUtil.getBean("orderManager");
/*     */ 
/*     */ 
/*  48 */          Order order = orderManager.findByOrderNr(object.getOrderNr());
/*  49 */          orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.ORDER_LINES});
/*     */ 
/*     */ 
/*     */ 
/*  53 */          if (order != null) {
/*  54 */             List<OrderLine> vegger = order.getOrderLineList("Vegg");
/*  55 */             int antall = 0;
/*  56 */             String gjortAv = "";
/*  57 */             Date startedDate = Util.getCurrentDate();
/*  58 */             Iterator var9 = vegger.iterator();            while(var9.hasNext()) {               OrderLine vegg = (OrderLine)var9.next();
/*  59 */                ++antall;
/*  60 */                if (vegg != null) {
/*  61 */                   if (started) {
/*  62 */                      vegg.setActionStarted(startedDate);
/*  63 */                      if (antall == 1) {
/*  64 */                         gjortAv = Util.showInputDialogWithdefaultValue((WindowInterface)null, "Gjøres av", "Gjøres av", this.login.getApplicationUser().getFullName());
/*     */                      }
/*     */ 
/*  67 */                      vegg.setDoneBy(gjortAv);
/*     */                   } else {
/*  69 */                      vegg.setActionStarted((Date)null);
/*  70 */                      vegg.setDoneBy((String)null);
/*     */                   }
/*  72 */                   this.managerRepository.getOrderLineManager().saveOrderLine(vegg);
/*     */ 
/*     */ 
/*     */                }
/*     */             }
/*     */ 
/*  78 */             this.managerRepository.getOrderManager().refreshObject(order);
/*     */          }
/*  80 */          this.applyListManager.refresh(object);
/*     */       }
/*  82 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    protected void handleApply(Produceable object, boolean applied, WindowInterface window, String aColliName) {
/*  87 */       OrderManager orderManager = this.managerRepository.getOrderManager();
/*  88 */       Order order = orderManager.findByOrderNr(object.getOrderNr());
/*  89 */       orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.ORDER_LINES});
/*  90 */       if (order != null) {
/*  91 */          List vegger = order.getOrderLineList("Vegg");
/*     */ 
/*     */          try {
/*  94 */             int antall = 0;
/*  95 */             OrderLine orderLineVegg = null;
/*     */ 
/*     */ 
/*  98 */             EditPacklistView editPacklistView = null;
/*  99 */             BigDecimal duration = null;
/* 100 */             String doneBy = null;            OrderLine vegg;
/* 101 */             for(Iterator var13 = vegger.iterator(); var13.hasNext(); this.managerRepository.getOrderLineManager().saveOrderLine(vegg)) {               vegg = (OrderLine)var13.next();
/* 102 */                if (vegg.getOrdNo() != null && vegg.getOrdNo() != 0 && vegg.getLnNo() != null) {
/* 103 */                   orderLineVegg = vegg;
/*     */ 
/*     */                }
/*     */ 
/* 107 */                ++antall;
/* 108 */                if (applied) {
/*     */ 
/* 110 */                   vegg.setProduced(object.getProduced());
/* 111 */                   this.setProducableApplied(vegg, aColliName);
/*     */ 
/* 113 */                   BigDecimal tidsbruk = vegg.getRealProductionHours();
/*     */ 
/* 115 */                   if (tidsbruk == null) {
/* 116 */                      tidsbruk = Tidsforbruk.beregnTidsforbruk(vegg.getActionStarted(), vegg.getProduced());
/*     */                   }
/*     */ 
/* 119 */                   if (antall == 1 && window != null) {
/* 120 */                      editPacklistView = new EditPacklistView(this.login, false, tidsbruk, vegg.getDoneBy());
/* 121 */                      JDialog dialog = Util.getDialog(window, "Vegg produsert", true);
/* 122 */                      WindowInterface window1 = new JDialogAdapter(dialog);
/* 123 */                      window1.add(editPacklistView.buildPanel(window1));
/* 124 */                      window1.pack();
/* 125 */                      Util.locateOnScreenCenter(window1);
/* 126 */                      window1.setVisible(true);
/*     */                   }
/*     */ 
/* 129 */                   if (editPacklistView != null && !editPacklistView.isCanceled()) {
/* 130 */                      duration = editPacklistView.getPacklistDuration();
/* 131 */                      doneBy = editPacklistView.getDoneBy();
/* 132 */                      vegg.setRealProductionHours(editPacklistView.getPacklistDuration());
/* 133 */                      vegg.setDoneBy(editPacklistView.getDoneBy());
/*     */                   }
/* 135 */                   vegg.setRealProductionHours(duration);
/* 136 */                   vegg.setDoneBy(doneBy);
/*     */                } else {
/* 138 */                   this.setProducableUnapplied(vegg);
/* 139 */                   duration = null;
/* 140 */                   doneBy = null;
/* 141 */                   vegg.setRealProductionHours((BigDecimal)null);
/* 142 */                   vegg.setDoneBy((String)null);
/*     */ 
/*     */                }
/*     */             }
/*     */ 
/* 147 */             if (orderLineVegg != null) {
/* 148 */                orderLineVegg.setRealProductionHours(duration);
/* 149 */                orderLineVegg.setDoneBy(doneBy);
/* 150 */                this.lagFerdigmelding(order, orderLineVegg, !applied, "Vegg");
/*     */             }
/* 152 */          } catch (ProTransException var18) {
/* 153 */             Util.showErrorDialog(window, "Feil", var18.getMessage());
/* 154 */             var18.printStackTrace();
/*     */          }
/*     */ 
/* 157 */          this.managerRepository.getOrderManager().refreshObject(order);
/* 158 */          this.managerRepository.getOrderManager().settStatus(order.getOrderId(), (String)null);
/*     */ 
/*     */ 
/* 161 */          this.applyListManager.refresh(object);
/*     */       }
/* 163 */    }
/*     */ 
/*     */    private void lagFerdigmelding(Integer ordno, Integer lnno, boolean minus) {
/* 166 */       Ordln ordln = this.managerRepository.getOrdlnManager().findByOrdNoAndLnNo(ordno, lnno);
/*     */ 
/* 168 */       if (ordln != null && ordln.getPurcno() != null && ordln.getPurcno() != 0) {
/* 169 */          List<String> fillinjer = new ArrayList();
/* 170 */          fillinjer.add(String.format("H;;%s;;;;;;;;;;;;;;;;;;;;;;%s;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4", ordln.getPurcno() != null ? ordln.getPurcno().toString() : "", ""));
/*     */ 
/* 172 */          List<Ordln> vegglinjer = this.managerRepository.getOrdlnManager().findOrdLnByOrdNo(ordln.getPurcno());         Iterator var7 = vegglinjer.iterator();
/* 173 */          while(var7.hasNext()) {            Ordln vegg = (Ordln)var7.next();
/* 174 */             if (vegg.getNoinvoab() != null && vegg.getNoinvoab().intValue() > 0) {
/* 175 */                fillinjer.add(this.lagLinje(vegg, minus));
/*     */             }         }
/*     */ 
/*     */          try {
/* 179 */             this.vismaFileCreator.writeFile(ordln.getPurcno().toString(), ApplicationParamUtil.findParamByName("visma_out_dir"), fillinjer, 1);
/*     */ 
/* 181 */          } catch (IOException var9) {
/* 182 */             throw new RuntimeException("Feilet ved skriving av vismafil", var9);
/*     */          }
/*     */       }
/*     */ 
/* 186 */    }
/*     */ 
/*     */    private String lagLinje(Ordln vegg, boolean minus) {
/* 189 */       StringBuilder stringBuilder = new StringBuilder();
/* 190 */       stringBuilder.append("L;$lnNo").append(StringUtils.leftPad("", 71, ";")).append("1;1;$lineStatus;3");
/*     */ 
/* 192 */       String lineString = StringUtils.replaceOnce(stringBuilder.toString(), "$lnNo", vegg.getLnno() != null ? vegg.getLnno().toString() : "");
/*     */ 
/* 194 */       return StringUtils.replaceOnce(lineString, "$lineStatus", (minus ? "-" : "") + vegg.getNoinvoab().toString());
/*     */    }
/*     */ }
