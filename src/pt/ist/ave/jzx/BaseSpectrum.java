package pt.ist.ave.jzx;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Label;
import java.awt.Panel;

/**
 * Base class extended by all Spectrum models (48k, 128k).
 * <P>
 * This class provides basic, common spectrum functionality for the various
 * spectrum models.
 * 
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan
 *         Surdulescu</A> (c) 2001 - 2006
 * @author <A HREF="mailto:webmaster@zx-spectrum.net">Erik Kunze</A> (c) 1995,
 *         1996, 1997
 * @author <A HREF="mailto:des@ops.netcom.net.uk">Des Herriott</A> (c) 1993,
 *         1994 <BR>
 *         You may use and distribute this software for free provided you
 *         include this copyright notice. You may not sell this software, use my
 *         name for publicity reasons or modify the code without permission from
 *         me.
 */
public abstract class BaseSpectrum extends BaseComponent {
	/**
	 * The 20ms frequency of the interrupt clock.
	 * 
	 * NOTE: This is set to 19 (instead of 20) because many games are
	 * frame-synchronized, and if they miss a screen refresh even by a little
	 * they wait until the next one. This means that roughly one out of two
	 * times, they will wait a frame too long. This effectively reduces the
	 * framerate by 50% to around 30FPS. By setting the frequency to 19, we
	 * guarantee that an interrupt will have arrived by the next frame, so the
	 * effective framerate is around 50FPS.
	 */
	public static final long FREQUENCY_MS = 19L;
	// public static final long FREQUENCY_MS = 1L;

	/** 48k Spectrum model 2 (issue 2) */
	public static final int ISSUE_2 = 2;

	/** 48k Spectrum model 3 (issue 3) */
	public static final int ISSUE_3 = 3;

	/** Label used to display various status messages. */
	protected Label m_status;

	/** Contained reference to the Z80 CPU. */
	protected Z80 m_cpu;

	/** Contained reference to the memory object. */
	protected BaseMemory m_memory;

	/** Contained reference to the I/O object. */
	protected BaseIO m_io;

	/** Contained reference to the screen object. */
	protected BaseScreen m_screen;

	/**
	 * Component that decorates the screen object for display in a Frame,
	 * Applet, etc.
	 */
	protected Container m_container;

	/** Contained reference to the keyboard object. */
	protected BaseKeyboard m_keyboard;

	/** The interrupt clock. */
	protected Clock m_clock;

	protected long m_frequency = FREQUENCY_MS;

	/** The model (issue) of this Spectrum. */
	protected int m_issue = ISSUE_3;

	/** The current vertical line being drawn. */
	protected int m_vline;

	/**
	 * The current scale value for this Spectrum.
	 * <P>
	 * This value is retrieved by the screen object during its <TT>init()</TT>
	 * phase.
	 */
	protected int m_scale = 1;

	/**
	 * The number of lines per TV frame.
	 * <P>
	 * This value is different for the different Spectrum models.
	 */
	protected int m_tvLines;

	/**
	 * The number of CPU T-States per TV line.
	 * <P>
	 * This value is different for the different Spectrum models.
	 */
	protected int m_cyclesLine;

	/**
	 * Allocate the clock object, and initialize all contained references (CPU,
	 * memory, I/O, screen and keyboard.)
	 */
	public void init(BaseSpectrum spectrum, ILogger logger) {
		super.init(spectrum, logger);

		m_clock = new Clock(m_frequency, logger);

		m_cpu.init(m_spectrum, logger);
		m_memory.init(m_spectrum, logger);
		m_io.init(m_spectrum, logger);
		m_screen.init(m_spectrum, logger);
		m_keyboard.init(m_spectrum, logger);

		m_container = new Panel();
		m_container.setLayout(new BorderLayout());

		m_status = new Label();

		m_container.add(m_screen, BorderLayout.CENTER);
		m_container.add(m_status, BorderLayout.SOUTH);

	}

	/**
	 * Reset all contained references (CPU, memory, I/O, screen and keyboard.)
	 */
	public void reset() {
		m_cpu.reset();
		m_memory.reset();
		m_io.reset();
		m_screen.reset();
		m_keyboard.reset();
	}

	/**
	 * Terminate all contained references (CPU, memory, I/O, screen and
	 * keyboard) and subsequently set them to null.
	 */
	public void terminate() {
		m_keyboard.terminate();
		m_io.terminate();
		m_screen.terminate();
		m_memory.terminate();
		m_cpu.terminate();

		m_status = null;

		m_keyboard = null;
		m_screen = null;
		m_io = null;
		m_memory = null;
		m_cpu = null;

		m_clock = null;

		super.terminate();
	}

	/**
	 * Trivial accessor for the issue (model) of this Spectrum.
	 */
	public int getIssue() {
		return m_issue;
	}

	/**
	 * Trivial accessor for the scale parameter of this Spectrum.
	 * <P>
	 * This method is called by the screen object in its <TT>init()</TT> method.
	 */
	public int getScale() {
		return m_scale;
	}

