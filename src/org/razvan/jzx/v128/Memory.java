package org.razvan.jzx.v128;

import java.io.IOException;

import org.razvan.jzx.BaseMemory;
import org.razvan.jzx.BaseSpectrum;
import org.razvan.jzx.ILogger;

/**
 * The 128k model specialization of the BaseMemory class.
 *
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan Surdulescu</A> (c) 2001 - 2006
 * <BR>
 * You may use and distribute this software for free provided you include
 * this copyright notice. You may not sell this software, use my name for
 * publicity reasons or modify the code without permission from me.
 */
public class Memory extends BaseMemory {
	/**
	 * Load the ROM files from "roms/128a.rom" and
	 * "roms/128b.rom"
	 */
	public void init(BaseSpectrum spectrum, ILogger logger) {
		super.init(spectrum, logger);
		
		try {
			readROM("/128a.rom", m_page[ROM0]);
			readROM("/128b.rom", m_page[ROM1]);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe.getMessage());
		}
	}
	
	/**
	 * Initialize all memory pages, except the ROM, to random values.
	 */
	public void reset() {
		// Skip pages 0 and 1 because they're the ROM
		for (int i = ROM2; i <= RAM7; i++) {
			for (int j = 0; j < PAGE_SIZE; j++) {
				m_page[i][j] = (byte) (Math.random() * 256);
			}
		}
	}
}
