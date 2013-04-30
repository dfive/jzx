package pt.ist.ave.jzx.instructions;

import pt.ist.ave.jzx.operations.CCF_Operation;
import pt.ist.ave.jzx.operations.OperationsFactory;

public class CCF extends Instruction {

	public CCF(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		CCF_Operation op = new CCF_Operation();
		op.ccf();
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
