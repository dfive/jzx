package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class OR_B extends Instruction {

	public OR_B(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.or_a(_cpu.getM_b8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
