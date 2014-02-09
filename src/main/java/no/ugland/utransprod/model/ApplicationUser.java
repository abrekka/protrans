package no.ugland.utransprod.model;

import java.util.HashSet;
import java.util.Set;

import no.ugland.utransprod.util.ApplicationParamUtil;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klase som representerer tabell APPLICATION_USER
 * 
 * @author atle.brekka
 */
public class ApplicationUser extends BaseObject {
    private static final long serialVersionUID = 1L;

    public static final ApplicationUser UNKNOWN = new ApplicationUser();

    private Integer userId;

    private String userName;

    private String firstName;

    private String lastName;

    private String password;

    private Set<UserRole> userRoles;

    private String groupUser;

    private JobFunction jobFunction;

    private ProductArea productArea;

    private Set<UserProductAreaGroup> userProductAreaGroups;

    private Integer packageType;

    public ApplicationUser() {
	super();
    }

    /**
     * @param aUserId
     * @param aUserName
     * @param aFirstName
     * @param aLastName
     * @param aPassword
     * @param someUserRoles
     * @param aGroupUser
     * @param aJobFunction
     * @param aProductArea
     * @param someUserProductAreaGroups
     */
    public ApplicationUser(final Integer aUserId, final String aUserName, final String aFirstName, final String aLastName, final String aPassword,
	    final Set<UserRole> someUserRoles, final String aGroupUser, final JobFunction aJobFunction, final ProductArea aProductArea,
	    final Set<UserProductAreaGroup> someUserProductAreaGroups) {
	super();
	this.userId = aUserId;
	this.userName = aUserName;
	this.firstName = aFirstName;
	this.lastName = aLastName;
	this.password = aPassword;
	this.userRoles = someUserRoles;
	this.groupUser = aGroupUser;
	this.jobFunction = aJobFunction;
	this.productArea = aProductArea;
	this.userProductAreaGroups = someUserProductAreaGroups;
    }

    /**
     * @return fornavn
     */
    public final String getFirstName() {
	return firstName;
    }

    /**
     * @param aFirstName
     */
    public final void setFirstName(final String aFirstName) {
	this.firstName = aFirstName;
    }

    /**
     * @return etternavn
     */
    public final String getLastName() {
	return lastName;
    }

    /**
     * @param aLastName
     */
    public final void setLastName(final String aLastName) {
	this.lastName = aLastName;
    }

    /**
     * @return passord
     */
    public final String getPassword() {
	return password;
    }

    /**
     * @param aPassword
     */
    public final void setPassword(final String aPassword) {
	this.password = aPassword;
    }

    /**
     * @return brukerid
     */
    public final Integer getUserId() {
	return userId;
    }

    /**
     * @param aUserId
     */
    public final void setUserId(final Integer aUserId) {
	this.userId = aUserId;
    }

    /**
     * @return brukernavn
     */
    public final String getUserName() {
	return userName;
    }

    /**
     * @param aUserName
     */
    public final void setUserName(final String aUserName) {
	this.userName = aUserName;
    }

    /**
     * @return brukerroller
     */
    public final Set<UserRole> getUserRoles() {
	return userRoles;
    }

    /**
     * @param aomeUserRoles
     */
    public final void setUserRoles(final Set<UserRole> aomeUserRoles) {
	this.userRoles = aomeUserRoles;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object other) {
	if (!(other instanceof ApplicationUser)) {
	    return false;
	}
	ApplicationUser castOther = (ApplicationUser) other;
	return new EqualsBuilder().append(userName, castOther.userName).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public final int hashCode() {
	return new HashCodeBuilder().append(userName).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public final String toString() {
	return firstName + " " + lastName;

    }

    /**
     * @return 'Ja' dersom bruker er gruppebruker
     */
    public final String getGroupUser() {
	return groupUser;
    }

    /**
     * @param aGroupUser
     */
    public final void setGroupUser(final String aGroupUser) {
	this.groupUser = aGroupUser;
    }

    /**
     * @param aUserRole
     */
    public final void addUserRole(final UserRole aUserRole) {
	if (userRoles == null) {
	    userRoles = new HashSet<UserRole>();
	}
	aUserRole.setApplicationUser(this);
	userRoles.add(aUserRole);
    }

    /**
     * @return funksjon
     */
    public final JobFunction getJobFunction() {
	return jobFunction;
    }

    /**
     * @param aJobFunction
     */
    public final void setJobFunction(final JobFunction aJobFunction) {
	this.jobFunction = aJobFunction;
    }

    /**
     * @return true dersom bruker er avviksansvarlig
     */
    public final boolean isDeviationManager() {
	String deviationManager = ApplicationParamUtil.getDeviationManagerName();
	if (deviationManager != null && getFullName().equalsIgnoreCase(deviationManager)) {
	    return true;
	}
	return false;
    }

    /**
     * @return produktområde
     */
    public final ProductArea getProductArea() {
	return productArea;
    }

    /**
     * @param aProductArea
     */
    public final void setProductArea(final ProductArea aProductArea) {
	this.productArea = aProductArea;
    }

    /**
     * @return fullt navn
     */
    public final String getFullName() {
	return firstName + " " + lastName;
    }

    /**
     * @return produktområder
     */
    public final Set<UserProductAreaGroup> getUserProductAreaGroups() {
	return userProductAreaGroups;
    }

    /**
     * @param comeUserProductAreaGroups
     */
    public final void setUserProductAreaGroups(final Set<UserProductAreaGroup> comeUserProductAreaGroups) {
	this.userProductAreaGroups = comeUserProductAreaGroups;
    }

    public Integer getPackageType() {
	return packageType;
    }

    public void setPackageType(Integer packageType) {
	this.packageType = packageType;
    }

}
