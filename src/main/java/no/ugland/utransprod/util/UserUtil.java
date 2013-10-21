package no.ugland.utransprod.util;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.service.UserTypeManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;

public final class UserUtil {
    private static Map<String, Boolean> userAccess;

    private static UserTypeManager userTypeManager;

    private UserUtil() {

    }
    public static void setUserTypeManagerForTest(UserTypeManager aUserTypeManager){
    	userTypeManager=aUserTypeManager;
    }

    private static void initUserAccess(final UserType userType) {
        if (userAccess == null) {
            userTypeManager = userTypeManager==null?(UserTypeManager) ModelUtil.getBean("userTypeManager"):userTypeManager;
            userTypeManager
                    .lazyLoad(
                            userType,
                            new LazyLoadEnum[][] {{LazyLoadEnum.USER_TYPE_ACCESSES,LazyLoadEnum.NONE}});

            Set<UserTypeAccess> accesses = userType.getUserTypeAccesses();
            if (accesses != null) {
                setUserAccesses(accesses);
            }

        }
    }

    private static void setUserAccesses(final Set<UserTypeAccess> accesses) {
        userAccess = new Hashtable<String, Boolean>();
        for (UserTypeAccess userTypeAccess : accesses) {
            userAccess.put(userTypeAccess.getWindowAccess().getWindowName(),
                    Util
                            .convertNumberToBoolean(userTypeAccess
                                    .getWriteAccess()));
        }
    }

    public static boolean hasAccess(final UserType userType, final String windowName) {
        if (Util.convertNumberToBoolean(userType.getIsAdmin())) {
            return true;
        }
        initUserAccess(userType);
        Boolean access = userAccess.get(windowName);
        if (access != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static boolean hasWriteAccess(final UserType userType, final String windowName) {
        if (userType == null) {
            throw new IllegalArgumentException("Brukertype må være satt");
        }
        if (Util.convertNumberToBoolean(userType.getIsAdmin())) {
            return true;
        }
        initUserAccess(userType);
        Boolean access = userAccess.get(windowName);
        if (access != null) {
            return access;
        }
        return Boolean.FALSE;
    }
}
