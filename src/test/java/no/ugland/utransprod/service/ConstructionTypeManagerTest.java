package no.ugland.utransprod.service;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;

import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeAttribute;
import no.ugland.utransprod.model.ProductArea;
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
public class ConstructionTypeManagerTest {
	private ConstructionTypeManager manager;

	private ConstructionType constructionType;

	@Before
	public void setUp() throws Exception {
		manager = (ConstructionTypeManager) ModelUtil
				.getBean("constructionTypeManager");
	}

	@After
	public void tearDown() throws Exception {
		if (constructionType != null
				&& constructionType.getConstructionTypeId() != null) {
			manager.removeConstructionType(constructionType);
		}
		AttributeManager attManager = (AttributeManager) ModelUtil
		.getBean("attributeManager");
		Attribute attribute = attManager.findByName("test");
		if(attribute!=null){
			attManager.removeAttribute(attribute);
		}
	}

	@Test
	public void testInsert() {
		ProductAreaManager productAreaManager=(ProductAreaManager)ModelUtil.getBean("productAreaManager");
		ProductArea productArea = productAreaManager.findByName("Garasje villa");
		constructionType = new ConstructionType();
		constructionType.setName("C2");
		constructionType.setProductArea(productArea);
		manager.saveConstructionType(constructionType);

		constructionType = manager.findByName("C2");
		assertNotNull(constructionType);
	}

	@Test
	public void testInsertAndDeleteAttribute() {
		ProductAreaManager productAreaManager=(ProductAreaManager)ModelUtil.getBean("productAreaManager");
		ProductArea productArea = productAreaManager.findByName("Garasje villa");
		constructionType = new ConstructionType();
		constructionType.setName("TEST");
		constructionType.setProductArea(productArea);

		AttributeManager attManager = (AttributeManager) ModelUtil
				.getBean("attributeManager");

		Attribute attribute = new Attribute(null, "test", null, null, null,
				null, null, null,null,null,null);
		attManager.saveAttribute(attribute);
		ConstructionTypeAttribute att = new ConstructionTypeAttribute(null,
				constructionType, attribute, "test", null, null);
		HashSet<ConstructionTypeAttribute> attributes = new HashSet<ConstructionTypeAttribute>();
		attributes.add(att);
		constructionType.setConstructionTypeAttributes(attributes);

		manager.saveConstructionType(constructionType);
		attributes.remove(att);
		att.setConstructionType(null);
		manager.saveConstructionType(constructionType);
	}

}
