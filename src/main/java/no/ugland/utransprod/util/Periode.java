package no.ugland.utransprod.util;

import java.text.ParseException;
import java.util.Date;

public class Periode extends YearWeek {
    public static final String PROPERTY_TO_WEEK = "toWeek";

    private Integer toWeek;

    public Periode() {
        super();
        toWeek = Util.getCurrentWeek();
    }

    public Periode(final Integer aYear, final Integer aWeek,
            final Integer aToWeek) {
        super(aYear, aWeek);
        this.toWeek = aToWeek;
    }

    public final Integer getToWeek() {
        return toWeek;
    }

    public final void setToWeek(final Integer aToWeek) {
        Integer oldToWeek = getToWeek();
        this.toWeek = aToWeek;
        firePropertyChange(PROPERTY_TO_WEEK, oldToWeek, aToWeek);
    }

    public final String getFormattetYearFromWeekToWeek() {
        return String.format("%1$d %2$02d-%3$02d", year, week, toWeek);
    }
    
    public final String getFormattetYearFromWeek() {
        return String.format("%1$d%2$02d", year, week);
    }
    public final String getFormattetYearToWeek() {
        return String.format("%1$d%2$02d", year, toWeek);
    }

    public Date getStartDate(){
            return Util.getFirstDateInWeek(getYear(), getWeek());
    }
    public Date getEndDate(){
        return Util.getLastDateInWeek(getYear(), getToWeek());
    }
    
    public static void main(String[] args){
        Periode test = new Periode(2008,50,50);
        
        System.out.println(test.getFormattetYearFromWeek());
        System.out.println(test.getFormattetYearToWeek());
        
        System.out.println(test.getStartDate());
        System.out.println(test.getEndDate());
        
        System.out.println(Util.convertDateToInt(test.getStartDate()));
        System.out.println(Util.convertDateToInt(test.getEndDate()));
    }
}
