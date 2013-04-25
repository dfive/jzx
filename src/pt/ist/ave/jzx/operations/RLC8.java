package pt.ist.ave.jzx.operations;

public class RLC8 extends ShiftTest {
	int _myReg8;
	
	public int rlc8(int reg8) {
		_myReg8 = reg8;
		_cpu.setM_carryF(getM_carryF());
		_work8 = ((reg8 << 1) | (getM_carryF() ? 1 : 0)) & 0xff;
		shiftTest(reg8);
		return _work8;
	}
	
	@Override
	public boolean getM_carryF() {
		return (_myReg8 & 0x80) != 0;
	}

}
