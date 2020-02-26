package no.ugland.utransprod.gui.handlers;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.service.ManagerRepository;

public class SutakPackageViewHandler extends PackageViewHandler {

	public SutakPackageViewHandler(ApplyListInterface<PackableListItem> packageInterface, Login login,
			final String mainArticleName, ManagerRepository managerRepository,
			DeviationViewHandlerFactory deviationViewHandlerFactory) {
		super(login, managerRepository, deviationViewHandlerFactory, packageInterface, "Pakking av sutaksplater",
				TableEnum.TABLEPACKAGESUTAK, mainArticleName);

	}

	@Override
	void initColumnWidthExt() {
		DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
		tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnExt(3).setPreferredWidth(200);
//	table.getColumnExt(9).setPreferredWidth(100);
		table.getColumnExt(8).setCellRenderer(tableCellRenderer);
	}

}
