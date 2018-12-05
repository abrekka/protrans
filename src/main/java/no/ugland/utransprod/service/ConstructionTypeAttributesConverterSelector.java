package no.ugland.utransprod.service;

public enum ConstructionTypeAttributesConverterSelector {
	GARASJE {
		@Override
		public ConstructionTypeAttributesConverter getConverter(OrdlnManager ordlnManager,
				CostTypeManager costTypeManager,CostUnitManager costUnitManager) {
			return new GarasjeConverter(ordlnManager,costTypeManager,costUnitManager);
		}
	},
	TAKSTOL {
		@Override
		public ConstructionTypeAttributesConverter getConverter(OrdlnManager ordlnManager,
				CostTypeManager costTypeManager,CostUnitManager costUnitManager) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	BYGGELEMENT {
		@Override
		public ConstructionTypeAttributesConverter getConverter(OrdlnManager ordlnManager,
				CostTypeManager costTypeManager,CostUnitManager costUnitManager) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	public abstract ConstructionTypeAttributesConverter getConverter(OrdlnManager ordlnManager,
			CostTypeManager costTypeManager,CostUnitManager costUnitManager);
}
