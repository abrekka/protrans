package no.ugland.utransprod.gui.handlers;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;

import no.ugland.utransprod.gui.AttributeView;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditExternalAttributeView;
import no.ugland.utransprod.gui.model.ExternalOrderLineModel;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.ExternalOrderLine;
import no.ugland.utransprod.model.ExternalOrderLineAttribute;
import no.ugland.utransprod.service.AttributeChoiceManager;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import com.google.inject.Inject;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Håndterer eksterne orderlinjer
 * 
 * @author atle.brekka
 * 
 */
public class ExternalOrderLineViewHandler extends
		AbstractViewHandlerShort<ExternalOrderLine, ExternalOrderLineModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	JXTable tableExternalAttributes;

	/**
	 * 
	 */
	ArrayListModel externalAttributeList;

	/**
	 * 
	 */
	SelectionInList externalAttributeSelectionList;

	/**
	 * 
	 */
	JButton buttonEditAttribute;

	/**
	 * 
	 */
	JButton buttonRemoveAttribute;

	
	/*
	 * private AttributeManager attributeManager; private AttributeChoiceManager
	 * attributeChoiceManager;
	 */
	private ManagerRepository managerRepository;
	private Login login;

	/**
	 * @param userType
	 */
	@Inject
	public ExternalOrderLineViewHandler(Login aLogin,ManagerRepository aManagerRepository) {
		super("Bestllingslinjer", aManagerRepository.getExternalOrderLineManager(), false, aLogin.getUserType(),
				true);
		managerRepository=aManagerRepository;
		login=aLogin;
		
		/*
		 * attributeManager =aAttributeManager; attributeChoiceManager
		 * =aAttributeChoiceManager;
		 */
	}

	/**
	 * Lager tekstfelt for navn
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldName(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel
				.getModel(ExternalOrderLineModel.PROPERTY_ARTICLE_NAME));
	}

	/**
	 * Lager tekstfelt for antall
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldNumberOfItems(
			PresentationModel presentationModel) {
		DecimalFormat format = new DecimalFormat();
		format.setDecimalSeparatorAlwaysShown(false);
		format.setParseBigDecimal(true);
		return BasicComponentFactory.createFormattedTextField(presentationModel
				.getModel(ExternalOrderLineModel.PROPERTY_NUMBER_OF_ITEMS),
				format);

	}

	/**
	 * Lager avbrytknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonCancel(WindowInterface window) {
		return new CancelButton(window, this, false, "Ok", IconEnum.ICON_OK,
				null, true);
	}

	/**
	 * Lager knapp for å legge til attributt
	 * 
	 * @param presentationModel
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonAddAttribute(PresentationModel presentationModel,
			WindowInterface window) {
		return new JButton(new AddAttributeAction(presentationModel, window));
	}

	/**
	 * Lager knapp for å editere attributt
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonEditAttribute(WindowInterface window) {
		buttonEditAttribute = new JButton(new EditAttributeAction(window));
		buttonEditAttribute.setEnabled(false);
		return buttonEditAttribute;
	}

	/**
	 * Lager knapp for å slette attributt
	 * 
	 * @return knapp
	 */
	public JButton getButtonRemoveAttribute() {
		buttonRemoveAttribute = new JButton(new RemoveAttributeAction());
		buttonRemoveAttribute.setEnabled(false);
		return buttonRemoveAttribute;
	}

	/**
	 * Lager tabell for attributter
	 * 
	 * @param presentationModel
	 * @return tabell
	 */
	@SuppressWarnings("unchecked")
	public JXTable getTableExternalAttributes(
			PresentationModel presentationModel) {
		Set<ExternalOrderLineAttribute> externalOrderLineAttributes = (Set<ExternalOrderLineAttribute>) presentationModel
				.getValue(ExternalOrderLineModel.PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTES);
		externalAttributeList = new ArrayListModel();
		if (externalOrderLineAttributes != null) {
			externalAttributeList.addAll(externalOrderLineAttributes);
		}
		externalAttributeSelectionList = new SelectionInList(
				(ListModel) externalAttributeList);
		externalAttributeSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				new EmptySelectionHandler());

		tableExternalAttributes = new JXTable();
		tableExternalAttributes.setModel(new ExternalAttributeTableModel(
				externalAttributeList));
		tableExternalAttributes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableExternalAttributes
				.setSelectionModel(new SingleListSelectionAdapter(
						externalAttributeSelectionList
								.getSelectionIndexHolder()));
		tableExternalAttributes.setColumnControlVisible(true);

		/*
		 * tableExternalAttributes.setHighlighters(new HighlighterPipeline( new
		 * Highlighter[] { new AlternateRowHighlighter() }));
		 */
		tableExternalAttributes.addHighlighter(HighlighterFactory
				.createAlternateStriping());
		tableExternalAttributes.getColumnExt(0).setPreferredWidth(105);
		return tableExternalAttributes;
	}

	/**
	 * Tabellmodell for attributter
	 * 
	 * @author atle.brekka
	 * 
	 */
	public static final class ExternalAttributeTableModel extends
			AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private static final String[] COLUMNS = { "Navn", "Verdi" };

		/**
		 * @param listModel
		 */
		public ExternalAttributeTableModel(ListModel listModel) {
			super(listModel, COLUMNS);
		}

		/**
		 * Henter verdi
		 * 
		 * @param rowIndex
		 * @param columnIndex
		 * @return verdi
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			ExternalOrderLineAttribute externalOrderLineAttribute = (ExternalOrderLineAttribute) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return externalOrderLineAttribute
						.getExternalOrderLineAttributeName();
			case 1:
				return externalOrderLineAttribute
						.getExternalOrderLineAttributeValue();
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
			case 1:
				return String.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	/**
	 * Legg til attributt
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class AddAttributeAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private PresentationModel presentationModel;

		/**
		 * 
		 */
		private WindowInterface window;

		/**
		 * @param aPresentationModel
		 * @param aWindow
		 */
		public AddAttributeAction(PresentationModel aPresentationModel,
				WindowInterface aWindow) {
			super("Ny attributt...");

			window = aWindow;
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0) {
			Set<ExternalOrderLineAttribute> attributes = (Set<ExternalOrderLineAttribute>) presentationModel
					.getBufferedValue(ExternalOrderLineModel.PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTES);

			AttributeViewHandler attributeViewHandler = new AttributeViewHandler(login,managerRepository);// ,
																		// attributes);
			AttributeView attributeView = new AttributeView(
					attributeViewHandler, true);
			WindowInterface dialog = new JDialogAdapter(new JDialog(
					ProTransMain.PRO_TRANS_MAIN, "Attributt", true));
			dialog.setName("AttributeView");
			dialog.add(attributeView.buildPanel(dialog));
			dialog.pack();
			Util.locateOnScreenCenter(dialog);
			dialog.setVisible(true);

			List<Attribute> newAttributes = attributeView.getSelectedObjects();
			if (newAttributes != null && newAttributes.size() == 1) {

				Attribute att = newAttributes.get(0);
				Object[] selectionValues = null;
				List<String> choices = att.getChoices();
				if (choices != null && choices.size() != 0) {
					selectionValues = choices.toArray();
				} else if (att.getYesNo() != null && att.getYesNo() > 0) {
					selectionValues = new Object[] { "Ja", "Nei" };
				}
				String attributeValue = (String) JOptionPane.showInputDialog(
						window.getComponent(), "Gi attributt verdi",
						"Attributt", JOptionPane.PLAIN_MESSAGE, null,
						selectionValues, null);

				if (attributeValue == null || attributeValue.length() == 0) {
					Util.showErrorDialog(window.getComponent(), "Feil",
							"Attributt må ha verdi");
				} else {
					ExternalOrderLine externalOrderLine = ((ExternalOrderLineModel) presentationModel
							.getBean()).getObject();
					ExternalOrderLineAttribute externalOrderLineAttribute = new ExternalOrderLineAttribute(
							null, externalOrderLine, attributeValue, att
									.getName());
					attributes.add(externalOrderLineAttribute);
					externalAttributeList.add(externalOrderLineAttribute);
				}
			}

		}
	}

	/**
	 * Fjern attributt
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class RemoveAttributeAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public RemoveAttributeAction() {
			super("Fjern attributt");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
		}
	}

	/**
	 * Editer attributt
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class EditAttributeAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public EditAttributeAction(WindowInterface aWindow) {
			super("Editer attributt...");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			ExternalOrderLineAttribute attribute = (ExternalOrderLineAttribute) externalAttributeSelectionList
					.getElementAt(tableExternalAttributes
							.convertRowIndexToModel(externalAttributeSelectionList
									.getSelectionIndex()));

			if (attribute != null) {
				ExternalOrderLineAttributeViewHandler externalAttributeViewHandler = new ExternalOrderLineAttributeViewHandler(
						userType);
				EditExternalAttributeView externalAttributeView = new EditExternalAttributeView(
						attribute, externalAttributeViewHandler);

				JDialog dialog = Util.getDialog(window, "Attributt", true);
				WindowInterface dialogWindow = new JDialogAdapter(dialog);
				dialog.add(externalAttributeView.buildPanel(dialogWindow));
				dialog.pack();
				Util.locateOnScreenCenter(dialog);
				dialog.setVisible(true);
			}

		}
	}

	/**
	 * Håndterer selektering av attributter
	 * 
	 * @author atle.brekka
	 * 
	 */
	class EmptySelectionHandler implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent arg0) {
			boolean hasSelection = externalAttributeSelectionList
					.hasSelection();
			buttonEditAttribute.setEnabled(hasSelection);
			buttonRemoveAttribute.setEnabled(hasSelection);

		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Bestillinger");
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
	protected AbstractEditView<ExternalOrderLineModel, ExternalOrderLine> getEditView(
			AbstractViewHandler<ExternalOrderLine, ExternalOrderLineModel> handler,
			ExternalOrderLine object, boolean searching) {
		return null;
	}

	@Override
	public CheckObject checkDeleteObject(ExternalOrderLine object) {
		return null;
	}
}
