package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public abstract class Operation {
	
	protected static Z80 _cpu;
	
	protected int _val16;
	protected int _work32;
	protected int _idx;
	protected int _work8;
	
	public static void setCpu(Z80 cpu){
		_cpu = cpu;
	}
	/**
	 * @return the m_carryF
	 */
	public abstract boolean getM_carryF();

	/**
	 * @return the m_addsubtractF
	 */
	public abstract boolean getM_addsubtractF();

	/**
	 * @return the m_parityoverflowF
	 */
	public abstract boolean getM_parityoverflowF();

	/**
	 * @return the m_halfcarryF
	 */
	public abstract boolean getM_halfcarryF();

	/**
	 * @return the m_zeroF
	 */
	public abstract boolean getM_zeroF();

	/**
	 * @return the m_signF
	 */
	public abstract boolean getM_signF();

	/**
	 * @return the m_5F
	 */
	public abstract boolean getM_5F();

	/**
	 * @return the m_3F
	 */
	public abstract boolean getM_3F();
}
