package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_AH extends Instruction {

	public LD_AH(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_a8(_cpu.getM_h8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
