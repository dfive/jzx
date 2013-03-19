package org.razvan.jzx;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Screen implementation that is optimized for running
 * in a JDK1.1 VM.
 * <P>
 * This implementation makes use of a cache of pre-rendered
 * Image objects for a given byte/attribute nibble. Its
 * performance hinges on the fact that the call to
 * <TT>Graphics.drawImage()</TT> is very fast in JDK1.1.
 *
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan Surdulescu</A> (c) 2001 - 2006
 * @author <A HREF="mailto:webmaster@zx-spectrum.net">Erik Kunze</A> (c) 1995, 1996, 1997
 * @author <A HREF="mailto:des@ops.netcom.net.uk">Des Herriott</A> (c) 1993, 1994
 * <BR>
 * You may use and distribute this software for free provided you include
 * this copyright notice. You may not sell this software, use my name for
 * publicity reasons or modify the code without permission from me.
 */
public class ScreenJava1 extends BaseScreen {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The maximum size of the image cache.
	 * <P>
	 * This is primarily for the purposes of
	 * running in Microsoft's JVIEW, since
	 * JVIEW has a limitation on the number
	 * of Image objects that can be allocated
	 * when running as an applet.
	 */
	private static final int IMAGE_CACHE_SIZE = 900;
	
	/**
	 * The image cache.
	 * <P>
	 * The cache is indexed on  the 4 pixel
	 * bits and the 7 attribute bits (exclude
	 * the flash bit, since it's simply an
	 * inversion of the attribute.) In sum,
	 * the cache has 2^11 elements.
	 */
	private Image[] m_imageMap;
	
	/**
	 * The current count of images allocated
	 * into the cache.
	 */
	private int m_count;
	
	/**
	 * Allocate the image cache.
	 */
	public void init(BaseSpectrum spectrum, ILogger logger) {
		super.init(spectrum, logger);
		
		m_imageMap = new Image[1 << 11];
	}
	
	/**
	 * Retrieve an Image object from the cache.
	 * <P>
	 * If the cache is not yet full, a new image
	 * is created, initialized and placed into
	 * the cache. Otherwise, a random image is
	 * taken out of the cache, reinitialized and
	 * replaced into the cache in a different
	 * position.
	 */
	protected Image getImage(int pix4, int attr8) {
		if (m_flashPhase && ((attr8 & FLASH) != 0)) {
			attr8 = (attr8 & 0xc0) | (~attr8 & 0x3f);
		}
		
		attr8 = (attr8 & ~FLASH);
		
		int color = ((pix4 << 7) | attr8);
		Image image = m_imageMap[color];
		
		if (image == null) {
			if (m_count > IMAGE_CACHE_SIZE) {
				while (true) {
					int i = (int) (Math.random() * m_imageMap.length);
					if (m_imageMap[i] != null) {
						image = m_imageMap[i];
						m_imageMap[i] = null;
						break;
					}
				}
			} else {
				image = this.createImage(4 * m_scale, 1 * m_scale);
				m_count++;
			}
			
			Graphics graphics = image.getGraphics();
			
			int ipix = m_inkTable[attr8];
			int ppix = m_paperTable[attr8];
			
			for (int i = 0; i < 4; i++) {
				graphics.setColor(s_colorPalette[((pix4 & 0x08) != 0 ? ipix : ppix)]);
				graphics.fillRect(i * m_scale, 0, 1 * m_scale, 1 * m_scale);
				pix4 <<= 1;
			}
			
			m_imageMap[color] = image;
		}
		
		return image;
	}
	
	/**
	 * Draw a given byte onto the screen using the given
	 * attribute, at the given location.
	 */
	protected void draw8(int x, int y, int pix8, int attr8) {
		Image image;
		
		image = getImage((pix8 & 0xf0) >> 4, attr8);
		m_offscreenImageGraphics.drawImage(image, x * m_scale, y * m_scale, this);
		
		image = getImage(pix8 & 0x0f, attr8);
		m_offscreenImageGraphics.drawImage(image, (x + 4) * m_scale, y * m_scale, this);
	}
	
	/**
	 * Clear and release the image map.
	 */
	public void terminate() {
		m_imageMap = null;
		
		super.terminate();
	}
}
