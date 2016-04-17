package no.ugland.utransprod.gui.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.inject.internal.Lists;

import no.ugland.utransprod.model.ProductArea;

public class ProductionReportData {
	private String ordrenummer;
	private ProductArea productArea;
	private Integer doAssembly;
	private String poststed;
	private Integer produksjonsuke;
	private List<Ordreinfo> ordreinfo;

	public ProductionReportData(String ordrenummer) {
		this.ordrenummer = ordrenummer;
	}

	public String getOrdrenummer() {
		return ordrenummer;
	}

	public List<ProductionReportData> getRapportdata() {
		return Lists.newArrayList(this);
	}

	public ProductionReportData medProductArea(ProductArea productArea) {
		this.productArea = productArea;
		return this;
	}

	public String getByggeart() {
		if (doAssembly != null && doAssembly == 1) {
			return "Montert";
		}
		if (productArea != null && productArea.getProductAreaId() == 5) {
			return "Prekutt";
		}
		return "Byggesett";
	}

	public ProductionReportData medMontering(Integer doAssembly) {
		this.doAssembly = doAssembly;
		return this;
	}

	public ProductionReportData medPoststed(String postOffice) {
		this.poststed = postOffice;
		return this;
	}

	public String getPoststed() {
		return poststed;
	}

	public ProductionReportData medProduksjonsuke(Integer productionWeek) {
		this.produksjonsuke = productionWeek;
		return this;
	}

	public Integer getProduksjonsuke() {
		return produksjonsuke;
	}

	public ProductionReportData medOrdreinfo(List<Ordreinfo> ordreinfo) {
		this.ordreinfo = ordreinfo;
		return this;
	}

	public String getOrdrebeskrivelse() {
		Collections.sort(ordreinfo, new Comparator<Ordreinfo>() {

			public int compare(Ordreinfo o1, Ordreinfo o2) {
				return o1.getLinjenr().compareTo(o2.getLinjenr());
			}
		});
		List<String> beskrivelser = Lists
				.newArrayList(Iterables.transform(ordreinfo, new Function<Ordreinfo, String>() {

					public String apply(Ordreinfo from) {
						return from.getBeskrivelse();
					}
				}));
		return Joiner.on("\n").skipNulls().join(beskrivelser);

	}
}
