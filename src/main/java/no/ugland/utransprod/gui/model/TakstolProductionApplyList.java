/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.google.inject.Inject;
/*     */ import com.google.inject.name.Named;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.ProTransException;
/*     */ import no.ugland.utransprod.gui.ArticlePackageViewFactory;
/*     */ import no.ugland.utransprod.gui.ArticleProductionPackageView;
/*     */ import no.ugland.utransprod.gui.Login;
/*     */ import no.ugland.utransprod.gui.WindowInterface;
/*     */ import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandler.PackProduction;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.Produceable;
/*     */ import no.ugland.utransprod.service.ManagerRepository;
/*     */ import no.ugland.utransprod.service.TakstolProductionVManager;
/*     */ import no.ugland.utransprod.service.VismaFileCreator;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TakstolProductionApplyList extends ProductionApplyList {
/*     */    private ArticleProductionPackageView articlePackageView;
/*     */    private String mainArticleName;
/*     */ 
/*     */    @Inject
/*     */    public TakstolProductionApplyList(@Named("takstolColliName") String colliName, Login login, @Named("takstol_article") String aMainArticleName, ManagerRepository managerRepository, ArticlePackageViewFactory aArticlePackageViewFactory) {
/*  34 */       super(login, managerRepository.getTakstolProductionVManager(), colliName, "Takstol", (Integer[])null, managerRepository, (VismaFileCreator)null);
/*     */ 
/*  36 */       this.mainArticleName = aMainArticleName;
/*  37 */       this.articlePackageView = aArticlePackageViewFactory.create(managerRepository.getArticleTypeManager().findByName("Takstoler"), this, colliName);
/*     */ 
/*     */ 
/*  40 */    }
/*     */ 
/*     */    public void setStarted(Produceable object, boolean started) {
/*  43 */       if (object != null) {
/*  44 */          List<Applyable> relatedArticles = object.getRelatedArticles();
/*  45 */          List<Applyable> objects = new ArrayList();
/*  46 */          Date startedDate = started ? Util.getCurrentDate() : null;
/*     */ 
/*  48 */          if (object.getOrderLineId() != null) {
/*  49 */             objects.add(object);
/*     */          }
/*  51 */          if (relatedArticles != null && relatedArticles.size() != 0) {
/*  52 */             objects.addAll(relatedArticles);
/*     */          }
/*     */ 
/*  55 */          this.setStartedDate(objects, startedDate);
/*     */       }
/*  57 */    }
/*     */ 
/*     */ 
/*     */    public void settStartetKapping(Produceable produceable, Date startetKappingDato) {
/*  61 */       OrderLine orderLine = this.managerRepository.getOrderLineManager().findByOrderLineId(produceable.getOrderLineId());
/*     */ 
/*  63 */       if (orderLine != null) {
/*  64 */          orderLine.setCuttingStarted(startetKappingDato);
/*  65 */          this.managerRepository.getOrderLineManager().saveOrderLine(orderLine);
/*  66 */          this.applyListManager.refresh(produceable);
/*     */       }
/*  68 */    }
/*     */ 
/*     */ 
/*     */    public void settKappingFerdig(Produceable produceable, Date kappingFerdigDato) {
/*  72 */       OrderLine orderLine = this.managerRepository.getOrderLineManager().findByOrderLineId(produceable.getOrderLineId());
/*     */ 
/*  74 */       if (orderLine != null) {
/*  75 */          orderLine.setCuttingDone(kappingFerdigDato);
/*  76 */          this.managerRepository.getOrderLineManager().saveOrderLine(orderLine);
/*  77 */          this.applyListManager.refresh(produceable);
/*     */       }
/*  79 */    }
/*     */ 
/*     */    private void setStartedDate(List<Applyable> objects, Date startedDate) {
/*  82 */       Iterator var3 = objects.iterator();      while(var3.hasNext()) {         Applyable object = (Applyable)var3.next();
/*  83 */          OrderLine orderLine = this.managerRepository.getOrderLineManager().findByOrderLineId(object.getOrderLineId());
/*     */ 
/*  85 */          if (orderLine != null) {
/*  86 */             orderLine.setActionStarted(startedDate);
/*  87 */             this.managerRepository.getOrderLineManager().saveOrderLine(orderLine);
/*     */ 
/*  89 */             this.applyListManager.refresh((Produceable)object);
/*     */          }
/*     */       }
/*     */ 
/*  93 */    }
/*     */ 
/*     */ 
/*     */    public void setApplied(Produceable object, boolean applied, WindowInterface window) {
/*     */       try {
/*  98 */          if (object != null) {
/*  99 */             List<Applyable> relatedArticles = object.getRelatedArticles();
/* 100 */             List<Applyable> objects = new ArrayList();
/* 101 */             object.setProduced(this.getProducedDate(object, applied));
/* 102 */             if (object.getOrderLineId() != null) {
/* 103 */                objects.add(object);
/*     */             }
/* 105 */             if (relatedArticles != null && relatedArticles.size() != 0) {
/* 106 */                objects.addAll(relatedArticles);
/* 107 */                Iterator var6 = objects.iterator();               while(var6.hasNext()) {                  Applyable item = (Applyable)var6.next();
/* 108 */                   item.setProduced(this.getProducedDate(item, applied));
/*     */                }
/*     */             }
/*     */ 
/* 112 */             this.applyAllObjects(applied, window, objects);
/* 113 */             if (applied) {
/* 114 */                this.articlePackageView.setArticles(objects, PackProduction.PRODUCTION);
/*     */ 
/* 116 */                Util.showEditViewable(this.articlePackageView, window);
/*     */             }
/* 118 */             this.applyAllObjects(applied, window, objects);
/*     */ 
/* 120 */             this.setOrderReady(object);
/* 121 */             this.checkCompleteness(object.getColli(), applied);
/*     */          }
/* 123 */       } catch (ProTransException var8) {
/* 124 */          Util.showErrorDialog(window, "Feil", var8.getMessage());
/* 125 */          var8.printStackTrace();
/*     */       }
/*     */ 
/* 128 */    }
/*     */ 
/*     */ 
/*     */    private void applyAllObjects(boolean applied, WindowInterface window, List<Applyable> objects) {
/* 132 */       boolean relatedNotPacked = false;
/* 133 */       Iterator var5 = objects.iterator();      while(var5.hasNext()) {         Applyable item = (Applyable)var5.next();
/* 134 */          item.setColli(applied ? item.getColli() : null);
/* 135 */          relatedNotPacked = relatedNotPacked ? relatedNotPacked : item.getColli() == null;
/*     */ 
/* 137 */          this.applyObject((Produceable)item, item.getProduced() != null, window);
/*     */       }
/* 139 */    }
/*     */ 
/*     */    private void setOrderReady(Produceable object) throws ProTransException {
/* 142 */       if (object.getProductAreaGroupName().equalsIgnoreCase("Takstol")) {
/* 143 */          Order order = this.managerRepository.getOrderManager().findByOrderNr(object.getOrderNr());
/*     */ 
/* 145 */          if (this.isAllProduced(object)) {
/* 146 */             order.setOrderReady(Util.getCurrentDate());
/*     */          } else {
/* 148 */             order.setOrderReady((Date)null);
/*     */          }
/* 150 */          this.managerRepository.getOrderManager().saveOrder(order);
/*     */       }
/* 152 */    }
/*     */ 
/*     */    private boolean isAllProduced(Produceable object) {
/* 155 */       return object.getProduced() != null ? object.isRelatedArticlesComplete() : false;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    private void applyObject(Produceable object, boolean applied, WindowInterface window) {
/* 163 */       object.setProduced(this.getProducedDate(object, applied));
/*     */ 
/* 165 */       OrderLine orderLine = this.managerRepository.getOrderLineManager().findByOrderLineId(object.getOrderLineId());
/*     */ 
/* 167 */       if (orderLine != null) {
/* 168 */          String aColliName = Util.getColliName(object.getArticleName());
/* 169 */          this.handleApply(object, applied, window, aColliName);
/*     */       }
/* 171 */    }
/*     */ 
/*     */    private Date getProducedDate(Applyable applyable, boolean applied) {
/* 174 */       if (applied) {
/* 175 */          return applyable.getProduced() == null ? Util.getCurrentDate() : applyable.getProduced();
/*     */ 
/*     */       } else {
/* 178 */          return null;
/*     */       }
/*     */    }
/*     */ 
/*     */    public Produceable getApplyObject(Transportable transportable, WindowInterface window) {
/* 183 */       List<Produceable> list = ((TakstolProductionVManager)this.applyListManager).findApplyableByOrderNrAndArticleName(transportable.getOrder().getOrderNr(), this.mainArticleName);
/*     */ 
/*     */ 
/*     */ 
/* 187 */       return list != null && list.size() != 0 ? (list != null && list.size() == 1 ? (Produceable)list.get(0) : this.selectApplyObject(list, window)) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    private Produceable selectApplyObject(List<Produceable> list, WindowInterface window) {
/* 194 */       return (Produceable)Util.showOptionsDialogCombo(window, list, "Velg artikkel", true, (Object)null);
/*     */    }
/*     */ }
