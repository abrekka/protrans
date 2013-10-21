package no.ugland.utransprod.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum for måneder.
 * @author atle.brekka
 */
public enum MonthEnum {
    JANUARY(1, "Januar"), FEBRUARY(2, "Februar"), MARCH(3, "Mars"), APRIL(4,
            "April"), MAY(5, "Mai"), JUNE(6, "Juni"), JULY(7, "Juli"), AUGUST(
            8, "August"), SEPTEMBER(9, "September"), OCTOBER(10, "Oktober"), NOVEMBER(
            11, "November"), DECEMBER(12, "Desember");

    private Integer month;

    private String monthString;

    /**
     * @param aMont
     * @param aMonthString
     */
    private MonthEnum(final Integer aMont, final String aMonthString) {
        month = aMont;
        monthString = aMonthString;
    }

    /**
     * @return måned
     */
    public final Integer getMonth() {
        return month;
    }

    /**
     * @return månednavn
     */
    public final String getMonthString() {
        return monthString;
    }

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public final String toString() {
        return monthString;
    }

    /**
     * Lager liste med alle måneder.
     * @return måneder
     */
    public static List<MonthEnum> getMonthList() {
        List<MonthEnum> list = new ArrayList<MonthEnum>();
        list.add(JANUARY);
        list.add(FEBRUARY);
        list.add(MARCH);
        list.add(APRIL);
        list.add(JUNE);
        list.add(JULY);
        list.add(AUGUST);
        list.add(SEPTEMBER);
        list.add(OCTOBER);
        list.add(NOVEMBER);
        list.add(DECEMBER);
        return list;
    }
}
