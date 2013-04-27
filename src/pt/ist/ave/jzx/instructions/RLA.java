package pt.ist.ave.jzx.instructions;

import pt.ist.ave.jzx.operations.RLA_Operation;

public class RLA extends Instruction {

	public RLA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		RLA_Operation rlaOperation = new RLA_Operation();
		rlaOperation.rla();
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
