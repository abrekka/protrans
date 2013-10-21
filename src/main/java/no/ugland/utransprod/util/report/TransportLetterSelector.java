package no.ugland.utransprod.util.report;

import no.ugland.utransprod.service.ManagerRepository;

public enum TransportLetterSelector {
	GARASJE {
		@Override
		public TransportLetter getTransportLetter(
				ManagerRepository managerRepository) {
			return new GarageTransportLetter(managerRepository);
		}
	},
	TAKSTOL {
		@Override
		public TransportLetter getTransportLetter(
				ManagerRepository managerRepository) {
			return new TrossTransportLetter(managerRepository);
		}
	},
	BYGGELEMENT {
		@Override
		public TransportLetter getTransportLetter(
				ManagerRepository managerRepository) {
			return new TrossTransportLetter(managerRepository);
		}
	};
	public abstract TransportLetter getTransportLetter(
			ManagerRepository managerRepository);
}
