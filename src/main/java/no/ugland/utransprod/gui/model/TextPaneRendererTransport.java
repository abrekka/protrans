package no.ugland.utransprod.gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

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
	return stringHasValue(transportable.getSpecialConcern()) ? transportable.getSpecialConcern() : "";
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
	added = stringHasValue(transportable.getSpecialConcern()) ? icons.add(IconEnum.ICON_WARNING.getIcon()) : false;
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
