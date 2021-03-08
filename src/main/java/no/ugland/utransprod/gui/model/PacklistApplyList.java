/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.google.inject.Inject;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.ProTransException;
/*     */ import no.ugland.utransprod.gui.Login;
/*     */ import no.ugland.utransprod.gui.WindowInterface;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.PacklistV;
/*     */ import no.ugland.utransprod.service.OrderManager;
/*     */ import no.ugland.utransprod.service.PacklistVManager;
/*     */ import no.ugland.utransprod.service.VismaFileCreator;
/*     */ import no.ugland.utransprod.util.ModelUtil;
/*     */ import no.ugland.utransprod.util.UserUtil;
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
/*     */ public class PacklistApplyList extends AbstractApplyList<PacklistV> {
/*     */    @Inject
/*     */    public PacklistApplyList(Login login, PacklistVManager manager) {
/*  35 */       super(login, manager, (VismaFileCreator)null);
/*  36 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setApplied(PacklistV object, boolean applied, WindowInterface window) {
/*  46 */       OrderManager orderManager = (OrderManager)ModelUtil.getBean("orderManager");
/*     */ 
/*  48 */       Order order = orderManager.findByOrderNr(object.getOrderNr());
/*  49 */       if (applied) {
/*  50 */          Date packlistDate = Util.getDate(window);
/*  51 */          order.setPacklistReady(packlistDate);
/*     */       } else {
/*  53 */          order.setPacklistReady((Date)null);
/*     */       }
/*     */       try {
/*  56 */          orderManager.saveOrder(order);
/*  57 */       } catch (ProTransException var7) {
/*  58 */          Util.showErrorDialog(window, "Feil", var7.getMessage());
/*  59 */          var7.printStackTrace();
/*     */       }
/*  61 */       this.applyListManager.refresh(object);
/*     */ 
/*  63 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public boolean hasWriteAccess() {
/*  69 */       return UserUtil.hasWriteAccess(this.login.getUserType(), "Pakkliste");
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    protected void sortObjectLines(List<PacklistV> lines) {
/*  77 */       Collections.sort(lines);
/*  78 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public PacklistV getApplyObject(Transportable transportable, WindowInterface window) {
/*  84 */       List<PacklistV> list = this.applyListManager.findApplyableByOrderNr(transportable.getOrder().getOrderNr());
/*  85 */       return list != null && list.size() == 1 ? (PacklistV)list.get(0) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setStarted(PacklistV object, boolean started) {
/*  92 */    }
/*     */ 
/*     */ 
/*     */    public boolean shouldGenerateVismaFile() {
/*  96 */       return false;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setRealProductionHours(PacklistV object, BigDecimal overstyrtTidsforbruk) {
/* 102 */    }
/*     */ }
