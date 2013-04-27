package pt.ist.ave.jzx.instructions;

import pt.ist.ave.jzx.operations.DAA_Operation;

public class DAA extends Instruction {

	public DAA(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		int work8;
		boolean carry = _cpu.getM_carryF();
		boolean addsubtract = _cpu.getM_addsubtractF();
		if (!addsubtract) {
			work8 = 0;
			if (_cpu.getM_halfcarryF() || (_cpu.getM_a8() & 0x0f) > 9) {
				work8 = 0x06;
			}
			if (_cpu.getM_carryF() || (_cpu.getM_a8() >> 4) > 9
					|| ((_cpu.getM_a8() >> 4) >= 9 && (_cpu.getM_a8() & 0x0f) > 9)) {
				work8 |= 0x60;
				carry = true;
			}
		} else {
			if (_cpu.getM_carryF()) {
				work8 = _cpu.getM_halfcarryF() ? 0x9a : 0xa0;
			} else {
				work8 = _cpu.getM_halfcarryF() ? 0xfa : 0x00;
			}
		}
		_cpu.add_a(work8);
		
		DAA_Operation daaOperation = new DAA_Operation();
		daaOperation.daa(addsubtract, carry);
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 4;
	}

}
