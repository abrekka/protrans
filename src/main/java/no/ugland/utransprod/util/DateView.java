package no.ugland.utransprod.util;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.buttons.CancelListener;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

/**
 * Klasse som brukes for å vise en dialog for valg av dato.
 * 
 * @author atle.brekka
 */
public class DateView implements Closeable, CancelListener {
	private JDateChooser dateChooser;

	private JButton buttonOk;

	/**
	 * Initierer vinduskomponenter.
	 * 
	 * @param window
	 */
	private void initComponents(final WindowInterface window, Date date) {
		window.setName("DateView");
		Date defaultDate = date == null ? Calendar.getInstance().getTime() : date;
		dateChooser = new JDateChooser(defaultDate);
		buttonOk = new CancelButton(window, this, false, "Ok", IconEnum.ICON_OK, this, true);
		buttonOk.setName("ButtonOk");
	}

	/**
	 * Bygger vinduspanel.
	 * 
	 * @param window
	 * @return panel
	 */
	public final JPanel buildPanel(final WindowInterface window, Date defaultDate) {

		initComponents(window, defaultDate);
		FormLayout layout = new FormLayout("10dlu,p,3dlu,55dlu,10dlu", "10dlu,p,3dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel("Velg dato:", cc.xy(2, 2));
		builder.add(dateChooser, cc.xy(4, 2));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonOk), cc.xyw(2, 4, 3));
		return builder.getPanel();
	}

	/**
	 * Henter dato.
	 * 
	 * @return dato
	 */
	public final Date getDate() {
		return dateChooser.getDate();
	}

	/**
	 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public final boolean canClose(final String actionString, final WindowInterface window) {
		return true;
	}

	/**
	 * Gjør ingenting.
	 * 
	 * @see no.ugland.utransprod.gui.buttons.CancelListener#canceled()
	 */
	public void canceled() {

	}
}
