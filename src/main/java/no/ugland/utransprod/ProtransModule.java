package no.ugland.utransprod;

import no.ugland.utransprod.gui.ArticlePackageViewFactory;
import no.ugland.utransprod.gui.ArticleProductionPackageView;
import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.DeviationOverviewViewFactoryImpl;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.LoginImpl;
import no.ugland.utransprod.gui.MenuBarBuilderImpl;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.PaidView;
import no.ugland.utransprod.gui.PaidViewFactory;
import no.ugland.utransprod.gui.action.SetProductionUnitAction;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandler;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.ArticleTypeViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.ArticleTypeViewHandlerFactoryImpl;
import no.ugland.utransprod.gui.handlers.ConstructionTypeViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.ConstructionTypeViewHandlerFactoryImpl;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactoryImpl;
import no.ugland.utransprod.gui.handlers.ExternalOrderViewHandler;
import no.ugland.utransprod.gui.handlers.ExternalOrderViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.MainPackageViewHandler;
import no.ugland.utransprod.gui.handlers.MainPackageViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.ShowTakstolInfoAction;
import no.ugland.utransprod.gui.handlers.ShowTakstolInfoActionFactory;
import no.ugland.utransprod.gui.handlers.SupplierOrderViewHandler;
import no.ugland.utransprod.gui.handlers.SupplierOrderViewHandlerFactory;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.service.AccidentManager;
import no.ugland.utransprod.service.ApplicationParamManager;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.ArticleTypeAttributeManager;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.AssemblyManager;
import no.ugland.utransprod.service.AssemblyOverdueVManager;
import no.ugland.utransprod.service.AttributeChoiceManager;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.CostTypeManager;
import no.ugland.utransprod.service.CostUnitManager;
import no.ugland.utransprod.service.CraningCostManager;
import no.ugland.utransprod.service.CustTrManager;
import no.ugland.utransprod.service.CustomerManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.EmployeeManager;
import no.ugland.utransprod.service.EmployeeTypeManager;
import no.ugland.utransprod.service.ExternalOrderLineManager;
import no.ugland.utransprod.service.ExternalOrderManager;
import no.ugland.utransprod.service.FaktureringVManager;
import no.ugland.utransprod.service.FrontProductionVManager;
import no.ugland.utransprod.service.FunctionCategoryManager;
import no.ugland.utransprod.service.GulvsponPackageVManager;
import no.ugland.utransprod.service.IncomingOrderManager;
import no.ugland.utransprod.service.InfoManager;
import no.ugland.utransprod.service.IntelleVManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.MainPackageVManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.ManagerRepositoryImpl;
import no.ugland.utransprod.service.OrdchgrHeadVManager;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.OrdlnManager;
import no.ugland.utransprod.service.PacklistVManager;
import no.ugland.utransprod.service.PaidVManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.PreventiveActionManager;
import no.ugland.utransprod.service.ProcentDoneManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.ProductionUnitManager;
import no.ugland.utransprod.service.SumOrderReadyVManager;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.service.SupplierTypeManager;
import no.ugland.utransprod.service.TaksteinSkarpnesVManager;
import no.ugland.utransprod.service.TakstolInfoVManager;
import no.ugland.utransprod.service.TakstolPackageVManager;
import no.ugland.utransprod.service.TakstolProductionVManager;
import no.ugland.utransprod.service.TransportCostBasisManager;
import no.ugland.utransprod.service.TransportCostManager;
import no.ugland.utransprod.service.TransportManager;
import no.ugland.utransprod.service.TransportSumVManager;
import no.ugland.utransprod.service.UdsalesmallManager;
import no.ugland.utransprod.service.UserTypeManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.report.AssemblyReportFactory;
import no.ugland.utransprod.util.report.AssemblyReportImpl;

import com.google.common.collect.Multimap;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryProvider;
import com.google.inject.name.Names;

public class ProtransModule extends AbstractModule {

