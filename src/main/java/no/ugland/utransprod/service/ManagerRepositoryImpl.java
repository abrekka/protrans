/*     */ package no.ugland.utransprod.service;
/*     */ 
/*     */ import com.google.inject.Inject;
/*     */ 
/*     */ 
/*     */ public class ManagerRepositoryImpl implements ManagerRepository {
/*     */    private ArticleTypeManager articleTypeManager;
/*     */    private ExternalOrderManager externalOrderManager;
/*     */    private AttributeManager attributeManager;
/*     */    private AttributeChoiceManager attributeChoiceManager;
/*     */    private ExternalOrderLineManager externalOrderLineManager;
/*     */    private EmployeeManager employeeManager;
/*     */    private EmployeeTypeManager employeeTypeManager;
/*     */    private PreventiveActionManager preventiveActionManager;
/*     */    private DeviationManager deviationManager;
/*     */    private DeviationVManager deviationVManager;
/*     */    private OrderManager orderManager;
/*     */    private AccidentManager accidentManager;
/*     */    private JobFunctionManager jobFunctionManager;
/*     */    private OrderLineManager orderLineManager;
/*     */    private ColliManager colliManager;
/*     */    private ProcentDoneManager procentDoneManager;
/*     */    private TransportManager transportManager;
/*     */    private ConstructionTypeManager constructionTypeManager;
/*     */    private AssemblyManager assemblyManager;
/*     */    private SupplierManager supplierManager;
/*     */    private ApplicationUserManager applicationUserManager;
/*     */    private TakstolInfoVManager takstolInfoVManager;
/*     */    private OrdlnManager ordlnManager;
/*     */    private IncomingOrderManager incomingOrderManager;
/*     */    private ProductionUnitManager productionUnitManager;
/*     */    private TakstolPackageVManager takstolPackageVManager;
/*     */    private TakstolProductionVManager takstolProductionVManager;
/*     */    private ProductAreaManager productAreaManager;
/*     */    private ArticleTypeAttributeManager articleTypeAttributeManager;
/*     */    private PostShipmentManager postShipmentManager;
/*     */    private PacklistVManager packlistVManager;
/*     */    private TransportCostManager transportCostManager;
/*     */    private FaktureringVManager faktureringVManager;
/*     */    private MainPackageVManager mainPackageVManager;
/*     */    private BudgetManager budgetManager;
/*     */    private SumOrderReadyVManager sumOrderReadyVManager;
/*     */    private DeviationStatusManager deviationStatusManager;
/*     */    private UdsalesmallManager udsalesmallManager;
/*     */    private TransportSumVManager transportSumVManager;
/*     */    private IntelleVManager ipkOrdManager;
/*     */    private ProductAreaGroupManager productAreaGroupManager;
/*     */    private CustTrManager custTrManager;
/*     */    private AssemblyOverdueVManager assemblyOverdueVManager;
/*     */    private FrontProductionVManager frontProductionVManager;
/*     */    private FakturagrunnlagVManager fakturagrunnlagVManager;
/*     */    private CostTypeManager costTypeManager;
/*     */    private CostUnitManager costUnitManager;
/*     */ 
/*     */    public ManagerRepositoryImpl() {
/*  56 */    }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    @Inject
/*     */    public ManagerRepositoryImpl(ArticleTypeManager aArticleTypeManager, ExternalOrderManager aExternalOrderManager, AttributeManager aAttributeManager, AttributeChoiceManager aAttributeChoiceManager, ExternalOrderLineManager aExternalOrderLineManager, EmployeeManager aEmployeeManager, EmployeeTypeManager aEmployeeTypeManager, PreventiveActionManager aPreventiveActionManager, DeviationManager aDeviationManager, OrderManager aOrderManager, AccidentManager aAccidentManager, JobFunctionManager aJobFunctionManager, OrderLineManager aOrderLineManager, ColliManager aColliManager, ProcentDoneManager aProcentDoneManager, TransportManager aTransportManager, ConstructionTypeManager aConstructionTypeManager, AssemblyManager aAssemblyManager, SupplierManager aSupplierManager, ApplicationUserManager aApplicationUserManager, TakstolInfoVManager aTakstolInfoVManager, OrdlnManager aOrdlnManager, IncomingOrderManager aIncomingOrderManager, ProductionUnitManager apProductionUnitManager, TakstolPackageVManager aTakstolPackageVManager, TakstolProductionVManager aTakstolProductionVManager, ProductAreaManager apProductAreaManager, ArticleTypeAttributeManager aArticleTypeAttributeManager, PostShipmentManager aPostShipmentManager, PacklistVManager aPacklistVManager, TransportCostManager aTransportCostManager, FaktureringVManager aFaktureringVManager, MainPackageVManager aMainPackageVManager, BudgetManager aProductionBudgetManager, SumOrderReadyVManager aSumOrderReadyVManager, DeviationStatusManager aDeviationStatusManager, UdsalesmallManager aUdsalesmallManager, TransportSumVManager aTransportSumVManager, IntelleVManager aIpkOrdManager, ProductAreaGroupManager aProductAreaGroupManager, CustTrManager aCustTrManager, AssemblyOverdueVManager aAssemblyOverdueVManager, FrontProductionVManager frontProductionVManager, FakturagrunnlagVManager fakturagrunnlagVManager, DeviationVManager aDeviationVManager, CostTypeManager aCostTypeManager, CostUnitManager aCostUnitManager) {
/*  75 */       this.costUnitManager = aCostUnitManager;
/*  76 */       this.costTypeManager = aCostTypeManager;
/*  77 */       this.ipkOrdManager = aIpkOrdManager;
/*  78 */       this.transportSumVManager = aTransportSumVManager;
/*  79 */       this.udsalesmallManager = aUdsalesmallManager;
/*  80 */       this.deviationStatusManager = aDeviationStatusManager;
/*  81 */       this.sumOrderReadyVManager = aSumOrderReadyVManager;
/*  82 */       this.budgetManager = aProductionBudgetManager;
/*  83 */       this.mainPackageVManager = aMainPackageVManager;
/*  84 */       this.faktureringVManager = aFaktureringVManager;
/*  85 */       this.transportCostManager = aTransportCostManager;
/*  86 */       this.postShipmentManager = aPostShipmentManager;
/*  87 */       this.productionUnitManager = apProductionUnitManager;
/*  88 */       this.incomingOrderManager = aIncomingOrderManager;
/*  89 */       this.ordlnManager = aOrdlnManager;
/*  90 */       this.takstolInfoVManager = aTakstolInfoVManager;
/*  91 */       this.applicationUserManager = aApplicationUserManager;
/*  92 */       this.supplierManager = aSupplierManager;
/*  93 */       this.constructionTypeManager = aConstructionTypeManager;
/*  94 */       this.transportManager = aTransportManager;
/*  95 */       this.procentDoneManager = aProcentDoneManager;
/*  96 */       this.colliManager = aColliManager;
/*  97 */       this.orderLineManager = aOrderLineManager;
/*  98 */       this.jobFunctionManager = aJobFunctionManager;
/*  99 */       this.accidentManager = aAccidentManager;
/* 100 */       this.orderManager = aOrderManager;
/* 101 */       this.deviationManager = aDeviationManager;
/* 102 */       this.deviationVManager = aDeviationVManager;
/* 103 */       this.preventiveActionManager = aPreventiveActionManager;
/* 104 */       this.employeeTypeManager = aEmployeeTypeManager;
/* 105 */       this.employeeManager = aEmployeeManager;
/* 106 */       this.externalOrderLineManager = aExternalOrderLineManager;
/* 107 */       this.attributeChoiceManager = aAttributeChoiceManager;
/* 108 */       this.attributeManager = aAttributeManager;
/* 109 */       this.articleTypeManager = aArticleTypeManager;
/* 110 */       this.externalOrderManager = aExternalOrderManager;
/* 111 */       this.assemblyManager = aAssemblyManager;
/* 112 */       this.takstolPackageVManager = aTakstolPackageVManager;
/* 113 */       this.takstolProductionVManager = aTakstolProductionVManager;
/* 114 */       this.productAreaManager = apProductAreaManager;
/* 115 */       this.articleTypeAttributeManager = aArticleTypeAttributeManager;
/* 116 */       this.packlistVManager = aPacklistVManager;
/* 117 */       this.productAreaGroupManager = aProductAreaGroupManager;
/* 118 */       this.custTrManager = aCustTrManager;
/* 119 */       this.assemblyOverdueVManager = aAssemblyOverdueVManager;
/* 120 */       this.frontProductionVManager = frontProductionVManager;
/* 121 */       this.fakturagrunnlagVManager = fakturagrunnlagVManager;
/* 122 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ArticleTypeManager getArticleTypeManager() {
/* 131 */       return this.articleTypeManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ExternalOrderManager getExternalOrderManager() {
/* 141 */       return this.externalOrderManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public AttributeManager getAttributeManager() {
/* 150 */       return this.attributeManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public AttributeChoiceManager getAttributeChoiceManager() {
/* 161 */       return this.attributeChoiceManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ExternalOrderLineManager getExternalOrderLineManager() {
/* 172 */       return this.externalOrderLineManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public EmployeeManager getEmployeeManager() {
/* 181 */       return this.employeeManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public EmployeeTypeManager getEmployeeTypeManager() {
/* 191 */       return this.employeeTypeManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public PreventiveActionManager getPreventiveActionManager() {
/* 202 */       return this.preventiveActionManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public DeviationManager getDeviationManager() {
/* 211 */       return this.deviationManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public OrderManager getOrderManager() {
/* 220 */       return this.orderManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public AccidentManager getAccidentManager() {
/* 229 */       return this.accidentManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public JobFunctionManager getJobFunctionManager() {
/* 239 */       return this.jobFunctionManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public OrderLineManager getOrderLineManager() {
/* 248 */       return this.orderLineManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ColliManager getColliManager() {
/* 257 */       return this.colliManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ProcentDoneManager getProcentDoneManager() {
/* 267 */       return this.procentDoneManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public TransportManager getTransportManager() {
/* 276 */       return this.transportManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ConstructionTypeManager getConstructionTypeManager() {
/* 287 */       return this.constructionTypeManager;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public AssemblyManager getAssemblyManager() {
/* 296 */       return this.assemblyManager;
/*     */    }
/*     */ 
/*     */    public SupplierManager getSupplierManager() {
/* 300 */       return this.supplierManager;
/*     */    }
/*     */ 
/*     */    public ApplicationUserManager getApplicationUserManager() {
/* 304 */       return this.applicationUserManager;
/*     */    }
/*     */ 
/*     */    public TakstolInfoVManager getTakstolInfoVManager() {
/* 308 */       return this.takstolInfoVManager;
/*     */    }
/*     */ 
/*     */    public OrdlnManager getOrdlnManager() {
/* 312 */       return this.ordlnManager;
/*     */    }
/*     */ 
/*     */    public IncomingOrderManager getIncomingOrderManager() {
/* 316 */       return this.incomingOrderManager;
/*     */    }
/*     */ 
/*     */    public ProductionUnitManager getProductionUnitManager() {
/* 320 */       return this.productionUnitManager;
/*     */    }
/*     */ 
/*     */    public TakstolPackageVManager getTakstolPackageVManager() {
/* 324 */       return this.takstolPackageVManager;
/*     */    }
/*     */ 
/*     */    public TakstolProductionVManager getTakstolProductionVManager() {
/* 328 */       return this.takstolProductionVManager;
/*     */    }
/*     */ 
/*     */    public ProductAreaManager getProductAreaManager() {
/* 332 */       return this.productAreaManager;
/*     */    }
/*     */ 
/*     */    public ArticleTypeAttributeManager getArticleTypeAttributeManager() {
/* 336 */       return this.articleTypeAttributeManager;
/*     */    }
/*     */ 
/*     */    public PostShipmentManager getPostShipmentManager() {
/* 340 */       return this.postShipmentManager;
/*     */    }
/*     */ 
/*     */    public PacklistVManager getPacklistVManager() {
/* 344 */       return this.packlistVManager;
/*     */    }
/*     */ 
/*     */    public TransportCostManager getTransportCostManager() {
/* 348 */       return this.transportCostManager;
/*     */    }
/*     */ 
/*     */    public FaktureringVManager getFaktureringVManager() {
/* 352 */       return this.faktureringVManager;
/*     */    }
/*     */ 
/*     */    public MainPackageVManager getMainPackageVManager() {
/* 356 */       return this.mainPackageVManager;
/*     */    }
/*     */ 
/*     */    public BudgetManager getBudgetManager() {
/* 360 */       return this.budgetManager;
/*     */    }
/*     */ 
/*     */    public SumOrderReadyVManager getSumOrderReadyVManager() {
/* 364 */       return this.sumOrderReadyVManager;
/*     */    }
/*     */ 
/*     */    public DeviationStatusManager getDeviationStatusManager() {
/* 368 */       return this.deviationStatusManager;
/*     */    }
/*     */ 
/*     */    public UdsalesmallManager getUdsalesmallManager() {
/* 372 */       return this.udsalesmallManager;
/*     */    }
/*     */ 
/*     */    public TransportSumVManager getTransportSumVManager() {
/* 376 */       return this.transportSumVManager;
/*     */    }
/*     */ 
/*     */    public IntelleVManager getIntelleVManager() {
/* 380 */       return this.ipkOrdManager;
/*     */    }
/*     */ 
/*     */    public ProductAreaGroupManager getProductAreaGroupManager() {
/* 384 */       return this.productAreaGroupManager;
/*     */    }
/*     */ 
/*     */    public CustTrManager getCustTrManager() {
/* 388 */       return this.custTrManager;
/*     */    }
/*     */ 
/*     */    public AssemblyOverdueVManager getAssemblyOverdueVManager() {
/* 392 */       return this.assemblyOverdueVManager;
/*     */    }
/*     */ 
/*     */    public FrontProductionVManager getFrontProductionVManager() {
/* 396 */       return this.frontProductionVManager;
/*     */    }
/*     */ 
/*     */    public FakturagrunnlagVManager getFakturagrunnlagVManager() {
/* 400 */       return this.fakturagrunnlagVManager;
/*     */    }
/*     */ 
/*     */    public DeviationVManager getDeviationVManager() {
/* 404 */       return this.deviationVManager;
/*     */    }
/*     */ 
/*     */    public CostTypeManager getCostTypeManager() {
/* 408 */       return this.costTypeManager;
/*     */    }
/*     */ 
/*     */    public CostUnitManager getCostUnitManager() {
/* 412 */       return this.costUnitManager;
/*     */    }
/*     */ }
