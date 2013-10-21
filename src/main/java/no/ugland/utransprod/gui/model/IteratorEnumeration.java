package no.ugland.utransprod.gui.model;

import java.util.Enumeration;
import java.util.Iterator;

/** enumeratorklasse som brukes i forbindelse med tretabell
 * @author atle.brekka
 * @param <T> 
 *
 */
@SuppressWarnings("unchecked")
public class IteratorEnumeration<T> implements Enumeration<T>
{
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	Iterator iterator;

	/**
	 * @param aIterator
	 */
	@SuppressWarnings("unchecked")
	public IteratorEnumeration(Iterator aIterator)
	{
		iterator = aIterator;
	}

	/**
	 * @see java.util.Enumeration#hasMoreElements()
	 */
	public boolean hasMoreElements()
	{
		return iterator.hasNext();
	}

	/**
	 * @see java.util.Enumeration#nextElement()
	 */
	public T nextElement()
	{
		return (T)iterator.next();
	}
}
