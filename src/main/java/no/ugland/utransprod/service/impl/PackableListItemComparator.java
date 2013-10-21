package no.ugland.utransprod.service.impl;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;

import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.Produceable;

public class PackableListItemComparator implements Comparator<PackableListItem> {

	public int compare(PackableListItem packable1, PackableListItem packable2) {
		return new CompareToBuilder().append(
				packable1.getTransportYear(), packable2.getTransportYear()).append(
						packable1.getTransportWeek(), packable2.getTransportWeek()).append(packable1.getLoadingDate(), packable2.getLoadingDate())
		.append(packable1.getTransportDetails(), packable2.getTransportDetails()).append(
				packable1.getLoadTime(), packable2.getLoadTime()).toComparison();
	}
}
