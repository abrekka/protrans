/*
 * Copyright (c) 2005, romain guy (romain.guy@jext.org) and craig wickesser
 * (craig@codecraig.com) All rights reserved. Redistribution and use in source
 * and binary forms, with or without modification, are permitted provided that
 * the following conditions are met: * Redistributions of source code must
 * retain the above copyright notice, this list of conditions and the following
 * disclaimer. * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution. *
 * Neither the name of the <ORGANIZATION> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package no.ugland.utransprod.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

/**
 * An infinite progress panel displays a rotating figure and a message to notice
 * the user of a long, duration unknown task. The shape and the text are drawn
 * upon a white veil which alpha level (or shield value) lets the underlying
 * component shine through. This panel is meant to be used asa <i>glass pane</i>
 * in the window performing the long operation.
 * <p>
 * Contrary to regular glass panes, you don't need to set it visible or not by
 * yourself. Once you've started the animation all the mouse events are
 * intercepted by this panel, preventing them from being forwared to the
 * underlying components.
 * <p>
 * The panel can be controlled by the <code>start()</code>,
 * <code>stop()</code> and <code>interrupt()</code> methods.
 * <p>
 * Example:
 * <p>
 * <pre>
 *    InfiniteProgressPanel pane = new InfiniteProgressPanel();
 *     frame.setGlassPane(pane);
 *     ... later in some other EDT event (otherwise the panel doesn't know the size and draws real funky)
 *     pane.start()
 * </pre>
 * <p>
 * Several properties can be configured at creation time. The message and its
 * font can be changed at runtime. Changing the font can be done using
 * <code>setFont()</code> and <code>setForeground()</code>.
 * <p>
 * If you experience performance issues, prefer the
 * <code>PerformanceInfiniteProgressPanel</code>.
 * <p>
 * For cancelable progress use the <code>CancelableProgressPanel</code> or the
 * <code>CancelableProgressAdapter</code> with a Panel.
 * @author Romain Guy, 17/02/2005
 * @since 1.0 <br>
 *        $Revision: 1.5 $
 */

