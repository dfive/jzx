package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_AB extends Instruction {

	public LD_AB(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_a8(_cpu.getM_b8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
