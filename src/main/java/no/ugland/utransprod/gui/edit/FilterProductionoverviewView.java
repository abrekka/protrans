package no.ugland.utransprod.gui.edit;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.handlers.ProductionOverviewViewHandler2.ProductionColumn;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.lowagie.text.Font;

public class FilterProductionoverviewView implements Closeable {
    private JLabel labelFilter;
    private JButton buttonFilter;
    private CancelButton buttonCancel;
    private ProductionoverviewFilter productionoverviewFilter;
    private JLabel labelSort;
    private PresentationModel presentationModel;
    private JComboBox comboBoxSort1;
    private JComboBox comboBoxSort2;
    private JComboBox comboBoxSort3;
    private ProductionoverviewFilterListener productionoverviewFilterListener;

    public interface ProductionoverviewFilterListener {
	void doFilter(ProductionoverviewFilter assemblyFilter);
    }

    public Component buildPanel(WindowInterface window, ProductionoverviewFilterListener productionoverviewFilterListener) {
	initComponents(window, productionoverviewFilterListener);
	FormLayout layout = new FormLayout("15dlu,50dlu:grow,3dlu,p,3dlu,p,15dlu", "10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p:grow,3dlu,p,5dlu");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(layout, new
	// FormDebugPanel());
	CellConstraints cc = new CellConstraints();

	builder.add(labelFilter, cc.xyw(2, 2, 1));

	builder.add(buildFilterPanel(), cc.xywh(2, 4, 1, 7));

	builder.add(labelSort, cc.xyw(4, 2, 3));
	builder.addLabel("1.", cc.xy(4, 4));
	builder.add(comboBoxSort1, cc.xy(6, 4));
	builder.addLabel("2.", cc.xy(4, 6));
	builder.add(comboBoxSort2, cc.xy(6, 6));
	builder.addLabel("3.", cc.xy(4, 8));
	builder.add(comboBoxSort3, cc.xy(6, 8));

	builder.add(ButtonBarFactory.buildCenteredBar(buttonFilter, buttonCancel), cc.xyw(2, 12, 5));

	return builder.getPanel();
    }

    private Component buildFilterPanel() {
	FormLayout layout = new FormLayout("p,3dlu,p:grow,", "");
	// DefaultFormBuilder builder = new DefaultFormBuilder(layout, new
	// FormDebugPanel());
	DefaultFormBuilder builder = new DefaultFormBuilder(layout);
	for (ProductionColumn column : ProductionColumn.getVisibleColumns()) {
	    Component component = column.getFilterComponent(presentationModel);
	    component.addKeyListener(new FieldKeyListener());
	    if (component != null) {
		builder.appendRow(new RowSpec("fill:p"));
		builder.append(column.getColumnName());
		builder.append(component);
		builder.appendRow(new RowSpec("center:3dlu"));
		builder.nextRow();
	    }
	}
	return builder.getPanel();
    }

    private void initComponents(WindowInterface window, ProductionoverviewFilterListener productionoverviewFilterListener) {
	this.productionoverviewFilterListener = productionoverviewFilterListener;
	labelFilter = new JLabel("Filter (% er wildcard)", SwingConstants.CENTER);
	labelFilter.setFont(labelFilter.getFont().deriveFont(labelFilter.getFont().getStyle() | Font.BOLD));
	labelSort = new JLabel("Sortering", SwingConstants.CENTER);
	labelSort.setFont(labelSort.getFont().deriveFont(labelSort.getFont().getStyle() | Font.BOLD));

	productionoverviewFilter = new ProductionoverviewFilter();
	presentationModel = new PresentationModel(productionoverviewFilter);

	buttonCancel = new CancelButton(window, this, true);
	buttonFilter = new JButton(new FilterAction(productionoverviewFilterListener));

	comboBoxSort1 = BasicComponentFactory.createComboBox(productionoverviewFilter.getSort1SelectionInList());
	comboBoxSort2 = BasicComponentFactory.createComboBox(productionoverviewFilter.getSort2SelectionInList());
	comboBoxSort3 = BasicComponentFactory.createComboBox(productionoverviewFilter.getSort3SelectionInList());

    }

    public boolean canClose(String actionString, WindowInterface window) {
	return true;
    }

    public class FilterAction extends AbstractAction {
	private ProductionoverviewFilterListener productionoverviewFilterListener;

	public FilterAction(ProductionoverviewFilterListener listener) {
	    super("Filtrer");
	    this.productionoverviewFilterListener = listener;
	}

	public void actionPerformed(ActionEvent arg0) {
	    productionoverviewFilterListener.doFilter(productionoverviewFilter);
	}

    }

    public class FieldKeyListener implements KeyListener {

	public void keyPressed(KeyEvent arg0) {
	    // TODO Auto-generated method stub

	}

	public void keyReleased(KeyEvent keyEvent) {
	    if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
		productionoverviewFilterListener.doFilter(productionoverviewFilter);
	    }

	}

	public void keyTyped(KeyEvent arg0) {
	    // TODO Auto-generated method stub

	}

    }
}
