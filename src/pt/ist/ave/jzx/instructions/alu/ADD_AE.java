package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class ADD_AE extends Instruction {

	public ADD_AE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.add_a(_cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
