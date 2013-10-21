package no.ugland.utransprod.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import no.ugland.utransprod.gui.handlers.ExcelReportViewHandler;
import no.ugland.utransprod.util.InternalFrameBuilder;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Dialog for å velge produktivitetsrapport
 * @author atle.brekka
 */
public class ExcelReportView implements Viewer {
    private JButton buttonShowReport;

    private ExcelReportViewHandler viewHandler;

    private JButton buttonCancel;
    private boolean addEmpty;

    /**
     * @param handler
     */
    public ExcelReportView(ExcelReportViewHandler handler,boolean useEmptyProductArea) {
        viewHandler = handler;
        addEmpty=useEmptyProductArea;

    }

    /**
     * Initierer vinduskomponeter
     * @param window
     */
    private void initComponents(WindowInterface window) {
        buttonShowReport = viewHandler.getButtonShowExcel(window);
        buttonCancel = viewHandler.getButtonCancel(window);
    }

    /**
     * Byger dialog panel for dialog
     * @param window
     * @return panel
     */
    public JPanel buildPanel(WindowInterface window) {
        initComponents(window);
        FormLayout layout = new FormLayout("10dlu,center:p,10dlu", "10dlu,p,5dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        // PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
        CellConstraints cc = new CellConstraints();

        builder.add(viewHandler.buildConstraintPanel(window,addEmpty), cc.xy(2, 2));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonShowReport, buttonCancel), cc.xy(2, 4));

        return builder.getPanel();
    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#buildWindow()
     */
    public WindowInterface buildWindow() {
        WindowInterface window = InternalFrameBuilder.buildInternalFrame(viewHandler.getWindowTitle(),
                viewHandler.getWindowSize(), false);
        window.add(buildPanel(window), BorderLayout.CENTER);

        return window;
    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#cleanUp()
     */
    public void cleanUp() {

    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#getTitle()
     */
    public String getTitle() {
        return "";
    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#initWindow()
     */
    public void initWindow() {

    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#useDispose()
     */
    public boolean useDispose() {
        return true;
    }
}
