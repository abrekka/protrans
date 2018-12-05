package no.ugland.utransprod.service;

import com.google.inject.Inject;

public class ManagerRepositoryImpl implements ManagerRepository {
    private ArticleTypeManager articleTypeManager;
    private ExternalOrderManager externalOrderManager;
    private AttributeManager attributeManager;
    private AttributeChoiceManager attributeChoiceManager;
    private ExternalOrderLineManager externalOrderLineManager;
    private EmployeeManager employeeManager;
    private EmployeeTypeManager employeeTypeManager;
    private PreventiveActionManager preventiveActionManager;
    private DeviationManager deviationManager;
    private DeviationVManager deviationVManager;
    private OrderManager orderManager;
    private AccidentManager accidentManager;
    private JobFunctionManager jobFunctionManager;
    private OrderLineManager orderLineManager;
    private ColliManager colliManager;
    private ProcentDoneManager procentDoneManager;
    private TransportManager transportManager;
    private ConstructionTypeManager constructionTypeManager;
    private AssemblyManager assemblyManager;
    private SupplierManager supplierManager;
    private ApplicationUserManager applicationUserManager;
    private TakstolInfoVManager takstolInfoVManager;
    private OrdlnManager ordlnManager;
    private IncomingOrderManager incomingOrderManager;
    private ProductionUnitManager productionUnitManager;
    private TakstolPackageVManager takstolPackageVManager;
    private TakstolProductionVManager takstolProductionVManager;
    private ProductAreaManager productAreaManager;
    private ArticleTypeAttributeManager articleTypeAttributeManager;
    private PostShipmentManager postShipmentManager;
    private PacklistVManager packlistVManager;
    private TransportCostManager transportCostManager;
    private FaktureringVManager faktureringVManager;
    private MainPackageVManager mainPackageVManager;
    private BudgetManager budgetManager;
    private SumOrderReadyVManager sumOrderReadyVManager;
    private DeviationStatusManager deviationStatusManager;
    private UdsalesmallManager udsalesmallManager;
    private TransportSumVManager transportSumVManager;
    private IntelleVManager ipkOrdManager;
    private ProductAreaGroupManager productAreaGroupManager;
    private CustTrManager custTrManager;
    private AssemblyOverdueVManager assemblyOverdueVManager;
    private FrontProductionVManager frontProductionVManager;
    private FakturagrunnlagVManager fakturagrunnlagVManager;
	private CostTypeManager costTypeManager;
	private CostUnitManager costUnitManager;

    public ManagerRepositoryImpl() {

    }

