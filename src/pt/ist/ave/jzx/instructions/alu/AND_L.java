package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class AND_L extends Instruction {

	public AND_L(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.and_a(_cpu.getM_l8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
