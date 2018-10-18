package no.ugland.utransprod.gui;

import java.awt.Component;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import no.ugland.utransprod.gui.handlers.TransportWeekViewHandler;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.util.YearWeek;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Klasse som håndterer visning av transportruter for et gitt år og uke
 * 
 * @author atle.brekka
 */
public class TransportWeekView {
	private YearWeek routeDate;

	private List<Transport> transportList;

	private TransportWeekViewHandler viewHandler;

	private JButton buttonAddTransport;

	private JPanel panelTransport;

	private JPanel panelTransportMain;

	private JButton buttonRemoveTransport;

	private JButton buttonEditTransport;

	private WindowInterface currentWindow;

	// private ProductAreaGroup productAreaGroup;

	public TransportWeekView(final YearWeek aRouteDate, final TransportWeekViewHandler aHandler) {// ,
		// final
		// ProductAreaGroup
		// aProductAreaGroup)
		// {
		// productAreaGroup = aProductAreaGroup;
		viewHandler = aHandler;
		routeDate = aRouteDate;
	}

	/**
	 * Initierer komponenter
	 * 
	 * @param window
	 */
	private void initComponents(final WindowInterface window, boolean ikkeTaMedOpplastet, String transportfirma) {
		transportList = viewHandler.getTransportList(routeDate, ikkeTaMedOpplastet, transportfirma);
		buttonAddTransport = viewHandler.getButtonAddTransport(window);
		buttonRemoveTransport = viewHandler.getButtonRemoveTransport(window);
		buttonRemoveTransport.setEnabled(false);
		buttonEditTransport = viewHandler.getButtonEditTransport(window);
		panelTransportMain = buildTransportMain();
	}

	/**
	 * Lager panel for transportvindu
	 * 
	 * @return panel
	 */
	private JPanel buildTransportMain() {
		FormLayout layout = new FormLayout("fill:p:grow", "fill:p:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		return builder.getPanel();
	}

	/**
	 * Bygger panel.
	 * 
	 * @param window
	 * @return panel
	 */
	public final Component buildPanel(final WindowInterface window) {
		currentWindow = window;
		initComponents(window, false, null);
		initEventHandling();
		FormLayout layout = new FormLayout("fill:230dlu:grow", "fill:270dlu:grow,3dlu,p");
		// PanelBuilder builder = new PanelBuilder(layout, new
		// FormDebugPanel());
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		panelTransport = buildTransportPanel(window);
		panelTransportMain.add(panelTransport, cc.xy(1, 1));
		JScrollPane scrollPaneTransport = new JScrollPane(panelTransportMain);
		scrollPaneTransport.setName("ScrollPaneTransport");
		builder.add(scrollPaneTransport, cc.xy(1, 1));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonAddTransport, buttonEditTransport, buttonRemoveTransport),
				cc.xy(1, 3));
		return builder.getPanel();
	}

	/**
	 * Bygger panel for visning av transportruter
	 * 
	 * @param window
	 * @return panel
	 */
	private JPanel buildTransportPanel(final WindowInterface window) {
		return viewHandler.useListView() ? buildTransportPanelList(window) : buildTransportPanelTransports(window);
	}

	private JPanel buildTransportPanelTransports(final WindowInterface window) {
		FormLayout layout = new FormLayout("fill:p:grow", "");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		int number = 0;
		for (Transport transport : transportList) {
			number++;
			builder.append(viewHandler.getTransportView(transport).buildPanel(window, number, viewHandler, this));
		}
		builder.nextLine();

		return builder.getPanel();
	}

	private JPanel buildTransportPanelList(WindowInterface window) {
		return viewHandler.getTransportListView(window).buildPanel();
	}

	/**
	 * Initierer hendelsehåndtering
	 */
	private void initEventHandling() {
		viewHandler.addListDataListener(new TransportListListener());
	}

	/**
	 * Endring av uke og år.
	 * 
	 * @param newYear
	 */
	public final void changeWeek(final Integer newYear, boolean ikkeTaMedOpplastet, String transportfirma) {
		if (newYear != null) {

			routeDate.setYear(newYear);
		}
		viewHandler.clear();
		// ProductAreaGroup group = null;
		// if
		// (!productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle"))
		// {
		// group = productAreaGroup;
		// }
		transportList = viewHandler.getTransportList(routeDate, ikkeTaMedOpplastet, transportfirma);
		panelTransportMain.remove(panelTransport);
		panelTransport = buildTransportPanel(currentWindow);
		CellConstraints cc = new CellConstraints();
		panelTransportMain.add(panelTransport, cc.xy(1, 1));
		panelTransportMain.repaint();
		panelTransportMain.validate();

		panelTransportMain.getParent().repaint();
		panelTransportMain.getParent().validate();

	}

	/**
	 * Klasse som lytter på endring av data
	 * 
	 * @author atle.brekka
	 */
	final class TransportListListener implements ListDataListener {

		/**
		 * @see javax.swing.event.ListDataListener#contentsChanged(javax.swing.event.ListDataEvent)
		 */
		public void contentsChanged(final ListDataEvent arg0) {
			changeWeek(null, false, null);

		}

		/**
		 * @see javax.swing.event.ListDataListener#intervalAdded(javax.swing.event.ListDataEvent)
		 */
		public void intervalAdded(final ListDataEvent arg0) {
			changeWeek(null, false, null);

		}

		/**
		 * @see javax.swing.event.ListDataListener#intervalRemoved(javax.swing.event.ListDataEvent)
		 */
		public void intervalRemoved(final ListDataEvent arg0) {
			changeWeek(null, false, null);

		}

	}

	/**
	 * Henter ut anatll transporter
	 * 
	 * @return antall
	 */
	public final int getNumberOfTransport() {
		return transportList.size();
	}

	public List<Transport> getTransportList() {
		return transportList;
	}

	// public final void setProductAreaGroup(final ProductAreaGroup
	// aProductAreaGroup) {
	// this.productAreaGroup = aProductAreaGroup;
	// }

}
