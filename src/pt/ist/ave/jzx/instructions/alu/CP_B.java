package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class CP_B extends Instruction {

	public CP_B(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.cmp_a(_cpu.getM_b8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
