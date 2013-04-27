package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class CCF_Operation extends Operation {

	private boolean m_carryF;
	private boolean m_carryF2;
	private int m_a8;
	
	{	
		_updatedFlags = new int[]{
				Z80.FLAG_HALF_CARRY,
				Z80.FLAG_ADD_SUBTRACT,
				Z80.FLAG_3,
				Z80.FLAG_5,
				Z80.FLAG_CARRY
		};
	}
	
	public void ccf() {
		m_carryF = _cpu.getM_carryF();
		m_carryF2 = _cpu.getM_carryF();
		m_a8 = _cpu.getM_a8();
		updateFlags();
	}
	
	@Override
	public boolean getM_carryF() {
		return !m_carryF2;
	}

	@Override
	public boolean getM_addsubtractF() {
		return false;
	}

	@Override
	public boolean getM_parityoverflowF() {
		notImplementedError("getM_parityoverflowF");
		return false;
	}

	@Override
	public boolean getM_halfcarryF() {
		return m_carryF;
	}

	@Override
	public boolean getM_zeroF() {
		notImplementedError("getM_zeroF");
		return false;
	}

	@Override
	public boolean getM_signF() {
		notImplementedError("getM_signF");
		return false;
	}

	@Override
	public boolean getM_5F() {
		return (_cpu.getM_a8() & Z80.FIVE_MASK) != 0;
	}

	@Override
	public boolean getM_3F() {
		return (m_a8 & Z80.THREE_MASK) != 0;
	}

}
