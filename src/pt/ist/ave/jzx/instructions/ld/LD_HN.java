package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_HN extends Instruction {

	public LD_HN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.inc16pc()));
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
