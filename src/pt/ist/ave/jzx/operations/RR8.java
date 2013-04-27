package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class RR8 extends ShiftTest {

	private boolean _carry;
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
	public int rr8(int reg8) {
		_carry = ((reg8 & 0x01) != 0);
		
		int work8 = ((reg8 >> 1) | ((_cpu.getM_carryF() ? 1 : 0) << 7));
		
		_cpu.setM_carryF(getM_carryF());
		
		shiftTest(work8);
//		updateFlags();
		return work8;
	}
	
	@Override
	public boolean getM_carryF() {
		return _carry;
	}

}
