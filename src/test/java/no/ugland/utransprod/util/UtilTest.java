package no.ugland.utransprod.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.test.FastTests;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
@Category(FastTests.class)
public class UtilTest extends TestCase {
	

	@Test
	public void skalHaandtereNull() {
		assertNull(Util.getLastFridayAsString(null, null));
	}

	@Test
	public void skalHenteSisteFredagSomStreng() {
		assertEquals("20111007", Util.getLastFridayAsString(2011, 41));
		assertEquals("20101231", Util.getLastFridayAsString(2011, 1));
	}

	@Test
	public void isAfter_200922_200917() {
		YearWeek yearWeek1 = new YearWeek(2009, 22);
		YearWeek yearWeek2 = new YearWeek(2009, 17);

		assertEquals(true, Util.isAfter(yearWeek1, yearWeek2));
	}

	@Test
	public void isAfter_200917_200922() {
		YearWeek yearWeek1 = new YearWeek(2009, 17);
		YearWeek yearWeek2 = new YearWeek(2009, 22);

		assertEquals(false, Util.isAfter(yearWeek1, yearWeek2));
	}

	@Test
	public void isAfter_200922_200922() {
		YearWeek yearWeek1 = new YearWeek(2009, 22);
		YearWeek yearWeek2 = new YearWeek(2009, 22);

		assertEquals(false, Util.isAfter(yearWeek1, yearWeek2));
	}

	@Test
	public void isAfter_nullnull_200922() {
		YearWeek yearWeek1 = new YearWeek(0, 0);
		YearWeek yearWeek2 = new YearWeek(2009, 22);

		assertEquals(false, Util.isAfter(yearWeek1, yearWeek2));
	}

	@Test
	public void convertStringToBigDecimalNull() {
		BigDecimal number = Util.convertStringToBigDecimal(null);
		assertNull(number);
	}

	@Test
	public void convertStringToBigDecimalEmpty() {
		BigDecimal number = Util.convertStringToBigDecimal("");
		assertNotNull(number);
		assertEquals(BigDecimal.valueOf(0), number.setScale(0));
	}

	@Test
	public void convertBigDecimalToStringWithoutDecimals() {
		BigDecimal number = BigDecimal.valueOf(100);
		assertEquals("100", Util.convertBigDecimalToString(number));
	}

	@Test
	public void convertBigDecimalToStringWithDecimals() {
		BigDecimal number = BigDecimal.valueOf(100.30);
		assertEquals("100,3", Util.convertBigDecimalToString(number));
	}

	@Test
	public void getFirstDateInWeek() {
		Date testDate = Util.getLastDateInWeek(2010, 1);
		System.out.println(testDate);
	}

	@Test
	public void addWeek() {
		YearWeek yearWeek = new YearWeek(2010, 3);
		yearWeek = Util.addWeek(yearWeek, -3);
		assertEquals(Integer.valueOf(2009), yearWeek.getYear());
		assertEquals(Integer.valueOf(53), yearWeek.getWeek());
	}
	
	@Test
	public void skal_ikke_ta_med_gitte_attributter(){
		String infoString = "Stående gavl (over 260cm):Nei;Papp:Ja;Bredde:320;Vinkel:38;test kledning for gavl:Ja;";
		String attributeIno = Util.removeNoAttributes(infoString,"Papp");
		assertEquals("Bredde:320,Vinkel:38,test kledning for gavl",
				attributeIno);
	}

	@Test
	public void removeNoAttributes() {
		String infoString = "Stående gavl (over 260cm):Nei;Bredde:320;Vinkel:38;test kledning for gavl:Ja;";
		String attributeIno = Util.removeNoAttributes(infoString);
		assertEquals("Bredde:320,Vinkel:38,test kledning for gavl",
				attributeIno);
	}

	@Test
	public void collectionFilter() {
		List<Order> orders = Lists.newArrayList();
		Order order = new Order();
		order.setInfo("test");
		order.setOrderNr("1");

		orders.add(order);

		order = new Order();
		order.setInfo("test2");
		order.setOrderNr("2");

		orders.add(order);

		order = new Order();
		order.setInfo("test");

		Predicate<Order> orderPredicate = new OrderPredicate(order);
		List<Order> filteredOrders = Lists.newArrayList(Collections2.filter(
				orders, orderPredicate));
		System.out.println(filteredOrders);
	}

	private class OrderPredicate implements Predicate<Order> {
		private Order searchOrder;

		public OrderPredicate(Order aSearchOrder) {
			searchOrder = aSearchOrder;
		}

		public boolean apply(Order order) {
			boolean infoMatch = !StringUtils.isEmpty(searchOrder.getInfo()) ? StringUtils
					.containsIgnoreCase(order.getInfo(), searchOrder.getInfo())
					: true;
			boolean orderNrMatch = !StringUtils.isEmpty(searchOrder
					.getOrderNr()) ? StringUtils.containsIgnoreCase(order
					.getOrderNr(), searchOrder.getOrderNr()) : true;
			return infoMatch && orderNrMatch;
		}

	}
}