	/**
	 * Trivial mutator for the scale parameter of this Spectrum.
	 */
	public void setScale(int scale) {
		m_scale = scale;
	}

	/**
	 * Trivial accessor for the current TV line being drawn.
	 */
	public int getVline() {
		return m_vline;
	}

	/**
	 * Get the frequency used by the {@link #m_clock}.
	 * 
	 * @return The clock frequency, in milliseconds
	 */
	public long getFrequency() {
		return m_frequency;
	}

	/**
	 * Set the frequency used by the {@link #m_clock}.
	 * 
	 * @param frequency
	 *            The clock frequency, in milliseconds
	 */
	public void setFrequency(long frequency) {
		if (frequency <= 0) {
			throw new IllegalArgumentException("Invalid frequency: "
					+ frequency);
		}

		m_frequency = frequency;
		m_clock.setFrequency(frequency);
	}

	/**
	 * Trivial accessor for this Spectrum's CPU.
	 */
	public Z80 getCPU() {
		return m_cpu;
	}

	/**
	 * Trivial accessor for this Spectrum's memory.
	 */
	public BaseMemory getMemory() {
		return m_memory;
	}

	/**
	 * Trivial accessor for this Spectrum's I/O.
	 */
	public BaseIO getIO() {
		return m_io;
	}

	/**
	 * Trivial accessor for this Spectrum's screen.
	 */
	public BaseScreen getScreen() {
		return m_screen;
	}

	/**
	 * Return a Container that decorates the Spectrum screen and adds other UI
	 * elements such as a status bar.
	 */
	public Component getContainer() {
		return m_container;
	}

	/**
	 * Trivial accessor for this Spectrum's keyboard.
	 */
	public BaseKeyboard getKeyboard() {
		return m_keyboard;
	}

	/** String that describes the type of Spectrum ("48", "128"). */
	public abstract String getMode();

	/**
	 * The main emulator loop.
	 * <P>
	 * The steps performed are as follows:
	 * <UL>
	 * <LI>Start the clock.</LI>
	 * <LI>Repeat until stopped:</LI>
	 * <UL>
	 * <LI>If the current CPU T-States is more than the number of cycles per
	 * line, increment the number of lines.</LI>
	 * <LI>If the number of lines is greater than the number of lines per frame:
	 * </LI>
	 * <UL>
	 * <LI>If this is the 25th frame (twice per second) toggle the flash.</LI>
	 * </UL>
	 * <LI>Refresh the current screen frame.</LI>
	 * <LI>Wait for the next CPU interrupt.</LI>
	 * <LI>Decode and execute the next CPU instruction.</LI>
	 * </UL> </UL>
	 */
	public void emulate() {
		m_clock.start();

		m_cpu.emulate();

		m_clock.end();
	}

	private int m_frames;
	private int m_interrupts;
	private long m_fpsTimer = System.currentTimeMillis();
	private long clock = System.currentTimeMillis() + 366000;

	public void update() {
		int tStates = m_cpu.getTStates();

		if (tStates >= m_cyclesLine) {

			//			System.out.println("TSTATES: " + tStates);

			tStates -= m_cyclesLine;
			m_cpu.setTStates(tStates);

			// Tell the IO that this many cycles have elapsed
			// in order to facilitate processing data in the
			// streamers
			m_io.advance(m_cyclesLine);

			if (++m_vline == m_tvLines) {
				m_vline = 0;

				m_frames++;
				long elapsed = System.currentTimeMillis() - m_fpsTimer;
				//				long tau = System.currentTimeMillis();
				if (elapsed > 1000L) {
					long fps = ((m_frames * 1000L) / elapsed);
					//					m_status.setText("FPS: " + fps + "    " + (clock - tau)/1000);
					m_status.setText("FPS: " + fps);
					m_fpsTimer = System.currentTimeMillis();
					m_frames = 0;
				}


				// Fire an interrupt per frame
				m_interrupts++;

				// Every 25 interrupts (twice/sec)
				// toggle the flash.
				if (m_interrupts == 25) {
					m_interrupts = 0;
					m_screen.flash();
				}


				m_screen.repaint();

				synchronized (m_clock) {
					while (!m_clock.interrupted) {
						try {
							m_clock.wait();
						} catch (InterruptedException ie) {
							m_logger.log(ILogger.C_ERROR, ie);
						}
					}

					m_clock.interrupted = false;
				}

				m_cpu.interrupt();
			}
		}
	}

	/**
	 * Pause the emulation (asynchronous).
	 */
	public void pause() {
		m_cpu.pause();
	}

	/**
	 * Unpause the emulation (asynchronous).
	 */
	public void unpause() {
		m_cpu.unpause();
	}

	/**
	 * Stop the emulation (asynchronous.)
	 */
	public void stop() {
		m_cpu.stop();
	}

	/**
	 * Load the Spectrum contents from the given loader, by calling the
	 * <TT>load()</TT> method of all contained components.
	 */
	public void load(BaseLoader loader) {
		m_cpu.load(loader);
		m_memory.load(loader);
		m_io.load(loader);
		m_screen.load(loader);
		m_keyboard.load(loader);
	}
}
