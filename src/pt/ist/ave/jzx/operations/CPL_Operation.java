package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class CPL_Operation extends Operation {

	
	private int m_a8;

	{	
		_updatedFlags = new int[]{
				Z80.FLAG_HALF_CARRY,
				Z80.FLAG_ADD_SUBTRACT,
				Z80.FLAG_3,
				Z80.FLAG_5,
		};
	}
	
	public void ccf() {
		
		m_a8 = _cpu.getM_a8();
		_cpu.setM_a8(m_a8 ^ 0xff);
		updateFlags();
	}
	
	@Override
	public boolean getM_carryF() {
		notImplementedError("getM_parityoverflowF");
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
		return true;
	}

	@Override
	public boolean getM_parityoverflowF() {
		notImplementedError("getM_parityoverflowF");
		return false;
	}

	@Override
	public boolean getM_halfcarryF() {
		return true;
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
		return (m_a8 & Z80.FIVE_MASK) != 0;
	}

	@Override
	public boolean getM_3F() {
		return (m_a8 & Z80.THREE_MASK) != 0;
	}

}
