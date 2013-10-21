package no.ugland.utransprod.gui.model;

import com.jgoodies.binding.beans.Model;

public class FileModel extends Model{
    private String fileContent;
    public FileModel(String aFileContent) {
        fileContent=aFileContent;
    }

    public static final String PROPERTY_FILE_CONTENT = "fileContent";
    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String aFileContent) {
        String oldContent=getFileContent();
        this.fileContent = fileContent;
        firePropertyChange(PROPERTY_FILE_CONTENT, oldContent, aFileContent);
    }

}
