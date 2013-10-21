package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;

import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.AttributeChoice;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 * 
 */
@Category(FastTests.class)
public class AttributeManagerTest {
	private AttributeManager attributeManager;

	private Attribute attribute;

	@Before
	public void setUp() throws Exception {
		attributeManager = (AttributeManager) ModelUtil
				.getBean("attributeManager");
	}

	@After
	public void tearDown() throws Exception {
		if (attribute != null && attribute.getAttributeId() != null) {
			attributeManager.removeAttribute(attribute);
			attribute = null;
		}
		Attribute att = attributeManager.findByName("testatle");
		if (att != null) {
			attributeManager.removeAttribute(att);
		}
	}

	@Test
	public void testInsertNoValuesSet() {
		Attribute att = new Attribute();
		try {
			attributeManager.saveAttribute(att);
			assertEquals(false, true); // skal ikke kunne lagre uten navn
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInsert() {
		attribute = new Attribute();
		attribute.setName("testatle");
		attributeManager.saveAttribute(attribute);
		Attribute findAttribute = attributeManager.findByName("testatle");
		assertNotNull(findAttribute);
		assertEquals("testatle", findAttribute.getName());
	}

	@Test
	public void testEdit() {
		attribute = new Attribute();
		attribute.setName("testatle");
		attributeManager.saveAttribute(attribute);
		Attribute findAttribute = attributeManager.findByName("testatle");
		assertNotNull(findAttribute);
		assertEquals("testatle", findAttribute.getName());
		findAttribute.setName("test2");
		findAttribute.setDescription("kommentar");
		attributeManager.saveAttribute(findAttribute);
		findAttribute = attributeManager.findByName("test2");
		assertNotNull(findAttribute);
		assertEquals("test2", findAttribute.getName());
		assertEquals("kommentar", findAttribute.getDescription());

	}

	@Test
	public void testRemove() {
		attribute = new Attribute();
		attribute.setName("testatle");
		attributeManager.saveAttribute(attribute);
		Attribute findAttribute = attributeManager.findByName("testatle");
		assertNotNull(findAttribute);
		assertEquals("testatle", findAttribute.getName());
		attributeManager.removeAttribute(attribute);
		attribute = attributeManager.findByName("test2");
		assertNull(attribute);

	}

	@Test
	public void testAddChoice() {
		attribute = new Attribute();
		attribute.setName("testatle");
		attributeManager.saveAttribute(attribute);
		Attribute findAttribute = attributeManager.findByName("testatle");
		assertNotNull(findAttribute);
		assertEquals("testatle", findAttribute.getName());

		Set<AttributeChoice> attributeChoices = new HashSet<AttributeChoice>();
		AttributeChoice attributeChoice = new AttributeChoice(null, "valg",
				findAttribute, null, null, null);
		attributeChoices.add(attributeChoice);
		findAttribute.setAttributeChoices(attributeChoices);

		attributeManager.saveAttribute(findAttribute);
		findAttribute = attributeManager.findByName("testatle");
		assertEquals(true, findAttribute.getAttributeChoices().size() != 0);
	}

}
