package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class ADD_HLDE extends Instruction {

	public ADD_HLDE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.add_hl(_cpu.de16());
	}

	@Override
	public int incTstates() {
		return 11;
	}

}
