/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import no.ugland.utransprod.dao.CraningCostDAO;
/*    */ import no.ugland.utransprod.model.CraningCost;
/*    */ import no.ugland.utransprod.service.CraningCostManager;
/*    */ import no.ugland.utransprod.util.rules.Craningbasis;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CraningCostManagerImpl implements CraningCostManager {
/*    */    private CraningCostDAO dao;
/*    */ 
/*    */    public void setCraningCostDAO(CraningCostDAO aDao) {
/* 18 */       this.dao = aDao;
/* 19 */    }
/*    */ 
/*    */    public BigDecimal findCostByCraningEnum(Craningbasis craningEnum) {
/* 22 */       CraningCost craningCost = this.dao.findByRuleId(craningEnum.getRuleId());
/* 23 */       return craningCost != null ? craningCost.getCostValue() : null;
/*    */    }
/*    */ }
