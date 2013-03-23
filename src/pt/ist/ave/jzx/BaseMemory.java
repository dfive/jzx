package pt.ist.ave.jzx;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Base class extended by all memory components that comprise the emulator.
 * <P>
 * This class provides basic, common memory functionality for the memory
 * subsystem of the emulator. Specific methods are overriden or implemented so
 * as to comply with the Spectrum 48k and the Spectrum 128k models.
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
 * 
 * @see pt.ist.ave.jzx.v48.Memory
 * @see pt.ist.ave.jzx.v128.Memory
 */
public abstract class BaseMemory extends BaseComponent {
	/**
	 * The size, in bytes, for a memory page.
	 */
	public static final int PAGE_SIZE = 16384;

	/** The first ROM page. */
	public static final int ROM0 = 0;

	/** The second ROM page. */
	public static final int ROM1 = 1;

	/** The third ROM page. */
	public static final int ROM2 = 2;

	/** The fourth ROM page. */
	public static final int ROM3 = 3;

	/** The first RAM page. */
	public static final int RAM0 = 4;

	/** The second RAM page. */
	public static final int RAM1 = 5;

	/** The third RAM page. */
	public static final int RAM2 = 6;

	/** The fourth RAM page. */
	public static final int RAM3 = 7;

	/** The fifth RAM page. */
	public static final int RAM4 = 8;

	/** The sixth RAM page. */
	public static final int RAM5 = 9;

	/** The seventh RAM page. */
	public static final int RAM6 = 10;

	/** The eighth RAM page. */
	public static final int RAM7 = 11;

	/**
	 * Maps a frame number to a physical page number.
	 * <P>
	 * There are four possible frame numbers (0 to 3.)
	 */
	protected int[] m_frameToPage;

	/**
	 * Maps the frame number to the physical page data.
	 * <P>
	 * There are four possible frame numbers (0 to 3.)
	 */
	protected byte[][] m_frame;

	/**
	 * The full memory array.
	 * <P>
	 * The sixteen possible phisical pages (4 ROM pages and 12 RAM pages.)
	 */
	protected byte[][] m_page;

	/**
	 * Cached reference to the screen component.
	 * <P>
	 * This is used to update the screen whenever the appropriate memory area is
	 * modified.
	 */
	protected BaseScreen m_screen;

	/**
	 * Allocate the memory arrays, cache the screen component and pages in the
	 * appropriate memory pages.
	 * <P>
	 * Note that the cached screen reference can be null after this call, in the
	 * event that the BaseSpectrum parameter is itself null. This is allowed for
	 * "headless" memory objects that are not tied to a particular BaseSpectrum
	 * instance. This is used for loading the snapshot data into a memory
	 * instance, that is then copied directly into the physical BaseSpectrum
	 * memory instance.
	 * 
	 * @see #pageIn
	 */
	public void init(BaseSpectrum spectrum, ILogger logger) {
		super.init(spectrum, logger);

		m_frameToPage = new int[4];
		m_page = new byte[12][PAGE_SIZE];
		m_frame = new byte[4][];

		if (m_spectrum != null) {
			m_screen = m_spectrum.getScreen();
		}

		pageIn(0, ROM0);
		pageIn(1, RAM5);
		pageIn(2, RAM2);
		pageIn(3, RAM0);
	}

	/**
	 * Sets the memory arrays to null and releases the cached screen reference.
	 */
	public void terminate() {
		m_frameToPage = null;
		m_page = null;
		m_frame = null;

		m_screen = null;

		super.terminate();
	}

	/**
	 * Load the contents of a ROM file into the given memory page.
	 * <p>
	 * The ROM file is specified as a Java Resource and loaded by means of
	 * <tt>Class.getResource(String)</tt>.
	 * 
	 * @param name
	 *            The name of the resource where the ROM file resides.
	 * @param page
	 *            The memory page into which to load the ROM file.
	 * @throws IOException
	 *             Thrown if the resource is not found or an error occurs during
	 *             reading.
	 */
	protected void readROM(String name, byte[] page) throws IOException {
		URL url = this.getClass().getResource(name);
		if (url == null) {
			throw new IOException("Could not find resource '" + name + "'");
		}

		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();

		try {
			int offset = 0;
			while (true) {
				int length = is.read(page, offset, page.length - offset);
				if (length <= 0) {
					break;
				} else {
					offset += length;
				}
			}
		} finally {
			is.close();
		}
	}

