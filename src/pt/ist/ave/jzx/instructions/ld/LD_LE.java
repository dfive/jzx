package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_LE extends Instruction {

	public LD_LE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_l8(_cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
