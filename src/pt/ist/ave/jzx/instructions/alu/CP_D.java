package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class CP_D extends Instruction {

	public CP_D(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.cmp_a(_cpu.getM_d8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
