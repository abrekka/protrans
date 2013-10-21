package no.ugland.utransprod.gui.handlers;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * Abstrakt klasse som implementere en del tomme metoder for
 * AbstractProductionPackageViewHandler
 * 
 * @author atle.brekka
 * @param <T>
 */
public abstract class AbstractProductionPackageViewHandlerShort<T extends Applyable>
		extends AbstractProductionPackageViewHandler<T> {

	@Inject
	public AbstractProductionPackageViewHandlerShort(final Login login,ManagerRepository managerRepository,
			DeviationViewHandlerFactory deviationViewHandlerFactory,
			@Assisted final ApplyListInterface<T> productionInterface,
			@Assisted final String title, 
			@Assisted final TableEnum tableEnum) {
		super(login,managerRepository,deviationViewHandlerFactory,productionInterface, title, 
				tableEnum);
	}

	/**
	 * Tom implementasjon
	 * 
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#setInvisibleCells()
	 */
	@Override
	protected void setInvisibleCells() {
	}

	/**
	 * Tom implementasjon
	 * 
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getTableModelReport()
	 */
	@Override
	protected final TableModel getTableModelReport() {
		return null;
	}

	@Override
	protected final TableCellRenderer getSpecificationCellRenderer() {
		return null;
	}

	/**
	 * Tom implementasjon
	 * 
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getSpecificationCell()
	 */
	@Override
	protected final int getSpecificationCell() {
		return -1;
	}

	/**
	 * Tom implementasjon
	 * 
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getReportEnum()
	 */
	@Override
	protected final ReportEnum getReportEnum() {
		return null;
	}

	@Override
	protected void checkLazyLoad(final T object) {
	}

	@Override
	protected final String getNotStartText() {
		return null;
	}

	@Override
	protected final String getStartText() {
		return null;
	}

	@Override
	protected final void setStarted(final T object, final boolean started) {
	}

	@Override
	protected final boolean getButtonStartEnabled(final T object) {
		return false;
	}

	@Override
	protected final Integer getStartColumn() {
		return null;
	}
}
