/*     */ package no.ugland.utransprod;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.inject.AbstractModule;
/*     */ import com.google.inject.Singleton;
/*     */ import com.google.inject.assistedinject.FactoryProvider;
/*     */ import com.google.inject.name.Names;
/*     */ import no.ugland.utransprod.gui.ArticlePackageViewFactory;
/*     */ import no.ugland.utransprod.gui.ArticleProductionPackageView;
/*     */ import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
/*     */ import no.ugland.utransprod.gui.DeviationOverviewViewFactoryImpl;
/*     */ import no.ugland.utransprod.gui.Login;
/*     */ import no.ugland.utransprod.gui.LoginImpl;
/*     */ import no.ugland.utransprod.gui.MenuBarBuilderImpl;
/*     */ import no.ugland.utransprod.gui.MenuBarBuilderInterface;
/*     */ import no.ugland.utransprod.gui.PaidView;
/*     */ import no.ugland.utransprod.gui.PaidViewFactory;
/*     */ import no.ugland.utransprod.gui.action.SetProductionUnitAction;
/*     */ import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
/*     */ import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandler;
/*     */ import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandlerFactory;
/*     */ import no.ugland.utransprod.gui.handlers.ArticleTypeViewHandlerFactory;
/*     */ import no.ugland.utransprod.gui.handlers.ArticleTypeViewHandlerFactoryImpl;
/*     */ import no.ugland.utransprod.gui.handlers.ConstructionTypeViewHandlerFactory;
/*     */ import no.ugland.utransprod.gui.handlers.ConstructionTypeViewHandlerFactoryImpl;
/*     */ import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
/*     */ import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactoryImpl;
/*     */ import no.ugland.utransprod.gui.handlers.ExternalOrderViewHandler;
/*     */ import no.ugland.utransprod.gui.handlers.ExternalOrderViewHandlerFactory;
/*     */ import no.ugland.utransprod.gui.handlers.MainPackageViewHandler;
/*     */ import no.ugland.utransprod.gui.handlers.MainPackageViewHandlerFactory;
/*     */ import no.ugland.utransprod.gui.handlers.OrderViewHandler;
/*     */ import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
/*     */ import no.ugland.utransprod.gui.handlers.ShowTakstolInfoAction;
/*     */ import no.ugland.utransprod.gui.handlers.ShowTakstolInfoActionFactory;
/*     */ import no.ugland.utransprod.gui.handlers.SupplierOrderViewHandler;
/*     */ import no.ugland.utransprod.gui.handlers.SupplierOrderViewHandlerFactory;
/*     */ import no.ugland.utransprod.model.ArticleType;
/*     */ import no.ugland.utransprod.model.CostType;
/*     */ import no.ugland.utransprod.model.CostUnit;
/*     */ import no.ugland.utransprod.service.AccidentManager;
/*     */ import no.ugland.utransprod.service.ApplicationParamManager;
/*     */ import no.ugland.utransprod.service.ApplicationUserManager;
/*     */ import no.ugland.utransprod.service.ArticleTypeAttributeManager;
/*     */ import no.ugland.utransprod.service.ArticleTypeManager;
/*     */ import no.ugland.utransprod.service.AssemblyManager;
/*     */ import no.ugland.utransprod.service.AssemblyOverdueVManager;
/*     */ import no.ugland.utransprod.service.AttributeChoiceManager;
/*     */ import no.ugland.utransprod.service.AttributeManager;
/*     */ import no.ugland.utransprod.service.BudgetManager;
/*     */ import no.ugland.utransprod.service.ColliManager;
/*     */ import no.ugland.utransprod.service.ConstructionTypeManager;
/*     */ import no.ugland.utransprod.service.CostTypeManager;
/*     */ import no.ugland.utransprod.service.CostUnitManager;
/*     */ import no.ugland.utransprod.service.CraningCostManager;
/*     */ import no.ugland.utransprod.service.CustTrManager;
/*     */ import no.ugland.utransprod.service.CustomerManager;
/*     */ import no.ugland.utransprod.service.DeviationManager;
/*     */ import no.ugland.utransprod.service.DeviationStatusManager;
/*     */ import no.ugland.utransprod.service.DeviationVManager;
/*     */ import no.ugland.utransprod.service.EmployeeManager;
/*     */ import no.ugland.utransprod.service.EmployeeTypeManager;
/*     */ import no.ugland.utransprod.service.ExternalOrderLineManager;
/*     */ import no.ugland.utransprod.service.ExternalOrderManager;
/*     */ import no.ugland.utransprod.service.FakturagrunnlagVManager;
/*     */ import no.ugland.utransprod.service.FaktureringVManager;
/*     */ import no.ugland.utransprod.service.FrontProductionVManager;
/*     */ import no.ugland.utransprod.service.FunctionCategoryManager;
/*     */ import no.ugland.utransprod.service.GulvsponPackageVManager;
/*     */ import no.ugland.utransprod.service.IgarasjenPackageVManager;
/*     */ import no.ugland.utransprod.service.IncomingOrderManager;
/*     */ import no.ugland.utransprod.service.InfoManager;
/*     */ import no.ugland.utransprod.service.IntelleVManager;
/*     */ import no.ugland.utransprod.service.JobFunctionManager;
/*     */ import no.ugland.utransprod.service.MainPackageVManager;
/*     */ import no.ugland.utransprod.service.ManagerRepository;
/*     */ import no.ugland.utransprod.service.ManagerRepositoryImpl;
/*     */ import no.ugland.utransprod.service.OrdchgrHeadVManager;
/*     */ import no.ugland.utransprod.service.OrderLineManager;
/*     */ import no.ugland.utransprod.service.OrderManager;
/*     */ import no.ugland.utransprod.service.OrdlnManager;
/*     */ import no.ugland.utransprod.service.PacklistVManager;
/*     */ import no.ugland.utransprod.service.PaidVManager;
/*     */ import no.ugland.utransprod.service.PostShipmentManager;
/*     */ import no.ugland.utransprod.service.PreventiveActionManager;
/*     */ import no.ugland.utransprod.service.ProcentDoneManager;
/*     */ import no.ugland.utransprod.service.ProductAreaGroupManager;
/*     */ import no.ugland.utransprod.service.ProductAreaManager;
/*     */ import no.ugland.utransprod.service.ProductionUnitManager;
/*     */ import no.ugland.utransprod.service.SumOrderReadyVManager;
/*     */ import no.ugland.utransprod.service.SupplierManager;
/*     */ import no.ugland.utransprod.service.SupplierTypeManager;
/*     */ import no.ugland.utransprod.service.SutakPackageVManager;
/*     */ import no.ugland.utransprod.service.TaksteinSkarpnesVManager;
/*     */ import no.ugland.utransprod.service.TakstolInfoVManager;
/*     */ import no.ugland.utransprod.service.TakstolPackageVManager;
/*     */ import no.ugland.utransprod.service.TakstolProductionVManager;
/*     */ import no.ugland.utransprod.service.TransportCostBasisManager;
/*     */ import no.ugland.utransprod.service.TransportCostManager;
/*     */ import no.ugland.utransprod.service.TransportManager;
/*     */ import no.ugland.utransprod.service.TransportSumVManager;
/*     */ import no.ugland.utransprod.service.UdsalesmallManager;
/*     */ import no.ugland.utransprod.service.UserTypeManager;
/*     */ import no.ugland.utransprod.service.VismaFileCreator;
/*     */ import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;
/*     */ import no.ugland.utransprod.util.ApplicationParamUtil;
/*     */ import no.ugland.utransprod.util.ModelUtil;
/*     */ import no.ugland.utransprod.util.report.AssemblyReportFactory;
/*     */ import no.ugland.utransprod.util.report.AssemblyReportImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProtransModule extends AbstractModule {
/*     */    protected void configure() {
/* 116 */       this.bind(AssemblyReportFactory.class).toProvider(FactoryProvider.newFactory(AssemblyReportFactory.class, AssemblyReportImpl.class));
/* 117 */       this.bind(CraningCostManager.class).toInstance((CraningCostManager)ModelUtil.getBean("craningCostManager"));
/* 118 */       this.bind(SupplierOrderViewHandlerFactory.class).toProvider(FactoryProvider.newFactory(SupplierOrderViewHandlerFactory.class, SupplierOrderViewHandler.class));
/*     */ 
/*     */ 
/* 121 */       this.bind(OrderViewHandlerFactory.class).toProvider(FactoryProvider.newFactory(OrderViewHandlerFactory.class, OrderViewHandler.class));
/* 122 */       this.bind(DeviationViewHandlerFactory.class).to(DeviationViewHandlerFactoryImpl.class);
/* 123 */       this.bind(ConstructionTypeViewHandlerFactory.class).to(ConstructionTypeViewHandlerFactoryImpl.class);
/* 124 */       this.bind(DeviationOverviewViewFactory.class).to(DeviationOverviewViewFactoryImpl.class);
/* 125 */       this.bind(MainPackageViewHandlerFactory.class).toProvider(FactoryProvider.newFactory(MainPackageViewHandlerFactory.class, MainPackageViewHandler.class));
/*     */ 
/* 127 */       this.bind(PaidViewFactory.class).toProvider(FactoryProvider.newFactory(PaidViewFactory.class, PaidView.class));
/* 128 */       this.bind(ArticleTypeViewHandlerFactory.class).to(ArticleTypeViewHandlerFactoryImpl.class);
/* 129 */       this.bind(ExternalOrderViewHandlerFactory.class).toProvider(FactoryProvider.newFactory(ExternalOrderViewHandlerFactory.class, ExternalOrderViewHandler.class));
/*     */ 
/*     */ 
/* 132 */       this.bind(ManagerRepository.class).to(ManagerRepositoryImpl.class).in(Singleton.class);
/*     */ 
/* 134 */       this.bind(MenuBarBuilderInterface.class).to(MenuBarBuilderImpl.class).in(Singleton.class);
/* 135 */       this.bind(TakstolPackageVManager.class).toInstance((TakstolPackageVManager)ModelUtil.getBean("takstolPackageVManager"));
/* 136 */       this.bind(VismaFileCreator.class).to(VismaFileCreatorImpl.class);
/* 137 */       this.bind(Boolean.class).annotatedWith(Names.named("useUniqueFileName")).toInstance(Boolean.TRUE);
/* 138 */       this.bind(OrdchgrHeadVManager.class).toInstance((OrdchgrHeadVManager)ModelUtil.getBean("ordchgrHeadVManager"));
/* 139 */       this.bind(String.class).annotatedWith(Names.named("takstol_article")).toInstance(ApplicationParamUtil.findParamByName("takstol_artikkel"));
/* 140 */       ArticleTypeManager articleTypeManager = (ArticleTypeManager)ModelUtil.getBean("articleTypeManager");
/* 141 */       this.bind(ArticleType.class).annotatedWith(Names.named("takstolArticle")).toInstance(articleTypeManager.findByName(ApplicationParamUtil.findParamByName("takstol_artikkel")));
/*     */ 
/* 143 */       this.bind(ArticleTypeManager.class).toInstance((ArticleTypeManager)ModelUtil.getBean("articleTypeManager"));
/* 144 */       this.bind(TakstolProductionVManager.class).toInstance((TakstolProductionVManager)ModelUtil.getBean("takstolProductionVManager"));
/* 145 */       this.bind(String.class).annotatedWith(Names.named("takstolColliName")).toInstance("Takstol");
/* 146 */       this.bind(Login.class).to(LoginImpl.class);
/* 147 */       this.bind(ColliManager.class).toInstance((ColliManager)ModelUtil.getBean("colliManager"));
/* 148 */       this.bind(OrderLineManager.class).toInstance((OrderLineManager)ModelUtil.getBean("orderLineManager"));
/* 149 */       this.bind(AttributeChoiceManager.class).toInstance((AttributeChoiceManager)ModelUtil.getBean("attributeChoiceManager"));
/* 150 */       this.bind(AttributeManager.class).toInstance((AttributeManager)ModelUtil.getBean("attributeManager"));
/* 151 */       this.bind(ConstructionTypeManager.class).toInstance((ConstructionTypeManager)ModelUtil.getBean("constructionTypeManager"));
/* 152 */       this.bind(CustomerManager.class).toInstance((CustomerManager)ModelUtil.getBean("customerManager"));
/* 153 */       this.bind(DeviationManager.class).toInstance((DeviationManager)ModelUtil.getBean("deviationManager"));
/* 154 */       this.bind(DeviationVManager.class).toInstance((DeviationVManager)ModelUtil.getBean("deviationVManager"));
/* 155 */       this.bind(ExternalOrderLineManager.class).toInstance((ExternalOrderLineManager)ModelUtil.getBean("externalOrderLineManager"));
/* 156 */       this.bind(String.class).annotatedWith(Names.named("gulvspon_article")).toInstance(ApplicationParamUtil.findParamByName("gulvspon_artikkel"));
/* 157 */       this.bind(AccidentManager.class).toInstance((AccidentManager)ModelUtil.getBean("accidentManager"));
/* 158 */       this.bind(ApplicationParamManager.class).toInstance((ApplicationParamManager)ModelUtil.getBean("applicationParamManager"));
/* 159 */       this.bind(UserTypeManager.class).toInstance((UserTypeManager)ModelUtil.getBean("userTypeManager"));
/* 160 */       this.bind(TransportManager.class).toInstance((TransportManager)ModelUtil.getBean("transportManager"));
/* 161 */       this.bind(TransportCostBasisManager.class).toInstance((TransportCostBasisManager)ModelUtil.getBean("transportCostBasisManager"));
/* 162 */       this.bind(TaksteinSkarpnesVManager.class).toInstance((TaksteinSkarpnesVManager)ModelUtil.getBean("taksteinSkarpnesVManager"));
/* 163 */       this.bind(SupplierManager.class).toInstance((SupplierManager)ModelUtil.getBean("supplierManager"));
/* 164 */       this.bind(ProductionUnitManager.class).toInstance((ProductionUnitManager)ModelUtil.getBean("productionUnitManager"));
/* 165 */       this.bind(BudgetManager.class).toInstance((BudgetManager)ModelUtil.getBean("budgetManager"));
/* 166 */       this.bind(ProcentDoneManager.class).toInstance((ProcentDoneManager)ModelUtil.getBean("procentDoneManager"));
/* 167 */       this.bind(PreventiveActionManager.class).toInstance((PreventiveActionManager)ModelUtil.getBean("preventiveActionManager"));
/* 168 */       this.bind(SupplierTypeManager.class).toInstance((SupplierTypeManager)ModelUtil.getBean("supplierTypeManager"));
/* 169 */       this.bind(PaidVManager.class).toInstance((PaidVManager)ModelUtil.getBean("paidVManager"));
/* 170 */       this.bind(OrderManager.class).toInstance((OrderManager)ModelUtil.getBean("orderManager"));
/* 171 */       this.bind(JobFunctionManager.class).toInstance((JobFunctionManager)ModelUtil.getBean("jobFunctionManager"));
/* 172 */       this.bind(InfoManager.class).toInstance((InfoManager)ModelUtil.getBean("infoManager"));
/* 173 */       this.bind(IncomingOrderManager.class).toInstance((IncomingOrderManager)ModelUtil.getBean("incomingOrderManager"));
/* 174 */       this.bind(FunctionCategoryManager.class).toInstance((FunctionCategoryManager)ModelUtil.getBean("functionCategoryManager"));
/* 175 */       this.bind(ExternalOrderManager.class).toInstance((ExternalOrderManager)ModelUtil.getBean("externalOrderManager"));
/* 176 */       this.bind(EmployeeTypeManager.class).toInstance((EmployeeTypeManager)ModelUtil.getBean("employeeTypeManager"));
/* 177 */       this.bind(EmployeeManager.class).toInstance((EmployeeManager)ModelUtil.getBean("employeeManager"));
/* 178 */       this.bind(DeviationStatusManager.class).toInstance((DeviationStatusManager)ModelUtil.getBean("deviationStatusManager"));
/* 179 */       this.bind(CostUnitManager.class).toInstance((CostUnitManager)ModelUtil.getBean("costUnitManager"));
/* 180 */       this.bind(CostTypeManager.class).toInstance((CostTypeManager)ModelUtil.getBean("costTypeManager"));
/* 181 */       this.bind(ApplicationUserManager.class).toInstance((ApplicationUserManager)ModelUtil.getBean("applicationUserManager"));
/* 182 */       this.bind(AssemblyManager.class).toInstance((AssemblyManager)ModelUtil.getBean("assemblyManager"));
/* 183 */       this.bind(FaktureringVManager.class).toInstance((FaktureringVManager)ModelUtil.getBean("faktureringVManager"));
/* 184 */       this.bind(GulvsponPackageVManager.class).toInstance((GulvsponPackageVManager)ModelUtil.getBean("gulvsponPackageVManager"));
/* 185 */       this.bind(IgarasjenPackageVManager.class).toInstance((IgarasjenPackageVManager)ModelUtil.getBean("igarasjenPackageVManager"));
/* 186 */       this.bind(SutakPackageVManager.class).toInstance((SutakPackageVManager)ModelUtil.getBean("sutakPackageVManager"));
/* 187 */       this.bind(PacklistVManager.class).toInstance((PacklistVManager)ModelUtil.getBean("packlistVManager"));
/* 188 */       this.bind(TakstolInfoVManager.class).toInstance((TakstolInfoVManager)ModelUtil.getBean("takstolInfoVManager"));
/* 189 */       this.bind(ShowTakstolInfoActionFactory.class).toProvider(FactoryProvider.newFactory(ShowTakstolInfoActionFactory.class, ShowTakstolInfoAction.class));
/*     */ 
/* 191 */       this.bind(OrdlnManager.class).toInstance((OrdlnManager)ModelUtil.getBean("ordlnManager"));
/*     */ 
/* 193 */       this.bind(SetProductionUnitActionFactory.class).toProvider(FactoryProvider.newFactory(SetProductionUnitActionFactory.class, SetProductionUnitAction.class));
/*     */ 
/*     */ 
/* 196 */       this.bind(ArticlePackageViewFactory.class).toProvider(FactoryProvider.newFactory(ArticlePackageViewFactory.class, ArticleProductionPackageView.class));
/*     */ 
/*     */ 
/* 199 */       this.bind(ArticlePackageViewHandlerFactory.class).toProvider(FactoryProvider.newFactory(ArticlePackageViewHandlerFactory.class, ArticlePackageViewHandler.class));
/*     */ 
/*     */ 
/* 202 */       this.bind(ProductAreaManager.class).toInstance((ProductAreaManager)ModelUtil.getBean("productAreaManager"));
/*     */ 
/* 204 */       this.bind(ArticleType.class).annotatedWith(Names.named("taksteinArticle")).toInstance(articleTypeManager.findByName(ApplicationParamUtil.findParamByName("stein_artikkel")));
/*     */ 
/*     */ 
/* 207 */       this.bind(ArticleTypeAttributeManager.class).toInstance((ArticleTypeAttributeManager)ModelUtil.getBean("articleTypeAttributeManager"));
/* 208 */       this.bind(PostShipmentManager.class).toInstance((PostShipmentManager)ModelUtil.getBean("postShipmentManager"));
/*     */ 
/* 210 */       CostTypeManager costTypeManager = (CostTypeManager)ModelUtil.getBean("costTypeManager");
/*     */ 
/* 212 */       this.bind(CostType.class).annotatedWith(Names.named("kostnadTypeTakstoler")).toInstance(costTypeManager.findByName("Takstoler"));
/*     */ 
/* 214 */       CostUnitManager costUnitManager = (CostUnitManager)ModelUtil.getBean("costUnitManager");
/*     */ 
/* 216 */       this.bind(CostUnit.class).annotatedWith(Names.named("kostnadEnhetTakstoler")).toInstance(costUnitManager.findByName("Intern"));
/*     */ 
/* 218 */       this.bind(TransportCostManager.class).toInstance((TransportCostManager)ModelUtil.getBean("transportCostManager"));
/*     */ 
/* 220 */       this.bind(MainPackageVManager.class).toInstance((MainPackageVManager)ModelUtil.getBean("mainPackageVManager"));
/*     */ 
/* 222 */       this.bind(SumOrderReadyVManager.class).toInstance((SumOrderReadyVManager)ModelUtil.getBean("sumOrderReadyVManager"));
/*     */ 
/* 224 */       this.bind(Multimap.class).annotatedWith(Names.named("colli_setup")).toInstance(ApplicationParamUtil.getColliSetup());
/*     */ 
/* 226 */       this.bind(UdsalesmallManager.class).toInstance((UdsalesmallManager)ModelUtil.getBean("udsalesmallManager"));
/* 227 */       this.bind(TransportSumVManager.class).toInstance((TransportSumVManager)ModelUtil.getBean("transportSumVManager"));
/* 228 */       this.bind(IntelleVManager.class).toInstance((IntelleVManager)ModelUtil.getBean("intelleVManager"));
/* 229 */       this.bind(CustTrManager.class).toInstance((CustTrManager)ModelUtil.getBean("custTrManager"));
/* 230 */       this.bind(ProductAreaGroupManager.class).toInstance((ProductAreaGroupManager)ModelUtil.getBean("productAreaGroupManager"));
/* 231 */       this.bind(AssemblyOverdueVManager.class).toInstance((AssemblyOverdueVManager)ModelUtil.getBean("assemblyOverdueVManager"));
/* 232 */       this.bind(FrontProductionVManager.class).toInstance((FrontProductionVManager)ModelUtil.getBean("frontProductionVManager"));
/* 233 */       this.bind(FakturagrunnlagVManager.class).toInstance((FakturagrunnlagVManager)ModelUtil.getBean("fakturagrunnlagVManager"));
/*     */ 
/* 235 */    }
/*     */ }
