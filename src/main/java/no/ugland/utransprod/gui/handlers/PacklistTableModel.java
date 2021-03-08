/*     */ package no.ugland.utransprod.gui.handlers;
/*     */ 
/*     */ import com.jgoodies.binding.adapter.AbstractTableAdapter;
/*     */ import javax.swing.ListModel;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.PostShipment;
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
/*     */ final class PacklistTableModel extends AbstractTableAdapter {
/*     */    private static final long serialVersionUID = 1L;
/*     */    private Integer articleCount;
/*     */    private PostShipment postShipment;
/*     */ 
/*     */    public PacklistTableModel(ListModel listModel, Integer aArticleCount, PostShipment aPostShipment) {
/*  32 */       super(listModel, new String[]{"address", "first_name", "last_name", "postal_code", "post_office", "order_nr", "customer_nr", "phone_nr", "construction_type", "number_of_articles", "article_name", "article_details", "packlist_comment"});
/*     */ 
/*     */ 
/*     */ 
/*  36 */       this.articleCount = aArticleCount;
/*  37 */       this.postShipment = aPostShipment;
/*  38 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Object getValueAt(int row, int column) {
/*  44 */       OrderLine orderLine = (OrderLine)this.getRow(row);
/*     */ 
/*  46 */       Order order = orderLine.getOrder();
/*     */ 
/*     */ 
/*  49 */       switch(column) {
/*     */       case 0:
/*  51 */          return order.getDeliveryAddress();
/*     */       case 1:
/*  53 */          return order.getCustomer().getFirstName();
/*     */       case 2:
/*  55 */          return order.getCustomer().getLastName();
/*     */       case 3:
/*  57 */          return order.getPostalCode();
/*     */       case 4:
/*  59 */          return order.getPostOffice();
/*     */       case 5:
/*  61 */          return order.getOrderNr();
/*     */       case 6:
/*  63 */          return order.getCustomer().getCustomerNr();
/*     */       case 7:
/*  65 */          return order.getTelephoneNr();
/*     */       case 8:
/*  67 */          return "Etterlevering";
/*     */       case 9:
/*  69 */          return this.articleCount;
/*     */       case 10:
/*  71 */          return Util.upperFirstLetter(orderLine.getArticleName());
/*     */       case 11:
/*  73 */          return orderLine.getDetails();
/*     */       case 12:
/*  75 */          return this.postShipment.getPacklistComments();
/*     */       default:
/*  77 */          throw new IllegalStateException("Unknown column");
/*     */       }
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Class<?> getColumnClass(int column) {
/*  86 */       switch(column) {
/*     */ 
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 7:
/*     */       case 8:
/*     */       case 10:
/*     */       case 11:
/*  98 */          return String.class;
/*     */       case 6:
/*     */       case 9:
/* 101 */          return Integer.class;
/*     */       default:
/* 103 */          throw new IllegalStateException("Unknown column");
/*     */       }
/*     */    }
/*     */ }
