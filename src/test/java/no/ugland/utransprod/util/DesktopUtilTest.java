package no.ugland.utransprod.util;

import static org.junit.Assert.assertEquals;

import java.io.File;

import no.ugland.utransprod.test.FastTests;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class DesktopUtilTest {


	@Test
    public void testOpenTxtFile() throws Exception {
        File txtFile = new File("C:\\java\\workspace\\ProTrans\\src\\test\\resources\\test.txt");
        DesktopUtil desktopUtil = new DesktopUtil();
        desktopUtil.openFile(txtFile, null);
    }

	@Test
	@Ignore
    public void testOpenBoqFile() throws Exception {
        File boqFile = new File("C:\\java\\workspace\\ProTrans\\src\\test\\resources\\test_cutting.boq");
        DesktopUtil desktopUtil = new DesktopUtil();
        boolean success = desktopUtil.openFile(boqFile, null);
        assertEquals(true, success);
    }
}
