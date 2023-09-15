package no.ugland.utransprod.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Tidsforbruk {
	private static Map<Integer, Set<Date>> holidays;

	public static boolean erInnenforArbeidstid(Date tidsstempel) {
		return isWorkingDay(tidsstempel) && hentArbeidsdagstart(tidsstempel).before(tidsstempel)
				&& hentArbeidsdagslutt(tidsstempel).after(tidsstempel);
	}

	public static BigDecimal beregnTidsforbruk(Date starttid, Date slutttid) {
		if (starttid == null || slutttid == null || slutttid.before(starttid)) {
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

	public static BigDecimal beregnTidsforbruk(Date starttid, Date slutttid, boolean ignorerArbeidsdag) {
		if (starttid == null || slutttid == null || slutttid.before(starttid)) {
			return BigDecimal.ZERO;
		}

		if (ignorerArbeidsdag) {
			return beregnTidsforbrukUtenforArbeidsdag(starttid, slutttid);
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

	private static BigDecimal beregnTidsforbrukUtenforArbeidsdag(Date starttid, Date slutttid) {
		long antallMillisekunder = slutttid.getTime() - starttid.getTime();
		long timer = TimeUnit.MILLISECONDS.toHours(antallMillisekunder);
		antallMillisekunder -= TimeUnit.HOURS.toMillis(timer);
		long minutter = TimeUnit.MILLISECONDS.toMinutes(antallMillisekunder);
		double minutterDelPaaTime = (double) minutter / 60;
		return BigDecimal.valueOf(timer + minutterDelPaaTime).setScale(2, RoundingMode.CEILING);
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

	private static Date hentArbeidsdagstart(Date starttid) {
		Calendar arbeidsdagslutt = Calendar.getInstance();
		arbeidsdagslutt.setTime(starttid);
		arbeidsdagslutt.set(Calendar.HOUR_OF_DAY, 7);
		arbeidsdagslutt.set(Calendar.MINUTE, 00);
		return arbeidsdagslutt.getTime();
	}

	private static Date hentNesteArbeidsdagstart(Date tid) {
		Calendar arbeidsdagstart = Calendar.getInstance();
		arbeidsdagstart.setTime(tid);
		arbeidsdagstart.add(Calendar.DAY_OF_MONTH, 1);
		arbeidsdagstart.set(Calendar.HOUR_OF_DAY, 7);
		arbeidsdagstart.set(Calendar.MINUTE, 00);

		if (isWorkingDay(arbeidsdagstart.getTime())) {
			return arbeidsdagstart.getTime();
		} else {
			return hentNesteArbeidsdagstart(arbeidsdagstart.getTime());
		}

	}

	private static Date hentLunsjslutt(Date starttid) {
		Calendar lunsjSlutt = Calendar.getInstance();
		lunsjSlutt.setTime(starttid);
		lunsjSlutt.set(Calendar.HOUR_OF_DAY, 12);
		lunsjSlutt.set(Calendar.MINUTE, 30);
		return lunsjSlutt.getTime();
	}

	public static boolean isWorkingDay(Date date) {
		return isWorkingDay(dateToCalendar(date));
	}

	private static Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	private static boolean isWorkingDay(Calendar cal) {
		return cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
				&& !isHoliday(cal);
	}

	private static boolean isHoliday(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		Set<?> yearSet = getHolidaySet(year);
		for (Object aYearSet : yearSet) {
			Date date = (Date) aYearSet;
			if (checkDate(cal, dateToCalendar(date))) {
				return true;
			}
		}
		return false;
	}

	private static Set<Date> getHolidaySet(int year) {
		if (holidays == null) {
			holidays = new HashMap();
		}
		if (!holidays.containsKey(year)) {
			Set<Date> yearSet = new HashSet();

			// Add set holidays.
			yearSet.add(getDate(1, Calendar.JANUARY, year));
			yearSet.add(getDate(1, Calendar.MAY, year));
			yearSet.add(getDate(17, Calendar.MAY, year));
			yearSet.add(getDate(25, Calendar.DECEMBER, year));
			yearSet.add(getDate(26, Calendar.DECEMBER, year));

			// Add movable holidays - based on easter day.
			Calendar easterDay = dateToCalendar(getEasterDay(year));

			// Sunday before easter.
			yearSet.add(rollGetDate(easterDay, -7));

			// Thurday before easter.
			yearSet.add(rollGetDate(easterDay, -3));

			// Friday before easter.
			yearSet.add(rollGetDate(easterDay, -2));

			// Easter day.
			yearSet.add(easterDay.getTime());

			// Second easter day.
			yearSet.add(rollGetDate(easterDay, 1));

			// "Kristi himmelfart" day.
			yearSet.add(rollGetDate(easterDay, 39));

			// "Pinse" day.
			yearSet.add(rollGetDate(easterDay, 49));

			// Second "Pinse" day.
			yearSet.add(rollGetDate(easterDay, 50));

			holidays.put(year, yearSet);
		}
		return holidays.get(year);
	}

	private static Date getDate(int day, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		return cal.getTime();
	}

	private static Date getEasterDay(int year) {
		int a = year % 19;
		int b = year / 100;
		int c = year % 100;
		int d = b / 4;
		int e = b % 4;
		int f = (b + 8) / 25;
		int g = (b - f + 1) / 3;
		int h = ((19 * a) + b - d - g + 15) % 30;
		int i = c / 4;
		int k = c % 4;
		int l = (32 + (2 * e) + (2 * i) - h - k) % 7;
		int m = (a + (11 * h) + (22 * l)) / 451;
		int n = (h + l - (7 * m) + 114) / 31; // This is the month number.
		int p = (h + l - (7 * m) + 114) % 31; // This is the date minus one.

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, n - 1);
		cal.set(Calendar.DATE, p + 1);

		return cal.getTime();
	}

	private static Date rollGetDate(Calendar calendar, int days) {
		Calendar easterSunday = (Calendar) calendar.clone();
		easterSunday.add(Calendar.DATE, days);
		return easterSunday.getTime();
	}

	private static boolean checkDate(Calendar cal, Calendar other) {
		return checkDate(cal, other.get(Calendar.DATE), other.get(Calendar.MONTH));
	}

	private static boolean checkDate(Calendar cal, int date, int month) {
		return cal.get(Calendar.DATE) == date && cal.get(Calendar.MONTH) == month;
	}
}
