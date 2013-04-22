package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class SUB_AL extends Instruction {

	public SUB_AL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.sub_a(_cpu.getM_l8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
