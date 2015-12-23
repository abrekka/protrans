package no.ugland.utransprod.gui.edit;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.handlers.AssemblyPlannerViewHandler.AssemblyColumn;
import no.ugland.utransprod.model.AssemblyV;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.lowagie.text.Font;

public class FilterAssemblyView implements Closeable {

    private CancelButton buttonCancel;
    private JButton buttonFilter;
    private PresentationModel presentationModel;
    private AssemblyFilter assemblyfilter;
    private JLabel labelFilter;
    private JLabel labelSort;
    private JComboBox comboBoxSort1;
    private JComboBox comboBoxSort2;
    private JComboBox comboBoxSort3;

    public JComponent buildPanel(final WindowInterface window, AssemblyfilterListener listener) {
	initComponents(window, listener);
	FormLayout layout = new FormLayout("15dlu,p:grow,3dlu,p,3dlu,p,15dlu", "10dlu,p,3dlu,p,3dlu,p,3dlu,p,p:grow,p,3dlu,p,5dlu");
	// PanelBuilder builder = new PanelBuilder(layout, new
	// FormDebugPanel());
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.add(labelFilter, cc.xyw(2, 2, 3));

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
	FormLayout layout = new FormLayout("p,3dlu,80dlu,", "");
	// DefaultFormBuilder builder = new DefaultFormBuilder(layout, new
	// FormDebugPanel());
	DefaultFormBuilder builder = new DefaultFormBuilder(layout);
	for (AssemblyColumn column : AssemblyColumn.getVisibleColumns()) {
	    Component component = column.getFilterComponent(presentationModel);
	    if (component != null) {
		if (JTextField.class.isInstance(component)) {
		    ((JTextField) component).getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "filter");
		    ((JTextField) component).getActionMap().put("filter", buttonFilter.getAction());
		}
		builder.appendRow(new RowSpec("fill:p"));
		builder.append(column.getColumnName());
		builder.append(component);
		builder.appendRow(new RowSpec("center:3dlu"));
		builder.nextRow();
	    }
	}
	JPanel panel = builder.getPanel();
	JRootPane root = SwingUtilities.getRootPane(panel);
	panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "filter");
	panel.getActionMap().put("filter", buttonFilter.getAction());
	return builder.getPanel();
    }

    protected void initComponents(final WindowInterface window, AssemblyfilterListener assemblyfilterListener) {
	labelFilter = new JLabel("Filter (% er wildcard)", SwingConstants.CENTER);
	labelFilter.setFont(labelFilter.getFont().deriveFont(labelFilter.getFont().getStyle() | Font.BOLD));
	labelSort = new JLabel("Sortering", SwingConstants.CENTER);
	labelSort.setFont(labelSort.getFont().deriveFont(labelSort.getFont().getStyle() | Font.BOLD));
	assemblyfilter = new AssemblyFilter();
	presentationModel = new PresentationModel(assemblyfilter);
	buttonCancel = new CancelButton(window, this, true);
	Action action = new FilterAction(assemblyfilterListener, window);
	buttonFilter = new JButton(action);

	buttonFilter.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "FILTER");
	buttonFilter.getActionMap().put("FILTER", action);

	comboBoxSort1 = BasicComponentFactory.createComboBox(assemblyfilter.getSort1SelectionInList());
	comboBoxSort2 = BasicComponentFactory.createComboBox(assemblyfilter.getSort2SelectionInList());
	comboBoxSort3 = BasicComponentFactory.createComboBox(assemblyfilter.getSort3SelectionInList());

    }

    public JButton getCancelButton(WindowInterface window) {
	buttonCancel = new CancelButton(window, this, true);
	buttonCancel.setName("ButtonCancel");
	return buttonCancel;
    }

    public boolean canClose(String actionString, WindowInterface window) {
	return true;
    }

    @SuppressWarnings("serial")
    public class FilterAction extends AbstractAction {
	private AssemblyfilterListener assemblyfilterListener;
	private WindowInterface window;

	public FilterAction(AssemblyfilterListener listener, WindowInterface window) {
	    super("Filtrer");
	    this.assemblyfilterListener = listener;
	    this.window = window;
	}

	public void actionPerformed(ActionEvent arg0) {
	    assemblyfilterListener.doFilter(assemblyfilter);

	    window.dispose();
	    window = null;
	}

    }

    public static class AssemblyFilter extends Model {
	private String assemblyteam;
	private String assemblyWeek;
	private String planned;
	private String packlist;
	private Boolean sent;
	private Boolean assemblied;
	private SelectionInList sort1SelectionList;
	private SelectionInList sort2SelectionList;
	private SelectionInList sort3SelectionList;
	private String sentBase;
	private String orderNr;
	private String firstname;
	private String lastname;
	private String postalCode;
	private String postOffice;
	private String telephone;
	private String comment;
	private String productArea;
	private String productionWeek;
	private String transport;
	private String takstein;
	private String assemblyCost;
	private String firstPlanned;
	private String craningCost;
	private String constructionTypeName;
	private String info;
	private String specialConcern;
	private String assemblerCost;
	private String assemblerCraning;
	private String assemblyComment;
	private String dg;
	private String deliveryAddress;

	public AssemblyFilter() {
	    sort1SelectionList = new SelectionInList(AssemblyColumn.getVisibleColumns());
	    sort2SelectionList = new SelectionInList(AssemblyColumn.getVisibleColumns());
	    sort3SelectionList = new SelectionInList(AssemblyColumn.getVisibleColumns());
	}

	public String getComment() {
	    return comment;
	}

	public void setComment(String comment) {
	    this.comment = comment;
	}

	public String getPacklist() {
	    return packlist;
	}

	public void setPacklist(String packlist) {
	    this.packlist = packlist;
	}

	public String getPlanned() {
	    return planned;
	}

	public void setPlanned(String planned) {
	    this.planned = planned;
	}

	public String getAssemblyWeek() {
	    return assemblyWeek;
	}

	public void setAssemblyWeek(String assemblyWeek) {
	    this.assemblyWeek = assemblyWeek;
	}

	public Boolean getAssemblied() {
	    return assemblied;
	}

	public void setAssemblied(Boolean assemblied) {
	    this.assemblied = assemblied;
	}

	public Boolean getSent() {
	    return sent;
	}

	public void setSent(Boolean sent) {
	    this.sent = sent;
	}

	public String getAssemblyteam() {
	    return assemblyteam;
	}

	public SelectionInList getSort1SelectionInList() {
	    return sort1SelectionList;
	}

	public SelectionInList getSort2SelectionInList() {
	    return sort2SelectionList;
	}

	public SelectionInList getSort3SelectionInList() {
	    return sort3SelectionList;
	}

	public void setAssemblyteam(String assemblyteam) {
	    this.assemblyteam = assemblyteam;
	}

	public boolean filter(AssemblyV assembly) {
	    boolean filter = true;
	    for (AssemblyColumn column : AssemblyColumn.getVisibleColumns()) {
		filter = filter && column.filter(assembly, this);
	    }
	    return filter;
	}

	public int sort(AssemblyV assembly1, AssemblyV assembly2) {
	    AssemblyColumn sortColumn1 = (AssemblyColumn) sort1SelectionList.getSelection();
	    AssemblyColumn sortColumn2 = (AssemblyColumn) sort2SelectionList.getSelection();
	    AssemblyColumn sortColumn3 = (AssemblyColumn) sort3SelectionList.getSelection();
	    int sortValue = 0;
	    if (sortColumn1 != null) {
		sortValue = sortColumn1.sort(assembly1, assembly2);
	    }
	    if (sortValue == 0 && sortColumn2 != null) {
		sortValue = sortColumn2.sort(assembly1, assembly2);
	    }
	    if (sortValue == 0 && sortColumn3 != null) {
		sortValue = sortColumn3.sort(assembly1, assembly2);
	    }
	    return sortValue;
	}

	public String getSentBase() {
	    return sentBase;
	}

	public void setSentBase(String sentBase) {
	    this.sentBase = sentBase;
	}

	public String getOrderNr() {
	    return orderNr;
	}

	public void setOrderNr(String orderNr) {
	    this.orderNr = orderNr;
	}

	public String getFirstname() {
	    return firstname;
	}

	public void setFirstname(String firstname) {
	    this.firstname = firstname;
	}

	public String getLastname() {
	    return lastname;
	}

	public void setLastname(String lastname) {
	    this.lastname = lastname;
	}

	public String getPostalCode() {
	    return postalCode;
	}

	public void setPostalCode(String postalCode) {
	    this.postalCode = postalCode;
	}

	public String getPostOffice() {
	    return postOffice;
	}

	public void setPostOffice(String postOffice) {
	    this.postOffice = postOffice;
	}

	public String getTelephone() {
	    return telephone;
	}

	public void setTelephone(String telephone) {
	    this.telephone = telephone;
	}

	public String getProductArea() {
	    return productArea;
	}

	public void setProductArea(String productArea) {
	    this.productArea = productArea;
	}

	public String getProductionWeek() {
	    return productionWeek;
	}

	public void setProductionWeek(String productionWeek) {
	    this.productionWeek = productionWeek;
	}

	public String getTransport() {
	    return transport;
	}

	public void setTransport(String transport) {
	    this.transport = transport;
	}

	public String getTakstein() {
	    return takstein;
	}

	public void setTakstein(String takstein) {
	    this.takstein = takstein;
	}

	public String getAssemblyCost() {
	    return assemblyCost;
	}

	public void setAssemblyCost(String assemblyCost) {
	    this.assemblyCost = assemblyCost;
	}

	public String getFirstPlanned() {
	    return firstPlanned;
	}

	public void setFirstPlanned(String firstPlanned) {
	    this.firstPlanned = firstPlanned;
	}

	public String getCraningCost() {
	    return craningCost;
	}

	public void setCraningCost(String craningCost) {
	    this.craningCost = craningCost;
	}

	public String getConstructionTypeName() {
	    return constructionTypeName;
	}

	public void setConstructionTypeName(String constructionTypeName) {
	    this.constructionTypeName = constructionTypeName;
	}

	public String getInfo() {
	    return info;
	}

	public void setInfo(String info) {
	    this.info = info;
	}

	public String getSpecialConcern() {
	    return specialConcern;
	}

	public void setSpecialConcern(String specialConcern) {
	    this.specialConcern = specialConcern;
	}

	public String getAssemblerCost() {
	    return assemblerCost;
	}

	public void setAssemblerCost(String assemblerCost) {
	    this.assemblerCost = assemblerCost;
	}

	public String getAssemblerCraning() {
	    return assemblerCraning;
	}

	public void setAssemblerCraning(String assemblerCraning) {
	    this.assemblerCraning = assemblerCraning;
	}

	public String getAssemblyComment() {
	    return assemblyComment;
	}

	public void setAssemblyComment(String assemblyComment) {
	    this.assemblyComment = assemblyComment;
	}

	public String getDg() {
	    return dg;
	}

	public void setDg(String dg) {
	    this.dg = dg;
	}

	public String getDeliveryAddress() {
	    return this.deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
	    this.deliveryAddress = deliveryAddress;
	}

    }

    public interface AssemblyfilterListener {
	void doFilter(AssemblyFilter assemblyFilter);
    }
}
