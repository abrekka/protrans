package no.ugland.utransprod.gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;

import no.ugland.utransprod.util.PrefsUtil;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.ColumnControlButton;

public class UBColumnControlPopup extends ColumnControlButton {
    JXTable table;

    ProductAreaGroupProvider productAreaGroupProvider;

    public UBColumnControlPopup(final JXTable aTable,
            final ProductAreaGroupProvider aProductAreaGroupProvider) {
        super(aTable);
        table = aTable;
        productAreaGroupProvider = aProductAreaGroupProvider;

    }

    @Override
    protected final void addAdditionalActionItems() {
        super.addAdditionalActionItems();
        List<Action> extraActions = new ArrayList<Action>();
        extraActions.add(new RestoreSystemSetupAction());
        getColumnControlPopup().addAdditionalActionItems(extraActions);
    }

    private class RestoreSystemSetupAction extends AbstractAction {
        public RestoreSystemSetupAction() {
            super("Sett systemdefault");
        }

        public void actionPerformed(final ActionEvent e) {
            PrefsUtil.clearUserPrefs(productAreaGroupProvider
                    .getProductAreaGroupName(), table.getName());
            PrefsUtil.setInvisibleColumns(productAreaGroupProvider
                    .getProductAreaGroupName(), table.getName(), table);
        }
    }
}
