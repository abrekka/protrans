package no.ugland.utransprod.gui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import no.ugland.utransprod.ProtransUncaughtHandler;
import no.ugland.utransprod.gui.handlers.PackageProductionViewHandler;
import no.ugland.utransprod.util.Util;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Standalone vindu for å vise produksjon og budsjett, samt vise generell
 * informasjon som legges inn i admin fra ProTrans
 * 
 * @author atle.brekka
 * 
 */
public class PackageProductionView {
	/**
	 * 
	 */
	private JLabel labelBudget;

	/**
	 * 
	 */
	private JLabel labelAggregateBudget;

	/**
	 * 
	 */
	private JLabel labelReal;

	/**
	 * 
	 */
	private JLabel labelAggregateReal;

	/**
	 * 
	 */
	private JLabel labelDeviation;

	/**
	 * 
	 */
	private JLabel labelDeviationKr;

	/**
	 * 
	 */
	private JLabel labelAggregateDeviationKr;

	/**
	 * 
	 */
	private JLabel labelDeviationProcent;

	/**
	 * 
	 */
	private JLabel labelDeviationProc;

	/**
	 * 
	 */
	private JLabel labelAggregateDeviationProc;

	/**
	 * 
	 */
	private JLabel labelSum;

	/**
	 * 
	 */
	private JLabel labelWeek;

	/**
	 * 
	 */
	private JLabel labelRealWeek;

	/**
	 * 
	 */
	private JLabel labelBudgetWeek;

	

	/**
	 * 
	 */
	private TextTicker textTickerInfo;

	/**
	 * 
	 */
	private PackageProductionViewHandler viewHandler;

	/**
	 * @param handler
	 */
	public PackageProductionView(PackageProductionViewHandler handler) {
		viewHandler = handler;
	}

	/**
	 * Initierer vinduskomponeter
	 */
	private void initComponents() {
		labelBudget = viewHandler.getLabelBudget();

		labelAggregateBudget = viewHandler.getLabelAggregateBudget();
		labelReal = viewHandler.getLabelReal();

		labelAggregateReal = viewHandler.getLabelAggregateReal();
		labelDeviation = viewHandler.getLabelDeviation();
		labelDeviationKr = viewHandler.getLabelDeviationKr();
		labelAggregateDeviationKr = viewHandler.getLabelAggregateDeviationKr();
		labelDeviationProcent = viewHandler.getLabelDeviationProcent();
		labelDeviationProc = viewHandler.getLabelDeviationProc();
		labelAggregateDeviationProc = viewHandler
				.getLabelAggregateDeviationProc();
		labelSum = viewHandler.getLabelSum();
		labelWeek = viewHandler.getLabelWeek();
		labelRealWeek = viewHandler.getLabelRealWeek();
		labelBudgetWeek = viewHandler.getLabelBudgetWeek();

		textTickerInfo = viewHandler.getTextTicker();

	}

	/**
	 * Bygger panel
	 * 
	 * @return panel
	 */
	public JPanel buildPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		FormLayout layout = new FormLayout("fill:p:grow",
				"p,100dlu,bottom:p:grow");

		FormDebugPanel formDebugPanel = new FormDebugPanel();

		formDebugPanel.setGridColor(Color.BLACK);
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(formDebugPanel,layout);
		CellConstraints cc = new CellConstraints();

		builder.add(buildStatisticsPanel(), cc.xy(1, 1));

		List<String> test = new ArrayList<String>();
		test.add("test");
		test.add("test2");
		builder.add(textTickerInfo, cc.xy(1, 3));

		panel.add(builder.getPanel(), new GridBagConstraints(1, 1, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		return panel;

	}

	/**
	 * Bygger statistikkpanel
	 * 
	 * @return panel
	 */
	@SuppressWarnings("deprecation")
	public JPanel buildStatisticsPanel() {
		initComponents();
		FormLayout layout = new FormLayout(
				"p,15dlu,p,15dlu,p,50dlu,p,15dlu,p,15dlu", "p,p,p");

		List<Integer> noBorders = new ArrayList<Integer>();
		noBorders.add(1);
		noBorders.add(3);
		noBorders.add(5);
		noBorders.add(7);
		noBorders.add(9);
		FormPanelGrid formDebugPanel = new FormPanelGrid(noBorders);

		formDebugPanel.setGridColor(Color.BLACK);
		formDebugPanel.setPaintInBackground(true);
		PanelBuilder builder = new PanelBuilder(formDebugPanel, layout);
		CellConstraints cc = new CellConstraints();
		builder.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		builder.add(labelWeek, cc.xy(1, 2));
		builder.add(labelSum, cc.xy(1, 3));
		builder.add(labelBudget, cc.xy(3, 1));
		builder.add(labelBudgetWeek, cc.xyw(3, 2, 2));
		builder.add(labelAggregateBudget, cc.xyw(3, 3, 2));
		builder.add(labelReal, cc.xy(5, 1));
		builder.add(labelRealWeek, cc.xyw(5, 2, 2));
		builder.add(labelAggregateReal, cc.xyw(5, 3, 2));
		builder.add(labelDeviation, cc.xy(7, 1));
		builder.add(labelDeviationKr, cc.xyw(7, 2, 2));
		builder.add(labelAggregateDeviationKr, cc.xyw(7, 3, 2));
		builder.add(labelDeviationProcent, cc.xy(9, 1));
		builder.add(labelDeviationProc, cc.xyw(9, 2, 2));
		builder.add(labelAggregateDeviationProc, cc.xyw(9, 3, 2));
		return builder.getPanel();
	}

	/**
	 * Starter vindu
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Thread
				.setDefaultUncaughtExceptionHandler(new ProtransUncaughtHandler());
		try {

			UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			LiquidLookAndFeel.setLiquidDecorations(true, "mac");

		} catch (Exception e) {
			e.printStackTrace();
		}

		ResourceBundle configuration = ResourceBundle.getBundle("application");

		String version = configuration.getString("version");

		LoadView loadView = new LoadView(version, "Produksjonsstatistikk");
		JDialog loadDialog = loadView.buildDialog();

		Util.locateOnScreenCenter(loadDialog);
		loadDialog.setVisible(true);

		final PackageProductionViewHandler packageProductionViewHandler = new PackageProductionViewHandler();
		PackageProductionView packageProductionView = new PackageProductionView(
				packageProductionViewHandler);

		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame jFrame = new JFrame("Produksjonsstatistikk");

		jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		jFrame.setIconImage(IconEnum.ICON_UGLAND_BIG.getIcon().getImage());
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		WindowInterface frame = new JFrameAdapter(jFrame);

		frame.add(packageProductionView.buildPanel());

		Timer t = new javax.swing.Timer(10000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				packageProductionViewHandler.refresh();
			}
		});
		t.start();

		frame.setVisible(true);
		loadDialog.dispose();

	}
}
