package no.ugland.utransprod.util.excel;

import org.apache.poi.hssf.util.HSSFColor;

public enum DayEnum {
    SUNDAY(1, "Søndag", HSSFColor.AQUA.index), MONDAY(2, "Mandag",
            HSSFColor.LEMON_CHIFFON.index), TUESDAY(3, "Tirsdag",
            HSSFColor.LIGHT_GREEN.index), WEDNESDAY(4, "Onsdag",
            HSSFColor.LIGHT_TURQUOISE.index), THURSDAY(5, "Torsdag",
            HSSFColor.LIGHT_YELLOW.index), FRIDAY(6, "Fredag",
            HSSFColor.TAN.index), SATURDAY(7, "Lørdag",
            HSSFColor.LIGHT_CORNFLOWER_BLUE.index);

    private int dayNumber;

    private String dayString;

    private short colorIndex;

    private DayEnum(final int aDayNumber, final String aDayString, final short aColorIndex) {
        dayNumber = aDayNumber;
        dayString = aDayString;
        colorIndex = aColorIndex;
    }

    @Override
    public String toString() {
        return dayString;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public short getColorIndex() {
        return colorIndex;
    }

    public static DayEnum getDayString(final int aDayNumber) {
        switch (aDayNumber) {
        case 1:
            return SUNDAY;
        case 2:
            return MONDAY;
        case 3:
            return TUESDAY;
        case 4:
            return WEDNESDAY;
        case 5:
            return THURSDAY;
        case 6:
            return FRIDAY;
        case 7:
            return SATURDAY;
        default:
            throw new IllegalStateException("Dag ikke definert " + aDayNumber);
        }
    }

    public static DayEnum getDayEnum(final String day) {
        if (day.equalsIgnoreCase("Søndag")) {
            return SUNDAY;
        } else if (day.equalsIgnoreCase("Mandag")) {
            return MONDAY;
        } else if (day.equalsIgnoreCase("Tirsdag")) {
            return TUESDAY;
        } else if (day.equalsIgnoreCase("Onsdag")) {
            return WEDNESDAY;
        } else if (day.equalsIgnoreCase("Torsdag")) {
            return THURSDAY;
        } else if (day.equalsIgnoreCase("Fredag")) {
            return FRIDAY;
        } else if (day.equalsIgnoreCase("Lørdag")) {
            return SATURDAY;
        } else {
            throw new IllegalStateException("Dag " + day + " er ikke definert");
        }

    }

    public static short getDayColorIndex(final String day) {
        return getDayEnum(day).getColorIndex();
    }
}
