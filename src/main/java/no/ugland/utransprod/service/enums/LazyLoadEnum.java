/*    */ package no.ugland.utransprod.service.enums;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import no.ugland.utransprod.ProTransRuntimeException;
/*    */ 
/*    */ 
/*    */ public enum LazyLoadEnum {
/*    */    ACCIDENT_PARTICIPANTS("AccidentParticipants"),
/*    */    USER_ROLES("UserRoles"),
/*    */    USER_PRODUCT_AREA_GROUPS("UserProductAreaGroups"),
/*    */    ATTRIBUTES("Attributes"),
/*    */    CONSTRUCTION_TYPE_ARTICLE_ATTRIBUTES("ConstructionTypeArticleAttributes"),
/*    */    ARTICLE_TYPE_ARTICLE_TYPES("ArticleTypeArticleTypes"),
/*    */    ARTICLE_TYPE_ARTICLE_TYPE_REFS("ArticleTypeArticleTypeRefs"),
/*    */    CONSTRUCTION_TYPE_ARTICLES("ConstructionTypeArticles"),
/*    */    ORDERS("Orders"),
/*    */    ATTRIBUTE_CHOICES("AttributeChoices"),
/*    */    ORDER_LINES("OrderLines"),
/*    */    ORDER_LINE_ATTRIBUTES("OrderLineAttributes"),
/*    */    CONSTRUCTION_TYPE_ATTRIBUTES("ConstructionTypeAttributes"),
/*    */    ADDRESSES("Addresses"),
/*    */    ORDER_COMMENTS("OrderComments"),
/*    */    ORDER_COSTS("OrderCosts"),
/*    */    EXTERNAL_ORDER_LINES("ExternalOrderLines"),
/*    */    EXTERNAL_ORDER_LINE_ATTRIBUTES("ExternalOrderLineAttributes"),
/*    */    FUNCTION_CATEGORIES("FunctionCategories"),
/*    */    COLLIES("Collies"),
/*    */    POST_SHIPMENTS("PostShipments"),
/*    */    EXTERNAL_ORDERS("ExternalOrders"),
/*    */    DEVIATIONS("deviations"),
/*    */    PROCENT_DONES("ProcentDones"),
/*    */    POST_SHIPMENT_REFS("PostShipmentRefs"),
/*    */    PRODUCTION_UNIT_PRODUCT_AREA_GROUPS("ProductionUnitProductAreaGroups"),
/*    */    EMPLOYEES("Employees"),
/*    */    ASSEMBLIES("Assemblies"),
/*    */    USER_TYPE_ACCESSES("UserTypeAccesses"),
/*    */    PREVENTIVE_ACTION_COMMENTS("PreventiveActionComments"),
/*    */    PREVENTIVE_ACTION_COMMENT_COMMENT_TYPES("PreventiveActionCommentCommentTypes"),
/*    */    CUTTING_LINES("CuttingLines"),
/*    */    NONE(""),
/*    */    SUPPLIER_PRODUCT_AREA_GROUPS("SupplierProductAreaGroups");
/*    */ 
/*    */    private String methodName;
/*    */ 
/*    */    private LazyLoadEnum(String aMethodName) {
/* 48 */       this.methodName = aMethodName;
/* 49 */    }
/*    */    public void lazyLoad(Object object, LazyLoadEnum lazyLoadEnum) {
/*    */       try {
/* 52 */          Method method = object.getClass().getMethod("get" + this.methodName, (Class[])null);
/* 53 */          Set<Object> set = (Set)method.invoke(object, (Class[])null);
/* 54 */          if (lazyLoadEnum != NONE) {            Iterator var5 = set.iterator();            while(var5.hasNext()) {
/* 55 */                Object obj = var5.next();
/* 56 */                lazyLoadEnum.lazyLoad(obj, NONE);
/*    */             }
/*    */          }
/*    */ 
/* 60 */          set.iterator();
/* 61 */       } catch (Exception var7) {
/* 62 */          var7.printStackTrace();
/* 63 */          throw new ProTransRuntimeException(var7.getMessage());
/*    */       }
/* 65 */    }
/*    */ }
