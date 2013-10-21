package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.CustomersViewHandler;
import no.ugland.utransprod.gui.model.CustomerModel;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.service.CustomerManager;

import com.google.inject.Inject;

public class CustomerAction extends AbstractAction {
	private final MenuBarBuilderInterface menuBarBuilder;
	private CustomerManager customerManager;
	private Login login;
	@Inject
	public CustomerAction(final MenuBarBuilderInterface aMenuBarBuilder,CustomerManager aCustomerManager,Login aLogin){
		super("Kunder...");
		login=aLogin;
		customerManager=aCustomerManager;
		menuBarBuilder = aMenuBarBuilder;
	}

	public void actionPerformed(ActionEvent e) {
		menuBarBuilder.openFrame(new OverviewView<Customer, CustomerModel>(
				new CustomersViewHandler(login,customerManager)));
		
	}
}
