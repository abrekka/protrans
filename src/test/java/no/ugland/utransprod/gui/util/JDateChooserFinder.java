package no.ugland.utransprod.gui.util;

import org.fest.swing.core.GenericTypeMatcher;

import com.toedter.calendar.JDateChooser;

public class JDateChooserFinder extends GenericTypeMatcher<JDateChooser> {
	private String name;

	public JDateChooserFinder(String aName) {
		super(JDateChooser.class);
		reset(false);
		name = aName;
	}

	@Override
	protected boolean isMatching(JDateChooser dateChooser) {
		requireShowing(true);
		return name.equalsIgnoreCase(dateChooser.getName());
	}

}
