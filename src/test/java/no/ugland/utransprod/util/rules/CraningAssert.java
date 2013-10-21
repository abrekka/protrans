package no.ugland.utransprod.util.rules;

import static no.ugland.utransprod.util.rules.CraningAssert.assertThat;

import java.math.BigDecimal;

import org.fest.assertions.Assertions;
import org.fest.assertions.Condition;
import org.fest.assertions.Description;
import org.fest.assertions.GenericAssert;

public class CraningAssert extends GenericAssert<Craning>{

	
	protected CraningAssert(Craning actual) {
		super(actual);
	}

	public static CraningAssert assertThat(Craning actual) {
		return new CraningAssert(actual);
	}

	public CraningAssert hasCraningbasisSize(int size) {
		Assertions.assertThat(actual.getCranings()).hasSize(size);
		return this;
	}

	public CraningAssert containsCraningbasis(Craningbasis... craningbasis) {
		Assertions.assertThat(actual.getCranings()).contains(craningbasis);
		return this;
	}

	public CraningAssert hasCostValue(int costValue) {
		Assertions.assertThat(actual.getCostValue()).isEqualTo(
				BigDecimal.valueOf(costValue));
		return this;
	}

	public CraningAssert hasBrickWallHeight(BigDecimal expected) {
		Assertions.assertThat(actual.getBrickWallHeight().getValue().floatValue())
				.isEqualTo(expected.floatValue());
		return this;
	}

	@Override
	protected GenericAssert<Craning> satisfies(Condition<Craning> condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> doesNotSatisfy(Condition<Craning> condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> is(Condition<Craning> condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> isNot(Condition<Craning> condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> as(String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> describedAs(String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> as(Description description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> describedAs(Description description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> isEqualTo(Craning expected) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> isNotEqualTo(Craning other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> isNotNull() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> isSameAs(Craning expected) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> isNotSameAs(Craning other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericAssert<Craning> overridingErrorMessage(String message) {
		// TODO Auto-generated method stub
		return null;
	}





}
