package pt.ist.ave.jzx;

import java.awt.Graphics;
import java.awt.image.MemoryImageSource;

/**
 * Screen implementation that runs in both JDK1.1 and JDK1.2 VMs, but is
 * optimized for neither.
 * <P>
 * This implementation uses a MemoryImageSource, and calls the method
 * <TT>newPixels()</TT> to indicate that new data is available for drawing into
 * the image.
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
public class ScreenBoth extends BaseScreen {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The underlying RGB data for the MemoryImageSource object.
	 */
	private int[] m_data;

	/**
	 * The MemoryImageSource object whose data is updated and drawn on the
	 * screen.
	 */
	private MemoryImageSource m_memoryImageSource;

	/**
	 * Allocate the RGB data array and the MemoryImageSource object that
	 * Decorates the data.
	 */
	public void init(BaseSpectrum spectrum, ILogger logger) {
		super.init(spectrum, logger);

		m_data = new int[m_screenWidth * m_screenHeight];

		m_memoryImageSource = new MemoryImageSource(m_screenWidth,
				m_screenHeight, m_data, 0, m_screenWidth);
		m_memoryImageSource.setAnimated(true);
	}

	/**
	 * Update the RGB data array and the MemoryImageSource for the new scale
	 * value.
	 */
	protected void setScale(int scale) {
		super.setScale(scale);

		m_data = new int[m_screenWidth * m_screenHeight];

		m_memoryImageSource = new MemoryImageSource(m_screenWidth,
				m_screenHeight, m_data, 0, m_screenWidth);
		m_memoryImageSource.setAnimated(true);
	}

	/**
	 * Create the MemoryImageSource object from the given Graphics object.
	 */
	public void paint(Graphics g) {
		if (m_offscreenImage == null) {
			m_offscreenImage = createImage(m_memoryImageSource);
			m_offscreenImageGraphics = null;
		}

		super.paint(g);
	}

	/**
	 * Draw a given byte into the RGB array using the given attribute, at the
	 * given location.
	 */
	protected void draw8(int x, int y, int pix8, int attr8) {
		if (m_flashPhase && ((attr8 & FLASH) != 0)) {
			attr8 = (attr8 & 0xc0) | (~attr8 & 0x3f);
		}

		int ipix = m_inkTable[attr8 & ~FLASH];
		int ppix = m_paperTable[attr8 & ~FLASH];

		int start = x * m_scale + y * m_scale * m_screenWidth;

		for (int i = 0; i < 8 * m_scale;) {
			int rgb = (0xff000000 | s_rgbPalette[((pix8 & 0x80) != 0 ? ipix
					: ppix)]);
			for (int j = 0; j < m_scale; j++) {
				m_data[start++] = rgb;
				i++;
			}
			pix8 <<= 1;
		}

		start = x * m_scale + y * m_scale * m_screenWidth;

		for (int i = 1; i < m_scale; i++) {
			System.arraycopy(m_data, start, m_data,
					(start + i * m_screenWidth), 8 * m_scale);
		}
	}

	/**
	 * Call <TT>newPixels()</TT> on the MemoryImageSource object at the end of a
	 * frame.
	 */
	protected void endRender() {
		m_memoryImageSource.newPixels();
	}
}
