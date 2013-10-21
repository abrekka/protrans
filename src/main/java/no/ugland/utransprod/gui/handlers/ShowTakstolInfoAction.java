/**
 * 
 */
package no.ugland.utransprod.gui.handlers;

import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JLabel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.TakstolInfoV;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.ReportViewer;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class ShowTakstolInfoAction extends AbstractAction {
	private static final String UTSTIKK_BILDE = "utstikk_bilde";
	private OrderNrProvider orderNrProvider;
	private ManagerRepository managerRepository;
	private WindowInterface window;

	// private InputStream iconStream;
	@Inject
	public ShowTakstolInfoAction(final ManagerRepository aManagerRepository,
			@Assisted WindowInterface aWindow,
			@Assisted OrderNrProvider aProduceableProvider) {
		super("Takstolinfo...");
		managerRepository = aManagerRepository;
		orderNrProvider = aProduceableProvider;
		window = aWindow;
	}

	public void actionPerformed(ActionEvent e) {
		Util.runInThreadWheel(window != null ? window.getRootPane() : null,
				new Threadable() {

					public void enableComponents(boolean enable) {
						// TODO Auto-generated method stub

					}

					public Object doWork(Object[] params, JLabel labelInfo) {
						labelInfo.setText("Genererer takstolinfo...");
						String errorString = null;
						try {
							showTakstolInfo();
						} catch (ProTransException e1) {
							errorString = e1.getMessage();

						}
						return errorString;
					}

					public void doWhenFinished(Object object) {
						if (object != null) {
							Util.showErrorDialog(window, "Feil", object
									.toString());
						}

					}
				}, null);

	}

	void showTakstolInfo() throws ProTransException {
		String orderNr = orderNrProvider.getSelectedOrderNr();
		List<TakstolInfoV> list = managerRepository.getTakstolInfoVManager()
				.findByOrderNr(orderNr);
		if (list.size() != 0) {
			TakstolInfoV takstolInfoV = getTakstolInfoV(list);
			if(takstolInfoV!=null){
			String reportFileName = getReportfileName(takstolInfoV);
			Map<String, Object> params = getParams(takstolInfoV);
			ReportViewer reportViewer = new ReportViewer("Takstolinfo");
			reportViewer.generateProtransReportFromBeanAndShow(list,
					"Takstolinfo", ReportEnum.TAKSTOL_INFO, params,
					reportFileName, null, false);
			}
		}
	}

	private Map<String, Object> getParams(TakstolInfoV takstolInfoV) {
		Map<String, Object> params = new Hashtable<String, Object>();

		if (takstolInfoV.getUtstikkType() != null) {
			String imagePath = ImageSelector.valueOf(
					"UTSTIKKTYPE_" + takstolInfoV.getUtstikkType())
					.getImagePath();
			if (imagePath != null) {
				InputStream iconStream = this.getClass().getClassLoader()
						.getResourceAsStream(imagePath);

				params.put(UTSTIKK_BILDE, iconStream);
			}
		}
		return params;
	}

	private String getReportfileName(TakstolInfoV takstolInfoV) {
		String reportFileName = "Takstolinfo - " + takstolInfoV.getOrdernr()
				+ ".pdf";
		return reportFileName;
	}

	private TakstolInfoV getTakstolInfoV(List<TakstolInfoV> list) {
		TakstolInfoV takstolInfoV = list.get(0);
		return takstolInfoV;
	}

	private enum ImageSelector {
		UTSTIKKTYPE_0(null), UTSTIKKTYPE_1(IconEnum.ICON_UTSTIKKTYPE_1), UTSTIKKTYPE_2(
				IconEnum.ICON_UTSTIKKTYPE_2), UTSTIKKTYPE_3(
				IconEnum.ICON_UTSTIKKTYPE_3), UTSTIKKTYPE_4(
				IconEnum.ICON_UTSTIKKTYPE_4);

		private IconEnum iconEnum;

		private ImageSelector(IconEnum aIconEnum) {
			iconEnum = aIconEnum;
		}

		public String getImagePath() {
			return iconEnum != null ? iconEnum.getIconPath() : null;
		}
	}

}