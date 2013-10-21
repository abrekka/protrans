package no.ugland.utransprod.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import no.ugland.utransprod.model.OrderLineAttribute;

import org.nfunk.jep.JEP;
import org.nfunk.jep.Variable;

/**
 * Klasse som brukes til å beregne mattematiske uttrykk.
 * 
 * @author atle.brekka
 */
public final class Calculator {
	private Calculator() {

	}

	/**
	 * Kalkulerer formel.
	 * 
	 * @param formula
	 * @param attributes
	 * @return resultat
	 */
	public static Double calculate(final String formula,
			final Collection<OrderLineAttribute> attributes) {
		JEP jep = getJEP(formula);

		addAttributesToFormula(attributes, jep);
		return jep.getValue();
	}

	

	private static void addAttributesToFormula(
			final Collection<OrderLineAttribute> attributes, JEP jep) {
		if (attributes != null) {
			for (OrderLineAttribute attribute : attributes) {
				addAttributeAsVariable(attribute,jep);

			}
		}
	}

	private static JEP getJEP(final String formula) {
		JEP jep = new JEP();
		jep.addStandardFunctions();
		jep.addStandardConstants();
		jep.setAllowUndeclared(true);
		jep.setImplicitMul(true);
		jep.parseExpression(formula);
		return jep;
	}

	private static void addAttributeAsVariable(OrderLineAttribute attribute,
			JEP jep) {
		if (attributeIsUsedInFormula(attribute, jep)) {
			// if (!attribute.isYesNo() &&
			// !attribute.isSelection()&&attribute.getAttributeValue().length()!=0)
			// {
			Variable var = jep.getVar(attribute.getAttributeName());
			jep.setVarValue(attribute.getAttributeName(), Double
					.valueOf(attribute.getAttributeNumberValue()));
		}

	}

	private static boolean attributeIsUsedInFormula(
			final OrderLineAttribute attribute, final JEP jep) {
		return attributeNameIsInFormula(attribute.getAttributeName(), jep)
				&& attributeIsValueAttribute(attribute);

	}

	private static boolean attributeIsValueAttribute(
			OrderLineAttribute attribute) {
		return !attribute.isYesNo() && !attribute.isSelection()
				&& attribute.getAttributeValue().length() != 0;
	}

	private static boolean attributeNameIsInFormula(final String attributeName,
			final JEP jep) {
		return jep.getVar(attributeName) != null;
	}

	/**
	 * Brukes for å teste.
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		Pattern pattern = Pattern.compile("\\d*\\.\\d*");
		Matcher matcher = pattern.matcher("22.5¨");
		if (matcher.lookingAt()) {
			String string = matcher.group();
			System.out.println(string);
		}
		// Calculator.calculate("(0.5*Bredde+45)*tan(Vinkel)", null);
		Calculator.calculate("tan((38*pi/180))", null);
		System.exit(0);
	}
}
