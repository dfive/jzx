package org.razvan.jzx;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Base class extended by all I/O components that comprise the
 * emulator.
 * <P>
 * This class provides basic, common I/O functionality for the
 * I/O subsystem of the emulator. Specific methods are overriden
 * or implemented so as to comply with the Spectrum 48k and
 * the Spectrum 128k models.
 *
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan Surdulescu</A> (c) 2001 - 2006
 * @author <A HREF="mailto:webmaster@zx-spectrum.net">Erik Kunze</A> (c) 1995, 1996, 1997
 * @author <A HREF="mailto:des@ops.netcom.net.uk">Des Herriott</A> (c) 1993, 1994
 * <BR>
 * You may use and distribute this software for free provided you include
 * this copyright notice. You may not sell this software, use my name for
 * publicity reasons or modify the code without permission from me.
 *
 * @see org.razvan.jzx.v48.IO
 * @see org.razvan.jzx.v128.IO
 */
public class BaseIO extends BaseComponent {
	/** The sampling frequency for playing sounds with the speaker or AY chip. */
	public static final float SAMPLE_FREQ = 48000.0f;
	/** The size of the buffer used for generating sounds. */
	public static final int LINE_BUF_SIZE = 4096;
	
	/**
	 * The number of microseconds for a CPU T State.
	 * 
	 * This constant is used in computing the number of samples
	 * to play after some number of T States have elapsed.
	 * Consider the note 'middle C' which has the frequency 261.63 hz. 
	 * In order to get this note the loudspeaker will have to be alternately 
	 * activated and deactivated every 1/523.26 th. of a second. In the 
	 * SPECTRUM the system clock is set to run at 3.5 mhz. and the note of 
	 * 'middle C' will require that the requisite OUT instruction be executed 
	 * as close as possible to every 6,689 T states.
	 */
	private static final double MICROSECONDS_PER_TSTATE = 1e6d / (523.6 * 6689);
	private static final double MICROSECONDS_PER_SAMPLE = 1e6d / SAMPLE_FREQ;
	
	private SourceDataLine m_line;
	
	/** The sound buffer as it is currently being filled and played. */
	protected byte[] m_buffer = new byte[BaseIO.LINE_BUF_SIZE];
	/** The current index into the sound buffer. */
	protected int m_index;
	
	public static int getAudioSamplesForTStates(int tStates) {
		int result = (int) ((tStates * BaseIO.MICROSECONDS_PER_TSTATE) / BaseIO.MICROSECONDS_PER_SAMPLE);
		if (result == 0) {
			return 1;
		} else {
			return result;
		}
	}
	
	/** Port address for the ULA. */
	public static final int P_ULA = 0xfe;
	
	/**
	 * Bit mask used to detemine if the specified
	 * port will cause a "select" on the ULA.
	 */
	public static final int B_ULA = 0x01;
	
	/** Bit mask used to determine the border color value. */
	public static final int B_BORDER = 0x07;
	
	/** Bit mask used to determine the "tape out" bit. */
	public static final int B_MIC = 0x08;
	
	/** Bit mask used to determine the speaker value. */
	public static final int B_SPEAKER = 0x10;
	
	/** Bit mask used to determine the keyboard value. */
	public static final int B_KEYBOARD = 0x1f;
	
	/** Bit mask used to determien the "tape in" bit. */
	public static final int B_EAR = 0x20;
	
	/** Kempston joystick address. */
	public static final int P_KEMPSTON = 0x1f;
	
	/**
	 * Bit mask used to determine if the Kempston
	 * joystick is selected.
	 */
	public static final int B_KEMPSTON = 0x20;
	
	/** Bit mask for "joystick right" event. */
	public static final int B_RIGHT = 0x01;
	
	/** Bit mask for "joystick left" event. */
	public static final int B_LEFT = 0x02;
	
	/** Bit mask for "joystick down" event. */
	public static final int B_DOWN = 0x04;
	
	/** Bit mask for "joystick up" event. */
	public static final int B_UP = 0x08;
	
	/** Bit mask for "joystick fire" event. */
	public static final int B_FIRE = 0x10;
	
