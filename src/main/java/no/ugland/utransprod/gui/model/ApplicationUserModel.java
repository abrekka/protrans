/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.ApplicationUser;
/*     */ import no.ugland.utransprod.model.JobFunction;
/*     */ import no.ugland.utransprod.model.Packagetype;
/*     */ import no.ugland.utransprod.model.ProductArea;
/*     */ import no.ugland.utransprod.model.UserProductAreaGroup;
/*     */ import no.ugland.utransprod.model.UserRole;
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
/*     */ public class ApplicationUserModel extends AbstractModel<ApplicationUser, ApplicationUserModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_FIRST_NAME = "firstName";
/*     */    public static final String PROPERTY_GROUP_USER = "groupUser";
/*     */    public static final String PROPERTY_LAST_NAME = "lastName";
/*     */    public static final String PROPERTY_PASSWORD = "password";
/*     */    public static final String PROPERTY_USER_NAME = "userName";
/*     */    public static final String PROPERTY_USER_ROLE_LIST = "userRoleList";
/*     */    public static final String PROPERTY_JOB_FUNCTION = "jobFunction";
/*     */    public static final String PROPERTY_PRODUCT_AREA = "productArea";
/*     */    public static final String PROPERTY_PACKAGE_TYPE = "packageType";
/*     */    public static final String PROPERTY_USER_PRODUCT_AREA_GROUP_LIST = "userProductAreaGroupList";
/*     */    private List<UserRole> userRoleList = new ArrayList();
/*     */    private List<UserProductAreaGroup> userProductAreaGroupList;
/*     */ 
/*     */    public ApplicationUserModel(ApplicationUser object) {
/*  90 */       super(object);
/*     */ 
/*     */ 
/*     */ 
/*  94 */       if (object.getUserRoles() != null) {
/*  95 */          this.userRoleList.addAll(object.getUserRoles());
/*     */       }
/*     */ 
/*  98 */       this.userProductAreaGroupList = new ArrayList();
/*     */ 
/* 100 */       if (object.getUserProductAreaGroups() != null) {
/* 101 */          this.userProductAreaGroupList.addAll(object.getUserProductAreaGroups());
/*     */       }
/* 103 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getFirstName() {
/* 109 */       return ((ApplicationUser)this.object).getFirstName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setFirstName(String firstName) {
/* 116 */       String oldName = this.getFirstName();
/* 117 */       ((ApplicationUser)this.object).setFirstName(firstName);
/* 118 */       this.firePropertyChange("firstName", oldName, firstName);
/* 119 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getGroupUser() {
/* 125 */       return ((ApplicationUser)this.object).getGroupUser();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setGroupUser(String groupUser) {
/* 132 */       String oldGroup = this.getGroupUser();
/* 133 */       ((ApplicationUser)this.object).setGroupUser(groupUser);
/* 134 */       this.firePropertyChange("groupUser", oldGroup, groupUser);
/* 135 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getLastName() {
/* 141 */       return ((ApplicationUser)this.object).getLastName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setLastName(String lastName) {
/* 148 */       String oldName = this.getLastName();
/* 149 */       ((ApplicationUser)this.object).setLastName(lastName);
/* 150 */       this.firePropertyChange("lastName", oldName, lastName);
/* 151 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPassword() {
/* 157 */       return ((ApplicationUser)this.object).getPassword();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPassword(String password) {
/* 164 */       String oldPass = this.getPassword();
/* 165 */       ((ApplicationUser)this.object).setPassword(password);
/* 166 */       this.firePropertyChange("password", oldPass, password);
/* 167 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getUserName() {
/* 173 */       return ((ApplicationUser)this.object).getUserName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setUserName(String userName) {
/* 180 */       String oldName = this.getUserName();
/* 181 */       ((ApplicationUser)this.object).setUserName(userName);
/* 182 */       this.firePropertyChange("userName", oldName, userName);
/* 183 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 191 */       presentationModel.getBufferedModel("firstName").addValueChangeListener(listener);
/* 192 */       presentationModel.getBufferedModel("groupUser").addValueChangeListener(listener);
/* 193 */       presentationModel.getBufferedModel("lastName").addValueChangeListener(listener);
/* 194 */       presentationModel.getBufferedModel("password").addValueChangeListener(listener);
/* 195 */       presentationModel.getBufferedModel("userName").addValueChangeListener(listener);
/* 196 */       presentationModel.getBufferedModel("userRoleList").addValueChangeListener(listener);
/* 197 */       presentationModel.getBufferedModel("jobFunction").addValueChangeListener(listener);
/* 198 */       presentationModel.getBufferedModel("productArea").addValueChangeListener(listener);
/* 199 */       presentationModel.getBufferedModel("packageType").addValueChangeListener(listener);
/* 200 */       presentationModel.getBufferedModel("userProductAreaGroupList").addValueChangeListener(listener);
/* 201 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ApplicationUserModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 209 */       ApplicationUserModel model = new ApplicationUserModel(new ApplicationUser());
/* 210 */       model.setFirstName((String)presentationModel.getBufferedValue("firstName"));
/* 211 */       model.setGroupUser((String)presentationModel.getBufferedValue("groupUser"));
/* 212 */       model.setLastName((String)presentationModel.getBufferedValue("lastName"));
/* 213 */       model.setPassword((String)presentationModel.getBufferedValue("password"));
/* 214 */       model.setUserName((String)presentationModel.getBufferedValue("userName"));
/* 215 */       model.setUserRoleList((List)presentationModel.getBufferedValue("userRoleList"));
/* 216 */       model.setJobFunction((JobFunction)presentationModel.getBufferedValue("jobFunction"));
/* 217 */       model.setProductArea((ProductArea)presentationModel.getBufferedValue("productArea"));
/* 218 */       model.setPackageType((Packagetype)presentationModel.getBufferedValue("packageType"));
/* 219 */       model.setUserProductAreaGroupList((List)presentationModel.getBufferedValue("userProductAreaGroupList"));
/* 220 */       return model;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<UserRole> getUserRoleList() {
/* 227 */       return new ArrayList(this.userRoleList);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setUserRoleList(List<UserRole> userRoleList) {
/* 234 */       List<UserRole> oldRoles = this.getUserRoleList();
/* 235 */       if (userRoleList != null) {
/* 236 */          this.userRoleList = new ArrayList(userRoleList);
/*     */       } else {
/* 238 */          this.userRoleList.clear();
/*     */       }
/* 240 */       this.firePropertyChange("userRoleList", oldRoles, userRoleList);
/* 241 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<UserProductAreaGroup> getUserProductAreaGroupList() {
/* 247 */       return new ArrayList(this.userProductAreaGroupList);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setUserProductAreaGroupList(List<UserProductAreaGroup> userProductAreaGroupList) {
/* 254 */       List<UserProductAreaGroup> oldGroups = this.getUserProductAreaGroupList();
/* 255 */       if (userProductAreaGroupList != null) {
/* 256 */          this.userProductAreaGroupList = new ArrayList(userProductAreaGroupList);
/*     */       } else {
/* 258 */          this.userProductAreaGroupList.clear();
/*     */       }
/* 260 */       this.firePropertyChange("userProductAreaGroupList", oldGroups, userProductAreaGroupList);
/* 261 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void viewToModel() {
/*     */       Object groups;
/* 269 */       if (this.userRoleList != null) {
/* 270 */          groups = ((ApplicationUser)this.object).getUserRoles();
/* 271 */          if (groups == null) {
/* 272 */             groups = new HashSet();
/*     */          }
/* 274 */          ((Set)groups).clear();
/* 275 */          ((Set)groups).addAll(this.userRoleList);
/* 276 */          ((ApplicationUser)this.object).setUserRoles((Set)groups);
/*     */       }
/*     */ 
/* 279 */       if (this.userProductAreaGroupList != null) {
/* 280 */          groups = ((ApplicationUser)this.object).getUserProductAreaGroups();
/* 281 */          if (groups == null) {
/* 282 */             groups = new HashSet();
/*     */          }
/* 284 */          ((Set)groups).clear();
/* 285 */          ((Set)groups).addAll(this.userProductAreaGroupList);
/* 286 */          ((ApplicationUser)this.object).setUserProductAreaGroups((Set)groups);
/*     */       }
/* 288 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void modelToView() {
/* 295 */       if (((ApplicationUser)this.object).getUserRoles() != null) {
/* 296 */          this.userRoleList.clear();
/* 297 */          this.userRoleList.addAll(((ApplicationUser)this.object).getUserRoles());
/*     */       }
/* 299 */       if (((ApplicationUser)this.object).getUserProductAreaGroups() != null) {
/* 300 */          this.userProductAreaGroupList.clear();
/* 301 */          this.userProductAreaGroupList.addAll(((ApplicationUser)this.object).getUserProductAreaGroups());
/*     */       }
/* 303 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public JobFunction getJobFunction() {
/* 309 */       return ((ApplicationUser)this.object).getJobFunction();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setJobFunction(JobFunction jobFunction) {
/* 316 */       JobFunction oldFunction = this.getJobFunction();
/* 317 */       ((ApplicationUser)this.object).setJobFunction(jobFunction);
/* 318 */       this.firePropertyChange("jobFunction", oldFunction, jobFunction);
/* 319 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ProductArea getProductArea() {
/* 325 */       return ((ApplicationUser)this.object).getProductArea();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setProductArea(ProductArea productArea) {
/* 332 */       ProductArea oldArea = this.getProductArea();
/* 333 */       ((ApplicationUser)this.object).setProductArea(productArea);
/* 334 */       this.firePropertyChange("productArea", oldArea, productArea);
/* 335 */    }
/*     */ 
/*     */    public Packagetype getPackageType() {
/* 338 */       return Packagetype.getPackageType(((ApplicationUser)this.object).getPackageType());
/*     */    }
/*     */ 
/*     */    public void setPackageType(Packagetype packagetype) {
/* 342 */       Packagetype oldPackagetype = this.getPackageType();
/* 343 */       ((ApplicationUser)this.object).setPackageType(Packagetype.ALLE.equals(packagetype) ? null : packagetype.getVerdi());
/* 344 */       this.firePropertyChange("packageType", oldPackagetype, packagetype);
/* 345 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void firePropertyChanged() {
/* 351 */       this.fireMultiplePropertiesChanged();
/* 352 */    }
/*     */ }
