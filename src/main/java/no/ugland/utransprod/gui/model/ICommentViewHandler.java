package no.ugland.utransprod.gui.model;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;

import com.jgoodies.binding.PresentationModel;

/**
 * Interface for klasser som h�ndterer kommentarer
 * 
 * @author atle.brekka
 * 
 */
public interface ICommentViewHandler {
	/**
	 * Lager tekstfelt for brukernavn
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	JTextField getTextFieldUserName(PresentationModel presentationModel);

	/**
	 * Lager tekstomr�de for kommentar
	 * 
	 * @param presentationModel
	 * @return tekstomr�de
	 */
	JTextArea getTextAreaComment(PresentationModel presentationModel);

	/**
	 * Lager avbrytknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	JButton getButtonCancel(WindowInterface window);

	/**
	 * Lager ok-knapp
	 * 
	 * @param window
	 * @return knapp
	 */
	JButton getButtonOk(WindowInterface window);

	/**
	 * Lager sjekkboks for transport
	 * 
	 * @param presentationModel
	 * @return sjekkboks
	 */
	JCheckBox getCheckBoxTransport(PresentationModel presentationModel);
}