	/** Port address for the Sinclair1 joystick (Interface II). */
	public static final int P_SINCLAIR1 = 0xeffe;
	
	/** Port address for the Sinclair2 joystick (Interface II). */
	public static final int P_SINCLAIR2 = 0xf7fe;
	
	/**
	 * Last border color.
	 * <P>
	 * This is used to determine if the newly specified border
	 * color should trigger a screen update.
	 */
	protected int m_lastBorderColor = BaseScreen.WHITE;
	
	/**
	 * The input port array.
	 * <P>
	 * This array is modified by the Z80 I/O instructions.
	 */
	protected int m_inPorts[];
	
	/**
	 * The output port array.
	 * <P>
	 * This array is modified by the Z80 I/O instructions.
	 */
	protected int m_outPorts[];
	
	/**
	 * The keyboard port array.
	 * <P>
	 * This array is modified by the Keyboard component
	 * directly.
	 */
	protected int m_keyPorts[];
	
	/**
	 * Cached reference to the Z80 component.
	 * <P>
	 * This is used to increment the CPU T-states
	 * after I/O instructions.
	 */
	protected Z80 m_cpu;
	
	/**
	 * Cached reference to the memory component.
	 * <P>
	 * This is used for all memory page modifications
	 * that are triggered by I/O instructions.
	 */
	protected BaseMemory m_memory;
	
	/**
	 * Cached reference to the screen component.
	 * <P>
	 * This is used to alter the border color.
	 */
	protected BaseScreen m_screen;
	
	protected int m_speakerLevel;
	
