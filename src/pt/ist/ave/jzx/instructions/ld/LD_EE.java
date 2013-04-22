package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_EE extends Instruction {

	public LD_EE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
