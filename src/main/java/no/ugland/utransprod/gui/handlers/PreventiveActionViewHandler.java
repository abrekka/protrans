package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditCommentView;
import no.ugland.utransprod.gui.edit.EditPreventiveActionView;
import no.ugland.utransprod.gui.model.ListMultilineRenderer;
import no.ugland.utransprod.gui.model.PreventiveActionCommentModel;
import no.ugland.utransprod.gui.model.PreventiveActionModel;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.model.CommentType;
import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.PreventiveAction;
import no.ugland.utransprod.model.PreventiveActionComment;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.PreventiveActionManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.CommentTypeUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.MultiColPatternFilter;
import no.ugland.utransprod.util.SuperPatternFilter;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.ReportViewer;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;

import com.google.inject.Inject;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.ArrayListModel;

/**
 * Håndterer prevantive tiltak
 * 
 * @author atle.brekka
 */
public class PreventiveActionViewHandler extends AbstractViewHandler<PreventiveAction, PreventiveActionModel> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private List<JobFunction> functionList;

    /**
     *
     */
    private ArrayListModel categoryList;

    /**
     *
     */
    final ArrayListModel commentList;

    /**
     *
     */
    // ApplicationUser applicationUser;

    /**
     *
     */
    JCheckBox checkBoxClosed;

    JCheckBox checkBoxFilterDone;

    JCheckBox checkBoxFilterOwn;

    private String userFullName;
    private Login login;

    /**
     * @param aUserType
     * @param aApplicationUser
     */
    @Inject
    public PreventiveActionViewHandler(Login aLogin, ManagerRepository managerRepository) {
	super("Korrigerende tiltak", managerRepository.getPreventiveActionManager(), aLogin.getUserType(), true);

	login = aLogin;
	functionList = new ArrayList<JobFunction>(managerRepository.getJobFunctionManager().findAll());
	categoryList = new ArrayListModel();
	commentList = new ArrayListModel();
	userFullName = login.getApplicationUser().getFullName();
    }

    public JCheckBox getCheckBoxFilterDone() {
	checkBoxFilterDone = new JCheckBox("Vis ferdige");
	checkBoxFilterDone.setSelected(false);
	checkBoxFilterDone.setName("CheckBoxFilterDone");
	checkBoxFilterDone.addActionListener(new FilterActionListener());
	return checkBoxFilterDone;
    }

    public JCheckBox getCheckBoxFilterOwn() {
	checkBoxFilterOwn = new JCheckBox("Vis egne");
	checkBoxFilterOwn.setSelected(true);
	checkBoxFilterOwn.setName("CheckBoxFilterOwn");
	checkBoxFilterOwn.addActionListener(new FilterActionListener());
	return checkBoxFilterOwn;
    }

    /**
     * Lager tekstfelt for prosjektnummer
     * 
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldProjectNr(PresentationModel presentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(presentationModel
		.getBufferedModel(PreventiveActionModel.PROPERTY_PREVENTIVE_ACTION_ID));
	textField.setName("TextFieldProjectNr");
	textField.setEnabled(false);
	return textField;
    }

    /**
     * Lager tekstfelt for prosjektleder
     * 
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldManager(PresentationModel presentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(PreventiveActionModel.PROPERTY_MANAGER));
	textField.setName("TextFieldManager");
	return textField;
    }

    /**
     * Lager tekstfelt for prosjektnavn
     * 
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldName(PresentationModel presentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(presentationModel
		.getBufferedModel(PreventiveActionModel.PROPERTY_PREVENTIVE_ACTION_NAME));
	textField.setName("TextFieldName");
	return textField;
    }

    /**
     * Lager komboboks for funkjson
     * 
     * @param presentationModel
     * @return komboboks
     */
    public JComboBox getComboBoxFunction(PresentationModel presentationModel) {
	JComboBox comboBox = new JComboBox(new ComboBoxAdapter(functionList,
		presentationModel.getBufferedModel(PreventiveActionModel.PROPERTY_JOB_FUNCTION)));
	comboBox.setName("ComboBoxFunction");
	comboBox.addItemListener(new FunctionItemListener(presentationModel));
	return comboBox;
    }

    /**
     * Lager komboboks for kategori
     * 
     * @param presentationModel
     * @return komboboks
     */
    public JComboBox getComboBoxCategory(PresentationModel presentationModel) {
	setCategoryList(presentationModel);
	JComboBox comboBox = new JComboBox(new ComboBoxAdapter((ListModel) categoryList,
		presentationModel.getBufferedModel(PreventiveActionModel.PROPERTY_FUNCTION_CATEGORY)));
	comboBox.setName("ComboBoxCategory");
	return comboBox;
    }

    /**
     * Lager tekstområde for beskrivelse
     * 
     * @param presentationModel
     * @return tekstområde
     */
    public JTextArea getTextAreaDescription(PresentationModel presentationModel) {
	JTextArea textArea = BasicComponentFactory.createTextArea(presentationModel.getBufferedModel(PreventiveActionModel.PROPERTY_DESCRIPTION));
	textArea.setName("TextAreaDescription");
	return textArea;
    }

    /**
     * Lager tekstområde for forventet resultat
     * 
     * @param presentationModel
     * @return tekstområde
     */
    public JTextArea getTextAreaExpectedOutcome(PresentationModel presentationModel) {
	JTextArea textArea = BasicComponentFactory
		.createTextArea(presentationModel.getBufferedModel(PreventiveActionModel.PROPERTY_EXPECTED_OUTCOME));
	textArea.setName("TextAreaExpectedOutcome");
	return textArea;
    }

    /**
     * Lager sjekkboks for lukket
     * 
     * @param presentationModel
     * @param window
     * @return sjekkboks
     */
    public JCheckBox getCheckBoxClosed(PresentationModel presentationModel, WindowInterface window) {
	checkBoxClosed = BasicComponentFactory.createCheckBox(
		presentationModel.getBufferedModel(PreventiveActionModel.PROPERTY_PROJECT_CLOSED_BOOLEAN), "Lukket");
	checkBoxClosed.setName("CheckBoxClosed");
	checkBoxClosed.addActionListener(new CheckBoxActionListener(window, presentationModel));
	return checkBoxClosed;
    }

    /**
     * Lager liste for kommentarer
     * 
     * @param presentationModel
     * @return liste
     */
    @SuppressWarnings("unchecked")
    public JList getListComments(PresentationModel presentationModel) {
	commentList.clear();
	commentList.addAll((List<PreventiveActionComment>) presentationModel.getBufferedValue(PreventiveActionModel.PROPERTY_COMMENTS));
	JList list = new JList(commentList);
	list.setName("ListPreventiveActionComments");
	list.setCellRenderer(new ListMultilineRenderer(55));
	return list;
    }

    /**
     * Lager knapp for å legge til kommentar
     * 
     * @param window
     * @param presentationModel
     * @return knapp
     */
    public JButton getButtonAddComment(WindowInterface window, PresentationModel presentationModel) {
	JButton button = new JButton(new AddComment(window, presentationModel));
	button.setName("AddPreventiveActionComment");
	return button;
    }

    /**
     * @param object
     * @return feilmelding
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
     */
    @Override
    public CheckObject checkDeleteObject(PreventiveAction object) {
	return null;
    }

    /**
     * @param object
     * @param presentationModel
     * @param window
     * @return feilmelding
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkSaveObject(java.lang.Object,
     *      com.jgoodies.binding.PresentationModel,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public CheckObject checkSaveObject(PreventiveActionModel object, PresentationModel presentationModel, WindowInterface window) {
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
     */
    @Override
    public String getAddRemoveString() {
	return "korrigerende tiltak";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddString()
     */
    @Override
    public String getAddString() {
	return "Nytt";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
     */
    @Override
    public String getClassName() {
	return "PreventiveAction";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
     */
    @Override
    public PreventiveAction getNewObject() {
	return new PreventiveAction();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public TableModel getTableModel(WindowInterface window) {
	setTableFilterOwnDone();
	// setTableFilterDone();

	return new PreventiveActionTableModel(objectSelectionList);
    }

    private void setTableFilterDone() {
	Filter[] filtersDone = new Filter[] { new PatternFilter("[^Ferdig]", Pattern.CASE_INSENSITIVE, 6) };
	FilterPipeline filterPipelineDone = new FilterPipeline(filtersDone);
	table.setFilters(filterPipelineDone);
    }

    private void setTableFilterOwnDone() {
	MultiColPatternFilter ownFilters = new MultiColPatternFilter(2);
	ownFilters.setFilterStr(userFullName, SuperPatternFilter.MODE.REGEX_FIND);

	Filter[] filtersOwnDone = new Filter[] { new PatternFilter("[^Ferdig]", Pattern.CASE_INSENSITIVE, 6), ownFilters };

	FilterPipeline filterPipelineOwnDone = new FilterPipeline(filtersOwnDone);
	table.setFilters(filterPipelineOwnDone);
    }

    private void setTableFilterOwn() {
	MultiColPatternFilter ownFilters = new MultiColPatternFilter(2);
	ownFilters.setFilterStr(userFullName, SuperPatternFilter.MODE.REGEX_FIND);
	Filter[] filtersOwn = new Filter[] { ownFilters };

	FilterPipeline filterPipelineOwn = new FilterPipeline(filtersOwn);
	table.setFilters(filterPipelineOwn);

    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
     */
    @Override
    public String getTableWidth() {
	return "320dlu";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
     */
    @Override
    public String getTitle() {
	return "Korrigerende tiltak";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
     */
    @Override
    public Dimension getWindowSize() {
	return new Dimension(770, 400);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
     */
    @Override
    public Boolean hasWriteAccess() {
	return UserUtil.hasWriteAccess(userType, "Prevantive tiltak");
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
     */
    @Override
    public void setColumnWidth(JXTable table) {
	// Prosjektnr
	table.getColumnExt(0).setPreferredWidth(65);
	// Prosjektnavn
	table.getColumnExt(1).setPreferredWidth(100);
	// Prosjektleder
	table.getColumnExt(2).setPreferredWidth(85);
	// Funksjon
	table.getColumnExt(3).setPreferredWidth(100);
	// Kategori
	table.getColumnExt(4).setPreferredWidth(120);
	// Åpen
	table.getColumnExt(5).setPreferredWidth(50);
	// viser ikke status
	table.getColumnExt(6).setVisible(false);

    }

    /**
     * Tabellmodell for prevantive tiltak
     * 
     * @author atle.brekka
     */
    private final class PreventiveActionTableModel extends AbstractTableAdapter {

	/**
         *
         */
	private static final long serialVersionUID = 1L;

	/**
	 * @param listModel
	 */
	public PreventiveActionTableModel(ListModel listModel) {
	    super(listModel, new String[] { "Prosjektnr", "Prosjektnavn", "Prosjektleder", "Funksjon", "Kategori", "Lukket", "Status" });
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
	    PreventiveAction preventiveAction = (PreventiveAction) getRow(rowIndex);

	    switch (columnIndex) {
	    case 0:
		return preventiveAction.getPreventiveActionId();
	    case 1:
		return preventiveAction.getPreventiveActionName();
	    case 2:
		return preventiveAction.getManager();
	    case 3:
		return preventiveAction.getJobFunction();
	    case 4:
		return preventiveAction.getFunctionCategory();
	    case 5:
		if (preventiveAction.getClosedDate() != null) {
		    return Boolean.TRUE;
		}
		return Boolean.FALSE;
	    case 6:
		if (preventiveAction.getClosedDate() != null) {
		    return "Ferdig";
		}
		return "Ikke ferdig";

	    default:
		throw new IllegalStateException("Unknown column");
	    }

	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
		return Integer.class;
	    case 1:
	    case 2:
	    case 6:
		return String.class;
	    case 3:
		return JobFunction.class;
	    case 4:
		return FunctionCategory.class;
	    case 5:
		return Boolean.class;
	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

    }

    /**
     * @param handler
     * @param object
     * @param searching
     * @return view
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getEditView(no.ugland.utransprod.gui.handlers.AbstractViewHandler,
     *      java.lang.Object, boolean)
     */
    @Override
    protected AbstractEditView<PreventiveActionModel, PreventiveAction> getEditView(
	    AbstractViewHandler<PreventiveAction, PreventiveActionModel> handler, PreventiveAction object, boolean searching) {
	if (object.getPreventiveActionId() == null) {

	    PreventiveActionComment preventiveActionComment = new PreventiveActionComment(null, login.getApplicationUser().getUserName(),
		    Util.getCurrentDate(), "Opprettet",
		    // 1,
		    object, null);
	    preventiveActionComment.addCommentType(CommentTypeUtil.getCommentType("Opprettet"));
	    object.addPreventiveActionComment(preventiveActionComment);
	} else {
	    overviewManager.lazyLoad(object, new LazyLoadEnum[][] { { LazyLoadEnum.PREVENTIVE_ACTION_COMMENTS,
		    LazyLoadEnum.PREVENTIVE_ACTION_COMMENT_COMMENT_TYPES } });
	}
	return new EditPreventiveActionView(searching, object, this);
    }

    /**
     * Setter liste med katagorier
     * 
     * @param presentationModel
     */
    void setCategoryList(PresentationModel presentationModel) {
	JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil.getBean("jobFunctionManager");
	JobFunction jobFunction = (JobFunction) presentationModel.getBufferedValue(PreventiveActionModel.PROPERTY_JOB_FUNCTION);

	jobFunctionManager.lazyLoad(jobFunction, new LazyLoadEnum[][] { { LazyLoadEnum.FUNCTION_CATEGORIES, LazyLoadEnum.NONE } });
	if (jobFunction != null) {
	    categoryList.clear();
	    categoryList.addAll(jobFunction.getFunctionCategories());

	}

    }

    /**
     * Lytter på endringer av funksjon
     * 
     * @author atle.brekka
     */
    private class FunctionItemListener implements ItemListener {
	/**
         *
         */
	private PresentationModel presentationModel;

	/**
	 * @param aPresentationModel
	 */
	public FunctionItemListener(PresentationModel aPresentationModel) {
	    presentationModel = aPresentationModel;
	}

	/**
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent arg0) {
	    setCategoryList(presentationModel);

	}

    }

    /**
     * Legger til kommentar
     * 
     * @author atle.brekka
     */
    private class AddComment extends AbstractAction {
	/**
         *
         */
	private static final long serialVersionUID = 1L;

	/**
         *
         */
	private WindowInterface window;

	/**
         *
         */
	private PresentationModel presentationModel;

	/**
	 * @param aWindow
	 * @param aPresentationModel
	 */
	public AddComment(WindowInterface aWindow, PresentationModel aPresentationModel) {
	    super("Legg til kommentar...");
	    window = aWindow;
	    presentationModel = aPresentationModel;

	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    PreventiveActionComment comment = new PreventiveActionComment();
	    comment.setUserName(login.getApplicationUser().getUserName());
	    comment.setCommentDate(Util.getCurrentDate());
	    comment.addCommentType(CommentTypeUtil.getCommentType("Bruker"));

	    CommentViewHandler preventiveActionCommentViewHandler = new CommentViewHandler(login, (PreventiveActionManager) overviewManager);
	    EditCommentView editDeviationCommentView = new EditCommentView(new PreventiveActionCommentModel(comment),
		    preventiveActionCommentViewHandler);

	    JDialog dialog = Util.getDialog(window, "Legg til kommentar", true);
	    dialog.setName("EditDeviationCommentView");
	    WindowInterface dialogWindow = new JDialogAdapter(dialog);
	    dialogWindow.add(editDeviationCommentView.buildPanel(dialogWindow));
	    dialog.pack();
	    Util.locateOnScreenCenter(dialog);
	    dialogWindow.setVisible(true);

	    if (preventiveActionCommentViewHandler.isOk()) {
		addComment(comment, presentationModel);
	    }

	}

    }

    /**
     * Legger til kommentar
     * 
     * @param comment
     * @param presentationModel
     */
    void addComment(PreventiveActionComment comment, PresentationModel presentationModel) {
	comment.setPreventiveAction(((PreventiveActionModel) presentationModel.getBean()).getObject());
	commentList.add(0, comment);
	presentationModel.setBufferedValue(PreventiveActionModel.PROPERTY_COMMENTS, commentList);
    }

    /**
     * Lytter på sjekkboks for lukket
     * 
     * @author atle.brekka
     */
    class CheckBoxActionListener implements ActionListener {
	/**
         *
         */
	private WindowInterface window;

	/**
         *
         */
	private PresentationModel presentationModel;

	/**
	 * @param aWindow
	 * @param aPresentationModel
	 */
	public CheckBoxActionListener(WindowInterface aWindow, PresentationModel aPresentationModel) {
	    window = aWindow;
	    presentationModel = aPresentationModel;
	}

	/**
	 * Viser dialog for å legge inn kommentar
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    String labelText;
	    String heading;
	    CommentType commentType;
	    if (checkBoxClosed.isSelected()) {
		commentType = CommentTypeUtil.getCommentType("Lukking");
		labelText = "Skriv kommentar til lukking";
		heading = "Lukking";
	    } else {
		commentType = CommentTypeUtil.getCommentType("Åpning");
		labelText = "Skriv kommentar til åpning";
		heading = "Åpning";
	    }
	    String commentText = Util.showInputDialog(window, heading, labelText);

	    PreventiveActionComment comment = new PreventiveActionComment();
	    comment.setUserName(login.getApplicationUser().getUserName());
	    comment.setCommentDate(Util.getCurrentDate());
	    comment.addCommentType(commentType);
	    comment.setComment(commentText);

	    addComment(comment, presentationModel);
	}

    }

    class FilterActionListener implements ActionListener {

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
	    table.clearSelection();
	    objectSelectionList.clearSelection();
	    // egne satt, ferdige ikke satt
	    if (checkBoxFilterOwn.isSelected() && !checkBoxFilterDone.isSelected()) {
		setTableFilterOwnDone();
	    }
	    // egne satt,ferdige satt
	    else if (checkBoxFilterOwn.isSelected() && checkBoxFilterDone.isSelected()) {
		setTableFilterOwn();
	    }
	    // egne ikke satt,ferdige ikke satt -> ingen filtre
	    else if (!checkBoxFilterOwn.isSelected() && !checkBoxFilterDone.isSelected()) {
		setTableFilterDone();
	    }
	    // egne ikke satt,ferdige satt
	    else if (!checkBoxFilterOwn.isSelected() && checkBoxFilterDone.isSelected()) {
		table.setFilters(null);
	    }

	    /*
	     * if (checkBoxFilterDone.isSelected()) { clearFilter(); } else {
	     * table.setFilters(filterPipelineDone);
	     */
	    table.repaint();

	    // }

	}

    }

    public int getRowCount() {
	return table.getRowCount();
    }

    @Override
    void afterSaveObject(PreventiveAction object, WindowInterface window) {
    }

    public JButton getButtonPrint(PresentationModel presentationModel) {
	JButton buttonPrint = new JButton(new PrintAction(window, presentationModel));
	buttonPrint.setIcon(IconEnum.ICON_PRINT.getIcon());
	buttonPrint.setName("ButtonPrint");
	return buttonPrint;
    }

    private class PrintAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private WindowInterface window;

	private PresentationModel presentationModel;

	/**
	 * @param aWindow
	 * @param aPresentationModel
	 */
	public PrintAction(WindowInterface aWindow, PresentationModel aPresentationModel) {
	    super("Skriv ut...");
	    presentationModel = aPresentationModel;
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    Util.runInThreadWheel(window.getRootPane(), new Printer(window, presentationModel), null);

	}
    }

    private class Printer implements Threadable {
	private WindowInterface owner;

	private PresentationModel presentationModel;

	/**
	 * @param aOwner
	 * @param aPresentationModel
	 */
	public Printer(WindowInterface aOwner, PresentationModel aPresentationModel) {
	    presentationModel = aPresentationModel;
	    owner = aOwner;
	}

	/**
	 * @see no.ugland.utransprod.util.Threadable#doWhenFinished(java.lang.Object)
	 */
	public void doWhenFinished(Object object) {
	    if (object != null) {
		Util.showErrorDialog(owner, "Feil", object.toString());
	    }
	}

	/**
	 * @see no.ugland.utransprod.util.Threadable#doWork(java.lang.Object[],
	 *      javax.swing.JLabel)
	 */
	public Object doWork(Object[] params, JLabel labelInfo) {
	    String errorMsg = null;
	    try {
		labelInfo.setText("Genererer utskrift...");
		generateReport(presentationModel);
	    } catch (ProTransException e) {
		e.printStackTrace();
		errorMsg = e.getMessage();
	    }
	    return errorMsg;
	}

	/**
	 * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
	 */
	public void enableComponents(boolean enable) {
	}

    }

    private void generateReport(PresentationModel presentationModel) throws ProTransException {
	PreventiveAction preventiveAction = ((PreventiveActionModel) presentationModel.getBean()).getObject();
	if (preventiveAction != null) {

	    ReportViewer reportViewer = new ReportViewer("Korrigerende tiltak", null);
	    // mailConfig);
	    InputStream iconStream = getClass().getClassLoader().getResourceAsStream(IconEnum.ICON_CHECKED.getIconPath());
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("checked", iconStream);
	    List<PreventiveAction> preventiveActionList = new ArrayList<PreventiveAction>();
	    preventiveActionList.add(preventiveAction);
	    reportViewer.generateProtransReportFromBeanAndShow(preventiveActionList, "Korrigerende tiltak", ReportEnum.KORRIGERENDE_TILTAK, params,
		    null, window, true);
	}
    }

}
