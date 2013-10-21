package no.ugland.utransprod.gui;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

public abstract class ProTransMenu {
	private Login login;
	
	@Inject
	public ProTransMenu(final Login aLogin){
		login = aLogin;
	}
	public abstract JMenu buildMenu();
	protected JMenu addMenu(final String menuText, final int keyEvent) {
		JMenu menu = new JMenu(menuText);
		menu.setMnemonic(keyEvent);
		return menu;
	}
	protected JMenuItem addMenuItem(final JMenu menu,
			final Action menuItemAction, final int keyEvent,
			final KeyStroke accelerator, final IconEnum iconEnum,
			final String actionCommand, final boolean adminOnly) {
		return addMenuItem(menu, menuItemAction, keyEvent, accelerator,
				iconEnum, actionCommand, null, false, adminOnly);
	}
	protected JMenuItem addMenuItem(final JMenu menu,
			final Action menuItemAction, final int keyEvent,
			final KeyStroke accelerator, final IconEnum iconEnum,
			final String actionCommand, final String windowName,
			final boolean writeAccess) {
		return addMenuItem(menu, menuItemAction, keyEvent, accelerator,
				iconEnum, actionCommand, windowName, writeAccess, false);
	}
	
	
	protected JMenuItem addMenuItem(final JMenu menu,
			final Action menuItemAction, final int keyEvent,
			final KeyStroke accelerator, final IconEnum iconEnum,
			final String actionCommand, final String windowName,
			final boolean writeAccess, final boolean adminOnly) {
		JMenuItem menuItem = null;
		if(menuItemAction!=null){
		menuItem = new JMenuItem(menuItemAction);

		menuItem.setName("MenuItem" + menuItemAction.getValue(Action.NAME));
		setKeyEvent(keyEvent, menuItem);

		setAccelerator(accelerator, menuItem);

		setIcon(iconEnum, menuItem);

		setActionCommand(actionCommand, menuItem);

		menu.add(menuItem);
		menuItem.setEnabled(false);

		handleAdmin(windowName, writeAccess, adminOnly, menuItem);
		}
		return menuItem;
	}
	
	private void handleAdmin(final String windowName,
			final boolean writeAccess, final boolean adminOnly,
			JMenuItem menuItem) {
		if (adminOnly) {
			enableIfAdmin(menuItem);
		} else {

			enableIfWriteAccess(windowName, writeAccess, menuItem);
		}
	}

	private void enableIfWriteAccess(final String windowName,
			final boolean writeAccess, JMenuItem menuItem) {
		if (windowName != null) {
			if (writeAccess) {
				enableWritwAccess(windowName, menuItem);
			} else {
				enableAccess(windowName, menuItem);
			}
		} else {
			menuItem.setEnabled(true);

		}
	}

	private void enableAccess(final String windowName, JMenuItem menuItem) {
		if (UserUtil.hasAccess(login.getUserType(), windowName)) {
			menuItem.setEnabled(true);
		}
	}

	private void enableWritwAccess(final String windowName, JMenuItem menuItem) {
		if (UserUtil.hasWriteAccess(login.getUserType(), windowName)) {
			menuItem.setEnabled(true);
		}
	}

	private void enableIfAdmin(JMenuItem menuItem) {
		if (Util.convertNumberToBoolean(login.getUserType().getIsAdmin())) {
			menuItem.setEnabled(true);
		}
	}

	private void setActionCommand(final String actionCommand, JMenuItem menuItem) {
		if (actionCommand != null) {
			menuItem.setActionCommand(actionCommand);
		}
	}

	private void setIcon(final IconEnum iconEnum, JMenuItem menuItem) {
		if (iconEnum != null) {
			menuItem.setIcon(iconEnum.getIcon());
		}
	}

	private void setAccelerator(final KeyStroke accelerator, JMenuItem menuItem) {
		if (accelerator != null) {
			menuItem.setAccelerator(accelerator);
		}
	}

	private void setKeyEvent(final int keyEvent, JMenuItem menuItem) {
		if (keyEvent != -1) {
			menuItem.setMnemonic(keyEvent);
		}
	}


}
