package pt.ist.ave.jzx.operations;

public class SLA8 extends ShiftTest {

	private boolean _carry;

	public int sla8(int reg8) {
		
		_carry = (reg8 & 0x80) != 0;
		
		_cpu.setM_carryF(getM_carryF());
		
		int work8 = (reg8 << 1) & 0xff;
		shiftTest(work8);

		return work8;
	}

	@Override
	public boolean getM_carryF() {
		return _carry;
	}

}
