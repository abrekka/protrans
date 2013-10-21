package no.ugland.utransprod.gui.handlers;

import com.google.inject.assistedinject.Assisted;

public interface ConstructionTypeViewHandlerFactory {
	ConstructionTypeViewHandler create(boolean isMasterDialog,boolean masterOverview);
}
