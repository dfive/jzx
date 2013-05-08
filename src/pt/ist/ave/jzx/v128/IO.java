package pt.ist.ave.jzx.v128;

import pt.ist.ave.jzx.BaseIO;
import pt.ist.ave.jzx.BaseLoader;
import pt.ist.ave.jzx.BaseMemory;
import pt.ist.ave.jzx.BaseSpectrum;
import pt.ist.ave.jzx.ILogger;

/**
 * The 128k model specialization of the BaseIO class.
 *
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan Surdulescu</A> (c) 2001 - 2006
 * @author <A HREF="mailto:webmaster@zx-spectrum.net">Erik Kunze</A> (c) 1995, 1996, 1997
 * @author <A HREF="mailto:des@ops.netcom.net.uk">Des Herriott</A> (c) 1993, 1994
 * <BR>
 * You may use and distribute this software for free provided you include
 * this copyright notice. You may not sell this software, use my name for
 * publicity reasons or modify the code without permission from me.
 */
public class IO extends BaseIO {
	/** Definition for all 128k ports. */
	public static final int P_128 = 0xfd;
	
	/** Port address for 128k memory management. */
	public static final int P_BANK128 = 0x7ffd;
	
	/** Bit mask for the 128k memory banks. */
	public static final int B_BANK128 = 0x8002;
	
	/** Bit mask for selecting the RAM page on C000-FFFF. */
	public static final int B_SELRAM = 0x07;
	
	/** Bit mask for selecting the Screen RAM page. */
	public static final int B_SELSCREEN = 0x08;
	
	/** Bit mask for selecting the ROM page. */
	public static final int B_SELROM = 0x10;
	
	/** Bit mask for enabling/disabling memory paging. */
	public static final int B_PAGING = 0x20;
	
	/** Sound control register (AY-3-8912 chip) */
	public static final int P_SNDCONTROL = 0xfffd;
	
	/** Sound data register (AY-3-8912 chip) */
	public static final int P_SNDDATA = 0xbffd;
	
	/** Sound chip mask (AY-3-8912 chip) */
	public static final int B_SNDCHIP = 0x8002;
	
	/** Sound control mask (AY-3-8912 chip) */
	public static final int B_SNDCONTROL = 0x4000;
	
	/** Sound data mask (AY-3-8912 chip) */
	public static final int B_SNDDATA = B_SNDCONTROL;
	
	/** Last value written to the memory bank port. */
	protected int m_last0x7ffd;
	
	/** Last value written to the sound chip port. */
	protected int m_last0xfffd;
	
	/** AY-3-8912 ports. */
	protected int[] m_psgPorts;
	
	/** AY-3-8912 chip. */
	protected AY8912 m_ay8912;
	
	/**
	 * Allocate the AY port array and cache the reference to
	 * the AY-3-8912 chip.
	 */
	public void init(BaseSpectrum spectrum, ILogger logger) {
		super.init(spectrum, logger);

		m_psgPorts = new int[16];
		
		m_ay8912 = ((Spectrum) spectrum).getAY8912();
	}
	
	/**
	 * Set the AY port array to 0.
	 */
	public void reset() {
		for (int i = 0; i < 16; i++) {
			m_psgPorts[i] = 0;
		}
		
		super.reset();
	}
	
	/**
	 * Set the port array to null and release the
	 * cached reference to the AY chip.
	 */
	public void terminate() {
		m_psgPorts = null;
		m_ay8912 = null;
		
		super.terminate();
	}
	
