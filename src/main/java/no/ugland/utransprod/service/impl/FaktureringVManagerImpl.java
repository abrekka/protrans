/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import no.ugland.utransprod.dao.FaktureringVDAO;
/*     */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*     */ import no.ugland.utransprod.model.FaktureringV;
/*     */ import no.ugland.utransprod.model.ProductAreaGroup;
/*     */ import no.ugland.utransprod.service.FaktureringVManager;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
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
/*     */ public class FaktureringVManagerImpl extends AbstractApplyListManager<FaktureringV> implements FaktureringVManager {
/*     */    private FaktureringVDAO dao;
/*     */ 
/*     */    public final void setFaktureringVDAO(FaktureringVDAO aDao) {
/*  27 */       this.dao = aDao;
/*  28 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<FaktureringV> findAllApplyable() {
/*  34 */       return this.dao.findAll();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<FaktureringV> findApplyableByCustomerNr(Integer customerNr) {
/*  41 */       return this.dao.findByCustomerNr(customerNr);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<FaktureringV> findApplyableByOrderNr(String orderNr) {
/*  48 */       return this.dao.findByOrderNr(orderNr);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refresh(FaktureringV faktureringV) {
/*  55 */       this.dao.refresh(faktureringV);
/*     */ 
/*  57 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<FaktureringV> findByParams(ExcelReportSetting params) {
/*  65 */       return this.dao.findByParams(params);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final String getInfoButtom(ExcelReportSetting params) {
/*  75 */       return null;
/*     */    }
/*     */ 
/*     */    public final String getInfoTop(ExcelReportSetting params) {
/*  79 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/*  89 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public CheckObject checkExcel(ExcelReportSetting params) {
/*  94 */       return null;
/*     */    }
/*     */ 
/*     */    public List<FaktureringV> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
/*  98 */       return this.dao.findByCustomerNrAndProductAreaGroup(customerNr, productAreaGroup);
/*     */    }
/*     */ 
/*     */    public List<FaktureringV> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
/* 102 */       return this.dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
/*     */    }
/*     */ 
/*     */    public FaktureringV findByOrderNr(String orderNr) {
/* 106 */       List<FaktureringV> list = this.dao.findByOrderNr(orderNr);
/* 107 */       return list != null && list.size() == 1 ? (FaktureringV)list.get(0) : null;
/*     */    }
/*     */ }