	/**
	 * Allocate the port arrays and cache the Z80, memory
	 * and screen components.
	 */
	public void init(BaseSpectrum spectrum, ILogger logger) {
		super.init(spectrum, logger);
		
		m_keyPorts = new int[9];
		m_inPorts = new int[256];
		m_outPorts = new int[256];
		
		m_cpu = m_spectrum.getCPU();
		m_memory = m_spectrum.getMemory();
		m_screen = m_spectrum.getScreen();

		// TODO: hacky, use audio only if running at real speed
		if (BaseSpectrum.FREQUENCY_MS > 1) {
			AudioFormat format = new AudioFormat(
					AudioFormat.Encoding.PCM_UNSIGNED, // encoding (pulse code modulation)
					BaseIO.SAMPLE_FREQ, // sample rate: number of samples taken per second, per channel
					8, // sample size in bits
					1, // channels (1 = mono, 2 = stereo)
					1, // frame size: number of channels * sample size per channel (in bytes)
					BaseIO.SAMPLE_FREQ, // frame rate: number of frames (set of samples for all channels) taken per second
					true); // big endian; relevant only if sample size > 8
			
			// It's good to set the recommended line buffer size to be a multiple of
			// the sample buffer size (instead of exactly the sample buffer size),
			// otherwise you introduce crackles when buffers are played. Presumably,
			// reaching the end of a line buffer may close the line or cause
			// some other end-condition that introduces a crackle
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format, 2 * BaseIO.LINE_BUF_SIZE);
			
			if (!AudioSystem.isLineSupported(info)) {
				m_logger.log(ILogger.C_DEBUG, "Unsupported audio format: " + format);
			}
			
			try {
				m_line = (SourceDataLine) AudioSystem.getLine(info);
				m_line.open();
				m_line.start();
			} catch (LineUnavailableException lue) {
				m_logger.log(ILogger.C_ERROR, lue);
				m_line = null;
			}
		}
	}
	
	/**
	 * Set the port arrays to zero.
	 */
	public void reset() {
		for (int i = 0; i < m_keyPorts.length; i++) {
			m_keyPorts[i] = 0xff;
		}
		
		for (int i = 0; i < m_inPorts.length; i++) {
			m_inPorts[i] = 0;
		}
		
		for (int i = 0; i < m_outPorts.length; i++) {
			m_outPorts[i] = 0;
		}
		
		if (m_line != null) {
			m_line.drain();
		}	
	}
	
	/**
	 * Release references to all cached components and port arrays.
	 */
	public void terminate() {
		if (m_line != null) {
			m_line.stop();
			m_line.close();
			m_line = null;
		}
		
		m_keyPorts = null;
		m_inPorts = null;
		m_outPorts = null;
		
		m_cpu = null;
		m_memory = null;
		m_screen = null;
		
		super.terminate();
	}
	
	/**
	 * Process an I/O "in" request.
	 * <P>
	 * The flow of this call is as follows:
	 * <UL>
	 * <LI>If you read from both the joystick and keyboard ports,
	 * the joystick takes priority.</LI>
	 * <LI>Read keyboard ports and increment CPU T-states.</LI>
	 * <LI>Return random value or memory value for all other
	 * unrecognized ports.</LI>
	 * </UL>
	 * <P>
	 * See the explicit code comments for specific details.
	 *
	 * @param port16 The 16-bit I/O input port.
	 * @return The 8-bit value read from the I/O port.
	 */
	public int in8(int port16) {
		// Joystick takes priority over keyboard.
		if ((port16 & B_KEMPSTON) == 0) {
			// Bits A5-7 are always 0
			return (m_inPorts[P_KEMPSTON] &
					(B_RIGHT | B_LEFT | B_DOWN | B_UP | B_FIRE));
		}
		
		// Read from keyboard or tape.
		if ((port16 & B_ULA) == 0) {
			// Bit 5 of port #FE is the EAR bit. Its value is either zero
			// (new Spectrum models) or one (Spectrum Model 2). Bits 6 and 7
			// are one.
			int res8 = (m_spectrum.getIssue() == BaseSpectrum.ISSUE_2) ? 0xff : 0xdf;
			
			// The high 8 bits of port #FE is read selects a half-row
			// of five keys:
			
			// Port:  Keys (bits 0 to 4)
			// #FEFE  SHIFT, Z, X, C, V
			// #EFFE  0, 9, 8, 7, 6
			// #FDFE  A, S, D, F, G
			// #DFFE  P, O, I, U, Y
			// #FBFE  Q, W, E, R, T
			// #BFFE  ENTER, L, K, J, H
			// #F7FE  1, 2, 3, 4, 5
			// #7FFE  SPACE, SYM SHFT, M, N
			
			// If one of the five lowest bits is zero, the key is pressed.
			// All half-rows are ANDed together.
			
			if ((port16 & 0x8000) == 0) {
				res8 &= m_keyPorts[7];
			}
			
			if ((port16 & 0x4000) == 0) {
				res8 &= m_keyPorts[6];
			}
			
			if ((port16 & 0x2000) == 0) {
				res8 &= m_keyPorts[5];
			}
			
			if ((port16 & 0x1000) == 0) {
				res8 &= m_keyPorts[4];
			}
			
			if ((port16 & 0x0800) == 0) {
				res8 &= m_keyPorts[3];
			}
			
			if ((port16 & 0x0400) == 0) {
				res8 &= m_keyPorts[2];
			}
			
			if ((port16 & 0x0200) == 0) {
				res8 &= m_keyPorts[1];
			}
			
			if ((port16 & 0x0100) == 0) {
				res8 &= m_keyPorts[0];
			}
			
			// Reading from port #FE is slower than from other ports, since
			// the ULA provides the result. A normal IN operation takes 11
			// T-States, while a IN FE operation takes 12 T-States.
			m_cpu.addTStates(1);
			
			return res8;
		}
		
		// If the processor reads from a non-existing IN port, nothing goes
		// on the data bus: you'll either read FF's (idle bus), or screen data
		// bytes (whenever the ULA is reading the screen memory.)
		int vline = m_spectrum.getVline();
		return (vline < 192 ?
				m_memory.read8((BaseScreen.ATTR_START) | ((vline & 0xf8) << 2)) :
					0xff);
	}

	/**
	 * Process an I/O "out" request.
	 * <P>
	 * The flow of this call is as follows:
	 * <UL>
	 * <LI>Set new border color, if appropriate.</LI>
	 * <LI>Output speaker sound, if appropriate.</LI>
	 * <LI>Insert appropriate delay if ULA is busy with video
	 * update.</LI>
	 * </UL>
	 * <P>
	 * See the explicit code comments for specific details.
	 *
	 * @param port16 The 16-bit I/O output port.
	 * @param val8 The 8-bit value to write to the I/O port.
	 */
	public void out(int port16, int val8) {
		// Border color and speaker control
		if ((port16 & B_ULA) == 0) {
			// Lowest three bits are the border color.
			// A zero in bit three activates the MIC output.
			// A one in bit four activates the EAR output (speaker.)
			// Other bits are unused.
			if (m_lastBorderColor != (val8 & B_BORDER)) {
				m_lastBorderColor = val8 & B_BORDER;
				m_screen.setBorderColor(m_lastBorderColor);
			}
			
			/*
			 * To turn the speaker on, write 1 to the 5th bit (val8)
			 * To turn the speaker off, write 0 to the 5th bit
			 * 
			 * The speaker is on if the 5th bit is 1 (m_outPorts[P_ULA])
			 * The speaker is off if the 5th bit is 0
			 */
			if ((val8 & B_SPEAKER) != 0) {
				m_speakerLevel = (byte) 0xef;
			} else {
				m_speakerLevel = 0;
			}
			
			// Writing to ULA I/O port causes the ULA to halt the processor. The
			// processor is halted when you want to access the ULA or low memory
			// and the ULA is busy reading. The ULA will only read bytes during 128
			// of the 224 T states of each screen line, in which case it halts the
			// CPU for 4 T-States.
			if (m_spectrum.getVline() < 192 && m_cpu.getTStates() < 128) {
				m_cpu.addTStates(4);
			}
		}
		
		m_outPorts[port16 & 0xff] = val8;
	}
	
	public void advance(int tStates) {
		m_index = mixSound(tStates, m_speakerLevel);
		
		if (m_index >= m_buffer.length) {
			// TODO: this blocks the main (CPU) thread!
			m_line.write(m_buffer, 0, BaseIO.LINE_BUF_SIZE);
			// Reset the buffer so that the next samples can be mixed
			for (int i = 0; i < m_buffer.length; i++) {
				m_buffer[i] = 0;
			}
			m_index = 0;
		}
	}
	
	protected int mixSound(int tStates, int val8) {
		int index = m_index;
		int samples = getAudioSamplesForTStates(tStates);

		// TODO: handle wrap-around
		while (index < m_index + samples && index < m_buffer.length) {
			int volume = ((m_buffer[index] & 0xff) + (val8 & 0xff));
			if (volume > 0xff) {
				volume = 0xff;
			}
			m_buffer[index++] = (byte) volume;
		}
		return index;
	}
	
	/**
	 * INport(port16) = INport(port16) OR mask8
	 */
	public void orIn(int port16, int mask8) {
		m_inPorts[port16] |= mask8;
	}
	
	/**
	 * INport(port16) = INport(port16) AND mask8
	 */
	public void andIn(int port16, int mask8) {
		m_inPorts[port16] &= mask8;
	}
	
	/**
	 * OUTport(port16) = OUTport(port16) OR mask8
	 */
	public void orOut(int port16, int mask8) {
		m_outPorts[port16] |= mask8;
	}
	
	/**
	 * OUTport(port16) = OUTport(port16) AND mask8
	 */
	public void andOut(int port16, int mask8) {
		m_outPorts[port16] &= mask8;
	}
	
	/**
	 * KEYport(port16) = KEYport(port16) OR mask8
	 */
	public void orKey(int port16, int mask8) {
		m_keyPorts[port16] |= mask8;
	}
	
	/**
	 * KEYport(port16) = KEYport(port16) AND mask8
	 */
	public void andKey(int port16, int mask8) {
		m_keyPorts[port16] &= mask8;
	}
	
	/**
	 * Extracts the saved border color and outputs
	 * it to the appropriate I/O port.
	 */
	public void load(BaseLoader loader) {
		out(BaseIO.P_ULA, loader.getBorder());
	}
}