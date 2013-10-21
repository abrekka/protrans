package no.ugland.utransprod.service;

import org.apache.commons.lang.StringUtils;

public enum ArticleTypeToOrderLineSelector {
	NULL {
		@Override
		public ArticleTypeToOrderLineConverter getConverter(ManagerRepository managerRepository) {
			return ArticleTypeToOrderLineConverter.UNKNOWN;
		}
	},
	DEFAULT {
		@Override
		public ArticleTypeToOrderLineConverter getConverter(ManagerRepository managerRepository) {
			return new DefaultConverter(managerRepository);
		}
	},
	PORT {
		@Override
		public ArticleTypeToOrderLineConverter getConverter(ManagerRepository managerRepository) {
			return new PortConverter(managerRepository);
		}
	},GAVL {
		@Override
		public ArticleTypeToOrderLineConverter getConverter(ManagerRepository managerRepository) {
			return new GavlConverter(managerRepository);
		}
	},
	TAKSTOLER {
		@Override
		public ArticleTypeToOrderLineConverter getConverter(ManagerRepository managerRepository) {
			return new TakstolConverter(managerRepository);
		}
	},
	TAKSTEIN {
		@Override
		public ArticleTypeToOrderLineConverter getConverter(ManagerRepository managerRepository) {
			return new TaksteinConverter(managerRepository);
		}
	},KLEDNING {
		@Override
		public ArticleTypeToOrderLineConverter getConverter(ManagerRepository managerRepository) {
			return new KledningConverter(managerRepository);
		}
	};
	public abstract ArticleTypeToOrderLineConverter getConverter(ManagerRepository managerRepository);

	public static ArticleTypeToOrderLineSelector getConverter(String articleName) {
		try {
			return ArticleTypeToOrderLineSelector.valueOf(StringUtils
					.upperCase(articleName));
		} catch (IllegalArgumentException e) {
			return DEFAULT;
		}
	}
}
