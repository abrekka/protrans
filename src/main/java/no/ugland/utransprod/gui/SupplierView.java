package no.ugland.utransprod.gui;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.SupplierViewHandler;
import no.ugland.utransprod.gui.model.SupplierModel;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.service.ManagerRepository;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class SupplierView extends OverviewView<Supplier, SupplierModel> {
	private JCheckBox checkBoxShowInactive;

	public SupplierView(Login login, ManagerRepository aManagerRepository) {
		super(new SupplierViewHandler(login, aManagerRepository));
	}

	@Override
	public final JPanel buildPanel(final WindowInterface window) {
		initComponents(window);
		FormLayout layout = new FormLayout("15dlu,"
				+ viewHandler.getTableWidth() + ":grow,3dlu,p,15dlu",
				"10dlu,p,3dlu,10dlu,p,3dlu,p,3dlu,p,3dlu,p,"
						+ viewHandler.getTableHeight() + ":grow,p,5dlu,p,5dlu");
		// PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
		PanelBuilder builder = new PanelBuilder(layout);
		table.getColumnExt(5).setVisible(false);
		JScrollPane scrollPaneTable = new JScrollPane(table);
		scrollPaneTable.setBorder(Borders.EMPTY_BORDER);
		CellConstraints cc = new CellConstraints();
		builder.add(checkBoxShowInactive, cc.xy(4, 2));
		builder.add(labelHeading, cc.xy(2, 2));
		builder.add(scrollPaneTable, cc.xywh(2, 4, 1, 9));

		builder.add(buildButtonPanel(), cc.xywh(4, 5, 1, 10));

		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonExcel, buttonCancel),
				cc.xyw(2, 15, 3));

		return builder.getPanel();
	}

	@Override
	protected void initComponents(WindowInterface window) {
		super.initComponents(window);
		checkBoxShowInactive = ((SupplierViewHandler) viewHandler)
				.getCheckBoxShowInactive();
	}
}
