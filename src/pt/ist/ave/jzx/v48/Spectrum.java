package pt.ist.ave.jzx.v48;

import pt.ist.ave.jzx.BaseKeyboard;
import pt.ist.ave.jzx.BaseScreen;
import pt.ist.ave.jzx.BaseSpectrum;
import pt.ist.ave.jzx.ILogger;
import pt.ist.ave.jzx.Z80;

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
	 * @see pt.ist.ave.jzx.BaseSpectrum#m_tvLines
	 * @see pt.ist.ave.jzx.BaseSpectrum#m_cyclesLine
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