	/**
	 * Read an 8-bit value from the specified 16-bit
	 * I/O port.
	 */
	public int in8(int port16) {
		int res8 = super.in8(port16);
		
		if ((port16 & 0xff) == P_128) {
			if ((port16 & (B_SNDCHIP | B_SNDDATA)) ==
				(P_SNDCONTROL & (B_SNDCHIP | B_SNDCONTROL))) {
				// When reading from a register, unused bits are always 0. Reading
				// always yields the value last written to the register, except for
				// R14 and R15 when bit 6 or 7 or R7 are reset (R14/R15 used for
				// input). On the AY-3-8912, when R7 bit 7 is reset, R15 always
				// reads 0xff.
				if (m_last0xfffd == 14) {
					// When register R14 acts as input port (bit 6 is reset in
					// register R7, Spectrum 128 software never does this),
					// bits in register R14 have following meaning:
					
					// Input:
					// bits 0-4: last values sent to these bit
					// bit    5: KEYPAD response bit
					// bit    6: DTR bit (i.e. printer is ready)
					// bit    7: RS232 input bit
					
					// Bits 5-7 are valid only if they are not masked, i.e
					// if corespodent mask bits is one, else they will return zeros.
					return 0xff;
				} else {
					return (m_psgPorts[m_last0xfffd]);
				}
			}
		}
		
		return res8;
	}
	
	
	/**
	 * Write an 8-bit value to the specified 16-bit
	 * I/O port.
	 */
	public void out(int port16, int val8) {
		super.out(port16, val8);
		
		if ((port16 & 0xff) == P_128) {
			if ((port16 & B_SNDCHIP) == (P_SNDCONTROL & B_SNDCHIP)) {
				// A register is selected by OUTing the register number in bits 0-3
				// to port #FFFD (only A15, A14 and A1 are decoded). Then write to
				// a register by OUTing to #BFFD. Writing to R14 or R15 when they
				// are selected for input does load the output register.
				if ((port16 & B_SNDCONTROL) != 0) {
					m_last0xfffd = val8;
				} else {
					// Store register values.
					m_psgPorts[m_last0xfffd] = val8;
					
					// Registers above R14 are ignored.
					// When register R14 acts as output port (bit 6 is set in
					// register R7), bits in register R14 have following
					// meanings:
					
					// Output:
					// bit    0: KEYPAD clock
					// bit  1,4: have no meaning
					// bit    2: CTS and MIDI output signal
					// bit    3: RS232 output bit
					// bits 5-7: masks for reading bits 5-7 */
					if (m_last0xfffd < 14) {
						m_ay8912.out8(m_last0xfffd, val8);
					}
				}
			} else {
				// Banked memory management
				if ((port16 & B_BANK128) == (P_BANK128 & B_BANK128)) {
					if (m_last0x7ffd == val8 || ((m_last0x7ffd & B_PAGING) != 0)) {
						return;
					}
					// Bit D3 is screen select; 0 selects page 5, 1 selects page 7
					m_screen.setPage((val8 & B_SELSCREEN) != 0 ? BaseMemory.RAM7 : BaseMemory.RAM5);
					if ((val8 & B_SELSCREEN) != (m_last0x7ffd & B_SELSCREEN)) {
						m_screen.reset();
						m_screen.repaint();
					}
					
					// Bit D5 is the page disable bit
					m_last0x7ffd = val8;

//					out.println("FIRST: " + (BaseMemory.RAM0 + (val8 & B_SELRAM)) + " to " + 3);
					if((BaseMemory.RAM0 + (val8 & B_SELRAM)) == 8)
						System.out.println("weeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
					// Bits D0-D2 are RAM select at 0xC000
					m_memory.pageIn(3, BaseMemory.RAM0 + (val8 & B_SELRAM));
					
					// Bit D4 is ROM select at 0x0000
//					out.println("SECOND: " + (BaseMemory.ROM0 + ((val8 & B_SELROM) >> 4)) + " to " + 0);
					m_memory.pageIn(0, BaseMemory.ROM0 + ((val8 & B_SELROM) >> 4));
				}
			}
		}
	}
	
	public void advance(int tStates) {
		mixSound(tStates, m_ay8912.getSound(tStates));
		super.advance(tStates);
	}
	
	/**
	 * Load the saved values of 0x7ffd and 0xfffd
	 * oirt values.
	 */
	public void load(BaseLoader loader) {
		super.load(loader);

		out(P_BANK128, loader.getLast0x7ffd());
		out(P_SNDCONTROL, loader.getLast0xfffd());
	}
}
