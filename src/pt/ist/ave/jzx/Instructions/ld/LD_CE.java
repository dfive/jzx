package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_CE extends Instruction {

	public LD_CE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_c8(_cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
