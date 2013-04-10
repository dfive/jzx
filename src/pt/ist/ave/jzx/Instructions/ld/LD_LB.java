package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_LB extends Instruction {

	public LD_LB(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_l8(_cpu.getM_b8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
