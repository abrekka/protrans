package no.ugland.utransprod.service.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.hibernate.Hibernate;

import no.ugland.utransprod.ProTransRuntimeException;

public enum LazyLoadEnum {
    ACCIDENT_PARTICIPANTS("AccidentParticipants"),
    USER_ROLES("UserRoles"),
    USER_PRODUCT_AREA_GROUPS("UserProductAreaGroups"),
    ATTRIBUTES("Attributes"),
    CONSTRUCTION_TYPE_ARTICLE_ATTRIBUTES("ConstructionTypeArticleAttributes"),
    ARTICLE_TYPE_ARTICLE_TYPES("ArticleTypeArticleTypes"),
    ARTICLE_TYPE_ARTICLE_TYPE_REFS("ArticleTypeArticleTypeRefs"),
    CONSTRUCTION_TYPE_ARTICLES("ConstructionTypeArticles"),
    ORDERS("Orders"),
    ATTRIBUTE_CHOICES("AttributeChoices"),
    ORDER_LINES("OrderLines"),
    ORDER_LINE_ATTRIBUTES("OrderLineAttributes"),
    CONSTRUCTION_TYPE_ATTRIBUTES("ConstructionTypeAttributes"),
    ADDRESSES("Addresses"),
    ORDER_COMMENTS("OrderComments"),
    ORDER_COSTS("OrderCosts"),
    EXTERNAL_ORDER_LINES("ExternalOrderLines"),
    EXTERNAL_ORDER_LINE_ATTRIBUTES("ExternalOrderLineAttributes"),
    FUNCTION_CATEGORIES("FunctionCategories"),
    COLLIES("Collies"),
    POST_SHIPMENTS("PostShipments"),
    EXTERNAL_ORDERS("ExternalOrders"),
    DEVIATIONS("deviations"),
    PROCENT_DONES("ProcentDones"),
    POST_SHIPMENT_REFS("PostShipmentRefs"),
    PRODUCTION_UNIT_PRODUCT_AREA_GROUPS("ProductionUnitProductAreaGroups"),
    EMPLOYEES("Employees"),
    ASSEMBLIES("Assemblies"),
    USER_TYPE_ACCESSES("UserTypeAccesses"),
    PREVENTIVE_ACTION_COMMENTS("PreventiveActionComments"),
    PREVENTIVE_ACTION_COMMENT_COMMENT_TYPES("PreventiveActionCommentCommentTypes"),
    CUTTING_LINES("CuttingLines"),
    NONE(""), SUPPLIER_PRODUCT_AREA_GROUPS("SupplierProductAreaGroups");
    
    private String methodName;
    private LazyLoadEnum(String aMethodName){
        methodName=aMethodName;
    }
    public void lazyLoad(Object object,LazyLoadEnum lazyLoadEnum){
        try {
            Method method = object.getClass().getMethod("get"+methodName, null);
            Set<Object> set = (Set<Object>)method.invoke(object, (Class[])null);
            if(lazyLoadEnum!=NONE){
                for(Object obj:set){
                    lazyLoadEnum.lazyLoad(obj, NONE);
                    
                }
            }
            set.iterator();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProTransRuntimeException(e.getMessage());
        }
    }
    
}
