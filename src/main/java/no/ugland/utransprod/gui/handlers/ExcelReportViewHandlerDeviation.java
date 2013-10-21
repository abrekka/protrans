package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.ListModel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.DeviationStatus;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.MonthEnum;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;
import no.ugland.utransprod.util.excel.ExcelReportSettingOwnProduction;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Hjelpeklasse for avviksrapport
 * 
 * @author atle.brekka
 */
public class ExcelReportViewHandlerDeviation extends ExcelReportViewHandler {
	/**
     * 
     */
	private JComboBox comboBoxDeviationFunction;

	/**
     * 
     */
	private JComboBox comboBoxFunctionCategory;

	/**
     * 
     */
	private JComboBox comboBoxDeviationStatus;

	/**
     * 
     */
	private List<JobFunction> deviationFunctionList;

	/**
     * 
     */
	private ArrayListModel functionCategoryList;

	/**
     * 
     */
	private List<DeviationStatus> deviationStatusList;

	/**
     * 
     */
	private JComboBox comboBoxMonths;
	private JComboBox comboBoxProductAreaGroup;
	private List<ProductAreaGroup> areas;

	/**
	 * @param aWindowTitle
	 * @param useFromAndTo
	 * @param excelReportType
	 */
	public ExcelReportViewHandlerDeviation(String aWindowTitle,
			boolean useFromAndTo, ExcelReportEnum excelReportType) {
		super(excelReportType, new Dimension(320, 130));
		presentationModel = new PresentationModel(
				new ExcelReportSettingDeviation(excelReportType));

		JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil
				.getBean("jobFunctionManager");
		DeviationStatusManager deviationStatusManager = (DeviationStatusManager) ModelUtil
				.getBean("deviationStatusManager");
		// ProductAreaManager productAreaManager = (ProductAreaManager)
		// ModelUtil.getBean("productAreaManager");
		deviationFunctionList = new ArrayList<JobFunction>();
		List<JobFunction> functions = jobFunctionManager.findAll();

		if (functions != null) {
			for (JobFunction function : functions) {
				jobFunctionManager
						.lazyLoad(function, new LazyLoadEnum[][] { {
								LazyLoadEnum.FUNCTION_CATEGORIES,
								LazyLoadEnum.NONE } });
			}
			deviationFunctionList.addAll(functions);
			deviationFunctionList.add(0, null);
		}

		functionCategoryList = new ArrayListModel();
		deviationStatusList = deviationStatusManager.findAll();
		deviationStatusList.add(0, null);

		ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil
				.getBean("productAreaGroupManager");
		areas = productAreaGroupManager.findAll();

	}

	/**
	 * Lager komboboks for avviksfunksjon
	 * 
	 * @return komboboks
	 */
	private JComboBox getComboBoxDeviationFunction() {
		presentationModel.getBufferedModel(
				ExcelReportSettingDeviation.PROPERTY_DEVIATION_FUNCTION)
				.addPropertyChangeListener(new FunctionChangeListener());
		return new JComboBox(
				new ComboBoxAdapter(
						deviationFunctionList,
						presentationModel
								.getModel(ExcelReportSettingDeviation.PROPERTY_DEVIATION_FUNCTION)));
	}

	/**
	 * Lager komboboks for kategori
	 * 
	 * @return komboboks
	 */
	private JComboBox getComboBoxFunctionCategory() {
		initFunctionCategoryList();
		return new JComboBox(
				new ComboBoxAdapter(
						(ListModel) functionCategoryList,
						presentationModel
								.getModel(ExcelReportSettingDeviation.PROPERTY_FUNCTION_CATEGORY)));
	}

	/**
	 * Lager komboboks for avviksstatus
	 * 
	 * @return komboboks
	 */
	private JComboBox getComboBoxDeviationStatus() {
		return new JComboBox(
				new ComboBoxAdapter(
						deviationStatusList,
						presentationModel
								.getModel(ExcelReportSettingDeviation.PROPERTY_DEVIATION_STATUS)));
	}

	/**
	 * Lager komboboks for produktområde
	 * 
	 * @return komboboks
	 */
	/*
	 * private JComboBox getComboBoxProductArea() { return new JComboBox( new
	 * ComboBoxAdapter( productAreaList, presentationModel
	 * .getModel(ExcelReportSettingDeviation.PROPERTY_PRODUCT_AREA))); }
	 */

