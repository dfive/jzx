package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_HC extends Instruction {

	public LD_HC(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_h8(_cpu.getM_c8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
