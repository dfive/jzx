package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class RLC8 extends ShiftTest {
	int _myReg8;
	private int _work8;
	{	
		_updatedFlags = new int[]{
				Z80.FLAG_ZERO,
				Z80.FLAG_SIGN,
				Z80.FLAG_HALF_CARRY,
				Z80.FLAG_PARITY_OVERFLOW,
				Z80.FLAG_ADD_SUBTRACT,
				Z80.FLAG_CARRY,
				Z80.FLAG_3,
				Z80.FLAG_5
		};
	}
	public int rlc8(int reg8) {
		_myReg8 = reg8;
		_cpu.setM_carryF(getM_carryF());
		_work8 = ((reg8 << 1) | (getM_carryF() ? 1 : 0)) & 0xff;
		shiftTest(reg8);
//		updateFlags();
		return _work8;
	}
	
	@Override
	public boolean getM_carryF() {
		return (_myReg8 & 0x80) != 0;
	}

}
