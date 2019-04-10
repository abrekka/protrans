package no.ugland.utransprod.gui.edit;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;

import org.apache.commons.lang.StringUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

public class EditPacklistView implements Closeable {
	private JButton buttonOk;
	private JButton buttonCancel;
	private JDateChooser dateChooser;
	boolean canceled = false;
	private JTextField textfieldTime;
	private JTextField textfieldDoneBy;
	private Login login;
	private boolean skalKunneSettedato;
	private String tidsbruk;
	private String defaultValue;

	public EditPacklistView(Login login, boolean skalKunneSettedato, BigDecimal tidsbruk, String defaultValue) {
		this.defaultValue = defaultValue;
		this.login = login;
		this.skalKunneSettedato = skalKunneSettedato;
		this.tidsbruk = tidsbruk == null ? null : String.valueOf(tidsbruk);
	}

	public JPanel buildPanel(WindowInterface window) {
		window.setName("EditPacklist");
		initComponents(window);
		FormLayout layout = new FormLayout("10dlu,60dlu,3dlu,60dlu,10dlu", "10dlu,p,3dlu,p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		if (skalKunneSettedato) {
			builder.addLabel("Dato:", cc.xy(2, 2));
			builder.add(dateChooser, cc.xy(4, 2));
		}
		builder.addLabel("Tidsbruk:", cc.xy(2, 4));
		builder.add(textfieldTime, cc.xy(4, 4));
		builder.addLabel("Gjort av:", cc.xy(2, 6));
		builder.add(textfieldDoneBy, cc.xy(4, 6));

		builder.add(ButtonBarFactory.buildCenteredBar(buttonOk, buttonCancel), cc.xyw(1, 8, 5));

		return builder.getPanel();
	}

	private void initComponents(WindowInterface window) {
		textfieldDoneBy = new JTextField(
				defaultValue == null ? login.getApplicationUser().getFullName() : defaultValue);
		textfieldTime = new JTextField(tidsbruk == null ? "" : tidsbruk);
		dateChooser = new JDateChooser(Calendar.getInstance().getTime());
		buttonOk = getButtonOk(window);
		buttonCancel = getButtonCancel(window);

	}

	public JButton getButtonCancel(WindowInterface window) {
		JButton button = new CancelButton(window, new CloseAction(), true);
		button.setName("ButtonCancel");
		return button;
	}

	public JButton getButtonOk(WindowInterface window) {
		JButton button = new CancelButton(window, this, true, "Ok", IconEnum.ICON_OK, null, true);
		button.setName("ButtonOk");
		return button;
	}

	public boolean canClose(String actionString, WindowInterface window) {
		return true;
	}

	class CloseAction implements Closeable {

		/**
		 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
		 *      no.ugland.utransprod.gui.WindowInterface)
		 */
		public boolean canClose(String actionString, WindowInterface window) {
			canceled = true;
			return true;
		}

	}

	public boolean isCanceled() {
		return canceled;
	}

	public Date getPacklistDate() {
		return dateChooser.getDate();
	}

	public BigDecimal getPacklistDuration() {
		String durationString = textfieldTime.getText();
		BigDecimal duration;
		try {
			duration = StringUtils.isBlank(durationString) ? null
					: new BigDecimal(StringUtils.replace(durationString, ",", "."));
		} catch (NumberFormatException e) {
			duration = null;
		}
		return duration;
	}

	public String getDoneBy() {
		return textfieldDoneBy.getText();
	}
}
