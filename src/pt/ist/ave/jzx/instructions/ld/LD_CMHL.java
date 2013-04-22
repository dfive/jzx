package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_CMHL extends Instruction {

	public LD_CMHL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.hl16()));
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
