package pt.ist.ave.jzx;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The entry point for JZX when running as a standalone application.
 * <P>
 * The command line parameters that can be specified for this application are
 * "-scale", "-mode" and "-snapshot", to provide the screen scale, hardware mode
 * and, respectively, the snapshot file to load into the emulator.
 * 
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan
 *         Surdulescu</A> (c) 2001 - 2006 <BR>
 *         You may use and distribute this software for free provided you
 *         include this copyright notice. You may not sell this software, use my
 *         name for publicity reasons or modify the code without permission from
 *         me.
 */
public class JZXFrame {
	private static final String SCALE_PARAMETER = "-scale";
	private static final String MODE_PARAMETER = "-mode";
	private static final String SNAPSHOT_PARAMETER = "-snapshot";

	private static final String MODE_48 = "48";
	private static final String MODE_128 = "128";

	/**
	 * Search the parameter list for a specified parameter and return the next
	 * entry in the parameter list.
	 * <P>
	 * The method returns null if the parameter is not found or if the parameter
	 * list is invalid.
	 */
	private static String getParameter(String[] args, String parameter) {
		for (int i = 0; i < args.length - 1; i++) {
			if (args[i].equalsIgnoreCase(parameter)) {
				return args[i + 1];
			}
		}

		return null;
	}

	/**
	 * Create the Frame, create the emulator instance, load the snapshot (if
	 * appropriate) and start the emulation loop.
	 */
	public static void main(String[] args) {
		Frame frame = new Frame("JZXFrame");

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				PerformanceCounter.report();
				System.exit(0);
			}
		});
		
		ConsoleLogger logger = new ConsoleLogger();
		BaseSpectrum spectrum = null;
		Z80Loader loader = null;
		URL url = null;

		try {
			// TODO: improve this
			String here = System.getProperty("user.dir").replace('\\', '/')
					+ "/";
			if (here.startsWith("/")) {
				url = new URL("file:" + here);
			} else {
				url = new URL("file:/" + here);
			}
		} catch (MalformedURLException mue) {
			logger.log(ILogger.C_ERROR, mue);
			System.exit(1);
		}

		int scale = 1;

		try {
			String scaleString = getParameter(args, SCALE_PARAMETER);
			if (scaleString != null) {
				scale = Integer.parseInt(scaleString);
			}

			if (scale < 1 || scale > 3) {
				scale = 1;
			}
		} catch (NumberFormatException nfe) {
			logger.log(ILogger.C_DEBUG, nfe);
		}

		String snapshot = getParameter(args, SNAPSHOT_PARAMETER);
		if (snapshot != null) {
			try {
				// TODO: use a factory
				loader = new Z80Loader(logger, url);
				loader.load(snapshot);

				if (loader.getMode() == BaseLoader.MODE_48) {
					spectrum = new pt.ist.ave.jzx.v48.Spectrum();
				} else if (loader.getMode() == BaseLoader.MODE_128) {
					spectrum = new pt.ist.ave.jzx.v128.Spectrum();
				} else {
					logger.log(ILogger.C_ERROR, "Unknown hardware mode: "
							+ loader.getMode());
				}
			} catch (IOException ioe) {
				logger.log(ILogger.C_ERROR, ioe);
			}
		}

		if (spectrum == null) {
			String mode = getParameter(args, MODE_PARAMETER);
			if (mode == null) {
				mode = MODE_48;
			}

			if (mode.equals(MODE_128)) {
				spectrum = new pt.ist.ave.jzx.v128.Spectrum();
			} else {
				spectrum = new pt.ist.ave.jzx.v48.Spectrum();
			}
		}

		spectrum.setScale(scale);
		spectrum.init(spectrum, logger);

		frame.add(spectrum.getContainer());

		frame.pack();
		frame.setVisible(true);

		spectrum.reset();

		if (loader != null) {
			spectrum.load(loader);
		}
		
		
		spectrum.emulate();
	}
}