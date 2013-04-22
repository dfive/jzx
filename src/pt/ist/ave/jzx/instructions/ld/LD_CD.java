package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_CD extends Instruction {

	public LD_CD(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_c8(_cpu.getM_d8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
