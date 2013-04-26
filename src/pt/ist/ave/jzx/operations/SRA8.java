package pt.ist.ave.jzx.operations;

public class SRA8 extends ShiftTest {

	private boolean _carry;

	public int sra8(int reg8) {
		_carry = (reg8 & 0x01) != 0;
		_cpu.setM_carryF(getM_carryF());
		
		int work8 = (reg8 & 0x80);
		work8 = ((reg8 >> 1) | work8);
		shiftTest(work8);

		return work8;
	}

	@Override
	public boolean getM_carryF() {
		return _carry;
	}

}
