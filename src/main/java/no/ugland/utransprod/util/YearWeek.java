package no.ugland.utransprod.util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.jgoodies.binding.beans.Model;

/**
 * Klasse som brukes for å sette år og uke for visning av transport.
 * @author atle.brekka
 */
public class YearWeek extends Model {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_YEAR = "year";

    public static final String PROPERTY_WEEK = "week";

    private static final int WEEK_ADD = 10;

    Integer year;

    Integer week;

    public YearWeek() {
        year = Util.getCurrentYear();
        week = Util.getCurrentWeek();
    }

    public YearWeek(final Integer aYear, final Integer aWeek) {
        this.year = aYear;
        this.week = aWeek;
    }

    /**
     * @return uke
     */
    public final Integer getWeek() {
        return week;
    }

    public final void setWeek(final Integer aWeek) {
        Integer oldWeek = getWeek();
        this.week = aWeek;
        firePropertyChange(PROPERTY_WEEK, oldWeek, aWeek);
    }

    /**
     * @return år
     */
    public final Integer getYear() {
        return year;
    }

    public final void setYear(final Integer aYear) {
        Integer oldYear = getYear();
        this.year = aYear;
        firePropertyChange(PROPERTY_YEAR, oldYear, aYear);
    }

    /**
     * @return formatert år og uke
     */
    public final String getFormattetYearWeek() {
        return String.format("%1$d%2$02d", year, week);
    }

    /**
     * Henter formatert år og uke med pluss 10 på uke.
     * @return formatert år og uke
     */
    public final String getFormattetYearWeekAdd10() {
        return String.format("%1$d%2$02d", year, week + WEEK_ADD);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof YearWeek)) {
            return false;
        }
        YearWeek castOther = (YearWeek) other;
        return new EqualsBuilder().append(year, castOther.year).append(week,
                castOther.week).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(year).append(week).toHashCode();
    }

}