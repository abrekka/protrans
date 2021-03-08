/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.TransportDAO;
/*     */ import no.ugland.utransprod.model.Transport;
/*     */ import no.ugland.utransprod.service.TransportManager;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadTransportEnum;
/*     */ import no.ugland.utransprod.util.Periode;
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
/*     */ public class TransportManagerImpl extends ManagerImpl<Transport> implements TransportManager {
/*     */    public final List<Transport> findAll() {
/*  23 */       return ((TransportDAO)this.dao).findAll();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveTransport(Transport transport) {
/*  30 */       this.dao.saveObject(transport);
/*     */ 
/*  32 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeTransport(Transport transport) {
/*  39 */       this.dao.removeObject(transport.getTransportId());
/*     */ 
/*  41 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Transport> findByYearAndWeek(Integer year, Integer week) {
/*  48 */       return ((TransportDAO)this.dao).findByYearAndWeek(year, week);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Transport> findByTransport(Transport transport) {
/*  58 */       return this.dao.findByExampleLike(transport);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Transport> findByObject(Transport object) {
/*  67 */       return this.findByTransport(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(Transport transport) {
/*  75 */       ((TransportDAO)this.dao).refreshObject(transport);
/*     */ 
/*  77 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(Transport object) {
/*  84 */       this.removeTransport(object);
/*     */ 
/*  86 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(Transport object) {
/*  93 */       this.saveTransport(object);
/*     */ 
/*  95 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void lazyLoadTransport(Transport transport, LazyLoadTransportEnum[] enums) {
/* 103 */       ((TransportDAO)this.dao).lazyLoadTransport(transport, enums);
/*     */ 
/* 105 */    }
/*     */ 
/*     */ 
/*     */    public final List<Transport> findBetweenYearAndWeek(Integer year, Integer fromWeek, Integer toWeek, String[] orderBy) {
/* 109 */       return ((TransportDAO)this.dao).findBetweenYearAndWeek(year, fromWeek, toWeek, orderBy);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Transport> findNewTransports() {
/* 116 */       return ((TransportDAO)this.dao).findNewTransports();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Transport> findByYearAndWeekAndProductAreaGroup(Integer year, Integer week, boolean ikkeTaMedOpplastet, String transportfirma) {
/* 124 */       return ((TransportDAO)this.dao).findByYearAndWeekAndProductAreaGroup(year, week, ikkeTaMedOpplastet, transportfirma);
/*     */    }
/*     */ 
/*     */ 
/*     */    public final List<Transport> findSentInPeriode(Periode periode) {
/* 129 */       return ((TransportDAO)this.dao).findSentInPeriode(periode);
/*     */    }
/*     */ 
/*     */    public final List<Transport> findInPeriode(Periode periode, String productAreaGroupName) {
/* 133 */       return ((TransportDAO)this.dao).findInPeriode(periode, productAreaGroupName);
/*     */    }
/*     */ 
/*     */    public void lazyLoad(Transport object, Enum[] enums) {
/* 137 */       this.lazyLoadTransport(object, (LazyLoadTransportEnum[])((LazyLoadTransportEnum[])enums));
/*     */ 
/* 139 */    }
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(Transport object) {
/* 143 */       return object.getTransportId();
/*     */    }
/*     */ 
/*     */    public Transport findById(Integer id) {
/* 147 */       return (Transport)((TransportDAO)this.dao).getObject(id);
/*     */    }
/*     */ }
