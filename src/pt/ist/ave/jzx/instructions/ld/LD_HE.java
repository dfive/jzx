package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_HE extends Instruction {

	public LD_HE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_h8(_cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
