package no.ugland.utransprod.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Tidsforbruk {

    public static BigDecimal beregnTidsforbruk(Date starttid, Date slutttid) {
	if (starttid == null || slutttid == null) {
	    return BigDecimal.ZERO;
	}
	Date arbeidsdagSlutt = hentArbeidsdagslutt(starttid);

	BigDecimal antallTimer = BigDecimal.ZERO;

	if (arbeidsdagSlutt.before(slutttid)) {
	    antallTimer = beregnTidsforbrukforenDag(starttid, arbeidsdagSlutt);
	    Date arbeidsdagStart = hentNesteArbeidsdagstart(starttid);
	    antallTimer = antallTimer.add(beregnTidsforbruk(arbeidsdagStart, slutttid));
	} else {
	    antallTimer = beregnTidsforbrukforenDag(starttid, slutttid);
	}

	return antallTimer;
    }

    private static BigDecimal beregnTidsforbrukforenDag(Date starttid, Date slutttid) {
	Date frokostSlutt = hentFrokostslutt(starttid);

	int frokost = 0;
	if (frokostSlutt.after(starttid) && frokostSlutt.before(slutttid)) {
	    frokost++;
	}

	Date lunsjSlutt = hentLunsjslutt(starttid);

	int lunsj = 0;
	if (lunsjSlutt.after(starttid) && lunsjSlutt.before(slutttid)) {
	    lunsj++;
	}

	long antallMillisekunder = slutttid.getTime() - starttid.getTime();
	antallMillisekunder = antallMillisekunder - (frokost * 1000 * 60 * 15) - (lunsj * 1000 * 60 * 30);
	long timer = TimeUnit.MILLISECONDS.toHours(antallMillisekunder);
	antallMillisekunder -= TimeUnit.HOURS.toMillis(timer);
	long minutter = TimeUnit.MILLISECONDS.toMinutes(antallMillisekunder);
	double minutterDelPaaTime = (double) minutter / 60;
	return BigDecimal.valueOf(timer + minutterDelPaaTime).setScale(2, RoundingMode.CEILING);
    }

    private static Date hentFrokostslutt(Date starttid) {
	Calendar frokostSlutt = Calendar.getInstance();
	frokostSlutt.setTime(starttid);
	frokostSlutt.set(Calendar.HOUR_OF_DAY, 9);
	frokostSlutt.set(Calendar.MINUTE, 45);
	return frokostSlutt.getTime();
    }

    private static Date hentArbeidsdagslutt(Date starttid) {
	Calendar arbeidsdagslutt = Calendar.getInstance();
	arbeidsdagslutt.setTime(starttid);
	arbeidsdagslutt.set(Calendar.HOUR_OF_DAY, 15);
	arbeidsdagslutt.set(Calendar.MINUTE, 15);
	return arbeidsdagslutt.getTime();
    }

    private static Date hentNesteArbeidsdagstart(Date tid) {
	Calendar arbeidsdagstart = Calendar.getInstance();
	arbeidsdagstart.setTime(tid);
	arbeidsdagstart.add(Calendar.DAY_OF_MONTH, 1);
	arbeidsdagstart.set(Calendar.HOUR_OF_DAY, 7);
	arbeidsdagstart.set(Calendar.MINUTE, 00);
	return arbeidsdagstart.getTime();
    }

    private static Date hentLunsjslutt(Date starttid) {
	Calendar lunsjSlutt = Calendar.getInstance();
	lunsjSlutt.setTime(starttid);
	lunsjSlutt.set(Calendar.HOUR_OF_DAY, 12);
	lunsjSlutt.set(Calendar.MINUTE, 30);
	return lunsjSlutt.getTime();
    }
}
