package org.razvan.jzx;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Screen implementation that is optimized for running
 * in a JDK1.2 VM.
 * <P>
 * This implementation makes use of a BufferedImage object,
 * whose setRGB() method is very fast for updating a large
 * array of new RGB values.
 *
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan Surdulescu</A> (c) 2001 - 2006
 * @author <A HREF="mailto:webmaster@zx-spectrum.net">Erik Kunze</A> (c) 1995, 1996, 1997
 * @author <A HREF="mailto:des@ops.netcom.net.uk">Des Herriott</A> (c) 1993, 1994
 * <BR>
 * You may use and distribute this software for free provided you include
 * this copyright notice. You may not sell this software, use my name for
 * publicity reasons or modify the code without permission from me.
 */
public class ScreenJava2 extends BaseScreen {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Dimensions for the 8-pixel wide area to
	 * update into the BufferedImage.
	 */
	private int m_pix8Width, m_pix8Height;
	
	/**
	 * The segment data to update into the BufferedImage.
	 */
	private int[] m_segment;
	
	/**
	 * The BufferedImage instance.
	 */
	private BufferedImage m_bufferedOffscreenImage;
	
	/**
	 * The data buffer for the offscreen image.
	 */
	private DataBuffer m_buffer;
	
	/**
	 * Compute the size of the segment that will be drawn
	 * into the BufferedImage, and allocate it.
	 */
	public void init(BaseSpectrum spectrum, ILogger logger) {
		super.init(spectrum, logger);
		
		m_pix8Width = 8 * m_scale;
		m_pix8Height = m_scale;
		
		m_segment = new int[m_pix8Width * m_pix8Height];
	}
	
	/**
	 * Update the segment size and re-allocate it for the
	 * new scale value.
	 */
	protected void setScale(int scale) {
		super.setScale(scale);
		
		m_pix8Width = 8 * m_scale;
		m_pix8Height = m_scale;
		
		m_segment = new int[m_pix8Width * m_pix8Height];
	}
	
	/**
	 * Create an Image that is compatible with the screen
	 * type and color depth.
	 */
	protected Image createCompatibleImage(int width, int height) {
		m_bufferedOffscreenImage = (BufferedImage) super.createCompatibleImage(width, height);
		m_buffer = m_bufferedOffscreenImage.getRaster().getDataBuffer();
		
		return m_bufferedOffscreenImage;
	}
	
	/**
	 * Draw a given byte onto the screen using the given
	 * attribute, at the given location.
	 */
	protected void draw8(int x, int y, int pix8, int attr8) {
		int xscale = x * m_scale;
		int yscale = y * m_scale;
		
		if (m_flashPhase && ((attr8 & FLASH) != 0)) {
			attr8 = (attr8 & 0xc0) | (~attr8 & 0x3f);
		}
		
		int ipix = m_inkTable[attr8 & ~FLASH];
		int ppix = m_paperTable[attr8 & ~FLASH];
		
		int[] segment;
		int offset, width;
		boolean update;
		
		// TODO: make this work for 16 bit
		/*
		 16bit: rmask=f800   gmask=7e0  bmask=1f amask=0
		 32bit: rmask=ff0000 gmask=ff00 bmask=ff amask=0
		 */
		if (m_buffer instanceof DataBufferInt) {
			segment = ((DataBufferInt) m_buffer).getBankData()[0];
			width = m_screenWidth;
			offset = yscale * width + xscale;
			// If we update the raster directly, there is no need
			// to call setRGB()
			update = false;
		} else {
			segment = m_segment;
			width = m_pix8Width;
			offset = 0;
			update = true;
		}
		
		for (int i = 0; i < m_pix8Width;) {
			int rgb = s_rgbPalette[((pix8 & 0x80) != 0 ? ipix : ppix)];
			for (int j = 0; j < m_scale; j++) {
				segment[offset + i++] = rgb;
			}
			pix8 <<= 1;
		}
		
		for (int i = 1; i < m_pix8Height; i++) {
			System.arraycopy(segment, offset, segment, offset + i * width, m_pix8Width);
		}
		
		if (update) {
			m_bufferedOffscreenImage.setRGB(
					xscale, yscale, // starting coordinates
					m_pix8Width, m_pix8Height, // width and height of region
					m_segment, // RGB data
					0, // offset into RGB data
					m_pix8Width // scanline stride for RGB data
			);
		}
	}
	
	public void dumpScreenshot() {
		try {
			// Save as PNG
			File file = new File("screenshot-" + System.currentTimeMillis() + ".png");
			ImageIO.write(m_bufferedOffscreenImage, "png", file);
		} catch (IOException e) {
			m_logger.log(ILogger.C_ERROR, "Failed to save screenshot: " +
					e.getMessage());
		}		
	}
}
