package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_EH extends Instruction {

	public LD_EH(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_e8(_cpu.getM_h8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
