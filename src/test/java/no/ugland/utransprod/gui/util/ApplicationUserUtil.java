package no.ugland.utransprod.gui.util;

import java.util.List;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.ModelUtil;

public class ApplicationUserUtil {
    private static ApplicationUser applicationUser;
    private static UserType userType;
    private static List<String> userNameList=null;
    
    public static ApplicationUser getUser(){
        return applicationUser!=null?applicationUser:initUser("admin","admin");
    }
    public static ApplicationUser getUser(String userName,String password){
        applicationUser=initUser(userName,password);
        return applicationUser;
    }
    public static UserType getUserType(){
        return userType!=null?userType:initUserType();
    }
    private static ApplicationUser initUser(String userName,String password){
        ApplicationUserManager applicationUserManager=(ApplicationUserManager)ModelUtil.getBean(ApplicationUserManager.MANAGER_NAME);
        return applicationUserManager.login(userName, password);
    }
    private static UserType initUserType(){
        ApplicationUserManager applicationUserManager=(ApplicationUserManager)ModelUtil.getBean(ApplicationUserManager.MANAGER_NAME);
        ApplicationUser user=getUser();
        applicationUserManager.lazyLoad(user, new LazyLoadEnum[][]{{LazyLoadEnum.USER_ROLES,LazyLoadEnum.NONE}});
        return user.getUserRoles().iterator().next().getUserType();
    }
	public static List<String> getUserList() {
		if(userNameList==null){
			ApplicationUserManager applicationUserManager=(ApplicationUserManager)ModelUtil.getBean(ApplicationUserManager.MANAGER_NAME);
			userNameList=applicationUserManager.findAllNamesNotGroup();
		}
		return userNameList;
	}
}
