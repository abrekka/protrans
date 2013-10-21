package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.OverviewManager;

public abstract class DefaultAbstractViewHandler<T, E> extends
		AbstractViewHandler<T, E> {

	private static final long serialVersionUID = 1L;

	public DefaultAbstractViewHandler(final String aHeading,
			final OverviewManager<T> aOverviewManager, final boolean oneObject,
			final UserType aUserType, final boolean useDisposeOnClose) {
		super(aHeading, aOverviewManager, oneObject, aUserType,
				useDisposeOnClose);
	}

	public DefaultAbstractViewHandler(final String aHeading,
			final OverviewManager<T> aOverviewManager,
			final UserType aUserType, final boolean useDisposeOnClose) {
		super(aHeading, aOverviewManager, aUserType, useDisposeOnClose);
	}

	@Override
	final String getAddString() {
		return null;
	}

	@Override
	void afterSaveObject(T object, WindowInterface window) {
	}

}
