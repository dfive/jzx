package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class INC_DE extends Instruction {

	public INC_DE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.inc16de();
	}

	@Override
	public int incTstates() {
		return 6;
	}

}
