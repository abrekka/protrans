package no.ugland.utransprod.gui.handlers;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.service.ManagerRepository;

/**
 * Håndterer gulvsponpakking
 * 
 * @author atle.brekka
 * 
 */
public class GulvsponPackageViewHandler extends PackageViewHandler {

    /**
     * @param packageInterface
     * @param deviationViewHandlerFactory
     * @param userType
     * @param applicationUser
     */
    public GulvsponPackageViewHandler(ApplyListInterface<PackableListItem> packageInterface, Login login, final String mainArticleName,
	    ManagerRepository managerRepository, DeviationViewHandlerFactory deviationViewHandlerFactory) {
	super(login, managerRepository, deviationViewHandlerFactory, packageInterface, "Pakking av gulvspon", TableEnum.TABLEPACKAGEGULVSPON,
		mainArticleName);

    }

    @Override
    void initColumnWidthExt() {
	DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
	tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	table.getColumnExt(3).setPreferredWidth(100);
	table.getColumnExt(9).setPreferredWidth(100);
	table.getColumnExt(8).setCellRenderer(tableCellRenderer);
    }

}
