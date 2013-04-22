package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class ADD_AL extends Instruction {

	public ADD_AL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.add_a(_cpu.getM_l8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
