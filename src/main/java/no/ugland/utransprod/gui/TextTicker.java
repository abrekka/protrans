package no.ugland.utransprod.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Viser tekst på et tivker panel
 * 
 * @author atle.brekka
 * 
 */
public class TextTicker extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	JPanel tickerpanel;

	/**
	 * 
	 */
	private List<String> texts;

	/**
	 * 
	 */
	private boolean changed = true;

	/**
	 * 
	 */
	Font infoFont;

	/**
	 * @param text
	 * @param font
	 */
	public TextTicker(List<String> text, Font font)

	{
		texts = text;
		infoFont = font;
		tickerpanel = new JPanel();
		tickerpanel.setFont(infoFont);
		this.setFont(infoFont);
		tickerpanel.setLocation(0, 0);
		tickerpanel.setFont(infoFont);
		add(tickerpanel);
		setUpLabels();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					int x = tickerpanel.getX() - 1;
					if (x < -tickerpanel.getWidth()) {
						x = getWidth();
					}
					tickerpanel.setLocation(x, 0);
					tickerpanel.setFont(infoFont);
					try {
						Thread.sleep(25);
					} catch (InterruptedException exception) {
					}
				}
			}
		}).start();

		Timer t = new javax.swing.Timer(5000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setUpLabels();
			}
		});
		t.start();

	}

	/**
	 * Setter tekster som skal vises
	 * 
	 * @param newTexts
	 */
	public void setTexts(List<String> newTexts) {
		texts = newTexts;
		changed = true;
	}

	/**
	 * Setter opp labels med tekst
	 */
	void setUpLabels() {
		if (changed) {
			tickerpanel.removeAll();
			int counter = 0;
			for (String text : texts) {
				if (counter > 0) {
					tickerpanel.add(Box.createHorizontalStrut(20));
				}
				TickerLabel link = new TickerLabel(text);
				link.setFont(infoFont);
				tickerpanel.add(link);
				tickerpanel.setFont(infoFont);
			}
			tickerpanel.updateUI();
			changed = false;
		}
	}

	/**
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		Dimension preferredSizeOfPanel = tickerpanel.getPreferredSize();
		return new Dimension(preferredSizeOfPanel.width / 2,
				preferredSizeOfPanel.height + 50);
	}

	/**
	 * Label for presentasjon av tekst
	 * 
	 * @author atle.brekka
	 * 
	 */
	class TickerLabel extends JLabel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @param text
		 */
		public TickerLabel(final String text) {
			setFont(infoFont);
			setText(text);

		}
	}
}
