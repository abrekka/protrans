/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.Icon;
/*     */ import no.ugland.utransprod.gui.IconEnum;
/*     */ import no.ugland.utransprod.model.Deviation;
/*     */ import no.ugland.utransprod.model.PostShipment;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ import org.apache.commons.lang.StringUtils;
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
/*     */ public class TextPaneRendererTransport extends TextPaneRenderer<Transportable> {
/*     */    private static final long serialVersionUID = 1L;
/*     */ 
/*     */    private boolean isPostShipment(Transportable transportable) {
/*  26 */       return transportable instanceof PostShipment || transportable instanceof Deviation;
/*     */    }
/*     */ 
/*     */    private String getWarning(Transportable transportable) {
/*  30 */       String specialConcern = transportable.getSpecialConcern();
/*  31 */       String justertTekst = "";
/*  32 */       if (StringUtils.isNotBlank(specialConcern)) {
/*  33 */          if (specialConcern.contains("Stående gavl")) {
/*  34 */             justertTekst = justertTekst + "Stående gavl (over 260cm) ";
/*     */          }
/*  36 */          if (specialConcern.contains("Stående tak")) {
/*  37 */             justertTekst = justertTekst + "Stående tak ";
/*     */          }
/*  39 */          if (specialConcern.contains("Høye vegger")) {
/*  40 */             justertTekst = justertTekst + "Høye vegger ";
/*     */          }
/*  42 */          if (specialConcern.contains("Lange vegger")) {
/*  43 */             justertTekst = justertTekst + "Lange vegger ";
/*     */          }      }
/*     */ 
/*  46 */       return justertTekst;
/*     */    }
/*     */ 
/*     */    private String getComment(Transportable transportable) {
/*  50 */       return this.stringHasValue(transportable.getComment()) ? this.getSplitComment(transportable.getComment()) : "";
/*     */    }
/*     */ 
/*     */    private boolean stringHasValue(String string) {
/*  54 */       return string != null && string.length() > 0;
/*     */    }
/*     */ 
/*     */    private String getSplitComment(String comment) {
/*  58 */       return Util.splitLongStringIntoLinesWithBr(comment, 50);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    protected List<Icon> getIcons(Transportable transportable) {
/*  64 */       List<Icon> icons = new ArrayList();      boolean var10000;
/*  65 */       if (this.stringHasValue(transportable.getComment())) {         icons.add(IconEnum.ICON_COMMENT.getIcon());      } else {         var10000 = false;      }
/*  66 */       if (StringUtils.isNotBlank(transportable.getSpecialConcern()) && transportable.getSpecialConcern().contains("Stående gavl")) {
/*     */ 
/*  68 */          icons.add(IconEnum.ICON_WARNING.getIcon());      } else {         var10000 = false;      }
/*  69 */       if (StringUtils.isNotBlank(transportable.getSpecialConcern()) && transportable.getSpecialConcern().contains("Stående tak")) {
/*     */ 
/*  71 */          icons.add(IconEnum.ICON_WARNING_BLUE.getIcon());      } else {         var10000 = false;      }
/*  72 */       if (StringUtils.isNotBlank(transportable.getSpecialConcern()) && transportable.getSpecialConcern().contains("Høye vegger")) {
/*     */ 
/*  74 */          icons.add(IconEnum.ICON_WARNING_YELLOW.getIcon());      } else {         var10000 = false;      }
/*  75 */       if (StringUtils.isNotBlank(transportable.getSpecialConcern()) && transportable.getSpecialConcern().contains("Lange vegger")) {
/*     */ 
/*  77 */          icons.add(IconEnum.ICON_WARNING_RED.getIcon());      } else {         var10000 = false;      }
/*  78 */       if (this.isPostShipment(transportable)) {         icons.add(IconEnum.ICON_POST_SHIPMENT.getIcon());      } else {         var10000 = false;      }
/*  79 */       return icons;
/*     */    }
/*     */ 
/*     */ 
/*     */    protected String getPaneText(Transportable transportable) {
/*  84 */       return transportable.getTransportString();
/*     */    }
/*     */ 
/*     */ 
/*     */    protected StringBuffer getPaneToolTipText(Transportable transportable) {
/*  89 */       StringBuffer toolTip = new StringBuffer();
/*     */ 
/*  91 */       toolTip.append(this.getComment(transportable));
/*     */ 
/*  93 */       toolTip.append(this.getWarning(transportable));
/*     */ 
/*  95 */       return toolTip;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    protected void setCenteredAlignment() {
/* 101 */    }
/*     */ }