	/**
	 * Lager komboboks med alle måneder
	 * 
	 * @return komboboks
	 */
	private JComboBox getComboBoxMonths() {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(MonthEnum
				.getMonthList(), presentationModel
				.getModel(ExcelReportSettingDeviation.PROPERTY_MONTH_ENUM)));
		comboBox.setName("ComboBoxMonth");
		return comboBox;
	}

	private JComboBox getComboBoxProductAreaGroup() {
		return new JComboBox(
				new ComboBoxAdapter(
						areas,
						presentationModel
								.getModel(ExcelReportSettingDeviation.PROPERTY_PRODUCT_AREA_GROUP)));
	}

	/**
	 * Initierer kategorier
	 */
	void initFunctionCategoryList() {
		functionCategoryList.clear();
		presentationModel.setBufferedValue(
				ExcelReportSettingDeviation.PROPERTY_FUNCTION_CATEGORY, null);
		JobFunction jobFunction = (JobFunction) presentationModel
				.getBufferedValue(ExcelReportSettingDeviation.PROPERTY_DEVIATION_FUNCTION);
		if (jobFunction != null && jobFunction.getFunctionCategories() != null) {
			functionCategoryList.addAll(jobFunction.getFunctionCategories());
			functionCategoryList.add(0, null);
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.ExcelReportViewHandler#initComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initComponents(WindowInterface window, boolean addEmpty) {
		super.initComponents(window, addEmpty);
		comboBoxDeviationFunction = getComboBoxDeviationFunction();
		comboBoxFunctionCategory = getComboBoxFunctionCategory();
		comboBoxDeviationStatus = getComboBoxDeviationStatus();
		comboBoxProductArea = getComboBoxProductArea(addEmpty);
		comboBoxMonths = getComboBoxMonths();
		comboBoxProductAreaGroup = getComboBoxProductAreaGroup();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.ExcelReportViewHandler#buildConstraintPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@SuppressWarnings("incomplete-switch")
	@Override
	public JPanel buildConstraintPanel(WindowInterface window, boolean addEmpty) {
		initComponents(window, addEmpty);
		FormLayout layout = new FormLayout(
				"p,3dlu,30dlu,3dlu,50dlu,3dlu,p,3dlu,30dlu,3dlu,p",
				"p,3dlu,p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("År:", cc.xy(1, 1));
		builder.add(yearChooser, cc.xy(3, 1));

		switch (((ExcelReportSetting) presentationModel.getBean())
				.getExcelReportType()) {
		case DEVIATION_SUMMARY:
			builder.addLabel("Måned:", cc.xy(5, 1));
			builder.add(comboBoxMonths, cc.xy(7, 1));
			builder.addLabel("Produktområde:", cc.xy(5, 3));
			builder.add(comboBoxProductArea, cc.xyw(7, 3, 1));
			break;
		case DEVIATION_SUM:
			builder.addLabel("Fra uke:", cc.xy(5, 1));
			builder.add(comboBoxWeekFrom, cc.xy(7, 1));
			builder.addLabel("Til uke:", cc.xy(9, 1));
			builder.add(comboBoxWeekTo, cc.xy(11, 1));
			builder.addLabel("Avviksfunksjon:", cc.xy(5, 3));
			builder.add(comboBoxDeviationFunction, cc.xyw(7, 3, 5));
			builder.addLabel("Kategori:", cc.xy(5, 5));
			builder.add(comboBoxFunctionCategory, cc.xyw(7, 5, 5));
			builder.addLabel("Status:", cc.xy(5, 7));
			builder.add(comboBoxDeviationStatus, cc.xyw(7, 7, 5));
			break;
		case DEVIATION_JOB_FUNCTION_SUM:
			builder.addLabel("Avviksfunksjon:", cc.xy(5, 1));
			builder.add(comboBoxDeviationFunction, cc.xy(7, 1));
			builder.addLabel("Produktområde:", cc.xy(5, 3));
			builder.add(comboBoxProductAreaGroup, cc.xy(7, 3));
			break;
		}

		return builder.getPanel();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.ExcelReportViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		switch (((ExcelReportSetting) presentationModel.getBean())
				.getExcelReportType()) {
		case DEVIATION_SUMMARY:
			return new Dimension(350, 140);
		case DEVIATION_JOB_FUNCTION_SUM:
			return new Dimension(360, 140);
		default:
			return new Dimension(380, 175);
		}
	}

	/**
	 * Håndterer endring av avviksfunksjon
	 * 
	 * @author atle.brekka
	 */
	class FunctionChangeListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			initFunctionCategoryList();

		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.ExcelReportViewHandler#checkParameters(no.ugland.utransprod.util.excel.ExcelReportSetting,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected boolean checkParameters(ExcelReportSetting setting,
			WindowInterface window) {
		String errorString = null;
		if (excelReportEnum.useFrom() && setting.getWeekFrom() == null) {
			errorString = "Det må velges fra uke";
		} else if (excelReportEnum.useTo() && setting.getWeekTo() == null) {
			errorString = "Det må velges til uke";
		} else if (setting.getExcelReportType() == null) {
			errorString = "Det må velges rapport";
		} else if (setting.getExcelReportType() == ExcelReportEnum.DEVIATION_SUMMARY
				&& ((ExcelReportSettingDeviation) setting).getProductArea() == null) {
			errorString = "Det må velges et produktområde!";
		} else if (setting.getExcelReportType() == ExcelReportEnum.DEVIATION_SUMMARY
				&& ((ExcelReportSettingDeviation) setting).getMonthEnum() == null) {
			errorString = "Det må velges en måned!";
		}
		if(setting.getExcelReportType()==ExcelReportEnum.DEVIATION_JOB_FUNCTION_SUM){
			if(((ExcelReportSettingDeviation)setting).getDeviationFunction()==null){
				errorString = "Det må velges en aviksfunksjon!";
			}else if(((ExcelReportSettingDeviation)setting).getProductAreaGroup()==null){
				errorString = "Det må velges et produktområde!";
			}
		}

		if (errorString != null) {
			Util.showErrorDialog(window, "Rapportparametre", errorString);
			return false;
		}
		return true;

	}
}
