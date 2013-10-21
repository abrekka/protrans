package no.ugland.utransprod.gui;

import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.ColliListViewHandler;
import no.ugland.utransprod.model.Colli;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ColliListView {
	private JButton buttonAddColli;

	private JButton buttonEditColli;

	private JButton buttonRemoveColli;
	private ColliListViewHandler viewHandler;

	public ColliListView(ColliListViewHandler handler) {
		viewHandler = handler;
	}

	public JPanel buildPanel(WindowInterface window, String colliListHeight,
			boolean onlyEdit) {
		initComponents(window);
		FormLayout layout = new FormLayout("fill:p:grow", "fill:"
				+ colliListHeight + "dlu:grow,3dlu,p");
		//PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(new JScrollPane(buildCollieListPanel(window)), cc.xy(1, 1));
		if (onlyEdit) {
			builder.add(ButtonBarFactory.buildCenteredBar(
					buttonEditColli), cc.xy(1, 3));
		} else {
			builder.add(ButtonBarFactory.buildCenteredBar(buttonAddColli,
					buttonEditColli, buttonRemoveColli), cc.xy(1, 3));
		}
		return builder.getPanel();
	}

	private JPanel buildCollieListPanel(WindowInterface window) {
		initComponents(window);
		FormLayout layout = new FormLayout("fill:90dlu,3dlu, fill:90dlu", "");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		// DefaultFormBuilder builder = new DefaultFormBuilder(new
		// FormDebugPanel(), layout);
		List<Colli> colliList = viewHandler.getColliList();
		Collections.sort(colliList);//, viewHandler.getColliComparator());
		for (Colli colli : colliList) {
			builder.append(viewHandler.getColliView(colli,window).buildPanel(window));
		}
		builder.appendRow(new RowSpec("top:1dlu"));
		builder.nextLine();

		return builder.getPanel();
	}

	private void initComponents(WindowInterface window) {
		buttonAddColli = viewHandler.getButtonAddColli(window);
		buttonEditColli = viewHandler.getButtonEditColli(window);
		buttonRemoveColli = viewHandler.getButtonRemoveColli(window);
	}
}