	/**
	 * Maps a particular physical page into a given virtual frame number.
	 * <P>
	 * The method sets:
	 * <UL>
	 * <LI><TT>m_frameToPage[frame] = page</TT></LI>
	 * <LI><TT>m_frame[frame] = m_page[page]</TT></LI>
	 * </UL>
	 * 
	 * @param frame
	 *            The virtual frame number. You cannot map a RAM page into frame
	 *            0.
	 * @param page
	 *            The physical page number.
	 */
	public void pageIn(int frame, int page) {
		if ((frame == 0) && (page >= RAM0)) {
			m_logger.log(ILogger.C_DEBUG,
					"Ignored attempt to page RAM at 0x0000");
			return;
		}

		m_frameToPage[frame] = page;
		m_frame[frame] = m_page[page];
	}

	/**
	 * Returns the byte array that comprises a particular page.
	 */
	public byte[] getBytes(int page) {
		return m_page[page];
	}

	/**
	 * Reads a byte from a particular 16-bit address.
	 * <P>
	 * The address is mapped into a frame number by taking the first two bits,
	 * and then frame number is then mapped into a physical page. The remaining
	 * address bits are used to index into the physical page.
	 * 
	 * @param addr16
	 *            The 16-bit address to read from.
	 * @return Returns the 8 bits at the specified address.
	 */
	public int read8(int addr16) {
		return (m_frame[(addr16 >> 14)][(addr16 & 0x3fff)] & 0xff);
	}

	/**
	 * Writes a byte to a particular 16-bit address.
	 * <P>
	 * The address is mapped into a frame number by taking the first two bits,
	 * and then frame number is then mapped into a physical page. The remaining
	 * address bits are used to index into the physical page.
	 * <P>
	 * Nothing is written to a ROM page.
	 * <P>
	 * If the address falls into the memory mapped area of the screen memory,
	 * the screen object is updated appropriately.
	 * 
	 * @param addr16
	 *            The 16-bit address to write to.
	 * @param val8
	 *            The 8 bits to write at the specified address.
	 */
	public void write8(int addr16, int val8) {
		int frame = (addr16 >> 14);
		int offset = (addr16 & 0x3fff);

		if (m_frame[frame][offset] == (byte) val8) {
			return;
		}

		int page = m_frameToPage[frame];

		// RAM0 is read-only
		if (page < RAM0) {
			return;
		}

		m_frame[frame][offset] = (byte) val8;

		if (m_spectrum != null) {
			if (page == m_screen.getPage()) {
				if (offset < BaseScreen.PIXEL_LENGTH) {
					m_screen.screenTouch(addr16);
				} else if (offset < (BaseScreen.PIXEL_LENGTH + BaseScreen.ATTR_LENGTH)) {
					m_screen.attrTouch(addr16);
				}
			}
		}
	}

	/**
	 * Reads a word starting at a particular 16-bit address.
	 * <P>
	 * The method calls <TT>read8()</TT> for two consecutive addresses and
	 * collates the results.
	 * 
	 * @param addr16
	 *            The 16-bit address to start reading from.
	 * @return Returns the 16 bits from the two consecutive addresses.
	 * 
	 * @see #read8
	 */
	public int read16(int addr16) {
		return ((read8((addr16 + 1) & 0xffff) << 8) | read8(addr16));
	}

	/**
	 * Writes a word starting at a particular 16-bit address.
	 * <P>
	 * The method calls <TT>write8()</TT> for two consecutive addresses, with
	 * the two halves of the specified argument.
	 * 
	 * @param addr16
	 *            The 16-bit address to start writing to.
	 * @param val16
	 *            The 16 bits to start writing at the specified address.
	 * 
	 * @see #write16
	 */
	public void write16(int addr16, int val16) {
		write8(addr16, (val16 & 0xff));
		write8((addr16 + 1) & 0xffff, (val16 >> 8));
	}

	/**
	 * Load the memory contents from the given BaseLoader object.
	 * <P>
	 * The BaseLoader contains a "headless" memory object inside it, whose page
	 * contents are copied into the page contents of "this" memory object.
	 * 
	 * @see BaseLoader
	 */
	public void load(BaseLoader loader) {
		BaseMemory memory = loader.getMemory();
		for (int i = RAM0; i <= RAM7; i++) {
			byte[] source = memory.getBytes(i);
			byte[] destination = getBytes(i);
			System.arraycopy(source, 0, destination, 0, BaseMemory.PAGE_SIZE);
		}
	}

	/**
	 * Return a string representation of the memory state, which is useful for
	 * debugging.
	 */
	public String toString() {
		StringBuffer result = new StringBuffer();

		for (int i = ROM0; i <= RAM7; i++) {
			result.append("P" + i + "=");
			int crc = 0;
			for (int j = 0; j < PAGE_SIZE; j++) {
				crc += (m_page[i][j] & 0xff);
			}
			result.append(crc);
			result.append(" ");
		}

		return result.toString();
	}
}
