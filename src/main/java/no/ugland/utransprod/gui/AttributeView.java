package no.ugland.utransprod.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.buttons.CancelListener;
import no.ugland.utransprod.gui.handlers.AttributeViewHandler;
import no.ugland.utransprod.model.Attribute;

import org.jdesktop.swingx.JXList;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Klasse som håndtrer visning av attributter som kan velges
 * @author atle.brekka
 */
public class AttributeView implements CancelListener {
    private AttributeViewHandler viewHandler;

    private JXList listAttributes;

    private JButton buttonOk;

    private JButton buttonCancel;

    private JButton buttonAdd;

    private JButton buttonEdit;

    private JButton buttonRemove;

    private boolean singleSelection = false;

    private boolean canceled = false;

    /**
     * @param handler
     * @param singleAttributeSelection
     *            true dersom det bare skal kunne velges en attributt
     */
    public AttributeView(final AttributeViewHandler handler,
            final boolean singleAttributeSelection) {
        viewHandler = handler;
        singleSelection = singleAttributeSelection;
    }

    /**
     * Initierer vinduskomponenter
     * @param window
     */
    private void initComponents(final WindowInterface window) {
        listAttributes = viewHandler.getListAttributes(singleSelection);
        buttonCancel = viewHandler.getCancelButton(window, this);
        buttonOk = viewHandler.getOkButton(window);
        buttonAdd = viewHandler.getAddAttributeButton(window);
        buttonEdit = viewHandler.getEditButton(window);
        buttonEdit.setEnabled(false);
        buttonRemove = viewHandler.getRemoveAttributeButton(window);
        buttonRemove.setEnabled(false);

    }

    /**
     * Initierer hendelsehåndtering
     */
    private void initEventHandling() {
    }

    /**
     * Bygger panel for visning av attributter
     * @param window
     * @return panel
     */
    public final JComponent buildPanel(final WindowInterface window) {
        initComponents(window);
        initEventHandling();
        FormLayout layout = new FormLayout("10dlu,80dlu,3dlu,p",
                "10dlu,p,3dlu,100dlu,3dlu,p,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        builder.addLabel("Attributter", cc.xy(2, 2));
        builder.add(new JScrollPane(listAttributes), cc.xy(2, 4));
        builder.add(buildButtonPanel(), cc.xy(4, 4));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonOk, buttonCancel),
                cc.xyw(2, 6, 3));
        return builder.getPanel();
    }

    /**
     * Bygger panel med knapper for å legge til, endre eller slette attributt
     * @return panel
     */
    private JPanel buildButtonPanel() {
        ButtonStackBuilder builder = new ButtonStackBuilder();
        builder.addGridded(buttonAdd);
        builder.addRelatedGap();
        builder.addGridded(buttonEdit);
        builder.addRelatedGap();
        builder.addGridded(buttonRemove);
        return builder.getPanel();
    }

    /**
     * Henter valgte attributter
     * @return attributter
     */
    @SuppressWarnings("unchecked")
    public final List<Attribute> getSelectedObjects() {
        return new ArrayList(Arrays.asList(listAttributes.getSelectedValues()));
    }

    /**
     * @see no.ugland.utransprod.gui.buttons.CancelListener#canceled()
     */
    public final void canceled() {
        canceled = true;

    }

    /**
     * Returnerer true dersom dialog er kanselert
     * @return kanselert
     */
    public final boolean isCanceled() {
        return canceled;
    }

}
