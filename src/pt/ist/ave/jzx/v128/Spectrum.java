package pt.ist.ave.jzx.v128;

import pt.ist.ave.jzx.BaseKeyboard;
import pt.ist.ave.jzx.BaseScreen;
import pt.ist.ave.jzx.BaseSpectrum;
import pt.ist.ave.jzx.ILogger;
import pt.ist.ave.jzx.Z80;

/**
 * The 128k model specialization of the BaseSpectrum class.
 *
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan Surdulescu</A> (c) 2001 - 2006
 * <BR>
 * You may use and distribute this software for free provided you include
 * this copyright notice. You may not sell this software, use my name for
 * publicity reasons or modify the code without permission from me.
 */
public class Spectrum extends BaseSpectrum {
	protected AY8912 m_ay8912;
	
	public AY8912 getAY8912() {
		return m_ay8912;
	}
	
	/**
	 * Allocate the 128k model versions of the memory, I/O
	 * and sound objects.
	 * <P>
	 * Set the m_tvLines parameter to 311 and m_cyclesLine
	 * parameter to 228.
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
		m_ay8912 = new AY8912();
		
		super.init(spectrum, logger);
		m_ay8912.init(spectrum, logger);
		
		m_tvLines = 311;
		m_cyclesLine = 228;
	}
	
	public String getMode() {
		return "128";
	}
}
