package no.ugland.utransprod.util.report;

import java.util.Map;

import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;

public class TrossTransportLetter extends AbstractTransportLetter{

	private static final String ORDER_TYPE_TEXT_TROSS_BUILDING = "Ordretype";


	public TrossTransportLetter(ManagerRepository managerRepository) {
		super(managerRepository);
	}

	
	@Override
	protected void addAssemblyInfoToHeading(Transportable transportable,
			StringBuilder heading) {
	}

	@Override
	protected void addSketchImageToParameters(Transportable transportable,
			Map<String, Object> parameters) {
	}


	@Override
	protected String getOrderTypeText() {
		return ORDER_TYPE_TEXT_TROSS_BUILDING;
	}


	@Override
	protected boolean shouldShowPlacement(Transportable transportable) {
		return false;
	}


	@Override
	public IconEnum getLogoIcon() {
		return IconEnum.ICON_UGLAND_TAKSTOL_BYGGELEMENT;
	}


	@Override
	protected boolean isTross(Transportable transportable) {
		return true;
	}

	

	

}
