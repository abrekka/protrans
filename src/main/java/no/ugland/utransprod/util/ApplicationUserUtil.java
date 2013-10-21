package no.ugland.utransprod.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.service.ApplicationUserManager;

public class ApplicationUserUtil {
	private static List<String> userNameList=null;
	private static ApplicationUserManager applicationUserManager;
	
	public static void setApplicationUserManager(ApplicationUserManager applicationUserManager){
		ApplicationUserUtil.applicationUserManager=applicationUserManager;
	}
	
	public static List<String> getUserList(boolean sort) {
		if(userNameList==null){
			Set<String> userNameSet=new HashSet<String>();
			userNameList=new ArrayList<String>();
			applicationUserManager=applicationUserManager!=null?applicationUserManager:(ApplicationUserManager)ModelUtil.getBean(ApplicationUserManager.MANAGER_NAME);
			
			userNameSet.addAll(applicationUserManager.findAllNamesNotGroup());
			userNameList.addAll(userNameSet);
			if(sort){
				Collections.sort(userNameList);
			}
		}
		
		
		return userNameList;
	}
}
