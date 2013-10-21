package no.ugland.utransprod.gui.edit;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.buttons.CancelListener;
import no.ugland.utransprod.gui.buttons.RefreshButton;
import no.ugland.utransprod.gui.buttons.SaveButton;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.value.Trigger;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.DefaultValidationResultModel;

/**
 * Absrakt klasse som brukes av klasser som viser vinduer for editering
 * 
 * @author atle.brekka
 * @param <E>
 * @param <T>
 */
public abstract class AbstractEditView<E, T> implements Closeable, Updateable,
		CancelListener, EditViewable {
	/**
	 * Lagreknapp
	 */
	JButton buttonSave;

	/**
	 * Avbrytknapp
	 */
	JButton buttonCancel;

	/**
	 * Oppdater
	 */
	JButton buttonRefresh;

	JButton buttonOk;

	/**
	 * Presentasjonsklasse for editeringsvindu
	 */
	PresentationModel presentationModel;

	/**
	 * Trigger som brukes til å trigge commit eller flush av objekter
	 */
	Trigger trigger;

	/**
	 * Validerinsresultat for objekt det editeres på
	 */
	final ValidationResultModel validationResultModel = new DefaultValidationResultModel();

	/**
	 * Klasse som håndterer vindusvariable
	 */
	AbstractViewHandler<T, E> viewHandler;

	/**
	 * True dersom det skal søkes
	 */
	boolean search = false;

	/**
	 * True dersom søkeknappen er trykket, for å skille på Avbrytknapp
	 */
	boolean searchPressed = false;

	/**
	 * Gjeldende objekt som vises i vindu
	 */
	AbstractModel<T, E> currentObject;

	/**
	 * True dersom vindu er kansellert
	 */
	private boolean canceled = false;

	/**
	 * Konstruktør
	 * 
	 * @param searchDialog
	 *            true dersom det er en søkedialog
	 * @param object
	 *            objektet som det skal editeres på
	 * @param aViewHandler
	 */
	public AbstractEditView(final boolean searchDialog,
			final AbstractModel<T, E> object,
			final AbstractViewHandler<T, E> aViewHandler) {
		this(searchDialog, object, aViewHandler, null);
	}

	/**
	 * @param searchDialog
	 * @param object
	 * @param aViewHandler
	 * @param aPresentationModel
	 */
	public AbstractEditView(final boolean searchDialog,
			final AbstractModel<T, E> object,
			final AbstractViewHandler<T, E> aViewHandler,
			final PresentationModel aPresentationModel) {
		viewHandler = aViewHandler;

		trigger = new Trigger();

		if (aPresentationModel == null) {
			presentationModel = new PresentationModel(object, trigger);
		} else {
			presentationModel = aPresentationModel;
			presentationModel.setTriggerChannel(trigger);
		}

		search = searchDialog;
		currentObject = object;
	}

	/**
	 * Initierer komponenene for det spesifikke editeringsvinduet
	 * 
	 * @param aWindow
	 */
	protected abstract void initEditComponents(WindowInterface aWindow);

	/**
	 * Henter validator for gjeldende objekt
	 * 
	 * @param object
	 * @return validator
	 */
	protected abstract Validator getValidator(E object, boolean serach);

	/**
	 * Initierer validering for gjeldende komponenter
	 */
	protected abstract void initComponentAnnotations();

	/**
	 * Lager editeringspanel
	 * 
	 * @param window
	 * @return editeringspanel
	 */
	protected abstract JComponent buildEditPanel(WindowInterface window);

	/**
	 * Initierer felles komponenter
	 * 
	 * @param window
	 */
	private void initComponents(final WindowInterface window) {
		viewHandler.setWindowInterface(window);
		window.setName("Edit" + viewHandler.getClassName() + "View");
		if (search) {
			buttonSave = new JButton(new SearchAction(window));
			buttonSave.setIcon(IconEnum.ICON_SEARCH.getIcon());
			buttonSave.setName("EditSearch" + viewHandler.getClassName());
			buttonSave.setEnabled(false);
			buttonCancel = new CancelButton(window, this, false, Util
					.getGuiProperty("button.cancel.text"),
					IconEnum.ICON_CANCEL, this, true);
		} else {
			buttonSave = new SaveButton(this, window);
			buttonSave.setName("Save" + viewHandler.getClassName());
			buttonSave.setEnabled(false);
			buttonCancel = new CancelButton(window, this, true);

		}

		buttonCancel.setName("EditCancel" + viewHandler.getClassName());
		buttonRefresh = new RefreshButton(this, window);
		buttonOk = new CancelButton(window, new OkAction(), true, "Ok",
				IconEnum.ICON_OK, null, true);
		buttonOk.setName("ButtonOk");
		initEditComponents(window);

	}

	/**
	 * Oppdaterer validering
	 */
	final void updateValidationResult() {
		Validator validator = getValidator(currentObject
				.getBufferedObjectModel(presentationModel), search);
		if (validator != null) {
			ValidationResult result = validator.validate();
			validationResultModel.setResult(result);
		}
	}

	/**
	 * Initierer hendelsehåndtering
	 */
	private void initEventHandling() {
		// if (!search) {
		PropertyConnector connector = new PropertyConnector(presentationModel,
				PresentationModel.PROPERTYNAME_BUFFERING, buttonSave, "enabled");
		connector.updateProperty2();

		// }

		PropertyChangeListener handler = new ValidationUpdateHandler();
		currentObject.addBufferChangeListener(handler, presentationModel);
	}

	/**
	 * Bygger editeringsvindu
	 * 
	 * @param window
	 * @return editeringsvindu
	 */
	public final JComponent buildPanel(final WindowInterface window) {

		initComponents(window);
		updateValidationResult();
		// if (!search) {
		initComponentAnnotations();
		// }
		initEventHandling();
		// if (search) {
		window.getRootPane().setDefaultButton(buttonSave);
		// }
		return buildEditPanel(window);
	}

	/**
	 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public final boolean canClose(final String actionString,
			final WindowInterface window1) {
		boolean canClose = true;
		if (presentationModel.isBuffering()) {
			if (Util.showConfirmDialog(window1.getComponent(), "Lagre?",
					"Det er gjort endringer, skal det lagres?")) {
				canClose = false;
			}
		}
		if (canClose) {
			canceled = true;
			currentObject.modelToView();
			viewHandler.setFlushing(true);
			trigger.triggerFlush();
			viewHandler.setFlushing(false);
			viewHandler.fireClose();
		}
		return canClose;
	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doSave(no.ugland.utransprod.gui.WindowInterface)
	 */
	@SuppressWarnings("unchecked")
	public final void doSave(final WindowInterface window1) {
		if (validationResultModel.hasErrors()) {
			Util.showErrorDialog((Component) null, "Rett feil",
					"Rett alle feil før lagring!");
		} else {
			E object = currentObject.getBufferedObjectModel(presentationModel);
			CheckObject checkObject = viewHandler.checkSaveObject(object,
					presentationModel, window1);

			String msg = null;
			boolean canSave = false;
			if (checkObject != null) {
				msg = checkObject.getMsg();
			}
			if (msg == null || msg.length() == 0) {
				canSave = true;
			} else {
				canSave = handleCheckObject(window1, checkObject, msg);

			}

			if (canSave) {
				trigger.triggerCommit();
				currentObject.viewToModel();
				viewHandler.saveObject((AbstractModel) presentationModel
						.getBean(), window1);
			}
		}

	}

	private boolean handleCheckObject(WindowInterface window,
			CheckObject checkObject, String msg) {
		boolean returnValue;
		if (checkObject.canContinue()) {
			returnValue = handleCanContinue(window, msg);
		} else {
			returnValue = false;
			Util.showErrorDialog((Component) null, "Feil", msg);
		}
		return returnValue;
	}

	private boolean handleCanContinue(WindowInterface window, String msg) {
		boolean doSave = Util.showConfirmDialog(window.getComponent(),
				"Slette?", msg + " Vil du slette?");
		return doSave;
	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
	 */
	public final boolean doDelete(final WindowInterface window) {
		return true;
	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
	 */
	public final void doNew(final WindowInterface window) {
	}

	/**
	 * Klasse som håndterer oppdatering av validering når kunde endrer seg
	 * 
	 * @author atle.brekka
	 */
	final class ValidationUpdateHandler implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(final PropertyChangeEvent evt) {
			boolean doUpdate = false;
			if (evt.getNewValue() != null) {
				if (!evt.getNewValue().equals(evt.getOldValue())) {
					doUpdate = true;
				}
			}
			if (evt.getOldValue() != null) {
				if (!evt.getOldValue().equals(evt.getNewValue())) {
					doUpdate = true;
				}
			}
			if (doUpdate) {
				updateValidationResult();
			}
		}

	}

	/**
	 * Klasse som håndterer søking
	 * 
	 * @author atle.brekka
	 */
	private class SearchAction extends AbstractAction {
		private WindowInterface window;

		private static final long serialVersionUID = 1L;

		/**
		 * Konstruktør
		 * 
		 * @param aWindow
		 */
		public SearchAction(final WindowInterface aWindow) {
			super("Søk");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			if (validationResultModel.hasErrors()) {
				Util.showErrorDialog((Component) null, "Mangler søkekriteria",
						"Legg inn søkekriteria for å søke");
			} else {
				trigger.triggerCommit();
				searchPressed = true;
				window.dispose();
			}

		}
	}

	/**
	 * Sjekker om søkeknappen er trykket
	 * 
	 * @return true dersom søkeknapp er trykket
	 */
	public final boolean isSearch() {
		return searchPressed;
	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doRefresh(no.ugland.utransprod.gui.WindowInterface)
	 */
	@SuppressWarnings("unchecked")
	public final void doRefresh(final WindowInterface window) {
		viewHandler.refreshObject((E) presentationModel.getBean());
		presentationModel.triggerCommit();

	}

	/**
	 * @see no.ugland.utransprod.gui.buttons.CancelListener#canceled()
	 */
	public final void canceled() {
		canceled = true;
	}

	/**
	 * Sjekker om vindu er kansellert
	 * 
	 * @return true dersom kansellert
	 */
	public final boolean isCanceled() {
		return canceled;
	}

	class OkAction implements Closeable {

		public boolean canClose(final String actionString,
				final WindowInterface window) {
			if (validationResultModel.hasErrors()) {
				Util.showErrorDialog(window.getComponent(), "Rett feil",
						"Rett alle feil før lagring!");
				return false;
			}
			trigger.triggerCommit();
			currentObject.viewToModel();
			return true;

		}

	}

}
