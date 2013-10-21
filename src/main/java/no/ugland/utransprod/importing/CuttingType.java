/**
 * 
 */
package no.ugland.utransprod.importing;

import no.ugland.utransprod.model.Cutting;
import no.ugland.utransprod.model.CuttingLine;

import org.apache.commons.lang.StringUtils;

public enum CuttingType {
    VERSION {
        @Override
        public boolean checkNumberOfElements(String[] elements) {
            return elements.length == 2;
        }

        @Override
        public void setCuttingData(Cutting cutting, String[] elements, Integer lineNr) {
            cutting.setVersion(elements[1].trim());

        }

        @Override
        public String toFileLine(Cutting cutting,CuttingLine cuttingLine) {
            return "VERSION :"+cutting.getVersion()+"\n";
        }
    },
    PRO_SIGN {
        @Override
        public boolean checkNumberOfElements(String[] elements) {
            return elements.length == 2;
        }

        @Override
        public void setCuttingData(Cutting cutting, String[] elements, Integer lineNr) {
            cutting.setProSign(elements[1].trim());

        }

        @Override
        public String toFileLine(Cutting cutting, CuttingLine cuttingLine) {
            return "PRO_SIGN:"+cutting.getProSign()+"\n";
        }
    },
    PRO_ID {
        @Override
        public boolean checkNumberOfElements(String[] elements) {
            return elements.length == 2;
        }

        @Override
        public void setCuttingData(Cutting cutting, String[] elements, Integer lineNr) {
            cutting.setProId(elements[1].trim());

        }

        @Override
        public String toFileLine(Cutting cutting, CuttingLine cuttingLine) {
            return "PRO_ID  :"+cutting.getProId()+"\n";
        }
    },
    KAP {
        @Override
        public boolean checkNumberOfElements(String[] elements) {
            return elements.length == 61;
        }

        @Override
        public void setCuttingData(Cutting cutting, String[] elements, Integer lineNr) {
            CuttingLine cuttingLine =setCuttingLineData(elements, cutting, lineNr);
            cuttingLine.setGrade(elements.length>CuttingLine.GRADE?elements[CuttingLine.GRADE].trim():null);
            cuttingLine.setTickness(elements.length>CuttingLine.TICKNESS?elements[CuttingLine.TICKNESS].trim():null);
            cuttingLine.setDepth(elements.length>CuttingLine.DEPTH?elements[CuttingLine.DEPTH].trim():null);
            cuttingLine.setTotalLength(elements.length>CuttingLine.TOTAL_LENGTH?elements[CuttingLine.TOTAL_LENGTH].trim():null);
            cuttingLine.setLengthCenter(elements.length>CuttingLine.LENGTH_CENTER?elements[CuttingLine.LENGTH_CENTER].trim():null);
            cuttingLine.setArea(elements.length>CuttingLine.AREA?elements[CuttingLine.AREA].trim():null);
            cuttingLine.setDescription(elements.length>CuttingLine.DESCRIPTION?elements[CuttingLine.DESCRIPTION].trim():null);
            cuttingLine.setNumberOf(elements.length>CuttingLine.NUMBER_OF?elements[CuttingLine.NUMBER_OF].trim():null);
            cuttingLine.setTimberMarking(elements.length>CuttingLine.TIMBER_MARKING?elements[CuttingLine.TIMBER_MARKING].trim():null);
            cuttingLine.setGrossLength(elements.length>CuttingLine.GROSS_LENGTH?elements[CuttingLine.GROSS_LENGTH].trim():null);
            cuttingLine.setDelPcBelongsTo(elements.length>CuttingLine.DEL_PC_BELONGS_TO?elements[CuttingLine.DEL_PC_BELONGS_TO].trim():null);

        }

        @Override
        public String toFileLine(Cutting cutting, CuttingLine cuttingLine) {
            return "KAP     :"+getCuttingLineDetails(cuttingLine)+"\n";
        }

        
    },
    POSTCUT {
        @Override
        public boolean checkNumberOfElements(String[] elements) {
            int numberOfPoints = Integer.valueOf(elements[2].trim());
            int totalColumns = (numberOfPoints * 2) + 3;
            return totalColumns == elements.length;
        }

        @Override
        public void setCuttingData(Cutting cutting, String[] elements, Integer lineNr) {
            setCuttingLineData(elements, cutting, lineNr);

        }

        @Override
        public String toFileLine(Cutting cutting, CuttingLine cuttingLine) {
            return "POSTCUT :"+getCuttingLineDetails(cuttingLine)+"\n";
        }
    };

    public abstract boolean checkNumberOfElements(String[] elements);

    public abstract void setCuttingData(Cutting cutting, String[] elements, Integer lineNr);
    public abstract String toFileLine(Cutting cutting,CuttingLine cuttingLine);

    private static CuttingLine setCuttingLineData(String[] elements, Cutting cutting, Integer lineNr) {
        CuttingLine cuttingLine = getCutting(elements, lineNr);

        cuttingLine.setCutLine(joinElements(elements));
        cutting.addCuttingLine(cuttingLine);
        return cuttingLine;
    }

    private static String joinElements(String[] elements) {
        String line = StringUtils.join(elements, ":", 2, elements.length);
        line += ":";
        return line;
    }

    private static CuttingLine getCutting(String[] elements, Integer lineNr) {
        CuttingLine cuttingLine = new CuttingLine();
        cuttingLine.setName(elements[0].trim());
        cuttingLine.setCutId(elements[1].trim());
        cuttingLine.setLineNr(lineNr);
        return cuttingLine;
    }
    private static String getCuttingLineDetails(CuttingLine cuttingLine) {
        return String.format("%1$3s", cuttingLine.getCutId())+":"+cuttingLine.getCutLine();
    }
}