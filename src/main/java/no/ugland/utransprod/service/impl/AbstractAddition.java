/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import no.ugland.utransprod.model.TransportCostAddition;
/*    */ import no.ugland.utransprod.service.ITransportCostAddition;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractAddition implements ITransportCostAddition {
/*    */    TransportCostAddition transportCostAdditon;
/*    */    String articlePath;
/*    */    String info;
/*    */ 
/*    */    public AbstractAddition(TransportCostAddition addition, String aArticlePath, String someInfo) {
/* 15 */       this.transportCostAdditon = addition;
/* 16 */       this.articlePath = aArticlePath;
/* 17 */       this.info = someInfo;
/* 18 */    }
/*    */ 
/*    */    public final String getArticlePath() {
/* 21 */       return this.articlePath;
/*    */    }
/*    */ 
/*    */    public final String getInfo() {
/* 25 */       return this.info;
/*    */    }
/*    */ }
