package no.ugland.utransprod.util.report;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;
import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.JRViewerProTrans;
import no.ugland.utransprod.util.MailUtil;
import no.ugland.utransprod.util.Util;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Brukes til å generere rapporter.
 * 
 * @author atle.brekka
 */
public class ReportViewer extends JDialog implements Closeable {
	private static final long serialVersionUID = 1L;

	private static final String UGLAND_LOGO_PARAMETER = "ugland_logo";

	private String currentHeading;

	private JPanel pnlMain;

	private JButton buttonCancel;

	private JButton buttonMail;

	JasperPrint jasperPrintReport;

	String fileName;
	private MailConfig mailConfig;

	public ReportViewer() {

	}

	/**
	 * @param heading
	 */
	public ReportViewer(final String heading) {
		this(heading, null);
	}

	public ReportViewer(final String heading, final MailConfig aMailConfig) {
		mailConfig = aMailConfig;
		setName(heading);
		currentHeading = heading;
	}

	/**
	 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public final boolean canClose(final String actionString, final WindowInterface window) {
		return true;
	}

	/**
	 * Lager panel.
	 * 
	 * @param window
	 * @return panel
	 */
	public final JPanel buildPanel(final WindowInterface window) {
		initComponents(window);
		FormLayout layout = new FormLayout("400dlu:grow", "fill:300dlu:grow,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();
		builder.add(pnlMain, cc.xy(1, 1));
		if (mailConfig != null) {
			builder.add(ButtonBarFactory.buildCenteredBar(buttonMail, buttonCancel), cc.xy(1, 3));
		} else {
			builder.add(ButtonBarFactory.buildCenteredBar(buttonCancel), cc.xy(1, 3));
		}
		return builder.getPanel();
	}

	/**
	 * Genererer rapport.
	 * 
	 * @param tableModel
	 * @param heading
	 * @param reportEnum
	 * @param extraParams
	 * @throws ProTransException
	 */
	public final void generateProtransReport(final TableModel tableModel, final String heading,
			final ReportEnum reportEnum, final Map<String, Object> extraParams) throws ProTransException {
		generateProtransReportFromDatasource(new JRTableModelDataSource(tableModel), heading, reportEnum, extraParams);
	}

	public final void generateProtransReportFromBeanAndShow(final Collection<?> beans, final String heading,
			final ReportEnum reportEnum, final Map<String, Object> extraParams, final String reportFileName,
			final WindowInterface window, final boolean isModal) throws ProTransException {
		JDialog dialog = Util.getDialog(window, heading, isModal);
		WindowInterface dialogWindow = new JDialogAdapter(dialog);
		dialogWindow.setName(heading);

		dialogWindow.add(buildPanel(dialogWindow));

		generateProtransReportFromBean(beans, heading, reportEnum, extraParams, reportFileName);
		dialogWindow.pack();
		dialogWindow.setSize(new Dimension(850, 700));
		Util.locateOnScreenCenter(dialogWindow);
		dialogWindow.setVisible(true);
	}

	public final void generateProtransReportAndShow(final TableModel tableModel, final String heading,
			final ReportEnum reportEnum, final Map<String, Object> extraParams, final WindowInterface window)
					throws ProTransException {
		JDialog dialog = Util.getDialog(window, heading, true);
		WindowInterface dialogWindow = new JDialogAdapter(dialog);

		dialogWindow.add(buildPanel(dialogWindow));

		generateProtransReport(tableModel, heading, reportEnum, null);
		dialogWindow.pack();
		dialogWindow.setSize(new Dimension(850, 700));
		Util.locateOnScreenCenter(dialogWindow);
		dialogWindow.setVisible(true);
	}

	/**
	 * Genererer rapport.
	 * 
	 * @param beans
	 * @param heading
	 * @param reportEnum
	 * @param extraParams
	 * @param reportFileName
	 * @throws ProTransException
	 */
	public final void generateProtransReportFromBean(final Collection<?> beans, final String heading,
			final ReportEnum reportEnum, final Map<String, Object> extraParams, final String reportFileName)
					throws ProTransException {
		fileName = reportFileName;
		generateProtransReportFromDatasource(new JRBeanCollectionDataSource(beans), heading, reportEnum, extraParams);
	}

	/**
	 * Initierer vinduskomponenter.
	 * 
	 * @param window
	 */
	private void initComponents(final WindowInterface window) {
		window.setName(currentHeading);
		buttonCancel = new CancelButton(window, this, true);
		buttonCancel.setName("ButtonCancel");
		buttonMail = new JButton(new MailAction(window));
		buttonMail.setIcon(IconEnum.ICON_MAIL.getIcon());
		pnlMain = new javax.swing.JPanel();
		pnlMain.setLayout(new java.awt.BorderLayout());

	}

	/**
	 * Stenger vindu.
	 */
	private void buttonCancelMouseClicked() {
		dispose();
	}

	private void generateReport(final String reportFile, final JRDataSource datasource, final String heading,
			final Map<String, Object> parameters, final boolean printable) throws ProTransException {
		try {
			InputStream stream = getClass().getClassLoader().getResourceAsStream(reportFile);

			if (stream == null) {
				throw new ProTransException(String.format("Fant ikke rapport %s",reportFile));
			}
			
			//Brukes for å kompilere rapporter laget i JasperSoft Studio
//			InputStream rapport =getClass().getClassLoader().getResourceAsStream("reports/assembly_ny.jrxml");
//			String filnavn=JasperCompileManager.compileReportToFile("reports/kontrollskjema_ny.jrxml");
//			String filnavn2=JasperCompileManager.compileReportToFile("reports/assembly_ny_ny.jrxml");
//			String filnavn3=JasperCompileManager.compileReportToFile("reports/hms2_ny_ny.jrxml");
			String filnavn3=JasperCompileManager.compileReportToFile("reports/fraktbrev.jrxml");
//			String filnavn4=JasperCompileManager.compileReportToFile("reports/hms_fraktbrev.jrxml");
//			String filnavn4=JasperCompileManager.compileReportToFile("reports/deleliste_tillegg.jrxml");
			
			jasperPrintReport = JasperFillManager.fillReport(stream, parameters, datasource);

			JRViewerProTrans viewer = new JRViewerProTrans(jasperPrintReport);
			viewer.setPrintable(printable);
			viewer.setSavable(printable);
			this.pnlMain.add(viewer, BorderLayout.CENTER);
		} catch (JRException e) {
			e.printStackTrace();
			String msg;
			if (e.getCause() instanceof FileNotFoundException) {
				msg = "Kunne ikke finne rapport " + reportFile;
			} else {
				msg = e.getMessage();
			}
			throw new ProTransException("Feil ved generering av rapport " + msg);
		}
	}

	private void generateProtransReportFromDatasource(final JRDataSource datasource, final String heading,
			final ReportEnum reportEnum, final Map<String, Object> extraParams) throws ProTransException {
		ReportCompiler.compileReports(false);
		Map<String, Object> parameters = new HashMap<String, Object>();
		addLogoToParameters(reportEnum, parameters, extraParams);
		parameters.put("HEADING", heading);
		InputStream iconStream = getClass().getClassLoader().getResourceAsStream("images/igland_garasjen.png");
		parameters.put("IGLAND_GARASJE_LOGO", iconStream);

		if (extraParams != null) {
			parameters.putAll(extraParams);
		}

		StringBuffer reportFile = new StringBuffer("reports/");

		reportFile.append(reportEnum.getReportFileName());

		generateReport(reportFile.toString(), datasource, heading, parameters, true);
	}

	private void addLogoToParameters(final ReportEnum reportEnum, Map<String, Object> parameters,
			final Map<String, Object> extraParams) {
		if (!logoIsAdded(extraParams)) {
			InputStream iconStream = getClass().getClassLoader().getResourceAsStream(reportEnum.getImagePath());

			parameters.put(UGLAND_LOGO_PARAMETER, iconStream);
		}

	}

	private boolean logoIsAdded(Map<String, Object> parameters) {
		return parameters != null && parameters.get(UGLAND_LOGO_PARAMETER) != null;

	}

	/**
	 * Lukker vindu.
	 * 
	 * @param evt
	 */
	protected final void buttonCancelActionPerformed(final ActionEvent evt) {
		buttonCancelMouseClicked();
	}

	/**
	 * Håndterer sending av rapport som mail.
	 * 
	 * @author atle.brekka
	 */
	private class MailAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MailAction(final WindowInterface aWindow) {
			super("Send epost...");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public final void actionPerformed(final ActionEvent arg0) {
			if (mailConfig != null) {
				sendMail(window);
			}

		}

	}

	private void sendMail(final WindowInterface window) {
		try {
			Util.setWaitCursor(window.getComponent());
//			HtmlEx
			String tmpPath = getTempDir();
			String tempFileName = getTempFileName(tmpPath, mailConfig.getFileName());
			File pdfFile = new File(tempFileName);
			JRPdfSaveContributor pdfSaver = new JRPdfSaveContributor(null, null);
			pdfSaver.save(jasperPrintReport, pdfFile);

			MailUtil.sendMailWithAttachmentDesktop(mailConfig.getToMailAddress(), mailConfig.getHeading(),
					mailConfig.getMsg(), tempFileName);
			// MailUtil.sendDeviationMail(tempFileName, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			Util.showErrorDialog(window, "Feil", e.getMessage());
		} finally {
			Util.setDefaultCursor(window.getComponent());
		}

	}

	final String getTempFileName(final String tmpPath, final String aFileName) {
		String tempFileName;
		if (fileName == null) {
			tempFileName = tmpPath + "/" + aFileName + Util.getCurrentDateAsDateTimeString() + ".pdf";
		} else {
			tempFileName = tmpPath + "/" + fileName;
		}
		return tempFileName;
	}

	final String getTempDir() throws ProTransException {
		String tmpPath = ApplicationParamUtil.findParamByName("temp_dir");
		File tempDir = new File(tmpPath);
		if (!tempDir.exists()) {
			if (!tempDir.mkdir()) {
				throw new ProTransException("Kunne ikke lage temp katalog " + tmpPath);
			}
		}
		return tmpPath;
	}
}
