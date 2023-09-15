package no.ugland.utransprod.util;

import org.jdesktop.jdic.desktop.DesktopException;
import org.jdesktop.jdic.desktop.Message;
import org.jdesktop.jdic.desktop.internal.LaunchFailedException;
import org.jdesktop.jdic.desktop.internal.MailerService;
import org.jdesktop.jdic.desktop.internal.ServiceManager;

public class DesktopNy {

	public static void mail(Message var0) throws DesktopException {
	      if (var0 == null) {
	         throw new DesktopException("The given message is null.");
	      } else {
	         MailerService var1 = (MailerService)ServiceManagerStubNy.getService("MailerService");

	         try {
	            var1.open(var0);
	         } catch (LaunchFailedException var3) {
	            throw new DesktopException(var3.getMessage());
	         }
	      }
	   }
}
