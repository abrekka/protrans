package no.ugland.utransprod.util.report;

import java.io.InputStream;
import java.util.Map;

import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

public class GarageTransportLetter extends AbstractTransportLetter {

    private static final String ORDER_TYPE_TEXT_GARAGE = "Garasjetype";

    @Inject
    public GarageTransportLetter(final ManagerRepository managerRepository) {
	super(managerRepository);
    }

    protected void addSketchImageToParameters(Transportable transportable, Map<String, Object> parameters) {
	ConstructionType constructionType = transportable.getConstructionType();
	InputStream iconStream = null;

	if (constructionType != null && constructionType.getSketchName() != null) {
	    iconStream = getClass().getClassLoader().getResourceAsStream("images/" + constructionType.getSketchName());
	}

	if (iconStream != null) {
	    parameters.put("skisse_bilde", iconStream);
	}
    }

    protected void addAssemblyInfoToHeading(Transportable transportable, StringBuilder heading) {

	if (transportable.getOrder().doAssembly()) {
	    heading.append(" Garasjen skal monteres av " + (transportable.getAssemblyTeamName() != null ? transportable.getAssemblyTeamName() : ""));

	    if (hasAssemblyData(transportable)) {
		heading.append("(tlf. ").append(transportable.getAssembly().getSupplier().getPhone()).append(")");
	    }
	} else {
	    heading.append(" Byggesett");
	}
    }

    private boolean hasAssemblyData(Transportable transportable) {
	return transportable.getAssembly() != null && transportable.getAssembly().getSupplier() != null
		&& transportable.getAssembly().getSupplier().getPhone() != null;
    }

    @Override
    protected String getOrderTypeText() {
	return ORDER_TYPE_TEXT_GARAGE;
    }

    @Override
    protected boolean shouldShowPlacement(Transportable transportable) {
	return !hasPostShipment(transportable);
    }

    private boolean hasPostShipment(Transportable transportable) {
	return transportable.getPostShipment() != null;
    }

    @Override
    public IconEnum getLogoIcon() {
	return IconEnum.ICON_IGLAND;
    }

    @Override
    protected boolean isTross(Transportable transportable) {
	return false;
    }

}
