package project1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class TestStopWatch {
	/*
	 * 23456789012345678901234567890123456789012345678901234567890123456789012
	 * 
	 * /****** Your assignment is to complete the test cases below *****
	 */

	// The following test cases are for the first part (steps 1 - 4)
	// of the assignment

	@Test
	public void testConstructor() {
		StopWatch s = new StopWatch(5, 10, 300);
		assertEquals(s.toString(), "5:10:300");

		s = new StopWatch("20:10:8");
		assertEquals(s.toString(), "20:10:008");

		s = new StopWatch("20:8");
		assertEquals(s.toString(), "0:20:008");

		s = new StopWatch("8");
		assertEquals(s.toString(), "0:00:008");

		s = new StopWatch("-1");
		assertEquals(s.toString(), "0:00:-001");
	}

	@Test
	public void testAddMethod() {
		StopWatch s1 = new StopWatch(5, 59, 300);
		s1.add(2000);
		assertEquals(s1.toString(), "6:01:300");

		s1 = new StopWatch(5, 59, 300);
		StopWatch s2 = new StopWatch(2, 2, 300);
		s1.add(s2);
		assertEquals(s1.toString(), "8:01:600");

		for (int i = 0; i < 15000; i++) {
			s1.inc();
		}
		assertEquals(s1.toString(), "8:16:600");

		// You are to create 6 more JUnit tests for different
		// add and inc methods.

	}

	@Test
	public void testEqual() {
		StopWatch s1 = new StopWatch(5, 59, 300);
		StopWatch s2 = new StopWatch(6, 01, 200);
		StopWatch s3 = new StopWatch(5, 50, 200);
		StopWatch s4 = new StopWatch(5, 59, 300);

		assertFalse(s1.equals(s2));
		assertTrue(s1.equals(s4));

		assertTrue(s2.compareTo(s1) > 0);
		assertTrue(s3.compareTo(s1) < 0);
		assertTrue(s1.compareTo(s4) == 0);

		// You are to create 3 more JUnit tests for the equals methods.

	}

	@Test
	public void testCompareTo() {
		StopWatch s1 = new StopWatch(5, 59, 300);
		StopWatch s2 = new StopWatch(6, 01, 200);
		StopWatch s3 = new StopWatch(5, 50, 200);
		StopWatch s4 = new StopWatch(5, 59, 300);

		assertFalse(s1.equals(s2));
		assertTrue(s1.equals(s4));

		assertTrue(s2.compareTo(s1) > 0);
		assertTrue(s3.compareTo(s1) < 0);
		assertTrue(s1.compareTo(s4) == 0);

		// You are to create 5 more JUnit tests for the compareTo Methods.

	}

	/*********************** Second part ******************************/

	// These test cases are for the second part (step 5) of the assignment

	@Test
	public void testLoadSave() {
		StopWatch s1 = new StopWatch(5, 59, 300);
		StopWatch s2 = new StopWatch(5, 59, 300);

		try {
			s1.save();
		} catch (IOException e) {

		}
		s1 = new StopWatch(); // resets to zero
		try {
			s1.load();
		} catch (IOException e) {

		}
		assertTrue(s1.equals(s2));

		// You are to create 1 more JUnit test for load and Save
	}

	@Test
	public void testCounter() {
		StopWatch s1 = new StopWatch(5, 59, 300);
		StopWatch s2 = new StopWatch(5, 59, 300);

		// System.out.println(StopWatch.getNumberCreated());
		// Be careful on this one!!!! why 20; why not 2
		assertTrue(StopWatch.getNumberCreated() == 20);

		// You are to create 2 more JUnit test for load and Save methods
	}

	// For Steps 7 and 8, add on any test cases that you believe are needed to
	// complete the project.
}
