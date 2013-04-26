package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class ADD_XX extends Operation { 
	
	private int _work32;
	private int _idx;
	private int _work8;

	public void add_xx(int val16) {
		_work32 = _cpu.getM_xx16() + val16;
		_idx = ((_cpu.getM_xx16() & 0x800) >> 9) | ((val16 & 0x800) >> 10)
				| ((_work32 & 0x800) >> 11);
		_cpu.setM_xx16(_work32 & 0xffff);
		
		_cpu.setM_halfcarryF(getM_halfcarryF());
		_cpu.setM_addsubtractF(getM_addsubtractF());
		_cpu.setM_carryF(getM_carryF());

		_work8 = _cpu.getM_xx16() >> 8;
		
		_cpu.setM_3F(getM_3F());
		_cpu.setM_5F(getM_5F());

	}

	@Override
	public boolean getM_carryF() {
		return ((_work32 & 0x10000) != 0);
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
		return Z80.m_halfcarryTable[_idx];
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
		return (_work8 & Z80.FIVE_MASK) != 0;
	}

	@Override
	public boolean getM_3F() {
		return ((_work8 & Z80.THREE_MASK) != 0);
	}

}
