package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.model.Order;

public interface ExternalOrderViewHandlerFactory {
	ExternalOrderViewHandler create(Order aOrder);
}
