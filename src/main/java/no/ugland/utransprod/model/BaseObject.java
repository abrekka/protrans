package no.ugland.utransprod.model;

import java.io.Serializable;

import com.jgoodies.binding.beans.Model;

/**
 * Base class for Model objects. Child objects should implement toString(),
 * equals() and hashCode();
 * <p>
 * <a href="BaseObject.java.html"><i>View Source</i></a>
 * </p>
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public abstract class BaseObject implements Serializable {
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public abstract String toString();

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public abstract boolean equals(Object o);

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public abstract int hashCode();

    /**
     * @param id
     */
    public void setObjectId(Object id) {

    }

    /**
     * @return objekt id
     */
    public Object getObjectId() {
        return null;
    }

    /**
     * @return objektnavn
     */
    public String getObjectName() {
        return "";
    }

    /**
     * Validerer objekt, og returenerer feilstreng dersom objektet ikke er
     * rikitg
     * @return feilstreng
     */
    public String validateObject() {
        return null;
    }
}
