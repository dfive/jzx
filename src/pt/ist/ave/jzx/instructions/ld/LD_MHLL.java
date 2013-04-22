package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_MHLL extends Instruction {

	public LD_MHLL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.getM_memory().write8(_cpu.hl16(), _cpu.getM_l8());
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
