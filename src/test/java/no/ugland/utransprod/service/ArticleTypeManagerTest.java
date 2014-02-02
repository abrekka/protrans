package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;
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
public class ArticleTypeManagerTest {
    private ArticleTypeManager articleTypeManager;

    private ArticleType articleType;

    @After
    public void tearDown() throws Exception {
	if (articleType != null && articleType.getArticleTypeId() != null) {
	    articleTypeManager.removeArticleType(articleType);
	    articleType = null;
	}
    }

    @Before
    public void setUp() throws Exception {
	articleTypeManager = (ArticleTypeManager) ModelUtil.getBean("articleTypeManager");
    }

    @Test
    public void testInsert() {
	articleType = new ArticleType();
	articleType.setArticleTypeName("Test");
	articleType.setDescription("Test");
	articleTypeManager.saveArticleType(articleType);

	ArticleType articleTypeTest = articleTypeManager.findByName("Vegg");
	assertNotNull(articleTypeTest);

    }

    @Test
    public void testFindAll() {
	articleType = new ArticleType();
	articleType.setArticleTypeName("Test");
	articleType.setDescription("Test");
	articleTypeManager.saveArticleType(articleType);

	List<ArticleType> all = articleTypeManager.findAll();

	assertNotNull(all);
	assertEquals(true, all.size() > 0);
    }

    @Test
    public void testFindArticle() {
	ArticleType testType = new ArticleType();

	testType = articleTypeManager.findByName("Gavlkledning");
	articleTypeManager.lazyLoad(testType, new LazyLoadArticleTypeEnum[] { LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE,
		LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE_REF });
	assertNotNull(testType);
    }

    @Test
    public void testFindAllConstructionTypeAttributes() {
	List<Attribute> attributes = articleTypeManager.findAllConstructionTypeAttributes();
	assertNotNull(attributes);
    }
}
