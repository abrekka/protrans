package no.ugland.utransprod.util.rules;

public enum CraningEnum {
    STANDARD("STANDARD"), STANDARD_PLUSS("STANDARD_PLUSS"), LONG_HIGH(
            "LONG_HIGH"), PORT_SUPPORT("PORT_SUPPORT"), ATTIC("ATTIC"), STANDARD_LONG_HIGH(
            "STANDARD_LONG_HIGH"), STANDARD_PORT_SUPPORT(
            "STANDARD_PORT_SUPPORT");

    private String ruleId;

    private CraningEnum(final String aRuleId) {
        ruleId = aRuleId;
    }

    public String getRuleId() {
        return ruleId;
    }
}
