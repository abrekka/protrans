package no.ugland.utransprod.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.UIManager;

import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.auth.LoginService;
import org.jdesktop.swingx.auth.PasswordStore;
import org.jdesktop.swingx.auth.UserNameStore;

/**
 * Klasse som overskriver JXLoginPanel for å tilpasse inlogginsvindu
 * @author atle.brekka
 */
public class LoginPanel extends JXLoginPane {
    private static final long serialVersionUID = 1L;

    /**
     * @param service
     * @param passwordStore
     * @param userStore
     * @param servers
     */
    public LoginPanel(final LoginService service,
            final PasswordStore passwordStore, final UserNameStore userStore,
            final List<String> servers) {
        super(service, passwordStore, userStore, servers);
    }

    /**
     * @param parent
     * @param svc
     * @return status
     */
    public static Status showLoginDialog(final Component parent,
            final LoginService svc) {
        return showLoginDialog(parent, svc, null, null);
    }

    /**
     * Shows a login dialog. This method blocks.
     * @param parent
     * @param svc
     * @param ps
     * @param us
     * @return The status of the login operation
     */
    public static Status showLoginDialog(final Component parent,
            final LoginService svc, final PasswordStore ps,
            final UserNameStore us) {
        return showLoginDialog(parent, svc, ps, us, null);
    }

    /**
     * @param parent
     * @param svc
     * @param ps
     * @param us
     * @param servers
     * @return status
     */
    public static Status showLoginDialog(final Component parent,
            final LoginService svc, final PasswordStore ps,
            final UserNameStore us, final List<String> servers) {
        LoginPanel panel = new LoginPanel(svc, ps, us, servers);
        return showLoginDialog(parent, panel);
    }

    @Override
    protected final Image createLoginBanner() {
        if(getUI() == null){
            return null;
        }
        return getBannerTest();
        //return getUI() == null ? null : getBannerTest();
    }

    /**
     * @return bilde
     */
    public final Image getBannerTest() {
        int w = 410;
        int h = 60;
        float loginStringX = w * .05f;
        float loginStringY = h * .75f;

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        Font font = UIManager.getFont("JXLoginPane.banner.font");
        g2.setFont(font);
        Graphics2D originalGraphics = g2;
        if (!getComponentOrientation().isLeftToRight()) {
            originalGraphics = (Graphics2D) g2.create();
            g2.scale(-1, 1);
            g2.translate(-w, 0);
            loginStringX = w
                    - (((float) font.getStringBounds(getBannerText(),
                            originalGraphics.getFontRenderContext()).getWidth()) + w * .05f);
        }

        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        // draw a big square
        g2.setColor(UIManager.getColor("JXLoginPane.banner.darkBackground"));
        g2.fillRect(0, 0, w, h);

        // create the curve shape
        GeneralPath curveShape = new GeneralPath(GeneralPath.WIND_NON_ZERO);
        curveShape.moveTo(0, h * .6f);
        curveShape.curveTo(w * .167f, h * 1.2f, w * .667f, h * -.5f, w,
                h * .75f);
        curveShape.lineTo(w, h);
        curveShape.lineTo(0, h);
        curveShape.lineTo(0, h * .8f);
        curveShape.closePath();

        // draw into the buffer a gradient (bottom to top), and the text "Login"
        GradientPaint gp = new GradientPaint(0, h, UIManager
                .getColor("JXLoginPane.banner.darkBackground"), 0, 0, UIManager
                .getColor("JXLoginPane.banner.lightBackground"));
        g2.setPaint(gp);
        g2.fill(curveShape);
        Image image = (Image) UIManager.get("JXLoginPane.banner.image");
        int x = UIManager.getInt("JXLoginPane.banner.image.x");
        int y = UIManager.getInt("JXLoginPane.banner.image.y");
        g2.drawImage(image, x, y, null);
        originalGraphics.setColor(UIManager
                .getColor("JXLoginPane.banner.foreground"));
        originalGraphics
                .drawString(getBannerText(), loginStringX, loginStringY);
        return img;
    }
}
