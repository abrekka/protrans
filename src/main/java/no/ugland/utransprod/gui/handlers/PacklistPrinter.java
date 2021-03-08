/*     */ package no.ugland.utransprod.gui.handlers;
/*     */ 
/*     */ import com.jgoodies.binding.list.ArrayListModel;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.swing.JLabel;
/*     */ import no.ugland.utransprod.ProTransException;
/*     */ import no.ugland.utransprod.gui.WindowInterface;
/*     */ import no.ugland.utransprod.gui.model.ReportEnum;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.PostShipment;
/*     */ import no.ugland.utransprod.service.DeviationManager;
/*     */ import no.ugland.utransprod.service.OrderLineManager;
/*     */ import no.ugland.utransprod.service.PostShipmentManager;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadDeviationEnum;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
/*     */ import no.ugland.utransprod.util.ModelUtil;
/*     */ import no.ugland.utransprod.util.Threadable;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ import no.ugland.utransprod.util.report.ReportViewer;
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
/*     */ class PacklistPrinter implements Threadable {
/*     */    private WindowInterface owner;
/*     */    private PostShipment postShipment;
/*     */ 
/*     */    public PacklistPrinter(WindowInterface aWindow, PostShipment aPostShipment) {
/*  42 */       this.owner = aWindow;
/*  43 */       this.postShipment = aPostShipment;
/*  44 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void doWhenFinished(Object object) {
/*  50 */       if (object != null) {
/*  51 */          Util.showErrorDialog(this.owner, "Feil", object.toString());
/*     */       }
/*  53 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final Object doWork(Object[] params, JLabel labelInfo) {
/*  60 */       labelInfo.setText("Genererer pakkliste...");
/*  61 */       String errorMsg = null;
/*     */ 
/*     */       try {
/*  64 */          if (this.postShipment != null) {
/*  65 */             this.lazyLoadPostShipment();
/*  66 */             Set<OrderLine> orderLineSet = this.postShipment.getOrderLines();
/*     */ 
/*  68 */             this.generatePacklistReport(orderLineSet, this.owner);
/*     */          }
/*  70 */       } catch (ProTransException var5) {
/*  71 */          var5.printStackTrace();
/*  72 */          errorMsg = var5.getMessage();
/*     */       }
/*     */ 
/*  75 */       return errorMsg;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    private void generatePacklistReport(Set<OrderLine> orderLineSet, WindowInterface window) throws ProTransException {
/*  81 */       if (orderLineSet != null) {
/*  82 */          this.lazyLoadOrderLines(orderLineSet);
/*  83 */          ArrayListModel orderLines = new ArrayListModel(this.postShipment.getOrderLines());
/*     */ 
/*     */ 
/*  86 */          ReportViewer reportViewer = new ReportViewer("Pakkliste");
/*  87 */          reportViewer.generateProtransReportAndShow(new PacklistTableModel(orderLines, orderLines.size(), this.postShipment), "Pakkliste", ReportEnum.PACKLIST, (Map)null, window);
/*     */ 
/*     */       }
/*     */ 
/*  91 */    }
/*     */ 
/*     */    private void lazyLoadOrderLines(Set<OrderLine> orderLineSet) {
/*  94 */       OrderLineManager orderLineManager = (OrderLineManager)ModelUtil.getBean("orderLineManager");
/*     */       Iterator var3 = orderLineSet.iterator();
/*     */       while(var3.hasNext()) {
/*  97 */          OrderLine orderLine = (OrderLine)var3.next();
/*  98 */          orderLineManager.lazyLoad(orderLine, new LazyLoadOrderLineEnum[]{LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE});
/*     */ 
/*     */ 
/*     */       }
/*     */ 
/* 103 */    }
/*     */ 
/*     */    private void lazyLoadPostShipment() {
/* 106 */       PostShipmentManager postShipmentManager = (PostShipmentManager)ModelUtil.getBean("postShipmentManager");
/*     */ 
/*     */ 
/* 109 */       postShipmentManager.lazyLoad(this.postShipment, new LazyLoadPostShipmentEnum[]{LazyLoadPostShipmentEnum.ORDER_LINES});
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 114 */       if (this.postShipment.getDeviation() != null) {
/* 115 */          DeviationManager deviationManager = (DeviationManager)ModelUtil.getBean("deviationManager");
/*     */ 
/* 117 */          deviationManager.lazyLoad(this.postShipment.getDeviation(), new LazyLoadDeviationEnum[]{LazyLoadDeviationEnum.COMMENTS});
/*     */       }
/* 119 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void enableComponents(boolean enable) {
/* 125 */    }
/*     */ }
