/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import no.ugland.utransprod.model.ApplicationParam;
/*     */ import no.ugland.utransprod.util.ApplicationParamUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApplicationParamModel extends AbstractModel<ApplicationParam, ApplicationParamModel> {
/*     */    private static final long serialVersionUID = 5172694792745020559L;
/*     */    public static final String PROPERTY_MAIL_HOST_NAME = "mailHostName";
/*     */    public static final String PROPERTY_MAIL_HOST_PASSWORD = "mailHostPassword";
/*     */    public static final String PROPERTY_MAIL_FROM_MAIL = "mailFromMail";
/*     */    public static final String PROPERTY_MAIL_FROM_NAME = "mailFromName";
/*     */    public static final String PROPERTY_MAIL_DEVIATION_SUBJECT = "mailDeviationSubject";
/*     */    public static final String PROPERTY_MAIL_DEVIATION_MESSAGE = "mailDeviationMessage";
/*     */    public static final String PROPERTY_MAIL_DEVIATION_ATTACHMENT_DESCRIPTION = "mailDeviationAttachmentDescription";
/*     */    public static final String PROPERTY_DEVIATION_MANAGER = "deviationManager";
/*     */    public static final String PROPERTY_COLLI_PARAMS = "colliParams";
/*     */    public static final String PROPERTY_TAKSTOL_ARTICLE_PARAMS = "takstolArticleParams";
/*     */    public static final String PROPERTY_NOT_PACKAGE_PARAM = "notPackageParam";
/*     */    public static final String PROPERTY_PARAM_NAME = "paramName";
/*     */    public static final String PROPERTY_PARAM_VALUE = "paramValue";
/*     */    public static final String PROPERTY_HMS_MANAGER = "hmsManager";
/*     */    private static Map<String, ApplicationParam> mailSetup;
/*     */    private static ApplicationParam deviationManagerParam;
/*     */    private List<ApplicationParam> colliParams;
/*     */    private static List<ApplicationParam> takstolArticleParams;
/*     */    private static ApplicationParam notPackageParam;
/*     */    private static ApplicationParam hmsManagerParam;
/*     */ 
/*     */    public ApplicationParamModel(ApplicationParam object) {
/*  67 */       super(object);
/*  68 */       this.init();
/*  69 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    private void init() {
/*  75 */       if (mailSetup == null) {
/*  76 */          mailSetup = ApplicationParamUtil.getMailSetup();
/*     */       }
/*  78 */       if (deviationManagerParam == null) {
/*  79 */          deviationManagerParam = ApplicationParamUtil.getDeviationManager();
/*     */       }
/*  81 */       if (hmsManagerParam == null) {
/*  82 */          hmsManagerParam = ApplicationParamUtil.getHmsManager();
/*     */       }
/*  84 */       if (this.colliParams == null) {
/*  85 */          this.colliParams = ApplicationParamUtil.getColliSetupParams();
/*     */       }
/*  87 */       if (takstolArticleParams == null) {
/*  88 */          takstolArticleParams = ApplicationParamUtil.getTakstolArticleSetupParams();
/*     */       }
/*  90 */       if (notPackageParam == null) {
/*  91 */          notPackageParam = ApplicationParamUtil.getNotPackageParam();
/*     */       }
/*  93 */    }
/*     */ 
/*     */    public List<ApplicationParam> getColliParams() {
/*  96 */       this.init();
/*  97 */       return new ArrayList(this.colliParams);
/*     */    }
/*     */    public void setColliParams(List<ApplicationParam> params) {
/* 100 */       List<ApplicationParam> oldParams = this.getColliParams();
/* 101 */       this.colliParams.clear();
/* 102 */       this.colliParams.addAll(params);
/* 103 */       this.firePropertyChange("colliParams", oldParams, params);
/* 104 */    }
/*     */    public List<ApplicationParam> getTakstolArticleParams() {
/* 106 */       this.init();
/* 107 */       return new ArrayList(takstolArticleParams);
/*     */    }
/*     */    public void setTakstolArticleParams(List<ApplicationParam> params) {
/* 110 */       List<ApplicationParam> oldParams = this.getTakstolArticleParams();
/* 111 */       takstolArticleParams.clear();
/* 112 */       takstolArticleParams.addAll(params);
/* 113 */       this.firePropertyChange("takstolArticleParams", oldParams, params);
/* 114 */    }
/*     */    public ApplicationParam getNotPackageParam() {
/* 116 */       return notPackageParam;
/*     */    }
/*     */    public void setNotPackageParam(ApplicationParam param) {
/* 119 */       ApplicationParam oldParam = this.getNotPackageParam();
/* 120 */       notPackageParam = param;
/* 121 */       this.firePropertyChange("notPackageParam", oldParam, param);
/* 122 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getMailHostName() {
/* 128 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_host_name");
/* 129 */       return param != null ? param.getParamValue() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setMailHostName(String mailHostName) {
/* 139 */       String oldName = this.getMailHostName();
/* 140 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_host_name");
/* 141 */       if (param == null) {
/* 142 */          param = new ApplicationParam();
/* 143 */          param.setParamName("mail_host_name");
/*     */       }
/*     */ 
/* 146 */       param.setParamValue(mailHostName);
/* 147 */       mailSetup.put("mail_host_name", param);
/* 148 */       this.firePropertyChange("mailHostName", oldName, mailHostName);
/* 149 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getMailHostPassword() {
/* 155 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_host_password");
/* 156 */       return param != null ? param.getParamValue() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setMailHostPassword(String mailHostPassword) {
/* 166 */       String oldPassword = this.getMailHostPassword();
/* 167 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_host_password");
/* 168 */       if (param == null) {
/* 169 */          param = new ApplicationParam();
/* 170 */          param.setParamName("mail_host_password");
/*     */       }
/*     */ 
/* 173 */       param.setParamValue(mailHostPassword);
/* 174 */       mailSetup.put("mail_host_password", param);
/* 175 */       this.firePropertyChange("mailHostPassword", oldPassword, mailHostPassword);
/*     */ 
/* 177 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getMailFromMail() {
/* 183 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_from_mail");
/* 184 */       return param != null ? param.getParamValue() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setMailFromMail(String mailFromMail) {
/* 194 */       String oldMail = this.getMailFromMail();
/* 195 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_from_mail");
/* 196 */       if (param == null) {
/* 197 */          param = new ApplicationParam();
/* 198 */          param.setParamName("mail_from_mail");
/*     */       }
/*     */ 
/* 201 */       param.setParamValue(mailFromMail);
/* 202 */       mailSetup.put("mail_from_mail", param);
/* 203 */       this.firePropertyChange("mailFromMail", oldMail, mailFromMail);
/* 204 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getMailFromName() {
/* 210 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_from_name");
/* 211 */       return param != null ? param.getParamValue() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setMailFromName(String mailFromName) {
/* 221 */       String oldName = this.getMailFromName();
/* 222 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_from_name");
/* 223 */       if (param == null) {
/* 224 */          param = new ApplicationParam();
/* 225 */          param.setParamName("mail_from_name");
/*     */       }
/*     */ 
/* 228 */       param.setParamValue(mailFromName);
/* 229 */       mailSetup.put("mail_from_name", param);
/* 230 */       this.firePropertyChange("mailFromName", oldName, mailFromName);
/* 231 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getMailDeviationSubject() {
/* 237 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_deviation_subject");
/* 238 */       return param != null ? param.getParamValue() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setMailDeviationSubject(String mailDeviationSubject) {
/* 248 */       String oldSubject = this.getMailDeviationSubject();
/* 249 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_deviation_subject");
/* 250 */       if (param == null) {
/* 251 */          param = new ApplicationParam();
/* 252 */          param.setParamName("mail_deviation_subject");
/*     */       }
/*     */ 
/* 255 */       param.setParamValue(mailDeviationSubject);
/* 256 */       mailSetup.put("mail_deviation_subject", param);
/* 257 */       this.firePropertyChange("mailDeviationSubject", oldSubject, mailDeviationSubject);
/*     */ 
/* 259 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getMailDeviationMessage() {
/* 265 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_deviation_message");
/* 266 */       return param != null ? param.getParamValue() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setMailDeviationMessage(String mailDeviationMessage) {
/* 276 */       String oldMessage = this.getMailDeviationMessage();
/* 277 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_deviation_message");
/* 278 */       if (param == null) {
/* 279 */          param = new ApplicationParam();
/* 280 */          param.setParamName("mail_deviation_message");
/*     */       }
/*     */ 
/* 283 */       param.setParamValue(mailDeviationMessage);
/* 284 */       mailSetup.put("mail_deviation_message", param);
/* 285 */       this.firePropertyChange("mailDeviationMessage", oldMessage, mailDeviationMessage);
/*     */ 
/* 287 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getMailDeviationAttachmentDescription() {
/* 293 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_deviation_attachment_description");
/*     */ 
/* 295 */       return param != null ? param.getParamValue() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setMailDeviationAttachmentDescription(String mailDeviationDesc) {
/* 305 */       String oldDesc = this.getMailDeviationAttachmentDescription();
/* 306 */       ApplicationParam param = (ApplicationParam)mailSetup.get("mail_deviation_attachment_description");
/*     */ 
/* 308 */       if (param == null) {
/* 309 */          param = new ApplicationParam();
/* 310 */          param.setParamName("mail_deviation_attachment_description");
/*     */       }
/*     */ 
/* 313 */       param.setParamValue(mailDeviationDesc);
/* 314 */       mailSetup.put("mail_deviation_attachment_description", param);
/* 315 */       this.firePropertyChange("mailDeviationAttachmentDescription", oldDesc, mailDeviationDesc);
/*     */ 
/* 317 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDeviationManager() {
/* 323 */       return deviationManagerParam != null ? deviationManagerParam.getParamValue() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDeviationManager(String deviationManager) {
/* 333 */       String oldmanager = this.getDeviationManager();
/*     */ 
/* 335 */       if (deviationManagerParam == null) {
/* 336 */          deviationManagerParam = new ApplicationParam();
/* 337 */          deviationManagerParam.setParamName("deviation_manager");
/*     */       }
/*     */ 
/* 340 */       deviationManagerParam.setParamValue(deviationManager);
/*     */ 
/* 342 */       this.firePropertyChange("deviationManager", oldmanager, deviationManager);
/*     */ 
/* 344 */    }
/*     */ 
/*     */    public String getHmsManager() {
/* 347 */       return hmsManagerParam != null ? hmsManagerParam.getParamValue() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setHmsManager(String hmsManager) {
/* 357 */       String oldmanager = this.getHmsManager();
/*     */ 
/* 359 */       if (hmsManagerParam == null) {
/* 360 */          hmsManagerParam = new ApplicationParam();
/* 361 */          hmsManagerParam.setParamName("hms_leder");
/*     */       }
/*     */ 
/* 364 */       hmsManagerParam.setParamValue(hmsManager);
/*     */ 
/* 366 */       this.firePropertyChange("hmsManager", oldmanager, hmsManager);
/*     */ 
/* 368 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public static Collection<ApplicationParam> getMailParams() {
/* 374 */       return mailSetup.values();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public static ApplicationParam getDeviationManagerParam() {
/* 381 */       return deviationManagerParam;
/*     */    }
/*     */ 
/*     */    public static ApplicationParam getHmsManagerParam() {
/* 385 */       return hmsManagerParam;
/*     */    }
/*     */ 
/*     */    public String getParamName() {
/* 389 */       return ((ApplicationParam)this.object).getParamName();
/*     */    }
/*     */    public void setParamName(String name) {
/* 392 */       String oldName = this.getParamName();
/* 393 */       ((ApplicationParam)this.object).setParamName(name);
/* 394 */       this.firePropertyChange("paramName", oldName, name);
/* 395 */    }
/*     */    public String getParamValue() {
/* 397 */       return ((ApplicationParam)this.object).getParamValue();
/*     */    }
/*     */    public void setParamValue(String value) {
/* 400 */       String oldValue = this.getParamValue();
/* 401 */       ((ApplicationParam)this.object).setParamValue(value);
/* 402 */       this.firePropertyChange("paramValue", oldValue, value);
/* 403 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 412 */       presentationModel.getBufferedModel("mailHostName").addValueChangeListener(listener);
/*     */ 
/* 414 */       presentationModel.getBufferedModel("mailHostPassword").addValueChangeListener(listener);
/*     */ 
/* 416 */       presentationModel.getBufferedModel("mailFromMail").addValueChangeListener(listener);
/*     */ 
/* 418 */       presentationModel.getBufferedModel("mailFromName").addValueChangeListener(listener);
/*     */ 
/* 420 */       presentationModel.getBufferedModel("mailDeviationSubject").addValueChangeListener(listener);
/*     */ 
/* 422 */       presentationModel.getBufferedModel("mailDeviationMessage").addValueChangeListener(listener);
/*     */ 
/* 424 */       presentationModel.getBufferedModel("mailDeviationAttachmentDescription").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 427 */       presentationModel.getBufferedModel("deviationManager").addValueChangeListener(listener);
/*     */ 
/* 429 */       presentationModel.getBufferedModel("colliParams").addValueChangeListener(listener);
/*     */ 
/* 431 */       presentationModel.getBufferedModel("paramName").addValueChangeListener(listener);
/*     */ 
/* 433 */       presentationModel.getBufferedModel("paramValue").addValueChangeListener(listener);
/*     */ 
/* 435 */       presentationModel.getBufferedModel("hmsManager").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 438 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ApplicationParamModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 446 */       ApplicationParamModel model = new ApplicationParamModel(new ApplicationParam());
/* 447 */       model.setParamName((String)presentationModel.getBufferedValue("paramName"));
/* 448 */       model.setParamValue((String)presentationModel.getBufferedValue("paramValue"));
/* 449 */       return model;
/*     */    }
/*     */ }
