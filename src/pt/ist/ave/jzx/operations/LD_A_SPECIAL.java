package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class LD_A_SPECIAL extends Operation {

	int _myReg8;
	int _my_iff1b;
	{	
		_updatedFlags = new int[]{
				Z80.FLAG_ZERO,
				Z80.FLAG_SIGN,
				Z80.FLAG_HALF_CARRY,
				Z80.FLAG_PARITY_OVERFLOW,
				Z80.FLAG_ADD_SUBTRACT,
				Z80.FLAG_3,
				Z80.FLAG_5
		};
	}
	public void ld_a_special(int reg8) {
		_myReg8 = reg8;
		_my_iff1b = _cpu.getM_iff1b();
		
		_cpu.setM_a8(_myReg8);

//		_cpu.setM_signF(getM_signF());
//		_cpu.setM_zeroF(getM_zeroF());
//		_cpu.setM_halfcarryF(getM_halfcarryF());
//		_cpu.setM_parityoverflowF(getM_parityoverflowF());
//		_cpu.setM_addsubtractF(getM_addsubtractF());
//		_cpu.setM_3F(getM_3F());
//		_cpu.setM_5F(getM_5F());
		updateFlags();
	}
	
	@Override
	public boolean getM_carryF() {
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
		return false;
	}

	@Override
	public boolean getM_parityoverflowF() {
		return _my_iff1b != 0;
	}

	@Override
	public boolean getM_halfcarryF() {
		return false;
	}

	@Override
	public boolean getM_zeroF() {
		return _myReg8 == 0;
	}

	@Override
	public boolean getM_signF() {
		return (_myReg8 & 0x80) != 0;
	}

	@Override
	public boolean getM_5F() {
		return (_myReg8 & Z80.FIVE_MASK) != 0;
	}

	@Override
	public boolean getM_3F() {
		return (_myReg8 & Z80.THREE_MASK) != 0;
	}

}
