package pt.ist.ave.jzx.operations;

public class RR8 extends ShiftTest {

	private boolean _carry;

	public int rr8(int reg8) {
		_carry = ((reg8 & 0x01) != 0);
		
		int work8 = ((reg8 >> 1) | ((_cpu.getM_carryF() ? 1 : 0) << 7));
		
		_cpu.setM_carryF(getM_carryF());
		
		shiftTest(work8);

		return work8;
	}
	
	@Override
	public boolean getM_carryF() {
		return _carry;
	}

}
