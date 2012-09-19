/*****************************************************************
 * Class that manages both the GUI and the TimerListener.
 * 
 * @author Russ Johnson
 * @version 9.10.2012
 *****************************************************************/
package project1;

/*************************************************************************
 * GUI for the stopwatch. Is able to start stop and reset
 *************************************************************************/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TimerListener extends JPanel {

	/* the wait between changes in displayed time */
	private final int WAIT;

	/*
	 * both the start and stop button (felt it would be repetitive to have a
	 * different buttons when only one can be used at a time)
	 */
	private JButton start;

	/* resets the stopwatch */
	private JButton reset;

	/* label displays time */
	private JLabel label;

	/* panel for buttons */
	private JPanel buttonPanel;

	/* timer for timed action */
	private Timer timer;

	/* watch needed for GUI to run */
	private StopWatch watch;

	/* boolean for whether timer is running */
	private boolean hasrun;

	/*************************************************************************
	 * Default constructor for GUI. Default wait time is 500 milliseconds
	 *************************************************************************/
	public TimerListener() {

		this.WAIT = 500;
		start = new JButton("Start");
		reset = new JButton("Reset");
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

	/*************************************************************************
	 * Constructor GUI that also for the wait time to be set.
	 *************************************************************************/
	public TimerListener(int wait) {

		this.WAIT = wait;
		start = new JButton("Start");
		reset = new JButton("Reset");
		ButtonListener listener = new ButtonListener();
		start.addActionListener(listener);
		reset.addActionListener(listener);
		label = new JLabel("0:00:000");

		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(200, 40));
		buttonPanel.add(start);
		buttonPanel.add(reset);

		setPreferredSize(new Dimension(200, 80));

		add(label);
		add(buttonPanel);

		timer = new Timer(WAIT, new CycleListener());
		watch = new StopWatch();
		hasrun = false;
	}

	/*************************************************************************
	 * Sets the label to the current time.
	 *************************************************************************/
	private class CycleListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			watch.add(WAIT);
			label.setText(watch.toString());
		}
	}

	/*************************************************************************
	 * Button listener for the start/stop and reset buttons. If reset is pushed
	 * while stopwatch is running, the time is set to zero and the stopwatch
	 * continues to run. If reset is pressed when stopwatch is not running, the
	 * time is set to zero.
	 *************************************************************************/
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == start) {
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
			} else if (event.getSource() == reset) {
				watch.setMilliseconds(0);
				watch.setSeconds(0);
				watch.setMinutes(0);
				label.setText(watch.toString());
			}

		}
	}
}
