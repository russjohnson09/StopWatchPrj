package project1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class TimerSample {
	public static void main(String args[]) {
		new JFrame().setVisible(true);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("Hello World Timer");
			}
		};
		Timer timer = new Timer(500, actionListener);
		timer.start();
	}
}
