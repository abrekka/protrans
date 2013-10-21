package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import no.ugland.utransprod.dao.ArticleTypeDAO;
import no.ugland.utransprod.dao.ImportOrderVDAO;
import no.ugland.utransprod.dao.OrdlnDAO;
import no.ugland.utransprod.dao.UdsalesmallDAO;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.ImportOrderV;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.OrdlnPK;
import no.ugland.utransprod.model.Prod;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Udsalesmall;
import no.ugland.utransprod.service.impl.ConstructionTypeManagerImpl;
import no.ugland.utransprod.service.impl.IncomingOrderManagerImpl;
import no.ugland.utransprod.test.FastTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.inject.internal.Lists;
@Category(FastTests.class)
public class IncomingOrderManagerTest {

	private static final String ARTICLE_PATH_TAKSTEIN = "Takstein";
	private IncomingOrderManagerImpl incomingOrderManager = new IncomingOrderManagerImpl();
	@Mock
	private ImportOrderVDAO importOrderVDAO;
	@Mock
	private OrdlnManager ordlnManager;
	@Mock
	private UdsalesmallDAO udsalesmallDao;
	@Mock
	private CostTypeManager costTypeManager;
	@Mock
	private CostUnitManager costUnitManager;

	@Before
	public void settopp(){
		MockitoAnnotations.initMocks(this);
		incomingOrderManager.setImportOrderVDAO(importOrderVDAO);
		incomingOrderManager.setOrdlnManager(ordlnManager);
		incomingOrderManager.setCostTypeManager(costTypeManager);
		incomingOrderManager.setCostUnitManager(costUnitManager);
	}
	@Test
	public void skal_importere_montering() throws Exception {
		CostType costType=new CostType();
		when(costTypeManager.findByName("Montering")).thenReturn(costType);
		CostUnit costUnit=new CostUnit();
		when(costUnitManager.findByName("Kunde")).thenReturn(costUnit);
		
		String orderNr="1";
		ImportOrderV importOrderV = new ImportOrderV();
		Integer userdefId=1;
		importOrderV.setUserdefId(userdefId);
		when(importOrderVDAO.findByNumber1(orderNr)).thenReturn(importOrderV);
		Order incomingOrder=new Order();
		incomingOrder.setOrderNr("1");
		Ordln ordln=new Ordln();
		Prod prod=new Prod();
		prod.setProdNo("MONTERING VILLA");
		ordln.setProd(prod);
		List<Ordln> ordlnList=Lists.newArrayList(ordln);
		when(ordlnManager.findCostLines(incomingOrder.getOrderNr())).thenReturn(ordlnList);
		
		Udsalesmall udsalessmall=new Udsalesmall();
		when(udsalesmallDao.getObject(userdefId)).thenReturn(udsalessmall);
		incomingOrderManager.setUdsalesmallDAO(udsalesmallDao);
		
		
		
		incomingOrder.setOrderNr(orderNr);
		incomingOrderManager.setCosts(incomingOrder);
		
		assertEquals(true, incomingOrder.doAssembly());
	}
	
	@Test
	public void importTakstein(){
//		Mockery mockery= new JUnit3Mockery();
		IncomingOrderManager incomingOrderManager = new IncomingOrderManagerImpl();
		final OrdlnManager ordlnManager=mock(OrdlnManager.class);
		final OrdlnDAO ordlnDAO=mock(OrdlnDAO.class);
		ConstructionTypeManager constructionTypeManager=new ConstructionTypeManagerImpl();
		final ArticleTypeDAO articleTypeDAO=mock(ArticleTypeDAO.class);
		((IncomingOrderManagerImpl)incomingOrderManager).setOrdlnManager(ordlnManager);
		((IncomingOrderManagerImpl)incomingOrderManager).setOrdlnDAO(ordlnDAO);
		((IncomingOrderManagerImpl)incomingOrderManager).setConstructionTypeManager(constructionTypeManager);
		((IncomingOrderManagerImpl)incomingOrderManager).setArticleTypeDAO(articleTypeDAO);
		
		final Order incomingOrder=new Order();
		incomingOrder.setOrderNr("1");
		ProductArea productArea=new ProductArea();
		ProductAreaGroup productAreaGroup=new ProductAreaGroup();
		productAreaGroup.setProductAreaGroupName("Garasje");
		productArea.setProductAreaGroup(productAreaGroup);
		incomingOrder.setProductArea(productArea);
		
		final List<Ordln> ordlnList=new ArrayList<Ordln>();
		Ordln ordln = new Ordln();
		ordln.setOrdlnPK(new OrdlnPK(1,1));
		Prod prod = new Prod();
		prod.setProdNo("TAKSTEIN");
		prod.setPrCatNo(1020410);
		prod.setPrCatNo2(9);
		ordln.setProd(prod);
		ordlnList.add(ordln);
		
		final Ord ord = new Ord();
		final ArticleType articleTypeTakstein=new ArticleType();
		articleTypeTakstein.setArticleTypeName("Takstein");
		
		final Collection<OrderLine> originalOrderLines =new HashSet<OrderLine>();
		OrderLine orderLineTakstol=new OrderLine();
		orderLineTakstol.setArticleName("Takstein");
		orderLineTakstol.setArticleType(articleTypeTakstein);
		originalOrderLines.add(orderLineTakstol);
		
		
		final  ManagerRepository managerRepository=mock(ManagerRepository.class);
		
		when(ordlnManager.findOrdByOrderNr("1")).thenReturn(ord);
		when(ordlnDAO.findByOrderNr("1")).thenReturn(ordlnList);
		when(articleTypeDAO.findByProdCatNoAndProdCatNo2(1020410, 9)).thenReturn(articleTypeTakstein);
		
		
		incomingOrderManager.setOrderLines(incomingOrder,managerRepository);
		
		OrderLine orderLine = incomingOrder.getOrderLine(ARTICLE_PATH_TAKSTEIN);
		assertNotNull(orderLine);
		assertFalse(orderLine==OrderLine.UNKNOWN);
	}
	
	
	}
