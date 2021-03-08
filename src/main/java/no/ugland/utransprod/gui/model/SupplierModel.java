/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.model.Supplier;
/*     */ import no.ugland.utransprod.model.SupplierProductAreaGroup;
/*     */ import no.ugland.utransprod.model.SupplierType;
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
/*     */ public class SupplierModel extends AbstractModel<Supplier, SupplierModel> {
/*     */    private static final long serialVersionUID = 1L;
/*     */    public static final String PROPERTY_SUPPLIER_ID = "supplierId";
/*     */    public static final String PROPERTY_SUPPLIER_NAME = "supplierName";
/*     */    public static final String PROPERTY_SUPPLIER_DESCRIPTION = "supplierDescription";
/*     */    public static final String PROPERTY_PHONE = "phone";
/*     */    public static final String PROPERTY_FAX = "fax";
/*     */    public static final String PROPERTY_SUPPLIER_NR = "supplierNr";
/*     */    public static final String PROPERTY_ADDRESS = "address";
/*     */    public static final String PROPERTY_POSTAL_CODE = "postalCode";
/*     */    public static final String PROPERTY_POST_OFFICE = "postOffice";
/*     */    public static final String PROPERTY_SUPPLIER_TYPE = "supplierType";
/*     */    public static final String PROPERTY_INACTIVE_BOOL = "inactiveBool";
/*     */    public static final String PROPERTY_SUPPLIER_PRODUCT_AREA_GROUPS_LIST = "supplierProductAreaGroupsList";
/*     */    private List<SupplierProductAreaGroup> supplierProductAreaGroups = new ArrayList();
/*     */ 
/*     */    public SupplierModel(Supplier object) {
/*  53 */       super(object);
/*     */ 
/*     */ 
/*     */ 
/*  57 */       if (object.getSupplierProductAreaGroups() != null) {
/*  58 */          this.supplierProductAreaGroups.addAll(object.getSupplierProductAreaGroups());
/*     */       }
/*  60 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getSupplierId() {
/*  66 */       return ((Supplier)this.object).getSupplierId();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSupplierId(Integer supplierId) {
/*  73 */       Integer oldId = this.getSupplierId();
/*  74 */       ((Supplier)this.object).setSupplierId(supplierId);
/*  75 */       this.firePropertyChange("supplierId", oldId, supplierId);
/*  76 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getSupplierName() {
/*  82 */       return ((Supplier)this.object).getSupplierName();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSupplierName(String supplierName) {
/*  89 */       String oldName = this.getSupplierName();
/*  90 */       ((Supplier)this.object).setSupplierName(supplierName);
/*  91 */       this.firePropertyChange("supplierName", oldName, supplierName);
/*  92 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getSupplierDescription() {
/*  98 */       return ((Supplier)this.object).getSupplierDescription();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSupplierDescription(String supplierDescription) {
/* 105 */       String oldDesc = this.getSupplierDescription();
/* 106 */       ((Supplier)this.object).setSupplierDescription(supplierDescription);
/* 107 */       this.firePropertyChange("supplierDescription", oldDesc, supplierDescription);
/* 108 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPhone() {
/* 114 */       return ((Supplier)this.object).getPhone();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPhone(String phone) {
/* 121 */       String oldPhone = this.getPhone();
/* 122 */       ((Supplier)this.object).setPhone(phone);
/* 123 */       this.firePropertyChange("phone", oldPhone, phone);
/* 124 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getFax() {
/* 130 */       return ((Supplier)this.object).getFax();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setFax(String fax) {
/* 137 */       String oldFax = this.getFax();
/* 138 */       ((Supplier)this.object).setFax(fax);
/* 139 */       this.firePropertyChange("fax", oldFax, fax);
/* 140 */    }
/*     */ 
/*     */    public String getSupplierNr() {
/* 143 */       return ((Supplier)this.object).getSupplierNr();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSupplierNr(String supplierNr) {
/* 150 */       String oldSupplierNr = this.getSupplierNr();
/* 151 */       ((Supplier)this.object).setSupplierNr(supplierNr);
/* 152 */       this.firePropertyChange("supplierNr", oldSupplierNr, supplierNr);
/* 153 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getAddress() {
/* 159 */       return ((Supplier)this.object).getAddress();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setAddress(String address) {
/* 166 */       String oldAddress = this.getAddress();
/* 167 */       ((Supplier)this.object).setAddress(address);
/* 168 */       this.firePropertyChange("address", oldAddress, address);
/* 169 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPostalCode() {
/* 175 */       return ((Supplier)this.object).getPostalCode();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPostalCode(String postalCode) {
/* 182 */       String oldCode = this.getPostalCode();
/* 183 */       ((Supplier)this.object).setPostalCode(postalCode);
/* 184 */       this.firePropertyChange("postalCode", oldCode, postalCode);
/* 185 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPostOffice() {
/* 191 */       return ((Supplier)this.object).getPostOffice();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPostOffice(String postOffice) {
/* 198 */       String oldOffice = this.getPostOffice();
/* 199 */       ((Supplier)this.object).setPostOffice(postOffice);
/* 200 */       this.firePropertyChange("postOffice", oldOffice, postOffice);
/* 201 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public SupplierType getSupplierType() {
/* 207 */       return ((Supplier)this.object).getSupplierType();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setSupplierType(SupplierType supplierType) {
/* 214 */       SupplierType oldType = this.getSupplierType();
/* 215 */       ((Supplier)this.object).setSupplierType(supplierType);
/* 216 */       this.firePropertyChange("supplierType", oldType, supplierType);
/* 217 */    }
/*     */ 
/*     */    public Boolean getInactiveBool() {
/* 220 */       return Util.convertNumberToBoolean(((Supplier)this.object).getInactive());
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setInactiveBool(Boolean isInactive) {
/* 227 */       Boolean oldInactive = this.getInactiveBool();
/* 228 */       ((Supplier)this.object).setInactive(Util.convertBooleanToNumber(isInactive));
/* 229 */       this.firePropertyChange("inactiveBool", oldInactive, isInactive);
/* 230 */    }
/*     */ 
/*     */    public List<SupplierProductAreaGroup> getSupplierProductAreaGroupsList() {
/* 233 */       return new ArrayList(this.supplierProductAreaGroups);
/*     */    }
/*     */ 
/*     */    public void setSupplierProductAreaGroupsList(List<SupplierProductAreaGroup> supplierProductAreaGroupList) {
/* 237 */       List<SupplierProductAreaGroup> oldGroups = this.getSupplierProductAreaGroupsList();
/* 238 */       if (supplierProductAreaGroupList != null) {
/* 239 */          this.supplierProductAreaGroups = new ArrayList(supplierProductAreaGroupList);
/*     */       } else {
/* 241 */          this.supplierProductAreaGroups.clear();
/*     */       }
/* 243 */       this.firePropertyChange("supplierProductAreaGroupsList", oldGroups, supplierProductAreaGroupList);
/* 244 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
/* 252 */       presentationModel.getBufferedModel("supplierName").addValueChangeListener(listener);
/* 253 */       presentationModel.getBufferedModel("supplierDescription").addValueChangeListener(listener);
/* 254 */       presentationModel.getBufferedModel("phone").addValueChangeListener(listener);
/* 255 */       presentationModel.getBufferedModel("fax").addValueChangeListener(listener);
/* 256 */       presentationModel.getBufferedModel("supplierNr").addValueChangeListener(listener);
/* 257 */       presentationModel.getBufferedModel("address").addValueChangeListener(listener);
/* 258 */       presentationModel.getBufferedModel("postalCode").addValueChangeListener(listener);
/* 259 */       presentationModel.getBufferedModel("postOffice").addValueChangeListener(listener);
/* 260 */       presentationModel.getBufferedModel("supplierType").addValueChangeListener(listener);
/* 261 */       presentationModel.getBufferedModel("inactiveBool").addValueChangeListener(listener);
/* 262 */       presentationModel.getBufferedModel("supplierProductAreaGroupsList").addValueChangeListener(listener);
/* 263 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public SupplierModel getBufferedObjectModel(PresentationModel presentationModel) {
/* 270 */       SupplierModel supplierModel = new SupplierModel(new Supplier());
/* 271 */       supplierModel.setSupplierId((Integer)presentationModel.getBufferedValue("supplierId"));
/* 272 */       supplierModel.setSupplierName((String)presentationModel.getBufferedValue("supplierName"));
/* 273 */       supplierModel.setSupplierDescription((String)presentationModel.getBufferedValue("supplierDescription"));
/* 274 */       supplierModel.setPhone((String)presentationModel.getBufferedValue("phone"));
/* 275 */       supplierModel.setFax((String)presentationModel.getBufferedValue("fax"));
/* 276 */       supplierModel.setSupplierNr((String)presentationModel.getBufferedValue("supplierNr"));
/* 277 */       supplierModel.setAddress((String)presentationModel.getBufferedValue("address"));
/* 278 */       supplierModel.setPostalCode((String)presentationModel.getBufferedValue("postalCode"));
/* 279 */       supplierModel.setPostOffice((String)presentationModel.getBufferedValue("postOffice"));
/* 280 */       supplierModel.setSupplierType((SupplierType)presentationModel.getBufferedValue("supplierType"));
/* 281 */       supplierModel.setInactiveBool((Boolean)presentationModel.getBufferedValue("inactiveBool"));
/* 282 */       supplierModel.setSupplierProductAreaGroupsList((List)presentationModel.getBufferedValue("supplierProductAreaGroupsList"));
/*     */ 
/*     */ 
/* 285 */       return supplierModel;
/*     */    }
/*     */ 
/*     */ 
/*     */    public void viewToModel() {
/* 290 */       if (this.supplierProductAreaGroups != null) {
/* 291 */          Set<SupplierProductAreaGroup> groups = ((Supplier)this.object).getSupplierProductAreaGroups();
/* 292 */          if (groups == null) {
/* 293 */             groups = new HashSet();
/*     */          }
/* 295 */          ((Set)groups).clear();
/* 296 */          ((Set)groups).addAll(this.supplierProductAreaGroups);
/* 297 */          ((Supplier)this.object).setSupplierProductAreaGroups((Set)groups);
/*     */       }
/* 299 */    }
/*     */ }
