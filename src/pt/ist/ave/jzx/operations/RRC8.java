package pt.ist.ave.jzx.operations;

public class RRC8 extends ShiftTest {

	boolean _carry;
	
	public int rcc8(int reg8) {
		_carry = (reg8 & 0x01) != 0;
		int work8 = ((reg8 >> 1) | ((_carry ? 1 : 0) << 7));
		_cpu.setM_carryF(_carry);
		shiftTest(work8);
		return work8;
	}
	
	@Override
	public boolean getM_carryF() {
		return _carry;
	}

}
