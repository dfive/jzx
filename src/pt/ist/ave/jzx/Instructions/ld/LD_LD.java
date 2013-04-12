package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_LD extends Instruction {

	public LD_LD(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_l8(_cpu.getM_d8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
