package pt.ist.ave.jzx.instructions;

import pt.ist.ave.jzx.operations.RRCA_Operation;
import pt.ist.ave.jzx.operations.OperationsFactory;

public class RRCA extends Instruction {

	public RRCA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		RRCA_Operation rrcaOperation = OperationsFactory.rrca;
		rrcaOperation.rra();
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
