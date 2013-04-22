package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class OR_E extends Instruction {

	public OR_E(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.or_a(_cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
