package pt.ist.ave.jzx;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.io.IOException;

/**
 * The entry point for JZX when running as an applet.
 * <P>
 * The applet parameters that can be specified for this applet are "SCALE",
 * "MODE" and "SNAPSHOT", to provide the screen scale, hardware mode and,
 * respectively, the snapshot file to load into the emulator.
 * <P>
 * The applet width and height must be a multiple of (316, 252)
 * 
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan
 *         Surdulescu</A> (c) 2001 - 2006 <BR>
 *         You may use and distribute this software for free provided you
 *         include this copyright notice. You may not sell this software, use my
 *         name for publicity reasons or modify the code without permission from
 *         me.
 */
public class JZXApplet extends Applet implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SCALE_PARAMETER = "SCALE";
	private static final String MODE_PARAMETER = "MODE";
	private static final String SNAPSHOT_PARAMETER = "SNAPSHOT";

	private static final String MODE_48 = "48";
	private static final String MODE_128 = "128";

	/**
	 * The Spectrum emulator running inside this applet.
	 */
	private BaseSpectrum m_spectrum;

	/**
	 * Return documentation for the parameters supported by this applet.
	 */
	public String[][] getParameterInfo() {
		return new String[][] {
				{ SCALE_PARAMETER, "1-3", "Scale the screen size by 'x'" },
				{ MODE_PARAMETER, "48 or 128", "Hardware mode: 48k or 128k" },
				{ SNAPSHOT_PARAMETER, "URI",
						"Codebase-relative URI to Z80 snapshot" } };
	}

	/**
	 * Parse the parameters, load the snapshot, create the spectrum emulator
	 * instance, pause it and start a runner thread to run the main emulation
	 * loop.
	 */
	public void init() {
		setLayout(new BorderLayout());

		AppletLogger logger = new AppletLogger(this);
		Z80Loader loader = null;

		int scale = 1;

		try {
			String scaleString = getParameter(SCALE_PARAMETER);
			if (scaleString != null) {
				scale = Integer.parseInt(scaleString);
			}

			if (scale < 1 || scale > 3) {
				scale = 1;
			}
		} catch (NumberFormatException nfe) {
			logger.log(ILogger.C_DEBUG, nfe);
		}

		String snapshot = getParameter(SNAPSHOT_PARAMETER);
		if (snapshot != null) {
			try {
				// TODO: use a factory
				loader = new Z80Loader(logger, getCodeBase());
				loader.load(snapshot);

				if (loader.getMode() == BaseLoader.MODE_48) {
					m_spectrum = new pt.ist.ave.jzx.v48.Spectrum();
				} else if (loader.getMode() == BaseLoader.MODE_128) {
					m_spectrum = new pt.ist.ave.jzx.v128.Spectrum();
				} else {
					logger.log(ILogger.C_ERROR, "Unknown hardware mode: "
							+ loader.getMode());
				}
			} catch (IOException ioe) {
				logger.log(ILogger.C_ERROR, ioe);
			}
		}

		if (m_spectrum == null) {
			String mode = getParameter(MODE_PARAMETER);
			if (mode == null) {
				mode = MODE_48;
			}

			if (mode.equals(MODE_128)) {
				m_spectrum = new pt.ist.ave.jzx.v128.Spectrum();
			} else {
				m_spectrum = new pt.ist.ave.jzx.v48.Spectrum();
			}
		}

		m_spectrum.setScale(scale);
		m_spectrum.init(m_spectrum, logger);

		add(m_spectrum.getContainer());

		m_spectrum.reset();

		if (loader != null) {
			m_spectrum.load(loader);
		}

		m_spectrum.pause();

		new Thread(this).start();
	}

	/**
	 * This is called from the thread started in <TT>init()</TT> and simply
	 * calls the <TT>emulate()</TT> method of the emulator.
	 * 
	 * @see #init
	 * @see #start
	 */
	public void run() {
		m_spectrum.emulate();
	}

	/**
	 * Unpause the emulator which is current paused in the runner thread.
	 * 
	 * @see #init
	 * @see #run
	 */
	public void start() {
		m_spectrum.unpause();
	}

	/**
	 * Pause the emulator.
	 * 
	 * @see #init
	 * @see #start
	 */
	public void stop() {
		m_spectrum.pause();
	}

	/**
	 * Stop the emulator.
	 * <P>
	 * This will cause the <TT>run()</TT> method above to return, which will end
	 * the runner thread. To restart the emulator, the <TT>init()</TT> method
	 * must be called again.
	 * 
	 * @see #init
	 * @see #run
	 */
	public void destroy() {
		m_spectrum.stop();
	}
}