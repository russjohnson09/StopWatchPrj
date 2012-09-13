/*******************************************************************************
 * Representation of stopwatch with milliseconds, seconds, and minutes.
 * 
 * 
 * @author Russ Johnson
 * @version 9.10.2012
 *******************************************************************************/
package project1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class StopWatch {

	/* represents minutes */
	private int minutes;

	/* represents seconds */
	private int seconds;

	/* represents milliseconds */
	private int milliseconds;

	/* number of StopWatch objects created */
	private static int numbercreated;

	/* ArrayList necessary for testing the equals method */
	private static ArrayList<StopWatch> stopwatches = new ArrayList<StopWatch>();

	/*************************************************************************
	 * Default constructor creates a stopwatch with no time.
	 *************************************************************************/
	public StopWatch() {
		minutes = 0;
		seconds = 0;
		milliseconds = 0;
		numbercreated += 1;
		stopwatches.add(this);
	}

	/*************************************************************************
	 * Constructor creates a StopWatch object with minutes, seconds, and
	 * milliseconds.
	 * 
	 * @param minutes
	 *            minutes in stopwatch
	 * @param seconds
	 *            seconds in stopwatch
	 * @param milliseconds
	 *            milliseconds in stopwatch
	 *************************************************************************/
	public StopWatch(int minutes, int seconds, int milliseconds) {
		this.minutes = minutes + (seconds * 1000 + milliseconds) / 60000;
		this.seconds = (seconds + milliseconds / 1000) % 60;
		this.milliseconds = milliseconds % 1000;
		numbercreated += 1;
		stopwatches.add(this);
	}

	/*************************************************************************
	 * Constructor creates a StopWatch object with seconds and milliseconds.
	 * 
	 * @param seconds
	 *            seconds in stopwatch
	 * @param milliseconds
	 *            milliseconds in stopwatch
	 * 
	 *************************************************************************/
	public StopWatch(int seconds, int milliseconds) {
		this.minutes = (seconds * 1000 + milliseconds) / 60000;
		this.seconds = (seconds + milliseconds / 1000) % 60;
		this.milliseconds = milliseconds % 1000;
		numbercreated += 1;
		stopwatches.add(this);
	}

	/*************************************************************************
	 * Constructor creates a StopWatch object with milliseconds
	 * 
	 * @param milliseconds
	 *            milliseconds in stopwatch
	 *************************************************************************/
	public StopWatch(int milliseconds) {
		this.minutes = milliseconds / 60000;
		this.seconds = (milliseconds / 1000) % 60;
		this.milliseconds = milliseconds % 1000;
		numbercreated += 1;
		stopwatches.add(this);
	}

	/*************************************************************************
	 * Constructor creates a StopWatch object with minutes, seconds and
	 * milliseconds based on a string.
	 * 
	 * @param startTime
	 *            string that contains time delimited by :
	 *************************************************************************/
	public StopWatch(String startTime) {
		Scanner sc = new Scanner(startTime).useDelimiter(":");
		ArrayList<Integer> a1 = new ArrayList<Integer>(3);
		while (sc.hasNextInt()) {
			a1.add(sc.nextInt());
		}
		if (a1.size() == 3) {
			int minutes = a1.get(0);
			int seconds = a1.get(1);
			int milliseconds = a1.get(2);
			this.minutes = minutes + (seconds * 1000 + milliseconds) / 60000;
			this.seconds = (seconds + milliseconds / 1000) % 60;
			this.milliseconds = milliseconds % 1000;
		} else if (a1.size() == 2) {
			int seconds = a1.get(0);
			int milliseconds = a1.get(1);
			this.minutes = (seconds * 1000 + milliseconds) / 60000;
			this.seconds = (seconds + milliseconds / 1000) % 60;
			this.milliseconds = milliseconds % 1000;
		} else if (a1.size() == 1) {
			int milliseconds = a1.get(0);
			this.minutes = milliseconds / 60000;
			this.seconds = (milliseconds / 1000) % 60;
			this.milliseconds = milliseconds % 1000;
		}
		numbercreated += 1;
		stopwatches.add(this);
	}

	/*************************************************************************
	 * Tests two StopWatch objects for equality defined as having the same
	 * milliseconds, seconds, and minutes.
	 * 
	 * @param other
	 *            the stopwatch that this one is being compared to
	 *************************************************************************/
	public boolean equals(StopWatch other) {
		return other.milliseconds == milliseconds && other.seconds == seconds
				&& other.minutes == minutes;
	}

	/*************************************************************************
	 * Returns the total milliseconds.
	 *************************************************************************/
	public int totalMilliseconds() {
		return milliseconds + seconds * 1000 + minutes * 60000;
	}

	/*************************************************************************
	 * Compares two stopwatches. If this one is larger than the other return 1.
	 * If this one is smaller return -1. Else (if equal) return 0.
	 * 
	 * @param other
	 *            the stopwatch that this one is being compared to
	 *************************************************************************/
	public int compareTo(StopWatch other) {

		if (other.totalMilliseconds() < totalMilliseconds())
			return 1;
		if (other.totalMilliseconds() > totalMilliseconds())
			return -1;

		return 0;
	}

	/*************************************************************************
	 * Adds some number of milliseconds to the stopwatch.
	 * 
	 * @param milliseconds
	 *            milliseconds being added
	 * @return none
	 *************************************************************************/
	public void add(int milliseconds) {

		setMilliseconds(this.milliseconds + milliseconds);
	}

	/*************************************************************************
	 * Adds this stopwatch to some other stopwatch.
	 * 
	 * @param other
	 *            stopwatch added to this stopwatch
	 * @return none
	 *************************************************************************/
	public void add(StopWatch other) {

		add(other.totalMilliseconds());
	}

	/*************************************************************************
	 * Increments the stopwatch by 1 millisecond (calls add with milliseconds
	 * equal to one).
	 * 
	 * @return none
	 *************************************************************************/
	public void inc() {

		add(1);
	}

	/*************************************************************************
	 * Converts the stopwatch to a string.
	 * 
	 * @return String
	 *************************************************************************/
	public String toString() {

		return minutes + ":" + String.format("%02d", seconds) + ":"
				+ String.format("%03d", milliseconds);
	}

	/*************************************************************************
	 * Saves the stopwatch to a user selected file.
	 * 
	 * @return none
	 * @throws IOException
	 *************************************************************************/
	public void save() throws IOException {

		PrintWriter out = null;
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showOpenDialog(null);

		if (status != JFileChooser.APPROVE_OPTION)
			System.out.println("No File Chosen");
		else {
			File file = chooser.getSelectedFile();

			out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			out.println(minutes + ":" + seconds + ":" + milliseconds);
			out.close();

		}
	}

	/*************************************************************************
	 * Loads a stopwatch to a user selected file.
	 * 
	 * @return none
	 * @throws IOException
	 *************************************************************************/
	public void load() throws IOException {

		JFileChooser chooser = new JFileChooser();

		int status = chooser.showOpenDialog(null);

		if (status != JFileChooser.APPROVE_OPTION)
			System.out.println("No File Chosen");
		else {
			File file = chooser.getSelectedFile();
			Scanner scan = new Scanner(file);
			String str1 = scan.nextLine();
			scan = new Scanner(str1).useDelimiter(":");
			setMinutes(scan.nextInt());
			setSeconds(scan.nextInt());
			setMilliseconds(scan.nextInt());
		}
	}

	// The Following are getter or setter methods.

	public static ArrayList<StopWatch> getStopWatches() {
		return stopwatches;
	}

	public static int getNumberCreated() {
		return numbercreated;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		if (seconds > 59) {
			this.seconds = seconds % 60;
			this.minutes = this.minutes + (seconds / 60);

		} else {
			this.seconds = seconds;
		}

	}

	public int getMilliseconds() {
		return milliseconds;
	}

	public void setMilliseconds(int milliseconds) {
		if (milliseconds > 999) {
			this.milliseconds = milliseconds % 1000;
			setSeconds(this.seconds + (milliseconds / 1000));
		} else {
			this.milliseconds = milliseconds;
		}
	}

	public static void main(String[] args) {

		StopWatch s = new StopWatch("20:10:8");
		System.out.println("Time: " + s);

		s = new StopWatch("20:8");
		System.out.println("Time: " + s);

		s = new StopWatch("8");
		System.out.println("Time: " + s);

		StopWatch s1 = new StopWatch(20, 2, 200);
		System.out.println("Time: " + s1);

		s1.add(1000);
		System.out.println("Time: " + s1);

		StopWatch s2 = new StopWatch(40, 10, 20);
		s2.add(100);
		for (int i = 0; i < 4000; i++) {
			s2.inc();
		}
		System.out.println("Time: " + s2);

		s = new StopWatch("");
		System.out.println(s + " = 0:00:000");

		s = new StopWatch(500, 67, 10);
		System.out.println(s + " = 501:07:010");

		s = new StopWatch(666, 99, 300);
		System.out.println(s + " = 667:39:300");

		s = new StopWatch(5, 10, 300);
		System.out.println(s + " = 5:10:300");

		s = new StopWatch("20:10:8");
		System.out.println(s + " = 20:10:008");

		s = new StopWatch("20:8");
		System.out.println(s + " = 0:20:008");

		s = new StopWatch("8");
		System.out.println(s + " = 0:00:008");

		s1 = new StopWatch(5, 59, 300);
		s1.add(2000);
		System.out.println(s1 + " = 6:01:300");

		s1 = new StopWatch(5, 59, 300);
		s2 = new StopWatch(2, 2, 300);
		s1.add(s2);
		System.out.println(s1 + " = 8:01:600");

		for (int i = 0; i < 15000; i++) {
			s1.inc();
		}
		System.out.println(s1 + " = 8:16:600");

		s1 = new StopWatch(6, 400, 1000000);
		System.out.println(s1 + " = 29:20:000");

		for (int i = 0; i < 15000; i++) {
			s1.add(1000);
		}

		System.out.println(s1 + " = 279:20:000");

		s1.add(s1);
		System.out.println(s1 + " = 558:40:000");

		for (int i = 0; i < 15000; i++) {
			s1.inc();
			s1.add(1);
		}
		System.out.println(s1 + " = 559:10:000");

		for (int i = 0; i < 15000; i++) {
			s1.add(2);
			s1.add(-2);
		}
		System.out.println(s1 + " = 559:10:000");

		for (int i = 0; i < 15000; i++) {
			s1.add(1);
			s1.add(-1);
			s1.add(100);
			s1.add(-100);
		}

		System.out.println(s1 + " = 559:10:000");

		s1.add(s2);

		System.out.println(s1 + " = 561:12:300");

		JFrame frame = new JFrame("Stop Watch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TimerListener());
		frame.pack();
		frame.setVisible(true);

	}
}
