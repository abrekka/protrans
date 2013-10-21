package no.ugland.utransprod.util.rules;

import no.ugland.utransprod.util.rules.Craning.CraningValue;

public enum Craningbasis {
	STANDARD("STANDARD") {
		@Override
		public boolean checkRule(Craning craning) {
			CraningValue degrees = craning.getDegrees();
			CraningValue width = craning.getWidth();
			if (isStandard1(degrees, width) || isStandard2(degrees, width)
					|| isStandard3(degrees, width)) {
				return true;
			}
			return false;
		}

		private boolean isStandard3(CraningValue degrees, CraningValue width) {
			return degrees.isGreaterOrEqualTo(36)
					&& width.isGreaterOrEqualTo(500);
		}

		private boolean isStandard2(CraningValue degrees, CraningValue width) {
			return degrees.isGreaterOrEqualTo(25) && degrees.isLessThan(35)
					&& width.isGreaterOrEqualTo(560);
		}

		private boolean isStandard1(CraningValue degrees, CraningValue width) {
			return degrees.isLessThan(25) && width.isGreaterOrEqualTo(680);
		}

	},
	STANDARD_PLUSS("STANDARD_PLUSS") {
		@Override
		public boolean checkRule(Craning craning) {
			CraningValue length = craning.getLength();
			return craning.getCranings().contains(STANDARD)
					&& length.isGreaterOrEqualTo(860);
		}
	},
	LONG_HIGH("LONG_HIGH") {
		@Override
		public boolean checkRule(Craning craning) {
			CraningValue wallHeight = craning.getWallHeight();
			CraningValue brickWallHeight = craning.getBrickWallHeight();
			return craning.getLongWalls() || wallHeight.isGreaterOrEqualTo(230)
					|| brickWallHeight.isGreaterOrEqualTo(50);
		}
	},
	PORT_SUPPORT("PORT_SUPPORT") {
		@Override
		public boolean checkRule(Craning craning) {
			CraningValue portWidth = craning.getPortWidth();
			return craning.getPortSupport() && portWidth.isGreaterThan(300);
		}
	},
	ATTIC("ATTIC") {
		@Override
		public boolean checkRule(Craning craning) {
			return craning.getAttic();
		}
	},
	STANDARD_LONG_HIGH("STANDARD_LONG_HIGH") {
		@Override
		public boolean checkRule(Craning craning) {
			if (craningsContainsStandardAndLongHigh(craning)
					|| craningsContainsStandardAndAttic(craning)) {
				craning.getCranings().clear();
				return true;
			}
			return false;
		}

		private boolean craningsContainsStandardAndAttic(Craning craning) {
			return craning.getCranings().contains(Craningbasis.STANDARD)
					&& craning.getCranings().contains(Craningbasis.ATTIC);
		}

		private boolean craningsContainsStandardAndLongHigh(Craning craning) {
			return craning.getCranings().contains(Craningbasis.STANDARD)
					&& craning.getCranings().contains(Craningbasis.LONG_HIGH);
		}
	},
	STANDARD_PORT_SUPPORT("STANDARD_PORT_SUPPORT") {
		@Override
		public boolean checkRule(Craning craning) {
			if (craning.getCranings().contains(Craningbasis.STANDARD)
					&& craning.getCranings()
							.contains(Craningbasis.PORT_SUPPORT)) {
				craning.getCranings().clear();
				return true;
			}
			return false;
		}
	};

	private String ruleId;

	private Craningbasis(final String aRuleId) {
		ruleId = aRuleId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public abstract boolean checkRule(Craning craning);
}
