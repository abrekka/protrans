/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.Hashtable;
/*    */ import java.util.Map;
/*    */ import no.ugland.utransprod.ProTransException;
/*    */ import no.ugland.utransprod.model.TransportCostAddition;
/*    */ import no.ugland.utransprod.service.ITransportCostAddition;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TransportCostAdditionImplFacory {
/*    */    private static Map<String, ITransportCostAddition> transportCostAdditionImplInstances = new Hashtable();
/*    */ 
/*    */    public static ITransportCostAddition getTransportCostAdditionImpl(TransportCostAddition addition) throws ProTransException {
/* 19 */       ITransportCostAddition transportCostAdditionImpl = (ITransportCostAddition)transportCostAdditionImplInstances.get(addition.getDescription());
/*    */ 
/* 21 */       if (transportCostAdditionImpl == null) {
/* 22 */          transportCostAdditionImpl = createTransportCostAdditionImpl(addition.getDescription(), addition);
/*    */ 
/* 24 */          transportCostAdditionImplInstances.put(addition.getDescription(), transportCostAdditionImpl);
/*    */ 
/*    */       }
/*    */ 
/* 28 */       return transportCostAdditionImpl;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    private static ITransportCostAddition createTransportCostAdditionImpl(String description, TransportCostAddition addition) throws ProTransException {
/* 34 */       if (description.equalsIgnoreCase("BreddeX2+høydeX2")) {
/* 35 */          return new AdditionWidhtHeight(addition, (String)null, "Lang garasje");
/* 36 */       } else if (description.equalsIgnoreCase("Takstein")) {
/* 37 */          return new AdditionTiles(addition, "Takstein", "Takstein");
/* 38 */       } else if (description.equalsIgnoreCase("Takstol")) {
/* 39 */          return new AdditionTruss(addition, "Takstoler", "Takstol");
/* 40 */       } else if (description.equalsIgnoreCase("Stående tak")) {
/* 41 */          return new AdditionStandingRoof(addition, (String)null, "Stående tak");
/* 42 */       } else if (description.equalsIgnoreCase("Gulvspon")) {
/* 43 */          return new AdditionGulvspon(addition, "Gulvspon", "Gulvspon");
/* 44 */       } else if (description.equalsIgnoreCase("Innredning")) {
/* 45 */          return new AdditionInnredning(addition, "iGarasjen innredning", "Innredning");
/* 46 */       } else if (description.equalsIgnoreCase("MaxBreddeX2+høydeX2")) {
/* 47 */          return new AdditionWidhtHeightMax(addition, (String)null, "Ekstra lang garasje");
/*    */       } else {
/* 49 */          throw new ProTransException("Ikke definert klasse for tillegg " + addition.getDescription());
/*    */       }
/*    */    }
/*    */ }
