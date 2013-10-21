package no.ugland.utransprod.gui.handlers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.Model;

/**
 * Håndterer setting av initialer for pakkere
 * @author atle.brekka
 */
public class PackInitialsViewHandler implements Closeable {
    /**
     * 
     */
    private final PresentationModel presentationModel;

    /**
     * 
     */
    private final List<String> packInitials;

    /**
     * 
     */
    boolean canceled = false;

    public PackInitialsViewHandler(String initials,BigDecimal colliHeight,ProductAreaGroup productAreaGroup,ApplicationUserManager applicationUserManager) {
        String[] initialsArray = null;
        if (initials != null) {
            initialsArray = initials.split("/");
        }
        presentationModel = new PresentationModel(new Initials(initialsArray,colliHeight));
        
        packInitials = applicationUserManager.findAllPackers(productAreaGroup);
    }

    /**
     * Lager komboboks med initialer
     * @return komboboks
     */
    public JComboBox getComboBoxInitials1() {
        JComboBox comboBox = new JComboBox(new ComboBoxAdapter(packInitials,
                presentationModel.getModel(Initials.PROPERTY_INITIALS_1)));
        comboBox.setName("ComboBoxInitials1");
        return comboBox;
    }

    /**
     * Lager komboboks med initialer
     * @return komboboks
     */
    public JComboBox getComboBoxInitials2() {
        return new JComboBox(new ComboBoxAdapter(packInitials,
                presentationModel.getModel(Initials.PROPERTY_INITIALS_2)));
    }

    /**
     * Lager komboboks med initialer
     * @return komboboks
     */
    public JComboBox getComboBoxInitials3() {
        return new JComboBox(new ComboBoxAdapter(packInitials,
                presentationModel.getModel(Initials.PROPERTY_INITIALS_3)));
    }

    /**
     * Lager tekstfelt for kollihøyde
     * @return tekstfelt
     */
    public JTextField getTextFieldColliHeight() {
        JTextField textField = BasicComponentFactory
                .createTextField(presentationModel
                        .getModel(Initials.PROPERTY_COLLI_HEIGHT_STRING));
        textField.setName("TextFieldColliHeight");
        return textField;
    }

    /**
     * Lager ok-knapp
     * @param window
     * @return knapp
     */
    public JButton getButtonOk(WindowInterface window) {
        JButton button = new CancelButton(window, this, true, "Ok",
                IconEnum.ICON_OK, null, true);
        button.setName("ButtonOk");
        return button;
    }

    /**
     * Lager avbrytknapp
     * @param window
     * @return knapp
     */
    public JButton getButtonCancel(WindowInterface window) {
        JButton button = new CancelButton(window, new CloseAction(), true);
        button.setName("ButtonCancel");
        return button;
    }

    /**
     * Henter alle initialer som en streng separert med /
     * @return initialstreng
     */
    @SuppressWarnings("unchecked")
    public String getInitials() {
        List<String> initialList = (List<String>) presentationModel
                .getValue(Initials.PROPERTY_INITIAL_LIST);
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        for (String initials : initialList) {
            if (initials != null) {
                if (counter > 0) {
                    builder.append("/");
                }
                builder.append(initials);
                counter++;
            }
        }
        return builder.toString();
    }

    /**
     * Henter kollihøyde
     * @return kollihøyde
     */
    public BigDecimal getColliHeight() {
        return (BigDecimal) presentationModel
                .getValue(Initials.PROPERTY_COLLI_HEIGHT);
    }

    /**
     * Intern klasse som holder på alle initialer.
     * @author atle.brekka
     */
    public class Initials extends Model {
        private static final long serialVersionUID = 1L;

        public static final String PROPERTY_INITIALS_1 = "initials1";

        public static final String PROPERTY_INITIALS_2 = "initials2";

        public static final String PROPERTY_INITIALS_3 = "initials3";

        public static final String PROPERTY_INITIAL_LIST = "initialList";

        public static final String PROPERTY_COLLI_HEIGHT_STRING = "colliHeightString";

        public static final String PROPERTY_COLLI_HEIGHT = "colliHeight";

        private String initials1;

        private String initials2;

        private String initials3;

        private BigDecimal colliHeight;

        public Initials(String[] initials,BigDecimal height) {
            colliHeight=height;
            if (initials != null) {
                if (initials.length > 0) {
                    initials1 = initials[0];
                }
                if (initials.length > 1) {
                    initials2 = initials[1];
                }
                if (initials.length > 2) {
                    initials3 = initials[2];
                }
            }

        }

        /**
         * Lager liste med alle initialer
         * @return initialer
         */
        public List<String> getInitialList() {
            List<String> returnList = new ArrayList<String>();
            Collections.addAll(returnList, new String[] {initials1, initials2,
                    initials3});
            return returnList;
        }

        /**
         * @return initialer
         */
        public String getInitials1() {
            return initials1;
        }

        /**
         * @param initials
         */
        public void setInitials1(String initials) {
            String oldInitials = getInitials1();
            initials1 = initials;
            firePropertyChange(PROPERTY_INITIALS_1, oldInitials, initials);
        }

        /**
         * @return initialer
         */
        public String getInitials2() {
            return initials2;
        }

        /**
         * @param initials
         */
        public void setInitials2(String initials) {
            String oldInitials = getInitials2();
            initials2 = initials;
            firePropertyChange(PROPERTY_INITIALS_2, oldInitials, initials);
        }

        /**
         * @return initialer
         */
        public String getInitials3() {
            return initials3;
        }

        /**
         * @param initials
         */
        public void setInitials3(String initials) {
            String oldInitials = getInitials3();
            initials3 = initials;
            firePropertyChange(PROPERTY_INITIALS_3, oldInitials, initials);
        }

        /**
         * @return kollihøyde som streng
         */
        public String getColliHeightString() {
            if (colliHeight != null) {
                return String.valueOf(colliHeight);
            }
            return null;
        }

        /**
         * @param colliHeight
         */
        public void setColliHeightString(String colliHeight) {
            String oldHeight = getColliHeightString();
            if (colliHeight != null) {
                this.colliHeight = BigDecimal.valueOf(Double
                        .valueOf(colliHeight.replaceAll(",", ".")));
            } else {
                this.colliHeight = null;
            }
            firePropertyChange(PROPERTY_COLLI_HEIGHT_STRING, oldHeight,
                    colliHeight);
        }

        /**
         * @return kollihøyde
         */
        public BigDecimal getColliHeight() {
            return colliHeight;
        }

        /**
         * @param colliHeight
         */
        public void setColliHeight(BigDecimal colliHeight) {
            BigDecimal oldHeight = getColliHeight();
            this.colliHeight = colliHeight;

            firePropertyChange(PROPERTY_COLLI_HEIGHT, oldHeight, colliHeight);
        }
    }

    /**
     * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    public boolean canClose(String actionString, WindowInterface window) {
        if (presentationModel.getValue(Initials.PROPERTY_INITIALS_1) == null) {
            Util.showErrorDialog(window, "Må sette pakker",
                    "Må sette mins en pakker!");
            return false;
        }
        return true;
    }

    /**
     * Sjekker om dialog er avbrutt
     * @return true dersom avbrutt
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * Avbryt
     * @author atle.brekka
     */
    class CloseAction implements Closeable {

        /**
         * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
         *      no.ugland.utransprod.gui.WindowInterface)
         */
        public boolean canClose(String actionString, WindowInterface window) {
            canceled = true;
            return true;
        }

    }

}
