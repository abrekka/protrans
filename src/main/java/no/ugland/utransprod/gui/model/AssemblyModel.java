package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.Supplier;

import com.jgoodies.binding.PresentationModel;

/**
 * GUI-modell for montering
 * 
 * @author atle.brekka
 */
public class AssemblyModel extends AbstractModel<Assembly, AssemblyModel> {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_SUPPLIER = "supplier";

    public static final String PROPERTY_ASSEMBLY_YEAR = "assemblyYear";

    public static final String PROPERTY_ASSEMBLY_WEEK = "assemblyWeek";

    public static final String PROPERTY_COMMENT_LIST = "commentList";
    public static final String PROPERTY_COMMENT = "comment";

    public static final String PROPERTY_PLANNED = "planned";

    public static final String PROPERTY_ASSEMBLIED_BOOL = "assembliedBool";

    public static final String PROPERTY_ASSEMBLIED_DATE = "assembliedDate";

    private List<OrderComment> assemblyComments;
    private String userName;

    public AssemblyModel(Assembly object, String aUserName) {
	super(object);
	assemblyComments = new ArrayList<OrderComment>();
	if (object.getOrder() != null && object.getOrder().getAssemblyComments() != null) {
	    assemblyComments.addAll(object.getOrder().getAssemblyComments());
	}
	userName = aUserName;
    }

    /**
     * Henter monteringsteam
     * 
     * @return monteringsteam
     */
    public Supplier getSupplier() {
	return object.getSupplier();
    }

    /**
     * Setter monteringsteam
     * 
     * @param assemblyTeam
     */
    public void setSupplier(Supplier supplier) {
	Supplier oldSupplier = getSupplier();
	object.setSupplier(supplier);
	firePropertyChange(PROPERTY_SUPPLIER, oldSupplier, supplier);
    }

    /**
     * Henter år
     * 
     * @return år
     */
    public Integer getAssemblyYear() {
	return object.getAssemblyYear();
    }

    /**
     * Setter år
     * 
     * @param assemblyYear
     */
    public void setAssemblyYear(Integer assemblyYear) {
	Integer oldYear = getAssemblyYear();
	object.setAssemblyYear(assemblyYear);
	firePropertyChange(PROPERTY_ASSEMBLY_YEAR, oldYear, assemblyYear);
    }

    /**
     * Henter uke
     * 
     * @return uke
     */
    public Integer getAssemblyWeek() {
	return object.getAssemblyWeek();
    }

    /**
     * Setter uke
     * 
     * @param assemblyWeek
     */
    public void setAssemblyWeek(Integer assemblyWeek) {
	Integer oldWeek = getAssemblyWeek();
	object.setAssemblyWeek(assemblyWeek);
	firePropertyChange(PROPERTY_ASSEMBLY_WEEK, oldWeek, assemblyWeek);
    }

    /**
     * @return true dersom montert
     */
    public Boolean getAssembliedBool() {
	return object.getAssembliedBool();
    }

    /**
     * @param assemblied
     */
    public void setAssembliedBool(Boolean assemblied) {
	Boolean oldAssemblied = getAssembliedBool();
	object.setAssembliedBool(assemblied);
	firePropertyChange(PROPERTY_ASSEMBLIED_BOOL, oldAssemblied, assemblied);
    }

    /**
     * @return monteringsdato
     */
    public Date getAssembliedDate() {
	return object.getAssembliedDate();
    }

    /**
     * @param assembliedDate
     */
    public void setAssembliedDate(Date assembliedDate) {
	Date oldAssemblied = getAssembliedDate();
	object.setAssembliedDate(assembliedDate);
	firePropertyChange(PROPERTY_ASSEMBLIED_DATE, oldAssemblied, assembliedDate);
    }

    /**
     * Henter kommentar
     * 
     * @return kommentar
     */
    public List<OrderComment> getCommentList() {
	return assemblyComments;
    }

    /**
     * Setter kommentar
     * 
     * @param comment
     */
    public void setCommentList(List<OrderComment> comments) {
	List<OrderComment> oldComments = new ArrayList<OrderComment>(getCommentList());
	assemblyComments.clear();
	if (comments != null) {
	    assemblyComments.addAll(comments);
	}

	firePropertyChange(PROPERTY_COMMENT_LIST, oldComments, comments);
    }

    public String getComment() {
	return object.getAssemblyComment();
    }

    /**
     * Setter kommentar
     * 
     * @param comment
     */
    public void setComment(String comments) {
	String oldComment = getComment();
	object.setAssemblyComment(comments);
	firePropertyChange(PROPERTY_COMMENT, oldComment, comments);
    }

    @Override
    public void viewToModel() {
	if (object.getOrder() != null) {
	    Set<OrderComment> comments = object.getOrder().getOrderComments();
	    if (comments == null) {
		comments = new HashSet<OrderComment>();
	    }
	    // comments.clear();
	    comments.addAll(assemblyComments);
	    object.getOrder().setOrderComments(comments);
	}
    }

    /**
     * @return planlagt
     */
    public String getPlanned() {
	return object.getPlanned();
    }

    /**
     * @param planned
     */
    public void setPlanned(String planned) {
	String oldPlanned = getPlanned();
	object.setPlanned(planned);
	firePropertyChange(PROPERTY_PLANNED, oldPlanned, planned);
    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#addBufferChangeListener(java.beans.PropertyChangeListener,
     *      com.jgoodies.binding.PresentationModel)
     */
    @Override
    public void addBufferChangeListener(PropertyChangeListener listener, PresentationModel presentationModel) {
	presentationModel.getBufferedModel(PROPERTY_ASSEMBLIED_DATE).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_SUPPLIER).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_ASSEMBLY_WEEK).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_ASSEMBLY_YEAR).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_COMMENT_LIST).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_COMMENT).addValueChangeListener(listener);
	presentationModel.getBufferedModel(PROPERTY_PLANNED).addValueChangeListener(listener);

    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractModel#getBufferedObjectModel(com.jgoodies.binding.PresentationModel)
     */
    @SuppressWarnings("unchecked")
    @Override
    public AssemblyModel getBufferedObjectModel(PresentationModel presentationModel) {
	AssemblyModel assemblyModel = new AssemblyModel(new Assembly(), userName);
	assemblyModel.setSupplier((Supplier) presentationModel.getBufferedValue(PROPERTY_SUPPLIER));
	assemblyModel.setAssemblyWeek((Integer) presentationModel.getBufferedValue(PROPERTY_ASSEMBLY_WEEK));
	assemblyModel.setAssemblyYear((Integer) presentationModel.getBufferedValue(PROPERTY_ASSEMBLY_YEAR));
	assemblyModel.setCommentList((List<OrderComment>) presentationModel.getBufferedValue(PROPERTY_COMMENT_LIST));
	assemblyModel.setComment((String) presentationModel.getBufferedValue(PROPERTY_COMMENT));
	assemblyModel.setPlanned((String) presentationModel.getBufferedValue(PROPERTY_PLANNED));
	assemblyModel.setAssembliedDate((Date) presentationModel.getBufferedValue(PROPERTY_ASSEMBLIED_DATE));
	return assemblyModel;
    }
}
