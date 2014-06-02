package no.ugland.utransprod.gui.handlers;

import java.awt.Component;

import javax.swing.JLabel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.TransportCostManager;
import no.ugland.utransprod.util.FileExtensionFilter;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;

public class TransportPriceHandler implements Threadable {
    private Component component;

    private WindowInterface waitWindow;

    private TransportCostActionEnum transportCostActionEnum;

    private ManagerRepository managerRepository;

    public TransportPriceHandler(final Component comp, final TransportCostActionEnum aTransportCostActionEnum,
	    final ManagerRepository aManagerRepository) {
	managerRepository = aManagerRepository;
	transportCostActionEnum = aTransportCostActionEnum;
	component = comp;
    }

    public final void importOrUpdateTransportPrices(final JLabel label, final WindowInterface window) throws ProTransException {
	TransportCostManager transportCostManager = (TransportCostManager) ModelUtil.getBean("transportCostManager");
	transportCostManager.setLabelInfo(label);
	String excelFileName = Util.getFileName(component, new FileExtensionFilter("xls", "Excel"), null);

	if (excelFileName != null) {
	    transportCostActionEnum.doImport(window, managerRepository.getTransportCostManager(), label, excelFileName);
	    /*
	     * switch (transportCostActionEnum) { case IMPORT: boolean add =
	     * Util.showConfirmDialog(window.getComponent(), "Legge til",
	     * "Skal postnummer legges til?");
	     * transportCostManager.importAllPostalCodes(excelFileName,add);
	     * break; case UPDATE:
	     * transportCostManager.updatePricesFromFile(excelFileName); break;
	     * case COUNTY: boolean addCounties =
	     * Util.showConfirmDialog(window.getComponent(), "Legge til",
	     * "Skal fylker legges til?");
	     * transportCostManager.importAllCounties(excelFileName,
	     * addCounties); break; case AREA: boolean addAreas =
	     * Util.showConfirmDialog(window.getComponent(), "Legge til",
	     * "Skal kommuner legges til?");
	     * transportCostManager.importAllAreas(excelFileName, addAreas);
	     * break; default: }
	     */

	}
    }

    public final void doWhenFinished(final Object object) {
	if (object != null) {
	    Util.showErrorDialog(component, "Feil", object.toString());
	} else {
	    Util.showMsgDialog(component, "Importert", "Ferdig med import");
	}
	if (waitWindow != null) {
	    waitWindow.dispose();
	}

    }

    public final Object doWork(final Object[] params, final JLabel labelInfo) {
	String errorMsg = null;
	try {
	    if (params != null) {
		waitWindow = (WindowInterface) params[0];
	    }
	    labelInfo.setText("Importerer gyldige adresser...");
	    importOrUpdateTransportPrices(labelInfo, waitWindow);

	} catch (ProTransException e) {
	    e.printStackTrace();
	    errorMsg = e.getMessage();
	}
	return errorMsg;
    }

    public final void enableComponents(final boolean enable) {

    }

    public enum TransportCostActionEnum {
	IMPORT {
	    @Override
	    public void doImport(WindowInterface window, TransportCostManager transportCostManager, JLabel label, String excelFileName)
		    throws ProTransException {

		transportCostManager.setLabelInfo(label);
		boolean add = Util.showConfirmDialog(window.getComponent(), "Legge til", "Skal postnummer legges til?");
		transportCostManager.importAllPostalCodes(excelFileName, add);

	    }
	},
	UPDATE {
	    @Override
	    public void doImport(WindowInterface window, TransportCostManager transportCostManager, JLabel label, String excelFileName)
		    throws ProTransException {
		transportCostManager.updatePricesFromFile(excelFileName);

	    }
	},
	COUNTY {
	    @Override
	    public void doImport(WindowInterface window, TransportCostManager transportCostManager, JLabel label, String excelFileName)
		    throws ProTransException {
		boolean addCounties = Util.showConfirmDialog(window.getComponent(), "Legge til", "Skal fylker legges til?");
		transportCostManager.importAllCounties(excelFileName, addCounties);

	    }
	},
	AREA {
	    @Override
	    public void doImport(WindowInterface window, TransportCostManager transportCostManager, JLabel label, String excelFileName)
		    throws ProTransException {
		boolean addAreas = Util.showConfirmDialog(window.getComponent(), "Legge til", "Skal kommuner legges til?");
		transportCostManager.importAllAreas(excelFileName, addAreas);

	    }
	},
	SNOW_LOAD {
	    @Override
	    public void doImport(WindowInterface window, TransportCostManager transportCostManager, JLabel label, String excelFileName)
		    throws ProTransException {
		transportCostManager.importSnowLoad(excelFileName);

	    }
	},
	ZONE_ADDITION_ASSEMBLY {
	    @Override
	    public void doImport(WindowInterface window, TransportCostManager transportCostManager, JLabel label, String excelFileName)
		    throws ProTransException {
		transportCostManager.setLabelInfo(label);
		transportCostManager.importAllZoneAddtionAssembly(excelFileName);

	    }
	};

	public abstract void doImport(WindowInterface window, TransportCostManager transportCostManager, JLabel label, String excelFileName)
		throws ProTransException;
    }
}
