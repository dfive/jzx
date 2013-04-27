package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class RRCA_Operation extends Operation {

	private boolean m_carryF;
	private int m_a8;

	{	
		_updatedFlags = new int[]{
				Z80.FLAG_HALF_CARRY,
				Z80.FLAG_ADD_SUBTRACT,
				Z80.FLAG_CARRY,
				Z80.FLAG_3,
				Z80.FLAG_5,
		};
	}
	
	public void rra() {
		m_a8 = _cpu.getM_a8();
		m_carryF = (m_a8 & 0x01) !=0;
		
		_cpu.setM_a8( (m_a8>>1) | ((m_carryF ? 1 : 0)) << 7 );
		
		updateFlags();
	}
	
	@Override
	public boolean getM_carryF() {
		return m_carryF;
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
		return false;
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
