package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class ADD_XX extends Operation { 
	
	public ADD_XX(int val16) {
		_val16 = val16;
	}
	
	public void execute() {
		_work32 = _cpu.getM_xx16() + _val16;
		_idx = ((_cpu.getM_xx16() & 0x800) >> 9) | ((_val16 & 0x800) >> 10)
				| ((_work32 & 0x800) >> 11);
		
		_cpu.setM_xx16(_work32 & 0xffff);
		_work8 = _cpu.getM_xx16() >> 8;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_halfcarryF() {
		return Z80.m_halfcarryTable[_idx];
	}

	@Override
	public boolean getM_zeroF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_signF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_5F() {
		return ((_work8 & Z80.FIVE_MASK) != 0);
	}

	@Override
	public boolean getM_3F() {
		return ((_work8 & Z80.THREE_MASK) != 0);
	}

}
