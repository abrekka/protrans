package no.ugland.utransprod.gui.util;

import org.fest.swing.core.GenericTypeMatcher;

import com.toedter.calendar.JYearChooser;

public class JYearChooserFinder extends GenericTypeMatcher<JYearChooser> {
	private String name;

	public JYearChooserFinder(String aName) {
		super(JYearChooser.class);
		name = aName;
	}

	@Override
	protected boolean isMatching(JYearChooser yearChooser) {
		return name.equalsIgnoreCase(yearChooser.getName())&&yearChooser.isShowing();
	}

}
