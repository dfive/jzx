package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_AE extends Instruction{

	public LD_AE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_a8(_cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
