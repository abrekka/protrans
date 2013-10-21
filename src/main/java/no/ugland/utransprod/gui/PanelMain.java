package no.ugland.utransprod.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;


/**
 * Hovedpanel for applikasjon, der alle andre andre vinduer vises
 * 
 * @author abr99
 * 
 */
public class PanelMain extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private JDesktopPane desktopPane;

	/**
	 * 
	 */
	public PanelMain() {
		init();
	}

	/**
	 * Initierer panel
	 */
	private void init() {
		BorderLayout thisLayout = new BorderLayout();
		this.setLayout(thisLayout);
		setPreferredSize(new Dimension(400, 300));
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		this.add(desktopPane, BorderLayout.CENTER);
	}

	/**
	 * Legger til vindu
	 * 
	 * @param frame
	 */
	public void addFrame(WindowInterface frame) {
		desktopPane.add(frame.getComponent());
	}
	
	public void removeFrame(Component comp){
		desktopPane.remove(comp);
	}

	/**
	 * Henter alle vinduer
	 * @return vinduer
	 */
	public JInternalFrame[] getAllWindows(){
		return desktopPane.getAllFrames();
	}
}
