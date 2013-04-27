package pt.ist.ave.jzx.instructions;

import pt.ist.ave.jzx.operations.CPL_Operation;

public class CPL extends Instruction {

	public CPL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		CPL_Operation op = new CPL_Operation();
		op.ccf();
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
