/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.gui.StartWindowEnum;
/*     */ import no.ugland.utransprod.model.UserType;
/*     */ import no.ugland.utransprod.model.UserTypeAccess;
/*     */ import no.ugland.utransprod.util.Util;
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
/*     */ public class UserTypeModel extends AbstractModel<UserType, UserTypeModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_DESCRIPTION = "description";
/*     */    public static final String PROPERTY_STARTUP_WINDOW = "startupWindow";
/*     */    public static final String PROPERTY_IS_ADMIN_BOOLEAN = "isAdminBoolean";
/*     */    public static final String PROPERTY_USER_TYPE_ACCESS_LIST = "userTypeAccessList";
/*     */    public static final String PROPERTY_STARTUP_WINDOW_ENUM = "startupWindowEnum";
/*     */    private List<UserTypeAccess> userTypeAccessList = new ArrayList();
/*     */ 
/*     */    public UserTypeModel(UserType object) {
/*  62 */       super(object);
/*     */ 
/*     */ 
/*  65 */       if (object.getUserTypeAccesses() != null) {
/*  66 */          this.userTypeAccessList.addAll(object.getUserTypeAccesses());
/*     */       }
/*  68 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDescription() {
/*  74 */       return ((UserType)this.object).getDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDescription(String description) {
/*  81 */       String oldDesc = this.getDescription();
/*  82 */       ((UserType)this.object).setDescription(description);
/*  83 */       this.firePropertyChange("description", oldDesc, description);
/*  84 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getStartupWindow() {
/*  90 */       return ((UserType)this.object).getStartupWindow();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setStartupWindow(String startupWindow) {
/*  97 */       String oldWin = this.getStartupWindow();
/*  98 */       ((UserType)this.object).setStartupWindow(startupWindow);
/*  99 */       this.firePropertyChange("startupWindow", oldWin, startupWindow);
/* 100 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public StartWindowEnum getStartupWindowEnum() {
/* 106 */       return ((UserType)this.object).getStartupWindowEnum();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setStartupWindowEnum(StartWindowEnum startupWindowEnum) {
/* 113 */       StartWindowEnum oldWin = this.getStartupWindowEnum();
/* 114 */       ((UserType)this.object).setStartupWindowEnum(startupWindowEnum);
/* 115 */       this.firePropertyChange("startupWindowEnum", oldWin, startupWindowEnum);
/*     */ 
/* 117 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean getIsAdminBoolean() {
/* 123 */       return Util.convertNumberToBoolean(((UserType)this.object).getIsAdmin());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setIsAdminBoolean(Boolean isAdmin) {
/* 130 */       Boolean oldAdmin = this.getIsAdminBoolean();
/* 131 */       ((UserType)this.object).setIsAdmin(Util.convertBooleanToNumber(isAdmin));
/* 132 */       this.firePropertyChange("isAdminBoolean", oldAdmin, isAdmin);
/* 133 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public List<UserTypeAccess> getUserTypeAccessList() {
/* 139 */       return new ArrayList(this.userTypeAccessList);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setUserTypeAccessList(List<UserTypeAccess> list) {
/* 146 */       this.userTypeAccessList.clear();
/* 147 */       this.userTypeAccessList.addAll(list);
/* 148 */       this.firePropertyChange("userTypeAccessList", list, list);
/* 149 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 158 */       presentationModel.getBufferedModel("description").addValueChangeListener(listener);
/*     */ 
/* 160 */       presentationModel.getBufferedModel("startupWindow").addValueChangeListener(listener);
/*     */ 
/* 162 */       presentationModel.getBufferedModel("userTypeAccessList").addValueChangeListener(listener);
/*     */ 
/* 164 */       presentationModel.getBufferedModel("startupWindowEnum").addValueChangeListener(listener);
/*     */ 
/*     */ 
/* 167 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public UserTypeModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 176 */       UserTypeModel model = new UserTypeModel(new UserType());
/* 177 */       model.setDescription((String)presentationModel.getBufferedValue("description"));
/*     */ 
/* 179 */       model.setStartupWindow((String)presentationModel.getBufferedValue("startupWindow"));
/*     */ 
/* 181 */       model.setUserTypeAccessList((List)presentationModel.getBufferedValue("userTypeAccessList"));
/*     */ 
/* 183 */       model.setStartupWindowEnum((StartWindowEnum)presentationModel.getBufferedValue("startupWindowEnum"));
/*     */ 
/* 185 */       return model;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void viewToModel() {
/* 193 */       Set<UserTypeAccess> userTypeAccesses = ((UserType)this.object).getUserTypeAccesses();
/* 194 */       if (userTypeAccesses == null) {
/* 195 */          userTypeAccesses = new HashSet();
/*     */       }
/* 197 */       ((Set)userTypeAccesses).clear();
/* 198 */       ((Set)userTypeAccesses).addAll(this.userTypeAccessList);
/* 199 */       ((UserType)this.object).setUserTypeAccesses((Set)userTypeAccesses);
/* 200 */    }
/*     */ }
