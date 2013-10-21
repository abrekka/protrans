package no.ugland.utransprod.gui;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.edit.EditViewable;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandler;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandler.PackProduction;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.model.ArticleType;

import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ArticleProductionPackageView implements EditViewable {
	private JXTable tableArticles;
	private ArticlePackageViewHandler viewHandler;
	private JButton buttonOk;
	private List<Applyable> articles;
	private PackProduction packProduction;
	private JButton buttonSetProductionUnit;
	private JButton buttonSetProductionDate;

	@Inject
	public ArticleProductionPackageView(
			final ArticlePackageViewHandlerFactory aArticlePackageViewHandlerFactory,
			@Assisted ArticleType articleType,@Assisted String defaultColliName) {
		viewHandler = aArticlePackageViewHandlerFactory.create(articleType,defaultColliName);
	}

	public void setArticles(List<Applyable> someArticles,
			final PackProduction aPackProduction) {
		packProduction = aPackProduction;
		articles = someArticles;
	}

	public JPanel buildPanel(WindowInterface window) {
		initComponents(window);
		FormLayout layout = new FormLayout("10dlu,245dlu,3dlu,p,3dlu,p,10dlu",
				"10dlu,p,3dlu,fill:100dlu:grow,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		//PanelBuilder builder = new PanelBuilder(new FormDebugPanel(), layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel("Artikler:",cc.xy(2, 2));
		builder.add(new JScrollPane(tableArticles), cc.xy(2, 4));
		if (packProduction == PackProduction.PRODUCTION) {
			builder.add(buildButtons(), cc.xy(4, 4));
		}
		builder.addLabel("Kollier:",cc.xy(6, 2));
		builder.add(buildColliesPanel(window), cc.xy(6, 4));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonOk), cc
				.xyw(2, 6, 5));
		return builder.getPanel();
	}

	private JPanel buildColliesPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("p",
				"fill:p:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		
		
		builder.add(viewHandler.getColliListView().buildPanel(window,"50",true),cc.xy(1, 1));

		return builder.getPanel();
	}

	private JPanel buildButtons() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		builder.addGridded(buttonSetProductionUnit);
		builder.addRelatedGap();
		builder.addGridded(buttonSetProductionDate);
		return builder.getPanel();
	}

	private void initComponents(WindowInterface window) {
		tableArticles = viewHandler.getTableArticles(articles, packProduction,window);
		buttonOk = viewHandler.getButtonOk(window);
		buttonSetProductionUnit = viewHandler
				.getButtonSetProductionUnit(window);
		buttonSetProductionDate = viewHandler
				.getButtonSetProductionDate(window);

	}

	public String getDialogName() {
		return "ArticlePackageView";
	}

	public String getHeading() {
		return "Artikler";
	}

}
