package pt.ist.ave.jzx;

import java.applet.Applet;
import java.applet.AppletContext;

/**
 * Log messages using the AppletContext.showStatus() method (when JZX is running
 * as an Applet.)
 * 
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan
 *         Surdulescu</A> (c) 2001 - 2006 <BR>
 *         You may use and distribute this software for free provided you
 *         include this copyright notice. You may not sell this software, use my
 *         name for publicity reasons or modify the code without permission from
 *         me.
 * 
 * @see ILogger
 */
public class AppletLogger implements ILogger {
	private AppletContext m_appletContext;

	/**
	 * Construct the AppletLogger using the AppletContext object from the
	 * specified Applet object.
	 */
	public AppletLogger(Applet applet) {
		m_appletContext = applet.getAppletContext();
	}

	/**
	 * Display the message in the status bar.
	 */
	public void log(int channel, String message) {
		m_appletContext.showStatus("Channel: " + channel + ", " + message);
	}

	/**
	 * Display the exception message in the status bar.
	 */
	public void log(int channel, Throwable t) {
		m_appletContext.showStatus("Channel: " + channel + ", "
				+ t.getMessage());
	}
}
