package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import no.ugland.utransprod.model.Cutting;
import no.ugland.utransprod.model.CuttingLine;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class CuttingManagerTest {

    @After
    public void tearDown() throws Exception {
        CuttingManager cuttingManager = (CuttingManager)ModelUtil.getBean(CuttingManager.MANAGER_NAME);
        Cutting cutting = cuttingManager.findByProId("0950435");
        if(cutting!=null){
            cuttingManager.removeCutting(cutting);
        }
        cutting = cuttingManager.findByProId("0950436");
        if(cutting!=null){
            cuttingManager.removeCutting(cutting);
        }
    }
    @Test
    public void testInsertCutting() throws Exception{
        CuttingManager cuttingManager = insertCutting("0950435",false);
        
        checkInsertedCutting(cuttingManager,"0950435");
    }

    
    @Test
    public void testInsertWithOverwrite() throws Exception{
        CuttingManager cuttingManager = insertCutting("0950435",false);
        checkInsertedCutting(cuttingManager,"0950435");
        insertCutting("0950436",true);
        checkInsertedCutting(cuttingManager,"0950436");
    }
    private void checkInsertedCutting(CuttingManager cuttingManager,final String proId) {
        Cutting savedCutting = cuttingManager.findByProId(proId);
        assertNotNull(savedCutting);
        cuttingManager.lazyLoad(savedCutting, new LazyLoadEnum[][]{{LazyLoadEnum.CUTTING_LINES,LazyLoadEnum.NONE}});
        assertEquals(proId, savedCutting.getProId());
        assertEquals("110", savedCutting.getVersion());
        assertEquals("JW", savedCutting.getProSign());
        assertEquals("49838", savedCutting.getOrder().getOrderNr());
        
        Set<CuttingLine> lines = savedCutting.getCuttingLines();
        assertNotNull(lines);
        assertEquals(1, lines.size());
        
        CuttingLine aLine=lines.iterator().next();
        assertEquals("KAP", aLine.getName());
        assertEquals("1", aLine.getCutId());
        assertEquals("cutLine", aLine.getCutLine());
        assertEquals(Integer.valueOf(1), aLine.getLineNr());
        assertEquals("area", aLine.getArea());
        assertEquals("aDelPcBelongsTo", aLine.getDelPcBelongsTo());
    }

    private CuttingManager insertCutting(final String proId,final Boolean overwrite) throws Exception {
        OrderManager orderManager=(OrderManager)ModelUtil.getBean(OrderManager.MANAGER_NAME);
        Order order =  orderManager.findByOrderNr("49838");
        Cutting cutting = new Cutting();
        cutting.setProId(proId);
        cutting.setVersion("110");
        cutting.setProSign("JW");
        cutting.setOrder(order);
        
        CuttingLine line = new CuttingLine();
        line.setName("KAP");
        line.setCutId("1");
        line.setCutLine("cutLine");
        line.setLineNr(1);
        line.setArea("area");
        line.setDelPcBelongsTo("aDelPcBelongsTo");
        line.setDepth("aDepth");
        line.setDescription("aDescription");
        line.setGrade("aGrade");
        line.setGrossLength("aGrossLength");
        line.setLengthCenter("aLengthCenter");
        line.setNumberOf("aNumberOf");
        line.setTickness("aTickness");
        line.setTimberMarking("aTimberMarking");
        line.setTotalLength("aTotalLength");
        
        cutting.addCuttingLine(line);
        CuttingManager cuttingManager = (CuttingManager)ModelUtil.getBean(CuttingManager.MANAGER_NAME);
        cuttingManager.saveCutting(cutting,overwrite);
        return cuttingManager;
    }

    
    
    
}
