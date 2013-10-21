package no.ugland.utransprod.gui.util;

import org.fest.swing.core.GenericTypeMatcher;
import org.jdesktop.swingx.JXTreeTable;

public class JXTreeTableFinder extends GenericTypeMatcher<JXTreeTable> {
	private String name;

	public JXTreeTableFinder(String aName) {
		super(JXTreeTable.class);
		name = aName;
	}

	@Override
	protected boolean isMatching(JXTreeTable treeTable) {
		return name.equalsIgnoreCase(treeTable.getName())&&treeTable.isShowing();
	}

}
