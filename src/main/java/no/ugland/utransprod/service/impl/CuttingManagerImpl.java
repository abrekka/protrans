/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.ProTransException;
/*    */ import no.ugland.utransprod.dao.CuttingDAO;
/*    */ import no.ugland.utransprod.model.Cutting;
/*    */ import no.ugland.utransprod.service.CuttingManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CuttingManagerImpl extends ManagerImpl<Cutting> implements CuttingManager {
/*    */   protected Serializable getObjectId(Cutting object) {
/* 15 */      return object.getCuttingId();
/*    */   }
/*    */ 
/*    */   public Cutting findByProId(String aProId) {
/* 19 */      Cutting cutting = new Cutting();
/* 20 */      cutting.setProId(aProId);
/* 21 */      List<Cutting> cuttings = this.dao.findByExample(cutting);
/* 22 */      return cuttings.size() == 1 ? (Cutting)cuttings.get(0) : null;
/*    */   }
/*    */ 
/*    */   public void removeCutting(Cutting cutting) {
/* 26 */      this.dao.removeObject(cutting.getCuttingId());
/*    */ 
/* 28 */   }
/*    */ 
/*    */   public void saveCutting(Cutting cutting, Boolean overwriteExistingCutting) throws ProTransException {
/* 31 */      Cutting existingCutting = this.findByProId(cutting.getProId());
/* 32 */      if (existingCutting != null && !existingCutting.getOrder().equals(cutting.getOrder())) {
/* 33 */         throw new ProTransException("Kappfil med proid " + cutting.getProId() + " er allerede importert!");
/*    */      } else {
/* 35 */         if (overwriteExistingCutting) {
/* 36 */            this.checkAndDeleteExisting(cutting);
/*    */         }
/* 38 */         this.dao.saveObject(cutting);
/*    */      }
/* 40 */   }
/*    */ 
/*    */   private void checkAndDeleteExisting(Cutting cutting) {
/* 43 */      Cutting oldCutting = ((CuttingDAO)this.dao).findByOrder(cutting.getOrder());
/* 44 */      if (oldCutting != null) {
/* 45 */         this.removeCutting(oldCutting);
/*    */      }
/*    */ 
/* 48 */   }
/*    */ }
