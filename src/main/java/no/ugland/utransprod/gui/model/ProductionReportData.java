package no.ugland.utransprod.gui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.internal.Lists;

import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderCommentCommentType;
import no.ugland.utransprod.model.ProductArea;

public class ProductionReportData {
	private String ordrenummer;
	private ProductArea productArea;
	private Integer doAssembly;
	private String poststed;
	private Integer transportuke;
	private List<Ordreinfo> ordreinfo;
	private String navn;
	private String telefonliste;
	private String leveringsadresse;
	private String postnr;
	// private String taktekke;
	private String pakketAv;
	private String bruker;
	private Integer produksjonsuke;
	private Set<OrderComment> kommentarer;
	private List<Delelisteinfo> deleliste;

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

	public ProductionReportData medTransportuke(Integer transportuke) {
		this.transportuke = transportuke;
		return this;
	}

	public String getTransportuke() {
		return transportuke == null ? "" : String.valueOf(transportuke);
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
				.newArrayList(Iterables.transform(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

					public boolean apply(Ordreinfo ordreinfo) {
						return !ordreinfo.getBeskrivelse().contains("Vegg:")
								&& !ordreinfo.getBeskrivelse().contains("Gavl:")
								&& !ordreinfo.getBeskrivelse().contains("Vindsperre")
								&& !ordreinfo.getBeskrivelse().contains("Takstol:")
								&& !ordreinfo.getProdno().contains("HJKASSSPES") && ordreinfo.getPrCatNo2() != 9;
					}
				}), new Function<Ordreinfo, String>() {

					public String apply(Ordreinfo from) {
						return from.getBeskrivelse();
					}
				}));
		return Joiner.on("\n").skipNulls().join(beskrivelser);

	}

	public ProductionReportData medNavn(String fullName) {
		this.navn = fullName;
		return this;
	}

	public String getNavn() {
		return navn;
	}

	public ProductionReportData medTelefonliste(String telephoneNr) {
		this.telefonliste = telephoneNr;
		return this;
	}

	public String getTelefon() {
		if (telefonliste != null) {
			return Joiner.on("\n").skipNulls().join(telefonliste.split(";"));
		}
		return "";
	}

	public ProductionReportData medLeveringsadresse(String deliveryAddress) {
		this.leveringsadresse = deliveryAddress;
		return this;
	}

	public String getLeveringsadresse() {
		return leveringsadresse;
	}

	public ProductionReportData medPostnr(String postalCode) {
		this.postnr = postalCode;
		return this;
	}

	public String getPostnr() {
		return postnr;
	}

	public String getVegghoyde() {
		if (ordreinfo != null && !ordreinfo.isEmpty()) {
			return String.format("%s mm",
					ordreinfo.get(0).getVegghoyde() == null ? 0 : ordreinfo.get(0).getVegghoyde() * 10);
		}
		return "";
	}

	public String getRingmur() {
		if (ordreinfo != null && !ordreinfo.isEmpty()) {
			return String.format("%s mm",
					ordreinfo.get(0).getMurhoyde() == null ? 0 : ordreinfo.get(0).getMurhoyde() * 10);
		}
		return "";
	}

	public String getTilpasningmur() {
		if (ordreinfo != null) {
			List<Ordreinfo> tilpasning = Lists.newArrayList(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

				public boolean apply(Ordreinfo ordreinfo) {
					return "AVTRAPPMUR".equalsIgnoreCase(ordreinfo.getProdno());
				}
			}));
			return tilpasning == null || tilpasning.isEmpty() ? "" : tilpasning.get(0).getBeskrivelse();
		}
		return "";
	}

	public String getVegginformasjon() {
		if (ordreinfo != null && !ordreinfo.isEmpty()) {
			List<Ordreinfo> tilpasning = Lists.newArrayList(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

				public boolean apply(Ordreinfo ordreinfo) {
					return ordreinfo.getBeskrivelse().contains("Vegg:");
				}
			}));
			return tilpasning == null || tilpasning.isEmpty() ? "" : tilpasning.get(0).getBeskrivelse();
		}
		return "";
	}

	public String getVeggtype() {
		if (ordreinfo != null && !ordreinfo.isEmpty()) {
			List<Ordreinfo> tilpasning = Lists.newArrayList(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

				public boolean apply(Ordreinfo ordreinfo) {
					return ordreinfo.getBeskrivelse().contains("Vegg:");
				}
			}));
			return tilpasning == null || tilpasning.isEmpty() ? "" : tilpasning.get(0).getType();
		}
		return "";
	}

	public String getGavlinformasjon() {
		if (ordreinfo != null && !ordreinfo.isEmpty()) {
			List<Ordreinfo> tilpasning = Lists.newArrayList(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

				public boolean apply(Ordreinfo ordreinfo) {
					return ordreinfo.getBeskrivelse().contains("Gavl:");
				}
			}));
			return tilpasning == null || tilpasning.isEmpty() ? "" : tilpasning.get(0).getBeskrivelse();
		}
		return "";
	}

	public String getGavltype() {
		if (ordreinfo != null && !ordreinfo.isEmpty()) {
			List<Ordreinfo> tilpasning = Lists.newArrayList(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

				public boolean apply(Ordreinfo ordreinfo) {
					return ordreinfo.getBeskrivelse().contains("Gavl:");
				}
			}));
			return tilpasning == null || tilpasning.isEmpty() ? "" : tilpasning.get(0).getType();
		}
		return "";
	}

	public Integer getSpesialtak() {
		if (ordreinfo != null && !ordreinfo.isEmpty()) {
			List<Ordreinfo> tilpasning = Lists.newArrayList(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

				public boolean apply(Ordreinfo ordreinfo) {
					return ordreinfo.getPrcatno() == 509600;
				}
			}));
			return tilpasning == null || tilpasning.isEmpty() ? 0 : tilpasning.get(0).getPurcno();
		}
		return 0;
	}

	public Integer getTakvinkel() {
		if (ordreinfo != null && !ordreinfo.isEmpty()) {
			List<Ordreinfo> tilpasning = Lists.newArrayList(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

				public boolean apply(Ordreinfo ordreinfo) {
					return ordreinfo.getProdno().contains("Tak");
				}
			}));
			return tilpasning == null || tilpasning.isEmpty() || tilpasning.get(0).getFree4() == null ? 0
					: tilpasning.get(0).getFree4();
		}
		return 0;
	}

	public String getVindsperre() {
		if (ordreinfo != null && !ordreinfo.isEmpty()) {
			List<Ordreinfo> tilpasning = Lists.newArrayList(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

				public boolean apply(Ordreinfo ordreinfo) {
					return ordreinfo.getPrCatNo2() != null
							&& (ordreinfo.getPrCatNo2() == 29 || ordreinfo.getPrCatNo2() == 30);
				}
			}));
			return tilpasning == null || tilpasning.isEmpty() || tilpasning.get(0).getPrCatNo2() == null ? "Nei" : "Ja";
		}
		return "Nei";
	}

	public String getUtlekting() {
		if (ordreinfo != null && !ordreinfo.isEmpty()) {
			List<Ordreinfo> tilpasning = Lists.newArrayList(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

				public boolean apply(Ordreinfo ordreinfo) {
					return ordreinfo.getPrCatNo2() != null
							&& (ordreinfo.getPrCatNo2() == 29 || ordreinfo.getPrCatNo2() == 30);
				}
			}));
			return tilpasning == null || tilpasning.isEmpty() || tilpasning.get(0).getInf() == null ? ""
					: tilpasning.get(0).getInf();
		}
		return "";
	}

	public String getHjornekassetype() {
		if (ordreinfo != null && !ordreinfo.isEmpty()) {
			List<Ordreinfo> tilpasning = Lists.newArrayList(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

				public boolean apply(Ordreinfo ordreinfo) {
					return ordreinfo.getProdno().contains("HJKASSSPES");
				}
			}));
			return tilpasning == null || tilpasning.isEmpty() || tilpasning.get(0).getBeskrivelse() == null ? ""
					: tilpasning.get(0).getBeskrivelse().replace("Spesial", "");
		}
		return "";
	}

	// public ProductionReportData medTaktekke(String taktekke) {
	// this.taktekke = taktekke;
	// return this;
	// }

	public String getTaktekke() {
		// return taktekke;
		if (ordreinfo != null && !ordreinfo.isEmpty()) {
			List<Ordreinfo> taktekke = Lists.newArrayList(Iterables.filter(ordreinfo, new Predicate<Ordreinfo>() {

				public boolean apply(Ordreinfo ordreinfo) {
					return ordreinfo.getPrCatNo2() != null && ordreinfo.getPrCatNo2() == 9;
				}
			}));
			return taktekke == null || taktekke.isEmpty() ? "" : taktekke.get(0).getBeskrivelse();
		}
		return "";
	}

	public ProductionReportData medPakketAv(String pakketAv) {
		this.pakketAv = pakketAv;
		return this;
	}

	public ProductionReportData medBruker(String bruker) {
		this.bruker = bruker;
		return this;
	}

	public String getAnsatt() {
		return pakketAv == null ? bruker : pakketAv;
	}

	public ProductionReportData medProduksjonsuke(Integer productionWeek) {
		this.produksjonsuke = productionWeek;
		return this;
	}

	public Integer getProduksjonsuke() {
		return produksjonsuke;
	}

	public ProductionReportData medKommentarer(Set<OrderComment> kommentarer) {
		this.kommentarer = kommentarer;
		return this;
	}

	public String getKommentarer() {
		if (kommentarer != null) {
			List<String> kommentarliste = Lists
					.newArrayList(Iterables.transform(Iterables.filter(kommentarer, new Predicate<OrderComment>() {

						public boolean apply(OrderComment kommentar) {
							if (kommentar.getCommentTypes() != null) {
								List<OrderCommentCommentType> pakkekommentarer = Lists
										.newArrayList(Iterables.filter(kommentar.getOrderCommentCommentTypes(),
												new Predicate<OrderCommentCommentType>() {

									public boolean apply(OrderCommentCommentType type) {
										return "Pakking".equals(type.getCommentType().getCommentTypeName());
									}
								}));
								return !pakkekommentarer.isEmpty();
							}
							return false;
						}
					}), new Function<OrderComment, String>() {

						public String apply(OrderComment from) {
							// TODO Auto-generated method stub
							return from.getComment();
						}
					}));
			return Joiner.on(" ").skipNulls().join(kommentarliste);
		}
		return "";
	}

	public ProductionReportData medDeleliste(List<Delelisteinfo> deleliste) {
		this.deleliste = deleliste;
		return this;
	}

	public List<Delelisteinfo> getDelelisteVegg() {
		return Lists.newArrayList(Iterables.filter(deleliste, new Predicate<Delelisteinfo>() {

			public boolean apply(Delelisteinfo del) {
				return del.getTxt() != null && del.getProdtp() == 10;
			}
		}));
	}

	public List<Delelisteinfo> getDelelisteTak() {
		return Lists.newArrayList(Iterables.filter(deleliste, new Predicate<Delelisteinfo>() {

			public boolean apply(Delelisteinfo del) {
				return del.getTxt() != null && del.getProdtp() == 20;
			}
		}));
	}

	public String getReisverk() {
		ArrayList<Delelisteinfo> reisverkliste = Lists
				.newArrayList(Iterables.filter(deleliste, new Predicate<Delelisteinfo>() {

					public boolean apply(Delelisteinfo del) {
						return del.getBeskrivelse().contains("Reisverk:");
					}
				}));
		return reisverkliste.isEmpty() ? null : reisverkliste.get(0).getBeskrivelse();
	}

	public List<Delelisteinfo> getDelelistePakking() {
		return Lists.newArrayList(Iterables.filter(deleliste, new Predicate<Delelisteinfo>() {

			public boolean apply(Delelisteinfo del) {
				return del.getTxt() != null && del.getProdtp() == 30 && del.getProdtp2() != 190
						&& !del.getProdno().toUpperCase().startsWith("TAKR");
			}
		}));
	}

	public List<Delelisteinfo> getDelelisteTakrenner() {
		return Lists.newArrayList(Iterables.filter(deleliste, new Predicate<Delelisteinfo>() {

			public boolean apply(Delelisteinfo del) {
				return (del.getTxt() != null && del.getProdtp() == 30 || del.getProdtp() == 35)
						&& (del.getProdtp2() == 190 || del.getPrCatNo2() == 9);
			}
		}));
	}

	public List<Delelisteinfo> getDelelisteAltUtenTakrennedeler() {
		return Lists.newArrayList(Iterables.filter(deleliste, new Predicate<Delelisteinfo>() {

			public boolean apply(Delelisteinfo del) {
				return del.getTxt() != null && del.getProdtp() != 35;
			}
		}));
	}

	public List<Delelisteinfo> getDelelisteKunde() {
		return Lists.newArrayList(Iterables.filter(deleliste, new Predicate<Delelisteinfo>() {

			public boolean apply(Delelisteinfo del) {
				return del.getTxt() != null;
			}
		}));
	}
}
