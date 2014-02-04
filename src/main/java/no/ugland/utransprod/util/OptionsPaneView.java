package no.ugland.utransprod.util;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;

import org.jdesktop.swingx.JXList;

import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

/**
 * Brukes for å vise dialog med valg.
 * 
 * @author atle.brekka
 */
public class OptionsPaneView implements Closeable {
    JXList listOptions;

    private JButton buttonOk;

    private JButton buttonCancel;

    JCheckBox checkBoxSelectAll;

    ArrayListModel objectList;

    private SelectionInList objectSelectionList;

    List<Object> selectedObjects;

    private boolean okButton;

    private boolean useCheckBox = false;

    private JComboBox comboBoxOptions;

    boolean comboBox = false;

    private String inputLabelText;
    private String inputLabelDate;
    private String defaultValue;

    private JTextField textFieldInput;

    private JDateChooser dateChooser;
    private boolean onlyOkButton;

    /**
     * @param objects
     * @param useOkButton
     * @param checkBoxAll
     * @param useComboBox
     * @param defaultObject
     * @param aInputLabelText
     * @param useOnlyOkButton
     */
    public OptionsPaneView(final Collection<?> objects, final boolean useOkButton, final boolean checkBoxAll, final boolean useComboBox,
	    final Object defaultObject, final String aInputLabelText, final boolean useOnlyOkButton, final String defaultValue, String inputLabelDate) {
	this.defaultValue = defaultValue;
	this.inputLabelDate = inputLabelDate;
	onlyOkButton = useOnlyOkButton;
	inputLabelText = aInputLabelText;
	comboBox = useComboBox;
	if (inputLabelText == null && inputLabelDate == null) {
	    createObjectList(objects, defaultObject);
	}
	okButton = useOkButton;
	useCheckBox = checkBoxAll;
    }

    private void createObjectList(final Collection<?> objects, final Object defaultObject) {
	objectList = new ArrayListModel(objects);

	objectSelectionList = new SelectionInList((ListModel) objectList);
	if (defaultObject != null) {
	    objectSelectionList.setSelection(defaultObject);
	}
    }

    /**
     * Initierer vinduskomponenter.
     * 
     * @param window
     */
    private void initComponents(final WindowInterface window) {
	if (inputLabelText != null) {
	    textFieldInput = new JTextField();
	    textFieldInput.setName("TextFieldInput");
	    if (defaultValue != null) {
		textFieldInput.setText(defaultValue);
	    }
	} else if (inputLabelDate != null) {
	    dateChooser = new JDateChooser();
	}

	else {
	    if (comboBox) {
		comboBoxOptions = new JComboBox(new ComboBoxAdapter(objectSelectionList));
		comboBoxOptions.setName("ComboBoxOptions");
	    } else {
		listOptions = new JXList(objectList);
		listOptions.setName("ListOptions");
	    }
	}
	buttonOk = new JButton(new OkAction(window));
	buttonOk.setIcon(IconEnum.ICON_OK.getIcon());
	buttonOk.setName("ButtonOk");
	buttonCancel = new CancelButton(window, this, true);
	buttonCancel.setName("ButtonCancel");
	checkBoxSelectAll = new JCheckBox(new SelectAllAction());
    }

    /**
     * Bygger panel.
     * 
     * @param window
     * @return panel
     */
    public final JPanel buildPanel(final WindowInterface window) {
	initComponents(window);
	FormLayout layout = new FormLayout("20dlu,p,20dlu", "10dlu,p,3dlu,p,3dlu,p,5dlu");
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	if (inputLabelText != null) {
	    builder.addLabel(inputLabelText, cc.xy(2, 2));
	    builder.add(textFieldInput, cc.xy(2, 4));
	} else if (inputLabelDate != null) {
	    builder.addLabel(inputLabelDate, cc.xy(2, 2));
	    builder.add(dateChooser, cc.xy(2, 4));
	} else {
	    if (useCheckBox) {
		builder.add(checkBoxSelectAll, cc.xy(2, 2));
	    }
	    if (comboBox) {
		builder.add(comboBoxOptions, cc.xy(2, 4));
	    } else {
		builder.add(new JScrollPane(listOptions), cc.xy(2, 4));
	    }
	}
	if (onlyOkButton) {
	    builder.add(ButtonBarFactory.buildCenteredBar(buttonOk), cc.xy(2, 6));
	} else if (okButton) {
	    builder.add(ButtonBarFactory.buildCenteredBar(buttonOk, buttonCancel), cc.xy(2, 6));
	} else {
	    builder.add(ButtonBarFactory.buildCenteredBar(buttonCancel), cc.xy(2, 6));
	}

	return builder.getPanel();
    }

    /**
     * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    public final boolean canClose(final String actionString, final WindowInterface window) {
	if (inputLabelText == null) {
	    selectedObjects = null;
	    objectSelectionList.clearSelection();
	}
	return true;
    }

    /**
     * Håndterer OK.
     * 
     * @author atle.brekka
     */
    private class OkAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public OkAction(final WindowInterface aWindow) {
	    super("Ok");
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public final void actionPerformed(final ActionEvent arg0) {
	    if (!comboBox && inputLabelText == null && inputLabelDate == null) {
		Object[] selection = listOptions.getSelectedValues();
		if (selection.length != 0) {

		    selectedObjects = new ArrayList<Object>(Arrays.asList(selection));
		}
	    }
	    window.dispose();

	}
    }

    /**
     * Henter liste med valg.
     * 
     * @return valg
     */
    public final List<Object> getSelectedObjects() {
	return selectedObjects;
    }

    /**
     * Henter inntastet tekst.
     * 
     * @return tekst
     */
    public final String getInputText() {
	return textFieldInput.getText();
    }

    public final Date getInputDate() {
	return dateChooser.getDate();
    }

    /**
     * Håndterer at alle skal velges.
     * 
     * @author atle.brekka
     */
    private class SelectAllAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	public SelectAllAction() {
	    super("Velg alle");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public final void actionPerformed(final ActionEvent event) {
	    if (checkBoxSelectAll.isSelected()) {
		listOptions.addSelectionInterval(0, objectList.getSize() - 1);
	    } else {
		listOptions.removeSelectionInterval(0, objectList.getSize() - 1);
	    }

	}
    }

    /**
     * Henter valg.
     * 
     * @return valg
     */
    public final Object getSelectedObject() {
	return objectSelectionList.getSelection();
    }
}
