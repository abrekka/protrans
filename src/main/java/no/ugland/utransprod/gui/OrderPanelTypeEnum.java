package no.ugland.utransprod.gui;

import java.util.Map;

import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PostShipment;

import org.apache.commons.lang.StringUtils;

/**
 * Enum for å holde rede på hva slags type ordrepanel som skal vises
 * 
 * @author atle.brekka
 */
public enum OrderPanelTypeEnum {
    NEW_ORDERS(new String[] { "ØUke", "Type", "Mont.", "Pnr.", "Poststed", "Stein", "Kunde", "Ordrenr", "Adresse", "Produktområde", "Sannsynlighet",
	    "Takstoltegner", "Prod.uke" }, 9) {
	@Override
	public Object getKundeInfo(Transportable transportable) {
	    return transportable.getOrder();
	}
    },
    ASSEMBLY_ORDERS(new String[] { "Type", "Pnr.", "Poststed", "Kunde", "Ordrenr", "Adresse", "Transport", "Produktområde" }, 7) {
	@Override
	public Object getKundeInfo(Transportable transportable) {
	    return transportable.getOrder();
	}
    },
    ORDER(new String[] { "Kunde", "Ordrenr", "Adresse", "Postnummer", "Poststed", "Type", "Transport", "Produktområde" }, 7) {
	@Override
	public Object getKundeInfo(Transportable transportable) {
	    return transportable.getCustomer();
	}
    },
    DEVIATION(new String[] { "Kunde", "Ordrenr", "Adresse", "Postnummer", "Poststed", "Type", "Transport", "Produktområde" }, 7) {
	@Override
	public Object getKundeInfo(Transportable transportable) {
	    return transportable.getCustomer();
	}
    },
    CONFIRM_REPORT(new String[] { "Kunde", "Ordrenr", "Adresse", "Postnummer", "Poststed", "Type", "Transport", "Egenproduksjon", "Frakt",
	    "Montering", "Intern", "Dekningsgrad" }, 0) {
	@Override
	public Object getKundeInfo(Transportable transportable) {
	    return transportable.getCustomer();
	}
    },
    INCOMING_ORDER(new String[] { "Kunde", "Ordrenr", "Adresse", "Postnummer", "Poststed", "Type", "Transport", "Produktområde", "Status" }, 7) {
	@Override
	public Object getKundeInfo(Transportable transportable) {
	    return transportable.getCustomer();
	}
    };
    private String[] columnNames;
    private Integer productAreaGroupColumn;

    private OrderPanelTypeEnum(String[] someColumnNames, Integer aProductAreaGroupColumn) {
	columnNames = someColumnNames != null ? someColumnNames.clone() : null;
	productAreaGroupColumn = aProductAreaGroupColumn;
    }

    public String getColumnName(int column) {
	return columnNames[column];
    }

    public int getColumCount() {
	return columnNames.length;
    }

    public Object getValueAt(Transportable transportable, int columnIndex, StatusCheckerInterface<Transportable> steinChecker,
	    Map<String, String> statusMap) {
	String columName = columnNames[columnIndex];
	OrderColumn col = OrderColumn.valueOf(StringUtils.upperCase(columName).replaceAll("\\.", ""));
	return col.getValue(transportable, statusMap, steinChecker, this);
    }

    public abstract Object getKundeInfo(Transportable transportable);

    public enum OrderColumn {
	ØUKE {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return ((Order) transportable).getDeliveryWeek();
	    }
	},
	TYPE {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return transportable.getConstructionType();
	    }
	},
	MONT {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		if (transportable.getOrder().doAssembly()) {
		    if (transportable.getOrder().getAssembly() != null) {
			return "M" + transportable.getOrder().getAssembly().getAssemblyWeek();
		    }
		    return "M";
		}
		return null;
	    }
	},
	PNR {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return transportable.getOrder().getPostalCode();
	    }
	},
	POSTSTED {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return transportable.getOrder().getPostOffice();
	    }
	},
	STEIN {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return statusMap.get(steinChecker.getArticleName());
	    }
	},
	KUNDE {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return orderPanelTypeEnum.getKundeInfo(transportable);
	    }
	},
	ORDRENR {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		String orderNr = transportable.getOrderNr();
		if (transportable instanceof PostShipment) {
		    orderNr += " - etterlevering";
		}
		return orderNr;
	    }
	},
	ADRESSE {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return transportable.getOrder().getDeliveryAddress();
	    }
	},
	PRODUKTOMRÅDE {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return transportable.getProductAreaGroup().getProductAreaGroupName();
	    }
	},
	TRANSPORT {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return transportable.getTransport();
	    }
	},
	POSTNUMMER {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return transportable.getOrder().getPostalCode();
	    }
	},
	EGENPRODUKSJON {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return ((Order) transportable).getCost("Egenproduksjon", "Kunde");
	    }
	},
	FRAKT {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return ((Order) transportable).getCost("Frakt", "Kunde");
	    }
	},
	MONTERING {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return ((Order) transportable).getCost("Montering", "Kunde");
	    }
	},
	INTERN {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return ((Order) transportable).getCost("Egenproduksjon", "Intern");
	    }
	},
	DEKNINGSGRAD {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return ((Order) transportable).getContributionRate();
	    }
	},
	STATUS {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return ((Order) transportable).getProbability();
	    }
	},
	SANNSYNLIGHET {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return transportable.getProbability();
	    }
	},
	TAKSTOLTEGNER {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return transportable.getTrossDrawer();
	    }
	},
	PRODUKE {
	    @Override
	    public Object getValue(Transportable transportable, Map<String, String> statusMap, StatusCheckerInterface<Transportable> steinChecker,
		    OrderPanelTypeEnum orderPanelTypeEnum) {
		return transportable.getOrder().getProductionWeek();
	    }
	};
	public abstract Object getValue(Transportable transportable, Map<String, String> statusMap,
		StatusCheckerInterface<Transportable> steinChecker, OrderPanelTypeEnum orderPanelTypeEnum);

    }

    public int getProductAreaGroupColumn() {
	return productAreaGroupColumn;
    }

}
