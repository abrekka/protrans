package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import no.ugland.utransprod.gui.handlers.OrderArticleViewHandler;

import org.jdesktop.swingx.JXTreeTable;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Klasse som håndterer visning av artikler for en gitt ordre
 * @author atle.brekka
 */
/**
 * @author atle.brekka
 * @param <T>
 * @param <E>
 */
public class OrderArticleView<T, E> {
    private JXTreeTable treeTableOrderLines;

    private JButton buttonAddArticle;

    private JButton buttonRemoveArticle;

    private JButton buttonEditArticle;

    private OrderArticleViewHandler<T, E> viewHandler;

    private JButton buttonEditAll;

    private JButton buttonExternalOrder;
    private JButton buttonShowOrdln;

    private boolean buttonsBelow = false;
    private JButton buttonImportOrderLines;
    private boolean importOrderLinesButtton;
    /**
     * @param handler
     * @param useButtonsBelow
     */
    public OrderArticleView(final OrderArticleViewHandler<T, E> handler,
            final boolean useButtonsBelow,final boolean useImportOrderLinesButtton) {
    	importOrderLinesButtton=useImportOrderLinesButtton;
        viewHandler = handler;
        buttonsBelow = useButtonsBelow;
    }

    /**
     * Initierere komponenter
     * @param window
     */
    private void initComponents(final WindowInterface window) {
        buttonAddArticle = viewHandler.getAddArticleButton(window, null);

        buttonRemoveArticle = viewHandler.getRemoveArticleButton(window, null);

        buttonEditArticle = viewHandler.getEditArticleButton(window);

        buttonEditAll = viewHandler.getEditAllButton(window);
        buttonExternalOrder = viewHandler.getButtonExternalOrder(window);

        treeTableOrderLines = viewHandler.getTreeTable(window);
        buttonShowOrdln=viewHandler.getButtonShowOrdln(window);
        if(importOrderLinesButtton){
        buttonImportOrderLines=viewHandler.getButtonImportOrderLines(window);
        }

    }

    /**
     * Initierer hendelsehåndtering
     */
    private void initEventHandling() {
        treeTableOrderLines.getSelectionModel().addListSelectionListener(
                viewHandler.getArticleSelectionListener());
    }

    /**
     * Setter kolonnebredder for tabell
     */
    private void initColumnWidth() {
        // Artikler
        // Artikkel
        TableColumn col = treeTableOrderLines.getColumnModel().getColumn(0);
        col.setPreferredWidth(190);

        // Verdi
        col = treeTableOrderLines.getColumnModel().getColumn(1);
        col.setPreferredWidth(50);
    }

    /**
     * Bygger panel
     * @param window
     * @return panel
     */
    public JPanel buildPanel(WindowInterface window) {
        initComponents(window);
        initEventHandling();
        initColumnWidth();
        FormLayout layout;
        if (buttonsBelow) {
            layout = new FormLayout("190dlu", "top:110dlu:grow,3dlu,p");
        } else {
            layout = new FormLayout("185dlu,3dlu,p", "top:110dlu:grow");
        }
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        builder.add(new JScrollPane(treeTableOrderLines), cc.xy(1, 1));
        if (buttonsBelow) {
            builder.add(ButtonBarFactory.buildCenteredBar(buttonAddArticle,
                    buttonEditArticle, buttonRemoveArticle), cc.xy(1, 3));
        } else {
            builder.add(buildArticleButtons(), cc.xy(3, 1));
        }
        return builder.getPanel();
    }

    /**
     * Bygger panel med knapper for artikler
     * @return panel
     */
    private JPanel buildArticleButtons() {
        ButtonStackBuilder buttonBuilder = new ButtonStackBuilder();
        buttonBuilder.addGridded(buttonAddArticle);
        buttonBuilder.addRelatedGap();
        buttonBuilder.addGridded(buttonEditArticle);
        buttonBuilder.addRelatedGap();
        buttonBuilder.addGridded(buttonEditAll);
        buttonBuilder.addRelatedGap();
        buttonBuilder.addGridded(buttonRemoveArticle);
        buttonBuilder.addRelatedGap();
        buttonBuilder.addGridded(buttonExternalOrder);
        buttonBuilder.addRelatedGap();
        buttonBuilder.addGridded(buttonShowOrdln);
        if(importOrderLinesButtton){
        buttonBuilder.addRelatedGap();
        buttonBuilder.addGridded(buttonImportOrderLines);
        }

        return buttonBuilder.getPanel();
    }
}
