package no.ugland.utransprod.gui.handlers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import com.jgoodies.binding.list.SelectionInList;

public class EmptySelectionListener implements PropertyChangeListener {
    private final List<JButton> buttons;
    private final SelectionInList selectionInList;
    public EmptySelectionListener(SelectionInList aSelectionInList){
        this(null,aSelectionInList);
    }
    public EmptySelectionListener(JButton aButton,SelectionInList aSelectionInList){
        buttons = new ArrayList<JButton>();
        if(aButton!=null){
            buttons.add(aButton);
        }
        selectionInList=aSelectionInList;
    }
    public void addButton(JButton button){
        buttons.add(button);
    }
    public void propertyChange(PropertyChangeEvent arg0) {
        boolean hasSelection = selectionInList.hasSelection();
        for(JButton button:buttons){
            button.setEnabled(hasSelection);
        }

    }
}
