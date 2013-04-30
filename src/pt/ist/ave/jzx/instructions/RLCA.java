package pt.ist.ave.jzx.instructions;

import pt.ist.ave.jzx.operations.OperationsFactory;
import pt.ist.ave.jzx.operations.RLCA_Operation;

public class RLCA extends Instruction {

	public RLCA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		RLCA_Operation rlcaOperation = OperationsFactory.rlca;
		rlcaOperation.rla();
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
