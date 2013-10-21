package no.ugland.utransprod.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.UserType;

public interface MenuBarBuilderInterface {
    void openFrame(final Viewer viewer);
    ApplicationUser getApplicationUser();
    UserType getUserType();
    JMenuBar buildMenuBar(final ProTransMain parentFrame,
            final ApplicationUser aApplicationUser,
            final UserType currentUserType);
    JMenu getMenuWindow();
	void setGarageMenu(GarageMenu garageMenu);
	void setProductionMenu(ProductionMenu productionMenu);
}