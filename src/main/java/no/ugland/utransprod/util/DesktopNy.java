package no.ugland.utransprod.util;

import org.apache.log4j.Logger;
import org.jdesktop.jdic.desktop.DesktopException;
import org.jdesktop.jdic.desktop.Message;
import org.jdesktop.jdic.desktop.internal.LaunchFailedException;
import org.jdesktop.jdic.desktop.internal.MailerService;
import org.jdesktop.jdic.desktop.internal.ServiceManager;

public class DesktopNy {
	private static final Logger LOGGER = Logger.getLogger(DesktopNy.class);

	public static void mail(Message var0) throws DesktopException {
	      if (var0 == null) {
	         throw new DesktopException("The given message is null.");
	      } else {
	    	  LOGGER.info("Finner MailerService");
	         MailerService var1 = (MailerService)ServiceManagerStubNy.getService("MailerService");

	         try {
	        	 LOGGER.info("Sender mail via MailerService");
	            var1.open(var0);
	         } catch (LaunchFailedException var3) {
	            throw new DesktopException(var3.getMessage());
	         }
	      }
	   }
}