public class InfiniteProgressPanel extends JComponent implements MouseListener,
        CancelableAdaptee {
    private static final long serialVersionUID = 3546080263571714356L;

    /** Contains the bars composing the circular shape. */
    private Ticker ticker = null;

    /**
     * The animation thread is responsible for fade in/out and rotation.
     */
    private Thread animation = null;

    /**
     * Notifies whether the animation is running or not.
     */
    boolean started = false;

    /**
     * Alpha level of the veil, used for fade in/out.
     */
    int alphaLevel = 0;

    /**
     * Duration of the veil's fade in/out.
     */
    int rampDelay = 300;

    /**
     * Alpha level of the veil.
     */
    private float shield = 0.70f;

    /**
     * Message displayed below the circular shape.
     */
    private String text = "";

    /**
     * Amount of bars composing the circular shape.
     */
    int barsCount = 14;

    /**
     * Amount of frames per seconde. Lowers this to save CPU.
     */
    float fps = 15.0f;

    /**
     * Rendering hints to set anti aliasing.
     */
    private RenderingHints hints = null;

    /**
     * An infiniteProgressAdapter to performa special drawing, ex: a cancel
     * button.
     */
    InfiniteProgressAdapter infiniteProgressAdapter = null;

    /**
     * Creates a new progress panel with default values:<br />
     * <ul>
     * <li>No message</li>
     * <li>14 bars</li>.
     * <li> Veil's alpha level is 70%</li>
     * <li>15 frames per second</li>
     * <li>Fade in/out last 300 ms</li>
     * </ul>
     */
    public InfiniteProgressPanel() {
        this("");
    }

    /**
     * Creates a new progress panel with default values:<br />.
     * <ul>
     * <li>14 bars</li>
     * <li>Veil's alpha level is 70%</li>
     * <li>15 frames per second</li>
     * <li>Fade in/out last 300 ms</li>
     * </ul>
     * @param aText
     *            The message to be displayed. Can be null or empty.
     */
    public InfiniteProgressPanel(final String aText) {
        this(aText, 14);
    }

    /**
     * Creates a new progress panel with default values:<br />.
     * <ul>
     * <li>Veil's alpha level is 70%</li>
     * <li>15 frames per second</li>
     * <li>Fade in/out last 300 ms</li>
     * </ul>
     * @param aText
     *            The message to be displayed. Can be null or empty.
     * @param aBarsCount
     *            The amount of bars composing the circular shape
     */
    public InfiniteProgressPanel(final String aText, final int aBarsCount) {
        this(aText, aBarsCount, 0.70f);
    }

    /**
     * Creates a new progress panel with default values:<br />.
     * <ul>
     * <li>15 frames per second</li>
     * <li>Fade in/out last 300 ms</li>
     * </ul>
     * @param aText
     *            The message to be displayed. Can be null or empty.
     * @param aBarsCount
     *            The amount of bars composing the circular shape.
     * @param aShield
     *            The alpha level between 0.0 and 1.0 of the colored shield (or
     *            veil).
     */
    public InfiniteProgressPanel(final String aText, final int aBarsCount,
            final float aShield) {
        this(aText, aBarsCount, aShield, 15.0f);
    }

    /**
     * Creates a new progress panel with default values:<br />.
     * <ul>
     * <li>Fade in/out last 300 ms</li>
     * </ul>
     * @param aText
     *            The message to be displayed. Can be null or empty.
     * @param aBarsCount
     *            The amount of bars composing the circular shape.
     * @param aShield
     *            The alpha level between 0.0 and 1.0 of the colored shield (or
     *            veil).
     * @param aFps
     *            The number of frames per second. Lower this value to decrease
     *            CPU usage.
     */
    public InfiniteProgressPanel(final String aText, final int aBarsCount,
            final float aShield, final float aFps) {
        this(aText, aBarsCount, aShield, aFps, 300);
    }

    /**
     * Creates a new progress panel.
     * @param aText
     *            The message to be displayed. Can be null or empty.
     * @param aBarsCount
     *            The amount of bars composing the circular shape.
     * @param aShield
     *            The alpha level between 0.0 and 1.0 of the colored shield (or
     *            veil).
     * @param aFps
     *            The number of frames per second. Lower this value to decrease
     *            CPU usage.
     * @param aRampDelay
     *            The duration, in milli seconds, of the fade in and the fade
     *            out of the veil.
     */
    public InfiniteProgressPanel(final String aText, final int aBarsCount,
            final float aShield, final float aFps, final int aRampDelay) {
        this.text = aText;

        if (aRampDelay >= 0) {
            this.rampDelay = aRampDelay;
        } else {
            this.rampDelay = 0;
        }
        if (aShield >= 0.0f) {
            this.shield = aShield;
        } else {
            this.shield = 0.0f;
        }
        if (aFps > 0.0f) {
            this.fps = aFps;
        } else {
            this.fps = 15.0f;
        }
        if (aBarsCount > 0) {
            this.barsCount = aBarsCount;
        } else {
            this.barsCount = 14;
        }

        this.hints = new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        this.hints.put(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.hints.put(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    /**
     * Changes the displayed message at runtime.
     * @param aText
     *            The message to be displayed. Can be null or empty.
     */
    public final void setText(final String aText) {
        this.text = aText;
        repaint();
    }

    /**
     * Returns the current displayed message.
     * @return melding
     */
    public final String getText() {
        return text;
    }

    /**
     * @param aInfiniteProgressAdapter
     *            an infiniteProgressAdapter to perform special drawing (ex: a
     *            cancel button)
     */
    public final void setInfiniteProgressAdapter(
            final InfiniteProgressAdapter aInfiniteProgressAdapter) {
        this.infiniteProgressAdapter = aInfiniteProgressAdapter;
    }

    /**
     * Adds a listener to the cancel button in this progress panel.
     * @param listener
     * @throws RuntimeException
     *             if the infiniteProgressAdapter is null or is not a
     *             CancelableProgessAdapter
     */
    public final void addCancelListener(final ActionListener listener) {
        if (infiniteProgressAdapter instanceof CancelableProgessAdapter) {
            ((CancelableProgessAdapter) infiniteProgressAdapter)
                    .addCancelListener(listener);
        } else {
            throw new RuntimeException(
                    "Expected CancelableProgessAdapter for cancel listener.  Adapter is "
                            + infiniteProgressAdapter);
        }
    }

    /**
     * Removes a listener to the cancel button in this progress panel.
     * @param listener
     * @throws RuntimeException
     *             if the infiniteProgressAdapter is null or is not a
     *             CancelableProgessAdapter
     */
    public final void removeCancelListener(final ActionListener listener) {
        if (infiniteProgressAdapter instanceof CancelableProgessAdapter) {
            ((CancelableProgessAdapter) infiniteProgressAdapter)
                    .removeCancelListener(listener);
        } else {
            throw new RuntimeException(
                    "Expected CancelableProgessAdapter for cancel listener.  Adapter is "
                            + infiniteProgressAdapter);
        }
    }

    /**
     * Starts the waiting animation by fading the veil in, then rotating the
     * shapes. This method handles the visibility of the glass pane.
     */
    public final void start() {
        addMouseListener(this);
        setVisible(true);
        buildTicker();
        animation = new Thread(new Animator(true));
        if (infiniteProgressAdapter != null) {
            infiniteProgressAdapter.animationStarting();
        }
        animation.start();
    }

    /**
     * Stops the waiting animation by stopping the rotation of the circular
     * shape and then by fading out the veil. This methods sets the panel
     * invisible at the end.
     */
    public final void stop() {
        if (infiniteProgressAdapter != null) {
            infiniteProgressAdapter.animationStopping();
        }
        if (animation != null) {
            animation.interrupt();
            try {
                animation.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            animation = null;

            animation = new Thread(new Animator(false));
            animation.start();
        }
    }

    /**
     * @see no.ugland.utransprod.util.CancelableAdaptee#getComponent()
     */
    public final JComponent getComponent() {
        return this;
    }

    /**
     * Interrupts the animation, whatever its state is. You can use it when you
     * need to stop the animation without running the fade out phase. This
     * methods sets the panel invisible at the end.
     */
    public final void interrupt() {
        if (animation != null) {
            animation.interrupt();
            animation = null;

            removeMouseListener(this);
            setVisible(false);
        }
    }

    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public final void paintComponent(final Graphics g) {
        if (started) {
            int width = getWidth();
            int height = getHeight();

            if (width == 0 || height == 0) {
                return;
            }

            @SuppressWarnings("hiding")
            final Ticker tmpTicker = getTicker();
            if (tmpTicker == null) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHints(hints);

            g2.setColor(new Color(255, 255, 255, (int) (alphaLevel * shield)));
            g2.fillRect(0, 0, getWidth(), getHeight());

            for (int i = 0; i < tmpTicker.bars.length; i++) {
                int channel = 224 - 128 / (i + 1);
                g2.setColor(new Color(channel, channel, channel, alphaLevel));
                g2.fill(tmpTicker.bars[i]);
            }

            double textMaxY = drawTextAt(text, getFont(), g2, width,
                    tmpTicker.maxY, getForeground());

            if (infiniteProgressAdapter != null) {
                infiniteProgressAdapter.paintSubComponents(textMaxY);
            }
        }
    }

    /**
     * Draw text in a Graphics2D.
     * @param text
     *            the text to draw
     * @param font
     *            the font to use
     * @param g2
     *            the graphics context to draw in
     * @param width
     *            the width of the parent, so it can be centered
     * @param aY
     *            the height at which to draw
     * @param foreGround
     *            the foreground color to draw in
     * @return the y value that is the y param + the text height.
     */
    public static double drawTextAt(final String text, final Font font,
            final Graphics2D g2, final int width, final double aY,
            final Color foreGround) {
        double y = aY;
        if (text != null && text.length() > 0) {
            FontRenderContext context = g2.getFontRenderContext();
            TextLayout layout = new TextLayout(text, font, context);
            Rectangle2D bounds = layout.getBounds();
            g2.setColor(foreGround);
            float textX = (float) (width - bounds.getWidth()) / 2;
            y = (float) (y + layout.getLeading() + 2 * layout.getAscent());
            layout.draw(g2, textX, (float) y);
        }
        return y;
    }

    /**
     * Ticker is not built until set bounds is called (or our width and height
     * are > 0).
     * @return null if not ready, or the built ticker
     */
    final Ticker getTicker() {
        if (ticker == null) {
            buildTicker();
        }
        return ticker;
    }

    /**
     * Builds the circular shape and returns the result as an array of
     * <code>Area</code>. Each <code>Area</code> is one of the bars
     * composing the shape.
     */
    private void buildTicker() {
        Area[] areas = new Area[barsCount];
        int width = getWidth();
        int height = getHeight();

        // Sometimes the bounds are set, rebuild the ticker later.
        if (width == 0 || height == 0) {
            return;
        }
        Point2D.Double center = new Point2D.Double((double) width / 2,
                (double) height / 2);
        double fixedAngle = 2.0 * Math.PI / barsCount;
        double maxY = 0.0d;
        for (double i = 0.0; i < barsCount; i++) {
            Area primitive = buildPrimitive();

            AffineTransform toCenter = AffineTransform.getTranslateInstance(
                    center.getX(), center.getY());
            AffineTransform toBorder = AffineTransform.getTranslateInstance(
                    35.0, -6.0);
            AffineTransform toCircle = AffineTransform.getRotateInstance(-i
                    * fixedAngle, center.getX(), center.getY());

            AffineTransform toWheel = new AffineTransform();
            toWheel.concatenate(toCenter);
            toWheel.concatenate(toBorder);

            primitive.transform(toWheel);
            primitive.transform(toCircle);

            areas[(int) i] = primitive;

            Rectangle2D bounds = primitive.getBounds2D();
            if (bounds.getMaxY() > maxY) {
                maxY = bounds.getMaxY();
            }
        }

        ticker = new Ticker();
        ticker.bars = areas;
        ticker.maxY = maxY;
    }

    /**
     * Builds a bar.
     * @return area
     */
    private Area buildPrimitive() {
        Rectangle2D.Double body = new Rectangle2D.Double(6, 0, 30, 12);

        Ellipse2D.Double head = new Ellipse2D.Double(0, 0, 12, 12);
        Ellipse2D.Double tail = new Ellipse2D.Double(30, 0, 12, 12);

        Area tick = new Area(body);
        tick.add(new Area(head));
        tick.add(new Area(tail));

        return tick;
    }

    /**
     * @author atle.brekka
     */
    class Ticker {
        double maxY = 0.0;

        Area[] bars;
    }

    /**
     * Animation thread.
     */
    private class Animator implements Runnable {
        private boolean rampUp = true;

        /**
         * @param aRampUp
         */
        protected Animator(final boolean aRampUp) {
            this.rampUp = aRampUp;
        }

        /**
         * @see java.lang.Runnable#run()
         */
        public void run() {
            @SuppressWarnings("hiding")
            final Ticker tmpTicker = getTicker();
            if (tmpTicker == null) {
                return;
            }

            Point2D.Double center = new Point2D.Double((double) getWidth() / 2,
                    (double) getHeight() / 2);
            double fixedIncrement = 2.0 * Math.PI / barsCount;
            AffineTransform toCircle = AffineTransform.getRotateInstance(
                    fixedIncrement, center.getX(), center.getY());

            long start = System.currentTimeMillis();
            if (rampDelay == 0) {
                if (rampUp) {
                    alphaLevel = 255;
                } else {
                    alphaLevel = 0;
                }

            }

            started = true;
            boolean inRamp = rampUp;

            while (!Thread.interrupted()) {
                if (!inRamp) {
                    for (int i = 0; i < tmpTicker.bars.length; i++) {
                        tmpTicker.bars[i].transform(toCircle);
                    }
                }

                repaint();

                if (rampUp) {
                    if (alphaLevel < 255) {
                        alphaLevel = (int) (255 * (System.currentTimeMillis() - start) / rampDelay);
                        if (alphaLevel >= 255) {
                            alphaLevel = 255;
                            inRamp = false;
                            if (infiniteProgressAdapter != null) {
                                infiniteProgressAdapter.rampUpEnded();
                            }
                        }
                    }
                } else if (alphaLevel > 0) {
                    alphaLevel = (int) (255 - (255 * (System
                            .currentTimeMillis() - start) / rampDelay));
                    if (alphaLevel <= 0) {
                        alphaLevel = 0;
                        break;
                    }
                } else {
                    break;
                }

                try {
                    int sleepTime;
                    if(inRamp){
                        sleepTime=10;
                    }else{
                        sleepTime=(int) (1000 / fps);
                    }
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ie) {
                    break;
                }
                Thread.yield();
            }

            if (!rampUp) {
                started = false;
                repaint();
                setVisible(false);
                removeMouseListener(InfiniteProgressPanel.this);
            }
        }
    }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(final MouseEvent e) {
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(final MouseEvent e) {
    }

    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(final MouseEvent e) {
    }

    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(final MouseEvent e) {
    }

    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(final MouseEvent e) {
    }
}