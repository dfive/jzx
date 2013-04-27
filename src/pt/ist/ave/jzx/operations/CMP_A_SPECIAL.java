package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class CMP_A_SPECIAL extends Operation {

	private int _work16;
	private int _idx;
	
	{	
		_updatedFlags = new int[]{
				Z80.FLAG_ZERO,
				Z80.FLAG_SIGN,
				Z80.FLAG_HALF_CARRY,
				Z80.FLAG_ADD_SUBTRACT,
		};
	}
	
	public void cmp_a_special(int val8) { 
		_work16 = _cpu.getM_a8() - val8;
		_idx = ((_cpu.getM_a8() & 0x88) >> 1) | ((val8 & 0x88) >> 2)
				| ((_work16 & 0x88) >> 3);
		
		_cpu.setM_signF(getM_signF());
		_cpu.setM_zeroF(getM_zeroF());
		_cpu.setM_halfcarryF(getM_halfcarryF());
		_cpu.setM_addsubtractF(getM_addsubtractF());
//		updateFlags();
	}
	
	@Override
	public boolean getM_carryF() {
		notImplementedError("getM_carryF");
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
		return Z80.m_subhalfcarryTable[_idx & 0x7];
	}

	@Override
	public boolean getM_zeroF() {
		return (_work16 & 0xff) == 0;
	}

	@Override
	public boolean getM_signF() {
		return (_work16 & 0x80) != 0;
	}

	@Override
	public boolean getM_5F() {
		notImplementedError("getM_5F");
		return false;
	}

	@Override
	public boolean getM_3F() {
		notImplementedError("getM_3F");
		return false;
	}

}