    @Inject
    public ManagerRepositoryImpl(ArticleTypeManager aArticleTypeManager, ExternalOrderManager aExternalOrderManager,
	    AttributeManager aAttributeManager, AttributeChoiceManager aAttributeChoiceManager, ExternalOrderLineManager aExternalOrderLineManager,
	    EmployeeManager aEmployeeManager, EmployeeTypeManager aEmployeeTypeManager, PreventiveActionManager aPreventiveActionManager,
	    DeviationManager aDeviationManager, OrderManager aOrderManager, AccidentManager aAccidentManager, JobFunctionManager aJobFunctionManager,
	    OrderLineManager aOrderLineManager, ColliManager aColliManager, ProcentDoneManager aProcentDoneManager,
	    TransportManager aTransportManager, ConstructionTypeManager aConstructionTypeManager, AssemblyManager aAssemblyManager,
	    SupplierManager aSupplierManager, ApplicationUserManager aApplicationUserManager, TakstolInfoVManager aTakstolInfoVManager,
	    OrdlnManager aOrdlnManager, IncomingOrderManager aIncomingOrderManager, ProductionUnitManager apProductionUnitManager,
	    TakstolPackageVManager aTakstolPackageVManager, TakstolProductionVManager aTakstolProductionVManager,
	    ProductAreaManager apProductAreaManager, ArticleTypeAttributeManager aArticleTypeAttributeManager,
	    PostShipmentManager aPostShipmentManager, PacklistVManager aPacklistVManager, TransportCostManager aTransportCostManager,
	    FaktureringVManager aFaktureringVManager, MainPackageVManager aMainPackageVManager, BudgetManager aProductionBudgetManager,
	    SumOrderReadyVManager aSumOrderReadyVManager, DeviationStatusManager aDeviationStatusManager, UdsalesmallManager aUdsalesmallManager,
	    TransportSumVManager aTransportSumVManager, IntelleVManager aIpkOrdManager, ProductAreaGroupManager aProductAreaGroupManager,
	    CustTrManager aCustTrManager, AssemblyOverdueVManager aAssemblyOverdueVManager, FrontProductionVManager frontProductionVManager,
	    FakturagrunnlagVManager fakturagrunnlagVManager,DeviationVManager aDeviationVManager,CostTypeManager aCostTypeManager,CostUnitManager aCostUnitManager) {
    	costUnitManager=aCostUnitManager;
    	costTypeManager=aCostTypeManager;
	ipkOrdManager = aIpkOrdManager;
	transportSumVManager = aTransportSumVManager;
	udsalesmallManager = aUdsalesmallManager;
	deviationStatusManager = aDeviationStatusManager;
	sumOrderReadyVManager = aSumOrderReadyVManager;
	budgetManager = aProductionBudgetManager;
	mainPackageVManager = aMainPackageVManager;
	faktureringVManager = aFaktureringVManager;
	transportCostManager = aTransportCostManager;
	postShipmentManager = aPostShipmentManager;
	productionUnitManager = apProductionUnitManager;
	incomingOrderManager = aIncomingOrderManager;
	ordlnManager = aOrdlnManager;
	takstolInfoVManager = aTakstolInfoVManager;
	applicationUserManager = aApplicationUserManager;
	supplierManager = aSupplierManager;
	constructionTypeManager = aConstructionTypeManager;
	transportManager = aTransportManager;
	procentDoneManager = aProcentDoneManager;
	colliManager = aColliManager;
	orderLineManager = aOrderLineManager;
	jobFunctionManager = aJobFunctionManager;
	accidentManager = aAccidentManager;
	orderManager = aOrderManager;
	deviationManager = aDeviationManager;
	deviationVManager = aDeviationVManager;
	preventiveActionManager = aPreventiveActionManager;
	employeeTypeManager = aEmployeeTypeManager;
	employeeManager = aEmployeeManager;
	externalOrderLineManager = aExternalOrderLineManager;
	attributeChoiceManager = aAttributeChoiceManager;
	attributeManager = aAttributeManager;
	articleTypeManager = aArticleTypeManager;
	externalOrderManager = aExternalOrderManager;
	assemblyManager = aAssemblyManager;
	takstolPackageVManager = aTakstolPackageVManager;
	takstolProductionVManager = aTakstolProductionVManager;
	productAreaManager = apProductAreaManager;
	articleTypeAttributeManager = aArticleTypeAttributeManager;
	packlistVManager = aPacklistVManager;
	productAreaGroupManager = aProductAreaGroupManager;
	custTrManager = aCustTrManager;
	assemblyOverdueVManager = aAssemblyOverdueVManager;
	this.frontProductionVManager = frontProductionVManager;
	this.fakturagrunnlagVManager = fakturagrunnlagVManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * no.ugland.utransprod.service.ManagerRepository#getArticleTypeManager()
     */
    public ArticleTypeManager getArticleTypeManager() {
	return articleTypeManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * no.ugland.utransprod.service.ManagerRepository#getExternalOrderManager()
     */
    public ExternalOrderManager getExternalOrderManager() {
	return externalOrderManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see no.ugland.utransprod.service.ManagerRepository#getAttributeManager()
     */
    public AttributeManager getAttributeManager() {
	return attributeManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * no.ugland.utransprod.service.ManagerRepository#getAttributeChoiceManager
     * ()
     */
    public AttributeChoiceManager getAttributeChoiceManager() {
	return attributeChoiceManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * no.ugland.utransprod.service.ManagerRepository#getExternalOrderLineManager
     * ()
     */
    public ExternalOrderLineManager getExternalOrderLineManager() {
	return externalOrderLineManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see no.ugland.utransprod.service.ManagerRepository#getEmployeeManager()
     */
    public EmployeeManager getEmployeeManager() {
	return employeeManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * no.ugland.utransprod.service.ManagerRepository#getEmployeeTypeManager()
     */
    public EmployeeTypeManager getEmployeeTypeManager() {
	return employeeTypeManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * no.ugland.utransprod.service.ManagerRepository#getPreventiveActionManager
     * ()
     */
    public PreventiveActionManager getPreventiveActionManager() {
	return preventiveActionManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see no.ugland.utransprod.service.ManagerRepository#getDeviationManager()
     */
    public DeviationManager getDeviationManager() {
	return deviationManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see no.ugland.utransprod.service.ManagerRepository#getOrderManager()
     */
    public OrderManager getOrderManager() {
	return orderManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see no.ugland.utransprod.service.ManagerRepository#getAccidentManager()
     */
    public AccidentManager getAccidentManager() {
	return accidentManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * no.ugland.utransprod.service.ManagerRepository#getJobFunctionManager()
     */
    public JobFunctionManager getJobFunctionManager() {
	return jobFunctionManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see no.ugland.utransprod.service.ManagerRepository#getOrderLineManager()
     */
    public OrderLineManager getOrderLineManager() {
	return orderLineManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see no.ugland.utransprod.service.ManagerRepository#getColliManager()
     */
    public ColliManager getColliManager() {
	return colliManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * no.ugland.utransprod.service.ManagerRepository#getProcentDoneManager()
     */
    public ProcentDoneManager getProcentDoneManager() {
	return procentDoneManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see no.ugland.utransprod.service.ManagerRepository#getTransportManager()
     */
    public TransportManager getTransportManager() {
	return transportManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * no.ugland.utransprod.service.ManagerRepository#getConstructionTypeManager
     * ()
     */
    public ConstructionTypeManager getConstructionTypeManager() {
	return constructionTypeManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see no.ugland.utransprod.service.ManagerRepository#getAssemblyManager()
     */
    public AssemblyManager getAssemblyManager() {
	return assemblyManager;
    }

    public SupplierManager getSupplierManager() {
	return supplierManager;
    }

    public ApplicationUserManager getApplicationUserManager() {
	return applicationUserManager;
    }

    public TakstolInfoVManager getTakstolInfoVManager() {
	return takstolInfoVManager;
    }

    public OrdlnManager getOrdlnManager() {
	return ordlnManager;
    }

    public IncomingOrderManager getIncomingOrderManager() {
	return incomingOrderManager;
    }

    public ProductionUnitManager getProductionUnitManager() {
	return productionUnitManager;
    }

    public TakstolPackageVManager getTakstolPackageVManager() {
	return takstolPackageVManager;
    }

    public TakstolProductionVManager getTakstolProductionVManager() {
	return takstolProductionVManager;
    }

    public ProductAreaManager getProductAreaManager() {
	return productAreaManager;
    }

    public ArticleTypeAttributeManager getArticleTypeAttributeManager() {
	return articleTypeAttributeManager;
    }

    public PostShipmentManager getPostShipmentManager() {
	return postShipmentManager;
    }

    public PacklistVManager getPacklistVManager() {
	return packlistVManager;
    }

    public TransportCostManager getTransportCostManager() {
	return transportCostManager;
    }

    public FaktureringVManager getFaktureringVManager() {
	return faktureringVManager;
    }

    public MainPackageVManager getMainPackageVManager() {
	return mainPackageVManager;
    }

    public BudgetManager getBudgetManager() {
	return budgetManager;
    }

    public SumOrderReadyVManager getSumOrderReadyVManager() {
	return sumOrderReadyVManager;
    }

    public DeviationStatusManager getDeviationStatusManager() {
	return deviationStatusManager;
    }

    public UdsalesmallManager getUdsalesmallManager() {
	return udsalesmallManager;
    }

    public TransportSumVManager getTransportSumVManager() {
	return transportSumVManager;
    }

    public IntelleVManager getIntelleVManager() {
	return ipkOrdManager;
    }

    public ProductAreaGroupManager getProductAreaGroupManager() {
	return productAreaGroupManager;
    }

    public CustTrManager getCustTrManager() {
	return custTrManager;
    }

    public AssemblyOverdueVManager getAssemblyOverdueVManager() {
	return assemblyOverdueVManager;
    }

    public FrontProductionVManager getFrontProductionVManager() {
	return frontProductionVManager;
    }

    public FakturagrunnlagVManager getFakturagrunnlagVManager() {
	return fakturagrunnlagVManager;
    }

	public DeviationVManager getDeviationVManager() {
		return deviationVManager;
	}

	public CostTypeManager getCostTypeManager() {
		return costTypeManager;
	}

	public CostUnitManager getCostUnitManager() {
		return costUnitManager;
	}

}
