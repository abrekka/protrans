package no.ugland.utransprod.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import no.ugland.utransprod.util.Util;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Vindu som vises når applikasjon starter opp
 * 
 * @author abr99
 * 
 */
public class LoadView implements ActionListener {
	/**
	 * 
	 */
	private String currentVersion;

	/**
	 * 
	 */
	private JLabel labelIcon;

	/**
	 * 
	 */
	private JProgressBar progressBar;

	/**
	 * 
	 */
	private int counter = 0;

	/**
	 * 
	 */
	private String systemName;

	/**
	 * @param version
	 * @param aSystemName
	 */
	public LoadView(String version, String aSystemName) {
		currentVersion = version;
		systemName = aSystemName;
	}

	/**
	 * 
	 */
	private void initComponents() {
		labelIcon = new JLabel();
		labelIcon.setIcon(IconEnum.ICON_UGLAND.getIcon());
		progressBar = new JProgressBar();
		Timer timer = new Timer(500, this);
		timer.start();
	}

	/**
	 * @return dialog
	 */
	public JDialog buildDialog() {
		initComponents();
		FormLayout layout = new FormLayout("p,30dlu,p,30dlu,p,3dlu,p", "");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.append(labelIcon);
		builder.append("Starter " + systemName);
		builder.append("Versjon:");
		builder.append(currentVersion);
		builder.appendRow(builder.getLineGapSpec());
		builder.nextLine();
		builder.add(progressBar, cc.xyw(builder.getColumn(), builder.getRow(),
				7));

		JDialog dialog = new JDialog();
		dialog.setTitle("Produksjon og transport - Igland Garasjen AS");

		dialog.setResizable(false);

		dialog.add(builder.getPanel());
		dialog.setModal(false);
		dialog.pack();

		return dialog;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (counter >= progressBar.getMaximum()) {
			counter = 0;
		}
		progressBar.setValue(counter++);

	}

	/**
	 * For test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LoadView loadView = new LoadView("0.0.1", "test");
		JDialog dialog = loadView.buildDialog();
		Util.locateOnScreenCenter(dialog);
		dialog.setVisible(true);
	}

}