    @Override
    protected void configure() {
	bind(AssemblyReportFactory.class).toProvider(FactoryProvider.newFactory(AssemblyReportFactory.class, AssemblyReportImpl.class));
	bind(CraningCostManager.class).toInstance((CraningCostManager) ModelUtil.getBean(CraningCostManager.MANAGER_NAME));
	bind(SupplierOrderViewHandlerFactory.class).toProvider(
		FactoryProvider.newFactory(SupplierOrderViewHandlerFactory.class, SupplierOrderViewHandler.class));

	bind(OrderViewHandlerFactory.class).toProvider(FactoryProvider.newFactory(OrderViewHandlerFactory.class, OrderViewHandler.class));
	bind(DeviationViewHandlerFactory.class).to(DeviationViewHandlerFactoryImpl.class);
	bind(ConstructionTypeViewHandlerFactory.class).to(ConstructionTypeViewHandlerFactoryImpl.class);
	bind(DeviationOverviewViewFactory.class).to(DeviationOverviewViewFactoryImpl.class);
	bind(MainPackageViewHandlerFactory.class).toProvider(
		FactoryProvider.newFactory(MainPackageViewHandlerFactory.class, MainPackageViewHandler.class));
	bind(PaidViewFactory.class).toProvider(FactoryProvider.newFactory(PaidViewFactory.class, PaidView.class));
	bind(ArticleTypeViewHandlerFactory.class).to(ArticleTypeViewHandlerFactoryImpl.class);
	bind(ExternalOrderViewHandlerFactory.class).toProvider(
		FactoryProvider.newFactory(ExternalOrderViewHandlerFactory.class, ExternalOrderViewHandler.class));

	bind(ManagerRepository.class).to(ManagerRepositoryImpl.class).in(Singleton.class);

	bind(MenuBarBuilderInterface.class).to(MenuBarBuilderImpl.class).in(Singleton.class);
	bind(TakstolPackageVManager.class).toInstance((TakstolPackageVManager) ModelUtil.getBean(TakstolPackageVManager.MANAGER_NAME));
	bind(VismaFileCreator.class).to(VismaFileCreatorImpl.class);
	bind(Boolean.class).annotatedWith(Names.named("useUniqueFileName")).toInstance(Boolean.TRUE);
	bind(OrdchgrHeadVManager.class).toInstance((OrdchgrHeadVManager) ModelUtil.getBean(OrdchgrHeadVManager.MANAGER_NAME));
	bind(String.class).annotatedWith(Names.named("takstol_article")).toInstance(ApplicationParamUtil.findParamByName("takstol_artikkel"));
	ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil.getBean(ArticleTypeManager.MANAGER_NAME);
	bind(ArticleType.class).annotatedWith(Names.named("takstolArticle")).toInstance(
		articleTypeManager.findByName(ApplicationParamUtil.findParamByName("takstol_artikkel")));
	bind(ArticleTypeManager.class).toInstance((ArticleTypeManager) ModelUtil.getBean(ArticleTypeManager.MANAGER_NAME));
	bind(TakstolProductionVManager.class).toInstance((TakstolProductionVManager) ModelUtil.getBean(TakstolProductionVManager.MANAGER_NAME));
	bind(String.class).annotatedWith(Names.named("takstolColliName")).toInstance("Takstol");
	bind(Login.class).to(LoginImpl.class);
	bind(ColliManager.class).toInstance((ColliManager) ModelUtil.getBean(ColliManager.MANAGER_NAME));
	bind(OrderLineManager.class).toInstance((OrderLineManager) ModelUtil.getBean(OrderLineManager.MANAGER_NAME));
	bind(AttributeChoiceManager.class).toInstance((AttributeChoiceManager) ModelUtil.getBean(AttributeChoiceManager.MANAGER_NAME));
	bind(AttributeManager.class).toInstance((AttributeManager) ModelUtil.getBean(AttributeManager.MANAGER_NAME));
	bind(ConstructionTypeManager.class).toInstance((ConstructionTypeManager) ModelUtil.getBean(ConstructionTypeManager.MANAGER_NAME));
	bind(CustomerManager.class).toInstance((CustomerManager) ModelUtil.getBean(CustomerManager.MANAGER_NAME));
	bind(DeviationManager.class).toInstance((DeviationManager) ModelUtil.getBean(DeviationManager.MANAGER_NAME));
	bind(ExternalOrderLineManager.class).toInstance((ExternalOrderLineManager) ModelUtil.getBean(ExternalOrderLineManager.MANAGER_NAME));
	bind(String.class).annotatedWith(Names.named("gulvspon_article")).toInstance(ApplicationParamUtil.findParamByName("gulvspon_artikkel"));
	bind(AccidentManager.class).toInstance((AccidentManager) ModelUtil.getBean(AccidentManager.MANAGER_NAME));
	bind(ApplicationParamManager.class).toInstance((ApplicationParamManager) ModelUtil.getBean(ApplicationParamManager.MANAGER_NAME));
	bind(UserTypeManager.class).toInstance((UserTypeManager) ModelUtil.getBean(UserTypeManager.MANAGER_NAME));
	bind(TransportManager.class).toInstance((TransportManager) ModelUtil.getBean(TransportManager.MANAGER_NAME));
	bind(TransportCostBasisManager.class).toInstance((TransportCostBasisManager) ModelUtil.getBean(TransportCostBasisManager.MANAGER_NAME));
	bind(TaksteinSkarpnesVManager.class).toInstance((TaksteinSkarpnesVManager) ModelUtil.getBean(TaksteinSkarpnesVManager.MANAGER_NAME));
	bind(SupplierManager.class).toInstance((SupplierManager) ModelUtil.getBean(SupplierManager.MANAGER_NAME));
	bind(ProductionUnitManager.class).toInstance((ProductionUnitManager) ModelUtil.getBean(ProductionUnitManager.MANAGER_NAME));
	bind(BudgetManager.class).toInstance((BudgetManager) ModelUtil.getBean(BudgetManager.MANAGER_NAME));
	bind(ProcentDoneManager.class).toInstance((ProcentDoneManager) ModelUtil.getBean(ProcentDoneManager.MANAGER_NAME));
	bind(PreventiveActionManager.class).toInstance((PreventiveActionManager) ModelUtil.getBean(PreventiveActionManager.MANAGER_NAME));
	bind(SupplierTypeManager.class).toInstance((SupplierTypeManager) ModelUtil.getBean(SupplierTypeManager.MANAGER_NAME));
	bind(PaidVManager.class).toInstance((PaidVManager) ModelUtil.getBean(PaidVManager.MANAGER_NAME));
	bind(OrderManager.class).toInstance((OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME));
	bind(JobFunctionManager.class).toInstance((JobFunctionManager) ModelUtil.getBean(JobFunctionManager.MANAGER_NAME));
	bind(InfoManager.class).toInstance((InfoManager) ModelUtil.getBean(InfoManager.MANAGER_NAME));
	bind(IncomingOrderManager.class).toInstance((IncomingOrderManager) ModelUtil.getBean(IncomingOrderManager.MANAGER_NAME));
	bind(FunctionCategoryManager.class).toInstance((FunctionCategoryManager) ModelUtil.getBean(FunctionCategoryManager.MANAGER_NAME));
	bind(ExternalOrderManager.class).toInstance((ExternalOrderManager) ModelUtil.getBean(ExternalOrderManager.MANAGER_NAME));
	bind(EmployeeTypeManager.class).toInstance((EmployeeTypeManager) ModelUtil.getBean(EmployeeTypeManager.MANAGER_NAME));
	bind(EmployeeManager.class).toInstance((EmployeeManager) ModelUtil.getBean(EmployeeManager.MANAGER_NAME));
	bind(DeviationStatusManager.class).toInstance((DeviationStatusManager) ModelUtil.getBean(DeviationStatusManager.MANAGER_NAME));
	bind(CostUnitManager.class).toInstance((CostUnitManager) ModelUtil.getBean(CostUnitManager.MANAGER_NAME));
	bind(CostTypeManager.class).toInstance((CostTypeManager) ModelUtil.getBean(CostTypeManager.MANAGER_NAME));
	bind(ApplicationUserManager.class).toInstance((ApplicationUserManager) ModelUtil.getBean(ApplicationUserManager.MANAGER_NAME));
	bind(AssemblyManager.class).toInstance((AssemblyManager) ModelUtil.getBean(AssemblyManager.MANAGER_NAME));
	bind(FaktureringVManager.class).toInstance((FaktureringVManager) ModelUtil.getBean(FaktureringVManager.MANAGER_NAME));
	bind(GulvsponPackageVManager.class).toInstance((GulvsponPackageVManager) ModelUtil.getBean(GulvsponPackageVManager.MANAGER_NAME));
	bind(PacklistVManager.class).toInstance((PacklistVManager) ModelUtil.getBean(PacklistVManager.MANAGER_NAME));
	bind(TakstolInfoVManager.class).toInstance((TakstolInfoVManager) ModelUtil.getBean(TakstolInfoVManager.MANAGER_NAME));
	bind(ShowTakstolInfoActionFactory.class).toProvider(
		FactoryProvider.newFactory(ShowTakstolInfoActionFactory.class, ShowTakstolInfoAction.class));
	bind(OrdlnManager.class).toInstance((OrdlnManager) ModelUtil.getBean(OrdlnManager.MANAGER_NAME));

	bind(SetProductionUnitActionFactory.class).toProvider(
		FactoryProvider.newFactory(SetProductionUnitActionFactory.class, SetProductionUnitAction.class));

	bind(ArticlePackageViewFactory.class).toProvider(
		FactoryProvider.newFactory(ArticlePackageViewFactory.class, ArticleProductionPackageView.class));

	bind(ArticlePackageViewHandlerFactory.class).toProvider(
		FactoryProvider.newFactory(ArticlePackageViewHandlerFactory.class, ArticlePackageViewHandler.class));

	bind(ProductAreaManager.class).toInstance((ProductAreaManager) ModelUtil.getBean(ProductAreaManager.MANAGER_NAME));

	bind(ArticleType.class).annotatedWith(Names.named("taksteinArticle")).toInstance(
		articleTypeManager.findByName(ApplicationParamUtil.findParamByName("stein_artikkel")));

	bind(ArticleTypeAttributeManager.class).toInstance((ArticleTypeAttributeManager) ModelUtil.getBean(ArticleTypeAttributeManager.MANAGER_NAME));
	bind(PostShipmentManager.class).toInstance((PostShipmentManager) ModelUtil.getBean(PostShipmentManager.MANAGER_NAME));

	CostTypeManager costTypeManager = (CostTypeManager) ModelUtil.getBean(CostTypeManager.MANAGER_NAME);

	bind(CostType.class).annotatedWith(Names.named("kostnadTypeTakstoler")).toInstance(costTypeManager.findByName("Takstoler"));

	CostUnitManager costUnitManager = (CostUnitManager) ModelUtil.getBean(CostUnitManager.MANAGER_NAME);

	bind(CostUnit.class).annotatedWith(Names.named("kostnadEnhetTakstoler")).toInstance(costUnitManager.findByName("Intern"));

	bind(TransportCostManager.class).toInstance((TransportCostManager) ModelUtil.getBean(TransportCostManager.MANAGER_NAME));

	bind(MainPackageVManager.class).toInstance((MainPackageVManager) ModelUtil.getBean(MainPackageVManager.MANAGER_NAME));

	bind(SumOrderReadyVManager.class).toInstance((SumOrderReadyVManager) ModelUtil.getBean(SumOrderReadyVManager.MANAGER_NAME));

	bind(Multimap.class).annotatedWith(Names.named("colli_setup")).toInstance(ApplicationParamUtil.getColliSetup());

	bind(UdsalesmallManager.class).toInstance((UdsalesmallManager) ModelUtil.getBean(UdsalesmallManager.MANAGER_NAME));
	bind(TransportSumVManager.class).toInstance((TransportSumVManager) ModelUtil.getBean(TransportSumVManager.MANAGER_NAME));
	bind(IntelleVManager.class).toInstance((IntelleVManager) ModelUtil.getBean(IntelleVManager.MANAGER_NAME));
	bind(CustTrManager.class).toInstance((CustTrManager) ModelUtil.getBean(CustTrManager.MANAGER_NAME));
	bind(ProductAreaGroupManager.class).toInstance((ProductAreaGroupManager) ModelUtil.getBean(ProductAreaGroupManager.MANAGER_NAME));
	bind(AssemblyOverdueVManager.class).toInstance((AssemblyOverdueVManager) ModelUtil.getBean(AssemblyOverdueVManager.MANAGER_NAME));
	bind(FrontProductionVManager.class).toInstance((FrontProductionVManager) ModelUtil.getBean(FrontProductionVManager.MANAGER_NAME));

    }

}
