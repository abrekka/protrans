package no.ugland.utransprod.gui.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.Packable;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.Util;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.inject.internal.Lists;

public class ArticlePacker {
    private ColliViewHandlerProvider colliViewHandlerProvider;
    private Multimap<String, String> colliSetup;
    private VismaFileCreator vismaFileCreator;

    public ArticlePacker(ColliViewHandlerProvider aColliViewHandlerProvider, Multimap<String, String> aColliSetup,
	    final VismaFileCreator aVismaFileCreator) {
	vismaFileCreator = aVismaFileCreator;
	colliSetup = aColliSetup;
	colliViewHandlerProvider = aColliViewHandlerProvider;
    }

    public void packOrderLines(final List<OrderLine> orderLines, Packable packable, final WindowInterface window, final boolean useDefaultColli) {
	List<String> articleNames = Lists.newArrayList(Iterables.transform(orderLines, tilArticleName()));
	if (canPack(articleNames)) {
	    createColliAndPackArticle(articleNames, packable, orderLines, window, useDefaultColli);
	} else {
	    Util.showMsgDialog(window.getComponent(), "Kan ikke pakke artikkel", "Denne artikkelen kan ikke pakkes her!");
	}

    }

    private Function<OrderLine, String> tilArticleName() {
	return new Function<OrderLine, String>() {

	    public String apply(OrderLine orderline) {
		return orderline.getArticleName();
	    }
	};
    }

    public boolean canPack(List<String> articleNames) {
	for (String articleName : articleNames) {
	    if (ApplicationParamUtil.getNotPackageList().indexOf(articleName) >= 0) {
		return false;
	    }
	}
	return true;
	// return ApplicationParamUtil.getNotPackageList().indexOf(articleName)
	// < 0 ? true : false;
    }

    private void createColliAndPackArticle(List<String> articleNames, Packable packable, List<OrderLine> orderLines, WindowInterface window,
	    boolean useDefaultColli) {
	String useColliName = useDefaultColli ? getColliName(articleNames) : null;
	List<Colli> collies = packable.getColliList();
	Collections.sort(collies, garasjepakkeFoerst());

	Colli useColli = getColliToUse(useColliName, collies, window);

	if (useColli != null) {
	    int teller = 0;
	    for (OrderLine orderLine : orderLines) {
		teller++;
		addArticleToColliAndSendFileToVisma(orderLine, useColli, window, teller);
	    }
	}
    }

    private Comparator<Colli> garasjepakkeFoerst() {
	return new Comparator<Colli>() {

	    public int compare(Colli colli, Colli annenColli) {
		if ("Garasjepakke".equalsIgnoreCase(colli.getColliName())) {
		    return -1;
		}
		return colli.getColliName().compareTo(annenColli.getColliName());
	    }
	};
    }

    private void addArticleToColliAndSendFileToVisma(OrderLine orderLine, Colli useColli, WindowInterface window, int teller) {
	try {
	    addArticleToColli(orderLine, useColli);
	    sendFileToVisma(orderLine, teller);
	} catch (ProTransException e1) {
	    Util.showErrorDialog(window, "Feil", e1.getMessage());
	    e1.printStackTrace();
	}
    }

    private void addArticleToColli(OrderLine orderLine, Colli useColli) throws ProTransException {
	ColliViewHandler colliViewHandler = colliViewHandlerProvider.getColliViewHandler(useColli);
	if (colliViewHandler != null) {
	    colliViewHandler.addOrderLine(orderLine, 0);
	}
    }

    private void sendFileToVisma(final OrderLine orderLine, int teller) throws ProTransException {
	if (orderLineIsImportedfromVisma(orderLine)) {
	    vismaFileCreator.createVismaFile(addOrderLineToList(orderLine), teller);
	}

    }

    private List<OrderLine> addOrderLineToList(OrderLine orderLine) {
	List<OrderLine> orderLineList = new ArrayList<OrderLine>();
	orderLineList.add(orderLine);
	return orderLineList;
    }

    private boolean orderLineIsImportedfromVisma(OrderLine orderLine) {
	return orderLine.getOrdln() != null;
    }

    private Colli getColliToUse(String useColliName, List<Colli> collies, WindowInterface window) {
	List<Colli> useColliList = null;
	Colli useColli = null;
	if (useColliName != null) {
	    useColliList = getColliFromColliName(useColliName, collies);
	    if (useColliList.size() == 1) {
		useColli = useColliList.get(0);
	    } else if (useColliList.size() > 1) {
		Colli defaultColli = useColliList.get(0);
		useColli = (Colli) Util.showOptionsDialogCombo(window, useColliList, "Velg kolli", true, defaultColli);
	    }
	} else {
	    Colli defaultColli = getDefaultColli(collies);
	    useColli = (Colli) Util.showOptionsDialogCombo(window, collies, "Velg kolli", true, defaultColli);
	}
	return useColli;
    }

    private Colli getDefaultColli(List<Colli> collies) {
	Colli defaultColli = collies != null && collies.size() > 0 ? collies.get(0) : null;
	return defaultColli;
    }

    private List<Colli> getColliFromColliName(String useColliName, List<Colli> collies) {
	List<Colli> useColli = Lists.newArrayList();
	for (Colli colli : collies) {
	    if (colli.getColliName().startsWith(useColliName)) {
		useColli.add(colli);
	    }
	}
	return useColli;
    }

    private String getColliName(List<String> articleNames) {
	String colliName = null;
	for (String articleName : articleNames) {
	    String tmpColliName = colliSetup.containsValue(articleName) ? getColliNameFromColliSetup(articleName) : null;
	    if (colliName == null) {
		colliName = tmpColliName;
	    } else if (!colliName.equalsIgnoreCase(tmpColliName)) {
		return null;
	    }
	}
	return colliName;
	// return colliSetup.containsValue(articleName) ?
	// getColliNameFromColliSetup(articleName) : null;
    }

    private String getColliNameFromColliSetup(String articleName) {
	String useColliName = null;
	Set<String> colliNames = colliSetup.keySet();

	Collection<String> tmpStr;
	for (String colliName : colliNames) {
	    tmpStr = colliSetup.get(colliName);
	    if (tmpStr.contains(articleName)) {
		useColliName = colliName;
		break;
	    }
	}
	return useColliName;
    }

}
