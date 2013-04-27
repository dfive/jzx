package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public abstract class Operation {
	
	protected static Z80 _cpu;
	
	protected int[] _updatedFlags;
	
	public static void setCpu(Z80 cpu){
		_cpu = cpu;
	}
	
	public void updateFlags() {
		for(int i=0; i<_updatedFlags.length; ++i) {
			_cpu.setFlagOperation(_updatedFlags[i], this);
		}
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
	
	public void notImplementedError(String methodName) {
		throw new RuntimeException("The method " + methodName + " is not implemented in class " + this.getClass());
	}
}
