package no.ugland.utransprod.gui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.handlers.OrderCostsViewHandler;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Klasse som håndterer visning av kostnader for en gitt ordre
 * 
 * @author atle.brekka
 * 
 */
public class OrderCostsView {
	/**
	 * 
	 */
	private JXTable tableCost;

	/**
	 * 
	 */
	private JButton buttonAddCost;

	/**
	 * 
	 */
	private JButton buttonRemoveCost;

	/**
	 * 
	 */
	private JButton buttonEditCost;

	/**
	 * 
	 */
	private OrderCostsViewHandler viewHandler;

	/**
	 * 
	 */
	private boolean buttonsRight;

	/**
	 * @param handler
	 * @param buttonsOnRight
	 */
	public OrderCostsView(OrderCostsViewHandler handler, boolean buttonsOnRight) {
		viewHandler = handler;
		buttonsRight = buttonsOnRight;
	}

	/**
	 * Initierer komponenter
	 * 
	 * @param window
	 * @throws ProTransException
	 */
	private void initComponents(WindowInterface window)
			throws ProTransException {
		tableCost = viewHandler.getTableCost(window);

		buttonAddCost = viewHandler.getAddCostButton(window);
		buttonRemoveCost = viewHandler.getRemoveCostButton(window);
		buttonEditCost = viewHandler.getEditCostButton(window);

	}

	/**
	 * Setter kolonnebredder for tabell
	 */
	private void initColumnWidth() {
		// Kostnader
		// Kostnad
		TableColumn col = tableCost.getColumnModel().getColumn(0);
		col.setPreferredWidth(95);

		// Kostnadsenhet
		col = tableCost.getColumnModel().getColumn(1);
		col.setPreferredWidth(90);

		// Beløp
		col = tableCost.getColumnModel().getColumn(2);
		col.setPreferredWidth(50);

		// Mva
		col = tableCost.getColumnModel().getColumn(3);
		col.setPreferredWidth(50);

		// Av.
		col = tableCost.getColumnModel().getColumn(4);
		col.setPreferredWidth(30);
	}

	/**
	 * Bygger panel
	 * 
	 * @param window
	 * @return panel
	 * @throws ProTransException
	 */
	public Component buildPanel(WindowInterface window)
			throws ProTransException {
		initComponents(window);
		initColumnWidth();
		viewHandler.initEventHandling(window);

		FormLayout layout;
		if (buttonsRight) {
			layout = new FormLayout("180dlu,3dlu,p", "80dlu:grow");
		} else {
			layout = new FormLayout("190dlu", "fill:80dlu:grow,3dlu,p");
		}
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(new JScrollPane(tableCost), cc.xy(1, 1));
		if (buttonsRight) {
			builder.add(buildCostButtons(), cc.xy(3, 1));
		} else {
			builder.add(ButtonBarFactory.buildCenteredBar(buttonAddCost,
					buttonEditCost, buttonRemoveCost), cc.xy(1, 3));
		}
		return builder.getPanel();
	}

	/**
	 * Bygger panel med knapper for kostnader
	 * 
	 * @return panel
	 */
	private JPanel buildCostButtons() {
		ButtonStackBuilder buttonBuilder = new ButtonStackBuilder();
		buttonBuilder.addGridded(buttonAddCost);
		buttonBuilder.addRelatedGap();
		buttonBuilder.addGridded(buttonEditCost);
		buttonBuilder.addRelatedGap();
		buttonBuilder.addGridded(buttonRemoveCost);

		return buttonBuilder.getPanel();
	}

}
