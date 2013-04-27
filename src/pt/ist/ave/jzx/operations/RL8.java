package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class RL8 extends ShiftTest {

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
	
	public int rl8(int reg8) {
		_carry = ((reg8 & 0x80) != 0);
		int work8 = ((reg8 << 1) | (_cpu.getM_carryF() ? 1 : 0)) & 0xff;
		
		_cpu.setM_carryF(_carry);
		
		shiftTest(work8);
//		updateFlags();
		
		return work8;
	}

	
	@Override
	public boolean getM_carryF() {
		return _carry;
	}

}
