package no.ugland.utransprod.gui.handlers;

import java.util.Map;

import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.model.OrderLine;

import com.google.inject.assistedinject.Assisted;

public interface MainPackageViewHandlerFactory {
	MainPackageViewHandler create(Map<String, String> aColliSetup,
			Map<String, StatusCheckerInterface<OrderLine>> chekers);
}
