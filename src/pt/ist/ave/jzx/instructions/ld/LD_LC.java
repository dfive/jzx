package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_LC extends Instruction {

	public LD_LC(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_l8(_cpu.getM_c8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
