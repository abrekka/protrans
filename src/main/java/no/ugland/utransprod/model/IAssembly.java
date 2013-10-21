package no.ugland.utransprod.model;

import java.util.Collection;

/**
 * Interface for klasser som kan monteres
 * 
 * @author atle.brekka
 * 
 */
public interface IAssembly {
	/**
	 * @return ordre for montering
	 */
	Order getAssemblyOrder();

	/**
	 * @return avvik for montering
	 */
	Deviation getAssemblyDeviation();

	/**
	 * @param assembly
	 */
	void setAssembly(Assembly assembly);

	/**
	 * @return ordrelinjer for montering
	 */
	Collection<OrderLine> getAssemblyOrderLines();

	/**
	 * @return montering
	 */
	Assembly getAssembly();
}
