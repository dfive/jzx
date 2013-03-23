package pt.ist.ave.jzx.v48;

import java.io.IOException;

import pt.ist.ave.jzx.BaseMemory;
import pt.ist.ave.jzx.BaseSpectrum;
import pt.ist.ave.jzx.ILogger;

/**
 * The 48k model specialization of the BaseMemory class.
 *
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan Surdulescu</A> (c) 2001 - 2006
 * <BR>
 * You may use and distribute this software for free provided you include
 * this copyright notice. You may not sell this software, use my name for
 * publicity reasons or modify the code without permission from me.
 */
public class Memory extends BaseMemory {
	/**
	 * Load the ROM files from "roms/spectrum.rom".
	 */
	public void init(BaseSpectrum spectrum, ILogger logger) {
		super.init(spectrum, logger);
		
		try {
			readROM("/spectrum.rom", m_page[ROM0]);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe.getMessage());
		}
	}
	
	/**
	 * Initialize all memory pages, except the ROM, to random values.
	 */
	public void reset() {
		// Skip page 0 because it's the ROM
		for (int i = ROM1; i <= RAM7; i++) {
			for (int j = 0; j < PAGE_SIZE; j++) {
				m_page[i][j] = (byte) (Math.random() * 256);
			}
		}
	}
}
