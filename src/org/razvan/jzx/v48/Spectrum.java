package org.razvan.jzx.v48;

import org.razvan.jzx.BaseKeyboard;
import org.razvan.jzx.BaseScreen;
import org.razvan.jzx.BaseSpectrum;
import org.razvan.jzx.ILogger;
import org.razvan.jzx.Z80;

/**
 * The 48k model specialization of the BaseSpectrum class.
 *
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan Surdulescu</A> (c) 2001 - 2006
 * <BR>
 * You may use and distribute this software for free provided you include
 * this copyright notice. You may not sell this software, use my name for
 * publicity reasons or modify the code without permission from me.
 */
public class Spectrum extends BaseSpectrum {
	/**
	 * Allocate the 48k model versions of the memory and I/O
	 * objects.
	 * <P>
	 * Set the m_tvLines parameter to 312 and m_cyclesLine
	 * parameter to 224.
	 *
	 * @see org.razvan.jzx.BaseSpectrum#m_tvLines
	 * @see org.razvan.jzx.BaseSpectrum#m_cyclesLine
	 */
	public void init(BaseSpectrum spectrum, ILogger logger) {
		m_cpu = new Z80();
		m_memory = new Memory();
		m_io = new IO();
		m_screen = BaseScreen.getInstance();
		m_keyboard = new BaseKeyboard();
		
		super.init(spectrum, logger);
		
		m_tvLines = 312;
		m_cyclesLine = 224;
	}
	
	public String getMode() {
		return "48";
	}
}