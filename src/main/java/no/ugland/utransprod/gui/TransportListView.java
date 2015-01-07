package no.ugland.utransprod.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.TransportWeekViewHandler;
import no.ugland.utransprod.model.ProductAreaGroup;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class TransportListView {

    private TransportWeekViewHandler transportWeekViewHandler;
    private JXTable tableTransportOrders;
    private WindowInterface window;

    public TransportListView(TransportWeekViewHandler viewHandler, WindowInterface window) {
	transportWeekViewHandler = viewHandler;
	this.window = window;
    }

    public JPanel buildPanel(ProductAreaGroup productAreaGroup) {
	initComponents(productAreaGroup);
	int tableSize = transportWeekViewHandler.getNumberOfOrders() * 35;
	tableSize = tableSize < 300 ? 300 : tableSize;
	FormLayout layout = new FormLayout("max(310dlu;p)", "fill:p:grow");
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.add(new JScrollPane(tableTransportOrders), cc.xy(1, 1));

	return builder.getPanel();
    }

    private void initComponents(ProductAreaGroup productAreaGroup) {
	tableTransportOrders = transportWeekViewHandler.getTableOrders(productAreaGroup, window);
    }
}
