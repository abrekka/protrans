/**
 * 
 */
package no.ugland.utransprod.gui.util;

import java.awt.Component;

import javax.swing.JButton;

import abbot.finder.Matcher;

/**
 * Hjelpeklasse til å finne komponenter ved testing
 * 
 * @author atle.brekka
 * 
 */
public class ProTransComponentMatcher implements Matcher {
	/**
	 * 
	 */
	private Class<?> clazz;

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private boolean useOnlyClass = false;

	/**
	 * 
	 */
	private boolean useOnlyName = false;

	/**
	 * @param componentClass
	 * @param componentName
	 */
	public ProTransComponentMatcher(Class<?> componentClass,
			String componentName) {
		this(componentClass, componentName, false);
	}

	/**
	 * @param componentClass
	 * @param componentName
	 * @param onlyClass
	 */
	public ProTransComponentMatcher(Class<?> componentClass,
			String componentName, boolean onlyClass) {
		this(componentClass, componentName, onlyClass, false);
	}

		/**
	 * @param componentClass
	 * @param componentName
	 * @param onlyClass
	 * @param onlyName
	 */
	public ProTransComponentMatcher(Class<?> componentClass,
			String componentName, boolean onlyClass, boolean onlyName) {
		clazz = componentClass;
		name = componentName;
		useOnlyClass = onlyClass;
		useOnlyName = onlyName;
	}

	/**
	 * @see abbot.finder.Matcher#matches(java.awt.Component)
	 */
	public boolean matches(Component c) {
		if (!useOnlyClass && !useOnlyName && c.getName() != null) {

			if (c.getClass().equals(clazz)
					&& c.getName().equalsIgnoreCase(name)) {
				return true;
			}
		} else if (useOnlyClass) {
			if (c.getClass().equals(clazz)) {
				return true;
			}
		} else if (useOnlyName) {
			if (c.getName() != null && c.getName().equalsIgnoreCase(name)) {
				return true;
			}
		} else {
			if (c instanceof JButton) {
				JButton button = (JButton) c;
				if (button.getText() != null
						&& button.getText().equalsIgnoreCase(name)) {
					return true;
				}
			}
		}
		return false;
	}

}