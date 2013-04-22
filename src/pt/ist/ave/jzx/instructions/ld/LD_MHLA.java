package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_MHLA extends Instruction {

	public LD_MHLA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.getM_memory().write8(_cpu.hl16(), _cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
