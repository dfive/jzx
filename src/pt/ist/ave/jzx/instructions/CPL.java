package pt.ist.ave.jzx.instructions;

import pt.ist.ave.jzx.operations.CPL_Operation;
import pt.ist.ave.jzx.operations.OperationsFactory;

public class CPL extends Instruction {

	public CPL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		CPL_Operation op = OperationsFactory.cpl_operation;
		op.ccf();
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
