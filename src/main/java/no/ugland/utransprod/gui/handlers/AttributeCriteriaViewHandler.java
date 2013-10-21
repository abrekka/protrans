package no.ugland.utransprod.gui.handlers;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.model.OrderLineAttributeCriteria;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;

/**
 * Hjelpeklasse for å sette verdier på utvalg som skal gjøres på attributter
 * 
 * @author atle.brekka
 * 
 */
public class AttributeCriteriaViewHandler implements Closeable {

	/**
	 * Lager komboboks med ja nei for om attributt skal være boolsk eller ikke
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JCheckBox getCheckBoxYesNo(PresentationModel presentationModel) {
		if (presentationModel != null) {
			return BasicComponentFactory
					.createCheckBox(
							presentationModel
									.getModel(OrderLineAttributeCriteria.PROPERTY_ATTRIBUTE_VALUE_BOOLEAN),
							"");
		}
		return new JCheckBox();
	}

	/**
	 * Lager tekstfelt for fra verdi
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldFrom(PresentationModel presentationModel) {
		return BasicComponentFactory
				.createTextField(presentationModel
						.getModel(OrderLineAttributeCriteria.PROPERTY_ATTRIBUTE_VALUE_FROM));
	}

	/**
	 * Lager tekstfelt for til verdi
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldTo(PresentationModel presentationModel) {
		return BasicComponentFactory
				.createTextField(presentationModel
						.getModel(OrderLineAttributeCriteria.PROPERTY_ATTRIBUTE_VALUE_TO));
	}

	/**
	 * Lager ok-knapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonOk(WindowInterface window) {
		return new CancelButton(window, this, false, "Ok", IconEnum.ICON_OK,
				null, true);
	}

	/**
	 * Lager label for attributtnavn
	 * 
	 * @param presentstionModel
	 * @return label
	 */
	public JLabel getLabelAttribute(PresentationModel presentstionModel) {
		return BasicComponentFactory.createLabel(presentstionModel
				.getModel(OrderLineAttributeCriteria.PROPERTY_ATTRIBUTE_NAME));
	}

	/**
	 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public boolean canClose(String actionString, WindowInterface window) {
		return true;
	}
}
