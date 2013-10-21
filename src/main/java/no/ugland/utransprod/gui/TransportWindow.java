package no.ugland.utransprod.gui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.RouteViewHandler;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.TransportManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Standalone vindu for transport
 * @author atle.brekka
 */
public class TransportWindow implements MainWindow, CloseListener {
    private Login login;
    private RouteViewHandler routeViewHandler;
    
    @Inject
    public TransportWindow(RouteViewHandler aRouteViewHandler){
    	routeViewHandler=aRouteViewHandler;
    }

    /**
     * @see no.ugland.utransprod.gui.MainWindow#buildMainWindow(no.ugland.utransprod.gui.SystemReadyListener)
     */
    public final Component buildMainWindow(final SystemReadyListener listener,ManagerRepository managerRepository) {
    	/*OrderManager orderManager=(OrderManager)ModelUtil.getBean(OrderManager.MANAGER_NAME);
    	TransportManager transportManager=(TransportManager)ModelUtil.getBean(TransportManager.MANAGER_NAME);
    	DeviationManager deviationManager=(DeviationManager)ModelUtil.getBean(DeviationManager.MANAGER_NAME);*/
        //OrderViewHandler orderViewHandler = new OrderViewHandler(true,login,orderManager);

        
        /*RouteViewHandler routeViewHandler = new RouteViewHandler(
                orderViewHandler, login.getApplicationUser(), login.getUserType(),orderManager,deviationManager,transportManager);*/
        routeViewHandler.addCloseListener(this);
        RouteView routeView = new RouteView(routeViewHandler);

        JFrame jFrame = new JFrame(routeViewHandler.getWindowTitle());
        jFrame.setIconImage(IconEnum.ICON_UGLAND_BIG.getIcon().getImage());
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        WindowInterface frame = new JFrameAdapter(jFrame);

        frame.add(routeView.buildPanel(frame));
        frame.pack();
        Util.locateOnScreenCenter(frame);
        frame.setVisible(true);
        listener.systemReady();
        return jFrame;

    }

    /**
     * @see no.ugland.utransprod.gui.MainWindow#setApplicationUser(no.ugland.utransprod.model.ApplicationUser)
     */
    public final void setLogin(final Login aLogin) {
        login = aLogin;

    }

    /**
     * @see no.ugland.utransprod.gui.CloseListener#windowClosed()
     */
    public final void windowClosed() {
        System.exit(0);

    }

    
}
