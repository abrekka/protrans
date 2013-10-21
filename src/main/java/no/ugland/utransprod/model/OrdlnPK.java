package no.ugland.utransprod.model;

import java.io.Serializable;

public class OrdlnPK implements Serializable {
    private Integer lnno;

    private Integer ordno;

    public OrdlnPK() {
        super();
    }

    public OrdlnPK(final Integer aLnno, final Integer aOrdno) {
        super();
        this.lnno = aLnno;
        this.ordno = aOrdno;
    }

    public final Integer getLnno() {
        return lnno;
    }

    public final void setLnno(final Integer aLnno) {
        this.lnno = aLnno;
    }

    public final Integer getOrdno() {
        return ordno;
    }

    public final void setOrdno(final Integer aOrdno) {
        this.ordno = aOrdno;
    }

}
