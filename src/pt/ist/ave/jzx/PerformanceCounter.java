package pt.ist.ave.jzx;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Track various time periods in the emulator and report them.
 * 
 * <P>
 * This class is used to determine possible performance bottlenecks.
 * 
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan
 *         Surdulescu</A> (c) 2001 - 2006 <BR>
 *         You may use and distribute this software for free provided you
 *         include this copyright notice. You may not sell this software, use my
 *         name for publicity reasons or modify the code without permission from
 *         me.
 */
public class PerformanceCounter {
	
	/**
	 * Store the total amount of time performed by a particular action and the
	 * number of times that action was performed.
	 */
	private static class Pair {
		double time;
		int count;
	}

	/** Enable or disable the performance counter functionality. */
	private static boolean s_enable = true;

	/**
	 * Skip this many events of any given time before recording times for that
	 * event type.
	 */
	private static int s_warmup = 50;

	/** The list of start times for an event of a given type. */
	private static Hashtable<String, Double> s_startTimes = new Hashtable<String, Double>();

	/** The list of {@link Pair} objects for an event of a given type. */
	private static Hashtable<String, Pair> s_timePairs = new Hashtable<String, Pair>();

	/** The list of {@link Pair} objects for an event of a given type. */
	private static Hashtable<String, Integer> s_cacheHits = new Hashtable<String, Integer>();
	
	/** The list of {@link Pair} objects for an event of a given type. */
	private static Hashtable<String, Integer> s_cacheMisses = new Hashtable<String, Integer>();
	
	/** Enable or disable the counter functionality. */
	public static void setEnable(boolean flag) {
		s_enable = flag;
	}

	/** Set the warmup count. */
	public static void setWarmup(int value) {
		if (value < 0) {
			throw new IllegalArgumentException("Invalid warmup count: " + value);
		}

		s_warmup = value;
	}

	/**
	 * Start tracking a new event of specified type.
	 * 
	 * <P>
	 * You may not start the same event type twice in a row; you must
	 * {@link #end} it first!
	 * 
	 * @param name
	 *            The type of performance event to start
	 */
	public static synchronized void start(String name) {
		if (!s_enable) {
			return;
		}

		if (s_startTimes.containsKey(name)) {
			throw new IllegalArgumentException("Timer '" + name
					+ "' already started");
		} else {
			long start = NativeTimer.currentTimeNanos();

			s_startTimes.put(name, new Double(start / 1000.0));
		}
	}

	/**
	 * End tracking an event of specified time and record the amount of time it
	 * took to complete.
	 * 
	 * <P>
	 * You may not end the same event type twice in a row; you must
	 * {@link #start} it first!
	 * 
	 * @param name
	 *            The type of performance event to end
	 */
	public static synchronized void end(String name) {
		if (!s_enable) {
			return;
		}

		long end = NativeTimer.currentTimeNanos();

		if (!s_startTimes.containsKey(name)) {
			throw new IllegalArgumentException("Timer '" + name
					+ "' not already started");
		} else {
			double start = ((Double) s_startTimes.remove(name)).doubleValue();

			Pair pair;
			if (s_timePairs.containsKey(name)) {
				pair = (Pair) s_timePairs.get(name);
			} else {
				pair = new Pair();
				s_timePairs.put(name, pair);
			}

			pair.count++;
			if (pair.count > s_warmup) {
				pair.time += (end / 1000.0 - start);
			}
		}
	}
	
	public static synchronized void cacheHit(String cacheName){
		int numHits = 0;
		if(s_cacheHits.contains(cacheName)){
			numHits = s_cacheHits.get(cacheName);
			s_cacheHits.remove(cacheName);
		}
		numHits += 1;
		s_cacheHits.put(cacheName, numHits);
	}
	
	public static synchronized void cacheMiss(String cacheName){
		int numHits = 0;
		if(s_cacheMisses.contains(cacheName)){
			numHits = s_cacheMisses.get(cacheName);
			s_cacheMisses.remove(cacheName);
		}
		numHits += 1;
		s_cacheMisses.put(cacheName, numHits);
	}

	/**
	 * Report all the performance events gathered thus far.
	 */
	public static synchronized void report() {
		if (!s_enable) {
			return;
		}
		System.out.println("###### cache report: ###############");
		for(String cacheName : s_cacheHits.keySet()){
			int numHits =  s_cacheHits.contains(cacheName) ? s_cacheHits.get(cacheName) : 0;
			int numMisses = s_cacheMisses.contains(cacheName) ? s_cacheMisses.get(cacheName) : 0;
			int hitRate = (numHits>0 && numMisses > 0) ? (numHits*100/(numHits+numMisses)) : 0;
			String logggg = ("Cache " + cacheName + " hits: " + numHits + "misses: " + numMisses + "hit rate: " + hitRate + "%");
			System.out.println(logggg);
		}
		System.out.println("###### timers report: ###############");
		for (Enumeration<String> keys = s_timePairs.keys(); keys.hasMoreElements();) {
			String name = (String) keys.nextElement();
			Pair pair = (Pair) s_timePairs.get(name);

			int count = pair.count - s_warmup;
			System.out.println(name + "=" + (pair.time / count) + " (" + count + ")");
		}
		

	}
}
