package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.gui.WindowInterface;

public interface ShowTakstolInfoActionFactory {
	ShowTakstolInfoAction create(OrderNrProvider aProduceableProvider,WindowInterface window);
}
