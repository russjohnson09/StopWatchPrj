/*****************************************************************
Class that manages both the GUI and the TimerListener.

@author Russ Johnson
@version 9.10.2012
 *****************************************************************/
package project1;

/*
 A component that acts as a simple stop-watch.  When the user clicks
 on it, this componet starts timing.  When the user clicks again,
 it displays the time between the two clicks.  Clicking a third time
 starts another timer, etc.  While it is timing, the label just
 displays the message "Timing....".
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TimerListener extends JPanel {

	private final int WAIT;
	private JButton start;
	private JLabel label;
	private JPanel buttonPanel;

	private Timer timer;
	private StopWatch watch;
	private boolean hasrun;

	public TimerListener() {
		WAIT = 16;
		start = new JButton("Start");
		ButtonListener listener = new ButtonListener();
		start.addActionListener(listener);
		label = new JLabel("0:00:000");
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(200, 40));
		buttonPanel.setBackground(Color.blue);
		buttonPanel.add(start);
		setPreferredSize(new Dimension(200, 80));
		setBackground(Color.cyan);
		add(label);
		add(buttonPanel);
		timer = new Timer(WAIT, new CycleListener());
		watch = new StopWatch();
		hasrun = false;
	}

	ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			label.setText(watch.toString());
			watch.add(WAIT);
		}
	};

	private class CycleListener implements ActionListener {
		// -----------------------------------------------------------------
		// Updates the position of the image and possibly the direction
		// of movement whenever the timer fires an action event.
		// -----------------------------------------------------------------
		public void actionPerformed(ActionEvent event) {
			watch.add(WAIT);
			label.setText(watch.toString());
		}
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (timer.isRunning()) {
				timer.stop();
				start.setText("Start");
			} else if (hasrun) {
				timer.restart();
				start.setText("Stop");
			}

			else {
				timer.start();
				hasrun = true;
				start.setText("Stop");
			}

		}
	}
}
