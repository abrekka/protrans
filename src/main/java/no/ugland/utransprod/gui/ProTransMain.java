package no.ugland.utransprod.gui;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import no.ugland.utransprod.gui.model.WindowEnum;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Klasse som håndterer visning av hovedvindu
 * 
 * @author abr99
 */
public class ProTransMain extends JFrame implements MainWindow {
    transient FrameListener frameListener;

    private static final long serialVersionUID = 1L;

    PanelMain panelMain;

    /**
     * Peker til hovedvinduet
     */
    public static ProTransMain PRO_TRANS_MAIN;

    private Login login;

    List<String> windowMenuTexts = new ArrayList<String>();

    Map<String, Viewer> viewers = new Hashtable<String, Viewer>();

    List<WindowInterface> windows = new ArrayList<WindowInterface>();

    List<JMenuItem> windowMenus = new ArrayList<JMenuItem>();

    JMenu currentMenuWindow;

    private final WindowMenuListener windowMenuListener;
    private MenuBarBuilderInterface menuBarBuilder;

    /**
     * @param applicationUser
     * @param userType
     */
    @Inject
    public ProTransMain(MenuBarBuilderInterface aMenuBarBuilderInterface, Login aLogin) {
	menuBarBuilder = aMenuBarBuilderInterface;
	login = aLogin;
	ProTransMain.PRO_TRANS_MAIN = this;
	frameListener = new FrameListener();
	windowMenuListener = new WindowMenuListener();
    }

