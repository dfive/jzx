package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class DEC_D extends Instruction {

	public DEC_D(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_d8(_cpu.dec8(_cpu.getM_d8()));
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
