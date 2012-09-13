/*****************************************************************
Class that manages the StopWatch object.

@author Russ Johnson
@version 9.10.2012
 *****************************************************************/
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

	/* The following ArrayList is necessary for testing the equals method. */
	private static ArrayList<StopWatch> stopwatches = new ArrayList<StopWatch>();

	public static ArrayList<StopWatch> getStopWatches() {
		return stopwatches;
	}

	/*
	 * Constructor class taking no arguments. Sets everything to zero, increases
	 * number created and adds this StopWatch to stopwatches ArrayList.
	 */
	public StopWatch() {
		minutes = 0;
		seconds = 0;
		milliseconds = 0;
		numbercreated += 1;
		stopwatches.add(this);
	}

	/*
	 * Constructor class that sets values for minutes, seconds, and
	 * milliseconds. Also deals with overflow for milliseconds and seconds.
	 */
	public StopWatch(int minutes, int seconds, int milliseconds) {
		this.minutes = minutes + (seconds * 1000 + milliseconds) / 60000;
		this.seconds = (seconds + milliseconds / 1000) % 60;
		this.milliseconds = milliseconds % 1000;
		numbercreated += 1;
		stopwatches.add(this);
	}

	/*
	 * Constructor class that sets values for seconds and milliseconds. Also
	 * deals with overflow for milliseconds and seconds.
	 */
	public StopWatch(int seconds, int milliseconds) {
		this.minutes = (seconds * 1000 + milliseconds) / 60000;
		this.seconds = (seconds + milliseconds / 1000) % 60;
		this.milliseconds = milliseconds % 1000;
		numbercreated += 1;
		stopwatches.add(this);
	}

	/*
	 * Constructor class that sets milliseconds. Also deals with overflow for
	 * milliseconds and seconds.
	 */
	public StopWatch(int milliseconds) {
		this.minutes = milliseconds / 60000;
		this.seconds = (milliseconds / 1000) % 60;
		this.milliseconds = milliseconds % 1000;
		numbercreated += 1;
		stopwatches.add(this);
	}

	/*
	 * Constructor that uses a string formated as 0:00:00, 00:00, or 00 and sets
	 * the minutes,
	 */
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

	/* Tests two StopWatch objects for equality. */
	public boolean equals(StopWatch other) {
		return other.milliseconds == milliseconds && other.seconds == seconds
				&& other.minutes == minutes;
	}

	/* Returns the total milliseconds. */
	public int totalMilliseconds() {
		return milliseconds + seconds * 1000 + minutes * 60000;
	}

	/* Returns the total milliseconds. */
	public int compareTo(StopWatch other) {
		if (other.totalMilliseconds() < totalMilliseconds())
			return 1;
		if (other.totalMilliseconds() > totalMilliseconds())
			return -1;

		return 0;
	}

	public void add(int milliseconds) {
		setMilliseconds(this.milliseconds + milliseconds);
	}

	public void add(StopWatch other) {
		add(other.totalMilliseconds());
	}

	public void inc() {
		add(1);
	}

	public String toString() {
		return minutes + ":" + String.format("%02d", seconds) + ":"
				+ String.format("%03d", milliseconds);
	}

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
