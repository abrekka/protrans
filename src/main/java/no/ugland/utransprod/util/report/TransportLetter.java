package no.ugland.utransprod.util.report;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.Transportable;

public interface TransportLetter {

	void generateTransportLetter(final Transportable transportable,
			final WindowInterface owner);

}
