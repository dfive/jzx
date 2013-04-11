package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_MHLE extends Instruction {

	public LD_MHLE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.getM_memory().write8(_cpu.hl16(), _cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
