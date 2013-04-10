package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_MDEA extends Instruction {

	public LD_MDEA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.getM_memory().write8(_cpu.de16(), _cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
