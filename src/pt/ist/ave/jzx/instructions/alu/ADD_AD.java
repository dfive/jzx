package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class ADD_AD extends Instruction {

	public ADD_AD(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.add_a(_cpu.getM_d8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
