package no.ugland.utransprod.importing;

import java.io.File;
import java.io.IOException;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.Cutting;

import org.apache.commons.io.FileUtils;

public class CuttingImport {

    private static final String FILE_NAME_NULL = "File name is null";

    public Cutting importCuttingFile(String fileName) throws ProTransException {
        File cuttingFile = getCuttingFile(fileName);
        if (cuttingFile.exists()) {
            return readAndImportCuttingFile(cuttingFile);
        }
        throw new ProTransException("Filen " + fileName + " finnes ikke");
    }

    private File getCuttingFile(String fileName) {
        String tmpFileName=fileName!=null?fileName:FILE_NAME_NULL;
        return new File(tmpFileName);
    }

    @SuppressWarnings("unchecked")
    private Cutting readAndImportCuttingFile(File cuttingFile) throws ProTransException {
        Cutting cutting = new Cutting();
        try {
            List<String> lines = FileUtils.readLines(cuttingFile);
            int lineCount = 0;

            for (String line : lines) {
                lineCount++;
                importLine(line, lineCount, cutting);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ProTransException(e);
        }
        return cutting;
    }

    private void importLine(final String line, int lineCount, final Cutting cutting) throws ProTransException {
        String[] lineElements = line.split(":");
        CuttingType cuttingType = validateLine(lineCount, lineElements);
        cuttingType.setCuttingData(cutting, lineElements,lineCount);
    }

    private CuttingType validateLine(int lineCount, String[] lineElements) throws ProTransException {
        if (lineElements == null || lineElements.length < 2) {
            throw new ProTransException("Linje " + lineCount + " inneholder for få elementer");
        }
        CuttingType cuttingType = CuttingType.valueOf(lineElements[0].trim());

        if (!cuttingType.checkNumberOfElements(lineElements)) {
            throw new ProTransException("Linje " + lineCount + " inneholder feil antall elementer "
                    + lineElements.length);
        }
        return cuttingType;
    }

}
