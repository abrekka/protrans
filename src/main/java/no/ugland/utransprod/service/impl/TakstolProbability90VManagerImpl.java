/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.ProTransException;
/*     */ import no.ugland.utransprod.dao.TakstolProbability90VDAO;
/*     */ import no.ugland.utransprod.gui.handlers.CheckObject;
/*     */ import no.ugland.utransprod.model.TakstolAllV;
/*     */ import no.ugland.utransprod.model.TakstolProbability90V;
/*     */ import no.ugland.utransprod.service.ImportOrderProb90VManager;
/*     */ import no.ugland.utransprod.service.TakstolAllVManager;
/*     */ import no.ugland.utransprod.service.enums.ProductAreaGroupEnum;
/*     */ import no.ugland.utransprod.util.excel.AntallSum;
/*     */ import no.ugland.utransprod.util.excel.ExcelReportSetting;
/*     */ import no.ugland.utransprod.util.excel.OrdreReserveTakstol;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TakstolProbability90VManagerImpl implements ImportOrderProb90VManager {
/*     */    public TakstolProbability90VDAO dao;
/*     */    private TakstolAllVManager takstolAllVManager;
/*     */ 
/*     */    public List<TakstolProbability90V> findAllTakstoler() {
/*  32 */       return this.dao.findAllTakstoler();
/*     */    }
/*     */ 
/*     */ 
/*     */    public void setTakstolProbability90VDAO(TakstolProbability90VDAO takstolProbability90VDAO) {
/*  37 */       this.dao = takstolProbability90VDAO;
/*  38 */    }
/*     */ 
/*     */ 
/*     */    public CheckObject checkExcel(ExcelReportSetting params) {
/*  42 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<?> findByParams(ExcelReportSetting params) throws ProTransException {
/*  48 */       return this.getReportBasis();
/*     */    }
/*     */ 
/*     */    private List<OrdreReserveTakstol> getReportBasis() {
/*  52 */       Set<OrdreReserveTakstol> ordreReserveList = new HashSet();
/*  53 */       List<TakstolProbability90V> takstoler90 = this.findAllTakstoler();
/*  54 */       ordreReserveList.addAll(this.convertTakstoler(takstoler90));
/*  55 */       List<TakstolAllV> takstolProduksjonsliste = this.takstolAllVManager.findAllNotProduced();
/*     */ 
/*     */ 
/*  58 */       ordreReserveList.addAll(this.convertTakstolProduksjonsListe(takstolProduksjonsliste));
/*  59 */       List<OrdreReserveTakstol> list = new ArrayList(ordreReserveList);
/*  60 */       Collections.sort(list);
/*  61 */       return list;
/*     */    }
/*     */ 
/*     */ 
/*     */    private Collection<? extends OrdreReserveTakstol> convertTakstolProduksjonsListe(List<TakstolAllV> takstolProduksjonsliste) {
/*  66 */       List<OrdreReserveTakstol> ordreReserveList = new ArrayList();
/*  67 */       if (takstolProduksjonsliste != null) {  Iterator var3 = takstolProduksjonsliste.iterator();  while(var3.hasNext()) {
/*  68 */      TakstolAllV order = (TakstolAllV)var3.next();
/*  69 */      if (order.getProbability() == 100) {
/*  70 */         ordreReserveList.add(this.createOrdreReserveTakstol(order));
/*     */      }  }
/*     */       }
/*     */ 
/*  74 */       return ordreReserveList;
/*     */    }
/*     */ 
/*     */    private OrdreReserveTakstol createOrdreReserveTakstol(TakstolAllV order) {
/*  78 */       OrdreReserveTakstol ordreReserveTakstol = new OrdreReserveTakstol();
/*  79 */       ordreReserveTakstol.setType("Avrop");
/*     */ 
/*  81 */       ordreReserveTakstol.setProductAreaGroup(order.getProductAreaGroupName());
/*     */ 
/*  83 */       ordreReserveTakstol.setCustomerNr(String.valueOf(order.getCustomerNr()));
/*  84 */       ordreReserveTakstol.setCustomerName(order.getCustomerName());
/*  85 */       ordreReserveTakstol.setOrderNr(order.getOrderNr());
/*  86 */       ordreReserveTakstol.setOwnProduction(this.getOwnProduction(order));
/*  87 */       ordreReserveTakstol.setDeliveryCost(order.getDeliveryCost());
/*  88 */       ordreReserveTakstol.setProductionDate(order.getProductionDate());
/*  89 */       ordreReserveTakstol.setTransportYear(order.getTransportYear());
/*  90 */       ordreReserveTakstol.setTransportWeek(order.getTransportWeek());
/*  91 */       return ordreReserveTakstol;
/*     */    }
/*     */ 
/*     */    private BigDecimal getOwnProduction(TakstolAllV order) {
/*  95 */       return order.getProductAreaGroupName().equalsIgnoreCase("Takstol") ? order.getOwnProduction() : order.getOwnInternalProduction();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    private List<OrdreReserveTakstol> convertTakstoler(List<TakstolProbability90V> takstoler) {
/* 102 */       List<OrdreReserveTakstol> ordreReserveList = new ArrayList();
/* 103 */       if (takstoler != null) {
/* 104 */   Iterator var3 = takstoler.iterator();  while(var3.hasNext()) {     TakstolProbability90V order = (TakstolProbability90V)var3.next();
/* 105 */      ordreReserveList.add(this.createOrdreReserveTakstol(order));
/*     */   }      }
/*     */ 
/* 108 */       return ordreReserveList;
/*     */    }
/*     */ 
/*     */ 
/*     */    private OrdreReserveTakstol createOrdreReserveTakstol(TakstolProbability90V order) {
/* 113 */       OrdreReserveTakstol ordreReserveTakstol = new OrdreReserveTakstol();
/* 114 */       ordreReserveTakstol.setType("Ordre");
/* 115 */       ordreReserveTakstol.setProductAreaGroup(ProductAreaGroupEnum.getProductAreGroupByProdno(order.getProductAreaNr()).getProductAreaGroupName());
/*     */ 
/*     */ 
/* 118 */       ordreReserveTakstol.setCustomerNr(order.getCustomerNr());
/* 119 */       ordreReserveTakstol.setCustomerName(order.getCustomerName());
/* 120 */       ordreReserveTakstol.setOrderNr(order.getNumber1());
/* 121 */       ordreReserveTakstol.setOwnProduction(order.getOwnProduction());
/* 122 */       ordreReserveTakstol.setDeliveryCost(order.getDeliveryCost());
/* 123 */       return ordreReserveTakstol;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getInfoButtom(ExcelReportSetting params) throws ProTransException {
/* 129 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public String getInfoTop(ExcelReportSetting params) {
/* 134 */       return null;
/*     */    }
/*     */ 
/*     */    public Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
/* 138 */       Map<Object, Object> reportMap = new Hashtable();
/* 139 */       List<OrdreReserveTakstol> reportBasis = this.getReportBasis();
/* 140 */       reportMap.put("Basis", reportBasis);
/* 141 */       Map<String, AntallSum> reportSum = this.getReportSum(reportBasis);
/* 142 */       reportMap.put("Sum", reportSum);
/*     */ 
/* 144 */       return reportMap;
/*     */    }
/*     */ 
/*     */ 
/*     */    private Map<String, AntallSum> getReportSum(List<OrdreReserveTakstol> reportBasis) {
/* 149 */       Map<String, AntallSum> basisMap = new Hashtable();
/* 150 */       if (reportBasis != null) {  Iterator var3 = reportBasis.iterator();  while(var3.hasNext()) {
/* 151 */      OrdreReserveTakstol order = (OrdreReserveTakstol)var3.next();
/* 152 */      String key = this.getKeyValue(order);
/* 153 */      AntallSum antallSum = (AntallSum)basisMap.get(key);
/* 154 */      antallSum = antallSum == null ? new AntallSum() : antallSum;
/* 155 */      antallSum.incrementNumberOf();
/* 156 */      antallSum.add(order.getOwnProduction());
/* 157 */      basisMap.put(key, antallSum);
/*     */   }      }
/*     */ 
/* 160 */       return basisMap;
/*     */    }
/*     */ 
/*     */    private String getKeyValue(OrdreReserveTakstol order) {
/* 164 */       if (order.getType().equalsIgnoreCase("Ordre")) {
/* 165 */   return "IkkeProsjektertEkstern";
/*     */ 
/* 167 */       } else if (!order.getProductAreaGroup().equalsIgnoreCase("Takstol")) {
/* 168 */   return order.getOwnProduction().equals(BigDecimal.ZERO) ? "IkkeProsjektertIntern" : "ProsjektertIntern";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       } else {
/* 174 */   return "ProsjektertEkstern";
/*     */       }
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setTakstolAllVManager(TakstolAllVManager takstolAllVManager) {
/* 181 */       this.takstolAllVManager = takstolAllVManager;
/* 182 */    }
/*     */ }
