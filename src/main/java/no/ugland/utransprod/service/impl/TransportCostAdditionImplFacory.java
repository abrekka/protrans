package no.ugland.utransprod.service.impl;

import java.util.Hashtable;
import java.util.Map;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.TransportCostAddition;
import no.ugland.utransprod.service.ITransportCostAddition;

public final class TransportCostAdditionImplFacory {
	private static Map<String, ITransportCostAddition> transportCostAdditionImplInstances = new Hashtable<String, ITransportCostAddition>();

	private TransportCostAdditionImplFacory() {

	}

	public static ITransportCostAddition getTransportCostAdditionImpl(
			final TransportCostAddition addition) throws ProTransException {
		ITransportCostAddition transportCostAdditionImpl = transportCostAdditionImplInstances
				.get(addition.getDescription());
		if (transportCostAdditionImpl == null) {
			transportCostAdditionImpl = createTransportCostAdditionImpl(
					addition.getDescription(), addition);
			transportCostAdditionImplInstances.put(addition.getDescription(),
					transportCostAdditionImpl);
		}

		return transportCostAdditionImpl;
	}

	private static ITransportCostAddition createTransportCostAdditionImpl(
			final String description, final TransportCostAddition addition)
			throws ProTransException {
		if (description.equalsIgnoreCase("BreddeX2+høydeX2")) {
			return new AdditionWidhtHeight(addition, null, "Lang garasje");
		} else if (description.equalsIgnoreCase("Takstein")) {
			return new AdditionTiles(addition, "Takstein", "Takstein");
		} else if (description.equalsIgnoreCase("Takstol")) {
			return new AdditionTruss(addition, "Takstoler", "Takstol");
		} else if (description.equalsIgnoreCase("Stående tak")) {
			return new AdditionStandingRoof(addition, null, "Stående tak");
		} else {
			throw new ProTransException("Ikke definert klasse for tillegg "
					+ addition.getDescription());
		}
	}
}
