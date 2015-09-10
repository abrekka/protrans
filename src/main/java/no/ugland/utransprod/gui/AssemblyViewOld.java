package no.ugland.utransprod.gui;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import no.ugland.utransprod.gui.handlers.SupplierOrderViewHandler;
import no.ugland.utransprod.gui.model.AssemblyModel;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JYearChooser;

/**
 * Klasse som håndtrer visning av monteringsinfo for ordre
 * 
 * @author atle.brekka
 */
public class AssemblyViewOld {
    /**
     * Liste over monteringsteam
     */
    private JComboBox comboBoxAssemblyTeam;

    /**
     * Komponent for valg av år
     */
    private JYearChooser yearChooser;

    /**
     * Uker
     */
    private JComboBox comboBoxAssemblyWeek;

    /**
     * Presentasjonsmodell
     */
    private PresentationModel presentationModel;

    /**
     * Klasse for håndtering av vindusvariable
     */
    private SupplierOrderViewHandler viewHandler;

    private ApplicationUser applicationUser;

    public AssemblyViewOld(final SupplierOrderViewHandler handler, final Assembly assembly, final ApplicationUser aApplicationUser) {
	applicationUser = aApplicationUser;
	viewHandler = handler;
	presentationModel = new PresentationModel(new AssemblyModel(assembly, applicationUser.getUserName()));
    }

    /**
     * Initierer komponenter
     */
    private void initComponents() {
	comboBoxAssemblyTeam = new JComboBox(new ComboBoxAdapter(viewHandler.getSupplierList(),
		presentationModel.getModel(AssemblyModel.PROPERTY_SUPPLIER)));
	comboBoxAssemblyTeam.setName("AssemblyTeam");

	ValueModel yearModel = presentationModel.getModel(AssemblyModel.PROPERTY_ASSEMBLY_YEAR);

	yearChooser = new JYearChooser();
	yearChooser.setName("AssemblyYear");

	PropertyConnector conn = new PropertyConnector(yearChooser, "year", yearModel, "value");
	conn.updateProperty2();

	comboBoxAssemblyWeek = new JComboBox(new ComboBoxAdapter(Util.getWeeks(), presentationModel.getModel(AssemblyModel.PROPERTY_ASSEMBLY_WEEK)));
	comboBoxAssemblyWeek.setName("AssemblyWeek");
	comboBoxAssemblyWeek.setSelectedItem(Util.getCurrentWeek());

    }

    /**
     * Bygger vinduspanel
     * 
     * @return panel
     */
    public final JComponent buildPanel() {
	initComponents();
	FormLayout layout = new FormLayout("p,3dlu,30dlu,3dlu,p,3dlu,p,3dlu,p", "p,3dlu,p");
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();
	builder.addLabel("År:", cc.xy(1, 1));
	builder.add(yearChooser, cc.xy(3, 1));
	builder.addLabel("Uke:", cc.xy(5, 1));
	builder.add(comboBoxAssemblyWeek, cc.xy(7, 1));

	builder.addLabel("Monteringsteam:", cc.xyw(1, 3, 5));
	builder.add(comboBoxAssemblyTeam, cc.xyw(7, 3, 3));
	return builder.getPanel();
    }
}
