package no.ugland.utransprod.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.ArticleTypeViewHandler;
import no.ugland.utransprod.model.ArticleType;

import org.jdesktop.swingx.JXList;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Klasse som håndterer visning av artikkeltyper som kan velges ut i fra en
 * liste
 * @author atle.brekka
 */
public class ArticleTypeView {
    private JButton buttonOk;

    private ArticleTypeViewHandler viewHandler;

    private JXList listArticles;

    /**
     * True dersom det bare skal kunne velges en artikkel
     */
    private boolean useSingleSelection = true;

    private JButton buttonAddArticle;

    private boolean addButton = false;

    private JButton buttonCancel;

    /**
     * @param handler
     * @param singleSelcetion
     * @param useAddButton
     */
    public ArticleTypeView(final ArticleTypeViewHandler handler,
            final boolean singleSelcetion, final boolean useAddButton) {
        viewHandler = handler;
        useSingleSelection = singleSelcetion;
        addButton = useAddButton;
    }

    /**
     * Initierer vinduskomponenter
     * @param window
     */
    private void initComponents(final WindowInterface window) {
        viewHandler.setWindowInterface(window);
        listArticles = viewHandler
                .getListArticles(useSingleSelection);
        buttonOk = viewHandler.getOkButton(window);
        buttonAddArticle = viewHandler.getButtonAddArticle();
        buttonCancel = viewHandler.getCancelButton(window);

    }

    /**
     * Bygger panel for visning av artikkeltype
     * @param window
     * @return panel
     */
    public final JComponent buildPanel(final WindowInterface window) {
        window.setName("ArticleTypeView");
        initComponents(window);

        FormLayout layout = new FormLayout("10dlu,120dlu,3dlu,p,10dlu",
                "10dlu,p,3dlu,100dlu,5dlu,p,3dlu");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        builder.addLabel("Artikler", cc.xy(2, 2));
        builder.add(new JScrollPane(listArticles), cc.xy(2, 4));
        if (addButton) {
            builder.add(buttonAddArticle, cc.xy(4, 4));
        }
        builder.add(ButtonBarFactory.buildCenteredBar(buttonOk, buttonCancel),
                cc.xyw(2, 6, 3));
        return builder.getPanel();
    }

    /**
     * Henter valgte artikkeltyper
     * @return artikkeltyper
     */
    @SuppressWarnings("unchecked")
    public final List<ArticleType> getSelectedObjects() {
        return new ArrayList(Arrays.asList(listArticles.getSelectedValues()));
    }

}
