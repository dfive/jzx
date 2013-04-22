package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_BMHL extends Instruction {

	public LD_BMHL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.hl16()));
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
