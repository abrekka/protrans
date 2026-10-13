package no.ugland.utransprod.util;

import org.apache.log4j.Logger;
import org.jdesktop.jdic.desktop.internal.impl.WinAPIWrapper;
import org.jdesktop.jdic.desktop.internal.impl.WinBrowserService;
import org.jdesktop.jdic.desktop.internal.impl.WinLaunchService;
import org.jdesktop.jdic.desktop.internal.impl.WinMapiMailer;
import org.jdesktop.jdic.desktop.internal.impl.WinMozMailer;

public class ServiceManagerStubNy {
	private static final Logger LOGGER = Logger.getLogger(ServiceManagerStubNy.class);

	public static Object getService(String var0) throws IllegalArgumentException {
		LOGGER.info("Skal finne service for " + var0);
		if (var0.equals("LaunchService")) {
			return new WinLaunchService();
		} else if (var0.equals("BrowserService")) {
			return new WinBrowserService();
		} else if (!var0.equals("MailerService")) {
			throw new IllegalArgumentException("The requested service is not supported.");
		} else {
			LOGGER.info("Bruker getDefaultMailer");
			String var1 = getDefaultMailer();
			LOGGER.info("Finner MailerService fra registry SOFTWARE\\Clients\\Mail: " + var1);
			if (!var1.equalsIgnoreCase("Mozilla") && !var1.equalsIgnoreCase("Mozilla Thunderbird")) {
				if ((var1.equalsIgnoreCase("Microsoft Outlook") || var1.equalsIgnoreCase("Outlook Express"))
				// && isMapiSupported()
				) {
					return new WinMapiMailer();
				} else {
					throw new UnsupportedOperationException("Current system default mailer is not supported.");
				}
			} else {
				String var2 = getMozMailerLocation(var1);
				return new WinMozMailer(var2);
			}
		}
	}

	static String getMozMailerLocation(String var0) {
		String var1 = WinAPIWrapper.WinRegQueryValueEx(-2147483646,
				"SOFTWARE\\Clients\\Mail\\" + var0 + "\\shell\\open\\command", "");
		int var2 = var1.lastIndexOf(32);
		return var1.substring(0, var2);
	}

	static String getDefaultMailer() {
		String var0 = WinAPIWrapper.WinRegQueryValueEx(-2147483646, "SOFTWARE\\Clients\\Mail", "");
		return var0;
	}

	static boolean isMapiSupported() {
		String var0 = WinAPIWrapper.WinRegQueryValueEx(-2147483646, "SOFTWARE\\Microsoft\\Windows Messaging Subsystem",
				"MAPI");
		return var0 != null && var0.equals("1");
	}
}
