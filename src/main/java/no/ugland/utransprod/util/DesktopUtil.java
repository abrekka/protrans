package no.ugland.utransprod.util;

import java.io.File;
import java.io.IOException;

import no.ugland.utransprod.gui.WindowInterface;

import org.jdesktop.jdic.desktop.Desktop;
import org.jdesktop.jdic.desktop.DesktopException;
import org.jdesktop.jdic.filetypes.Action;
import org.jdesktop.jdic.filetypes.Association;
import org.jdesktop.jdic.filetypes.AssociationAlreadyRegisteredException;
import org.jdesktop.jdic.filetypes.AssociationNotRegisteredException;
import org.jdesktop.jdic.filetypes.AssociationService;
import org.jdesktop.jdic.filetypes.RegisterFailedException;

public class DesktopUtil {

    private AssociationService assocService;
    private boolean shouldUnregister = false;

    public DesktopUtil() {
        assocService = new AssociationService();
    }

    public boolean openFile(final File showFile, final WindowInterface window) throws DesktopException {
        boolean success = false;
        if (checkFileAccociation(showFile, window)) {
            Desktop.open(showFile);
            unregister(showFile);
            success = true;
        }
        return success;
    }
    
    public static boolean openFileNew(final File fileToShow){
    	try {
			java.awt.Desktop.getDesktop().open(fileToShow);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }

    private void unregister(File showFile) {
        try {
            if (shouldUnregister) {
                Association assoc = getFileAccociation(FileExtensionFilter.getExtension(showFile));
                if (assoc != null) {
                    assocService.unregisterSystemAssociation(assoc);
                }
            }
        } catch (AssociationNotRegisteredException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RegisterFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        shouldUnregister = false;
    }

    private boolean checkFileAccociation(final File showFile, final WindowInterface window) {
        return hasFileAccociation(showFile) ? true : accociateFile(showFile, window);
    }

    private boolean accociateFile(final File showFile, final WindowInterface window) {
        try {
            if (window == null
                    || Util
                            .showConfirmDialog(window, "Filtype?",
                                    "Det er ingen applikasjon assosiert med denne file, vil du åpne med teksteditor?")) {
                Association assoc = getFileAccociation(".txt");

                Association newAssoc = new Association();

                newAssoc.addFileExtension(FileExtensionFilter.getExtension(showFile));
                Action openAction = new Action("open", assoc.getActionByVerb("open").getCommand());
                newAssoc.addAction(openAction);

                assocService.registerSystemAssociation(newAssoc);
                shouldUnregister = true;
                return true;
            }
        } catch (AssociationAlreadyRegisteredException e) {
            e.printStackTrace();
        } catch (RegisterFailedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Association getFileAccociation(String fileExtension) {

        return assocService.getFileExtensionAssociation(fileExtension);
    }

    private boolean hasFileAccociation(File showFile) {
        return getFileAccociation(FileExtensionFilter.getExtension(showFile)) != null;
    }

}
