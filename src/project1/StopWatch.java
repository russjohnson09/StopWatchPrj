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
	private int minutes;
	private int seconds;
	private int milliseconds;
	private static int numbercreated;

	/* The following ArrayList is neccessary for testing the equals method. */
	private static ArrayList<StopWatch> stopwatches = new ArrayList<StopWatch>();

	public static ArrayList<StopWatch> getStopWatches() {
		return stopwatches;
	}

	public StopWatch() {
		minutes = 0;
		seconds = 0;
		milliseconds = 0;
		numbercreated += 1;
		stopwatches.add(this);
	}

	public StopWatch(int minutes, int seconds, int milliseconds) {
		this.minutes = minutes + (seconds * 1000 + milliseconds) / 60000;
		this.seconds = (seconds + milliseconds / 1000) % 60;
		this.milliseconds = milliseconds % 1000;
		numbercreated += 1;
		stopwatches.add(this);
	}

	public StopWatch(int seconds, int milliseconds) {
		this.minutes = (seconds * 1000 + milliseconds) / 60000;
		this.seconds = (seconds + milliseconds / 1000) % 60;
		this.milliseconds = milliseconds % 1000;
		numbercreated += 1;
		stopwatches.add(this);
	}

	public StopWatch(int milliseconds) {
		this.minutes = milliseconds / 60000;
		this.seconds = (milliseconds / 1000) % 60;
		this.milliseconds = milliseconds % 1000;
		numbercreated += 1;
		stopwatches.add(this);
	}

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
			this.seconds = seconds % 60 + milliseconds / 1000;
			this.milliseconds = milliseconds % 1000;
		} else if (a1.size() == 2) {
			int seconds = a1.get(0);
			int milliseconds = a1.get(1);
			this.minutes = (seconds * 1000 + milliseconds) / 60000;
			this.seconds = seconds % 60 + milliseconds / 1000;
			this.milliseconds = milliseconds % 1000;
		} else if (a1.size() == 1) {
			int milliseconds = a1.get(0);
			this.minutes = milliseconds / 60000;
			this.seconds = milliseconds / 1000;
			this.milliseconds = milliseconds % 1000;
		}
		numbercreated += 1;
		stopwatches.add(this);
	}

	public boolean equals(StopWatch other) {
		return other.milliseconds == milliseconds && other.seconds == seconds
				&& other.minutes == minutes;
	}

	public int totalMilliseconds() {
		return milliseconds + seconds * 1000 + minutes * 60000;
	}

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

		JFrame frame = new JFrame("Stop Watch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TimerListener());
		frame.pack();
		frame.setVisible(true);

	}
}
