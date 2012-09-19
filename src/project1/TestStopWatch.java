/*****************************************************************
 * JUnit for the StopWatch class.
 * 
 * @author Russ Johnson
 * @version 9.10.2012
 *****************************************************************/
package project1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

public class TestStopWatch {

	/*************************************************************************
	 * Tests the various constructors of StopWatch.
	 *************************************************************************/
	@Test
	public void testConstructor() {

		StopWatch s;

		/* should work with empty string */
		s = new StopWatch("");
		assertEquals(s.toString(), "0:00:000");

		/* should work when seconds are over 60 */
		s = new StopWatch(500, 67, 10);
		assertEquals(s.toString(), "501:07:010");

		/* should work when seconds are over 60 */
		s = new StopWatch(666, 99, 300);
		assertEquals(s.toString(), "667:39:300");

		/* another test case */
		s = new StopWatch(5, 10, 300);
		assertEquals(s.toString(), "5:10:300");

		/* should work with a string that has minutes, seconds, and milliseconds */
		s = new StopWatch("20:10:8");
		assertEquals(s.toString(), "20:10:008");

		/* should work with a string that has seconds and milliseconds */
		s = new StopWatch("20:8");
		assertEquals(s.toString(), "0:20:008");

		/* should work with a string that has just milliseconds */
		s = new StopWatch("8");
		assertEquals(s.toString(), "0:00:008");
	}

	/*************************************************************************
	 * Tests the add and inc methods.
	 *************************************************************************/
	@Test
	public void testAddMethod() {
		StopWatch s1;
		StopWatch s2;

		/* add should work with any positive integer */
		s1 = new StopWatch(5, 59, 300);
		s1.add(2000);
		assertEquals(s1.toString(), "6:01:300");

		s1 = new StopWatch(5, 59, 300);
		s2 = new StopWatch(2, 2, 300);
		/* test the addition of one stopwatch to another */
		s1.add(s2);
		assertEquals(s1.toString(), "8:01:600");

		/* should work no matter how many times we increment */
		for (int i = 0; i < 15000; i++) {
			s1.inc();
		}
		assertEquals(s1.toString(), "8:16:600");

		s1 = new StopWatch(6, 400, 1000000);
		assertEquals(s1.toString(), "29:20:000");

		/* should work no matter how many times we use it on one stopwatch */
		for (int i = 0; i < 15000; i++) {
			s1.add(1000);
		}

		assertEquals(s1.toString(), "279:20:000");

		/* test the addition of one stopwatch to another */
		s1.add(s1);
		assertEquals(s1.toString(), "558:40:000");

		for (int i = 0; i < 15000; i++) {
			s1.inc();
			s1.add(1);
		}
		assertEquals(s1.toString(), "559:10:000");

		for (int i = 0; i < 15000; i++) {
			s1.add(2);
			s1.add(-2);
		}
		assertEquals(s1.toString(), "559:10:000");

		/* if we add and immediately subtract the number should be the same */
		for (int i = 0; i < 15000; i++) {
			s1.add(1);
			s1.add(-1);
			s1.add(100);
			s1.add(-100);
		}

		assertEquals(s1.toString(), "559:10:000");

		/* test the addition of one stopwatch to another */
		s1.add(s2);

		assertEquals(s1.toString(), "561:12:300");

	}

	/*************************************************************************
	 * Tests the equal method.
	 *************************************************************************/
	@Test
	public void testEqual() {
		StopWatch s1 = new StopWatch(5, 59, 300);
		StopWatch s2 = new StopWatch(6, 01, 200);
		StopWatch s3 = new StopWatch(5, 50, 200);
		StopWatch s4 = new StopWatch(5, 59, 300);

		ArrayList<StopWatch> sarray = StopWatch.getStopWatches();
		Iterator<StopWatch> itr1 = sarray.iterator();
		/*
		 * Ensures that the equals method has the symmetric property. It uses
		 * two iterators and a nested while loop to ensure that every
		 * combination is tested.
		 */
		while (itr1.hasNext()) {
			StopWatch x = itr1.next();
			Iterator<StopWatch> itr2 = sarray.iterator();
			while (itr2.hasNext()) {
				StopWatch y = itr2.next();
				assertTrue(x.equals(y) == y.equals(x));

			}
		}

		/* equals should be false if not equal */
		assertFalse(s1.equals(s2));

		/* equals should be true if equal */
		assertTrue(s1.equals(s4));

		/* various comparisons */
		assertTrue(s2.compareTo(s1) > 0);
		assertTrue(s3.compareTo(s1) < 0);
		assertTrue(s1.compareTo(s4) == 0);

	}

	/*************************************************************************
	 * Tests the comparison method.
	 *************************************************************************/
	@Test
	public void testCompareTo() {
		StopWatch s1 = new StopWatch(5, 59, 300);
		StopWatch s2 = new StopWatch(6, 01, 200);
		StopWatch s3 = new StopWatch(5, 50, 200);
		StopWatch s4 = new StopWatch(5, 59, 300);
		StopWatch s5 = new StopWatch(665, 1, 300);
		StopWatch s6 = new StopWatch(100, 10000, 300);
		StopWatch s7 = new StopWatch(1, 23, 300);
		StopWatch s8 = new StopWatch(5, 100, 300);
		StopWatch s9 = new StopWatch(5, 100, 300);

		assertFalse(s1.equals(s2));
		assertTrue(s1.equals(s4));

		assertTrue(s8.equals(s9));

		/*
		 * The StopWatches s8 and s9 are equal and therefore the comparison
		 * should return the same result.
		 */
		assertTrue(s8.compareTo(s9) == s9.compareTo(s8));

		assertTrue(s2.compareTo(s1) > 0);
		assertTrue(s3.compareTo(s1) < 0);
		assertTrue(s2.compareTo(s1) == 1);
		assertTrue(s3.compareTo(s1) == -1);
		assertTrue(s1.compareTo(s4) == 0);

		assertTrue(s1.compareTo(s5) < 0);
		assertTrue(s5.compareTo(s6) > 0);
		assertTrue(s6.compareTo(s7) > 0);

		ArrayList<StopWatch> sarray = StopWatch.getStopWatches();
		Iterator<StopWatch> itr1 = sarray.iterator();

		/* A self-comparison should always return a zero. */
		while (itr1.hasNext()) {
			StopWatch x = itr1.next();
			assertTrue(x.compareTo(x) == 0);
		}

	}

	/***********************
	 * Test for the load and save methods of StopWatch.
	 * 
	 * @throws IOException
	 ******************************/
	@Test
	public void testLoadSave() throws IOException {
		StopWatch s1 = new StopWatch(5, 59, 300);
		StopWatch s2 = new StopWatch(5, 59, 300);

		s1.save();
		s1 = new StopWatch();
		s1.load();

		assertTrue(s1.equals(s2));

		StopWatch s3 = new StopWatch();
		s3.load();

		assertEquals(s3.toString(), "5:59:300");
	}

	@Test
	public void testCounter() {
		StopWatch s1 = new StopWatch(5, 59, 300);
		StopWatch s2 = new StopWatch(5, 59, 300);

		assertEquals(StopWatch.getNumberCreated(), 30);

	}

}
