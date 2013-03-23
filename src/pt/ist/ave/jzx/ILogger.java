package pt.ist.ave.jzx;

/**
 * Interface implemented by all classes that are able to log messages generated
 * by the emulator.
 * 
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan
 *         Surdulescu</A> (c) 2001 - 2006 <BR>
 *         You may use and distribute this software for free provided you
 *         include this copyright notice. You may not sell this software, use my
 *         name for publicity reasons or modify the code without permission from
 *         me.
 */
public interface ILogger {
	/**
	 * ERROR channel: all error messages should go here.
	 * <P>
	 * An example of an error message is where the snapshot file is corrupt, the
	 * ROM files cannot be found etc.
	 */
	public static final int C_ERROR = 0;

	/**
	 * DEBUG channel: all debug messages should go here.
	 * <P>
	 * An example of a debug message is where functionality that is not
	 * implemented is called.
	 */
	public static final int C_DEBUG = 1;

	/**
	 * Log a message to a given channel.
	 */
	public void log(int channel, String message);

	/**
	 * Log an exception to a given channel.
	 */
	public void log(int channel, Throwable t);
}
