package no.ugland.utransprod.service;

public enum ConstructionTypeAttributesConverterSelector {
	GARASJE {
		@Override
		public ConstructionTypeAttributesConverter getConverter(OrdlnManager ordlnManager) {
			return new GarasjeConverter(ordlnManager);
		}
	},TAKSTOL {
		@Override
		public ConstructionTypeAttributesConverter getConverter(
				OrdlnManager ordlnManager) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	BYGGELEMENT {
		@Override
		public ConstructionTypeAttributesConverter getConverter(
				OrdlnManager ordlnManager) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	public abstract ConstructionTypeAttributesConverter getConverter(OrdlnManager ordlnManager);
}
