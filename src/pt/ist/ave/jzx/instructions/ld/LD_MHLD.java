package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_MHLD extends Instruction {

	public LD_MHLD(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.getM_memory().write8(_cpu.hl16(), _cpu.getM_d8());
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
