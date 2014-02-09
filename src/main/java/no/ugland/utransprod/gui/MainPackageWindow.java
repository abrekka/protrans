package no.ugland.utransprod.gui;

import java.awt.Component;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import no.ugland.utransprod.gui.checker.LagerProductionStatusChecker;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.handlers.MainPackageViewHandler;
import no.ugland.utransprod.gui.handlers.MainPackageViewHandlerFactory;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.service.ApplicationParamManager;
import no.ugland.utransprod.service.GulvsponPackageVManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import com.google.common.collect.Multimap;
import com.google.inject.Inject;

/**
 * Klasse som viser pakkevindu som frittstående vindu
 * 
 * @author atle.brekka
 */
public class MainPackageWindow implements MainWindow, CloseListener {
    private Login login;
    /*
     * ApplicationUser applicationUser;
     * 
     * UserType applicationUserType;
     */
    // private OrderViewHandler orderViewHandler;
    private MainPackageViewHandlerFactory mainPackageViewHandlerFactory;

    @Inject
    public MainPackageWindow(MainPackageViewHandlerFactory aMainPackageViewHandlerFactory) {
	// orderViewHandler=orderViewHandlerFactory.create(true);
	mainPackageViewHandlerFactory = aMainPackageViewHandlerFactory;
    }

    /**
     * @see no.ugland.utransprod.gui.MainWindow#buildMainWindow(no.ugland.utransprod.gui.SystemReadyListener)
     */
    public final Component buildMainWindow(final SystemReadyListener listener, ManagerRepository managerRepository) {
	JFrame jFrame = null;

	ApplicationParamManager applicationParamManager = (ApplicationParamManager) ModelUtil.getBean("applicationParamManager");

	GulvsponPackageVManager gulvsponPackageVManager = (GulvsponPackageVManager) ModelUtil.getBean("gulvsponPackageVManager");
	Multimap<String, String> colliSetup = applicationParamManager.getColliSetup();

	String veggArticleName = applicationParamManager.findByName("vegg_artikkel");
	String frontArticleName = applicationParamManager.findByName("front_artikkel");

	String attributeName = applicationParamManager.findByName("front_attributt_navn");
	String attributeValue = applicationParamManager.findByName("front_attributt_verdi");

	Map<String, StatusCheckerInterface<OrderLine>> statusChekers = new Hashtable<String, StatusCheckerInterface<OrderLine>>();

	statusChekers.put(veggArticleName, new LagerProductionStatusChecker(veggArticleName, attributeName, attributeValue));
	statusChekers.put(frontArticleName, new LagerProductionStatusChecker(frontArticleName, attributeName, attributeValue));

	/*
	 * OrderManager orderManager = (OrderManager)
	 * ModelUtil.getBean(OrderManager.MANAGER_NAME); ColliManager
	 * colliManager = (ColliManager)
	 * ModelUtil.getBean(ColliManager.MANAGER_NAME); OrderLineManager
	 * orderLineManager = (OrderLineManager)
	 * ModelUtil.getBean(OrderLineManager.MANAGER_NAME); ArticleTypeManager
	 * articleTypeManager = (ArticleTypeManager)
	 * ModelUtil.getBean(ArticleTypeManager.MANAGER_NAME); AttributeManager
	 * attributeManager = (AttributeManager)
	 * ModelUtil.getBean(AttributeManager.MANAGER_NAME); OrdchgrHeadVManager
	 * ordchgrHeadVManager = (OrdchgrHeadVManager)
	 * ModelUtil.getBean(OrdchgrHeadVManager.MANAGER_NAME); DeviationManager
	 * deviationManager = (DeviationManager)
	 * ModelUtil.getBean(DeviationManager.MANAGER_NAME); VismaFileCreator
	 * vismaFileCreator=new VismaFileCreatorImpl(ordchgrHeadVManager, true);
	 */

	// OrderViewHandler orderViewHandler = new
	// OrderViewHandler(true,login,orderManager);
	// applicationUserType, applicationUser,orderManager);

	MainPackageViewHandler mainPackageViewHandler = mainPackageViewHandlerFactory.create(colliSetup, statusChekers);
	mainPackageViewHandler.addCloseListener(this);
	MainPackageView mainPackageView = new MainPackageView(mainPackageViewHandler);
	jFrame = new JFrame("Pakking garasjepakke");
	jFrame.setIconImage(IconEnum.ICON_UGLAND_BIG.getIcon().getImage());
	jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	WindowInterface frame = new JFrameAdapter(jFrame);
	jFrame.setJMenuBar(mainPackageViewHandler.getMenuBar(login.getApplicationUser(), gulvsponPackageVManager, frame));

	frame.add(mainPackageView.buildPanel(frame));
	jFrame.setSize(mainPackageViewHandler.getWindowSize());
	Util.locateOnScreenCenter(frame);
	frame.setVisible(true);
	listener.systemReady();
	return jFrame;
    }

    /**
     * @see no.ugland.utransprod.gui.MainWindow#setApplicationUser(no.ugland.utransprod.model.ApplicationUser)
     */
    public final void setLogin(final Login aLogin) {
	// applicationUser = currentUser;
	login = aLogin;

    }

    /**
     * @see no.ugland.utransprod.gui.CloseListener#windowClosed()
     */
    public final void windowClosed() {
	System.exit(0);

    }

    /**
     * @see no.ugland.utransprod.gui.MainWindow#setUserType(no.ugland.utransprod.model.UserType)
     */
    /*
     * public final void setUserType(final UserType currentUserType) {
     * this.applicationUserType = currentUserType;
     * 
     * }
     */

}
