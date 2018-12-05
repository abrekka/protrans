package no.ugland.utransprod.gui.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import org.apache.commons.lang.StringUtils;

import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.util.Util;

/**
 * Render for å vise transport.
 * 
 * @author atle.brekka
 */
public class TextPaneRendererTransport extends TextPaneRenderer<Transportable> {

	private static final long serialVersionUID = 1L;

	private boolean isPostShipment(final Transportable transportable) {
		return transportable instanceof PostShipment || transportable instanceof Deviation;
	}

	private String getWarning(final Transportable transportable) {
		String specialConcern=transportable.getSpecialConcern();
		String justertTekst="";
		if(StringUtils.isNotBlank(specialConcern)){
			if(specialConcern.contains("Stående gavl")){
				justertTekst+="Stående gavl (over 260cm) ";
			}
			if(specialConcern.contains("Stående tak")){
				justertTekst+="Stående tak ";
			}
			if(specialConcern.contains("Høye vegger")){
				justertTekst+="Høye vegger ";
			}
			if(specialConcern.contains("Lange vegger")){
				justertTekst+="Lange vegger ";
			}
		}
		return justertTekst;
	}

	private String getComment(final Transportable transportable) {
		return stringHasValue(transportable.getComment()) ? getSplitComment(transportable.getComment()) : "";
	}

	private boolean stringHasValue(String string) {
		return string != null && string.length() > 0;
	}

	private String getSplitComment(final String comment) {
		return Util.splitLongStringIntoLinesWithBr(comment, MAX_LENGHT);

	}

	@Override
	protected List<Icon> getIcons(Transportable transportable) {
		List<Icon> icons = new ArrayList<Icon>();
		boolean added = stringHasValue(transportable.getComment()) ? icons.add(IconEnum.ICON_COMMENT.getIcon()) : false;
		added = StringUtils.isNotBlank(transportable.getSpecialConcern())
				&& transportable.getSpecialConcern().contains("Stående gavl")
						? icons.add(IconEnum.ICON_WARNING.getIcon()) : false;
		added = StringUtils.isNotBlank(transportable.getSpecialConcern())
				&& transportable.getSpecialConcern().contains("Stående tak")
						? icons.add(IconEnum.ICON_WARNING_BLUE.getIcon()) : false;
		added = StringUtils.isNotBlank(transportable.getSpecialConcern())
				&& transportable.getSpecialConcern().contains("Høye vegger")
						? icons.add(IconEnum.ICON_WARNING_YELLOW.getIcon()) : false;
		added = StringUtils.isNotBlank(transportable.getSpecialConcern())
				&& transportable.getSpecialConcern().contains("Lange vegger")
						? icons.add(IconEnum.ICON_WARNING_RED.getIcon()) : false;
		added = isPostShipment(transportable) ? icons.add(IconEnum.ICON_POST_SHIPMENT.getIcon()) : false;
		return icons;
	}

	@Override
	protected String getPaneText(Transportable transportable) {
		return transportable.getTransportString();
	}

	@Override
	protected StringBuffer getPaneToolTipText(Transportable transportable) {
		StringBuffer toolTip = new StringBuffer();

		toolTip.append(getComment(transportable));

		toolTip.append(getWarning(transportable));
		
		return toolTip;
	}

	@Override
	protected void setCenteredAlignment() {

	}

}
