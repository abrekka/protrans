/**
 * 
 */
package no.ugland.utransprod.gui.handlers;

public class CheckObject {

    private boolean canContinue;

    private String msg;
    private Object refObject;

    public CheckObject(final String aMsg, final boolean doCanContinue) {
        this(aMsg,doCanContinue,null);
    }
    
    public CheckObject(final String aMsg, final boolean doCanContinue,final Object aRefObject) {
        msg = aMsg;
        canContinue = doCanContinue;
        refObject=aRefObject;
    }

    public final String getMsg() {
        return msg;
    }

    public final boolean canContinue() {
        return canContinue;
    }

    public Object getRefObject() {
        return refObject;
    }

    public void setRefObject(Object refObject) {
        this.refObject = refObject;
    }
}