    /**
     * Bygger hovedvindu
     * 
     * @return vindu
     */
    public Component buildFrame() {
	setTitle("ProTrans - Grimstad Industrier");
	setIconImage(IconEnum.ICON_UGLAND2.getIcon().getImage());

	setJMenuBar(menuBarBuilder.buildMenuBar(this, login.getApplicationUser(), login.getUserType()));
	currentMenuWindow = menuBarBuilder.getMenuWindow();
	setExtendedState(Frame.MAXIMIZED_BOTH);
	this.setResizable(true);
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	try {
	    panelMain = new PanelMain();

	    getContentPane().add(panelMain);
	    JFrame.setDefaultLookAndFeelDecorated(true);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	setMaximizedBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
	setVisible(true);
	return this;
    }

    /**
     * Legger til internt vindu
     * 
     * @param iFrame
     */
    public void addInternalFrame(WindowInterface iFrame) {
	panelMain.addFrame(iFrame);

	iFrame.addInternalFrameListener(frameListener);
    }

    /**
     * Legger vindu til i liste over aktive vinduer
     * 
     * @param iFrame
     */
    private void addFrameToMenu(WindowInterface iFrame) {
	JMenuItem menuItemEstateFrame = new JMenuItem(iFrame.getTitle());
	windowMenuTexts.add(iFrame.getTitle());

	windows.add(iFrame);
	windowMenus.add(menuItemEstateFrame);
	menuItemEstateFrame.addActionListener(windowMenuListener);

	currentMenuWindow.add(menuItemEstateFrame);

    }

    /**
     * Håndterer menyvalg hvor vindu allerede er aktivt
     * 
     * @param actionCommand
     */
    void handleDefaultMenuAction(String actionCommand) {
	int index = windowMenuTexts.indexOf(actionCommand);

	if (index != -1) {
	    try {
		windows.get(index).setSelected(true);
	    } catch (PropertyVetoException e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * Åpner vindu
     * 
     * @param viewer
     */
    public void openFrame(Viewer viewer) {
	Util.setWaitCursor(getRootPane());

	String title = viewer.getTitle();

	viewers.put(title, viewer);

	int index = windowMenuTexts.indexOf(title);

	if (index != -1) {
	    handleDefaultMenuAction(title);
	} else {
	    WindowInterface window = viewer.buildWindow();
	    addInternalFrame(window);
	    addFrameToMenu(window);
	    window.pack();

	    Util.locateOnScreenCenter(window);
	    try {
		window.setSelected(true);
	    } catch (java.beans.PropertyVetoException ex) {
		ex.printStackTrace();
	    }

	}
	Util.setDefaultCursor(getRootPane());
    }

    /**
     * Åpner og legger til vindu
     * 
     * @param window
     * @param windowEnum
     */
    public void openFrame(WindowInterface window, WindowEnum windowEnum) {
	Util.setWaitCursor(getRootPane());

	String title = windowEnum.getTitle();

	int index = windowMenuTexts.indexOf(title);

	if (index != -1) {
	    handleDefaultMenuAction(title);
	} else {
	    window.setVisible(true);

	    if (!window.isAdded()) {
		addInternalFrame(window);
		window.setAdded(true);
		window.pack();
	    }
	    addFrameToMenu(window);

	}
	Util.locateOnScreenCenter(window);
	try {
	    window.setSelected(true);
	} catch (java.beans.PropertyVetoException ex) {
	    ex.printStackTrace();

	}
	Util.setDefaultCursor(getRootPane());
    }

    /**
     * @see no.ugland.utransprod.gui.MainWindow#buildMainWindow(no.ugland.utransprod.gui.SystemReadyListener)
     */
    public Component buildMainWindow(SystemReadyListener listener, ManagerRepository managerRepository) {
	Component comp = buildFrame();
	listener.systemReady();
	return comp;
    }

    /**
     * @see no.ugland.utransprod.gui.MainWindow#setApplicationUser(no.ugland.utransprod.model.ApplicationUser)
     */
    public void setLogin(Login aLogin) {
	this.login = aLogin;

    }

    /**
     * Henter alle vinduer
     * 
     * @return allevinduer
     */
    public JInternalFrame[] getAllWindows() {
	return panelMain.getAllWindows();
    }

    /**
     * Håndterer lukking av vinduer
     * 
     * @author atle.brekka
     */
    class FrameListener implements InternalFrameListener {

	/**
	 * @see javax.swing.event.InternalFrameListener#internalFrameActivated(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameActivated(InternalFrameEvent arg0) {
	}

	/**
	 * @see javax.swing.event.InternalFrameListener#internalFrameClosed(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameClosed(InternalFrameEvent internalEvent) {
	    Object source = internalEvent.getSource();

	    if (source instanceof JInternalFrame) {
		JInternalFrame iFrame = (JInternalFrame) source;
		String title = iFrame.getTitle();
		Viewer viewer = viewers.get(title);
		if (viewer != null && viewer.useDispose()) {
		    iFrame.removeInternalFrameListener(frameListener);
		    panelMain.removeFrame(iFrame);
		    iFrame.dispose();
		}
		int index = windowMenuTexts.indexOf(title);

		if (index != -1) {
		    JMenuItem menuItem = windowMenus.remove(index);
		    currentMenuWindow.remove(menuItem);
		    windowMenuTexts.remove(index);
		    windows.remove(index);
		}

		if (viewer != null) {
		    viewer.cleanUp();
		    viewers.remove(title);

		}
	    }

	}

	/**
	 * @see javax.swing.event.InternalFrameListener#internalFrameClosing(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameClosing(InternalFrameEvent arg0) {
	}

	/**
	 * @see javax.swing.event.InternalFrameListener#internalFrameDeactivated(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameDeactivated(InternalFrameEvent arg0) {
	}

	/**
	 * @see javax.swing.event.InternalFrameListener#internalFrameDeiconified(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameDeiconified(InternalFrameEvent arg0) {
	}

	/**
	 * @see javax.swing.event.InternalFrameListener#internalFrameIconified(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameIconified(InternalFrameEvent arg0) {
	}

	/**
	 * @see javax.swing.event.InternalFrameListener#internalFrameOpened(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameOpened(InternalFrameEvent arg0) {
	}

    }

    /**
     * @author atle.brekka
     */
    class WindowMenuListener implements ActionListener {

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent actionEvent) {
	    handleDefaultMenuAction(actionEvent.getActionCommand());

	}

    }

}
