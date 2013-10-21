package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ReportValidator;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;

public class ShowReportAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    private WindowInterface window;

    private Threadable printer;

    private ReportValidator reportValidator;

    private boolean reportValid = true;

    public ShowReportAction(final WindowInterface aWindow,
            final Threadable aPrinter, final ReportValidator aReportValidator) {
        super("Rapport");
        window = aWindow;
        printer = aPrinter;
        if (aReportValidator != null) {
            reportValidator = aReportValidator;
            reportValid = false;
        }
    }

    public final void actionPerformed(final ActionEvent arg0) {
        if (reportValid||reportValidator.isValid()) {
            Util.runInThreadWheel(window.getRootPane(), printer, null);
        }

    }
}
