package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.model.ApplicationParam;
import no.ugland.utransprod.util.ApplicationParamUtil;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for APPLICATION_PARAM
 * 
 * @author atle.brekka
 * 
 */
public class ApplicationParamModel extends
		AbstractModel<ApplicationParam, ApplicationParamModel> {
	private static final long serialVersionUID = 5172694792745020559L;

	public static final String PROPERTY_MAIL_HOST_NAME = "mailHostName";

	public static final String PROPERTY_MAIL_HOST_PASSWORD = "mailHostPassword";

	public static final String PROPERTY_MAIL_FROM_MAIL = "mailFromMail";

	public static final String PROPERTY_MAIL_FROM_NAME = "mailFromName";

	public static final String PROPERTY_MAIL_DEVIATION_SUBJECT = "mailDeviationSubject";

	public static final String PROPERTY_MAIL_DEVIATION_MESSAGE = "mailDeviationMessage";

	public static final String PROPERTY_MAIL_DEVIATION_ATTACHMENT_DESCRIPTION = "mailDeviationAttachmentDescription";

	public static final String PROPERTY_DEVIATION_MANAGER = "deviationManager";
	public static final String PROPERTY_COLLI_PARAMS = "colliParams";
	public static final String PROPERTY_TAKSTOL_ARTICLE_PARAMS = "takstolArticleParams";
	public static final String PROPERTY_NOT_PACKAGE_PARAM = "notPackageParam";

	public static final String PROPERTY_PARAM_NAME = "paramName";

	public static final String PROPERTY_PARAM_VALUE = "paramValue";

	public static final String PROPERTY_HMS_MANAGER = "hmsManager";

	/**
	 * Inneholder alle parametre for mailoppsett
	 */
	private static Map<String, ApplicationParam> mailSetup;

	/**
	 * Avviksansvarlig
	 */
	private static ApplicationParam deviationManagerParam;
	private List<ApplicationParam> colliParams;
	private static List<ApplicationParam> takstolArticleParams;
	private static ApplicationParam notPackageParam;
	private static ApplicationParam hmsManagerParam;

	/**
	 * @param object
	 */
	public ApplicationParamModel(ApplicationParam object) {
		super(object);
		init();
	}
	
	/**
	 * Initierer variable
	 */
	private void init() {
		if (mailSetup == null) {
			mailSetup = ApplicationParamUtil.getMailSetup();
		}
		if (deviationManagerParam == null) {
			deviationManagerParam = ApplicationParamUtil.getDeviationManager();
		}
		if (hmsManagerParam == null) {
			hmsManagerParam = ApplicationParamUtil.getHmsManager();
		}
		if(colliParams==null){
		    colliParams=ApplicationParamUtil.getColliSetupParams();
		}
		if(takstolArticleParams==null){
		    takstolArticleParams=ApplicationParamUtil.getTakstolArticleSetupParams();
        }
		if(notPackageParam==null){
		    notPackageParam = ApplicationParamUtil.getNotPackageParam();
		}
	}
	
	public List<ApplicationParam> getColliParams(){
	    init();
	    return new ArrayList<ApplicationParam>(colliParams);
	}
	public void setColliParams(List<ApplicationParam> params){
	    List<ApplicationParam> oldParams = getColliParams();
        colliParams.clear();
        colliParams.addAll(params);
        firePropertyChange(PROPERTY_COLLI_PARAMS, oldParams, params);
    }
	public List<ApplicationParam> getTakstolArticleParams(){
        init();
        return new ArrayList<ApplicationParam>(takstolArticleParams);
    }
    public void setTakstolArticleParams(List<ApplicationParam> params){
        List<ApplicationParam> oldParams = getTakstolArticleParams();
        takstolArticleParams.clear();
        takstolArticleParams.addAll(params);
        firePropertyChange(PROPERTY_TAKSTOL_ARTICLE_PARAMS, oldParams, params);
    }
    public ApplicationParam getNotPackageParam(){
        return notPackageParam;
    }
    public void setNotPackageParam(ApplicationParam param){
        ApplicationParam oldParam=getNotPackageParam();
        notPackageParam=param;
        firePropertyChange(PROPERTY_NOT_PACKAGE_PARAM, oldParam, param);
    }

	/**
	 * @return mailhost
	 */
	public String getMailHostName() {
		ApplicationParam param = mailSetup.get("mail_host_name");
		if (param != null) {
			return param.getParamValue();
		}
		return null;
	}

	/**
	 * @param mailHostName
	 */
	public void setMailHostName(String mailHostName) {
		String oldName = getMailHostName();
		ApplicationParam param = mailSetup.get("mail_host_name");
		if (param == null) {
			param = new ApplicationParam();
			param.setParamName("mail_host_name");

		}
		param.setParamValue(mailHostName);
		mailSetup.put("mail_host_name", param);
		firePropertyChange(PROPERTY_MAIL_HOST_NAME, oldName, mailHostName);
	}

	/**
	 * @return passord for mailhost
	 */
	public String getMailHostPassword() {
		ApplicationParam param = mailSetup.get("mail_host_password");
		if (param != null) {
			return param.getParamValue();
		}
		return null;
	}

	/**
	 * @param mailHostPassword
	 */
	public void setMailHostPassword(String mailHostPassword) {
		String oldPassword = getMailHostPassword();
		ApplicationParam param = mailSetup.get("mail_host_password");
		if (param == null) {
			param = new ApplicationParam();
			param.setParamName("mail_host_password");

		}
		param.setParamValue(mailHostPassword);
		mailSetup.put("mail_host_password", param);
		firePropertyChange(PROPERTY_MAIL_HOST_PASSWORD, oldPassword,
				mailHostPassword);
	}

	/**
	 * @return fra mailadresse
	 */
	public String getMailFromMail() {
		ApplicationParam param = mailSetup.get("mail_from_mail");
		if (param != null) {
			return param.getParamValue();
		}
		return null;
	}

	/**
	 * @param mailFromMail
	 */
	public void setMailFromMail(String mailFromMail) {
		String oldMail = getMailFromMail();
		ApplicationParam param = mailSetup.get("mail_from_mail");
		if (param == null) {
			param = new ApplicationParam();
			param.setParamName("mail_from_mail");

		}
		param.setParamValue(mailFromMail);
		mailSetup.put("mail_from_mail", param);
		firePropertyChange(PROPERTY_MAIL_FROM_MAIL, oldMail, mailFromMail);
	}

	/**
	 * @return fra navn
	 */
	public String getMailFromName() {
		ApplicationParam param = mailSetup.get("mail_from_name");
		if (param != null) {
			return param.getParamValue();
		}
		return null;
	}

	/**
	 * @param mailFromName
	 */
	public void setMailFromName(String mailFromName) {
		String oldName = getMailFromName();
		ApplicationParam param = mailSetup.get("mail_from_name");
		if (param == null) {
			param = new ApplicationParam();
			param.setParamName("mail_from_name");

		}
		param.setParamValue(mailFromName);
		mailSetup.put("mail_from_name", param);
		firePropertyChange(PROPERTY_MAIL_FROM_NAME, oldName, mailFromName);
	}

	/**
	 * @return overskrift for avviksmail
	 */
	public String getMailDeviationSubject() {
		ApplicationParam param = mailSetup.get("mail_deviation_subject");
		if (param != null) {
			return param.getParamValue();
		}
		return null;
	}

	/**
	 * @param mailDeviationSubject
	 */
	public void setMailDeviationSubject(String mailDeviationSubject) {
		String oldSubject = getMailDeviationSubject();
		ApplicationParam param = mailSetup.get("mail_deviation_subject");
		if (param == null) {
			param = new ApplicationParam();
			param.setParamName("mail_deviation_subject");

		}
		param.setParamValue(mailDeviationSubject);
		mailSetup.put("mail_deviation_subject", param);
		firePropertyChange(PROPERTY_MAIL_DEVIATION_SUBJECT, oldSubject,
				mailDeviationSubject);
	}

	/**
	 * @return melding for avviksmail
	 */
	public String getMailDeviationMessage() {
		ApplicationParam param = mailSetup.get("mail_deviation_message");
		if (param != null) {
			return param.getParamValue();
		}
		return null;
	}

	/**
	 * @param mailDeviationMessage
	 */
	public void setMailDeviationMessage(String mailDeviationMessage) {
		String oldMessage = getMailDeviationMessage();
		ApplicationParam param = mailSetup.get("mail_deviation_message");
		if (param == null) {
			param = new ApplicationParam();
			param.setParamName("mail_deviation_message");

		}
		param.setParamValue(mailDeviationMessage);
		mailSetup.put("mail_deviation_message", param);
		firePropertyChange(PROPERTY_MAIL_DEVIATION_MESSAGE, oldMessage,
				mailDeviationMessage);
	}

	/**
	 * @return beskrivelse av vedlegg for avvik
	 */
	public String getMailDeviationAttachmentDescription() {
		ApplicationParam param = mailSetup
				.get("mail_deviation_attachment_description");
		if (param != null) {
			return param.getParamValue();
		}
		return null;
	}

	/**
	 * @param mailDeviationDesc
	 */
	public void setMailDeviationAttachmentDescription(String mailDeviationDesc) {
		String oldDesc = getMailDeviationAttachmentDescription();
		ApplicationParam param = mailSetup
				.get("mail_deviation_attachment_description");
		if (param == null) {
			param = new ApplicationParam();
			param.setParamName("mail_deviation_attachment_description");

		}
		param.setParamValue(mailDeviationDesc);
		mailSetup.put("mail_deviation_attachment_description", param);
		firePropertyChange(PROPERTY_MAIL_DEVIATION_ATTACHMENT_DESCRIPTION,
				oldDesc, mailDeviationDesc);
	}

	/**
	 * @return avviksansvarlig
	 */
	public String getDeviationManager() {
		if (deviationManagerParam != null) {
			return deviationManagerParam.getParamValue();
		}
		return null;
	}

	/**
	 * @param deviationManager
	 */
	public void setDeviationManager(String deviationManager) {
		String oldmanager = getDeviationManager();

		if (deviationManagerParam == null) {
			deviationManagerParam = new ApplicationParam();
			deviationManagerParam.setParamName("deviation_manager");

		}
		deviationManagerParam.setParamValue(deviationManager);

		firePropertyChange(PROPERTY_DEVIATION_MANAGER, oldmanager,
				deviationManager);
	}
	
	public String getHmsManager() {
		if (hmsManagerParam != null) {
			return hmsManagerParam.getParamValue();
		}
		return null;
	}

	/**
	 * @param deviationManager
	 */
	public void setHmsManager(String hmsManager) {
		String oldmanager = getHmsManager();

		if (hmsManagerParam == null) {
			hmsManagerParam = new ApplicationParam();
			hmsManagerParam.setParamName("hms_leder");

		}
		hmsManagerParam.setParamValue(hmsManager);

		firePropertyChange(PROPERTY_HMS_MANAGER, oldmanager,
				hmsManager);
	}

	/**
	 * @return mailoppsett
	 */
	public static Collection<ApplicationParam> getMailParams() {
		return mailSetup.values();
	}

	/**
	 * @return avviksansvarlig
	 */
	public static ApplicationParam getDeviationManagerParam() {
		return deviationManagerParam;
	}
	
	public static ApplicationParam getHmsManagerParam() {
		return hmsManagerParam;
	}
	
	public String getParamName(){
		return object.getParamName();
	}
	public void setParamName(String name){
		String oldName=getParamName();
		object.setParamName(name);
		firePropertyChange(PROPERTY_PARAM_NAME, oldName, name);
	}
	public String getParamValue(){
		return object.getParamValue();
	}
	public void setParamValue(String value){
		String oldValue=getParamValue();
		object.setParamValue(value);
		firePropertyChange(PROPERTY_PARAM_VALUE, oldValue, value);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
	 *      com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel) {
		presentationModel.getBufferedModel(PROPERTY_MAIL_HOST_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_MAIL_HOST_PASSWORD)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_MAIL_FROM_MAIL)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_MAIL_FROM_NAME)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_MAIL_DEVIATION_SUBJECT)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_MAIL_DEVIATION_MESSAGE)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(
				PROPERTY_MAIL_DEVIATION_ATTACHMENT_DESCRIPTION)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_DEVIATION_MANAGER)
				.addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_COLLI_PARAMS)
        .addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PARAM_NAME)
        .addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_PARAM_VALUE)
        .addValueChangeListener(listener);
		presentationModel.getBufferedModel(PROPERTY_HMS_MANAGER)
		.addValueChangeListener(listener);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
	 */
	@Override
	public ApplicationParamModel getBufferedObjectModel(
			PresentationModel presentationModel) {
		ApplicationParamModel model=new ApplicationParamModel(new ApplicationParam());
		model.setParamName((String)presentationModel.getBufferedValue(PROPERTY_PARAM_NAME));
		model.setParamValue((String)presentationModel.getBufferedValue(PROPERTY_PARAM_VALUE));
		return model;
	}

}
