package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_EC extends Instruction {

	public LD_EC(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_e8(_cpu.getM_c8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
