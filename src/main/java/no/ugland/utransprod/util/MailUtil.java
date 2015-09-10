package no.ugland.utransprod.util;

import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.ProTransException;

import org.jdesktop.jdic.desktop.Desktop;
import org.jdesktop.jdic.desktop.Message;

/**
 * Brukes til å sende mail.
 * 
 * @author atle.brekka
 */
public final class MailUtil {

    // private static String deviationSubject;

    // private static String deviationMessage;

    // private static boolean dllInitiated = false;

    private MailUtil() {

    }

    /**
     * Initierer.
     * 
     * @throws FileNotFoundException
     */
    /*
     * private static void init() throws FileNotFoundException { if
     * (!dllInitiated) { initDll(); } /*if (deviationSubject == null) {
     * initMailParams();
     * 
     * }
     */
    // }

    /*
     * private static void initMailParams() { deviationSubject =
     * ApplicationParamUtil .findParamByName("mail_deviation_subject");
     * deviationMessage = ApplicationParamUtil
     * .findParamByName("mail_deviation_message"); }
     */

    /*
     * private static void initDll() throws FileNotFoundException { URL dllUrl =
     * MailUtil.class.getClassLoader().getResource("jdic.dll"); if (dllUrl ==
     * null) { throw new FileNotFoundException("Finner ikke jdic.dll"); }
     * System.load(dllUrl.getPath()); dllInitiated = true; }
     */

    /**
     * Rensker tittel.
     */
    /*
     * public static void clear() { //deviationSubject = null; }
     */

    /**
     * Sender avviksmail.
     * 
     * @param pathToAttachment
     * @param fileName
     * @throws ProTransException
     * @throws FileNotFoundException
     */
    /*
     * public static void sendDeviationMail(final String pathToAttachment, final
     * String fileName) throws ProTransException, FileNotFoundException {
     * init(); sendMailWithAttachmentDesktop("", deviationSubject,
     * deviationMessage, pathToAttachment); }
     */

    /**
     * Sender mail til mailprogram som er satt opp på maskinen.
     * 
     * @param toMail
     * @param subject
     * @param message
     * @param pathToAttachment
     * @throws ProTransException
     */
    public static void sendMailWithAttachmentDesktop(final String toMail, final String subject, final String message, final String pathToAttachment)
	    throws ProTransException {
	try {
	    // init();
	    Message msg = new Message();
	    msg.setToAddrs(getToList(toMail));
	    msg.setSubject(subject);
	    msg.setBody(message);

	    List<String> attachList = new ArrayList<String>();
	    attachList.add(pathToAttachment);
	    msg.setAttachments(attachList);
	    Desktop.mail(msg);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new ProTransException(e);
	}
    }

    private static List<String> getToList(final String toMail) {
	List<String> toList = new ArrayList<String>();
	if (toMail != null && toMail.length() != 0) {
	    toList.add(toMail);

	}
	return toList;
    }

}
