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
	private static int numbercreated = 0;

	public StopWatch() {
		minutes = 0;
		seconds = 0;
		milliseconds = 0;
		numbercreated += 1;
	}

	public StopWatch(int minutes, int seconds, int milliseconds) {
		this.minutes = minutes + (seconds * 1000 + milliseconds) / 60000;
		this.seconds = seconds % 60 + milliseconds / 1000;
		this.milliseconds = milliseconds % 1000;
		numbercreated += 1;
	}

	public StopWatch(int seconds, int milliseconds) {
		this.minutes = (seconds * 1000 + milliseconds) / 60000;
		this.seconds = seconds % 60 + milliseconds / 1000;
		this.milliseconds = milliseconds % 1000;
		numbercreated += 1;
	}

	public StopWatch(int milliseconds) {
		this.minutes = milliseconds / 60000;
		this.seconds = milliseconds / 1000;
		this.milliseconds = milliseconds % 1000;
		numbercreated += 1;
	}

	public StopWatch(String startTime) {
		// int delimeter1 = startTime.indexOf(":");
		// int delimeter2 = startTime.substring(delimeter1 + 1).indexOf(":");
		// if (delimeter1 == -1) {
		// int milliseconds = Integer.valueOf(startTime);
		// this.minutes = milliseconds / 60000;
		// this.seconds = milliseconds / 1000;
		// this.milliseconds = milliseconds % 1000;
		//
		// } else if (delimeter2 == -1) {
		// int seconds = Integer.valueOf(startTime.substring(0, delimeter1));
		// int milliseconds = Integer.valueOf(startTime
		// .substring(delimeter1 + 1));
		// this.minutes = milliseconds / 60000 + seconds / 60;
		// this.seconds = seconds % 60 + milliseconds / 1000;
		// this.milliseconds = milliseconds % 1000;
		//
		// } else {
		// int minutes = Integer.valueOf(startTime.substring(0, delimeter1));
		// int seconds = Integer.valueOf(startTime.substring(delimeter1 + 1,
		// delimeter2));
		// int milliseconds = Integer.valueOf(startTime
		// .substring(delimeter2 + 1));
		// this.minutes = minutes + seconds / 60 + milliseconds / 60000;
		// this.seconds = seconds % 60 + milliseconds / 1000;
		// this.milliseconds = milliseconds % 1000;
		//
		// }
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

	// try {
	//
	// BufferedWriter out = new BufferedWriter(new FileWriter(
	// "outfilename"));
	// out.write("aString");
	// out.close();
	//
	// // JFileChooser chooser = new JFileChooser();
	// // int status = chooser.showOpenDialog(null);
	// // File file = chooser.getSelectedFile();
	// // Scanner scan = new Scanner(file);
	// // String info = "";
	// //
	// // PrintWriter out = null;
	// //
	// // out = new PrintWriter(new BufferedWriter(new FileWriter(
	// // "population.txt")));
	// // out.println("ANCHORAGE" + " " + "25600");
	// // out.close();
	// } catch (IOException error) {
	// System.out.println("Oops!");
	// }
	//
	// }

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

		StopWatch s3 = new StopWatch(0, 0, 0);
		for (int i = 0; i < 1005; i++) {
			s3.add(1);
		}
		s3.add(2004);
		System.out.println(s3);

		StopWatch s1 = new StopWatch(5, 59, 300);
		s1.add(2000);
		System.out.println(s1);

		s1 = new StopWatch("8:01:600");

		for (int i = 0; i < 15000; i++) {
			s1.inc();
		}

		System.out.println(s1);

		JFrame frame = new JFrame("Stop Watch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TimerListener());
		frame.pack();
		frame.setVisible(true);
		//
		// TimerListener tl1 = new TimerListener();
		//
		// Timer timer = new Timer();
		//
		// // TO DO:
		// // create a TimeListener object
		// // create a Timer object appropriately
		// // start the Timer object
		//
		// // use the following to stop the timer
		// Scanner scan = new Scanner(System.in);
		// System.out.println("Hit any Key to stop the timer:");
		// scan.nextLine();
		// timer.stop();

		// int delay = 1000; //milliseconds
		// ActionListener taskPerformer = new ActionListener() {
		// public void actionPerformed(ActionEvent evt) {
		// System.out.println('H"');
		// }
		// };
		// new Timer(delay, taskPerformer).start();

	}
}
