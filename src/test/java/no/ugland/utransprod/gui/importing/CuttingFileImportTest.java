package no.ugland.utransprod.gui.importing;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.net.URL;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.importing.CuttingImport;
import no.ugland.utransprod.model.Cutting;
import no.ugland.utransprod.model.CuttingLine;
import no.ugland.utransprod.test.FastTests;

import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class CuttingFileImportTest {
	@Test
	public void testImportCuttingFileWithNullAsFileName() {
		CuttingImport cuttingImport = new CuttingImport();
		try {
			cuttingImport.importCuttingFile(null);
			assertEquals("Skulle vært kastet exception", true, false);
		} catch (ProTransException e) {
		}
	}

	@Test
	public void testImportCuttingFile() throws Exception {
		CuttingImport cuttingImport = new CuttingImport();
		
		URL url=this.getClass().getClassLoader().getResource("test_cutting.boq");
		
		Cutting cutting = cuttingImport.importCuttingFile(url.getFile());
		assertNotNull(cutting);
		assertEquals("110", cutting.getVersion());
		assertEquals("JW", cutting.getProSign());
		assertEquals("0950435", cutting.getProId());
		Set<CuttingLine> cuttingLines = cutting.getCuttingLines();
		assertNotNull(cuttingLines);
		assertEquals(18, cuttingLines.size());
		validateCuttingLines(cuttingLines);

	}

	private void validateCuttingLines(Set<CuttingLine> cuttingLines) {
		for (CuttingLine cuttingLine : cuttingLines) {
			LineEnum lineEnum = LineEnum.valueOf(cuttingLine.getName() + "_"
					+ cuttingLine.getCutId() + "_" + cuttingLine.getLineNr());
			lineEnum.validate(cuttingLine);
		}

	}

	private enum LineEnum {
		KAP_1_4(
				"  0:  0:  1:C30   :  48: 223: 4711: 4668:   103225:  50.000: 223.002:   0.000: 136.399:   0.000: 136.399: 236.248:   0.002:4710.888:   0.000:4710.888: 111.500:4710.888: 111.500:4710.888: 223.000:OVERGURT                      :  36:O1    : 5100:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:T2/T3/T4/T5/T6/T7/T8                    :  21:  15:2:*1:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("KAP", cuttingLine.getName());
				assertEquals("1", cuttingLine.getCutId());
				assertEquals("C30", cuttingLine.getGrade());
				assertEquals("48", cuttingLine.getTickness());
				assertEquals("223", cuttingLine.getDepth());
				assertEquals("4711", cuttingLine.getTotalLength());
				assertEquals("4668", cuttingLine.getLengthCenter());
				assertEquals("103225", cuttingLine.getArea());
				assertEquals("OVERGURT", cuttingLine.getDescription());
				assertEquals("36", cuttingLine.getNumberOf());
				assertEquals("O1", cuttingLine.getTimberMarking());
				assertEquals("5100", cuttingLine.getGrossLength());
				assertEquals("T2/T3/T4/T5/T6/T7/T8", cuttingLine
						.getDelPcBelongsTo());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		POSTCUT_2_5(
				"  8: 4710.89:    0.00: 4710.89:  223.00:   50.00:  223.00:    0.00:  136.40:  236.25:    0.00:  556.34:    0.00:  576.32:   34.62:  636.29:    0.00:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("POSTCUT", cuttingLine.getName());
				assertEquals("2", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		KAP_2_6(
				"  0:  0:  1:C30   :  48: 223: 4711: 4668:   103225:  50.000: 223.002:   0.000: 136.399:   0.000: 136.399: 236.248:   0.002:4710.888:   0.000:4710.888: 111.500:4710.888: 111.500:4710.888: 223.000:OVERGURT                      :  12:O2    : 5100:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:T1/T9/T10                               :   6:   6:2:*1:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("KAP", cuttingLine.getName());
				assertEquals("2", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		KAP_3_7(
				"  0:  0:  3:C30   :  48: 223: 3696: 3696:    82421:   0.000: 223.000:   0.000: 111.500:   0.000: 111.500:   0.000:   0.000:3696.000:   0.001:3696.000: 111.501:3696.000: 111.501:3696.000: 223.000:OVERGURT                      :   2:O3    : 3900:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:B3                                      :   0:   0:2:*1:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("KAP", cuttingLine.getName());
				assertEquals("3", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		POSTCUT_4_8(
				" 11:   50.00:  223.00:    0.00:  136.40:  236.25:    0.00:  556.34:    0.00:  584.63:   49.01:  669.51:    0.00: 2784.91:    0.00: 2825.11:   69.64: 2885.73:   34.64: 2970.54:  181.52: 2898.69:  223.00:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("POSTCUT", cuttingLine.getName());
				assertEquals("4", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		KAP_4_9(
				"  0:  0:  1:C30   :  48: 223: 2971: 2887:    63315:  50.000: 223.001:   0.000: 136.399:   0.000: 136.399: 236.247:   0.001:2865.737:   0.000:2970.536: 181.517:2970.536: 181.517:2898.686: 223.000:OVERGURT                      :   6:O4    : 3300:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo1                                     :   0:   0:2:*1:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("KAP", cuttingLine.getName());
				assertEquals("4", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		POSTCUT_5_10(
				" 11:   50.00:  223.00:    0.00:  136.40:  236.25:    0.00:  548.25:    0.00:  578.02:   51.56:  667.33:    0.00: 2784.91:    0.00: 2825.11:   69.64: 2885.73:   34.64: 2970.54:  181.52: 2898.69:  223.00:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("POSTCUT", cuttingLine.getName());
				assertEquals("5", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		KAP_5_11(
				"  0:  0:  1:C30   :  48: 223: 2971: 2887:    63315:  50.000: 223.001:   0.000: 136.399:   0.000: 136.399: 236.247:   0.001:2865.737:   0.000:2970.536: 181.517:2970.536: 181.517:2898.686: 223.000:OVERGURT                      :   3:O5    : 3300:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo2                                     :   0:   0:2:*1:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("KAP", cuttingLine.getName());
				assertEquals("5", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		POSTCUT_6_12(
				" 11:   50.00:  223.00:    0.00:  136.40:  236.25:    0.00:  492.83:    0.00:  536.45:   75.56:  667.33:    0.00: 2784.91:    0.00: 2825.11:   69.64: 2885.73:   34.64: 2970.54:  181.52: 2898.69:  223.00:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("POSTCUT", cuttingLine.getName());
				assertEquals("6", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		KAP_6_13(
				"  0:  0:  1:C30   :  48: 223: 2971: 2887:    63315:  50.000: 223.001:   0.000: 136.399:   0.000: 136.399: 236.247:   0.001:2865.737:   0.000:2970.536: 181.517:2970.536: 181.517:2898.686: 223.000:OVERGURT                      :   3:O6    : 3300:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo6                                     :   0:   0:2:*1:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("KAP", cuttingLine.getName());
				assertEquals("6", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		POSTCUT_7_14(
				"  9:  386.25:    0.00: 2390.80:    0.00: 2431.01:   69.64: 2491.63:   34.64: 2576.43:  181.52: 2504.58:  223.00:  115.43:  223.00:  254.02:  142.98:  225.16:   93.00:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("POSTCUT", cuttingLine.getName());
				assertEquals("7", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		KAP_7_15(
				"  0:  0:  1:C30   :  48: 223: 2576: 2343:    52048:   0.000: 223.001: 193.123: 111.501: 193.123: 111.501: 386.247:   0.001:2471.634:   0.000:2576.433: 181.517:2576.433: 181.517:2504.583: 223.000:OVERGURT                      :   1:O7    : 2700:1:1:4:  0:300:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo3/fo5                                 :   0:   2:2:*1:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("KAP", cuttingLine.getName());
				assertEquals("7", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		KAP_7_16(
				"  0:  0:  1:C30   :  48: 223: 2576: 2343:    52048:   0.000: 223.001: 193.123: 111.501: 193.123: 111.501: 386.247:   0.001:2471.634:   0.000:2576.433: 181.517:2576.433: 181.517:2504.583: 223.000:OVERGURT                      :   1:O7/   : 2700:1:1:4:300:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo3/fo5                                 :   0:   2:2:*1:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("KAP", cuttingLine.getName());
				assertEquals("7", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		POSTCUT_8_17(
				"  7:  128.75:  223.00:    0.00:    0.00: 1759.83:    0.00: 1800.04:   69.64: 1860.66:   34.64: 1945.46:  181.52: 1873.61:  223.00:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("POSTCUT", cuttingLine.getName());
				assertEquals("8", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		KAP_8_18(
				"  0:  0:  1:C30   :  48: 223: 1945: 1841:    40848: 128.749: 223.001:  64.375: 111.501:  64.375: 111.501:   0.000:   0.001:1840.658:   0.000:1945.458: 181.517:1945.458: 181.517:1873.607: 223.000:OVERGURT                      :   2:O8    : 2100:1:1:4:450:450:1:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo4                                     :   0:   0:2:*1:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("KAP", cuttingLine.getName());
				assertEquals("8", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		POSTCUT_9_19(
				"  7:   71.79:  223.00:    0.00:  181.55:   84.81:   34.66:  154.09:   74.66:  197.19:    0.00: 1344.68:    0.00: 1215.93:  223.00:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("POSTCUT", cuttingLine.getName());
				assertEquals("9", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		KAP_9_20(
				"  0:  0:  2:C30   :  48: 223: 1345: 1240:    27450:  71.792: 223.001:   0.000: 181.553:   0.000: 181.553: 104.818:   0.001:1344.675:   0.000:1280.302: 111.500:1280.302: 111.500:1215.928: 223.000:OVERGURT                      :   5:O9    : 1500:0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:T4/T6                                   :   0:   5:2:*1:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("KAP", cuttingLine.getName());
				assertEquals("9", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		},
		KAP_10_21(
				"  0:  0:  1:C30   :  48: 223: 1127: 1036:    23103:  91.040: 223.000:  45.520: 111.500:  45.520: 111.500:   0.000:   0.000:1036.012:   0.000:1081.532: 111.500:1081.532: 111.500:1127.052: 223.000:OVERGURT                      :   2:O10   : 1500:2:1:4:450:450:1:   0:5:8:450:450:0:   0:0:0:  0:  0:0:   0:0:0:  0:  0:0:   0:fo7                                     :   0:   0:2:*1:") {
			@Override
			public void validate(CuttingLine cuttingLine) {
				assertEquals("KAP", cuttingLine.getName());
				assertEquals("10", cuttingLine.getCutId());
				assertEquals(getExpectedCutLine(), cuttingLine.getCutLine());

			}
		};
		private String expectedCutLine;

		public abstract void validate(CuttingLine cuttingLine);

		private LineEnum(String aCutLine) {
			expectedCutLine = aCutLine;
		}

		protected String getExpectedCutLine() {
			return expectedCutLine;
		}
	}
}
