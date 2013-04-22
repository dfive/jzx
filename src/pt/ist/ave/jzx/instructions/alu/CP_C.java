package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class CP_C extends Instruction {

	public CP_C(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.cmp_a(_cpu.getM_c8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
