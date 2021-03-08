/*    */ package no.ugland.utransprod.gui.model;
/*    */ import com.jgoodies.binding.beans.Model;
/*    */ public class FileModel extends Model {
/*    */    private String fileContent;
/*    */    public static final String PROPERTY_FILE_CONTENT = "fileContent";
/*    */ 
/*    */    public FileModel(String aFileContent) {
/*  8 */       this.fileContent = aFileContent;
/*  9 */    }
/*    */ 
/*    */ 
/*    */    public String getFileContent() {
/* 13 */       return this.fileContent;
/*    */    }
/*    */ 
/*    */    public void setFileContent(String aFileContent) {
/* 17 */       String oldContent = this.getFileContent();
/* 18 */       this.fileContent = this.fileContent;
/* 19 */       this.firePropertyChange("fileContent", oldContent, aFileContent);
/* 20 */    }
/*    */ }
