package no.ugland.utransprod.util;

/**
 * Klasse som håndterer Integer som ja/nei.
 * @author abr99
 */
public class YesNoInteger {
    private Integer integerValue;

    /**
     * @param value
     */
    public YesNoInteger(final int value) {
        integerValue = value;
    }

    /**
     * @param value
     */
    public YesNoInteger(final Integer value) {
        integerValue = value;
    }

    public final Integer getIntegerValue() {
        return integerValue;
    }

    /**
     * Returnerer NEI dersom verdi er 0, ingen dersom -1 ellers JA.
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        String returnString = "NEI";
        if (integerValue != null) {
            switch (integerValue) {
            case 0:
                returnString = "NEI";
                break;
            case -1:
                returnString = "ingen";
                break;
            default:
                returnString = "JA";
                break;
            }
        }
        return returnString;
    }
}
