package no.ugland.utransprod.dao;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class ArticleTypeDAOIntegrationTest {
    private static ArticleTypeDAO articleTypeDAO;
    private static ArticleType articleType;

    @BeforeClass
    public static void settopp() {
	articleTypeDAO = (ArticleTypeDAO) ModelUtil.getBean(ArticleTypeDAO.DAO_NAME);
	articleType = new ArticleType();
	articleType.setArticleTypeName("test1");
	articleType.setProdCatNo2(26);
	articleTypeDAO.saveObject(articleType);

	articleType = new ArticleType();
	articleType.setArticleTypeName("test2");
	articleType.setProdCatNo(1526001);
	articleTypeDAO.saveObject(articleType);
    }

    @AfterClass
    public static void ryddopp() {
	articleType = articleTypeDAO.findByName("test1");
	if (articleType != null) {
	    articleTypeDAO.removeObject(articleType.getArticleTypeId());
	}

	articleType = articleTypeDAO.findByName("test2");
	if (articleType != null) {
	    articleTypeDAO.removeObject(articleType.getArticleTypeId());
	}
    }

    @Test
    public void testFindByProdCatNoAndProdCatNo2Kledning() {
	articleType = articleTypeDAO.findByProdCatNoAndProdCatNo2(0, 3);
	assertNotNull(articleType);
	assertEquals("Kledning", articleType.getArticleTypeName());
    }

    @Test
    public void testFindByProdCatNoAndProdCatNo2Trapp() {

	ArticleType articleType = articleTypeDAO.findByProdCatNoAndProdCatNo2(1526001, 26);
	assertNotNull(articleType);
	assertEquals("Trapp", articleType.getArticleTypeName());
    }

    @Test
    public void testFindByProdCatNoAndProdCatNo2Test() {
	articleType = articleTypeDAO.findByProdCatNoAndProdCatNo2(null, 26);
	assertNotNull(articleType);
	assertEquals("test1", articleType.getArticleTypeName());
    }

    @Test
    public void testFindByProdCatNoAndProdCatNo2Test2() {
	articleType = articleTypeDAO.findByProdCatNoAndProdCatNo2(1526001, null);
	assertNotNull(articleType);
	assertEquals("test2", articleType.getArticleTypeName());
    }
}
