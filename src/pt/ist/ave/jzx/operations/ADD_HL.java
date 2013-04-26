package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class ADD_HL extends Operation {
	private int _m_h8;
	private int _work32;
	private int _idx;

	public void add_hl(int val16) {
		_cpu.setM_x8(_cpu.getM_h8());

		int hl16 = _cpu.hl16();
		_work32 = hl16 + val16;
		_idx = ((hl16 & 0x800) >> 9) | ((val16 & 0x800) >> 10)
				| ((_work32 & 0x800) >> 11);

		_cpu.hl16(_work32 & 0xffff);

		_m_h8 = _cpu.getM_h8();


		_cpu.setM_halfcarryF(getM_halfcarryF());
		_cpu.setM_addsubtractF(getM_addsubtractF());
		_cpu.setM_carryF(getM_carryF());
		_cpu.setM_3F(getM_3F());
		_cpu.setM_5F(getM_5F());
	}

	@Override
	public boolean getM_carryF() {
		return (_work32 & 0x10000) != 0;
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
		return (_m_h8 & Z80.FIVE_MASK) != 0;
	}

	@Override
	public boolean getM_3F() {
		return (_m_h8 & Z80.THREE_MASK) != 0;
	}

}
