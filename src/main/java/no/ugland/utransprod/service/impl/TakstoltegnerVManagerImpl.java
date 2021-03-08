/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import com.google.common.collect.ArrayListMultimap;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import no.ugland.utransprod.ProTransException;
/*    */ import no.ugland.utransprod.dao.TakstoltegnerVDAO;
/*    */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*    */ import no.ugland.utransprod.model.TakstoltegnerV;
/*    */ import no.ugland.utransprod.model.TakstoltegnerVSum;
/*    */ import no.ugland.utransprod.service.TakstoltegnerVManager;
/*    */ import no.ugland.utransprod.util.Periode;
/*    */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TakstoltegnerVManagerImpl implements TakstoltegnerVManager {
/*    */    private TakstoltegnerVDAO dao;
/*    */ 
/*    */    public Map<TakstoltegnerVSum, Collection<TakstoltegnerV>> findByPeriode(Periode periode) {
/* 26 */       Collection<TakstoltegnerV> tegnere = this.dao.findByPeriode(periode);
/* 27 */       Multimap<String, TakstoltegnerV> tegnerMap = ArrayListMultimap.create();
/*    */ 
/* 29 */       Iterator var4 = tegnere.iterator();      while(var4.hasNext()) {         TakstoltegnerV tegner = (TakstoltegnerV)var4.next();
/* 30 */          tegnerMap.put(tegner.getTrossDrawer() + tegner.getProductAreaGroupName(), tegner);
/*    */       }
/*    */ 
/* 33 */       Map<TakstoltegnerVSum, Collection<TakstoltegnerV>> sumMap = Maps.newHashMap();
/*    */ 
/* 35 */       Iterator var8 = tegnerMap.keySet().iterator();      while(var8.hasNext()) {         String tegnerNavn = (String)var8.next();
/* 36 */          TakstoltegnerVSum takstoltegnerVSum = new TakstoltegnerVSum();
/* 37 */          takstoltegnerVSum.generateSum(tegnerMap.get(tegnerNavn));
/* 38 */          sumMap.put(takstoltegnerVSum, tegnerMap.get(tegnerNavn));
/*    */       }
/*    */ 
/* 41 */       return sumMap;
/*    */    }
/*    */ 
/*    */    public void setTakstoltegnerVDAO(TakstoltegnerVDAO dao) {
/* 45 */       this.dao = dao;
/* 46 */    }
/*    */ 
/*    */ 
/*    */    public CheckObject checkExcel(ExcelReportSetting params) {
/* 50 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public List<?> findByParams(ExcelReportSetting params) throws ProTransException {
/* 56 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    public String getInfoButtom(ExcelReportSetting params) throws ProTransException {
/* 62 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    public String getInfoTop(ExcelReportSetting params) {
/* 67 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */    public Map<Object, Object> getReportDataMap(ExcelReportSetting params) throws ProTransException {
/* 72 */       Map<TakstoltegnerVSum, Collection<TakstoltegnerV>> dataList = this.findByPeriode(params.getPeriode());
/* 73 */       Map<Object, Object> dataMap = Maps.newHashMap();
/* 74 */       dataMap.put("Reportdata", dataList);
/* 75 */       return dataMap;
/*    */    }
/*    */ }
