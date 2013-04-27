package pt.ist.ave.jzx.instructions;

import pt.ist.ave.jzx.operations.SCF_Operation;

public class SCF extends Instruction {

	public SCF(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		SCF_Operation scfOperation = new SCF_Operation();
		scfOperation.scf();
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 4;
	}

}
