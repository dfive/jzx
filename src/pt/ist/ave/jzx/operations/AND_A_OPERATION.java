package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class AND_A_OPERATION extends Operation {
	private int _m_a8;
	
	public void and_a(int val8) {
		_m_a8 = _cpu.getM_a8() & val8;
		
		_cpu.setM_a8(_m_a8);
		
		_cpu.setM_signF(getM_signF());
		_cpu.setM_zeroF(getM_zeroF());
		_cpu.setM_halfcarryF(getM_halfcarryF());
		_cpu.setM_parityoverflowF(getM_parityoverflowF());
		_cpu.setM_addsubtractF(getM_addsubtractF());
		_cpu.setM_carryF(getM_carryF());
		_cpu.setM_3F(getM_3F());
		_cpu.setM_5F(getM_5F());
	}

	@Override
	public boolean getM_carryF() {
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
		return false;
	}

	@Override
	public boolean getM_parityoverflowF() {
		return Z80.m_parityTable[_m_a8];
	}

	@Override
	public boolean getM_halfcarryF() {
		return true;
	}

	@Override
	public boolean getM_zeroF() {
		return _m_a8 == 0;
	}

	@Override
	public boolean getM_signF() {
		return (_m_a8 & 0x80) != 0;
	}

	@Override
	public boolean getM_5F() {
		return (_m_a8 & Z80.FIVE_MASK) != 0;
	}

	@Override
	public boolean getM_3F() {
		return (_m_a8 & Z80.THREE_MASK) != 0;
	}

}
