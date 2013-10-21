package no.ugland.utransprod.util.report;

public class MailConfig {
    private String fileName;

    private String heading;

    private String msg;
    
    private String toMailAddress;

    public MailConfig(final String aFileName, final String aHeading,
            final String aMsg,final String aToMailAddress) {
        super();
        this.fileName = aFileName;
        this.heading = aHeading;
        this.msg = aMsg;
        this.toMailAddress=aToMailAddress;
    }

    public final String getFileName() {
        return fileName;
    }

    public final String getHeading() {
        return heading;
    }

    public final String getMsg() {
        return msg;
    }

    public final String getToMailAddress() {
        return toMailAddress;
    }
    
    public final void addToHeading(String addText){
        if(addText!=null){
            heading+=" "+addText;
        }
    }
}
