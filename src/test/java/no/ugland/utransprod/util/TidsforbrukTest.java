package no.ugland.utransprod.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class TidsforbrukTest {
    @Test
    public void skalBeregneArbeidstidSomGaarOverMaanedsskifteTidsberegning() throws Exception {
	Date starttid = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2014.01.31 07:00");
	Date sluttid = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2014.02.01 15:15");
	Assertions.assertThat(Tidsforbruk.beregnTidsforbruk(starttid, sluttid)).isEqualTo(BigDecimal.valueOf(15).setScale(2));
    }

    @Test
    public void skalBeregneArbeidstidSomGaarOverFlereDagerTidsberegning() throws Exception {
	Date starttid = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2014.01.01 14:00");
	Date sluttid = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2014.01.02 08:00");
	Assertions.assertThat(Tidsforbruk.beregnTidsforbruk(starttid, sluttid)).isEqualTo(BigDecimal.valueOf(2.25).setScale(2));
    }

    @Test
    public void skalIkkeTaMedFrokostEllerLunsjVedTidsberegning() throws Exception {
	Date starttid = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2014.01.01 07:00");
	Date sluttid = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2014.01.01 15:00");
	Assertions.assertThat(Tidsforbruk.beregnTidsforbruk(starttid, sluttid)).isEqualTo(BigDecimal.valueOf(7.25).setScale(2));
    }

    @Test
    public void skalIkkeTaMedLunsjVedTidsberegning() throws Exception {
	Date starttid = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2014.01.01 10:00");
	Date sluttid = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2014.01.01 15:00");
	Assertions.assertThat(Tidsforbruk.beregnTidsforbruk(starttid, sluttid)).isEqualTo(BigDecimal.valueOf(4.50).setScale(2));
    }

    @Test
    public void skalIkkeTaMedFrokostVedTidsberegning() throws Exception {
	Date starttid = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2014.01.01 07:00");
	Date sluttid = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2014.01.01 12:00");
	Assertions.assertThat(Tidsforbruk.beregnTidsforbruk(starttid, sluttid)).isEqualTo(BigDecimal.valueOf(4.75));
    }

    @Test
    public void skalBeregneTidsforbrukDersomStarterNULL() {
	Date starttid = new Date();
	Assertions.assertThat(Tidsforbruk.beregnTidsforbruk(starttid, null)).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    public void skalBeregneTidsforbruk() throws Exception {
	Date starttid = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2014.01.01 10:00");
	Date sluttid = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2014.01.01 12:00");
	Assertions.assertThat(Tidsforbruk.beregnTidsforbruk(starttid, sluttid)).isEqualTo(BigDecimal.valueOf(2.00).setScale(2));
    }
}
