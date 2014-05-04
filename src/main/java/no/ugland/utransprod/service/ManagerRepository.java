package no.ugland.utransprod.service;

public interface ManagerRepository {

    public abstract ArticleTypeManager getArticleTypeManager();

    public abstract ExternalOrderManager getExternalOrderManager();

    public abstract AttributeManager getAttributeManager();

    public abstract AttributeChoiceManager getAttributeChoiceManager();

    public abstract ExternalOrderLineManager getExternalOrderLineManager();

    public abstract EmployeeManager getEmployeeManager();

    public abstract EmployeeTypeManager getEmployeeTypeManager();

    public abstract PreventiveActionManager getPreventiveActionManager();

    public abstract DeviationManager getDeviationManager();

    public abstract OrderManager getOrderManager();

    public abstract AccidentManager getAccidentManager();

    public abstract JobFunctionManager getJobFunctionManager();

    public abstract OrderLineManager getOrderLineManager();

    public abstract ColliManager getColliManager();

    public abstract ProcentDoneManager getProcentDoneManager();

    public abstract TransportManager getTransportManager();

    public abstract ConstructionTypeManager getConstructionTypeManager();

    public abstract AssemblyManager getAssemblyManager();

    public abstract SupplierManager getSupplierManager();

    public abstract ApplicationUserManager getApplicationUserManager();

    public abstract TakstolInfoVManager getTakstolInfoVManager();

    public abstract OrdlnManager getOrdlnManager();

    public abstract IncomingOrderManager getIncomingOrderManager();

    public abstract ProductionUnitManager getProductionUnitManager();

    public abstract TakstolProductionVManager getTakstolProductionVManager();

    public abstract TakstolPackageVManager getTakstolPackageVManager();

    public abstract ProductAreaManager getProductAreaManager();

    public abstract ArticleTypeAttributeManager getArticleTypeAttributeManager();

    public abstract PostShipmentManager getPostShipmentManager();

    public abstract PacklistVManager getPacklistVManager();

    public abstract TransportCostManager getTransportCostManager();

    public abstract FaktureringVManager getFaktureringVManager();

    public abstract MainPackageVManager getMainPackageVManager();

    public abstract SumOrderReadyVManager getSumOrderReadyVManager();

    public abstract BudgetManager getBudgetManager();

    public abstract DeviationStatusManager getDeviationStatusManager();

    public abstract UdsalesmallManager getUdsalesmallManager();

    public abstract TransportSumVManager getTransportSumVManager();

    public abstract IntelleVManager getIntelleVManager();

    public abstract ProductAreaGroupManager getProductAreaGroupManager();

    public abstract CustTrManager getCustTrManager();

    public abstract AssemblyOverdueVManager getAssemblyOverdueVManager();

    public abstract FrontProductionVManager getFrontProductionVManager();

    public abstract FakturagrunnlagVManager getFakturagrunnlagVManager();

}