package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_CH extends Instruction {

	public LD_CH(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_c8(_cpu.getM_h8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
